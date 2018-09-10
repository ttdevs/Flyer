package com.ttdevs.flyer.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ttdevs.flyer.R;
import com.ttdevs.flyer.utils.Constant;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.ttdevs.flyer.utils.Constant.INDEX_LOG_CONTENT;
import static com.ttdevs.flyer.utils.Constant.INDEX_LOG_LEVEL;

/**
 * @author ttdevs
 * 2018-09-07 23:28
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> implements ActionMode.Callback {

    private Context mContext;
    private ClipboardManager mClipboard;
    private List<String> mData;
    private LayoutInflater mInflater;
    private TextView tvLog;

    public LogAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);

        mData = data;

        mContext = context;
        mClipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
    }

    public void setData(List<String> data) {
        mData = data;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_logcat_content, parent, false);
        tvLog = itemView.findViewById(R.id.tv_log);
        tvLog.setCustomSelectionActionModeCallback(this);
        return new LogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        String logString = mData.get(position);

        String[] items = logString.split("\\s+");

        ForegroundColorSpan colorSpan = Constant.LOG_COLOR_SPAN.get('I');
        if (items.length >= INDEX_LOG_CONTENT) {
            if (items[INDEX_LOG_LEVEL].length() > 0) {
                char flag = items[INDEX_LOG_LEVEL].toCharArray()[0];
                colorSpan = Constant.LOG_COLOR_SPAN.get(flag);
            }
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(logString);
        builder.setSpan(colorSpan, 0, logString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        holder.tvLog.setText(builder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        int start = tvLog.getSelectionStart();
        int end = tvLog.getSelectionEnd();
        String txt = tvLog.getText().toString();
        String result = txt.substring(start, end);
        mClipboard.setPrimaryClip(ClipData.newPlainText(null, result));
        Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    class LogViewHolder extends RecyclerView.ViewHolder implements ActionMode.Callback{
        public TextView tvLog;

        public LogViewHolder(View view) {
            super(view);

            tvLog = view.findViewById(R.id.tv_log);
            tvLog.setCustomSelectionActionModeCallback(this);
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            int start = tvLog.getSelectionStart();
            int end = tvLog.getSelectionEnd();
            String txt = tvLog.getText().toString();
            String result = txt.substring(start, end);
            mClipboard.setPrimaryClip(ClipData.newPlainText(null, result));
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();

            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
