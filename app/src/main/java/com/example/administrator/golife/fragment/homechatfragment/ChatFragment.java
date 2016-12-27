package com.example.administrator.golife.fragment.homechatfragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.golife.fragment.BaseFragment;

/**
 * Created by yhy on 2016/12/27.
 */
public class ChatFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected View initView() {
        TextView tv=new TextView(mContext);
        tv.setText("adfadf");
        return tv;
    }
}
