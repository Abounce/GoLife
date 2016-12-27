package com.example.administrator.golife.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.golife.R;
import com.example.administrator.golife.adapter.JokeTextAdapterTwo;
import com.example.administrator.golife.bean.JokeBean;
import com.example.administrator.golife.util.CacheUtils;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.MyApplication;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by yhy on 2016/12/8.
 */
public class JokeFragment extends BaseFragment {
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
    private String jokeurl;
    /**
     * 当前页
     */
    private  int curPage = 1;
    private List<JokeBean.ResultBean.DataBean> jokedata;
    private RecyclerView joke_rv;
    private MaterialRefreshLayout joke_text_reresh;
    private JokeTextAdapterTwo adapter;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_joke, null);
        joke_rv = (RecyclerView) view.findViewById(R.id.joke_rv);
        joke_text_reresh= (MaterialRefreshLayout) view.findViewById(R.id.joke_text_reresh);
        joke_text_reresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state=STATE_REFRES;
                curPage=1;
                jokeurl= Config.BASE_JOKE_TEXT+curPage+Config.IMAGE_SIZE;
                getDataFromService();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                state=STATE_LOADMORE;
                curPage+=1;
                jokeurl= Config.BASE_JOKE_TEXT+curPage+Config.IMAGE_SIZE;
                //  Logger.d(url);
                getDataFromService();
            }
        });
        return view;
    }
    @Override
    protected void initData() {
        setDefultUrl();
        getDataFromService();


    }

    private void setDefultUrl() {
          curPage=1;
          jokeurl= Config.BASE_JOKE_TEXT+curPage+Config.IMAGE_SIZE;

    }

    private void getDataFromService() {
        String result = CacheUtils.getString(MyApplication.getContext(),jokeurl);
        if (!TextUtils.isEmpty(result)){
            parseData(result);
        }
        OkHttpUtils.get().url(jokeurl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                CacheUtils.putString(MyApplication.getContext(),jokeurl,response);

                parseData(response);
            }
        });
    }

    private void parseData(String result) {
        Gson gson =new Gson();
        JokeBean jokeBean = gson.fromJson(result, JokeBean.class);
        jokedata = jokeBean.getResult().getData();
        showData();


    }

    private void showData() {
        switch (state) {
            case STATE_NORMAL:
        adapter=new JokeTextAdapterTwo(jokedata);
        joke_rv.setLayoutManager(new LinearLayoutManager(mContext));
        joke_rv.setAdapter(adapter);
                break;
            case STATE_REFRES:
                adapter.clearData();
                adapter.addData(0,jokedata);
                joke_text_reresh.finishRefresh();

                break;
            case STATE_LOADMORE:
                adapter.addData(adapter.getDataCount(),jokedata);
                joke_text_reresh.finishRefreshLoadMore();
                break;
        }
    }
}
