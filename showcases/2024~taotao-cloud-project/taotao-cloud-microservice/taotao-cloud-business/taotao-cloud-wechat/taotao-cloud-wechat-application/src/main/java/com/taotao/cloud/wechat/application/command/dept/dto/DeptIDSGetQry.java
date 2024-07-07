

package com.taotao.cloud.sys.application.command.dept.dto;

import com.taotao.cloud.ddd.application.model.CommonCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DeptIDSGetQry", description = "查看部门IDS命令请求")
public class DeptIDSGetQry extends CommonCommand {

	@Schema(name = "roleId", description = "角色ID")
	private Long roleId;

}
