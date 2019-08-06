package com.mmall.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class BeanConvertUtil
{
	
	static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			 return ""+value;
		}else if(clazz == String.class) {
			 return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else {
			try{
				return mapper.writeValueAsString(value);
			}catch (IOException e){
				e.printStackTrace();
				return null;
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String str, Class<T> clazz) {
		if(str == null || str.length() <= 0 || clazz == null) {
			 return null;
		}
		if(clazz == int.class || clazz == Integer.class) {
			 return (T)Integer.valueOf(str);
		}else if(clazz == String.class) {
			 return (T)str;
		}else if(clazz == long.class || clazz == Long.class) {
			return  (T)Long.valueOf(str);
		}else {
			try{
				return mapper.readValue(str, clazz);
			}catch (IOException e){
				e.printStackTrace();
				return null;
			}
		}
	}
}
