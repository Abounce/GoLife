package com.example.administrator.golife.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.adapter.InviteAdapter;
import com.example.administrator.golife.bean.InvationInfo;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends AppCompatActivity {

    @BindView(R.id.invite_lv)
    ListView inviteLv;
    private InviteAdapter adapter;
    private LocalBroadcastManager mLBM;
    private InviteAdapter.OnInviteListener mOnInviteListener =new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(final InvationInfo invationInfo) {
             //通知环信服务器接收添加好友
            Modle.getInStance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invationInfo.getUserInfo().getHxid());

                        //本地数据库更新
                        Modle.getInStance().getDBManager().getInviteTableDao().updateInvitationStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT,invationInfo.getUserInfo().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 页面发生变化
                                Toast.makeText(InviteActivity.this, "接受了邀请", Toast.LENGTH_SHORT).show();

                                // 刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受邀请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onReject(final InvationInfo invationInfo) {
            //通知环信服务器拒绝添加好友
            Modle.getInStance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(invationInfo.getUserInfo().getHxid());
                        //本地数据库更新
                        Modle.getInStance().getDBManager().getInviteTableDao().removeInvitation(invationInfo.getUserInfo().getHxid());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝成功了", Toast.LENGTH_SHORT).show();

                                // 刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝失败了", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onInviteAccept(final InvationInfo invationInfo) {
            Modle.getInStance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 告诉环信服务器接受了邀请
                        EMClient.getInstance().groupManager().acceptInvitation(invationInfo.getGroup().getGroupId(), invationInfo.getGroup().getInvatePerson());

                        // 本地数据更新
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE);
                        Modle.getInStance().getDBManager().getInviteTableDao().addInvitation(invationInfo);

                        // 内存数据的变化
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受邀请", Toast.LENGTH_SHORT).show();

                                // 刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受邀请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onInviteReject(final InvationInfo invationInfo) {
            Modle.getInStance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 告诉环信服务器拒绝了邀请
                        EMClient.getInstance().groupManager().declineInvitation(invationInfo.getGroup().getGroupId(), invationInfo.getGroup().getInvatePerson(),"拒绝邀请");

                        // 更新本地数据库
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_REJECT_INVITE);
                        Modle.getInStance().getDBManager().getInviteTableDao().addInvitation(invationInfo);

                        // 更新内存的数据
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝邀请", Toast.LENGTH_SHORT).show();

                                // 刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onApplicationAccept(final InvationInfo invationInfo) {
            Modle.getInStance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 告诉环信服务器接受了申请
                        EMClient.getInstance().groupManager().acceptApplication(invationInfo.getGroup().getGroupId(), invationInfo.getGroup().getInvatePerson());

                        // 更新数据库
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION);
                        Modle.getInStance().getDBManager().getInviteTableDao().addInvitation(invationInfo);

                        // 更新内存
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受申请", Toast.LENGTH_SHORT).show();

                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onApplicationReject(final InvationInfo invationInfo) {
            Modle.getInStance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 告诉环信服务器拒绝了申请
                        EMClient.getInstance().groupManager().declineApplication(invationInfo.getGroup().getGroupId(),invationInfo.getGroup().getInvatePerson(),"拒绝申请");

                        // 更新本地数据库
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION);
                        Modle.getInStance().getDBManager().getInviteTableDao().addInvitation(invationInfo);

                        // 更新内存
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝申请", Toast.LENGTH_SHORT).show();

                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝申请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        adapter=new InviteAdapter(this,mOnInviteListener);
        inviteLv.setAdapter(adapter);
        // 刷新方法
        refresh();
        // 注册邀请信息变化的广播
        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(InviteChangedReceiver, new IntentFilter(Config.CONTACT_INVITE_CHANGE));
        mLBM.registerReceiver(InviteChangedReceiver, new IntentFilter(Config.GROUP_INVITE_CHANGED));
    }
    private BroadcastReceiver InviteChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // 刷新页面
            refresh();
        }
    };


    private void refresh() {
        List<InvationInfo> invitations = Modle.getInStance().getDBManager().getInviteTableDao().getInvitations();
        adapter.refresh(invitations);
    }
    @Override
    protected void onDestroy() {
        mLBM.unregisterReceiver(InviteChangedReceiver);
        super.onDestroy();

    }

}
