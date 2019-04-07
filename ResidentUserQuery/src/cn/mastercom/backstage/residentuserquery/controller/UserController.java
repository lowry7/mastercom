package cn.mastercom.backstage.residentuserquery.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import cn.mastercom.backstage.residentuserquery.service.impl.UserService;
import cn.mastercom.backstage.residentuserquery.vo.LoginVo;

@Controller
public class UserController
{
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
		userService.sendverifycode();
    }
	
	@RequestMapping(value="/login/do_login")
	@ResponseBody
    public String do_login(LoginVo loginVo)
	{
		System.out.println(loginVo.toString());
		return loginVo.toString();
	}
}
