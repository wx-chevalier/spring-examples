package com.taotao.cloud.message.biz.austin.cron.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.java3y.austin.common.constant.AccessTokenPrefixConstant;
import com.java3y.austin.common.constant.CommonConstant;
import com.java3y.austin.common.dto.account.DingDingWorkNoticeAccount;
import com.java3y.austin.common.enums.ChannelType;
import com.java3y.austin.support.config.SupportThreadPoolConfig;
import com.java3y.austin.support.dao.ChannelAccountDao;
import com.java3y.austin.support.domain.ChannelAccount;
import com.java3y.austin.support.utils.AccessTokenUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 刷新钉钉的access_token
 * <p>
 * https://open.dingtalk.com/document/orgapp-server/obtain-orgapp-token
 *
 * @author 3y
 */
@Service
@Slf4j
public class RefreshDingDingAccessTokenHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChannelAccountDao channelAccountDao;


    /**
     * 每小时请求一次接口刷新（以防失效)
     */
    @XxlJob("refreshAccessTokenJob")
    public void execute() {
        log.info("refreshAccessTokenJob#execute!");
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
            List<ChannelAccount> accountList = channelAccountDao.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.DING_DING_WORK_NOTICE.getCode());
            for (ChannelAccount channelAccount : accountList) {
                DingDingWorkNoticeAccount account = JSON.parseObject(channelAccount.getAccountConfig(), DingDingWorkNoticeAccount.class);
                String accessToken = AccessTokenUtils.getDingDingAccessToken(account);
                if (StrUtil.isNotBlank(accessToken)) {
                    redisTemplate.opsForValue().set(AccessTokenPrefixConstant.DING_DING_ACCESS_TOKEN_PREFIX + channelAccount.getId(), accessToken);
                }
            }
        });
    }

}
