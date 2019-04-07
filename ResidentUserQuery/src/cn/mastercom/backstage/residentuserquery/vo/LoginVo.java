package cn.mastercom.backstage.residentuserquery.vo;

import javax.validation.constraints.NotNull;



public class LoginVo {
	
	@NotNull
	private String mobile;
	
	@NotNull
	private String code;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "LoginVo [mobile=" + mobile + ", code=" + code + "]";
	}
}
