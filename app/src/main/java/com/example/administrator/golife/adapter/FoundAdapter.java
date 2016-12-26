package com.example.administrator.golife.adapter;

import android.content.Context;

import com.example.administrator.golife.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */
public class FoundAdapter extends CommonAdapter<String> {

    public FoundAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.fragment_found_tv,s);
    }
}
