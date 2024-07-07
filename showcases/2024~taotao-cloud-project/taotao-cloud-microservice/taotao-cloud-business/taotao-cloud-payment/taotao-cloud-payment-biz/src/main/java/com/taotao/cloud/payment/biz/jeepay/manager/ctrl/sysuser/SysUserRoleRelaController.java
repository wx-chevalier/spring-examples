/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.payment.biz.jeepay.manager.ctrl.sysuser;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeequan.jeepay.core.aop.MethodLog;
import com.jeequan.jeepay.core.entity.SysUserRoleRela;
import com.jeequan.jeepay.core.model.ApiPageRes;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.mgr.service.AuthService;
import com.jeequan.jeepay.service.impl.SysUserRoleRelaService;
import com.jeequan.jeepay.service.impl.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/*
* 用户角色关联关系
*
* @author terrfly
* @site https://www.jeequan.com
* @since 2021/6/8 17:13
*/
@Api(tags = "系统管理（用户-角色-权限关联信息）")
@RestController
@RequestMapping("api/sysUserRoleRelas")
public class SysUserRoleRelaController extends CommonCtrl {

	@Autowired private SysUserRoleRelaService sysUserRoleRelaService;
	@Autowired private SysUserService sysUserService;
	@Autowired private AuthService authService;

	/** list */
	@ApiOperation("关联关系--用户-角色关联信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "iToken", value = "用户身份凭证", required = true, paramType = "header"),
			@ApiImplicitParam(name = "pageNumber", value = "分页页码", dataType = "int", defaultValue = "1"),
			@ApiImplicitParam(name = "pageSize", value = "分页条数（-1时查全部数据）", dataType = "int", defaultValue = "20"),
			@ApiImplicitParam(name = "userId", value = "用户ID")
	})
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_UPD_ROLE' )")
	@RequestMapping(value="", method = RequestMethod.GET)
	public ApiPageRes<SysUserRoleRela> list() {

		SysUserRoleRela queryObject = getObject(SysUserRoleRela.class);

		LambdaQueryWrapper<SysUserRoleRela> condition = SysUserRoleRela.gw();

		if(queryObject.getUserId() != null){
			condition.eq(SysUserRoleRela::getUserId, queryObject.getUserId());
		}

		IPage<SysUserRoleRela> pages = sysUserRoleRelaService.page(getIPage(true), condition);

		return ApiPageRes.pages(pages);
	}

	/** 重置用户角色关联信息 */
	@ApiOperation("更改用户角色信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "iToken", value = "用户身份凭证", required = true, paramType = "header"),
			@ApiImplicitParam(name = "sysUserId", value = "用户ID", required = true),
			@ApiImplicitParam(name = "roleIdListStr", value = "角色信息，eg：[str1,str2]，字符串列表转成json字符串", required = true)
	})
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_UPD_ROLE' )")
	@RequestMapping(value="relas/{sysUserId}", method = RequestMethod.POST)
	@MethodLog(remark = "更改用户角色信息")
	public ApiRes relas(@PathVariable("sysUserId") Long sysUserId) {

		List<String> roleIdList = JSONArray.parseArray(getValStringRequired("roleIdListStr"), String.class);

		sysUserService.saveUserRole(sysUserId, roleIdList);

		authService.refAuthentication(Arrays.asList(sysUserId));

		return ApiRes.ok();
	}


}
