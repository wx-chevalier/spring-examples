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

package com.taotao.cloud.wechat.biz.weixin.constant;

/**
 * 全局返回码 小程序用6开头，例60001
 *
 * @author www.joolun.com 2019年7月25日
 */
public enum MyReturnCode {
    ERR_60000(60000, "系统错误，请稍候再试") {}, // 其它错误
    ERR_60001(60001, "登录超时，请重新登录") {},
    ERR_60002(60002, "session不能为空") {},

    ERR_70001(70001, "该状态订单不允许操作") {},
    ERR_70002(70002, "请选择付款方式") {},
    ERR_70003(70003, "没有符合下单条件的规格商品，商品已下架或库存不足") {},
    ERR_70004(70004, "只有未支付的详单能发起支付") {},
    ERR_70005(70005, "无效订单") {},

    ERR_80004(80004, "该商品已删除") {},
    ;
    ;

    MyReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MyReturnCode{" + "code='" + code + '\'' + "msg='" + msg + '\'' + '}';
    }
}
