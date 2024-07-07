
package com.taotao.cloud.order.domain.order.entity;

import static lombok.AccessLevel.PRIVATE;

import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.ddd.domain.model.AggregateRoot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Schema(name = "Dept", description = "部门")
public class OrderEntity extends AggregateRoot<Long> {

	@Schema(name = "name", description = "部门名称")
	private String name;

	@Schema(name = "pid", description = "部门父节点ID")
	private Long pid;

	@Schema(name = "path", description = "部门节点")
	private String path;

	@Schema(name = "sort", description = "部门排序")
	private Integer sort;

	public void checkName(long count) {
		if (count > 0) {
			throw new BusinessException("部门名称已存在，请重新填写");
		}
	}

	public void checkIdAndPid() {
		if (id.equals(pid)) {
			throw new BusinessException("上级部门不能为当前部门");
		}
	}

}
