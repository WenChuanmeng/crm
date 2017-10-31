package com.situ.crm.service;

import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.DataDic;

public interface IDataDicService {
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @return
	 */
	DataGridResult findAll(Integer page, Integer rows, DataDic dataDic);

	ServerResponse delete(String ids);

	ServerResponse add(DataDic dataDic);

	ServerResponse update(DataDic dataDic);

	List<DataDic> findDataDicName();
}
