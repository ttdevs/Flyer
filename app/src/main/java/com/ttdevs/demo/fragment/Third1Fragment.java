package com.ttdevs.demo.fragment;

import android.os.Bundle;

/**
 * @author ttdevs
 */
public class Third1Fragment extends ThirdBaseFragment {

    public static Third1Fragment newInstance(String content) {
        Third1Fragment fragment = new Third1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }
}
