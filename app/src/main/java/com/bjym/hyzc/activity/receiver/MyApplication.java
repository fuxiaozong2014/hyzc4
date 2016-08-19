package com.bjym.hyzc.activity.receiver;

import android.app.Application;

import com.bjym.hyzc.activity.net.PersistentCookieStore;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by fushaoqing on 2016/6/29.
 */
public class MyApplication extends Application {
    private static OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);
        initHttpUtils();
        instance = this;
     //  ge.overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }


    /**
     * 初始化httputils
     */
    private void initHttpUtils() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        //                .addInterceptor(new LoggerInterceptor("TAG"))
//其他配置
        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
    private static MyApplication instance;
    public static MyApplication getAppContext() {
        return instance;
    }
}



