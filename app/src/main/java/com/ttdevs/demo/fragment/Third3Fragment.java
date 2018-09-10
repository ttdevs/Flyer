package com.ttdevs.demo.fragment;

import android.os.Bundle;

/**
 * @author ttdevs
 */
public class Third3Fragment extends ThirdBaseFragment {

    public static Third3Fragment newInstance(String content) {
        Third3Fragment fragment = new Third3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }
}
