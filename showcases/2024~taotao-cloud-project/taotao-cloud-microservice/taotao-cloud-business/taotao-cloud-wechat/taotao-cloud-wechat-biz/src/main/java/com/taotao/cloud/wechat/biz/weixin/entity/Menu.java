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

package com.taotao.cloud.wechat.biz.weixin.entity;

import org.dromara.hutooljson.JSONUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import me.chanjar.weixin.common.bean.menu.WxMenuRule;

/**
 * 自定义菜单模型
 *
 * @author www.joolun.com
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -7083914585539687746L;

    private List<MenuButton> button = new ArrayList<>();

    private WxMenuRule matchrule;

    /** 反序列化 */
    public static Menu fromJson(String json) {
        return JSONUtil.parseObj(json).toBean(Menu.class);
    }

    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
