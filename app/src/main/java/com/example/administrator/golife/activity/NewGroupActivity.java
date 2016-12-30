package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.administrator.golife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGroupActivity extends AppCompatActivity {

    @BindView(R.id.et_newgroup_name)
    EditText etNewgroupName;
    @BindView(R.id.et_newgroup_desc)
    EditText etNewgroupDesc;
    @BindView(R.id.cb_newgroup_public)
    CheckBox cbNewgroupPublic;
    @BindView(R.id.cb_newgroup_invite)
    CheckBox cbNewgroupInvite;
    @BindView(R.id.bt_newgroup_create)
    Button btNewgroupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);
    }
}
