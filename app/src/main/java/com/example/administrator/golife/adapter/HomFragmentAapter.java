package com.example.administrator.golife.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.golife.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
public class HomFragmentAapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private List<String> mStrings;

    public HomFragmentAapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
        mStrings=new ArrayList<>();
        mStrings.add("主页");
        mStrings.add("分类");
        mStrings.add("趣图");
        mStrings.add("段子");
        mStrings.add("发现");
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }
}
