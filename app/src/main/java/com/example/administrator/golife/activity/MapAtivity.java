package com.example.administrator.golife.activity;

import android.os.Bundle;

import com.example.administrator.golife.R;

import butterknife.ButterKnife;

public class MapAtivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_ativity);
        ButterKnife.bind(this);
    }
}
