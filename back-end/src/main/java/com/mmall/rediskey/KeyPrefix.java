package com.mmall.rediskey;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
