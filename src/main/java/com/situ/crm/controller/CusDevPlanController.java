package com.situ.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.CusDevPlan;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;
import com.situ.crm.service.ICusDevPlanService;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.service.ICusDevPlanService;
import com.situ.crm.service.IUserService;

@Controller
@RequestMapping("/cusDevPlan")
public class CusDevPlanController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@Autowired
	private ICusDevPlanService cusDevPlanService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private ISaleChanceService saleChanceService;
	
	@RequestMapping("/index")
	public String index() {
		return "cus_dev_plan";
	}
	
	@RequestMapping("/findSaleChanceById")
	@ResponseBody
	private ServerResponse<SaleChance> findSaleChanceById(Integer saleChanceId) {
		return saleChanceService.findSaleChanceById(saleChanceId);
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, Integer saleChanceId) {
		return cusDevPlanService.findAll(page, rows, saleChanceId);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return cusDevPlanService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(Integer saleChanceId, CusDevPlan cusDevPlan) {
		return cusDevPlanService.add(saleChanceId,cusDevPlan);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(Integer saleChanceId, CusDevPlan cusDevPlan) {
		return cusDevPlanService.update(saleChanceId, cusDevPlan);
	}
	
	@RequestMapping("/findAssignMan")
	@ResponseBody
	public List<User> findAssignMan() {
		return userService.findAssignMan();
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	private ServerResponse deleteById(Integer id) {
		return cusDevPlanService.deleteById(id);
	}
	
	@RequestMapping("/updateSaleChanceDevResult")
	@ResponseBody
	private ServerResponse updateSaleChanceDevResult(SaleChance saleChance) {
		return saleChanceService.updateSaleChanceDevResult(saleChance);
	}
}
