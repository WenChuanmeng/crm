package com.situ.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.pojo.Product;
import com.situ.crm.service.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private IProductService productService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@RequestMapping("/index")
	public String index() {
		return "product_manager";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public DataGridResult findAll(Integer page, Integer rows, Product product) {
		return productService.findAll(page, rows, product);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ServerResponse delete(String ids) {
		return productService.delete(ids);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ServerResponse add(Product product) {
		return productService.add(product);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ServerResponse update(Product product) {
		return productService.update(product);
	}
	
}
