package com.example.administrator.golife.fragment.homechatfragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.AddContactActivity;
import com.hyphenate.easeui.ui.EaseContactListFragment;

/**
 * Created by Administrator on 2016/12/27.
 */
public class ContactFragment extends EaseContactListFragment {
    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.add);

        //添加头布局
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_contact_head, null);
        listView.addHeaderView(head);
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
    }
}
