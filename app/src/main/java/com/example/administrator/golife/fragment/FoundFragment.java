package com.example.administrator.golife.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.ChatHomeActivity;
import com.example.administrator.golife.activity.LoginActivity;
import com.example.administrator.golife.activity.MapAtivity;
import com.example.administrator.golife.adapter.FoundAdapter;
import com.example.administrator.golife.table.User;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.chat.EMClient;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhy on 2016/12/8.
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
    //

      mdatas=new ArrayList<>();
        mdatas.add("微聊");
//        mdatas.add("定位");
//        mdatas.add("天气");
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
//                    case 1:
//                         Map();
//                        break;
//                    case 2:
//                        break;

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void Map() {
        startActivity(new Intent(mContext, MapAtivity.class));
    }

    private void DengLu() {



        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

        if (EMClient.getInstance().isLoggedInBefore()){
            //之前登陆过
       User muser=null;
           List<User> users = DataSupport.where("hxid=?", EMClient.getInstance().getCurrentUser()).find(User.class);
            for (User user:users){
                if (user.getHxid().equals(EMClient.getInstance().getCurrentUser())){
                    muser=user;
                }
            }
            if (muser==null){
                Intent intent=new Intent(mContext, LoginActivity.class);
              startActivity(intent);
           }else {
                //效验
                Modle.getInStance().logingSucess(muser.getName());
                // 跳转到主页面
                Intent intent = new Intent(mContext, ChatHomeActivity.class);
                startActivity(intent);
         }

        }else {
            //没有登陆过，调到注册页面
            Intent intent=new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        }
    //  getActivity().finish();
            }
        });

    }
}
