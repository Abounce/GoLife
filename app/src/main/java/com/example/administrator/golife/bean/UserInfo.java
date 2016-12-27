package com.example.administrator.golife.bean;

/**
 * Created by yhy on 2016/12/27.
 */

//没用的bean,
public class UserInfo {
    private String name;// 用户名称
    private String hxid;// 环信id

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
        this.hxid=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }
}
