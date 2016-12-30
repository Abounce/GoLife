package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.golife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickContactActivity extends AppCompatActivity {

    @BindView(R.id.tv_pick_save)
    TextView tvPickSave;
    @BindView(R.id.lv_pick)
    ListView lvPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
        ButterKnife.bind(this);

    }
}
