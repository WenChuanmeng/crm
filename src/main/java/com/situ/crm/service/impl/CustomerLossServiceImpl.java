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
import com.situ.crm.mapper.CustomerLossMapper;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.mapper.CustomerLossMapper;
import com.situ.crm.pojo.CustomerLossExample;
import com.situ.crm.pojo.CustomerLoss;
import com.situ.crm.pojo.CustomerLossExample;
import com.situ.crm.pojo.CustomerLossExample.Criteria;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.service.ICustomerLossService;
import com.situ.crm.service.ICustomerPlanService;
import com.situ.crm.service.ICustomerLossService;
import com.situ.crm.util.Util;

@Service
public class CustomerLossServiceImpl implements ICustomerLossService{
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Autowired
	private CustomerLossMapper customerLossMapper;
	@Override
	public DataGridResult findAll(Integer page, Integer rows,CustomerLoss customerLoss) {
		DataGridResult result = new DataGridResult();
		CustomerLossExample customerLossExample = new CustomerLossExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		com.situ.crm.pojo.CustomerLossExample.Criteria createCriteria = customerLossExample.createCriteria();
		if (StringUtils.isNotEmpty(customerLoss.getCustomerManager())) {
			createCriteria.andCustomerManagerLike(com.situ.crm.util.Util.formatLike(customerLoss.getCustomerManager()));
		}
		if (StringUtils.isNotEmpty(customerLoss.getCustomerName())) {
			createCriteria.andCustomerNameLike(com.situ.crm.util.Util.formatLike(customerLoss.getCustomerName()));
		}
		if (customerLoss.getStatus() != null) {
			createCriteria.andStatusEqualTo(customerLoss.getStatus());
		}
		//查询状态为已分配的 
		List<CustomerLoss> saleChanceList = customerLossMapper.selectByExample(customerLossExample);
		//total
		PageInfo<CustomerLoss> pageInfo = new PageInfo<>(saleChanceList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(saleChanceList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			customerLossMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(Integer saleChanceId, CustomerLoss customerLoss) {
		
		//customerLoss.setSaleChanceId(saleChanceId);
		if (customerLossMapper.insert(customerLoss) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(Integer saleChanceId, CustomerLoss customerLoss) {
		//customerLoss.setSaleChanceId(saleChanceId);
		if (customerLossMapper.updateByPrimaryKeySelective(customerLoss) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

	@Override
	public SaleChance findBySaleChanceId(Integer saleChanceId) {
		return saleChanceMapper.selectByPrimaryKey(saleChanceId);
	}

	@Override
	public ServerResponse deleteById(Integer id) {
		if (id == null) {
			return ServerResponse.createERROR("id不能为空");
		}
		int result = customerLossMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return ServerResponse.createSUCCESS("删除成功");
		}
		return ServerResponse.createERROR("删除失败");
	}

	@Override
	public ServerResponse<CustomerLoss> findCustomerLossById(Integer lossId) {
		CustomerLoss customerLoss = customerLossMapper.selectByPrimaryKey(lossId);
		return ServerResponse.createSUCCESS(customerLoss);
	}

	@Override
	public ServerResponse customerLossStatus(CustomerLoss customerLoss) {
		int result = customerLossMapper.updateByPrimaryKeySelective(customerLoss);
		if (result > 0) {
			return ServerResponse.createSUCCESS("操作成功");
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后");
	}


	/*@Override
	public List<CustomerLoss> findCustomerLossName() {
		LinkedList<CustomerLoss> findCustomerLossName = customerLossMapper.findCustomerLossName();
		CustomerLoss customerLoss = new CustomerLoss();
		customerLoss.setCustomerLossName(null);
		findCustomerLossName.addFirst(customerLoss);
		return findCustomerLossName;
	}*/

}
