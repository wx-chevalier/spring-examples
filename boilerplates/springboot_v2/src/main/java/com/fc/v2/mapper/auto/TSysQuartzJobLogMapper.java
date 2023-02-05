
package com.fc.v2.mapper.auto;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.auto.TSysQuartzJobLog;

/**
 * 定时任务调度日志Mapper接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface TSysQuartzJobLogMapper extends BaseMapper<TSysQuartzJobLog> {

    /**
     * 清空定时任务调度日志信息
     */
    public void cleanQuartzJobLog();
}
