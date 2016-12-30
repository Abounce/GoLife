package com.example.administrator.golife.fragment.homechatfragment;

import android.content.Intent;

import com.example.administrator.golife.activity.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * Created by yhy on 2016/12/27.
 */
public class ChatFragment extends EaseConversationListFragment {
    private EMMessageListener EMMessagelisttner=new EMMessageListener() {
        //接受到消息的变化回调
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            EaseUI.getInstance().getNotifier().onNewMesg(list);
            //刷新页面
            refresh();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    @Override
    protected void initView() {
        super.initView();
        //添加item的点击事件
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation emConversation) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,emConversation.conversationId());
                //是否是群聊
                if (emConversation.getType()== EMConversation.EMConversationType.GroupChat){
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                }
                startActivity(intent);
            }
        });
        conversationList.clear();
        EMClient.getInstance().chatManager().addMessageListener(EMMessagelisttner);
    }
}
