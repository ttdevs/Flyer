package com.ttdevs.flyer.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ttdevs.flyer.R;
import com.ttdevs.flyer.adapter.LogAdapter;
import com.ttdevs.flyer.utils.ActivityStack;
import com.ttdevs.flyer.utils.ApplicationUtil;
import com.ttdevs.flyer.utils.Constant;
import com.ttdevs.flyer.utils.LogcatUtil;
import com.ttdevs.flyer.utils.OnLoadMoreListener;
import com.ttdevs.flyer.utils.OnTouchYListener;

import java.util.ArrayList;

import static com.ttdevs.flyer.utils.Constant.MAX_LOG_SIZE;
import static com.ttdevs.flyer.utils.Constant.DELETE_LOG_SIZE;

/**
 * @author ttdevs
 * 2018-08-28 17:01
 */
public class FlyerWindow extends LinearLayout {
    private static WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();

    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        mLayoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = ApplicationUtil.getDimensionPixelSize(R.dimen.window_height);
    }

    private Context mContext;
    private WindowManager mWindowManager;

    private View viewIcon;
    private AppCompatEditText viewKeyword;
    private Spinner spLevel;
    private View viewClean;
    private CheckBox cbScroll;
    private View viewClose;
    private RecyclerView rvLog;
    private TextView tvTopActivity;
    private View viewChangeHeight;

    private ArrayList<String> mDataList = new ArrayList<>();
    private ArrayList<String> mDataSearch = new ArrayList<>();
    private LogAdapter mAdapter;

    private LogcatUtil mLogcat;

    public FlyerWindow(Context context, int y) {
        super(context);

        mContext = context;

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams.y = y;

        initView();

        ActivityStack.addTopActivityChangeListener(new ActivityStack.OnTopActivityChangeListener() {
            @Override
            public void onTopActivityChange(String activity) {
                tvTopActivity.setText(activity);
            }
        });
    }

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.color.global_bg_black);
        inflate(mContext, R.layout.layout_flyer_window, this);

        viewIcon = findViewById(R.id.view_icon);
        spLevel = findViewById(R.id.spLevel);
        viewKeyword = findViewById(R.id.view_keyword);
        viewClean = findViewById(R.id.view_clean);
        cbScroll = findViewById(R.id.cb_scroll);
        viewClose = findViewById(R.id.view_close);
        tvTopActivity = findViewById(R.id.tv_top_activity);
        viewChangeHeight = findViewById(R.id.view_change_height);
        rvLog = findViewById(R.id.rv_log);
        rvLog.setLayoutManager(new LinearLayoutManager(mContext));
        rvLog.setAdapter(mAdapter = new LogAdapter(mContext, mDataList));
        rvLog.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!cbScroll.isChecked()) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        viewClean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
            }
        });
        viewClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        viewIcon.setOnTouchListener(new OnTouchYListener() {
            @Override
            public void onYChange(int y) {
                mLayoutParams.y += y;
                mLayoutParams.y = Math.max(0, mLayoutParams.y);
                mWindowManager.updateViewLayout(FlyerWindow.this, mLayoutParams);
            }
        });
        viewChangeHeight.setOnTouchListener(new OnTouchYListener() {
            private int minHeight = getResources().getDimensionPixelSize(R.dimen.window_min_height);

            @Override
            public void onYChange(int y) {
                mLayoutParams.height += y;
                if (mLayoutParams.height < minHeight) {
                    mLayoutParams.height = minHeight;
                }
                mWindowManager.updateViewLayout(FlyerWindow.this, mLayoutParams);
            }
        });
        initSpinner();
        initKeyword();
    }

    private void initSpinner() {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.logcat_level,
                R.layout.view_logcat_level);
        adapter.setDropDownViewResource(R.layout.item_logcat_level);
        spLevel.setAdapter(adapter);
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence level = adapter.getItem(position);
                showLogcat(level.charAt(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initKeyword() {
        viewKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mAdapter.setData(mDataList);
                } else {
                    mDataSearch.clear();
                    for (String item : mDataList) {
                        if (item.toLowerCase().contains(keyword.toLowerCase())) {
                            mDataSearch.add(item);
                        }
                    }
                    mAdapter.setData(mDataSearch);
                }
            }
        });
    }

    public void show() {
        try {
            mWindowManager.addView(this, mLayoutParams);

            tvTopActivity.setText(ActivityStack.getTopActivity());
            showLogcat(Constant.VERBOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            mWindowManager.removeView(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeLogcat();
        }
    }

    private void showLogcat(char level) {
        closeLogcat();

        String cmd = String.format("logcat -v threadtime *:%c", level);
        mLogcat = new LogcatUtil(mHandler, cmd);
        mLogcat.start();
    }

    private void closeLogcat() {
        if (null != mLogcat) {
            mLogcat.quit();
            mLogcat = null;
        }

        clearLog();
    }

    private void clearLog() {
        mDataSearch.clear();
        mDataList.clear();
        mAdapter.notifyDataSetChanged();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case Constant.KEY_LOG_MESSAGE:
                    updateLog(msg.obj.toString());
                    break;

                default:
                    break;
            }
        }
    };

    private void updateLog(String logMsg) {
        if (TextUtils.isEmpty(logMsg)) {
            return;
        }
        if (logMsg.equals(Constant.KEY_RESET_LOGCAT)) {
            showLogcat(Constant.VERBOSE);
            return;
        }
        mDataList.add(logMsg);

        // 当Log数量大于指定数量，清除之前的Log
        if (mDataList.size() > MAX_LOG_SIZE) {
            for (int i = 0; i < DELETE_LOG_SIZE; i++) {
                mDataList.remove(i);
            }
        }
        // 锁定的时候始终滚到最底部
        if (cbScroll.isChecked()) {
            mAdapter.notifyDataSetChanged();
            rvLog.scrollToPosition(mDataList.size() - 1);
        }
    }
}
