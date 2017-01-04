package com.example.administrator.golife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.InvationInfo;
import com.example.administrator.golife.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
public class InviteAdapter extends BaseAdapter {
    private Context context;
    private List<InvationInfo> mdatas=new ArrayList<>();
    private OnInviteListener mOnInviteListener;
    public InviteAdapter(Context context,OnInviteListener mOnInviteListener) {
        this.context=context;
        this.mOnInviteListener=mOnInviteListener;
    }
    public void refresh(List<InvationInfo> data){
        mdatas.clear();
        mdatas.addAll(data);
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return mdatas==null?0:mdatas.size();
    }

    @Override
    public InvationInfo getItem(int i) {
        return mdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Holder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_inivite_item,viewGroup,false);
            holder=new Holder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_invite_name);
            holder.reason = (TextView) convertView.findViewById(R.id.tv_invite_reason);

            holder.accept = (Button) convertView.findViewById(R.id.bt_invite_accept);
            holder.reject = (Button) convertView.findViewById(R.id.bt_invite_reject);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }

        final InvationInfo invationInfo = getItem(i);

        UserInfo user = invationInfo.getUserInfo();

        if (user!=null){
            //联系人信息
            holder.name.setText(user.getName());
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
            if (invationInfo.getStatus()==InvationInfo.InvitationStatus.NEW_INVITE){   //新的邀请
               if (invationInfo.getReason()==null){
                   holder.reason.setText("添加好友");
               }else{
                   holder.reason.setText(invationInfo.getReason());
               }
                holder.accept.setVisibility(View.VISIBLE);
                holder.reject.setVisibility(View.VISIBLE);
            }else if (invationInfo.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT) {// 接受邀请

                if (invationInfo.getReason() == null) {
                    holder.reason.setText("接受邀请");
                } else {
                    holder.reason.setText(invationInfo.getReason());
                }
            } else if (invationInfo.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {// 邀请被接受
                if (invationInfo.getReason() == null) {
                    holder.reason.setText("邀请被接受");
                } else {
                    holder.reason.setText(invationInfo.getReason());
                }
            }

            // 按钮的处理
            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onAccept(invationInfo);
                }
            });

            // 拒绝按钮的点击事件处理
            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onReject(invationInfo);
                }
            });


        }else {
            //群信息
            // 显示名称
            holder.name.setText(invationInfo.getGroup().getInvatePerson());

            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);

            // 显示原因
            switch(invationInfo.getStatus()){
                // 您的群申请请已经被接受
                case GROUP_APPLICATION_ACCEPTED:
                    holder.reason.setText("您的群申请请已经被接受");
                    break;
                //  您的群邀请已经被接收
                case GROUP_INVITE_ACCEPTED:
                    holder.reason.setText("您的群邀请已经被接收");
                    break;

                // 你的群申请已经被拒绝
                case GROUP_APPLICATION_DECLINED:
                    holder.reason.setText("你的群申请已经被拒绝");
                    break;

                // 您的群邀请已经被拒绝
                case GROUP_INVITE_DECLINED:
                    holder.reason.setText("您的群邀请已经被拒绝");
                    break;

                // 您收到了群邀请
                case NEW_GROUP_INVITE:
                    holder.accept.setVisibility(View.VISIBLE);
                    holder.reject.setVisibility(View.VISIBLE);

                    // 接受邀请
                    holder.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onInviteAccept(invationInfo);
                        }
                    });

                    // 拒绝邀请
                    holder.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onInviteReject(invationInfo);
                        }
                    });

                    holder.reason.setText("您收到了群邀请");
                    break;

                // 您收到了群申请
                case NEW_GROUP_APPLICATION:
                    holder.accept.setVisibility(View.VISIBLE);
                    holder.reject.setVisibility(View.VISIBLE);

                    // 接受申请
                    holder.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationAccept(invationInfo);
                        }
                    });

                    // 拒绝申请
                    holder.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationReject(invationInfo);
                        }
                    });

                    holder.reason.setText("您收到了群申请");
                    break;

                // 你接受了群邀请
                case GROUP_ACCEPT_INVITE:
                    holder.reason.setText("你接受了群邀请");
                    break;

                // 您批准了群申请
                case GROUP_ACCEPT_APPLICATION:
                    holder.reason.setText("您批准了群申请");
                    break;

                // 您拒绝了群邀请
                case GROUP_REJECT_INVITE:
                    holder.reason.setText("您拒绝了群邀请");
                    break;

                // 您拒绝了群申请
                case GROUP_REJECT_APPLICATION:
                    holder.reason.setText("您拒绝了群申请");
                    break;
            }
        }



        return convertView;
    }
    private static class Holder{
        private TextView name;
        private TextView reason;
        private Button accept;
        private Button reject;

    }
    public interface OnInviteListener {
        // 联系人接受按钮的点击事件
        void onAccept(InvationInfo invationInfo);

        // 联系人拒绝按钮的点击事件
        void onReject(InvationInfo invationInfo);

        // 接受邀请按钮处理
        void onInviteAccept(InvationInfo invationInfo);
        // 拒绝邀请按钮处理
        void onInviteReject(InvationInfo invationInfo);

        // 接受申请按钮处理
        void onApplicationAccept(InvationInfo invationInfo);
        // 拒绝申请按钮处理
        void onApplicationReject(InvationInfo invationInfo);
    }
}
