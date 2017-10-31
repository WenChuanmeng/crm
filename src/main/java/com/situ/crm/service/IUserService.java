package com.situ.crm.service;

import java.util.LinkedList;
import java.util.List;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;

public interface IUserService {
	/**
	 * 根据分页信息返回所有数据
	 * @param page 当前页
	 * @param rows 一页多少数据
	 * @return
	 */
	DataGridResult findAll(Integer page, Integer rows, User user);

	ServerResponse delete(String ids);

	ServerResponse add(User user);

	ServerResponse update(User user);

	User findUser(User userTemp);

	ServerResponse updatePassword(User user, String newpassword);

	/**
	 * 查找客户经理
	 * @return LinkedList<SaleChance>
	 */
	List<User> findAssignMan();
}
