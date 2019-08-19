package com.mmall.controller.access;

public class TokenContext
{
	private static ThreadLocal<String> tokenContext=new ThreadLocal<>();

	public static String getToken()
	{
		return tokenContext.get();
	}

	public static void setToken(String token)
	{
		tokenContext.set(token);
	}
	
}
