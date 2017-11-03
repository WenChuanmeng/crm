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
import com.situ.crm.mapper.CustomerLossMeasureMapper;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.mapper.CustomerLossMeasureMapper;
import com.situ.crm.pojo.CustomerLossMeasureExample;
import com.situ.crm.pojo.CustomerLossMeasure;
import com.situ.crm.pojo.CustomerLossMeasureExample;
import com.situ.crm.pojo.CustomerLossMeasureExample.Criteria;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.service.ICustomerLossMeasureService;
import com.situ.crm.service.ICustomerPlanService;
import com.situ.crm.service.ICustomerLossMeasureService;
import com.situ.crm.util.Util;

@Service
public class CustomerLossMeasureServiceImpl implements ICustomerLossMeasureService{
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Autowired
	private CustomerLossMeasureMapper customerLossMeasureMapper;
	@Override
	public DataGridResult findAll(Integer page, Integer rows, Integer lossId) {
		DataGridResult result = new DataGridResult();
		CustomerLossMeasureExample customerLossMeasureExample = new CustomerLossMeasureExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		com.situ.crm.pojo.CustomerLossMeasureExample.Criteria createCriteria = customerLossMeasureExample.createCriteria();
		if (lossId != null) {
			createCriteria.andLossIdEqualTo(lossId);
		}
		
		//查询状态为已分配的 
		List<CustomerLossMeasure> saleChanceList = customerLossMeasureMapper.selectByExample(customerLossMeasureExample);
		//total
		PageInfo<CustomerLossMeasure> pageInfo = new PageInfo<>(saleChanceList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(saleChanceList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			customerLossMeasureMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(Integer saleChanceId, CustomerLossMeasure customerLossMeasure) {
		
		//customerLossMeasure.setSaleChanceId(saleChanceId);
		if (customerLossMeasureMapper.insert(customerLossMeasure) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(Integer saleChanceId, CustomerLossMeasure customerLossMeasure) {
		//customerLossMeasure.setSaleChanceId(saleChanceId);
		if (customerLossMeasureMapper.updateByPrimaryKeySelective(customerLossMeasure) > 0) {
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
		int result = customerLossMeasureMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return ServerResponse.createSUCCESS("删除成功");
		}
		return ServerResponse.createERROR("删除失败");
	}


	/*@Override
	public List<CustomerLossMeasure> findCustomerLossMeasureName() {
		LinkedList<CustomerLossMeasure> findCustomerLossMeasureName = customerLossMeasureMapper.findCustomerLossMeasureName();
		CustomerLossMeasure customerLossMeasure = new CustomerLossMeasure();
		customerLossMeasure.setCustomerLossMeasureName(null);
		findCustomerLossMeasureName.addFirst(customerLossMeasure);
		return findCustomerLossMeasureName;
	}*/

}
