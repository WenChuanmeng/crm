package com.situ.crm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.situ.crm.pojo.User;
import com.situ.crm.service.IUserService;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping("/login")
	private String login() {
		return "login";
	}
	
	@RequestMapping("/loginIn")
	private String loginIn(String name, User userTemp, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = userService.findUser(userTemp);
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/index/index.action";
		} else {
			model.addAttribute("error", "用户民或密码错误");
			return "redirect:login.action";
		}
	}
	
	@RequestMapping("/loginOut")
	private String loginOut(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (null != user) {
			session.removeAttribute("user");
			return "redirect:login.action";
		}
		return "redirct:/index/index.action";
	}
}
