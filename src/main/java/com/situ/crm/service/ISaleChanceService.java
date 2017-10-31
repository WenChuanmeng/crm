package com.situ.crm.service;

import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.SaleChance;

public interface ISaleChanceService {
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @return DataGridResult
	 */
	DataGridResult findAll(Integer page, Integer rows, SaleChance saleChance);

	/**
	 * 根据id删除
	 * @param ids
	 * @return ServerResponse
	 */
	ServerResponse delete(String ids);

	/**
	 * 添加
	 * @param saleChance
	 * @return ServerResponse
	 */
	ServerResponse add(SaleChance saleChance);

	/**
	 * 更新
	 * @param saleChance
	 * @return ServerResponse
	 */
	ServerResponse update(SaleChance saleChance);

	/*List<SaleChance> findSaleChanceName();*/
}
