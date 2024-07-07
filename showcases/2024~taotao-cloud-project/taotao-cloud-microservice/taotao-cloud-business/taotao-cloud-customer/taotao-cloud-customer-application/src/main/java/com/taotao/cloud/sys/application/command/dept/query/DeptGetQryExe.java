

package com.taotao.cloud.sys.application.command.dept.query;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.convertor.DeptConvertor;
import org.laokou.admin.domain.gateway.DeptGateway;
import org.laokou.admin.dto.dept.DeptGetQry;
import org.laokou.admin.dto.dept.clientobject.DeptCO;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

import static org.laokou.common.i18n.common.DatasourceConstants.TENANT;

/**
 * 查看部门执行器.
 *
 * 
 */
@Component
@RequiredArgsConstructor
public class DeptGetQryExe {

	private final DeptGateway deptGateway;

	private final DeptConvertor deptConvertor;

	/**
	 * 执行查看部门.
	 * @param qry 查看部门参数
	 * @return 部门
	 */
	@DS(TENANT)
	public Result<DeptCO> execute(DeptGetQry qry) {
		return Result.of(deptConvertor.convertClientObject(deptGateway.getById(qry.getId())));
	}

}
