package cn.mastercom.backstage.residentuserquery.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mastercom.backstage.residentuserquery.tools.AESUtil;
import cn.mastercom.backstage.residentuserquery.tools.AccountValidatorUtil;
import cn.mastercom.backstage.residentuserquery.tools.ResidentUserData;



@Service
public class QueryService
{
	@Autowired
	ResidentUserData residentUserData;
	
	public String doQuery(String querytextarea)
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
				}
			}
		}
		return new String(sb);
	}

}
