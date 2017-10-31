package com.situ.crm.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.SaleChanceExample;
import com.situ.crm.pojo.SaleChanceExample.Criteria;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.util.Util;

@Service
public class SaleChanceServiceImpl implements ISaleChanceService{
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Override
	public DataGridResult findAll(Integer page, Integer rows, SaleChance saleChance) {
		DataGridResult result = new DataGridResult();
		SaleChanceExample saleChanceExample = new SaleChanceExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		Criteria createCriteria = saleChanceExample.createCriteria();
		if (StringUtils.isNotEmpty(saleChance.getCustomerName())) {
			createCriteria.andCustomerNameLike(Util.formatLike(saleChance.getCustomerName()));
		}
		List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
		//total
		PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(saleChanceList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			saleChanceMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(SaleChance saleChance) {
		
		
		if (saleChanceMapper.insert(saleChance) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(SaleChance saleChance) {
		if (saleChanceMapper.updateByPrimaryKey(saleChance) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}


	/*@Override
	public List<SaleChance> findSaleChanceName() {
		LinkedList<SaleChance> findSaleChanceName = saleChanceMapper.findSaleChanceName();
		SaleChance saleChance = new SaleChance();
		saleChance.setSaleChanceName(null);
		findSaleChanceName.addFirst(saleChance);
		return findSaleChanceName;
	}*/

}
