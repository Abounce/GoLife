package com.example.administrator.golife.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.PhotoData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yhy on 2016/12/21.
 */
public class JokeImageAdapter extends CommonAdapter<PhotoData.ResultBean.DataBean> {
    private int countdata;
    private List<PhotoData.ResultBean.DataBean> datas;
    public JokeImageAdapter(Context context, int layoutId, List<PhotoData.ResultBean.DataBean> datas) {
        super(context, layoutId, datas);
        countdata=datas.size();
        this.datas=datas;
    }

    @Override
    protected void convert(ViewHolder holder, PhotoData.ResultBean.DataBean dataBean, int position) {
        PhotoData.ResultBean.DataBean dataBean1 = datas.get(position);
        holder.setText(R.id.photo_tv,dataBean1.getContent());
        holder.setText(R.id.photo_time,dataBean1.getUpdatetime());
        ImageView iv = holder.getView(R.id.photo_iv);
        Glide.with(mContext).load(dataBean1.getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);

    }
    public int getDataCount() {
        return countdata;
    }
    /**
     * 根据指定位置添加数据
     * @param position
     * @param data
     */
    public void addData(int position, List<PhotoData.ResultBean.DataBean> data) {
        if(data != null && data.size() >0){
            datas.addAll(position,data);

            notifyItemRangeChanged(position,datas.size());
        }

    }
}
