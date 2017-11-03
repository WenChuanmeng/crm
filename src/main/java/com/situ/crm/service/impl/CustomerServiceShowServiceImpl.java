package com.situ.crm.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.mapper.CustomerServiceMapper;
import com.situ.crm.pojo.CustomerService;
import com.situ.crm.pojo.CustomerServiceExample;
import com.situ.crm.pojo.CustomerServiceExample.Criteria;
import com.situ.crm.service.ICustomerServiceAssignService;
import com.situ.crm.service.ICustomerServiceCreateService;
import com.situ.crm.service.ICustomerServiceDealResultService;
import com.situ.crm.service.ICustomerServiceDealService;
import com.situ.crm.service.ICustomerServiceShowService;
import com.situ.crm.util.Util;

@Service
public class CustomerServiceShowServiceImpl implements ICustomerServiceShowService{
	@Autowired
	private CustomerServiceMapper customerServiceMapper;

	@Override
	public DataGridResult findAll(Integer page, Integer rows, CustomerService customerService, Date beginTime, Date endTime) {
		DataGridResult result = new DataGridResult();
		CustomerServiceExample customerServiceExample = new CustomerServiceExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		Criteria createCriteria = customerServiceExample.createCriteria();
		if (StringUtils.isNotEmpty(customerService.getCustomer())) {
			createCriteria.andCustomerLike(Util.formatLike(customerService.getCustomer()));
		}
		if (StringUtils.isNotEmpty(customerService.getOverview())) {
			createCriteria.andOverviewLike(Util.formatLike(customerService.getOverview()));
		}
		if (StringUtils.isNotEmpty(customerService.getServiceType())) {
			createCriteria.andServiceTypeLike(Util.formatLike(customerService.getServiceType()));
		}
		if (customerService.getStatus() != null) {
			createCriteria.andStatusEqualTo(customerService.getStatus());
		}
		if (beginTime != null && endTime != null) {
			createCriteria.andCreateTimeBetween(beginTime, endTime);
		}
		
		createCriteria.andServiceDealResultIsNotNull();
		List<CustomerService> customerServiceList = customerServiceMapper.selectByExample(customerServiceExample);
		//total
		PageInfo<CustomerService> pageInfo = new PageInfo<>(customerServiceList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(customerServiceList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			customerServiceMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(CustomerService customerService) {
		
		if (customerServiceMapper.insert(customerService) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(CustomerService customerService) {
		if (customerServiceMapper.updateByPrimaryKeySelective(customerService) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

	@Override
	public ServerResponse<CustomerService> findCustomerServiceById(Integer customerServiceId) {
		CustomerService customerService = customerServiceMapper.selectByPrimaryKey(customerServiceId);
		if (customerService != null) {
			return ServerResponse.createSUCCESS(customerService);
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后重试");
	}

	@Override
	public ServerResponse updateCustomerServiceDevResult(CustomerService customerService) {
		int result = customerServiceMapper.updateByPrimaryKeySelective(customerService);
		
		if (result > 0) {
			return ServerResponse.createSUCCESS("更改成功");
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后再试");
	}


	/*@Override
	public List<CustomerService> findCustomerServiceName() {
		LinkedList<CustomerService> findCustomerServiceName = customerServiceMapper.findCustomerServiceName();
		CustomerService customerService = new CustomerService();
		customerService.setCustomerServiceName(null);
		findCustomerServiceName.addFirst(customerService);
		return findCustomerServiceName;
	}*/

}
