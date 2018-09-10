package com.ttdevs.demo.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ttdevs.demo.R;

/**
 * @author ttdevs
 */
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ClipboardManager mClipboard;
    private AppCompatTextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        tvContent = findViewById(R.id.tv_content);
        initCopyText(tvContent);
    }

    private void initCopyText(final TextView tvContent) {
        tvContent.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                int start = tvContent.getSelectionStart();
                int end = tvContent.getSelectionEnd();
                String txt = tvContent.getText().toString();
                String result = txt.substring(start, end);
                mClipboard.setPrimaryClip(ClipData.newPlainText(null, result));
                Toast.makeText(SecondActivity.this, result, Toast.LENGTH_LONG).show();
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
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_third:
                startActivity(new Intent(this, ThirdActivity.class));
                break;
            case R.id.bt_finish:
                finish();
                break;

            default:
                break;
        }
    }
}
