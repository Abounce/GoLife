package com.example.administrator.golife.fragment.homechatfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.AddContactActivity;
import com.example.administrator.golife.activity.ChatActivity;
import com.example.administrator.golife.activity.GropListActivity;
import com.example.administrator.golife.activity.InviteActivity;
import com.example.administrator.golife.bean.UserInfo;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.Modle;
import com.example.administrator.golife.util.spUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhy on 2016/12/27.
 */
public class ContactFragment extends EaseContactListFragment {
    private ImageView contact_red;
    private LocalBroadcastManager LBM;
    private BroadcastReceiver ContactInviteChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //红点显示
            contact_red.setVisibility(View.VISIBLE);
            //更新红点的状态
            spUtils.getInstance().save(spUtils.IS_NEW_INVITE,true);
        }
    };
    private IntentFilter intentFilter;
    private BroadcastReceiver ContactChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshContact();
        }
    };
    private String mHxid;


    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.add);

        //添加头布局
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_contact_head, null);
        listView.addHeaderView(head);
        contact_red = (ImageView)head.findViewById(R.id.iv_contact_red);

        //获取邀请信息item
        LinearLayout ll_contact_invite = (LinearLayout) head.findViewById(R.id.ll_contact_invite);
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把红点隐藏掉

                contact_red.setVisibility(View.GONE);
                //更新红点的状态
                spUtils.getInstance().save(spUtils.IS_NEW_INVITE,false);
                Intent intent=new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);
            }
        });
        //设置listview的点击事件
        //因为EaseContactListFragment封装好了 我直接调用当前的的就行了
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser easeUser) {
                if (easeUser==null){
                    return;
                }
               Intent intent=new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,easeUser.getUsername());
                startActivity(intent);
            }
        });
       LinearLayout ll_contact_group= (LinearLayout) head.findViewById(R.id.ll_contact_group);
        ll_contact_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(getActivity(), GropListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        //初始化红点
        Boolean redboolean = spUtils.getInstance().getboolean(spUtils.IS_NEW_INVITE, false);
        contact_red.setVisibility(redboolean?View.VISIBLE:View.GONE);

        //注册广播
         LBM=LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter(Config.CONTACT_INVITE_CHANGE);
        LBM.registerReceiver(ContactInviteChangeReceiver,intentFilter );

        LBM.registerReceiver(ContactChangeReceiver,new IntentFilter(Config.CONTACT_CHANGE));
        //从环信服务器获取最新的联系人信息，并展示在此界面上
        getContactFromHXService();

        //绑定listview和contextmenu
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

        EaseUser itemAtPosition = (EaseUser) listView.getItemAtPosition(position);
         mHxid = itemAtPosition.getUsername();

        getActivity().getMenuInflater().inflate(R.menu.contact,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_delete:
                //删除选中联系人
                deleteContact();
                return  true;


        }
        return super.onContextItemSelected(item);
    }

    private void deleteContact() {
        //去服务器删除
        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(mHxid);
                    //本地数据库更新
                    Modle.getInStance().getDBManager().getContactTableDao().deleteContact(mHxid);
                    if (getActivity()==null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshContact();
                            Toast.makeText(getActivity(), "删除好友"+mHxid+"成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    if (getActivity()==null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshContact();
                            Toast.makeText(getActivity(), "删除好友"+mHxid+"失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getContactFromHXService() {
        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> huanid = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (huanid!=null&&huanid.size()>=0){
                        List<UserInfo> userinfos=new ArrayList<UserInfo>();
                        //保存到本地数据库
                        for (String id:huanid){
                        UserInfo userinfo=new UserInfo(id);
                        userinfos.add(userinfo);
                        }
                        Modle.getInStance().getDBManager().getContactTableDao().saveContacts(userinfos,true);
                        //刷新页面
                        if (getActivity()==null){
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshContact();
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void refreshContact() {
        //获取数据
        List<UserInfo> contacts = Modle.getInStance().getDBManager().getContactTableDao().getContacts();
        if (contacts!=null&&contacts.size()>=0){
            Map<String,EaseUser> contactmap =new HashMap<>();
            for (UserInfo contact:contacts){

            contactmap.put(contact.getHxid(),new EaseUser(contact.getHxid()));
            }
            setContactsMap(contactmap);
            refresh();

        }
    }

    @Override
    public void onDestroy() {
        LBM.unregisterReceiver(ContactInviteChangeReceiver);
        LBM.unregisterReceiver(ContactChangeReceiver);
        super.onDestroy();
    }



}
