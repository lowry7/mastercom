package cn.mastercom.backstage.residentuserquery.entity;

import java.util.Date;

public class Log {
    private String userId;

    private Date queryTime;

    private String msisdn;


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}