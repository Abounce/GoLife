package com.example.administrator.golife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.adapter.GroupAdapter;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

public class GropListActivity extends AppCompatActivity {

    private ListView grop_lv;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grop_list);
        initView();
        initData();
    }

    private void initView() {
        grop_lv= (ListView) findViewById(R.id.grop_lv);
        //item的点击事件
        grop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    return;
                }
                Intent intent=new Intent(GropListActivity.this,ChatActivity.class);

              intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(i - 1);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,emGroup.getGroupId());
                startActivity(intent);
            }
        });
        View head = LayoutInflater.from(this).inflate(R.layout.header_grouplist, null);
        grop_lv.addHeaderView(head);
        LinearLayout ll_grouplist= (LinearLayout) head.findViewById(R.id.ll_grouplist);
        ll_grouplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //跳转到新建群
                Intent intent =new Intent(GropListActivity.this,NewGroupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        adapter = new GroupAdapter();
        grop_lv.setAdapter(adapter);
        getGropFromService();

    }

    private void getGropFromService() {
        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取环信服务器上保存的数据
                    final List<EMGroup> groups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    //更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GropListActivity.this, "加载群信息成功", Toast.LENGTH_SHORT).show();
                            //adapter.refresh(groups);
                            //从网络上拿到了，保存到了本地SDK 中  不需要 重新创建数据库
                           refresh();

                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GropListActivity.this, "加载群信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void refresh() {
        adapter.refresh(EMClient.getInstance().groupManager().getAllGroups());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}
