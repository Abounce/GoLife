package com.example.administrator.golife.dao;

import com.example.administrator.golife.bean.UserInfo;
import com.example.administrator.golife.db.DBHelper;

import java.util.List;

/**
 * Created by yhy on 2016/12/27.
 */
public class ContactTableDao {
    private DBHelper dbHelper;


    public ContactTableDao(DBHelper dbHelper) {
        this.dbHelper=dbHelper;
    }
    //获取所以联系人信息
    public List<UserInfo> getContacts(){
     return null;
    }
    //通过单个环信id获取单个联系人信息
    public  UserInfo getContactById(String huanxinID){
        return null;
    }

    //通过集合环信id获取联系人信息
    public List<UserInfo> getContactByIds(List<String> huanxinID) {
        return null;
    }

    //保存单个联系人
    public  void saveContact(UserInfo userinfo,boolean isMyContact){


    }
    public  void saveContacts(List<UserInfo> userinfos,boolean isMyContact){

    }
    //删除联系人信息
    public void deleteContact(String hxid){

    }


}
