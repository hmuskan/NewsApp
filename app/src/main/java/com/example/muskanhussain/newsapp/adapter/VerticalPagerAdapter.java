package com.example.muskanhussain.newsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.muskanhussain.newsapp.R;
import com.example.muskanhussain.newsapp.model.News;

import java.util.List;

/**
 * Created by Muskan Hussain on 05-09-2019
 */
public class VerticalPagerAdapter extends PagerAdapter {
    private Context context;
    private List<News> list;
    private LayoutInflater inflater;

    public VerticalPagerAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.from(context).inflate(R.layout.news_layout, container, false);
        // TODO: Fill values of itemView as per layout, edit the news_layout.xml first
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
