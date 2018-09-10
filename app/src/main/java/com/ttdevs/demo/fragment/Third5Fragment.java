package com.ttdevs.demo.fragment;

import android.os.Bundle;

/**
 * @author ttdevs
 */
public class Third5Fragment extends ThirdBaseFragment {

    public static Third5Fragment newInstance(String content) {
        Third5Fragment fragment = new Third5Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }
}
