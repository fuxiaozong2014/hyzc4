package com.bjym.hyzc.activity.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bjym.hyzc.R;

/**
 * @author Wisn
 * @time 2016/6/25 18:36
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Context context;
    //private Callback callback;
    private  Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
       StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
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
     * 取消进度栏
     */
    public  void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 进度等待
     * @param message
     */
    public  void showDialogProgress(String message) {
        if(context!=null){
            dialog = new Dialog(context, R.style.MyDialogStyle);
            dialog.setContentView(R.layout.dialog_progress);// 为对话框设置自定义布局
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0f;
            window.setAttributes(params);
            dialog.setCanceledOnTouchOutside(true);
            TextView messageTv = (TextView) dialog
                    .findViewById(R.id.dialog_message);
            if (message != null) {
                messageTv.setText(message);
            }
            dialog.show();
        }else{
            dialog=null;
        }
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
