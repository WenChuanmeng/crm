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
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.CustomerOrder;
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;
import com.situ.crm.service.ICustomerOrderService;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.service.ICustomerOrderService;
import com.situ.crm.service.IUserService;

@Controller
@RequestMapping("/customerOrder")
public class CustomerOrderController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@Autowired
	private ICustomerOrderService customerOrderService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping("/index")
	public String index() {
		return "customer_order_manager";
	}
	
	@RequestMapping("/findCustomerById")
	@ResponseBody
	private ServerResponse<Customer> findCustomerById(Integer customerId) {
		return customerService.findCustomerById(customerId);
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, Integer customerId) {
		return customerOrderService.findAll(page, rows, customerId);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return customerOrderService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(Integer customerId, CustomerOrder customerOrder) {
		return customerOrderService.add(customerId,customerOrder);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(Integer customerId, CustomerOrder customerOrder) {
		return customerOrderService.update(customerId, customerOrder);
	}
	
	@RequestMapping("/findAssignMan")
	@ResponseBody
	public List<User> findAssignMan() {
		return userService.findAssignMan();
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	private ServerResponse deleteById(Integer id) {
		return customerOrderService.deleteById(id);
	}
	
}
