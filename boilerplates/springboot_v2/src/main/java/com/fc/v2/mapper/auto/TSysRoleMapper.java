
package com.fc.v2.mapper.auto;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.auto.TSysRole;
import org.apache.ibatis.annotations.Select;

/**
 * 角色Mapper接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface TSysRoleMapper extends BaseMapper<TSysRole> {
	/**
	 * 根据用户id查询角色
	 * 
	 * @param userid
	 * @return
	 */
	@Select("select r.id,r.name  from t_sys_role r LEFT JOIN t_sys_role_user ru ON r.id = ru.sys_role_id where ru.sys_user_id=#{userid}")
	public List<TSysRole> queryUserRole(Long userid);
}