package com.example.muskanhussain.newsapp.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.muskanhussain.newsapp.fragment.ParentFragment;
import com.example.muskanhussain.newsapp.model.News;

import java.util.List;

/**
 * Created by Muskan Hussain on 05-09-2019
 */
public class VerticalPagerAdapter extends FragmentPagerAdapter {
    List<News> newsList;

    public VerticalPagerAdapter(FragmentManager fm, List<News> newsList) {
        super(fm);
        this.newsList = newsList;
    }

    @Override
    public Fragment getItem(int i) {
        Log.d("MHPAR", String.valueOf(i));
        return ParentFragment.newInstance(newsList.get(i));
    }

    @Override
    public int getCount() {
        return newsList.size();
    }
}
