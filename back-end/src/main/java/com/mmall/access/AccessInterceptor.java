package com.mmall.access;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AccessInterceptor  extends HandlerInterceptorAdapter{

    Log log=LogFactory.getLog(AccessInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("====================dsd====");
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
		}
		return true;
	}
	
/*	private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(Result.error(cm));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}*/

/*	private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request, MiaoshaUserService.COOKI_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return userService.getByToken(response, token);
	}*/
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(cookies == null || cookies.length <= 0){
			return null;
		}
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
