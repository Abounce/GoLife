package com.example.administrator.golife.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.fragment.HomeFragment;
import com.example.administrator.golife.util.ActivityCollector;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.main_tablayout)
    TabLayout mainTablayout;
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.main_navigation)
    NavigationView mainNavigation;
    @BindView(R.id.main_drawer)
    DrawerLayout mainDrawer;
    private long mCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mainToolbar.setTitle("主页");
        mainToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mainToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainDrawer, mainToolbar, 0, 0);
        mainDrawer.setDrawerListener(toggle);
        toggle.syncState();
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.back);
//        }
         changeFragment(new HomeFragment());
         mainNavigation.setNavigationItemSelectedListener(this);
         View head =View.inflate(this,R.layout.navigation_head,null);
         Button navigation_headbt= (Button) head.findViewById(R.id.navigation_headbt);
         CircleImageView navigation_headimage= (CircleImageView) head.findViewById(R.id.navigation_headimage);
         navigation_headbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "修改头像成功", Toast.LENGTH_SHORT).show();
            }
        });

        mainNavigation.addHeaderView(head);

    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.commit();

    }

    public TabLayout getTabLayout() {
        return mainTablayout;
    }
    //实现双击两次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mCurrentTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mCurrentTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;//如果是后退键，则截获动作
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                //主页
                changeFragment(new HomeFragment());
                mainToolbar.setTitle("主页");
                break;
            case R.id.menu_set:
                //设置
                break;
            case R.id.menu_about:
                //关于
                break;
            case R.id.menu_ancellation:
                //注销
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                break;
        }
        mainDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public Toolbar gettoolbar(){
        return mainToolbar;
    }
}
