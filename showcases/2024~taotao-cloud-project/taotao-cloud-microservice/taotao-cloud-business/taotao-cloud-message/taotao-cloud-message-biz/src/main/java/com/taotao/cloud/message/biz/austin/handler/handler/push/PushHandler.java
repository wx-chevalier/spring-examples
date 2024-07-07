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

package com.taotao.cloud.message.biz.austin.handler.handler.push;

import org.dromara.hutoolcore.collection.CollUtil;
import org.dromara.hutoolcore.util.IdUtil;
import org.dromara.hutoolhttp.ContentType;
import org.dromara.hutoolhttp.Header;
import org.dromara.hutoolhttp.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.taotao.cloud.message.biz.austin.common.constant.SendAccountConstant;
import com.taotao.cloud.message.biz.austin.common.domain.TaskInfo;
import com.taotao.cloud.message.biz.austin.common.dto.account.GeTuiAccount;
import com.taotao.cloud.message.biz.austin.common.dto.model.PushContentModel;
import com.taotao.cloud.message.biz.austin.common.enums.ChannelType;
import com.taotao.cloud.message.biz.austin.handler.domain.push.PushParam;
import com.taotao.cloud.message.biz.austin.handler.domain.push.getui.BatchSendPushParam;
import com.taotao.cloud.message.biz.austin.handler.domain.push.getui.SendPushParam;
import com.taotao.cloud.message.biz.austin.handler.domain.push.getui.SendPushResult;
import com.taotao.cloud.message.biz.austin.handler.handler.BaseHandler;
import com.taotao.cloud.message.biz.austin.handler.handler.Handler;
import com.taotao.cloud.message.biz.austin.support.domain.MessageTemplate;
import com.taotao.cloud.message.biz.austin.support.utils.AccountUtils;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 通知栏消息发送处理
 *
 * <p>(目前具体的实现是个推服务商，安卓端已验证)
 *
 * @author 3y
 */
@Component
@Slf4j
public class PushHandler extends BaseHandler implements Handler {

    private static final String BASE_URL = "https://restapi.getui.com/v2/";
    private static final String SINGLE_PUSH_PATH = "/push/single/cid";
    private static final String BATCH_PUSH_CREATE_TASK_PATH = "/push/list/message";
    private static final String BATCH_PUSH_PATH = "/push/list/cid";

    public PushHandler() {
        channelCode = ChannelType.PUSH.getCode();
    }

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean handler(TaskInfo taskInfo) {

        try {
            GeTuiAccount account = accountUtils.getAccount(
                    taskInfo.getSendAccount(),
                    SendAccountConstant.GE_TUI_ACCOUNT_KEY,
                    SendAccountConstant.GE_TUI_ACCOUNT_PREFIX,
                    GeTuiAccount.class);
            String token = redisTemplate
                    .opsForValue()
                    .get(SendAccountConstant.GE_TUI_ACCESS_TOKEN_PREFIX + taskInfo.getSendAccount());
            PushParam pushParam = PushParam.builder()
                    .token(token)
                    .appId(account.getAppId())
                    .taskInfo(taskInfo)
                    .build();

            String result;
            if (taskInfo.getReceiver().size() == 1) {
                result = singlePush(pushParam);
            } else {
                result = batchPush(createTaskId(pushParam), pushParam);
            }
            SendPushResult sendPushResult = JSON.parseObject(result, SendPushResult.class);
            if (sendPushResult.getCode().equals(0)) {
                return true;
            }
            // 常见的错误 应当 关联至 AnchorState,由austin后台统一透出失败原因
            log.error(
                    "PushHandler#handler fail!result:{},params:{}",
                    JSON.toJSONString(sendPushResult),
                    JSON.toJSONString(taskInfo));
        } catch (Exception e) {
            log.error(
                    "PushHandler#handler fail!e:{},params:{}",
                    Throwables.getStackTraceAsString(e),
                    JSON.toJSONString(taskInfo));
        }
        return false;
    }

    /**
     * 单推
     *
     * @param pushParam
     * @return http result
     */
    private String singlePush(PushParam pushParam) {
        String url = BASE_URL + pushParam.getAppId() + SINGLE_PUSH_PATH;
        SendPushParam sendPushParam = assembleParam(
                (PushContentModel) pushParam.getTaskInfo().getContentModel(),
                pushParam.getTaskInfo().getReceiver());
        String body = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header("token", pushParam.getToken())
                .body(JSON.toJSONString(sendPushParam))
                .timeout(2000)
                .execute()
                .body();
        return body;
    }

    /**
     * 批量推送
     *
     * @param taskId 个推 返回的任务Id
     * @param pushParam
     * @return
     */
    private String batchPush(String taskId, PushParam pushParam) {
        String url = BASE_URL + pushParam.getAppId() + BATCH_PUSH_PATH;
        BatchSendPushParam batchSendPushParam = BatchSendPushParam.builder()
                .taskId(taskId)
                .isAsync(true)
                .audience(BatchSendPushParam.AudienceVO.builder()
                        .cid(pushParam.getTaskInfo().getReceiver())
                        .build())
                .build();
        String body = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header("token", pushParam.getToken())
                .body(JSON.toJSONString(batchSendPushParam))
                .timeout(2000)
                .execute()
                .body();
        return body;
    }

    /**
     * 群推前需要构建taskId
     *
     * @param pushParam
     * @return http result
     */
    private String createTaskId(PushParam pushParam) {
        String url = BASE_URL + pushParam.getAppId() + BATCH_PUSH_CREATE_TASK_PATH;
        SendPushParam param =
                assembleParam((PushContentModel) pushParam.getTaskInfo().getContentModel());
        String taskId = "";
        try {
            String body = HttpRequest.post(url)
                    .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                    .header("token", pushParam.getToken())
                    .body(JSON.toJSONString(param))
                    .timeout(2000)
                    .execute()
                    .body();

            taskId = JSON.parseObject(body, SendPushResult.class).getData().getString("taskId");
        } catch (Exception e) {
            log.error(
                    "PushHandler#createTaskId fail :{},params:{}",
                    Throwables.getStackTraceAsString(e),
                    JSON.toJSONString(pushParam.getTaskInfo()));
        }

        return taskId;
    }

    private SendPushParam assembleParam(PushContentModel pushContentModel) {
        return assembleParam(pushContentModel, null);
    }

    private SendPushParam assembleParam(PushContentModel pushContentModel, Set<String> cid) {
        SendPushParam param = SendPushParam.builder()
                .requestId(String.valueOf(IdUtil.getSnowflake().nextId()))
                .pushMessage(SendPushParam.PushMessageVO.builder()
                        .notification(SendPushParam.PushMessageVO.NotificationVO.builder()
                                .title(pushContentModel.getTitle())
                                .body(pushContentModel.getContent())
                                .clickType("startapp")
                                .build())
                        .build())
                .build();
        if (CollUtil.isNotEmpty(cid)) {
            param.setAudience(SendPushParam.AudienceVO.builder().cid(cid).build());
        }
        return param;
    }

    @Override
    public void recall(MessageTemplate messageTemplate) {}
}
