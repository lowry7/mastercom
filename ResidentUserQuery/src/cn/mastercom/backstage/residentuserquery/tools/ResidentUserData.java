package cn.mastercom.backstage.residentuserquery.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;




import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import cn.mastercom.backstage.residentuserquery.MyProperties;

@Component
public class ResidentUserData
{
	private static Logger log = LoggerFactory.getLogger(ResidentUserData.class);
	
	private  Map<String, Long> userCellMap;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MyProperties myProperties;
	
	
	private  void read()
	{
		userCellMap=new HashMap<>();
		log.info( "开始读取常驻配置...");
		String sql="SELECT DISTINCT MSISDN,ECI FROM "+myProperties.getTableName();
		log.info( sql);
		try(
			Connection conn=dataSource.getConnection();
			PreparedStatement statment = conn.prepareStatement(
					"SELECT DISTINCT MSISDN,ECI FROM "+"tb_常驻用户分析_数据_用户常驻地_dd_180125");
			ResultSet rs =statment.executeQuery()
			)
		{
			while (rs.next())
			{
				userCellMap.put(rs.getString("MSISDN"), rs.getLong("ECI"));
			}

			log.info("读取常驻配置完毕 常驻配置数=" + userCellMap.size());
		}
		catch (Exception e)
		{
			log.error("读取常驻配置!",e);
		}
	}

	public Map<String, Long> getUserCellMap()
	{
		if(userCellMap==null)
		{
			read();
		}
		return userCellMap;
	}
	
}
