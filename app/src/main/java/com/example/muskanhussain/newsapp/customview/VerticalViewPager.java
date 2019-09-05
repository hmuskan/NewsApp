package com.example.muskanhussain.newsapp.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Muskan Hussain on 05-09-2019
 */
public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        @Override
        public void transformPage(@NonNull View view, float v) {
            if (v < -1) {
                view.setAlpha(0);
            } else if (v <= 0) {
                view.setAlpha(1);
                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -v);
                //set Y position to swipe in from top
                float yPosition = v * view.getHeight();
                view.setTranslationY(yPosition);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (v <= 1) {
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -v);


                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(v));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0);
            }

        }
    }
}
