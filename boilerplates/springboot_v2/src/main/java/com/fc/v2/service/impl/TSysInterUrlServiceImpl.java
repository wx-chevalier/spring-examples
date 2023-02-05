
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysInterUrlMapper;
import com.fc.v2.model.auto.TSysInterUrl;
import com.fc.v2.service.ITSysInterUrlService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 拦截urlService业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysInterUrlServiceImpl  extends ServiceImpl<TSysInterUrlMapper, TSysInterUrl> implements ITSysInterUrlService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysInterUrlServiceImpl.class);

    @Autowired
    private TSysInterUrlMapper tSysInterUrlMapper;

    /**
     * 查询拦截url
     * 
     * @param id 拦截urlID
     * @return 拦截url
     */
    @Override
    public TSysInterUrl selectTSysInterUrlById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询拦截url列表
     * 
     * @param queryWrapper 拦截url
     * @return 拦截url
     */
    @Override
    public List<TSysInterUrl> selectTSysInterUrlList(Wrapper<TSysInterUrl> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询拦截url列表
     *
     * @param tSysInterUrl 拦截url
     * @return 拦截url
     */
    @Override
    public List<TSysInterUrl> selectTSysInterUrlList(TSysInterUrl tSysInterUrl) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysInterUrl, true, true);
    	QueryWrapper<TSysInterUrl> queryWrapper = new QueryWrapper<TSysInterUrl>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增拦截url
     * 
     * @param tSysInterUrl 拦截url
     * @return 结果
     */
    @Override
    public int insertTSysInterUrl(TSysInterUrl tSysInterUrl)
    {
        return this.baseMapper.insert(tSysInterUrl);
    }

    /**
     * 修改拦截url
     * 
     * @param tSysInterUrl 拦截url
     * @return 结果
     */
    @Override
    public int updateTSysInterUrl(TSysInterUrl tSysInterUrl)
    {
        return this.baseMapper.updateById(tSysInterUrl);
    }

    /**
     * 删除拦截url对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysInterUrlByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除拦截url信息
     * 
     * @param id 拦截urlID
     * @return 结果
     */
    @Override
    public int deleteTSysInterUrlById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查name
     *
     * @param sysInterUrl
     * @return
     */
    @Override
    public int checkNameUnique(TSysInterUrl sysInterUrl) {
        QueryWrapper<TSysInterUrl> queryWrapper = new QueryWrapper<TSysInterUrl>();
        queryWrapper.eq("inter_name", sysInterUrl.getInterName());
        return this.baseMapper.selectList(queryWrapper).size();
    }
}
