package com.situ.crm.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.Customer;

public interface ICustomerService {
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @return DataGridResult
	 */
	DataGridResult findAll(Integer page, Integer rows, Customer customer, Date beginTime, Date endTime);

	/**
	 * 根据id删除
	 * @param ids
	 * @return ServerResponse
	 */
	ServerResponse delete(String ids);

	/**
	 * 添加
	 * @param customer
	 * @return ServerResponse
	 */
	ServerResponse add(Customer customer);

	/**
	 * 更新
	 * @param customer
	 * @return ServerResponse
	 */
	ServerResponse update(Customer customer);

	/**
	 * 根据id查找营销机会
	 * @param customerId
	 * @return ServerResponse<Customer>
	 */
	ServerResponse<Customer> findCustomerById(Integer customerId);

	/**
	 * 更新营销机会的开发状态
	 * @param customer
	 * @return ServerResponse
	 */
	ServerResponse updateCustomerDevResult(Customer customer);

	/*List<Customer> findCustomerName();*/
}
