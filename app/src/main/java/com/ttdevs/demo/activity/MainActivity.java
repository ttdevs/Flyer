package com.ttdevs.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ttdevs.demo.R;
import com.ttdevs.flyer.Flyer;

/**
 * @author ttdevs
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    private Handler mHandler = new Handler();
    private int mInterval = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler.postDelayed(this, mInterval);
    }

    @Override
    public void run() {
        System.out.println(">>>>>" + System.currentTimeMillis());
        for (int i = 0; i < 1; i++) {
            Log.v(">>>>>", "verbose");
            Log.d(">>>>>", "debug");
            Log.i(">>>>>", "info");
            Log.w(">>>>>", "warn");
            Log.e(">>>>>", "error");
            Log.wtf(">>>>>", "assert");
        }
        mHandler.postDelayed(this, mInterval);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_open:
                Flyer.show();
                break;
            case R.id.bt_fast:
                mInterval = 2;
                break;
            case R.id.bt_slow:
                mInterval = 2 * 1000;
                break;

            case R.id.bt_second:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            default:
                break;
        }
    }
}
