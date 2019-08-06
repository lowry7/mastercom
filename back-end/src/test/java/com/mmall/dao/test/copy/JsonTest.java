package com.mmall.dao.test.copy;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.mmall.pojo.User;


public class JsonTest
{
	@Test
	public void json()
	{
		 String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]}";
	        ObjectMapper mapper = new ObjectMapper();
	        //Json映射为对象
	        User student;
			try
			{
				student = mapper.readValue(s, User.class);
				//对象转化为Json
		        String json = mapper.writeValueAsString(student);
		        System.out.println(json);
		        System.out.println(student.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        ;
	}
}
