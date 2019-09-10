package com.example.muskanhussain.newsapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muskanhussain.newsapp.R;
import com.example.muskanhussain.newsapp.adapter.ChildViewPagerAdapter;
import com.example.muskanhussain.newsapp.model.News;

public class ParentFragment extends Fragment {

    ViewPager nestedViewPager;
    Activity mActivity;
    ToggleVerticalScrolling tv;
    int oldPosition = -1;

    public ParentFragment() {
        // Required empty public constructor
    }


    public static ParentFragment newInstance(News news) {
        ParentFragment fragment = new ParentFragment();
        Bundle args = new Bundle();
        args.putString("title", news.getTitle());
        args.putString("publishedAt", news.getPublishedAt());
        args.putString("description", news.getDescription());
        args.putString("url", news.getUrl());
        args.putString("imgUrl", news.getImageUrl());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_parent, container, false);
        String title = getArguments().getString("title");
        String publishedAt = getArguments().getString("publishedAt");
        String description = getArguments().getString("description");
        String url = getArguments().getString("url");
        String imgUrl = getArguments().getString("imgUrl");
        News news = new News(title, description, imgUrl, url, publishedAt);
        nestedViewPager = itemView.findViewById(R.id.nestedViewPager);
        nestedViewPager.setAdapter(new ChildViewPagerAdapter(getChildFragmentManager(), news));
        nestedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(i != oldPosition) {
                    tv.trigger(i);
                }
                oldPosition = i;
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return itemView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity) {
            mActivity = (Activity) context;
        }
        try {
            tv = (ToggleVerticalScrolling) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data");
        }

    }

    public interface ToggleVerticalScrolling {
        void trigger(int page);
    }


}
