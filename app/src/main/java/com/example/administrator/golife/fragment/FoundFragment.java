package com.example.administrator.golife.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.LoginActivity;
import com.example.administrator.golife.adapter.FoundAdapter;
import com.hyphenate.chat.EMClient;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
public class FoundFragment extends BaseFragment {
    private RecyclerView fragment_found_rv;
   private List<String> mdatas;
    private FoundAdapter adapter;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_found, null);
      //  View view=  View.inflate(mContext,R.layout.fragment_found,null);
        fragment_found_rv = (RecyclerView)view.findViewById(R.id.fragment_found_rv);

        return view;
    }

    @Override
    protected void initData() {
      mdatas=new ArrayList<>();
        mdatas.add("微聊");
        mdatas.add("定位");
        mdatas.add("天气");
       // mdatas.add("交友");

    fragment_found_rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
    fragment_found_rv.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
    adapter=new FoundAdapter(mContext,R.layout.fragment_found_item,mdatas);
    fragment_found_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:
                        DengLu();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void DengLu() {
        if (EMClient.getInstance().isLoggedInBefore()){
            //之前登陆过

        }else {
            //没有登陆过，调到注册页面
            Intent intent=new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        }
    }
}
