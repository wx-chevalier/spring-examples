package com.taotao.cloud.message.biz.channels.websockt.netty;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

	@Override
	public void sendInfo(Chat chat) {
		QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
		List<Chat> chats = this.baseMapper.selectList(queryWrapper.lambda()
			.eq(Chat::getTargetUserId, chat.getTargetUserId()).or().eq(Chat::getUserId, chat.getTargetUserId()).or().eq(Chat::getTargetUserId, chat.getUserId()).or().eq(Chat::getUserId, chat.getUserId()));

		NettyWebSocket.sendInfo(chat, chats);
	}
}
