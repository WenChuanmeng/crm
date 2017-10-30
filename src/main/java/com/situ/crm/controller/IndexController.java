package com.situ.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

	//跳转到首页
	@RequestMapping("/index")
	private String index() {
		return "index";
	}
}
