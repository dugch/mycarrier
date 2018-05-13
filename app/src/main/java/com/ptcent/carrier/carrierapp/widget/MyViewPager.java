package com.ptcent.carrier.carrierapp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author by dugc
 * @package com.chinatelling.psisystem.widget
 * @describe Viewpager
 * @date 2017/11/16    15:17
 */

public class MyViewPager extends ViewPager {

    private boolean swipeEnabled = true;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.swipeEnabled)
            try {
                return super.onInterceptTouchEvent(event);
            } catch (IllegalArgumentException e) {
            }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.swipeEnabled)
            try {
                return super.onTouchEvent(event);
            } catch (IllegalArgumentException e) {
            }
        return false;
    }

    public boolean isSwipeEnabled() {
        return swipeEnabled;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

}
