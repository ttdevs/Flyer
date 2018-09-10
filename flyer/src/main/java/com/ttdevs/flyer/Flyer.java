package com.ttdevs.flyer;

import com.ttdevs.flyer.utils.ActivityStack;
import com.ttdevs.flyer.utils.ApplicationUtil;
import com.ttdevs.flyer.utils.SystemUtils;
import com.ttdevs.flyer.view.FlyerWindow;

/**
 * @author ttdevs
 * 2018-08-28 16:49
 */
public class Flyer {
    private static FlyerWindow mFloatWindow;

    private Flyer() {

    }

    public synchronized static void show() {
        if (!SystemUtils.requestPermission()) {
            return;
        }

        ActivityStack.register(ApplicationUtil.getApplication());

        if (null == mFloatWindow) {
            int marginTop = ApplicationUtil.getDimensionPixelSize(R.dimen.window_margin_top);
            mFloatWindow = new FlyerWindow(ApplicationUtil.getApplication(), marginTop);
        }
        mFloatWindow.show();
    }

    public static void dismiss() {
        if (null == mFloatWindow) {
            return;
        }
        mFloatWindow.dismiss();
    }
}
