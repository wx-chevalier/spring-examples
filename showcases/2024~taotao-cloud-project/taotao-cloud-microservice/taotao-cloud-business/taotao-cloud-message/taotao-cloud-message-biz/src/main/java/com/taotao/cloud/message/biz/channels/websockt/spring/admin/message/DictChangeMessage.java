package com.taotao.cloud.message.biz.channels.websockt.spring.admin.message;

import com.taotao.cloud.message.biz.ballcat.common.websocket.message.JsonWebSocketMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典修改消息
 *
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Getter
@Setter
public class DictChangeMessage extends JsonWebSocketMessage {

	public DictChangeMessage() {
		super("dict-change");
	}

	/**
	 * 改动的字典标识
	 */
	private String dictCode;

}
