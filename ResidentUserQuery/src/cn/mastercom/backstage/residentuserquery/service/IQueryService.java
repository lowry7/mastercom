package cn.mastercom.backstage.residentuserquery.service;

public interface IQueryService
{
	String doQuery( String userId,String querytextarea);
	void insertQueryLog(String userID, String msisn);
	String hello();
}
