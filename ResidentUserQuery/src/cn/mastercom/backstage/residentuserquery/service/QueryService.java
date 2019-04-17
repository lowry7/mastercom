package cn.mastercom.backstage.residentuserquery.service;

import java.util.Date;
import java.util.Map;

import cn.mastercom.backstage.residentuserquery.dao.LogMapper;
import cn.mastercom.backstage.residentuserquery.entity.Log;
import cn.mastercom.backstage.residentuserquery.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mastercom.backstage.residentuserquery.tools.AESUtil;
import cn.mastercom.backstage.residentuserquery.tools.AccountValidatorUtil;
import cn.mastercom.backstage.residentuserquery.tools.ResidentUserData;
import org.springframework.transaction.annotation.Transactional;


@Service
public class QueryService
{
	@Autowired
	ResidentUserData residentUserData;
	@Autowired
	LogMapper logMapper;

	public String doQuery( String userId,String querytextarea)
	{
		String[] tels =querytextarea.split("\n");
		StringBuilder sb=new StringBuilder();
		Map<String, Long> userCellMap=residentUserData.getUserCellMap();
		for(String tel:tels)
		{
			if(AccountValidatorUtil.isMobile(tel))
			{
				String aesTel = AESUtil.parseByte2HexStr(AESUtil.encrypt(tel,"1234561234567890"));
				if(userCellMap.containsKey(aesTel))
				{
					sb.append(tel+"\t"+userCellMap.get(aesTel)+"\n");
					insertQueryLog(userId,aesTel);
				}
			}
		}
		return new String(sb);
	}

	public void insertQueryLog(String userID, String msisn) {
		Log log =new Log();
		log.setUserId(userID);
		log.setQueryTime(new Date());
		log.setMsisdn(msisn);
		logMapper.insertSelective(log);
	}
}
