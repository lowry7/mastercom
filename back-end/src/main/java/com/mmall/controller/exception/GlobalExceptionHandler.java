package com.mmall.controller.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	@ExceptionHandler(value=Exception.class)
	public ServerResponse<String> exceptionHandler(HttpServletRequest request, Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException) {
			GlobalException ex = (GlobalException)e;
			return ServerResponse.createByError(ex.getResponseCode());
		}else if(e instanceof BindException) {
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return ServerResponse.createByErrorCodeMessage(ResponseCode.BIND_ERROR, ResponseCode.BIND_ERROR.fillArgs(msg));
		}else {
			return ServerResponse.createByError(ResponseCode.SERVER_ERROR);
		}
	}
}
