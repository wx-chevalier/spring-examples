/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.sys.biz.job.schedule.record;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.taotao.cloud.job.biz.schedule.entity.ScheduledJob;
import com.taotao.cloud.job.biz.schedule.service.ScheduledJobService;
import com.taotao.cloud.job.schedule.model.ScheduledTask;
import com.taotao.cloud.job.schedule.task.ScheduleTaskRecord;
import java.util.List;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduleTaskRecordImpl implements ScheduleTaskRecord {


	@Autowired
	private ScheduledJobService scheduledJobService;

	@Override
	public ScheduledTask selectTaskById(String id) {
		ScheduledJob scheduledJob = scheduledJobService.getById(id);
		return BeanUtil.copyProperties(scheduledJob, ScheduledTask.class);
	}

	@Override
	public void update(ScheduledTask task) {
		scheduledJobService.updateById(BeanUtil.copyProperties(task, ScheduledJob.class));
	}

	@Override
	public List<ScheduledTask> taskList() {
		List<ScheduledJob> scheduledJobs = scheduledJobService.list(new LambdaQueryWrapper<>());
		return scheduledJobs.stream()
			.map(task -> {
				return BeanUtil.copyProperties(task, ScheduledTask.class);
			})
			.toList();
	}
}
