package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.golife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.tv_add_find) //查找
    TextView tvAddFind;
    @BindView(R.id.et_add_name) //编辑名字
    EditText etAddName;
    @BindView(R.id.iv_add_photo) //图片
    ImageView ivAddPhoto;
    @BindView(R.id.tv_add_name)  //名称
    TextView tvAddName;
    @BindView(R.id.bt_add_add)   //添加
    Button btAddAdd;
    @BindView(R.id.rl_add)      //显示或者隐藏
    RelativeLayout rlAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
    }
}
