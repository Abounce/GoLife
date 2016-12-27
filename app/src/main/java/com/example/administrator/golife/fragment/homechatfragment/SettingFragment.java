package com.example.administrator.golife.fragment.homechatfragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.LoginActivity;
import com.example.administrator.golife.fragment.BaseFragment;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2016/12/27.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private Button bt_setting_out;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_setting, null);
        bt_setting_out = (Button)view.findViewById(R.id.bt_setting_out);

        return view;
    }

    @Override
    protected void initData() {
        bt_setting_out.setText("退出登录（" + EMClient.getInstance().getCurrentUser() + ")");
       bt_setting_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_setting_out:
                Modle.getInStance().getExecutorService().execute(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "退出成功", Toast.LENGTH_SHORT).show();
                                        // 回到登录页面
                                        Intent intent = new Intent(mContext, LoginActivity.class);

                                        startActivity(intent);

                                        getActivity().finish();
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, final String s) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "退出失败"+s, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                    }
                });
                break;
        }

    }
}
