
package com.fc.v2.mapper.auto;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.auto.TSysOperLog;

/**
 * 日志记录Mapper接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface TSysOperLogMapper extends BaseMapper<TSysOperLog> {

    /**
     * 清空日志
     *
     * @return
     */
    public void cleanTSysOperLog();
}
