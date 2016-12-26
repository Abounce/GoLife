package com.example.administrator.golife.util;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/26.
 */
public class Modle {
    private Context context;
    private static  Modle modle=new Modle();
    private ExecutorService executorService= Executors.newCachedThreadPool();
    private Modle() {
    }
    public  static Modle getInStance(){
        return modle;
    }
    public void init(Context context){
        this.context=context;
    }
    public ExecutorService getExecutorService(){

        return executorService;

    }
}
