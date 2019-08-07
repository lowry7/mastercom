package com.mmall.dao.test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.pojo.User;

public class JsonTest
{
	
	@Test
	public void json()
	{
		ObjectMapper mapper = new ObjectMapper();
		// Json映射为对象
		User a = new User();
		a.setPassword("111");
		a.setUsername("aaaaageely");
		a.setRole(0);
		a.setCreateTime(new Date());
		a.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		Map map=Maps.newHashMap();
		String[] ss={"s","a","d"};
		map.put("sad", Lists.newArrayList(ss));
		map.put("2", "dsa");
		try
		{
			// 对象转化为Json
			String json = mapper.writeValueAsString(a);
			System.out.println(json);
			
			String jsond = mapper.writeValueAsString(map);
			System.out.println(jsond);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
