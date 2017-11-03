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
import com.situ.crm.mapper.CustomerMapper;
import com.situ.crm.mapper.CustomerLinkmanMapper;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.mapper.CustomerMapper;
import com.situ.crm.pojo.CustomerExample;
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.CustomerExample;
import com.situ.crm.pojo.CustomerExample.Criteria;
import com.situ.crm.pojo.CustomerLinkman;
import com.situ.crm.pojo.CustomerLinkmanExample;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.service.ICustomerLinkManService;
import com.situ.crm.service.ICustomerPlanService;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.util.Util;

@Service
public class CustomerLinkManServiceImpl implements ICustomerLinkManService{
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private CustomerLinkmanMapper customerLinkmanMapper;
	@Override
	public DataGridResult findAll(Integer page, Integer rows, Integer customerId) {
		DataGridResult result = new DataGridResult();
		CustomerLinkmanExample customerLinkmanExample = new CustomerLinkmanExample();
		
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		com.situ.crm.pojo.CustomerLinkmanExample.Criteria createCriteria = customerLinkmanExample.createCriteria();
		if (customerId != null) {
			createCriteria.andCustomerIdEqualTo(customerId);
		}
		
		//查询状态为已分配的 
		List<CustomerLinkman> selectByExample = customerLinkmanMapper.selectByExample(customerLinkmanExample);
		//total
		PageInfo<CustomerLinkman> pageInfo = new PageInfo<>(selectByExample);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(selectByExample);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			customerMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(Integer customerId, CustomerLinkman customerLinkman) {
		
		customerLinkman.setCustomerId(customerId);
		if (customerLinkmanMapper.insert(customerLinkman) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(Integer customerId, CustomerLinkman customerLinkman) {
		customerLinkman.setCustomerId(customerId);
		if (customerLinkmanMapper.updateByPrimaryKeySelective(customerLinkman) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

	@Override
	public SaleChance findBySaleChanceId(Integer customerId) {
		return saleChanceMapper.selectByPrimaryKey(customerId);
	}

	@Override
	public ServerResponse deleteById(Integer id) {
		if (id == null) {
			return ServerResponse.createERROR("id不能为空");
		}
		int result = customerLinkmanMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return ServerResponse.createSUCCESS("删除成功");
		}
		return ServerResponse.createERROR("删除失败");
	}


	/*@Override
	public List<Customer> findCustomerName() {
		LinkedList<Customer> findCustomerName = customerMapper.findCustomerName();
		Customer customer = new Customer();
		customer.setCustomerName(null);
		findCustomerName.addFirst(customer);
		return findCustomerName;
	}*/

}
