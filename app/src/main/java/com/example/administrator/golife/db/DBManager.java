package com.example.administrator.golife.db;

import android.content.Context;

import com.example.administrator.golife.dao.ContactTableDao;
import com.example.administrator.golife.dao.InviteTableDao;

/**
 * Created by yhy on 2016/12/28.
 */

//保证两张表操作的是同一个数据库，传入的dbhelper 要一样
public class DBManager {
    private final DBHelper dbHelper;
    private final InviteTableDao inviteTableDao;
    private final ContactTableDao contactTableDao;

    public DBManager(Context context, String name) {
       dbHelper = new DBHelper(context, name);
        inviteTableDao = new InviteTableDao(dbHelper);
        contactTableDao = new ContactTableDao(dbHelper);
    }
    public  ContactTableDao getContactTableDao(){
        return contactTableDao;
    }
    public  InviteTableDao getInviteTableDao(){
        return  inviteTableDao;
    }

    public void close() {
        dbHelper.close();
    }
}
