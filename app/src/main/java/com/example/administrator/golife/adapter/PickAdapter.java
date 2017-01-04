package com.example.administrator.golife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.PickContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
*选择联系人的适配器
*@author yhy
*@time 2017/1/3 9:30
*/
public class PickAdapter extends BaseAdapter{
    List<PickContactInfo> pickContactInfos;
    List<String> mExistMembers=new ArrayList<>();
    public PickAdapter(List<PickContactInfo> pickContactInfos, List<String> ExistMembers) {

        this.pickContactInfos=pickContactInfos;
        mExistMembers.clear();
        mExistMembers.addAll(ExistMembers);
    }

    @Override
    public int getCount() {
        return pickContactInfos==null?0:pickContactInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // 创建或获取viewHolder
        ViewHolder holder  = null;

        if(convertView == null) {
            holder = new ViewHolder();

        //    convertView = View.inflate(mContext, R.layout.item_pick, null);
          convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick,parent,false);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb_pick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_pick_name);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取当前item数据
        PickContactInfo pickContactInfo = pickContactInfos.get(i);

        // 显示数据
        holder.tv_name.setText(pickContactInfo.getUserInfo().getName());
        holder.cb.setChecked(pickContactInfo.isCheaked());

        // 判断
        if(mExistMembers.contains(pickContactInfo.getUserInfo().getHxid())) {
            holder.cb.setChecked(true);
            pickContactInfo.setCheaked(true);
        }

        return convertView;

    }
    private class ViewHolder{
        private CheckBox cb;
        private TextView tv_name;
    }
    // 获取选择的联系人
    public List<String> getPickContacts() {

        List<String> picks = new ArrayList<>();

        for (PickContactInfo pick: pickContactInfos){

            // 判断是否选中
            if(pick.isCheaked()) {
                picks.add(pick.getUserInfo().getName());
            }
        }

        return picks;
    }
}
