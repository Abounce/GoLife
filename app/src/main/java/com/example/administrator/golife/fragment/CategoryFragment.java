package com.example.administrator.golife.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.CategoryActivity;
import com.example.administrator.golife.bean.CatogeryBean;
import com.example.administrator.golife.util.Config;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
public class CategoryFragment extends BaseFragment {
    private RecyclerView categorv;
    private String[] mCategory = {"社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] mImages = {"http://img4.imgtn.bdimg.com/it/u=94001432,2982290636&fm=23&gp=0.jpg",
            "http://pic91.huitu.com/res/20161128/575321_20161128215951640050_1.jpg",
            "http://gb.cri.cn/mmsource/images/2015/10/09/2014112117260737396.jpg",
            "http://v1.qzone.cc/avatar/201406/26/13/16/53abac9a97720408.jpg%21200x200.jpg",
            "http://upload.taihainet.com/2016/1111/1478797486684.jpeg",
            "http://imgstore.cdn.sogou.com/app/a/100540002/442642.jpg",
            "http://bcs.kuaiapk.com/rbpiczy/Wallpaper/2013/10/9/312ac549c96e4ea78260a3d88d227b53.jpg",
            "http://www.cs.com.cn/images/2014nz/ten_p09.jpg",
            "http://img1.gtimg.com/ent/pics/hv1/38/214/1921/124967633.jpg"
    };
    private String[] mCategoryId = {Config.SHEHUI, Config.GUONEI, Config.GUOJI, Config.YULE,
            Config.TIYU,Config.JUNSHI, Config.KEJI, Config.CAIJING, Config.SHISHANG};
    private List<CatogeryBean> mdatas;
    @Override
    protected void initData() {
        mdatas=new ArrayList<>();
        for (int i = 0; i <mCategory.length ; i++) {
            mdatas.add(new CatogeryBean(mCategory[i],mImages[i]));
        }
        CategoryAdapter adapter = new CategoryAdapter(mContext, R.layout.category_item, mdatas);
        GridLayoutManager layoutManager=new GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false);
        categorv.setLayoutManager(layoutManager);
        categorv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext,"点击了"+mdatas.get(position).getText()+"位置是"+position, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent();
                intent.putExtra("image_url",mdatas.get(position).getImages());
                intent.putExtra("text",mdatas.get(position).getText());
                String cateUrl = Config.BASE_NEWS_URL+mCategoryId[position]+ Config.KEY +
                        Config.NEWS_KEY;//具体分类新闻的url
                intent.putExtra("caterul",cateUrl);
                intent.setClass(mContext, CategoryActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    protected View initView() {
        View view =View.inflate(mContext, R.layout.fragment_category,null);
        categorv = (RecyclerView) view.findViewById(R.id.category_rv);
        return view ;
    }
    class CategoryAdapter extends CommonAdapter<CatogeryBean>{


        public CategoryAdapter(Context context, int layoutId, List<CatogeryBean> datas) {
            super(context, layoutId, datas);

        }

        @Override
        protected void convert(ViewHolder holder, CatogeryBean catogeryBean, int position) {
          holder.setText(R.id.cate_text,catogeryBean.getText());
            ImageView iv = holder.getView(R.id.cate_image);
            Glide.with(mContext)
                    .load(catogeryBean.getImages())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.login)
                    .error(R.drawable.login)
                    .into(iv);
        }
    }
}
