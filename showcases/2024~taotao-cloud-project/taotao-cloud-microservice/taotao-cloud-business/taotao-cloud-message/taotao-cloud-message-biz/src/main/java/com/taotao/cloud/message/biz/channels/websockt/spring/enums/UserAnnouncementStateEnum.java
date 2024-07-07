package com.taotao.cloud.message.biz.channels.websockt.spring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户公告状态
 *
 * @author Hccake 2021/1/19
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum UserAnnouncementStateEnum {

	/**
	 * 未读
	 */
	UNREAD(0),
	/**
	 * 已读
	 */
	READ(1);

	private final int value;

}
