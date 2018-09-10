package com.ttdevs.flyer.utils;

import android.app.ActivityThread;
import android.app.Application;

/**
 * @author ttdevs
 * 2018-08-28 16:52
 */
public class ApplicationUtil {
    private ApplicationUtil() {
    }

    public static Application getApplication() {
        return ActivityThread.currentApplication();
    }

    public static int getDimensionPixelSize(int resId) {
        return getApplication().getResources().getDimensionPixelSize(resId);
    }
}
