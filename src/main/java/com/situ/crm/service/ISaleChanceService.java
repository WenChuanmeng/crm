package com.situ.crm.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletOutputStream;

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
	DataGridResult findAll(Integer page, Integer rows, SaleChance saleChance, Date beginTime, Date endTime);

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

	/**
	 * 根据id查找营销机会
	 * @param saleChanceId
	 * @return ServerResponse<SaleChance>
	 */
	ServerResponse<SaleChance> findSaleChanceById(Integer saleChanceId);

	/**
	 * 更新营销机会的开发状态
	 * @param saleChance
	 * @return ServerResponse
	 */
	ServerResponse updateSaleChanceDevResult(SaleChance saleChance);

	/**
	 * 生成Excel表格
	 * @return ServerResponse
	 * @throws IOException 
	 */

	void createExcel(ServletOutputStream outputStream) throws IOException;

	int uploadExcel(String fileName);

	/*List<SaleChance> findSaleChanceName();*/
}
