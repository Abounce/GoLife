package com.example.administrator.golife.util;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/12/8.
 */
public class MyApplication extends Application {
    private static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
       context= getApplicationContext();

        OkHttpUtils.initClient(okHttpClient);
        Logger.init("TAG");

//        com.wanjian.sak.LayoutManager.init()
     //   LayoutManager.init(this);
    }
   public static Context getContext(){
     return context;
    }
}
