package com.example.administrator.golife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.golife.R;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhy on 2016/12/30.
 */
public class GroupAdapter extends BaseAdapter {
    List<EMGroup> emGroups=new ArrayList<>();
    @Override
    public int getCount() {
        return emGroups==null?0:emGroups.size();
    }

    @Override
    public Object getItem(int i) {
        return emGroups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converView, ViewGroup parent) {
        Holder holder=null;
        if (converView==null){
            holder=new Holder();
            converView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouplist,parent,false);
            holder.name= (TextView) converView.findViewById(R.id.tv_grouplist_name);
            converView.setTag(holder);
        }else {
            holder= (Holder) converView.getTag();
        }
        holder.name.setText(emGroups.get(i).getGroupName());
        return converView;
    }
    private class Holder{
        private TextView name;
    }

   public void refresh( List<EMGroup> emGroup){
       if (emGroup!=null&&emGroup.size()>=0){
           emGroups.clear();
           emGroups.addAll(emGroup);
           notifyDataSetChanged();
       }

   }
}
