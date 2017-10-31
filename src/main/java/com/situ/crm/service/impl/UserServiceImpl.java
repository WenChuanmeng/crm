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
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;
import com.situ.crm.pojo.UserExample;
import com.situ.crm.pojo.UserExample.Criteria;
import com.situ.crm.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
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
		if (StringUtils.isNotEmpty(user.getName())) {
			createCriteria.andNameLike(com.situ.crm.util.Util.formatLike(user.getName()));
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

	@Override
	public User findUser(User userTemp) {
		
		return userMapper.findUser(userTemp);
	}

	@Override
	public ServerResponse updatePassword(User user, String newpassword) {
		User userTemp = userMapper.findUser(user);
		
		if (userTemp == null) {
			return ServerResponse.createERROR("密码不正确");
		} else {
			if (newpassword.trim().equals(userTemp.getPassword())) {
				return ServerResponse.createERROR("不能与前密码一致");
			} else {
				userTemp.setPassword(newpassword);
				int result = userMapper.updateByPrimaryKeySelective(userTemp);
				if (result > 0 ) {
					return ServerResponse.createSUCCESS("修改成功");
				}
				return ServerResponse.createERROR("服务器繁忙请稍后");
			}
			
		}
	}

	@Override
	public List<User> findAssignMan() {
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRoleNameEqualTo("客户经理");
		List<User> list = userMapper.selectByExample(example);
		User user = new User();
		user.setRoleName(null);
		list.add(0, user);
		return list;
	}

}
