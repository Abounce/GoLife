package com.example.administrator.golife.util;

import android.content.Context;

import com.example.administrator.golife.db.DBManager;

import org.litepal.tablemanager.Connector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yhy on 2016/12/26.
 */
public class Modle {
    private Context context;
    private static  Modle modle=new Modle();
    private ExecutorService executorService= Executors.newCachedThreadPool();
    private DBManager dbManager;

    private Modle() {
    }
    public  static Modle getInStance(){
        return modle;
    }
    public void init(Context context){
        this.context=context;
        Connector.getDatabase();
        EventListener eventListener = new EventListener(context);

    }
    public ExecutorService getExecutorService(){

        return executorService;

    }
    public  void logingSucess(String name){
        if (dbManager!=null){
            dbManager.close();
        }
       dbManager = new DBManager(context, name);

    }
    public DBManager getDBManager(){
        return  dbManager;
    }
}
