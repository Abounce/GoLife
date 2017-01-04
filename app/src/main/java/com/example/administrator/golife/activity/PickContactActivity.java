package com.example.administrator.golife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.golife.R;
import com.example.administrator.golife.adapter.PickAdapter;
import com.example.administrator.golife.bean.PickContactInfo;
import com.example.administrator.golife.bean.UserInfo;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
*选择联系人的适配器
*@author yhy
*@time 2017/1/3 9:29
*/
public class PickContactActivity extends AppCompatActivity {

    @BindView(R.id.tv_pick_save)
    TextView tvPickSave;
    @BindView(R.id.lv_pick)
    ListView lvPick;
    private List<PickContactInfo> pickContactInfos;
    private PickAdapter pickAdapter;
    private List<String> mExistMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
        ButterKnife.bind(this);
        // 获取传递过来的数据
        getData();
        lvPick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox cheak = (CheckBox) view.findViewById(R.id.cb_pick);
                cheak.setClickable(!cheak.isChecked());
                //修改当前item数据
                PickContactInfo pickContactInfo = pickContactInfos.get(i);
                pickContactInfo.setCheaked(cheak.isChecked());
                //      Toast.makeText(PickContactActivity.this, "aaaaaaa", Toast.LENGTH_SHORT).show();
                //刷新适配器
                pickAdapter.notifyDataSetChanged();
            }
        });
        tvPickSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> pickContacts = pickAdapter.getPickContacts();
                //返还数据给启动页面
                Intent intent=new Intent();
                intent.putExtra("group",pickContacts.toArray(new String[0]));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        initData();
    }

    private void getData() {
        String groupId = getIntent().getStringExtra(Config.GROUP_ID);

        if (groupId != null) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
            // 获取群众已经存在的所有群成员
            mExistMembers = group.getMembers();
        }

        if (mExistMembers == null) {
            mExistMembers = new ArrayList<>();
        }
    }

    private void initData() {
        //从数据库中获取联系人信息
        List<UserInfo> contacts = Modle.getInStance().getDBManager().getContactTableDao().getContacts();
        pickContactInfos = new ArrayList<>();
        for (UserInfo contact:contacts
             ) {

            PickContactInfo pickContactInfo = new PickContactInfo(contact, false);
            pickContactInfos.add(pickContactInfo);

        }
        pickAdapter = new PickAdapter(pickContactInfos,mExistMembers);
        lvPick.setAdapter(pickAdapter);

    }
}
