/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.order.application.service.cart.render.impl;

import com.taotao.cloud.common.enums.PromotionTypeEnum;
import com.taotao.cloud.common.utils.number.CurrencyUtils;
import com.taotao.cloud.order.application.service.cart.render.ICartRenderStep;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 购物促销信息渲染实现
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 08:50:44
 */
@AllArgsConstructor
@Service
public class CouponRender implements ICartRenderStep {

    private final PromotionPriceUtil promotionPriceUtil;
    private final MemberCouponService memberCouponService;

    @Override
    public RenderStepEnum step() {
        return RenderStepEnum.COUPON;
    }

    @Override
    public void render(TradeDTO tradeDTO) {
        // 优惠券列表
        this.renderCouponRule(tradeDTO);
        // 主要渲染各个优惠的价格
        this.renderCoupon(tradeDTO);
    }

    /**
     * 渲染优惠券规则
     *
     * @param tradeDTO 交易dto
     * @since 2022-04-28 08:52:38
     */
    private void renderCouponRule(TradeDTO tradeDTO) {
        List<MemberCoupon> memberCouponList = memberCouponService.getMemberCoupons();

        if (!memberCouponList.isEmpty()) {
            this.checkMemberExistCoupon(tradeDTO, memberCouponList);
        } else {
            tradeDTO.setPlatformCoupon(null);
            tradeDTO.setStoreCoupons(new HashMap<>());
        }
        memberCouponList.forEach(memberCoupon -> available(tradeDTO, memberCoupon));
    }

    /**
     * 检查使用中的优惠券是否存在与用户的优惠券中
     *
     * @param tradeDTO 交易dto
     * @param memberCouponList 会员优惠券列表
     * @since 2022-04-28 08:52:40
     */
    private void checkMemberExistCoupon(TradeDTO tradeDTO, List<MemberCoupon> memberCouponList) {
        if (tradeDTO.getPlatformCoupon() != null && tradeDTO.getPlatformCoupon().getMemberCoupon() != null) {
            boolean b = memberCouponList.parallelStream().anyMatch(i -> i.getId()
                    .equals(tradeDTO.getPlatformCoupon().getMemberCoupon().getId()));
            if (!b) {
                tradeDTO.setPlatformCoupon(null);
            }
        }
        if (!tradeDTO.getStoreCoupons().isEmpty()) {
            for (Map.Entry<String, MemberCouponDTO> entry :
                    tradeDTO.getStoreCoupons().entrySet()) {
                if (entry.getValue().getMemberCoupon() != null
                        && memberCouponList.parallelStream().noneMatch(i -> i.getId()
                                .equals(entry.getValue().getMemberCoupon().getId()))) {
                    tradeDTO.getStoreCoupons().remove(entry.getKey());
                }
            }
        }
    }

    /**
     * 判定优惠券是否可用
     *
     * @param tradeDTO 交易dto
     * @param memberCoupon 会员优惠券
     * @since 2022-04-28 08:52:50
     */
    private void available(TradeDTO tradeDTO, MemberCoupon memberCoupon) {
        if (memberCoupon == null) {
            return;
        }
        List<CartSkuVO> filterSku = filterSkuVo(tradeDTO.getCheckedSkuList(), memberCoupon);
        if (filterSku == null || filterSku.isEmpty()) {
            tradeDTO.getCantUseCoupons().add(new MemberCouponVO(memberCoupon, "购物车中没有满足优惠券使用范围的优惠券"));
            return;
        }
        List<PriceDetailDTO> priceDetailDTOS =
                filterSku.stream().map(CartSkuVO::getPriceDetailDTO).toList();

        PriceDetailDTO totalPrice = new PriceDetailDTO();
        totalPrice.accumulationPriceDTO(priceDetailDTOS);

        // 满足条件判定
        if (totalPrice.getGoodsPrice() >= memberCoupon.getConsumeThreshold()) {
            tradeDTO.getCanUseCoupons().add(memberCoupon);
        } else {
            tradeDTO.getCantUseCoupons()
                    .add(new MemberCouponVO(
                            memberCoupon,
                            "优惠券使用门槛不足，还差"
                                    + StringUtils.toFen(CurrencyUtils.sub(
                                            memberCoupon.getConsumeThreshold(), totalPrice.getGoodsPrice()))
                                    + "元"));
        }
    }

