package com.example.administrator.golife.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.golife.bean.UserInfo;
import com.example.administrator.golife.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhy on 2016/12/27.
 */
public class ContactTableDao {
    private DBHelper dbHelper;
    private UserInfo userinfo;

    public ContactTableDao(DBHelper dbHelper) {
        this.dbHelper=dbHelper;
    }
    //获取所以联系人信息
    public List<UserInfo> getContacts(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(ContactTable.TAB_NAME, null, "is_contact=?", new String[]{"1"}, null, null, null);
        List<UserInfo> users =new ArrayList<>();
        while (cursor.moveToNext()){
            UserInfo userinfo=new UserInfo();
            userinfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userinfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            users.add(userinfo);
        }
        cursor.close();

        return users;
    }
    //通过单个环信id获取单个联系人信息
    public  UserInfo getContactById(String huanxinID){
        if (huanxinID==null){
            return null;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(ContactTable.TAB_NAME, null,"hxid=?", new String[]{huanxinID}, null, null, null);
        if (cursor.moveToNext()){
            userinfo=new UserInfo();
            userinfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userinfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
        }
        cursor.close();
        return userinfo;
    }

    //通过集合环信id获取联系人信息
    public List<UserInfo> getContactByIds(List<String> huanxinID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (huanxinID==null||huanxinID.size()<=0){
            return null;
        }
        List<UserInfo> users =new ArrayList<>();
            for (String id:huanxinID){
//                Cursor cursor = db.query(ContactTable.TAB_NAME, null, "hxid=?", new String[]{id}, null, null, null);
//                while (cursor.moveToNext()){
//                    userinfo=new UserInfo();
//                    userinfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
//                    userinfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
//                    users.add(userinfo);
                UserInfo contactById = getContactById(id);
                users.add(contactById);
            }
        return users;
    }

    //保存单个联系人
    public  void saveContact(UserInfo userinfo,boolean isMyContact){
        if (userinfo==null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(ContactTable.COL_NAME,userinfo.getName());
        value.put(ContactTable.COL_HXID,userinfo.getHxid());
        value.put(ContactTable.COL_IS_CONTACT,isMyContact?1:0);
        db.replace(ContactTable.TAB_NAME,null,value);

    }
    public  void saveContacts(List<UserInfo> userinfos,boolean isMyContact){
        if (userinfos==null||userinfos.size()<=0){
            return;
        }

        for (UserInfo user:userinfos){
           saveContact(user,isMyContact);
        }
    }
    //删除联系人信息
    public void deleteContact(String hxid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ContactTable.TAB_NAME,"hxid=?",new String[]{hxid});
    }


}
