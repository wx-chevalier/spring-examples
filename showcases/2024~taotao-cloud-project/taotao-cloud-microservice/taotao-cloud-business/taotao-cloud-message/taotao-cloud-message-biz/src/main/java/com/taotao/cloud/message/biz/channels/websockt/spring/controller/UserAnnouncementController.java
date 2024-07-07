package com.taotao.cloud.message.biz.channels.websockt.spring.controller;

import com.taotao.cloud.security.springsecurity.utils.SecurityUtils;
import com.taotao.cloud.data.mybatis.pagehelper.PageParam;
import com.taotao.cloud.message.biz.ballcat.notify.model.qo.UserAnnouncementQO;
import com.taotao.cloud.message.biz.ballcat.notify.model.vo.UserAnnouncementPageVO;
import com.taotao.cloud.message.biz.ballcat.notify.service.UserAnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户公告表
 *
 * @author hccake 2020-12-25 08:04:53
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/user-announcement")
@Tag(name = "用户公告表管理")
public class UserAnnouncementController {

	private final UserAnnouncementService userAnnouncementService;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param userAnnouncementQO 用户公告表查询对象
	 * @return R 通用返回体
	 */
	@GetMapping("/page")
	@PreAuthorize("@per.hasPermission('notify:userannouncement:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public R<PageResult<UserAnnouncementPageVO>> getUserAnnouncementPage(@Validated PageParam pageParam,
																		 UserAnnouncementQO userAnnouncementQO) {
		return R.ok(userAnnouncementService.queryPage(pageParam, userAnnouncementQO));
	}

	@PatchMapping("/read/{announcementId}")
	@PreAuthorize("@per.hasPermission('notify:userannouncement:read')")
	@Operation(summary = "用户公告已读上报", description = "用户公告已读上报")
	public R<Void> readAnnouncement(@PathVariable("announcementId") Long announcementId) {
		Integer userId = SecurityUtils.getUser().getUserId();
		userAnnouncementService.readAnnouncement(userId, announcementId);
		return R.ok();
	}

}
