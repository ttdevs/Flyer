package com.ttdevs.demo.fragment;

import android.os.Bundle;

/**
 * @author ttdevs
 */
public class Third2Fragment extends ThirdBaseFragment {

    public static Third2Fragment newInstance(String content) {
        Third2Fragment fragment = new Third2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }
}
