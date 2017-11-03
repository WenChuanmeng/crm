package com.situ.crm.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.OrderItem;
import com.situ.crm.pojo.SaleChance;

public interface IOrderItemService {
	
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @return DataGridResult
	 */
	DataGridResult findAll(Integer page, Integer rows, Integer orderId);

	/**
	 * 根据id删除
	 * @param ids
	 * @return ServerResponse
	 */
	ServerResponse delete(String ids);

	/**
	 * 添加
	 * @param orderId 
	 * @param OrderItem orderItem
	 * @return ServerResponse
	 */
	ServerResponse add(Integer orderId, OrderItem orderItem);

	/**
	 * 更新
	 * @param orderId 
	 * @param OrderItem orderItem
	 * @return ServerResponse
	 */
	ServerResponse update(Integer orderId, OrderItem orderItem);

	/**
	 * 根据id查找计划项
	 * @param orderId
	 * @return SaleChance
	 */
	SaleChance findBySaleChanceId(Integer orderId);

	/**
	 * 删除计划项
	 * @param id
	 * @return ServerResponse
	 */
	ServerResponse deleteById(Integer id);

	/*List<Customer> findCustomerName();*/
}
