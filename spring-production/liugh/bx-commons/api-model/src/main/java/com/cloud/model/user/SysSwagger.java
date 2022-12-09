package com.cloud.model.user;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * swagger信息表
 * </p>
 *
 * @author liugh123
 * @since 2019-06-17
 */
@TableName("sys_swagger")
@ApiModel(value="Swagger对象")
@Data
public class SysSwagger {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @TableId(value = "swagger_id", type = IdType.AUTO)
    private Integer swaggerId;

    @ApiModelProperty(value = "swagger地址")
    private String url;

    @ApiModelProperty(value = "模块端口号")
    private Integer port;

    @ApiModelProperty(value = "模块名")
    private String name;

}
