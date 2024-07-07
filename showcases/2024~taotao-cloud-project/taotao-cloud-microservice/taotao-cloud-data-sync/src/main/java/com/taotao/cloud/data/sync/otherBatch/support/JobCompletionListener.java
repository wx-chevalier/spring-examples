/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.data.sync.otherBatch.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * JobCompletionListener
 * </p>
 *
 * @author livk
 */
@Slf4j
@Component
public class JobCompletionListener implements JobExecutionListener {

	// 用于批处理开始前执行
	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("任务id={}开始于{}", jobExecution.getJobId(), jobExecution.getStartTime());
	}

	// 用于批处理开始后执行
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("任务id={}结束于{}", jobExecution.getJobId(), jobExecution.getEndTime());
		}
		else {
			log.info("任务id={}执行异常状态={}", jobExecution.getJobId(), jobExecution.getStatus());
		}
	}

}
