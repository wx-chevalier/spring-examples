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

package com.taotao.cloud.wechat.biz.niefy.modules.wx.manage;

import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.form.WxMsgReplyForm;
import com.github.niefy.modules.wx.service.MsgReplyService;
import com.github.niefy.modules.wx.service.WxMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信消息
 *
 * @author niefy
 * @since 2020-05-14 17:28:34
 */
@RestController
@RequestMapping("/manage/wxMsg")
@Api(tags = {"公众号消息记录-管理后台"})
public class WxMsgManageController {
    @Autowired
    private WxMsgService wxMsgService;

    @Autowired
    private MsgReplyService msgReplyService;

    /** 列表 */
    @GetMapping("/list")
    @RequiresPermissions("wx:wxmsg:list")
    @ApiOperation(value = "列表")
    public R list(@CookieValue String appid, @RequestParam Map<String, Object> params) {
        params.put("appid", appid);
        PageUtils page = wxMsgService.queryPage(params);

        return R.ok().put("page", page);
    }

    /** 信息 */
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:wxmsg:info")
    @ApiOperation(value = "详情")
    public R info(@CookieValue String appid, @PathVariable("id") Long id) {
        WxMsg wxMsg = wxMsgService.getById(id);

        return R.ok().put("wxMsg", wxMsg);
    }

    /** 回复 */
    @PostMapping("/reply")
    @RequiresPermissions("wx:wxmsg:save")
    @ApiOperation(value = "回复")
    public R reply(@CookieValue String appid, @RequestBody WxMsgReplyForm form) {

        msgReplyService.reply(form.getOpenid(), form.getReplyType(), form.getReplyContent());
        return R.ok();
    }

    /** 删除 */
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxmsg:delete")
    @ApiOperation(value = "删除")
    public R delete(@CookieValue String appid, @RequestBody Long[] ids) {
        wxMsgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
