package com.bjym.hyzc.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.bjym.hyzc.R;

import cn.jpush.android.api.JPushInterface;

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
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
            }
        }
    };

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }
    @Override
    public void onStart() {
        super.onStart();
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
