package com.example.administrator.golife.fragment.homechatfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.AddContactActivity;
import com.example.administrator.golife.activity.InviteActivity;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.spUtils;
import com.hyphenate.easeui.ui.EaseContactListFragment;

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
        LBM.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(Config.CONTACT_INVITE_CHANGE));
    }
}
