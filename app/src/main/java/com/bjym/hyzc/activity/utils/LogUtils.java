package com.bjym.hyzc.activity.utils;

import android.util.Log;

public class LogUtils {
    private static final boolean ENABLE = true;
    
    public static void i(String tag, String msg) {
        if (ENABLE) {
            Log.i(tag, msg);
        }
    }
    
    public static void i(Class<?> cls, String msg) {
        if (ENABLE) {
            Log.i("HYZC" + cls.getSimpleName(), msg);
        }
    }
}
