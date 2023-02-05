package com.cloud.constants;

/**
 * @author liugh 66864662@qq.com
 * @date 2019/6/1
 */
public enum CodeEnum {
    SUCCESS("10000","操作成功"),
    ERROR("20000","操作失败");

    private String respCode;

    private String respMsg;
    CodeEnum(String respCode, String respMsg){
        this.respCode = respCode;
        this.respMsg=respMsg;
    }

    public String getCode() {
        return respCode;
    }
    public String getMsg() {
        return respMsg;
    }
}
