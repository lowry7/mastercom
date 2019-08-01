package cn.mastercom.backstage.residentuserquery.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.MessageProducer;
import com.dianping.cat.message.Transaction;

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
		MessageProducer cat = Cat.getProducer();
	    Transaction t = cat.newTransaction("URL", "resdentUserQuery/HelloWorld");  //type=URL的事务记录: 你的接口/方法名称
	    try{
	    	Cat.logEvent("your event type", "keyValuePairs");
	        //do your business
	    	System.out.println("dada");
	        t.setStatus(Message.SUCCESS);
	    } catch (Exception e) {
	        Cat.getProducer().logError(e);
	        t.setStatus(e);
	    } finally {
	        t.complete();
	    }
	    
	    return "hello";
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
