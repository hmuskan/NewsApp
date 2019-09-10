package com.example.muskanhussain.newsapp.fragment;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muskanhussain.newsapp.R;
import com.example.muskanhussain.newsapp.model.News;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChildFragment extends Fragment {


    public ChildFragment() {
        // Required empty public constructor
    }


    public static ChildFragment newInstance(News news, boolean isWebView) {
        ChildFragment fragment = new ChildFragment();
        Bundle args = new Bundle();
        args.putString("title", news.getTitle());
        args.putString("publishedAt", news.getPublishedAt());
        args.putString("description", news.getDescription());
        args.putString("url", news.getUrl());
        args.putString("imgUrl", news.getImageUrl());
        args.putBoolean("isWebView", isWebView);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_child, container, false);
        WebView webView = itemView.findViewById(R.id.webView);
        ImageView newsImage = itemView.findViewById(R.id.news_image);
        TextView headline = itemView.findViewById(R.id.headline);
        TextView publishedDate = itemView.findViewById(R.id.published_date);
        TextView content = itemView.findViewById(R.id.news_content);
        Button fullNews = itemView.findViewById(R.id.button);
        boolean isWebView = getArguments().getBoolean("isWebView");
        if(isWebView) {
            webView.setVisibility(View.VISIBLE);
            newsImage.setVisibility(View.GONE);
            headline.setVisibility(View.GONE);
            publishedDate.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            fullNews.setVisibility(View.GONE);
            webView.loadUrl(getArguments().getString("url"));

        } else {
            webView.setVisibility(View.INVISIBLE);
            newsImage.setVisibility(View.VISIBLE);
            headline.setVisibility(View.VISIBLE);
            publishedDate.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            fullNews.setVisibility(View.VISIBLE);
            String imgUrl = getArguments().getString("imgUrl");
            if(imgUrl != null) {
                Picasso.get().load(imgUrl).into(newsImage);
            }
            headline.setText(getArguments().getString("title"));
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            try {
                Date date = df1.parse(getArguments().getString("publishedAt"));
                publishedDate.setText(date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            content.setText(getArguments().getString("description"));
        }
        fullNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentFragment parentFragment = ((ParentFragment) ChildFragment.this.getParentFragment());
                parentFragment.nestedViewPager.setCurrentItem(1);
            }
        });
        return itemView;

    }


}
