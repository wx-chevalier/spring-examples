package com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.voucher.service;

import cn.bootx.platform.common.core.exception.DataNotExistException;
import cn.bootx.platform.common.core.rest.PageResult;
import cn.bootx.platform.common.core.rest.param.PageParam;
import cn.bootx.platform.common.core.util.LocalDateTimeUtil;
import cn.bootx.platform.common.mybatisplus.util.MpUtil;
import cn.bootx.platform.daxpay.exception.pay.PayFailureException;
import cn.bootx.platform.daxpay.param.channel.VoucherPayParam;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.voucher.dao.VoucherManager;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.voucher.entity.Voucher;
import com.taotao.cloud.payment.biz.daxpay.single.service.dto.channel.voucher.VoucherDto;
import com.taotao.cloud.payment.biz.daxpay.single.service.param.channel.voucher.VoucherQuery;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 储值卡查询
 *
 * @author xxm
 * @since 2022/3/14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherQueryService {

    private final VoucherManager voucherManager;

    /**
     * 分页
     */
    public PageResult<VoucherDto> page(PageParam pageParam, VoucherQuery query) {
        return MpUtil.convert2DtoPageResult(voucherManager.page(pageParam, query));
    }

    /**
     * 根据id查询
     */
    public VoucherDto findById(Long id) {
        return voucherManager.findById(id).map(Voucher::toDto).orElseThrow(() -> new DataNotExistException("储值卡不存在"));
    }

    /**
     * 根据卡号查询
     */
    public VoucherDto findByCardNo(String cardNo) {
        return voucherManager.findByCardNo(cardNo)
                .map(Voucher::toDto)
                .orElseThrow(() -> new DataNotExistException("储值卡不存在"));
    }

    /**
     * 根据卡号查询
     */
    public Voucher getVoucherByCardNo(String cardNo) {
        return voucherManager.findByCardNo(cardNo)
                .orElseThrow(() -> new DataNotExistException("储值卡不存在"));
    }


    /**
     * 获取并检查储值卡
     */
    public Voucher getAndCheckVoucher(VoucherPayParam voucherPayParam) {
        String cardNo = voucherPayParam.getCardNo();
        Voucher voucher = voucherManager.findByCardNo(cardNo).orElseThrow(()->new PayFailureException("储值卡不存在"));

        // 卡信息校验
        String timeCheck = this.check(voucher);
        if (StrUtil.isNotBlank(timeCheck)) {
            throw new PayFailureException(timeCheck);
        }
        return voucher;
    }

    /**
     * 判断卡号是否存在
     */
    public boolean existsByCardNo(String cardNo){
        return voucherManager.existsByCardNo(cardNo);
    }

    /**
     * 获取并判断卡状态
     */
    public VoucherDto getAndJudgeVoucher(String cardNo){
        Voucher voucher = voucherManager.findByCardNo(cardNo).orElseThrow(() -> new DataNotExistException("储值卡不存在"));
        // 过期
        String checkMsg = this.check(voucher);
        if (StrUtil.isNotBlank(checkMsg)){
            throw new PayFailureException(checkMsg);
        }
        return voucher.toDto();
    }

    /**
     * 卡信息检查
     */
    public String check(Voucher voucher) {
        // 有效期校验
        if (voucher.isEnduring()){
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (!LocalDateTimeUtil.between(now, voucher.getStartTime(), voucher.getEndTime())){
            return "";
        }
        return null;
    }

}
