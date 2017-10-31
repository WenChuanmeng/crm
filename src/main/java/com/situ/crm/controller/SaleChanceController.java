package com.situ.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.service.IUserService;

@Controller
@RequestMapping("/saleChance")
public class SaleChanceController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@Autowired
	private ISaleChanceService saleChanceService;
	
	@Autowired
	private IUserService userService;

	@RequestMapping("/index")
	public String index() {
		return "sale_chance_manager";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, SaleChance saleChance,Date beginTime, Date endTime) {
		System.out.println(saleChance + " +++++++++++++ "+ beginTime + "+++++++++=" +endTime);
		return saleChanceService.findAll(page, rows, saleChance, beginTime, endTime);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return saleChanceService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(SaleChance saleChance) {
		System.out.println(saleChance);
		return saleChanceService.add(saleChance);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(SaleChance saleChance) {
		return saleChanceService.update(saleChance);
	}
	
	@RequestMapping("/findAssignMan")
	@ResponseBody
	public List<User> findAssignMan() {
		return userService.findAssignMan();
	}
}
