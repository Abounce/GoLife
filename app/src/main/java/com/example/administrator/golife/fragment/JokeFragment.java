package com.example.administrator.golife.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/8.
 */
public class JokeFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected View initView() {
        TextView tv =new TextView(mContext);
        tv.setText("JokeFragment");
        return tv;
    }
}
