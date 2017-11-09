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
import com.situ.crm.pojo.Customer;
import com.situ.crm.pojo.DataDic;
import com.situ.crm.pojo.User;
import com.situ.crm.service.ICustomerService;
import com.situ.crm.service.IDataDicService;
import com.situ.crm.service.IUserService;
import com.situ.crm.vo.CustomerConstitute;
import com.situ.crm.vo.CustomerContributeAnalysis;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IDataDicService dataDicService;

	@RequestMapping("/index")
	public String index() {
		return "customer_manager";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, Customer customer,Date beginTime, Date endTime) {
		return customerService.findAll(page, rows, customer, beginTime, endTime);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return customerService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(Customer customer) {
		System.out.println(customer);
		return customerService.add(customer);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(Customer customer) {
		return customerService.update(customer);
	}
	
	@RequestMapping("/findAssignMan")
	@ResponseBody
	public List<User> findAssignMan() {
		return userService.findAssignMan();
	}
	
	//客户等级
	@RequestMapping("/findLevel")
	@ResponseBody
	private List<DataDic> findLevel() {
		return dataDicService.findLevel();
	}
	
	//客户贡献首页
	@RequestMapping("/customerContributeAnalysisPage")
	private String customerContributeAnalysisPage() {
		return "customer_contribute_analysis";
	}
	
	//查询客户贡献总金额
	@RequestMapping("/customerContributeAnalysis")
	@ResponseBody
	private DataGridResult customerContributeAnalysis(Integer page, Integer rows, CustomerContributeAnalysis contributeAnalysis) {
		return customerService.customerContributeAnalysis(page, rows, contributeAnalysis);
	}
	
	//客户构成页面
	@RequestMapping("/customerConstitutePage")
	private String customerConstitutePage() {
		return "customer_constitute";
	}
	
	//查询每个客户等级的数量
	@RequestMapping("/customerConstitute")
	@ResponseBody
	private ServerResponse<List<CustomerConstitute>> customerConstitute() {
		return customerService.customerConstitute();
	}
}
