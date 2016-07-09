package com.bjym.hyzc.activity.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by fushaoqing on 2016/6/27.
 */
public class MyToast {

    public static void showToast(final Activity ctx,final String msg){
        //如果是主线程直接弹出toast
        if ( "main".equals(Thread.currentThread().getName())) {
            Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
        }else{
            //如果不是
            ctx.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}
