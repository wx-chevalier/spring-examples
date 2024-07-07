package com.taotao.cloud.message.biz.austin.handler.handler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendresultRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationRecallRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationRecallResponse;
import com.google.common.base.Throwables;
import com.java3y.austin.common.constant.AccessTokenPrefixConstant;
import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.constant.SendChanelUrlConstant;
import com.java3y.austin.common.domain.LogParam;
import com.java3y.austin.common.domain.RecallTaskInfo;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.dto.account.DingDingWorkNoticeAccount;
import com.java3y.austin.common.dto.model.DingDingWorkContentModel;
import com.java3y.austin.common.enums.ChannelType;
import com.java3y.austin.common.enums.SendMessageType;
import com.java3y.austin.handler.handler.BaseHandler;
import com.java3y.austin.handler.handler.Handler;
import com.java3y.austin.support.config.SupportThreadPoolConfig;
import com.java3y.austin.support.utils.AccessTokenUtils;
import com.java3y.austin.support.utils.AccountUtils;
import com.java3y.austin.support.utils.LogUtils;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 钉钉消息自定义机器人 消息处理器
 * <p>
 * https://open.dingtalk.com/document/group/custom-robot-access
 *
 * @author 3y
 */
@Slf4j
@Service
public class DingDingWorkNoticeHandler extends BaseHandler implements Handler {


    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private LogUtils logUtils;

    public DingDingWorkNoticeHandler() {
        channelCode = ChannelType.DING_DING_WORK_NOTICE.getCode();
    }

    private static final String DING_DING_RECALL_KEY_PREFIX = "RECALL_";
    private static final String RECALL_BIZ_TYPE = "DingDingWorkNoticeHandler#recall";

