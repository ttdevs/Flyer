package com.ttdevs.flyer.utils;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ttdevs
 * 2018-08-29 11:06
 */
public class LogcatUtil extends Thread {

    private boolean isQuit = false;

    private Handler mHandler;
    private String mCmd;

    public LogcatUtil(Handler handler, String cmd) {
        mHandler = handler;
        mCmd = cmd;
    }

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Process process = null;
        BufferedReader bufferedReader = null;
        try {
            process = Runtime.getRuntime().exec(mCmd);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            isQuit = true;
            return;
        }

        while (!isQuit) {
            try {
                String line = bufferedReader.readLine();
                sendMessage(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedReader.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Runtime.getRuntime().exec("logcat -c");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String msgString) {
        if (isQuit) {
            return;
        }
        Message msg = mHandler.obtainMessage();
        msg.what = Constant.KEY_LOG_MESSAGE;
        msg.obj = msgString;
        mHandler.sendMessage(msg);
    }

    public void quit() {
        isQuit = true;
    }
}
