package com.example.administrator.golife.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.NewsData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yhy on 2016/12/9.
 */
public class NewsAdapter extends CommonAdapter<NewsData.ResultBean.DataBean> {
  //  private List<NewsData.ResultBean.DataBean> datas;
    public NewsAdapter(Context context, int layoutId, List<NewsData.ResultBean.DataBean> datas) {
        super(context, layoutId, datas);
       // this.datas=datas;
    }
    @Override
    protected void convert(ViewHolder holder, NewsData.ResultBean.DataBean dataBean, int position) {
//        NewsData.ResultBean.DataBean bean= datas.get(position-1);
//        holder.setText(R.id.cv_text,bean.getTitle());
//           holder.setText(R.id.author,bean.getAuthor_name());
//           holder.setText(R.id.date,bean.getDate());

        holder.setText(R.id.cv_text,dataBean.getTitle());
        holder.setText(R.id.author,dataBean.getAuthor_name());
        holder.setText(R.id.date,dataBean.getDate());

           ImageView iv = holder.getView(R.id.cv_image);
            Glide.with(mContext)
                .load(dataBean.getThumbnail_pic_s())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.login)
                .error(R.drawable.login)
                .into(iv);

    }
}
