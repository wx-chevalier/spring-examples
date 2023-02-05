
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysQuartzJobLogMapper;
import com.fc.v2.model.auto.TSysProvince;
import com.fc.v2.model.auto.TSysQuartzJobLog;
import com.fc.v2.service.ITSysQuartzJobLogService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 定时任务调度日志Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysQuartzJobLogServiceImpl  extends ServiceImpl<TSysQuartzJobLogMapper, TSysQuartzJobLog> implements ITSysQuartzJobLogService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysQuartzJobLogServiceImpl.class);

    @Autowired
    private TSysQuartzJobLogMapper tSysQuartzJobLogMapper;

    /**
     * 查询定时任务调度日志
     * 
     * @param id 定时任务调度日志ID
     * @return 定时任务调度日志
     */
    @Override
    public TSysQuartzJobLog selectTSysQuartzJobLogById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询定时任务调度日志列表
     * 
     * @param queryWrapper 定时任务调度日志
     * @return 定时任务调度日志
     */
    @Override
    public List<TSysQuartzJobLog> selectTSysQuartzJobLogList(Wrapper<TSysQuartzJobLog> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询定时任务调度日志列表
     *
     * @param tSysQuartzJobLog 定时任务调度日志
     * @return 定时任务调度日志
     */
    @Override
    public List<TSysQuartzJobLog> selectTSysQuartzJobLogList(TSysQuartzJobLog tSysQuartzJobLog) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysQuartzJobLog, true, true);
    	QueryWrapper<TSysQuartzJobLog> queryWrapper = new QueryWrapper<TSysQuartzJobLog>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增定时任务调度日志
     * 
     * @param tSysQuartzJobLog 定时任务调度日志
     * @return 结果
     */
    @Override
    public int insertTSysQuartzJobLog(TSysQuartzJobLog tSysQuartzJobLog)
    {
        tSysQuartzJobLog.setId(IdWorker.getId());
        return this.baseMapper.insert(tSysQuartzJobLog);
    }

    /**
     * 修改定时任务调度日志
     * 
     * @param tSysQuartzJobLog 定时任务调度日志
     * @return 结果
     */
    @Override
    public int updateTSysQuartzJobLog(TSysQuartzJobLog tSysQuartzJobLog)
    {
        return this.baseMapper.updateById(tSysQuartzJobLog);
    }

    /**
     * 删除定时任务调度日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysQuartzJobLogByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除定时任务调度日志信息
     * 
     * @param id 定时任务调度日志ID
     * @return 结果
     */
    @Override
    public int deleteTSysQuartzJobLogById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 清空定时任务调度日志信息
     */
    @Override
    public void cleanQuartzJobLog() {
        tSysQuartzJobLogMapper.cleanQuartzJobLog();
    }
}
