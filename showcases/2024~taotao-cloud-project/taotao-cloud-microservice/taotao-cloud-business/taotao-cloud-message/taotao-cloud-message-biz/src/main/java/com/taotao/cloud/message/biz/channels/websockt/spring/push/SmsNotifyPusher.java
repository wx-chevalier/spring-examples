package com.taotao.cloud.message.biz.channels.websockt.spring.push;

import org.dromara.hutoolcore.util.StrUtil;
import com.hccake.ballcat.common.util.HtmlUtils;
import com.hccake.ballcat.notify.enums.NotifyChannelEnum;
import com.hccake.ballcat.notify.model.domain.NotifyInfo;
import com.hccake.ballcat.system.model.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 短信通知发布
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Component
public class SmsNotifyPusher implements NotifyPusher {

	/**
	 * 当前发布者对应的接收方式
	 * @see NotifyChannelEnum
	 * @return 推送方式
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.SMS.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<SysUser> userList) {
		List<String> phoneList = userList.stream()
			.map(SysUser::getPhone)
			.filter(StrUtil::isNotBlank)
			.toList();
		// 短信文本去除 html 标签
		String content = HtmlUtils.toText(notifyInfo.getContent());
		// TODO 对接短信发送平台
		LogUtils.info("短信推送");
	}

}
