package com.example.administrator.golife.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.golife.bean.GroupInfo;
import com.example.administrator.golife.bean.InvationInfo;
import com.example.administrator.golife.bean.UserInfo;
import com.example.administrator.golife.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhy on 2016/9/24.
 */
// 邀请信息表的操作类
public class InviteTableDao {
    private DBHelper mHelper;

    public InviteTableDao(DBHelper helper) {
        mHelper = helper;
    }

    // 添加邀请
    public void addInvitation(InvationInfo invitationInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(InviteTable.COL_REASON,invitationInfo.getReason());
        value.put(InviteTable.COL_STATUS,invitationInfo.getStatus().ordinal());
        UserInfo userInfo = invitationInfo.getUserInfo();
        if (userInfo!=null){
            //联系人信息
            value.put(InviteTable.COL_USER_HXID,userInfo.getHxid());
            value.put(InviteTable.COL_USER_NAME,userInfo.getName());
        }else {
            //群主信息
            value.put(InviteTable.COL_GROUP_HXID,invitationInfo.getGroup().getGroupId());
            value.put(InviteTable.COL_GROUP_NAME,invitationInfo.getGroup().getGroupName());
            //主键不能为空，否则报错
            value.put(InviteTable.COL_USER_HXID,invitationInfo.getGroup().getInvatePerson());

        }
        db.replace(InviteTable.TAB_NAME,null,value);


    }

    // 获取所有邀请信息
    public List<InvationInfo> getInvitations() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(InviteTable.TAB_NAME, null, null, null, null, null, null);
        List<InvationInfo> invationInfos=new ArrayList<>();
        while (cursor.moveToNext()){
            InvationInfo info=new InvationInfo();
            info.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
            info.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));
            String goupid = cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID));
            if (goupid==null){
                //联系人信息
                UserInfo userinfo=new UserInfo();
                userinfo.setHxid(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                userinfo.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                info.setUserInfo(userinfo);
            }else {
                //群主信息
                GroupInfo groupinfo=new GroupInfo();
                groupinfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID)));
                groupinfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                groupinfo.setInvatePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                info.setGroup(groupinfo);

            }
            invationInfos.add(info);

        }
        cursor.close();
        return invationInfos;
    }

    // 将int类型状态转换为邀请的状态
    private InvationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;
    }

    // 删除邀请
    public void removeInvitation(String hxId) {
        if (hxId == null) {
            return;
        }

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行删除语句
        db.delete(InviteTable.TAB_NAME, InviteTable.COL_USER_HXID + "=?", new String[]{hxId});
    }

    // 更新邀请状态
    public void updateInvitationStatus(InvationInfo.InvitationStatus invitationStatus, String hxId) {
        if (hxId == null) {
            return;
        }

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        // 执行更新操作
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_STATUS, invitationStatus.ordinal());

        db.update(InviteTable.TAB_NAME, values, InviteTable.COL_USER_HXID + "=?", new String[]{hxId});
    }

}
