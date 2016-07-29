package com.bjym.hyzc.activity.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bjym.hyzc.R;

/**
 * @author Wisn
 * @time 2016/6/25 18:36
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Context context;
    //private Callback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());*/

        this.context = this;
        setContentView(setMainView());
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
        InitData();
    }

    /**
     * 子类实现选择监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 用于指定View和View的初始化
     *
     * @return
     */
    public abstract View setMainView();

    /**
     * 加载数据
     */
    public abstract void InitData();


}
