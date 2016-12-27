package com.example.administrator.golife.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.golife.dao.ContactTable;
import com.example.administrator.golife.dao.InviteTable;

/**
 * Created by Administrator on 2016/12/27.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(ContactTable.CREATE_TAB);
            db.execSQL(InviteTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
