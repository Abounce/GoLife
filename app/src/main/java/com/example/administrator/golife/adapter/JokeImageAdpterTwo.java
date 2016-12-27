package com.example.administrator.golife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.PhotoViewActivity;
import com.example.administrator.golife.bean.PhotoData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhy on 2016/12/22.
 */
public class JokeImageAdpterTwo extends RecyclerView.Adapter<JokeImageAdpterTwo.MyViewholder> {
    List<PhotoData.ResultBean.DataBean> ImageData;

    private Context contxt;

    public JokeImageAdpterTwo(List<PhotoData.ResultBean.DataBean> ImageData) {
        this.ImageData = ImageData;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        contxt = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_photo_item, parent, false);
        MyViewholder holder = new MyViewholder(view);
        //   int position = holder.getAdapterPosition();
        //   String url = ImageData.get(position).getUrl();


        //   final String url = dataBean.getUrl();
//        holder.photoIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(contxt, PhotoViewActivity.class);
//                intent.putExtra("url",url);
//                contxt.startActivity(intent);
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        PhotoData.ResultBean.DataBean dataBean = ImageData.get(position);
        holder.photoTime.setText(dataBean.getUpdatetime());
        holder.photoTv.setText(dataBean.getContent());
        Glide.with(contxt).load(dataBean.getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.photoIv);
        final String url = dataBean.getUrl();
        holder.photoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contxt, PhotoViewActivity.class);
                intent.putExtra("url", url);
                contxt.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ImageData.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_tv)
        TextView photoTv;
        @BindView(R.id.photo_iv)
        ImageView photoIv;
        @BindView(R.id.photo_time)
        TextView photoTime;

        public MyViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photoTv = (TextView) itemView.findViewById(R.id.photo_tv);
            photoTime = (TextView) itemView.findViewById(R.id.photo_time);
            photoIv = (ImageView) itemView.findViewById(R.id.photo_iv);
            // PhotoData.ResultBean.DataBean dataBean = ImageData.get(getLayoutPosition());
        }
    }


    /**
     * 清除数据
     */
    public void clearData() {
        ImageData.clear();
        notifyItemRangeChanged(0, ImageData.size());

    }

    /**
     * 根据指定位置添加数据
     *
     * @param position
     * @param data
     */
    public void addData(int position, List<PhotoData.ResultBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            ImageData.addAll(position, data);
            notifyItemRangeChanged(position, ImageData.size());
        }

    }

    /**
     * 得到总的条数
     *
     * @return
     */
    public int getDataCount() {
        return ImageData.size();
    }


}
