package com.example.administrator.golife.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.golife.R;
import com.example.administrator.golife.activity.MainActivity;
import com.example.administrator.golife.adapter.HomFragmentAapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhy on 2016/12/8.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.homefragment_vp)
    ViewPager homefragmentVp;
    private Context mContext;
    private List<BaseFragment> mFragments;
    private TabLayout tabLayout;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_homefragment, null);
        ButterKnife.bind(this, view);
        MainActivity mactivity = (MainActivity) getActivity();
        tabLayout = mactivity.getTabLayout();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragments();
        HomFragmentAapter homFragmentAapter = new HomFragmentAapter(getChildFragmentManager(), mFragments);
        homefragmentVp.setAdapter(homFragmentAapter);
        tabLayout.setupWithViewPager(homefragmentVp);
        homefragmentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity mactivity = (MainActivity) getActivity();
                List<String> mStrings=new ArrayList<String>();
                mStrings.add("主页");
                mStrings.add("分类");
                mStrings.add("趣图");
                mStrings.add("段子");
                mStrings.add("发现");
                mactivity.gettoolbar().setTitle(mStrings.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragments() {
        mFragments=new ArrayList<>();
        mFragments.add(new NewsFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new PhotoFragment());
        mFragments.add(new JokeFragment());
        mFragments.add(new FoundFragment());
    }
}
