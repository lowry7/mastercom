package cn.mastercom.backstage.residentuserquery.controller;

import javax.servlet.http.HttpSession;

import cn.mastercom.backstage.residentuserquery.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mastercom.backstage.residentuserquery.result.CodeMsg;
import cn.mastercom.backstage.residentuserquery.result.Result;
import cn.mastercom.backstage.residentuserquery.service.QueryService;
import cn.mastercom.backstage.residentuserquery.tools.Const;


@Controller
@RequestMapping("/query")
public class QueryController
{
	private static Logger log = LoggerFactory.getLogger(QueryController.class);
	@Autowired
	QueryService queryService;
	@RequestMapping("")
	public String hello()
	{
		return "query.html";
	}
	
	@RequestMapping("/do_query")
	@ResponseBody
	public Result<?> doQuery(HttpSession session,String querytextarea)
	{
		if(session.getAttribute(Const.USER_KEY)==null)
		{
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		String userId= (String)session.getAttribute(Const.USER_KEY);
		String sb =queryService.doQuery(userId,querytextarea);
		log.info(sb);
		return Result.success(sb);
	}
}
