package com.example.administrator.golife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.golife.bean.InvationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
public class InviteAdapter extends BaseAdapter {
    private Context context;
    private List<InvationInfo> mdatas=new ArrayList<>();
    public InviteAdapter(Context context) {
        this.context=context;
    }
    public void refresh(List<InvationInfo> data){
        mdatas.addAll(data);
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return mdatas==null?0:mdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
