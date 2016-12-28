package com.example.administrator.golife.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.administrator.golife.bean.InvationInfo;
import com.example.administrator.golife.bean.UserInfo;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by yhy on 2016/12/28.
 */
public class EventListener {
    private final LocalBroadcastManager mLBM;
    private Context context;
    public EventListener(Context context) {
        this.context=context;
        // 创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(context);
        //注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }
      private   EMContactListener emContactListener=new EMContactListener() {
           //联系人增加后执行的方法
            @Override
            public void onContactAdded(String hxid) {
             //数据库更新
                Modle.getInStance().getDBManager().getContactTableDao().saveContact(new UserInfo(hxid),true);
              //发送联系人广播
                mLBM.sendBroadcast(new Intent(Config.CONTACT_CHANGE));

            }
             //联系人删除后执行的方法
            @Override
            public void onContactDeleted(String hxid) {
                //数据库更新
                Modle.getInStance().getDBManager().getContactTableDao().deleteContact(hxid);
                //同时也要把邀请信息删除了
                Modle.getInStance().getDBManager().getInviteTableDao().removeInvitation(hxid);

                //发送联系人广播
                mLBM.sendBroadcast(new Intent(Config.CONTACT_CHANGE));

            }
         //接受到联系人的新邀请
            @Override
            public void onContactInvited(String hxid, String reason) {
                //数据库更新
                InvationInfo invitationinfo =new InvationInfo();
                invitationinfo.setUserInfo(new UserInfo(hxid));
                invitationinfo.setReason(reason);
                invitationinfo.setStatus(InvationInfo.InvitationStatus.NEW_INVITE);
                Modle.getInStance().getDBManager().getInviteTableDao().addInvitation(invitationinfo);
                //红点处理
                spUtils.getInstance().save(spUtils.IS_NEW_INVITE,true);
                //发送邀请信息变化
                mLBM.sendBroadcast(new Intent(Config.CONTACT_INVITE_CHANGE));
            }
         // 别人同意了你的好友邀请
            @Override
            public void onContactAgreed(String hxid) {
                //数据库更新
                InvationInfo invitationinfo =new InvationInfo();
                invitationinfo.setUserInfo(new UserInfo(hxid));
                invitationinfo.setStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
                Modle.getInStance().getDBManager().getInviteTableDao().addInvitation(invitationinfo);
                //红点处理
                spUtils.getInstance().save(spUtils.IS_NEW_INVITE,true);
                //发送邀请信息变化
                mLBM.sendBroadcast(new Intent(Config.CONTACT_INVITE_CHANGE));
            }
          //别人拒绝了你的好友邀请
            @Override
            public void onContactRefused(String s) {
// 红点的处理
                spUtils.getInstance().save(spUtils.IS_NEW_INVITE, true);

                // 发送邀请信息变化的广播
                mLBM.sendBroadcast(new Intent(Config.CONTACT_INVITE_CHANGE));
            }
        };

}
