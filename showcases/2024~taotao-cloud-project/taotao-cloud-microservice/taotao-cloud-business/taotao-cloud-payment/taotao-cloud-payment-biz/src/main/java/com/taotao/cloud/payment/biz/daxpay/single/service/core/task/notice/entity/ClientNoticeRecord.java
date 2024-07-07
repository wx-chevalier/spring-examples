package com.taotao.cloud.payment.biz.daxpay.single.service.core.task.notice.entity;

import cn.bootx.platform.common.core.function.EntityBaseFunction;
import cn.bootx.platform.common.mybatisplus.base.MpCreateEntity;
import cn.bootx.platform.daxpay.service.code.ClientNoticeSendTypeEnum;
import cn.bootx.platform.daxpay.service.core.task.notice.convert.ClientNoticeConvert;
import cn.bootx.platform.daxpay.service.dto.record.notice.ClientNoticeRecordDto;
import cn.bootx.table.modify.annotation.DbColumn;
import cn.bootx.table.modify.annotation.DbTable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息通知任务记录表
 * @author xxm
 * @since 2024/2/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@DbTable(comment = "消息通知任务记录")
@TableName("pay_client_notice_record")
public class ClientNoticeRecord extends MpCreateEntity implements EntityBaseFunction<ClientNoticeRecordDto> {

    /** 任务ID */
    @DbColumn(comment = "任务ID")
    private Long taskId;

    /** 请求次数 */
    @DbColumn(comment = "请求次数")
    private Integer reqCount;

    /** 发送是否成功 */
    @DbColumn(comment = "发送是否成功")
    private boolean success;

    /**
     * 发送类型, 自动发送, 手动发送
     * @see ClientNoticeSendTypeEnum
     */
    @DbColumn(comment = "发送类型")
    private String sendType;

    /** 错误信息 */
    @DbColumn(comment = "错误信息")
    private String errorMsg;

    /**
     * 转换
     */
    @Override
    public ClientNoticeRecordDto toDto() {
        return ClientNoticeConvert.CONVERT.convert(this);
    }
}
