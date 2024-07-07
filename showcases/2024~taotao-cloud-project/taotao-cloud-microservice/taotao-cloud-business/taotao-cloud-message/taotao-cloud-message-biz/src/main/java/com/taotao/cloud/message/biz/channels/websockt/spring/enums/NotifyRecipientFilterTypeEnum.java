package com.taotao.cloud.message.biz.channels.websockt.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知接收者筛选类型
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum NotifyRecipientFilterTypeEnum {

	// 全部
	ALL(1),
	// 指定用户
	SPECIFY_ROLE(2),
	// 指定组织
	SPECIFY_ORGANIZATION(3),
	// 指定用户类型
	SPECIFY_USER_TYPE(4),
	// 指定用户
	SPECIFY_USER(5),;

	private final int value;

}
