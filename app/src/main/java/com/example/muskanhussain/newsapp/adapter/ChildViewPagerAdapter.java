package com.example.muskanhussain.newsapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.muskanhussain.newsapp.fragment.ChildFragment;
import com.example.muskanhussain.newsapp.model.News;

/**
 * Created by Muskan Hussain on 10-09-2019
 */
public class ChildViewPagerAdapter extends FragmentStatePagerAdapter {
    News news;
    public ChildViewPagerAdapter(FragmentManager fm, News news) {
        super(fm);
        this.news = news;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0:
                return ChildFragment.newInstance(news, false);
            case 1:
                return ChildFragment.newInstance(news, true);
            default:
                return ChildFragment.newInstance(news, true);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    /*@Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }*/
}
