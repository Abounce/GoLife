package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.table.User;
import com.example.administrator.golife.util.Modle;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.tv_add_find) //查找
    TextView tvAddFind;
    @BindView(R.id.et_add_name) //编辑名字
    EditText etAddName;
    @BindView(R.id.iv_add_photo) //图片
    ImageView ivAddPhoto;
    @BindView(R.id.tv_add_name)  //名称
    TextView tvAddName;
    @BindView(R.id.bt_add_add)   //添加
    Button btAddAdd;
    @BindView(R.id.rl_add)      //显示或者隐藏
    RelativeLayout rlAdd;
    private String name;
    private User userbean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        tvAddFind.setOnClickListener(this);
        btAddAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_add_find:
                //查找
                find();
                break;
            case R.id.bt_add_add:
                //添加
                add();
                break;
        }

    }

    private void find() {
        name = etAddName.getText().toString();
        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       userbean = new User(name);


                        rlAdd.setVisibility(View.VISIBLE);
                tvAddName.setText(userbean.getName());
                    }
                });


            }
        });
    }

    private void add() {
        Modle.getInStance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(userbean.getHxid(),"加个好友呗");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(AddContactActivity.this, "添加好友失败"+e.toString(), Toast.LENGTH_SHORT).show();
                       }
                   });

                }
            }
        });


    }

}
