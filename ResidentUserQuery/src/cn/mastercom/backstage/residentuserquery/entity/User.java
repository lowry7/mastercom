package cn.mastercom.backstage.residentuserquery.entity;

public class User {
    private String userId;

    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}