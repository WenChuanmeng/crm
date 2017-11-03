package com.situ.crm.service.impl;

import java.text.SimpleDateFormat;
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
import com.situ.crm.mapper.CustomerLossMapper;
import com.situ.crm.mapper.CustomerMapper;
import com.situ.crm.mapper.CustomerOrderMapper;
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.CustomerExample;
import com.situ.crm.pojo.CustomerExample.Criteria;
import com.situ.crm.pojo.CustomerLoss;
import com.situ.crm.pojo.CustomerOrder;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.util.Util;

@Service
public class CustomerServiceImpl implements ICustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private CustomerOrderMapper customerOrderMapper;
	
	@Autowired
	private CustomerLossMapper customerLossMapper;
	
	@Override
	public DataGridResult findAll(Integer page, Integer rows, Customer customer, Date beginTime, Date endTime) {
		DataGridResult result = new DataGridResult();
		CustomerExample customerExample = new CustomerExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		Criteria createCriteria = customerExample.createCriteria();
		if (StringUtils.isNotEmpty(customer.getName())) {
			createCriteria.andNameLike(Util.formatLike(customer.getName()));
		}
		if (StringUtils.isNotEmpty(customer.getNum())) {
			createCriteria.andNumLike(Util.formatLike(customer.getNum()));
		}
		
		List<Customer> customerList = customerMapper.selectByExample(customerExample);
		//total
		PageInfo<Customer> pageInfo = new PageInfo<>(customerList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(customerList);
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
	public ServerResponse add(Customer customer) {
		
		/*if (customer.getAssignMan() == null && customer.getAssignMan().trim().equals("")) {
			customer.setStatus(0);
		}
		if (customer.getAssignMan().equals("--暂不指派--")) {
			customer.setAssignMan(null);
			customer.setStatus(0);
		}*/
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");//设置日期格式
		String date = df.format(new Date());
		String num = "KH" + date;
		customer.setNum(num);
		if (customerMapper.insert(customer) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(Customer customer) {
		customer.setStatus(1);
		if (customerMapper.updateByPrimaryKeySelective(customer) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

	@Override
	public ServerResponse<Customer> findCustomerById(Integer customerId) {
		Customer customer = customerMapper.selectByPrimaryKey(customerId);
		if (customer != null) {
			return ServerResponse.createSUCCESS(customer);
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后重试");
	}

	@Override
	public ServerResponse updateCustomerDevResult(Customer customer) {
		int result = customerMapper.updateByPrimaryKeySelective(customer);
		
		if (result > 0) {
			return ServerResponse.createSUCCESS("更改成功");
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后再试");
	}

	@Override
	public void checkCustomerLoss() {
		
		//1. 查找流失客户
		List<Customer> customerList =  customerMapper.findLossCustomer();
		for (Customer customer : customerList) {
			//2.实例化用户流失实体
			CustomerLoss customerLoss= new CustomerLoss();
			customerLoss.setCustomerNo(customer.getNum());
			customerLoss.setCustomerName(customer.getName());
			customerLoss.setCustomerManager(customer.getManagerName());
			customerLoss.setStatus(0);
			//3.查找指定用户的最近的订单
			CustomerOrder customerOrder = customerOrderMapper.findLastOrder(customer.getId());
			
			if (customerOrder == null) {
				customerLoss.setLastOrderTime(null);
			} else {
				customerLoss.setLastOrderTime(customerOrder.getOrderDate());
			}
			
			//4.添加到客户流失表
			customerLossMapper.insert(customerLoss);
			//5 将客户表customer中的status改成1
			customer.setStatus(1);
			customerMapper.updateByPrimaryKeySelective(customer);
		}
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
