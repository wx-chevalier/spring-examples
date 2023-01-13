package com.cloud.utils;

import com.cloud.constants.CodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liugh 66864662@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统一返回json对象")
public class ResultHelper<T> implements Serializable {

    @ApiModelProperty(value = "统一返回数据")
    private T data;
    @ApiModelProperty(value = "统一返回状态码")
    private String respCode;
    @ApiModelProperty(value = "统一返回说明信息")
    private String respMsg;

    public static <T> ResultHelper<T> succeed2Msg(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResultHelper<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResultHelper<T> succeed(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResultHelper<T> succeedWith(T datas, String code, String msg) {
        return new ResultHelper<>(datas, code, msg);
    }

    public static <T> ResultHelper<T> failed2Msg(String msg) {
        return failedWith(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> ResultHelper<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.ERROR.getCode(), msg);
    }


    public static <T> ResultHelper<T> failedWith(T datas, String code, String msg) {
        return new ResultHelper<>(datas, code, msg);
    }

}
