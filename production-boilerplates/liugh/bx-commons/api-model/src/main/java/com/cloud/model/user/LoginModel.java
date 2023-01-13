package com.cloud.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/12.
 */
@Data
@ApiModel("登录对象")
public class LoginModel implements Serializable {

    private static final long serialVersionUID = 6111979315815067628L;


    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "客户端id",required =true)
    private String clientId;
    @ApiModelProperty(value = "客户端密码",required =true)
    private String clientSecret;
    @ApiModelProperty(value = "范围")
    private String scope;
    @ApiModelProperty(value = "授权类型")
    private String grantType;
    @ApiModelProperty(hidden=true)
    private String refreshToken;

}

