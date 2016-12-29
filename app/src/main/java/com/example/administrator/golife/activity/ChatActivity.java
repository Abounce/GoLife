package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.golife.R;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
*
*@author yhy
*created at 2016/12/29
*/
public class ChatActivity extends AppCompatActivity {

    private String mHxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();

    }

    private void initData() {
        EaseChatFragment easechatfragment=new EaseChatFragment();
        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        easechatfragment.setArguments(getIntent().getExtras());
        //替换fragement
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.chat_fl,easechatfragment);
        ft.commit();
    }


}
