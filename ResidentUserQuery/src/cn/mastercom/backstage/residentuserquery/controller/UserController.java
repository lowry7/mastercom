package cn.mastercom.backstage.residentuserquery.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mastercom.backstage.residentuserquery.result.Result;
import cn.mastercom.backstage.residentuserquery.service.UserService;
import cn.mastercom.backstage.residentuserquery.tools.Const;
import cn.mastercom.backstage.residentuserquery.vo.LoginVo;
import org.thymeleaf.util.StringUtils;

@Controller
public class UserController
{
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	
	@RequestMapping(value="")
    public String login(Model model) 
	{
		return "login";
    }
	
	@RequestMapping(value="/login/sendverifycode")
	@ResponseBody
    public void sendverifycode(HttpSession session,String tel)
	{
		String code=userService.getCode(tel);
		if(!StringUtils.isEmpty(code))
		{
			session.setAttribute(Const.CODE_KEY, code);
		}
		log.info("s"+code);
		userService.sendverifycode(tel,code);

    }
	
	@RequestMapping(value="/login/do_login",method = RequestMethod.POST)
	@ResponseBody
    public Result doLogin(HttpSession session,LoginVo loginVo)
	{
    	log.info(loginVo.toString());
		Result<String> response = userService.doLogin(session,loginVo);
		if(response.isSuccess()){
			session.setAttribute(Const.USER_KEY,loginVo.getMobile());
		}
    	return response;
	}
}
