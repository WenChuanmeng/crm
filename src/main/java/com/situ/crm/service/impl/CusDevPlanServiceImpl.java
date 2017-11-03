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
import com.situ.crm.mapper.CusDevPlanMapper;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.mapper.CusDevPlanMapper;
import com.situ.crm.pojo.CusDevPlanExample;
import com.situ.crm.pojo.CusDevPlan;
import com.situ.crm.pojo.CusDevPlanExample;
import com.situ.crm.pojo.CusDevPlanExample.Criteria;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.service.ICusDevPlanService;
import com.situ.crm.service.ICustomerPlanService;
import com.situ.crm.service.ICusDevPlanService;
import com.situ.crm.util.Util;

@Service
public class CusDevPlanServiceImpl implements ICusDevPlanService{
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Autowired
	private CusDevPlanMapper cusDevPlanMapper;
	@Override
	public DataGridResult findAll(Integer page, Integer rows, Integer saleChanceId) {
		DataGridResult result = new DataGridResult();
		CusDevPlanExample cusDevPlanExample = new CusDevPlanExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		com.situ.crm.pojo.CusDevPlanExample.Criteria createCriteria = cusDevPlanExample.createCriteria();
		if (saleChanceId != null) {
			createCriteria.andSaleChanceIdEqualTo(saleChanceId);
		}
		
		//查询状态为已分配的 
		List<CusDevPlan> saleChanceList = cusDevPlanMapper.selectByExample(cusDevPlanExample);
		//total
		PageInfo<CusDevPlan> pageInfo = new PageInfo<>(saleChanceList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(saleChanceList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			cusDevPlanMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(Integer saleChanceId, CusDevPlan cusDevPlan) {
		
		cusDevPlan.setSaleChanceId(saleChanceId);
		if (cusDevPlanMapper.insert(cusDevPlan) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(Integer saleChanceId, CusDevPlan cusDevPlan) {
		cusDevPlan.setSaleChanceId(saleChanceId);
		if (cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) > 0) {
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
		int result = cusDevPlanMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return ServerResponse.createSUCCESS("删除成功");
		}
		return ServerResponse.createERROR("删除失败");
	}


	/*@Override
	public List<CusDevPlan> findCusDevPlanName() {
		LinkedList<CusDevPlan> findCusDevPlanName = cusDevPlanMapper.findCusDevPlanName();
		CusDevPlan cusDevPlan = new CusDevPlan();
		cusDevPlan.setCusDevPlanName(null);
		findCusDevPlanName.addFirst(cusDevPlan);
		return findCusDevPlanName;
	}*/

}
