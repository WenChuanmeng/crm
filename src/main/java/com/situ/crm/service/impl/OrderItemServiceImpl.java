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
import com.situ.crm.mapper.OrderItemMapper;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.mapper.CustomerMapper;
import com.situ.crm.pojo.CustomerExample;
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.CustomerExample;
import com.situ.crm.pojo.CustomerExample.Criteria;
import com.situ.crm.pojo.OrderItem;
import com.situ.crm.pojo.OrderItemExample;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.service.IOrderItemService;
import com.situ.crm.service.IOrderItemService;
import com.situ.crm.service.ICustomerPlanService;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.util.Util;

@Service
public class OrderItemServiceImpl implements IOrderItemService{
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Override
	public DataGridResult findAll(Integer page, Integer rows, Integer orderId) {
		DataGridResult result = new DataGridResult();
		OrderItemExample orderItemExample = new OrderItemExample();
		
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		com.situ.crm.pojo.OrderItemExample.Criteria createCriteria = orderItemExample.createCriteria();
		if (orderId != null) {
			createCriteria.andOrderIdEqualTo(orderId);
		}
		
		//查询状态为已分配的 
		List<OrderItem> selectByExample = orderItemMapper.selectByExample(orderItemExample);
		//total
		PageInfo<OrderItem> pageInfo = new PageInfo<>(selectByExample);
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
	public ServerResponse add(Integer orderId, OrderItem orderItem) {
		
		//orderItem.setCustomerId(orderId);
		if (orderItemMapper.insert(orderItem) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(Integer orderId, OrderItem orderItem) {
		//orderItem.setCustomerId(orderId);
		if (orderItemMapper.updateByPrimaryKeySelective(orderItem) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

	@Override
	public SaleChance findBySaleChanceId(Integer orderId) {
		return saleChanceMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public ServerResponse deleteById(Integer id) {
		if (id == null) {
			return ServerResponse.createERROR("id不能为空");
		}
		int result = orderItemMapper.deleteByPrimaryKey(id);
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
