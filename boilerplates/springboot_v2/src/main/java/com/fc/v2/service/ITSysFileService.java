
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysFile;

/**
 * 文件信息Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysFileService extends IService<TSysFile> {
	/**
	 * 查询文件信息
	 * 
	 * @param id 文件信息ID
	 * @return 文件信息
	 */
	public TSysFile selectTSysFileById(Long id);

	/**
	 * 查询文件信息列表
	 * 
	 * @param queryWrapper 文件信息
	 * @return 文件信息集合
	 */
	public List<TSysFile> selectTSysFileList(Wrapper<TSysFile> queryWrapper);

	/**
	 * 查询文件信息列表
	 *
	 * @param tSysFile 文件信息
	 * @return 文件信息集合
	 */
	public List<TSysFile> selectTSysFileList(TSysFile tSysFile);

	/**
	 * 新增文件信息
	 * 
	 * @param tSysFile 文件信息
	 * @return 结果
	 */
	public int insertTSysFile(TSysFile tSysFile);

	/**
	 * 修改文件信息
	 * 
	 * @param tSysFile 文件信息
	 * @return 结果
	 */
	public int updateTSysFile(TSysFile tSysFile);

	/**
	 * 批量删除文件信息
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysFileByIds(String ids);

	/**
	 * 删除文件信息信息
	 * 
	 * @param id 文件信息ID
	 * @return 结果
	 */
	public int deleteTSysFileById(Long id);

	/**
	 * 检查name
	 *
	 * @param tsysFile
	 * @return
	 */
	int checkNameUnique(TSysFile tsysFile);
}
