package com.example.administrator.golife.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.NewsData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.detail_iv)
    ImageView detailIv;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;

    @BindView(R.id.detail_appbar)
    AppBarLayout detailAppbar;
    @BindView(R.id.detail_webview)
    WebView detailWebview;
    @BindView(R.id.detial_float)
    FloatingActionButton detialFloat;
    @BindView(R.id.detail_collapsing)
    CollapsingToolbarLayout detailCollapsing;
    private NewsData.ResultBean.DataBean baseNews;

    private NewsData.ResultBean.DataBean itemnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initData();
        //    detailToolbar.setTitleTextColor(Color.WHITE);
        detailToolbar.setTitle(baseNews.getTitle());
        detailToolbar.setNavigationIcon(R.drawable.backx);
        setSupportActionBar(detailToolbar);
        detailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        detailWebview.setWebViewClient(new WebViewClient());
        detailWebview.loadUrl(baseNews.getUrl());
        // mCtbLayout.setTitle(mTitle);

        detailCollapsing.setCollapsedTitleTextColor(Color.WHITE);
        detailCollapsing.setExpandedTitleColor(Color.WHITE);
        Glide.with(this)
                .load(baseNews.getThumbnail_pic_s())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.login)
                .error(R.drawable.login)
                .into(detailIv);
        //  detailTv.setText(topnews.getTitle());


    }

    private void initData() {
        String id = getIntent().getStringExtra("id");

        if ("1".equals(id)){

         baseNews = (NewsData.ResultBean.DataBean) getIntent().getSerializableExtra("topnews");
        }else  if ("2".equals(id)){

         baseNews = (NewsData.ResultBean.DataBean) getIntent().getSerializableExtra("itemnews");
        }else if ("3".equals(id)){
            baseNews = (NewsData.ResultBean.DataBean) getIntent().getSerializableExtra("categorynews");
        }
    }
}
