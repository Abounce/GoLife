package com.example.administrator.golife.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.NewsData;
import com.example.administrator.golife.util.CacheUtils;
import com.example.administrator.golife.util.MyApplication;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class CategoryActivity extends BaseActivity {

    @BindView(R.id.category_toolbar)
    Toolbar categoryToolbar;
    @BindView(R.id.category_coll)
    CollapsingToolbarLayout categoryColl;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.category_fab)
    FloatingActionButton categoryFab;
    @BindView(R.id.category_iv)
    ImageView categoryIv;
    @BindView(R.id.category_rv)
    RecyclerView categoryRv;
    @BindView(R.id.category_refresh)
    SwipeRefreshLayout categoryRefresh;
    private String image_url;
    private String text;
    private String caterul;
    private List<NewsData.ResultBean.DataBean> data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(categoryToolbar);
        initdata();

        categoryRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromService();
            //    Logger.init("TAG").logLevel(LogLevel.NONE);
     //           Logger.d("----------------刷新成功");
                categoryRefresh.setRefreshing(false);
            }
        });
        String cache = CacheUtils.getString(MyApplication.getContext(), caterul);
        if (!TextUtils.isEmpty(cache)){
            parsedata(cache);
        }
        getDataFromService();

    }

    private void initdata() {
        image_url = getIntent().getStringExtra("image_url");
        text = getIntent().getStringExtra("text");
        caterul = getIntent().getStringExtra("caterul");
       // Logger.d(caterul);
        categoryColl.setTitle(text);
        categoryColl.setCollapsedTitleTextColor(Color.WHITE);
        categoryColl.setExpandedTitleColor(Color.WHITE);
        Glide.with(this)
                .load(image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.login)
                .into(categoryIv);
    }

    private void getDataFromService() {

        OkHttpUtils.get().url(caterul).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CacheUtils.putString(MyApplication.getContext(),"caterul",response);
                        parsedata(response);
                    }
                });
          }

    private void parsedata(String response) {
        NewsData newsData = new Gson().fromJson(response, NewsData.class);
        data=newsData.getResult().getData();
        CategoryAdapter adapter=new CategoryAdapter(this,R.layout.news_rv_item,data);
        categoryRv.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,LinearLayoutManager.VERTICAL,false));
        categoryRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent =new Intent(CategoryActivity.this, NewsDetailActivity.class);
                intent.putExtra("categorynews",data.get(position));
                intent.putExtra("id","3");
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }
    class CategoryAdapter extends CommonAdapter<NewsData.ResultBean.DataBean>{

        public CategoryAdapter(Context context, int layoutId, List<NewsData.ResultBean.DataBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, NewsData.ResultBean.DataBean dataBean, int position) {
            holder.setText(R.id.cv_text,dataBean.getTitle());
            holder.setText(R.id.author,dataBean.getAuthor_name());
            holder.setText(R.id.date,dataBean.getDate());
            ImageView iv = holder.getView(R.id.cv_image);
            Glide.with(CategoryActivity.this)
                    .load(dataBean.getThumbnail_pic_s())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.login)
                    .error(R.drawable.login)
                    .into(iv);
        }
    }



}
