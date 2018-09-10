package com.ttdevs.demo.fragment;

import android.os.Bundle;

/**
 * @author ttdevs
 */
public class Third4Fragment extends ThirdBaseFragment {

    public static Third4Fragment newInstance(String content) {
        Third4Fragment fragment = new Third4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }
}
