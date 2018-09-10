package com.ttdevs.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttdevs.demo.R;

/**
 * @author ttdevs
 */
public class ThirdBaseFragment extends Fragment {
    public static final String ARG_CONTENT = "arg_content";

    private String mContent;
    private TextView tvContent;

    public ThirdBaseFragment() {
        // Required empty public constructor
    }

    public static ThirdBaseFragment newInstance(String content) {
        ThirdBaseFragment fragment = new ThirdBaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mContent = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        tvContent = view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvContent.setText(mContent);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }
}
