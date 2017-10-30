package com.situ.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.Util;
import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ResponseCode;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.mapper.UserMapper;
import com.situ.crm.pojo.User;
import com.situ.crm.pojo.UserExample;
import com.situ.crm.pojo.UserExample.Criteria;
import com.situ.crm.service.IDataDictService;
import com.situ.crm.service.IUserService;

@Service
public class DataDictServiceImpl implements IDataDictService{
	@Autowired
	private UserMapper userMapper;

	@Override
	public DataGridResult findAll(Integer page, Integer rows, User user) {
		DataGridResult result = new DataGridResult();
		UserExample userExample = new UserExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		Criteria createCriteria = userExample.createCriteria();
		if (StringUtils.isNotEmpty(user.getUserName())) {
			createCriteria.andUserNameLike(com.situ.crm.util.Util.formatLike(user.getUserName()));
		}
		List<User> userList = userMapper.selectByExample(userExample);
		//total
		PageInfo<User> pageInfo = new PageInfo<>(userList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(userList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			userMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(User user) {
		if (userMapper.insert(user) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(User user) {
		if (userMapper.updateByPrimaryKey(user) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

}
