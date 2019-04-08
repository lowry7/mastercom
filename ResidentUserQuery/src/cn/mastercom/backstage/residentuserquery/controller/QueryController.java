package cn.mastercom.backstage.residentuserquery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mastercom.backstage.residentuserquery.result.Result;
import cn.mastercom.backstage.residentuserquery.service.QueryService;


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
	public Result<?> doQuery(String querytextarea)
	{
		String sb =queryService.doQuery(querytextarea);
		log.info(sb);
		return Result.success(sb);
	}
}
