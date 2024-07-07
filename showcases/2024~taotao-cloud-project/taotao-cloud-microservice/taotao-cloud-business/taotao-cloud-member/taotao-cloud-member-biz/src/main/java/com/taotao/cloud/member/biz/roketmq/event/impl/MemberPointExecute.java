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

package com.taotao.cloud.member.biz.roketmq.event.impl;

import com.taotao.cloud.member.api.enums.PointTypeEnum;
import com.taotao.cloud.member.biz.model.entity.Member;
import com.taotao.cloud.member.biz.roketmq.event.MemberRegisterEvent;
import com.taotao.cloud.member.biz.service.business.IMemberService;
import com.taotao.cloud.order.api.feign.IFeignOrderApi;
import com.taotao.cloud.sys.api.enums.SettingCategoryEnum;
import com.taotao.cloud.sys.api.feign.IFeignSettingApi;
import com.taotao.cloud.sys.api.model.vo.setting.PointSettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 会员积分 */
@Service
public class MemberPointExecute implements MemberRegisterEvent {

    /** 配置 */
    @Autowired
    private IFeignSettingApi settingApi;
    /** 会员 */
    @Autowired
    private IMemberService memberService;
    /** 订单 */
    @Autowired
    private IFeignOrderApi orderApi;

    /**
     * 会员注册赠送积分
     *
     * @param member 会员
     */
    @Override
    public void memberRegister(Member member) {
        // 获取积分设置
        PointSettingVO pointSetting = getPointSetting();
        // 赠送会员积分
        memberService.updateMemberPoint(
                pointSetting.getRegister().longValue(),
                PointTypeEnum.INCREASE.name(),
                member.getId(),
                "会员注册，赠送积分" + pointSetting.getRegister() + "分");
    }

    /**
     * 获取积分设置
     *
     * @return 积分设置
     */
    private PointSettingVO getPointSetting() {
        PointSettingVO setting = settingApi.getPointSetting(SettingCategoryEnum.POINT_SETTING.name());
        return setting;
    }
}
