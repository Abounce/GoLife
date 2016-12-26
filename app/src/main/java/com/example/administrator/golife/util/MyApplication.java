package com.example.administrator.golife.util;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
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

        //初始化EASEUI
        EMOptions options = new EMOptions();
       // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //默认添加群时，是不需要验证的，改成需要验证
        options.setAutoAcceptGroupInvitation(false);
        EaseUI.getInstance().init(this, options);
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
        //初始化modle
        Modle.getInStance().init(this);
    }
   public static Context getContext(){
     return context;
    }
}
