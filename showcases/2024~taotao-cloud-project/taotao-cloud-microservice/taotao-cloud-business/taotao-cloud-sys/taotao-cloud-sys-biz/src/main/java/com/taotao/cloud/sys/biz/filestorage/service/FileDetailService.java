package com.taotao.cloud.sys.biz.filestorage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cloud.file.biz.filestorage.mapper.FileDetailMapper;
import com.taotao.cloud.file.biz.filestorage.model.FileDetail;
import lombok.SneakyThrows;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Service;

/**
 * 用来将文件上传记录保存到数据库，这里使用了 MyBatis-Plus 和 Hutool 工具类
 */
@Service
public class FileDetailService extends ServiceImpl<FileDetailMapper, FileDetail> implements FileRecorder {


	/**
	 * 保存文件信息到数据库
	 */
	@SneakyThrows
	@Override
	public boolean save(FileInfo info) {
		FileDetail detail = BeanUtil.copyProperties(info,FileDetail.class,"attr");

		//这是手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
		if (info.getAttr() != null) {
			detail.setAttr(new ObjectMapper().writeValueAsString(info.getAttr()));
		}
		boolean b = save(detail);
		if (b) {
			info.setId(detail.getId());
		}
		return b;
	}

	@Override
	public void update(FileInfo fileInfo) {

	}

	/**
	 * 根据 url 查询文件信息
	 */
	@SneakyThrows
	@Override
	public FileInfo getByUrl(String url) {
		FileDetail detail = getOne(new QueryWrapper<FileDetail>().eq(FileDetail.COL_URL,url));
		FileInfo info = BeanUtil.copyProperties(detail,FileInfo.class,"attr");

		//这是手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
		if (StrUtil.isNotBlank(detail.getAttr())) {
			info.setAttr(new ObjectMapper().readValue(detail.getAttr(),Dict.class));
		}
		return info;
	}

	/**
	 * 根据 url 删除文件信息
	 */
	@Override
	public boolean delete(String url) {
		remove(new QueryWrapper<FileDetail>().eq(FileDetail.COL_URL,url));
		return true;
	}

	@Override
	public void saveFilePart(FilePartInfo filePartInfo) {

	}

	@Override
	public void deleteFilePartByUploadId(String s) {

	}
}


