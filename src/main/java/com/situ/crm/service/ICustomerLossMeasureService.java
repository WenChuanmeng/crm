package com.situ.crm.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.CustomerLossMeasure;
import com.situ.crm.pojo.SaleChance;

public interface ICustomerLossMeasureService {
	
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @return DataGridResult
	 */
	DataGridResult findAll(Integer page, Integer rows, Integer lossId);

	/**
	 * 根据id删除
	 * @param ids
	 * @return ServerResponse
	 */
	ServerResponse delete(String ids);

	/**
	 * 添加
	 * @param saleChanceId 
	 * @param customerLossMeasure
	 * @return ServerResponse
	 */
	ServerResponse add(Integer saleChanceId, CustomerLossMeasure customerLossMeasure);

	/**
	 * 更新
	 * @param saleChanceId 
	 * @param customerLossMeasure
	 * @return ServerResponse
	 */
	ServerResponse update(Integer saleChanceId, CustomerLossMeasure customerLossMeasure);

	/**
	 * 根据id查找计划项
	 * @param saleChanceId
	 * @return SaleChance
	 */
	SaleChance findBySaleChanceId(Integer lossId);

	/**
	 * 删除计划项
	 * @param id
	 * @return ServerResponse
	 */
	ServerResponse deleteById(Integer id);

	/*List<CustomerLossMeasure> findCustomerLossMeasureName();*/
}
