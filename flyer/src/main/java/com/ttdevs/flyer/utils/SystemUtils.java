package com.ttdevs.flyer.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ttdevs
 * 2018-09-07 23:28
 */
public class SystemUtils {

    public static boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(ApplicationUtil.getApplication())) {
                SystemUtils.requestGrantOverlay(ApplicationUtil.getApplication());
                return false;
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestGrantOverlay(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * From UETool
     *
     * @return top activity name
     */
    public static String getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);
            Field mActivitiesField = activityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Map activities = (Map) mActivitiesField.get(currentActivityThread);
            for (Object record : activities.values()) {
                Class recordClass = record.getClass();
                Field pausedField = recordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!(boolean) pausedField.get(record)) {
                    Field activityField = recordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return activityField.get(record).getClass().getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
