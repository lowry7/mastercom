package cn.mastercom.backstage.residentuserquery.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;


@Service
public class UserService
{
	
	public String login(String username, String password)
	{
		return password;
	}

	public void sendverifycode() {
		String code = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
 
			int r = random.nextInt(10); //每次随机出一个数字（0-9）
 
			code = code + r;  //把每次随机出的数字拼在一起
 
		}
		System.out.println(code);
	}
}
