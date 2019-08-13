package com.mmall.controller.access;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.redis.RedisService;
import com.mmall.rediskey.AccessKey;
import com.mmall.service.IUserService;
import com.mmall.util.BeanConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AccessInterceptor  extends HandlerInterceptorAdapter{

    Log log=LogFactory.getLog(AccessInterceptor.class);


	@Autowired
	IUserService userService;

	@Autowired
	RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod) {
		  // 设置允许多个域名请求
		  String[] allowDomains = {"http://127.0.0.1:8088"};
		  Set<String> allowOrigins = new HashSet<>(Arrays.asList(allowDomains));
		  String originHeads = request.getHeader("Origin");
		  if(allowOrigins.contains(originHeads)){
		    //设置允许跨域的配置
		    // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
			response.setHeader("Access-Control-Allow-Origin", originHeads);
		  }

			User user = userService.getUserInfo(request, response);
			UserContext.setUser(user);
			HandlerMethod hm = (HandlerMethod)handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
			if(accessLimit == null) {
				return true;
			}
			int seconds = accessLimit.seconds();
			int maxCount = accessLimit.maxCount();
			boolean needLogin = accessLimit.needLogin();
			String key = request.getRequestURI();
			if(needLogin) {
				if(user == null) {
					render(response, ResponseCode.NEED_LOGIN);
					return false;
				}
				key += "_" + user.getId();
			}else {
				//do nothing
			}
			AccessKey ak = AccessKey.withExpire(seconds);
			Integer count = redisService.get(ak, key, Integer.class);
			if(count  == null) {
				redisService.set(ak, key, 1);
			}else if(count < maxCount) {
				redisService.incr(ak, key);
			}else {
				render(response, ResponseCode.ACCESS_LIMIT_REACHED);
				return false;
			}
		}
		return true;
	}

	private void render(HttpServletResponse response, ResponseCode responseCode)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = BeanConvertUtil.beanToString(ServerResponse.createByError(responseCode));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}
	
}
