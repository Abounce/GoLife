package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.administrator.golife.R;
import com.example.administrator.golife.fragment.homechatfragment.ChatFragment;
import com.example.administrator.golife.fragment.homechatfragment.ContactFragment;
import com.example.administrator.golife.fragment.homechatfragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatHomeActivity extends AppCompatActivity {


    @BindView(R.id.homechat_rg)
    RadioGroup homechatRg;

    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        homechatRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.homechat_huihua:
                        fragment=new ChatFragment();
                        break;
                    case R.id.homechat_lianxiren:
                        fragment=new ContactFragment();
                        break;
                    case R.id.homechat_shezhi:
                        fragment=new SettingFragment();
                        break;
                }
                swithFragment(fragment);

            }
        });
        homechatRg.check(R.id.homechat_huihua);
    }

    private void swithFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.homechat_fl,fragment);
        ft.commit();

    }
}
