package com.situ.crm.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.Customer;
import com.situ.crm.vo.CustomerConstitute;
import com.situ.crm.vo.CustomerContributeAnalysis;

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

	/**
	 * 查找流失的用户
	 */
	void checkCustomerLoss();

	/**
	 * 查找客户端的贡献
	 * @param page
	 * @param rows
	 * @param contributeAnalysis
	 * @return DataGridResult
	 */
	DataGridResult customerContributeAnalysis(Integer page, Integer rows,
			CustomerContributeAnalysis contributeAnalysis);

	/**
	 * 查询各个用户等级的数量
	 * @return ServerResponse<CustomerConstitute>
	 */
	ServerResponse<List<CustomerConstitute>> customerConstitute();

}
