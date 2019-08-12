package com.mmall.controller.exception;

import com.mmall.common.ResponseCode;


public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private ResponseCode responseCode;
	
	public GlobalException(ResponseCode responseCode) {
		super(responseCode.toString());
		this.responseCode = responseCode;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

}
