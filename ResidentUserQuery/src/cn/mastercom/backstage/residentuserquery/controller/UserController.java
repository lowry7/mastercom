package cn.mastercom.backstage.residentuserquery.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mastercom.backstage.residentuserquery.result.CodeMsg;
import cn.mastercom.backstage.residentuserquery.result.Result;
import cn.mastercom.backstage.residentuserquery.service.UserService;
import cn.mastercom.backstage.residentuserquery.tools.Const;
import cn.mastercom.backstage.residentuserquery.vo.LoginVo;

@Controller
public class UserController
{
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	
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
    public void sendverifycode(HttpSession session) 
	{
		String sendverifycode=userService.sendverifycode();
		session.setAttribute(Const.CODE_KEY, sendverifycode);
    }
	
	@RequestMapping(value="/login/do_login")
	@ResponseBody
    public Result<?> doogin(HttpSession session,LoginVo loginVo)
	{
    	log.info(loginVo.toString());
    	String sendverifycode = (String) session.getAttribute(Const.CODE_KEY);
    	if(loginVo.getCode().equals(sendverifycode))
    	{
    		//登陆成功
    		session.setAttribute(Const.USER_KEY, loginVo);
        	return Result.success(CodeMsg.SUCCESS);
    	}
    	//登陆失败
    	return Result.error(CodeMsg.CODE_ERROR);
	}
}