    /**
     * 过滤购物车商品信息，按照优惠券的适用范围过滤
     *
     * @param cartSkuVOS 购物车中的产品列表
     * @param memberCoupon 会员优惠券
     * @return 按照优惠券的适用范围过滤的购物车商品信息
     */
    private List<CartSkuVO> filterSkuVo(List<CartSkuVO> cartSkuVOS, MemberCoupon memberCoupon) {

        List<CartSkuVO> filterSku;
        // 平台店铺过滤
        if (Boolean.TRUE.equals(memberCoupon.getIsPlatform())) {
            filterSku = cartSkuVOS;
        } else {
            filterSku = cartSkuVOS.stream()
                    .filter(cartSkuVO -> cartSkuVO.getStoreId().equals(memberCoupon.getStoreId()))
                    .toList();
        }
        if (filterSku == null || filterSku.isEmpty()) {
            return Collections.emptyList();
        }
        // 优惠券类型判定
        switch (PromotionsScopeTypeEnum.valueOf(memberCoupon.getScopeType())) {
            case ALL:
                return filterSku;
            case PORTION_GOODS:
                // 按照商品过滤
                filterSku = filterSku.stream()
                        .filter(cartSkuVO -> memberCoupon
                                .getScopeId()
                                .contains(cartSkuVO.getGoodsSku().getId()))
                        .toList();
                break;

            case PORTION_SHOP_CATEGORY:
                // 按照店铺分类过滤
                filterSku = this.filterPromotionShopCategory(filterSku, memberCoupon);
                break;

            case PORTION_GOODS_CATEGORY:

                // 按照店铺分类过滤
                filterSku = filterSku.stream()
                        .filter(cartSkuVO -> {
                            // 平台分类获取
                            String[] categoryPath =
                                    cartSkuVO.getGoodsSku().getCategoryPath().split(",");
                            // 平台三级分类
                            String categoryId = categoryPath[categoryPath.length - 1];
                            return memberCoupon.getScopeId().contains(categoryId);
                        })
                        .toList();
                break;
            default:
                return Collections.emptyList();
        }
        return filterSku;
    }

