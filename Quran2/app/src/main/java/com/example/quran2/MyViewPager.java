package com.example.quran2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toolbar;

import java.util.Calendar;

import static com.example.quran2.MainActivity.toolbar;

public class MyViewPager extends ViewPager {

    /**
     * Max allowed duration for a "click", in milliseconds.
     */
    private static final int MAX_CLICK_DURATION = 1000;

    /**
     * Max allowed distance to move during a "click", in DP.
     */
    private static final int MAX_CLICK_DISTANCE = 15;

    private long pressStartTime;
    private float pressedX;
    private float pressedY;

    private int k = 0;

//    private static final int MAX_CLICK_DURATION = 2000;
//    private long startClickTime;

//    private boolean isClicked(MotionEvent event) {
//        // To differentiate a click and a long click.
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                startClickTime = System.nanoTime();
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                long clickDuration = System.nanoTime() - startClickTime;
//                if (clickDuration < MAX_CLICK_DURATION) {
//                    //click event has occurred
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                pressStartTime = System.currentTimeMillis();
                pressedX = ev.getX();
                pressedY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                if (pressDuration < MAX_CLICK_DURATION && distance(pressedX, pressedY, ev.getX(), ev.getY()) < MAX_CLICK_DISTANCE) {
                    // Click event has occurred
                    if (k == 0) {
                        k = 1;
                        toolbar.setVisibility(View.VISIBLE);
                    } else if (k == 1) {
                        k = 0;
                        toolbar.setVisibility(View.GONE);
                    }
                }
            }


            //            if (ev.getEventTime() < MAX_CLICK_DURATION) {
//            //User is clicking on the screen
//            if (k == 0) {
//                k = 1;
//                toolbar.setVisibility(View.VISIBLE);
//            } else if (k == 1) {
//                k = 0;
//                toolbar.setVisibility(View.GONE);
//            }
//        }
        }
        return true;
    }
    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }


}
