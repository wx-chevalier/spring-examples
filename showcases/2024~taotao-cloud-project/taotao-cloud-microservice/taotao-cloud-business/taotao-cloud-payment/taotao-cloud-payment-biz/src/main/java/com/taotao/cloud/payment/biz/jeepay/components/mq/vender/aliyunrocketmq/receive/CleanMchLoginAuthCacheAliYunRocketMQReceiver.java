/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.payment.biz.jeepay.components.mq.vender.aliyunrocketmq.receive;

import com.jeequan.jeepay.components.mq.constant.MQVenderCS;
import com.jeequan.jeepay.components.mq.executor.MqThreadExecutor;
import com.jeequan.jeepay.components.mq.model.CleanMchLoginAuthCacheMQ;
import com.jeequan.jeepay.components.mq.vender.aliyunrocketmq.AbstractAliYunRocketMQReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * AliYunRocketMQ消息接收器：仅在vender=AliYunRocketMQ时 && 项目实现IMQReceiver接口时 进行实例化
 * 业务：  清除商户登录信息
 */
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ALIYUN_ROCKET_MQ)
@ConditionalOnBean(CleanMchLoginAuthCacheMQ.IMQReceiver.class)
@Slf4j
public class CleanMchLoginAuthCacheAliYunRocketMQReceiver extends AbstractAliYunRocketMQReceiver {

    private static final String CONSUMER_NAME = "清除商户登录消息";

    @Autowired
    private CleanMchLoginAuthCacheMQ.IMQReceiver mqReceiver;

    /**
     * 接收 【 queue 】 类型的消息
     **/
    @Override
    @Async(MqThreadExecutor.EXECUTOR_PAYORDER_MCH_NOTIFY)
    public void receiveMsg(String msg) {
        mqReceiver.receive(CleanMchLoginAuthCacheMQ.parse(msg));
    }

    /**
     * 获取topic名称
     *
     * @return
     */
    @Override
    public String getMQName() {
        return CleanMchLoginAuthCacheMQ.MQ_NAME;
    }

    /**
     * 获取业务名称
     *
     * @return
     */
    @Override
    public String getConsumerName() {
        return CONSUMER_NAME;
    }

}