    /**
     * 优惠券按照店铺分类过滤
     *
     * @param filterSku 过滤的购物车商品信息
     * @param memberCoupon 会员优惠
     * @return 优惠券按照店铺分类过滤的购物车商品信息
     */
    private List<CartSkuVO> filterPromotionShopCategory(List<CartSkuVO> filterSku, MemberCoupon memberCoupon) {
        return filterSku.stream()
                .filter(cartSkuVO -> {
                    if (CharSequenceUtil.isNotEmpty(cartSkuVO.getGoodsSku().getStoreCategoryPath())) {
                        // 获取店铺分类
                        String[] storeCategoryPath =
                                cartSkuVO.getGoodsSku().getStoreCategoryPath().split(",");
                        for (String category : storeCategoryPath) {
                            // 店铺分类只要有一项吻合，即可返回true
                            if (memberCoupon.getScopeId().contains(category)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .toList();
    }

    /**
     * 渲染优惠券
     *
     * @param tradeDTO 购物车展示信息
     */
    private void renderCoupon(TradeDTO tradeDTO) {
        MemberCouponDTO platformCoupon = tradeDTO.getPlatformCoupon();
        // 如果有勾选平台优惠券
        if (platformCoupon != null) {
            renderSku(tradeDTO, platformCoupon);
        }
        // 计算商家优惠券
        Map<String, MemberCouponDTO> map = tradeDTO.getStoreCoupons();
        if (map != null && map.size() > 0) {
            for (MemberCouponDTO memberCouponDTO : map.values()) {
                renderSku(tradeDTO, memberCouponDTO);
            }
        }
    }

    /**
     * 渲染sku优惠信息
     *
     * @param tradeDTO 交易DTO
     * @param memberCouponDTO 优惠券DTO
     */
    private void renderSku(TradeDTO tradeDTO, MemberCouponDTO memberCouponDTO) {

        // 计算优惠总金额
        BigDecimal countPrice = 0D;
        Map<String, BigDecimal> couponMap = memberCouponDTO.getSkuDetail();
        for (BigDecimal skuPrice : couponMap.values()) {
            countPrice = CurrencyUtils.add(countPrice, skuPrice);
        }

        // 接收具体优惠券信息
        MemberCoupon coupon = memberCouponDTO.getMemberCoupon();
        // 处理一个极端情况，如果优惠券满减金额大于订单金额
        if (coupon.getCouponType().equals(CouponTypeEnum.PRICE.name()) && coupon.getPrice() > countPrice) {
            // 将符合优惠券的金额写入，即最大扣减金额
            coupon.setPrice(countPrice);
        }

        if (coupon.getCouponType().equals(CouponTypeEnum.PRICE.name())) {
            // 减免现金，则按照商品价格计算 需要通过工具类进行优惠金额的分发，分发给每个商品
            this.renderCouponPrice(couponMap, tradeDTO, coupon, memberCouponDTO);
        } else {
            // 打折券 直接计算
            this.renderCouponDiscount(couponMap, tradeDTO, coupon);
        }
    }

    /**
     * 减免现金，则按照商品价格计算 需要通过工具类进行优惠金额的分发，分发给每个商品
     *
     * @param couponMap 优惠券结算信息
     * @param tradeDTO 交易dto
     * @param coupon 优惠券信息
     * @param memberCouponDTO 用于计算优惠券结算详情
     */
    private void renderCouponPrice(
            Map<String, BigDecimal> couponMap,
            TradeDTO tradeDTO,
            MemberCoupon coupon,
            MemberCouponDTO memberCouponDTO) {
        // 分发优惠券
        promotionPriceUtil.recountPrice(
                tradeDTO,
                memberCouponDTO.getSkuDetail(),
                memberCouponDTO.getMemberCoupon().getPrice(),
                Boolean.TRUE.equals(coupon.getIsPlatform())
                        ? PromotionTypeEnum.PLATFORM_COUPON
                        : PromotionTypeEnum.COUPON);
        // 如果是平台券 则需要计算商家承担比例
        if (Boolean.TRUE.equals(coupon.getIsPlatform()) && coupon.getStoreCommission() > 0) {

            // 循环所有优惠券
            for (String skuId : couponMap.keySet()) {

                for (CartSkuVO cartSkuVO : tradeDTO.getSkuList()) {
                    // 写入平台优惠券承担比例
                    if (cartSkuVO.getGoodsSku().getId().equals(skuId)) {
                        // 写入店铺承担比例
                        cartSkuVO.getPriceDetailDTO().setSiteCouponPoint(coupon.getStoreCommission());
                    }
                }
            }
        }
    }

    /**
     * 打折券计算
     *
     * @param couponMap 优惠券结算信息
     * @param tradeDTO 交易dto
     * @param coupon 优惠券信息
     */
    private void renderCouponDiscount(Map<String, BigDecimal> couponMap, TradeDTO tradeDTO, MemberCoupon coupon) {
        // 循环所有优惠券
        for (String skuId : couponMap.keySet()) {

            // 循环购物车商品
            for (CartSkuVO item : tradeDTO.getSkuList()) {
                // 如果id相等，则渲染商品价格信息
                if (item.getGoodsSku().getId().equals(skuId)) {

                    PriceDetailDTO priceDetailDTO = item.getPriceDetailDTO();

                    // 打折金额=商品金额*折扣/10
                    BigDecimal discountCouponPrice = CurrencyUtils.mul(
                            priceDetailDTO.getGoodsPrice(),
                            CurrencyUtils.sub(1, CurrencyUtils.div(coupon.getDiscount(), 10, 3)));

                    // 平台券则写入店铺承担优惠券比例
                    if (Boolean.TRUE.equals(coupon.getIsPlatform())) {
                        priceDetailDTO.setSiteCouponPrice(discountCouponPrice);
                        priceDetailDTO.setSiteCouponPoint(coupon.getStoreCommission());
                    }
                    priceDetailDTO.setCouponPrice(
                            CurrencyUtils.add(priceDetailDTO.getCouponPrice(), discountCouponPrice));
                }
            }
        }
    }
}
