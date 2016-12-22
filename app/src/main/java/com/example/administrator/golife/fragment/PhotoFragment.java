package com.example.administrator.golife.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.golife.R;
import com.example.administrator.golife.adapter.JokeImageAdpterTwo;
import com.example.administrator.golife.bean.PhotoData;
import com.example.administrator.golife.util.CacheUtils;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.MyApplication;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/8.
 */
public class PhotoFragment extends BaseFragment {
    private RecyclerView photo_rv;
    private MaterialRefreshLayout photo_refresh;
    /**
     * 默认状态
     */
    private static final int STATE_NORMAL = 1;
    /**
     * 下拉刷新状态
     */
    private static final int STATE_REFRES = 2;

    /**
     * 上拉刷新（加载更多）状态
     */
    private static final int STATE_LOADMORE = 3;

    /**
     * 默认是正常状态
     */
    private int state = STATE_NORMAL;
    /**
     * 当前页
     */
    private  int curPage = 1;
    private String url;
    private List<PhotoData.ResultBean.DataBean> ImageData;
    private JokeImageAdpterTwo adapter;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_photo, null, false);
        photo_rv = (RecyclerView) view.findViewById(R.id.photo_rv);
        photo_refresh = (MaterialRefreshLayout) view.findViewById(R.id.photo_refresh);

        initRefresh();
        return view;
    }

    @Override
    protected void initData() {
        setDefault();
        getDataFromService();

    }

    private void initRefresh() {
        photo_refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state=STATE_REFRES;
                curPage=1;
                url= Config.BASE_JOKE_IMAGE+curPage+Config.IMAGE_SIZE;
                getDataFromService();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                state=STATE_LOADMORE;
                curPage+=1;
                url= Config.BASE_JOKE_IMAGE+curPage+Config.IMAGE_SIZE;
              //  Logger.d(url);
                getDataFromService();

            }
        });
    }

    private void setDefault() {
        state = STATE_NORMAL;
        curPage=1;
        url= Config.BASE_JOKE_IMAGE+curPage+Config.IMAGE_SIZE;
    }

    private void getDataFromService() {
        String result = CacheUtils.getString(MyApplication.getContext(), url);
        if (!TextUtils.isEmpty(result)){
            prseJson(result);
        }

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                CacheUtils.putString(MyApplication.getContext(),url,response);
               prseJson(response);
            }
        });
    }

    private void prseJson(String response) {
        Gson gson=new Gson();
        PhotoData photoData = gson.fromJson(response, PhotoData.class);
        ImageData = photoData.getResult().getData();
        showData();
    }

    private void showData() {
        switch (state) {

        case STATE_NORMAL://默认
        //显示数据
        //设置适配器
            adapter = new JokeImageAdpterTwo(ImageData);
            photo_rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            photo_rv.setAdapter(adapter);
            break;
        case STATE_REFRES://下拉刷新
            //1.把之前的数据清除
            adapter.clearData();
            //2.添加新的数据-刷新
            adapter.addData(0,ImageData);
            //3.把状态还原
            photo_refresh.finishRefresh();




        break;
        case STATE_LOADMORE://上拉刷新（加载更多）

            //1.把新的数据添加到原来的数据的末尾-刷新
            adapter.addData(adapter.getDataCount(),ImageData);

            photo_refresh.finishRefreshLoadMore();

        break;
        }
    }

}
