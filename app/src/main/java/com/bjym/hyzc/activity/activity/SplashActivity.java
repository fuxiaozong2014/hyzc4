package com.bjym.hyzc.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;

import com.bjym.hyzc.R;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class SplashActivity extends Activity {

    private static final int EmptyMsg = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_splash);
        sendMsg();
    }

    private void sendMsg() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                handler.sendEmptyMessageDelayed(EmptyMsg, 3000);
            }
        }.start();
    }

}
