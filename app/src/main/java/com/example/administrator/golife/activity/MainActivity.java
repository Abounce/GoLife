package com.example.administrator.golife.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.golife.R;
import com.example.administrator.golife.fragment.HomeFragment;
import com.example.administrator.golife.util.ActivityCollector;
import com.example.administrator.golife.view.ActionSheetDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    /** 指定拍摄图片文件位置避免获取到缩略图 */
    private File outFile;
    /** 标记是拍照还是相册0 是拍照1是相册 */
    private int cameraorpic;
    /** 选择头像相册选取 */
    private static final int REQUESTCODE_PICK = 1;
    /** 裁剪好头像-设置头像 */
    private static final int REQUESTCODE_CUTTING = 2;
    /** 选择头像拍照选取 */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
    /** 裁剪好的头像的Bitmap */
    private Bitmap currentBitmap;
    /** 圆形图片的Imageview */
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
    private CircleImageView navigation_headimage;

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
        navigation_headimage= (CircleImageView) head.findViewById(R.id.navigation_headimage);
         navigation_headbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(MainActivity.this, "修改头像成功", Toast.LENGTH_SHORT).show();
                create();

            }
        });

        mainNavigation.addHeaderView(head);

    }

    private void create() {
        new ActionSheetDialog(MainActivity.this).Builder()

                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int witch) {
                        cameraorpic = 1;
                        openCamera();
                    }
                }).addSheetItem("打开相册",ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int witch) {
                cameraorpic = 0;
                openPic();
            }
        }).show();
    }

    private void openPic() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }

    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  进行判断是那个操作跳转回来的，如果是裁剪跳转回来的这块就要把图片现实到View上，其他两种的话都把数据带入裁剪界面
        switch (requestCode) {
            //相册
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            //裁剪
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            //拍照
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(outFile));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 把裁剪好的图片设置到View上或者上传到网络
     * @param data
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            /** 可用于图像上传 */
            currentBitmap = extras.getParcelable("data");

            navigation_headimage.setImageBitmap(currentBitmap);
        }
    }

    /**
     * 调用系统的图片裁剪
     * @param data
     */
    private void startPhotoZoom(Uri data) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);

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
                Toast.makeText(MainActivity.this,"设置成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                //关于
                try {
                    PackageManager manager = this.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                    String version = info.versionName;
                    Toast.makeText(MainActivity.this,"最新版本为:"+version, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
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
