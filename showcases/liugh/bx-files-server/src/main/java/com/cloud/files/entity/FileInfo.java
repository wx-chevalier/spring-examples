package com.cloud.files.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author liugh
 * @since 2019-06-13
 */
@TableName("tb_file_info")
@ApiModel(value="FileInfo对象", description="文件信息表")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileInfo extends Model<FileInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "服务名称")
    @TableField("application_name")
    private String applicationName;

    @ApiModelProperty(value = "文件类型")
    @TableField("content_type")
    private String contentType;

    @ApiModelProperty(value = "文件大小")
    private Long size;

    @ApiModelProperty(value = "缩略图")
    @TableField("thumb_url")
    private String thumbUrl;

    @ApiModelProperty(value = "文件网络url")
    private String url;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Long createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
