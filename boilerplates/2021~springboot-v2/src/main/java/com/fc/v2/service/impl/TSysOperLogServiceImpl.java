
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysOperLogMapper;
import com.fc.v2.model.auto.TSysOperLog;
import com.fc.v2.service.ITSysOperLogService;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 日志记录Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysOperLogServiceImpl  extends ServiceImpl<TSysOperLogMapper, TSysOperLog> implements ITSysOperLogService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysOperLogServiceImpl.class);

    @Autowired
    private TSysOperLogMapper tSysOperLogMapper;

    /**
     * 查询日志记录
     * 
     * @param id 日志记录ID
     * @return 日志记录
     */
    @Override
    public TSysOperLog selectTSysOperLogById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询日志记录列表
     * 
     * @param queryWrapper 日志记录
     * @return 日志记录
     */
    @Override
    public List<TSysOperLog> selectTSysOperLogList(Wrapper<TSysOperLog> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询日志记录列表
     * 
     * @param tSysOperLog 日志记录
     * @return 日志记录
     */
    @Override
    public List<TSysOperLog> selectTSysOperLogList(TSysOperLog tSysOperLog) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysOperLog, true, true);
    	QueryWrapper<TSysOperLog> queryWrapper = new QueryWrapper<TSysOperLog>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增日志记录
     * 
     * @param tSysOperLog 日志记录
     * @return 结果
     */
    @Override
    public int insertTSysOperLog(TSysOperLog tSysOperLog)
    {
        return this.baseMapper.insert(tSysOperLog);
    }

    /**
     * 修改日志记录
     * 
     * @param tSysOperLog 日志记录
     * @return 结果
     */
    @Override
    public int updateTSysOperLog(TSysOperLog tSysOperLog)
    {
        return this.baseMapper.updateById(tSysOperLog);
    }

    /**
     * 删除日志记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysOperLogByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除日志记录信息
     * 
     * @param id 日志记录ID
     * @return 结果
     */
    @Override
    public int deleteTSysOperLogById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

	/**
	 * 获取最新10条日志
	 *
	 * @return
	 */
    @Override
    public List<TSysOperLog> getNEW() {
        QueryWrapper<TSysOperLog> queryWrapper = new QueryWrapper<TSysOperLog>();
        queryWrapper.orderByDesc("id");
        PageHelper.startPage(1, 10);
        return this.selectTSysOperLogList(queryWrapper);
    }

    /**
     * 清空日志
     *
     * @return
     */
    @Override
    public void cleanTSysOperLog() {
        tSysOperLogMapper.cleanTSysOperLog();
    }
}
