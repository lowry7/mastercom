package cn.mastercom.backstage.residentuserquery.service;

import java.sql.*;
import java.util.Random;

import cn.mastercom.backstage.residentuserquery.MyProperties;
import cn.mastercom.backstage.residentuserquery.dao.UserMapper;
import cn.mastercom.backstage.residentuserquery.result.CodeMsg;
import cn.mastercom.backstage.residentuserquery.result.Result;
import cn.mastercom.backstage.residentuserquery.tools.Const;
import cn.mastercom.backstage.residentuserquery.vo.LoginVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService
{
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	public UserMapper userMapper;

	@Autowired
	public MyProperties myProperties;

	public static final String sql="INSERT  INTO wf_task_sms_send\n" +
			"                ( [desttermid] ,\n" +
			"                  [msgcontent] ,\n" +
			"                  [createtime] ,\n" +
			"                  [status]\n" +
			"                )\n" +
			"                 SELECT  ? ,\n" +
			"                                ? ,\n" +
			"                                GETDATE() ,\n" +
			"                                0\n";

	public String getCode(String tel) {
		if(userMapper.selectByUserId(tel)!=null)
		{
			return getCode();
		}
		return null;
	}

	public Result doLogin(HttpSession session, LoginVo loginVo) {
		String sendverifycode = (String) session.getAttribute(Const.CODE_KEY);
		if(loginVo.getCode().equals(sendverifycode))
		{
			//登陆成功
			return Result.success();
		}
		//登陆失败
		return Result.error(CodeMsg.CODE_ERROR);
	}
	public void sendverifycode(String tel,String code) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			log.error(String.valueOf(e));
		}
		try(Connection conn=DriverManager.getConnection(myProperties.getConnString());
			PreparedStatement statment = conn.prepareStatement(sql);
			)
		{
			statment.setString(1,tel);
			statment.setString(2,code);
			statment.execute();
		}
		catch (Exception e)
		{
			log.error(String.valueOf(e));
		}
	}
	private String getCode() {
		String code = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {

			int r = random.nextInt(10); //每次随机出一个数字（0-9）

			code = code + r;  //把每次随机出的数字拼在一起

		}
		System.out.println(code);
		return code;
	}


}
