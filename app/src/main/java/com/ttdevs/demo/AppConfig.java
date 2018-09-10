package com.ttdevs.demo;

import android.app.Application;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;

/**
 * @author ttdevs
 * Create at 2018-08-28 16:48
 */
public class AppConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                error.printStackTrace();

                throw error;
            }
        }).start();
    }
}
