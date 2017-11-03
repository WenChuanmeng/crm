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
import com.situ.crm.pojo.CustomerContact;
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.User;
import com.situ.crm.service.ICustomerContactService;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.service.ICustomerContactService;
import com.situ.crm.service.IUserService;

@Controller
@RequestMapping("/customerContact")
public class CustomerContactController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@Autowired
	private ICustomerContactService customerContactService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping("/index")
	public String index() {
		return "customer_contact_manager";
	}
	
	@RequestMapping("/findCustomerById")
	@ResponseBody
	private ServerResponse<Customer> findCustomerById(Integer customerId) {
		return customerService.findCustomerById(customerId);
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, Integer customerId) {
		return customerContactService.findAll(page, rows, customerId);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return customerContactService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(Integer customerId, CustomerContact customerContact) {
		return customerContactService.add(customerId,customerContact);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(Integer customerId, CustomerContact customerContact) {
		return customerContactService.update(customerId, customerContact);
	}
	
	@RequestMapping("/findAssignMan")
	@ResponseBody
	public List<User> findAssignMan() {
		return userService.findAssignMan();
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	private ServerResponse deleteById(Integer id) {
		return customerContactService.deleteById(id);
	}
	
}
