package com.example.administrator.golife;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.activity.BaseActivity;
import com.example.administrator.golife.activity.MainActivity;
import com.example.administrator.golife.util.CacheUtils;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.MyApplication;
import com.example.administrator.golife.util.StatusUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LuncherActivity extends BaseActivity {


    @BindView(R.id.luncher_iv)
    ImageView luncherIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
       // Log.d("data", "onCreate: ----------------------");
        ButterKnife.bind(this);
        StatusUtil.StatusChange(this);

        String cache = CacheUtils.getString(MyApplication.getContext(), Config.LOGIN_IMAGE);
        if (!TextUtils.isEmpty(cache)){
            parseData(cache);
        }
        getDataFromService();
        startAnimation();

    }

    private void getDataFromService() {
    //  String url ="http://news-at.zhihu.com/api/4/start-image/480*728";
        OkHttpUtils.get().url(Config.LOGIN_IMAGE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Bitmap bitmap = BitmapFactory.decodeResource(LuncherActivity.this.getResources(),R.drawable.login);
                luncherIv.setImageBitmap(bitmap);
            //    String name = Thread.currentThread().getName();
              //  Logger.d(name);
            }
            @Override
            public void onResponse(String response, int id) {
                parseData(response);
             //   String name = Thread.currentThread().getName();
              //  Logger.d(name);
                CacheUtils.putString(MyApplication.getContext(),Config.LOGIN_IMAGE,response);

            }
        });

    }

    private void parseData(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String img = object.getString("img");
       //     Logger.d(img);
            Glide.with(this)
                    .load(img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.login)
                    .error(R.drawable.login)
                    .into(luncherIv);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(luncherIv, "scaleX", 1f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(luncherIv, "scaleY", 1f, 1.2f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(luncherIv, "alpha", 0, 1);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY).with(alpha);
        set.setStartDelay(300);
        set.setDuration(3200);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            startActivity(new Intent(LuncherActivity.this, MainActivity.class));
            finish();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}
