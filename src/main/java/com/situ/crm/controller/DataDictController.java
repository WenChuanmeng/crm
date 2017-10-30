package com.situ.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.User;
import com.situ.crm.service.IDataDictService;

@Controller
@RequestMapping("/user")
public class DataDictController {
	@Autowired
	private IDataDictService dataDictService;

	@RequestMapping("/index")
	public String index() {
		return "user_manager";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, User user) {
		return dataDictService.findAll(page, rows, user);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return dataDictService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(User user) {
		return dataDictService.add(user);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(User user) {
		return dataDictService.update(user);
	}
	
}