    @Override
    public boolean handler(TaskInfo taskInfo) {
        try {
            DingDingWorkNoticeAccount account = accountUtils.getAccountById(taskInfo.getSendAccount(), DingDingWorkNoticeAccount.class);
            OapiMessageCorpconversationAsyncsendV2Request request = assembleParam(account, taskInfo);
            String accessToken = getAccessToken(account, Long.valueOf(taskInfo.getSendAccount()));
            OapiMessageCorpconversationAsyncsendV2Response response = new DefaultDingTalkClient(SendChanelUrlConstant.DING_DING_SEND_URL).execute(request, accessToken);

            // 发送成功后记录TaskId，用于消息撤回(支持当天的)
            if (response.getErrcode() == 0) {
                redisTemplate.opsForList().leftPush(DING_DING_RECALL_KEY_PREFIX + taskInfo.getMessageTemplateId(), String.valueOf(response.getTaskId()));
                redisTemplate.opsForValue().set(DING_DING_RECALL_KEY_PREFIX + taskInfo.getMessageId(), String.valueOf(response.getTaskId()));
                redisTemplate.expire(DING_DING_RECALL_KEY_PREFIX + taskInfo.getMessageTemplateId(), (DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000, TimeUnit.SECONDS);
                redisTemplate.expire(DING_DING_RECALL_KEY_PREFIX + taskInfo.getMessageId(), (DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000, TimeUnit.SECONDS);
                return true;
            }

            // 常见的错误 应当 关联至 AnchorState,由austin后台统一透出失败原因
            log.error("DingDingWorkNoticeHandler#handler fail!result:{},params:{}", JSON.toJSONString(response), JSON.toJSONString(taskInfo));
        } catch (Exception e) {
            log.error("DingDingWorkNoticeHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
        }
        return false;
    }

    /**
     * 拼装参数
     *
     * @param account
     * @param taskInfo
     */
    private OapiMessageCorpconversationAsyncsendV2Request assembleParam(DingDingWorkNoticeAccount account, TaskInfo taskInfo) {
        OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
        DingDingWorkContentModel contentModel = (DingDingWorkContentModel) taskInfo.getContentModel();
        try {
            // 接收者相关
            if (AustinConstant.SEND_ALL.equals(CollUtil.getFirst(taskInfo.getReceiver()))) {
                req.setToAllUser(true);
            } else {
                req.setUseridList(StringUtils.join(taskInfo.getReceiver(), StrUtil.C_COMMA));
            }
            req.setAgentId(Long.parseLong(account.getAgentId()));


            OapiMessageCorpconversationAsyncsendV2Request.Msg message = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            message.setMsgtype(SendMessageType.getDingDingWorkTypeByCode(contentModel.getSendType()));

            // 根据类型设置入参
            if (SendMessageType.TEXT.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.Text textObj = new OapiMessageCorpconversationAsyncsendV2Request.Text();
                textObj.setContent(contentModel.getContent());
                message.setText(textObj);
            }
            if (SendMessageType.IMAGE.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.Image image = new OapiMessageCorpconversationAsyncsendV2Request.Image();
                image.setMediaId(contentModel.getMediaId());
                message.setImage(image);
            }
            if (SendMessageType.VOICE.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.Voice voice = new OapiMessageCorpconversationAsyncsendV2Request.Voice();
                voice.setMediaId(contentModel.getMediaId());
                voice.setDuration(contentModel.getDuration());
                message.setVoice(voice);
            }
            if (SendMessageType.FILE.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.File file = new OapiMessageCorpconversationAsyncsendV2Request.File();
                file.setMediaId(contentModel.getMediaId());
                message.setFile(file);
            }
            if (SendMessageType.LINK.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
                link.setText(contentModel.getContent());
                link.setTitle(contentModel.getTitle());
                link.setPicUrl(contentModel.getMediaId());
                link.setMessageUrl(contentModel.getUrl());
                message.setLink(link);
            }

            if (SendMessageType.MARKDOWN.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
                markdown.setText(contentModel.getContent());
                markdown.setTitle(contentModel.getTitle());
                message.setMarkdown(markdown);

            }
            if (SendMessageType.ACTION_CARD.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.ActionCard actionCard = new OapiMessageCorpconversationAsyncsendV2Request.ActionCard();
                actionCard.setTitle(contentModel.getTitle());
                actionCard.setMarkdown(contentModel.getContent());
                actionCard.setBtnOrientation(contentModel.getBtnOrientation());
                actionCard.setBtnJsonList(JSON.parseArray(contentModel.getBtns(), OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList.class));
                message.setActionCard(actionCard);

            }
            if (SendMessageType.OA.getCode().equals(contentModel.getSendType())) {
                OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
                oa.setMessageUrl(contentModel.getUrl());
                oa.setHead(JSON.parseObject(contentModel.getDingDingOaHead(), OapiMessageCorpconversationAsyncsendV2Request.Head.class));
                oa.setBody(JSON.parseObject(contentModel.getDingDingOaBody(), OapiMessageCorpconversationAsyncsendV2Request.Body.class));
                message.setOa(oa);
            }
            req.setMsg(message);
        } catch (Exception e) {
            log.error("assembleParam fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(taskInfo));
        }
        return req;
    }

    /**
     * 拉取回执
     */
    public void pull(Long accountId) {
        try {
            DingDingWorkNoticeAccount account = accountUtils.getAccountById(accountId.intValue(), DingDingWorkNoticeAccount.class);
            String accessToken = getAccessToken(account, accountId);
            DingTalkClient client = new DefaultDingTalkClient(SendChanelUrlConstant.DING_DING_PULL_URL);
            OapiMessageCorpconversationGetsendresultRequest req = new OapiMessageCorpconversationGetsendresultRequest();
            req.setAgentId(Long.valueOf(account.getAgentId()));
            req.setTaskId(456L);
            OapiMessageCorpconversationGetsendresultResponse rsp = client.execute(req, accessToken);
        } catch (Exception e) {
            log.error("DingDingWorkNoticeHandler#pull fail:{}", Throwables.getStackTraceAsString(e));
        }
    }


    /**
     * 在下发的时候存储了
     * messageTemplate -> taskIdList
     * messageId -> taskIdList
     * <p>
     * 在有效期内的taskIdList，优先撤回messageId，如果未传入messageId，则按照模板id撤回
     *
     * @param recallTaskInfo
     */
    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
            try {
                DingDingWorkNoticeAccount account = accountUtils.getAccountById(recallTaskInfo.getSendAccount(), DingDingWorkNoticeAccount.class);
                String accessToken = getAccessToken(account, Long.valueOf(recallTaskInfo.getSendAccount()));

                // 优先去除messageId，如果未传入messageId，则按照模板id去除
                if (CollUtil.isNotEmpty(recallTaskInfo.getRecallMessageId())) {
                    String taskId = redisTemplate.opsForValue().get(DING_DING_RECALL_KEY_PREFIX + recallTaskInfo.getMessageTemplateId());
                    recallBiz(account, accessToken, taskId);
                } else {
                    while (redisTemplate.opsForList().size(DING_DING_RECALL_KEY_PREFIX + recallTaskInfo.getMessageTemplateId()) > 0) {
                        String taskId = redisTemplate.opsForList().leftPop(DING_DING_RECALL_KEY_PREFIX + recallTaskInfo.getMessageTemplateId());
                        recallBiz(account, accessToken, taskId);
                    }
                }
            } catch (Exception e) {
                log.error("DingDingWorkNoticeHandler#recall fail:{}", Throwables.getStackTraceAsString(e));
            }
        });
    }

    /**
     * 调用 钉钉api 撤回消息
     *
     * @param account
     * @param accessToken
     * @param taskId
     * @throws ApiException
     */
    private void recallBiz(DingDingWorkNoticeAccount account, String accessToken, String taskId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(SendChanelUrlConstant.DING_DING_RECALL_URL);
        OapiMessageCorpconversationRecallRequest req = new OapiMessageCorpconversationRecallRequest();
        req.setAgentId(Long.valueOf(account.getAgentId()));
        req.setMsgTaskId(Long.valueOf(taskId));
        OapiMessageCorpconversationRecallResponse rsp = client.execute(req, accessToken);
        logUtils.print(LogParam.builder().bizType(RECALL_BIZ_TYPE).object(JSON.toJSONString(rsp)).build());
    }

    /**
     * 获取第三方token
     *
     * @param account   钉钉工作消息 账号信息
     * @param accountId 账号ID
     * @return token
     */
    private String getAccessToken(DingDingWorkNoticeAccount account, Long accountId) {
        String accessToken = redisTemplate.opsForValue().get(AccessTokenPrefixConstant.DING_DING_ACCESS_TOKEN_PREFIX + accountId);
        if (StrUtil.isNotBlank(accessToken)) {
            return accessToken;
        }
        accessToken = AccessTokenUtils.getDingDingAccessToken(account);
        if (StrUtil.isNotBlank(accessToken)) {
            redisTemplate.opsForValue().set(AccessTokenPrefixConstant.DING_DING_ACCESS_TOKEN_PREFIX + accountId, accessToken);
        } else {
            log.error("DingDingWorkNoticeHandler#getAccessToken fail accessToken{} accountId{} ", accessToken, accountId);
        }
        return accessToken;
    }

}

