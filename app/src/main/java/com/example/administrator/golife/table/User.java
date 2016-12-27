package com.example.administrator.golife.table;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/12/27.
 */
public class User extends DataSupport {
    private String name;// 用户名称
    private String hxid;// 环信id

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
