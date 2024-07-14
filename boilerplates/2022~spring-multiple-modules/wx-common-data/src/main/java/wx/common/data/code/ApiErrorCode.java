package wx.common.data.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApiErrorCode {

  // 通用
  RESOURCE_EXIST("资源已存在"),
  NOT_FOUND("资源未发现"),
  PASS_NOT_MATCH("密码错误"),
  SEND_RATE_FAST("发送频率过于频繁"),

  // 材料相关
  MATERIAL_NOT_ALLOW_DELETE("不允许删除材料"),

  // 文件相关
  EXCEL_PARSE_ERROR("Excel 文件解析错误");

  @Getter String desc;
}
