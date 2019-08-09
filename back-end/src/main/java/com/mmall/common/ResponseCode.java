package com.mmall.common;



/**
 * Created by LHT
 */
public enum ResponseCode {

    //通用模块
	 SUCCESS(0,"SUCCESS"),
	 ERROR(1,"ERROR"),
	 NEED_LOGIN(10,"NEED_LOGIN"),
	 ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
     SERVER_ERROR (500100, "服务端异常"),
	 BIND_ERROR (500101, "参数校验异常：%s"),
	 REQUEST_ILLEGAL (500102, "请求非法"),
	 ACCESS_LIMIT_REACHED(500104, "访问太频繁！"),
	 //用户模块 5002XX
	 LOGIN_SESSION_ERROR (500210, "Session不存在或者已经失效"),
	 LOGIN_MOBILE_ERROR (500213, "手机号格式错误"),
	 LOGIN_USERNAMEORPASSWORD_ERROR (500215, "用户名或者密码错误"),
	 
	 USER_NAME_ALREADY_EXISTS(500216, "用户名已存在"),
	 USER_NAME_ALREADY_NOTEXISTS(500217, "用户名不存在"),
	 USER_EMAIL_EXISTS(500218, "email已存在"),
	 USER_QUESTION_NOTEXISTS(500219, "找回密码问题不存在"),
	 
	
	//商品模块 5003XX
	
	
	//订单模块 5004XX
	ORDER_NOT_EXIST (500400, "订单不存在"),
	
	//秒杀模块 5005XX
	MIAO_SHA_OVER (500500, "商品已经秒杀完毕"),
	REPEATE_MIAOSHA (500501, "不能重复秒杀"),
	MIAOSHA_FAIL (500502, "秒杀失败");

    private  int code;
    private  String desc;


    private ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    
    public String fillArgs(Object... args) {
		return String.format(this.desc, args);
	}
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
