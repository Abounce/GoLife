package com.example.administrator.golife.adapter;

import android.content.Context;

import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.JokeBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yhy on 2016/12/22.
 */
public class JokeTextAdapter extends CommonAdapter<JokeBean.ResultBean.DataBean> {


    public JokeTextAdapter(Context context, int layoutId, List<JokeBean.ResultBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, JokeBean.ResultBean.DataBean dataBean, int position) {
        holder.setText(R.id.joke_tv,dataBean.getContent());
        holder.setText(R.id.joke_time,dataBean.getUpdatetime());
    }
}
