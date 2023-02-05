
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysFileMapper;
import com.fc.v2.model.auto.TSysFile;
import com.fc.v2.service.ITSysFileService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件信息Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysFileServiceImpl extends ServiceImpl<TSysFileMapper, TSysFile> implements ITSysFileService {
	
	private static final Logger logger = LoggerFactory.getLogger(TSysFileServiceImpl.class);

	@Autowired
	private TSysFileMapper tSysFileMapper;

	/**
	 * 查询文件信息
	 * 
	 * @param id 文件信息ID
	 * @return 文件信息
	 */
	@Override
	public TSysFile selectTSysFileById(Long id) {
		return this.baseMapper.selectById(id);
	}

	/**
	 * 查询文件信息列表
	 * 
	 * @param queryWrapper 文件信息
	 * @return 文件信息
	 */
	@Override
	public List<TSysFile> selectTSysFileList(Wrapper<TSysFile> queryWrapper) {
		return this.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 查询文件信息列表
	 *
	 * @param tSysFile 文件信息
	 * @return 文件信息
	 */
	@Override
	public List<TSysFile> selectTSysFileList(TSysFile tSysFile) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysFile, true, true);
    	QueryWrapper<TSysFile> queryWrapper = new QueryWrapper<TSysFile>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 新增文件信息
	 * 
	 * @param tSysFile 文件信息
	 * @return 结果
	 */
	@Override
	public int insertTSysFile(TSysFile tSysFile) {
		return this.baseMapper.insert(tSysFile);
	}

	/**
	 * 修改文件信息
	 * 
	 * @param tSysFile 文件信息
	 * @return 结果
	 */
	@Override
	public int updateTSysFile(TSysFile tSysFile) {
		return this.baseMapper.updateById(tSysFile);
	}

	/**
	 * 删除文件信息对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteTSysFileByIds(String ids) {
		return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
	}

	/**
	 * 删除文件信息信息
	 * 
	 * @param id 文件信息ID
	 * @return 结果
	 */
	@Override
	public int deleteTSysFileById(Long id) {
		return this.baseMapper.deleteById(id);
	}

	/**
	 * 检查name
	 *
	 * @param tsysFile
	 * @return
	 */
	@Override
	public int checkNameUnique(TSysFile tsysFile) {
		QueryWrapper<TSysFile> queryWrapper = new QueryWrapper<TSysFile>();
		queryWrapper.eq("file_name", tsysFile.getFileName());
		return this.baseMapper.selectList(queryWrapper).size();
	}
}
