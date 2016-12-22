package com.example.administrator.golife.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.golife.R;
import com.example.administrator.golife.bean.JokeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/22.
 */
public class JokeTextAdapterTwo extends RecyclerView.Adapter<JokeTextAdapterTwo.MyViewHoldr> {
    List<JokeBean.ResultBean.DataBean> jokedata;

    public JokeTextAdapterTwo(List<JokeBean.ResultBean.DataBean> jokedata) {
        this.jokedata = jokedata;
    }

    @Override
    public MyViewHoldr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_jake_item, parent, false);
        MyViewHoldr holer = new MyViewHoldr(view);

        return holer;
    }

    @Override
    public void onBindViewHolder(MyViewHoldr holder, int position) {
        holder.jokeTv.setText(jokedata.get(position).getContent());
        holder.jokeTime.setText(jokedata.get(position).getUpdatetime());
        jokedata.get(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return jokedata.size();
    }

    public class MyViewHoldr extends RecyclerView.ViewHolder {
    @BindView(R.id.joke_tv)
    TextView jokeTv;
    @BindView(R.id.joke_time)
    TextView jokeTime;
        public MyViewHoldr(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    /**
     * 清除数据
     */
    public void clearData() {
        jokedata.clear();
        notifyItemRangeChanged(0, jokedata.size());

    }

    /**
     * 根据指定位置添加数据
     *
     * @param position
     * @param data
     */
    public void addData(int position, List<JokeBean.ResultBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            jokedata.addAll(position, data);
            notifyItemRangeChanged(position, jokedata.size());

        }

    }

    /**
     * 得到总的条数
     *
     * @return
     */
    public int getDataCount() {
        return jokedata.size();
    }
}
