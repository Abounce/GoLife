package com.example.administrator.golife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.table.User;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.text_name)
    TextInputLayout textName;
    @BindView(R.id.text_mima)
    TextInputLayout textMima;
    @BindView(R.id.bt_login_regist)
    Button btLoginRegist;
    @BindView(R.id.bt_login_login)
    Button btLoginLogin;
    private String name;
    private String pasword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initchat();
        btLoginRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regist();
            }
        });
        btLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
    }

    private void login() {
        //登陆
        name= textName.getEditText().getText().toString();
        pasword= textMima.getEditText().getText().toString();

        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(name, pasword, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // 对模型层数据的处理
                        Modle.getInStance().logingSucess(name);

                        // 保存用户账号信息到本地数据库
                        User user=new User();
                        user.setName(name);
                        user.setHxid(name);
                        user.save();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 提示登录成功
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                                // 跳转到主页面
                                Intent intent = new Intent(LoginActivity.this, ChatHomeActivity.class);

                                startActivity(intent);

                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        // 提示登录失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败"+s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }

    private void initchat() {
        textName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<3){
                    textName.setError("不能为空,用户名要大于3位");
                    textName.setErrorEnabled(true);
                }else {
                    textName.setErrorEnabled(false);
                }

            }
        });
//        textMima.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.length()<4){
//                    textName.setError("不能为空,密码要大于4位");
//                    textMima.setErrorEnabled(true);
//                }else {
//                    textMima.setErrorEnabled(false);
//                }
//            }
//        });
    }

    private void regist() {
        //注册
        name= textName.getEditText().getText().toString();
        pasword= textMima.getEditText().getText().toString();

        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器注册账号
                try {
                    EMClient.getInstance().createAccount(name,pasword);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "注册失败"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
