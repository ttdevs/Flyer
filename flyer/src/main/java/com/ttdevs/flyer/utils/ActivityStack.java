package com.ttdevs.flyer.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ttdevs
 * 2018-09-07 23:28
 */
public class ActivityStack {

    private ActivityStack() {

    }

    private static String mActivity;

    public static String getTopActivity() {
        if(TextUtils.isEmpty(mActivity)){
            return SystemUtils.getTopActivity();
        }
        return mActivity;
    }

    public static void register(Application application) {
        if (null == application) {
            return;
        }
        try {
            Class.forName("android.support.v4.app.FragmentManager$FragmentLifecycleCallbacks");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(application, "Android support package version is low.", Toast.LENGTH_LONG).show();
            return;
        }

        application.registerActivityLifecycleCallbacks(callbacks);
    }

    private static Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            mActivity = activity.getClass().getName();

            for (OnTopActivityChangeListener listener : mListener) {
                listener.onTopActivityChange(activity.getClass().getName());
            }

            if (activity instanceof FragmentActivity) {
                registerFragmentLifecycleCallbacks((FragmentActivity) activity);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

        private void registerFragmentLifecycleCallbacks(final FragmentActivity activity) {
            final FragmentManager manager = activity.getSupportFragmentManager();
            manager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {

                @Override
                public void onFragmentResumed(FragmentManager fm, Fragment f) {
                    super.onFragmentResumed(fm, f);

                    updateVisibleStatus();
                }

                @Override
                public void onFragmentPaused(FragmentManager fm, Fragment f) {
                    super.onFragmentPaused(fm, f);

                    updateVisibleStatus();
                }

                private void updateVisibleStatus() {
                    for (Fragment fragment : manager.getFragments()) {
                        if (fragment.getUserVisibleHint()) {
                            String result = String.format("%s/%s", activity.getClass().getName(), fragment.getClass().getSimpleName());
                            for (OnTopActivityChangeListener listener : mListener) {
                                listener.onTopActivityChange(result);
                            }
                        }
                    }
                }

            }, true);
        }
    };

    private static List<OnTopActivityChangeListener> mListener = new ArrayList<>();

    public static void addTopActivityChangeListener(OnTopActivityChangeListener listener) {
        if (null != listener) {
            mListener.add(listener);
        }
    }

    public interface OnTopActivityChangeListener {
        /**
         * 栈顶Activity改变
         *
         * @param activityName top activity name
         */
        void onTopActivityChange(String activityName);
    }
}
