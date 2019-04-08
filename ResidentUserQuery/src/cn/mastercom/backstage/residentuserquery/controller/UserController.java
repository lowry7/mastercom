package cn.mastercom.backstage.residentuserquery.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mastercom.backstage.residentuserquery.result.Result;
import cn.mastercom.backstage.residentuserquery.service.UserService;
import cn.mastercom.backstage.residentuserquery.vo.LoginVo;

@Controller
public class UserController
{
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	
	String sendverifycode;
	
	@RequestMapping(value="/")
    public String hello(Model model) 
	{
		model.addAttribute("name","罗海涛");
		return "hello";
    }
	
	@RequestMapping(value="/login")
    public String login(Model model) 
	{
		return "login";
    }
	
	@RequestMapping(value="/login/sendverifycode")
	@ResponseBody
    public void sendverifycode() 
	{
		sendverifycode=userService.sendverifycode();
    }
	
	@RequestMapping(value="/login/do_login")
	@ResponseBody
    public Result<?> doogin(LoginVo loginVo)
	{
    	log.info(loginVo.toString());
    	//登录
    	return Result.success(loginVo);
	}
}
