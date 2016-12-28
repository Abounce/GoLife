package com.example.administrator.golife.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yhy on 2016/12/28.
 */

//单列模式
public class spUtils {
    //是否有新的消息
    public static final String IS_NEW_INVITE = "is_new_invite";

    private static spUtils spUtils= new spUtils();
    private static  SharedPreferences sp;

    private spUtils() {
    }
    public static spUtils getInstance(){
        if (sp==null){

            sp = MyApplication.getContext().getSharedPreferences("im", Context.MODE_PRIVATE);
        }
        return spUtils;
    }
    public void save(String key,Object value){
        if (value instanceof String){
            sp.edit().putString(key, (String) value).apply();
        }else if (value instanceof Boolean){
            sp.edit().putBoolean(key, (Boolean) value).apply();
        }else  if (value instanceof Integer){
            sp.edit().putInt(key, (Integer) value).apply();
        }
    }
    public String getstring(String key,String defValue){
      return   sp.getString(key,defValue);
    }
    public Boolean getboolean(String key,Boolean defValue){
        return sp.getBoolean(key,defValue);
    }
    public int getint(String key,int defValue){
        return sp.getInt(key,defValue);
    }


}
