package com.fc.v2.common.quartz;

import com.fc.v2.common.quartz.utils.JobInvokeUtil;
import com.fc.v2.model.auto.TSysQuartzJob;

import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author  jan  橙寂
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, TSysQuartzJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }


}
