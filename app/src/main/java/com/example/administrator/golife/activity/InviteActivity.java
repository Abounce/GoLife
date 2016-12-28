package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.administrator.golife.R;
import com.example.administrator.golife.adapter.InviteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends AppCompatActivity {

    @BindView(R.id.invite_lv)
    ListView inviteLv;
    private InviteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        adapter=new InviteAdapter(this);
        inviteLv.setAdapter(adapter);
    }


}
