package com.mmall.controller.config;


import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.mmall.controller.access.TokenContext;

@Service
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

	
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Token.class);
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory  binderFactory) {
		return TokenContext.getToken();
	}

}
