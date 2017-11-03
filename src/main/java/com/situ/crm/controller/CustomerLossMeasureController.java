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
import com.situ.crm.pojo.CustomerLoss;
import com.situ.crm.pojo.CustomerLossMeasure;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;
import com.situ.crm.service.ICustomerLossMeasureService;
import com.situ.crm.service.ICustomerLossService;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.service.ICustomerLossMeasureService;
import com.situ.crm.service.IUserService;

@Controller
@RequestMapping("/customerLossMeasure")
public class CustomerLossMeasureController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@Autowired
	private ICustomerLossMeasureService customerLossMeasureService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private ICustomerLossService customerLossService;
	
	@RequestMapping("/index")
	public String index() {
		return "customer_loss_measure_manager";
	}
	
	@RequestMapping("/findCustomerLossById")
	@ResponseBody
	private ServerResponse<CustomerLoss> findCustomerLossById(Integer lossId) {
		return customerLossService.findCustomerLossById(lossId);
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, Integer lossId) {
		return customerLossMeasureService.findAll(page, rows, lossId);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return customerLossMeasureService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(Integer saleChanceId, CustomerLossMeasure customerLossMeasure) {
		return customerLossMeasureService.add(saleChanceId,customerLossMeasure);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(Integer saleChanceId, CustomerLossMeasure customerLossMeasure) {
		return customerLossMeasureService.update(saleChanceId, customerLossMeasure);
	}
	
	@RequestMapping("/findAssignMan")
	@ResponseBody
	public List<User> findAssignMan() {
		return userService.findAssignMan();
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	private ServerResponse deleteById(Integer id) {
		return customerLossMeasureService.deleteById(id);
	}
	
	@RequestMapping("/customerLossStatus")
	@ResponseBody
	private ServerResponse customerLossStatus(CustomerLoss customerLoss) {
		return customerLossService.customerLossStatus(customerLoss);
	}
}
