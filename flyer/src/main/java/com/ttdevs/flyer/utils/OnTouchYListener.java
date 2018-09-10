package com.ttdevs.flyer.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author ttdevs
 * 2018-09-07 23:28
 */
public abstract class OnTouchYListener implements View.OnTouchListener {

    private float mY, mLastY;

    public OnTouchYListener() {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mY = event.getRawY();
                mLastY = mY;
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) (event.getRawY() - mLastY);
                onYChange(y);
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Y移动的距离
     *
     * @param y move y
     */
    public abstract void onYChange(int y);
}
