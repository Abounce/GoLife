package com.example.administrator.golife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.golife.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends AppCompatActivity {

    private PhotoView photo_pv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        photo_pv = (PhotoView) findViewById(R.id.photo_pv);
        String url = getIntent().getStringExtra("url");

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photo_pv);

        Picasso.with(this)
                .load(url)
                .into(photo_pv, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {

                    }
                });

//        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(photo_pv);
//        attacher.update();
    }
}
