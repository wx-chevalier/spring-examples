package com.taotao.cloud.order.application.liteflow.component;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.example.bean.PriceStepVO;
import com.yomahub.liteflow.example.enums.PriceTypeEnum;
import com.yomahub.liteflow.example.slot.PriceContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 会员折扣计算组件
 */
@Component("memberDiscountCmp")
public class MemberDiscountCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        PriceContext context = this.getContextBean(PriceContext.class);
        String memberCode = context.getMemberCode();

        /***这里Mock下通过memberCode去查会员等级表然后获取的会员折扣为9折的代码***/
        BigDecimal memberDiscount = new BigDecimal("0.9");

        //进行计算会员折扣
        BigDecimal prePrice = context.getLastestPriceStep().getCurrPrice();
        BigDecimal currPrice = prePrice.multiply(memberDiscount).setScale(2, RoundingMode.HALF_UP);

        //加入到价格步骤中
        context.addPriceStep(new PriceStepVO(PriceTypeEnum.MEMBER_DISCOUNT,
                memberCode,
                prePrice,
                currPrice.subtract(prePrice),
                currPrice,
                PriceTypeEnum.MEMBER_DISCOUNT.getName()));

    }

    @Override
    public boolean isAccess() {
        PriceContext context = this.getContextBean(PriceContext.class);
        if(CollectionUtils.isNotEmpty(context.getProductPackList())
                && StringUtils.isNotBlank(context.getMemberCode())){
            return true;
        }else{
            return false;
        }
    }
}
