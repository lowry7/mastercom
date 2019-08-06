package com.mall.exception;

import com.mmall.common.ResponseCode;


public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private ResponseCode cm;
	
	public GlobalException(ResponseCode cm) {
		super(cm.toString());
		this.cm = cm;
	}

	public ResponseCode getCm() {
		return cm;
	}

}
