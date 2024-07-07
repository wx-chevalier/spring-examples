package com.taotao.cloud.message.biz.austin.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 撤回任务信息
 *
 * @author 3y
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecallTaskInfo {
    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 需要撤回的消息ids
     */
    private List<String> recallMessageId;

    /**
     * 发送账号
     */
    private Integer sendAccount;

    /**
     * 发送渠道
     */
    private Integer sendChannel;
}
