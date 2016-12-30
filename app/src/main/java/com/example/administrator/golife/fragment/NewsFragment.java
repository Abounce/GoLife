package com.example.administrator.golife.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.NewsDetailActivity;
import com.example.administrator.golife.adapter.NewsAdapter;
import com.example.administrator.golife.bean.NewsData;
import com.example.administrator.golife.util.CacheUtils;
import com.example.administrator.golife.util.Config;
import com.example.administrator.golife.util.MyApplication;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by yhy on 2016/12/8.
 */
public class NewsFragment extends BaseFragment {
   private RecyclerView recyclerview;
    private MaterialRefreshLayout refresh;
    private static final String MAIN_URL = Config.BASE_NEWS_URL + Config.TOP + Config.KEY
            + Config.NEWS_KEY;
    private List<NewsData.ResultBean.DataBean> topnews;

    private List<NewsData.ResultBean.DataBean> itemnews;
    private NewsAdapter newsAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private SliderLayout slider;
    private PagerIndicator indicator;

    @Override
    protected View initView() {
        View view =View.inflate(mContext, R.layout.fragment_news,null);
        recyclerview= (RecyclerView) view.findViewById(R.id.news_recyclerview);
        refresh= (MaterialRefreshLayout) view.findViewById(R.id.news_refresh);

        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
           //    Log.e("", "onRefresh:-------------------------------------------");
               getDataFromService();

            }
        });
        return view;
    }

    @Override
    protected void initData() {
        String cache = CacheUtils.getString(MyApplication.getContext(), MAIN_URL);
        if (!TextUtils.isEmpty(cache)){
            parseData(cache);
        }
        getDataFromService();

    }

    private void getDataFromService() {
        OkHttpUtils
                .get()
                .url(MAIN_URL)
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
              //  Logger.json(response);
                  parseData(response);
                  CacheUtils.putString(MyApplication.getContext(),MAIN_URL,response);
            }
        });

    }

    private void parseData(String response) {
         Gson  gson=new Gson();
         NewsData newsData = gson.fromJson(response, NewsData.class);
         NewsData.ResultBean result = newsData.getResult();
         List<NewsData.ResultBean.DataBean> maindata = result.getData();
         topnews = maindata.subList(0, 5);
         itemnews = maindata.subList(5, maindata.size());
         newsAdapter = new NewsAdapter(mContext, R.layout.news_rv_item, itemnews);
         recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        // recyclerview.setAdapter(newsAdapter);
          initHeaderView();
          newsAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
             //   Toast.makeText(mContext,"打印的位置是"+position+"--内容"+itemnews.get(position-1).getTitle()+"位置：---", Toast.LENGTH_SHORT).show();
              //  Logger.d("打印的位置是"+position+"--内容"+itemnews.get(position).getTitle());
                Intent intent =new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("itemnews",itemnews.get(position-1));
                intent.putExtra("id","2");
                startActivity(intent);
            }

             @Override
             public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                 return false;
            }


        });
        recyclerview.setAdapter(mHeaderAndFooterWrapper);
        refresh.finishRefresh();

    }

    private void initHeaderView() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(newsAdapter);
      //  View head=View.inflate(mContext,R.layout.fragment_news_top,null);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.fragment_news_top, null);
        slider= (SliderLayout) inflate.findViewById(R.id.fragment_slider);
        indicator = (PagerIndicator) inflate.findViewById(R.id.custom_indicator);
        initSlider();

        mHeaderAndFooterWrapper.addHeaderView(inflate);


    }

    private void initSlider() {

        for (int i = 0; i <topnews.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            //String topiamge = CacheUtils.getString(MyApplication.getContext(), "TOPIAMGE");

            textSliderView.image(topnews.get(i).getThumbnail_pic_s());
          //  CacheUtils.putString(MyApplication.getContext(),"TOPIMAGE",topnews.get(i).getThumbnail_pic_s());
            textSliderView.description(topnews.get(i).getTitle());
            slider.addSlider(textSliderView);
            final int finalI = i;

            final int finalI1 = i;
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {
                Toast.makeText(mContext, ""+topnews.get(finalI).getTitle(), Toast.LENGTH_LONG).show();
                Intent intent =new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("topnews",topnews.get(finalI1));
                intent.putExtra("id","1");
                startActivity(intent);

            }
        });
        }
        slider.setCustomIndicator(indicator);
       // slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
       // slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(3000);
    }

    @Override
    public void onDestroy() {
        slider.stopAutoCycle();
        super.onDestroy();
    }
}
