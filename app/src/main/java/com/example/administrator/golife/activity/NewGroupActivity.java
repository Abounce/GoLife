package com.example.administrator.golife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGroupActivity extends AppCompatActivity {

    @BindView(R.id.et_newgroup_name)
    EditText etNewgroupName;
    @BindView(R.id.et_newgroup_desc)
    EditText etNewgroupDesc;
    @BindView(R.id.cb_newgroup_public)
    CheckBox cbNewgroupPublic;
    @BindView(R.id.cb_newgroup_invite)
    CheckBox cbNewgroupInvite;
    @BindView(R.id.bt_newgroup_create)
    Button btNewgroupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);
        initdata();
    }

    private void initdata() {
        btNewgroupCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(NewGroupActivity.this,PickContactActivity.class);
                startActivityForResult(intent,1);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode==RESULT_OK){
                    //创建群
                    createGroup(data.getStringArrayExtra("group"));
                }
                break;
        }
    }

    private void createGroup(final String[] groups) {
        // 群名称
        final String groupName = etNewgroupName.getText().toString();
        // 群描述
        final String groupDesc = etNewgroupDesc.getText().toString();
        //去环信服务器上创建群
        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器创建群
                // 参数一：群名称；参数二：群描述；参数三：群成员；参数四：原因；参数五：参数设置
                EMGroupManager.EMGroupOptions options = new EMGroupManager.EMGroupOptions();

                options.maxUsers = 200;//群最多容纳多少人
                EMGroupManager.EMGroupStyle groupStyle = null;

                if (cbNewgroupPublic.isChecked()) {//公开
                    if (cbNewgroupInvite.isChecked()) {// 开放群邀请
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                    } else {
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
                    }
                } else {
                    if (cbNewgroupInvite.isChecked()) {// 开放群邀请
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                    } else {
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                    }
                }

                options.style = groupStyle; // 创建群的类型

                try {
                    EMClient.getInstance().groupManager().createGroup(groupName, groupDesc, groups, "申请加入群", options);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewGroupActivity.this, "创建群成功", Toast.LENGTH_SHORT).show();

                            // 结束当前页面
                            finish();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewGroupActivity.this, "创建群失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
