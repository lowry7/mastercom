package cn.mastercom.backstage.residentuserquery;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="mastercom")
public class MyProperties
{
	private String tableName;
	private String logTableName;
	private String connString;

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getLogTableName() {
		return logTableName;
	}

	public void setLogTableName(String logTableName) {
		this.logTableName = logTableName;
	}

	public String getConnString() {
		return connString;
	}

	public void setConnString(String connString) {
		this.connString = connString;
	}
}
