package com.situ.crm.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.CustomerService;
import com.situ.crm.vo.CustomerServiceAnalysis;

public interface ICustomerServiceShowService {
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @param endTime 
	 * @param beginTime 
	 * @return DataGridResult
	 */
	DataGridResult findAll(Integer page, Integer rows, CustomerService customerService, Date beginTime, Date endTime);

	/**
	 * 根据id删除
	 * @param ids
	 * @return ServerResponse
	 */
	ServerResponse delete(String ids);

	/**
	 * 添加
	 * @param customerService
	 * @return ServerResponse
	 */
	ServerResponse add(CustomerService customerService);

	/**
	 * 更新
	 * @param customerService
	 * @return ServerResponse
	 */
	ServerResponse update(CustomerService customerService);

	/**
	 * 根据id查找营销机会
	 * @param customerServiceId
	 * @return ServerResponse<CustomerService>
	 */
	ServerResponse<CustomerService> findCustomerServiceById(Integer customerServiceId);

	/**
	 * 更新营销机会的开发状态
	 * @param customerService
	 * @return ServerResponse
	 */
	ServerResponse updateCustomerServiceDevResult(CustomerService customerService);

	/**
	 * 查找服务类型及其次数
	 * @return ServerResponse<CustomerServiceAnalysis>
	 */
	ServerResponse<List<CustomerServiceAnalysis>> customerServiceAnalysis();

	/*List<CustomerService> findCustomerServiceName();*/
}
