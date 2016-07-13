package com.bjym.hyzc.activity.utils;

import com.bjym.hyzc.activity.receiver.MyApplication;

public class DensityUtil {
    /**
     * 手机的分辨率
     */
    public static final float DENSITY = MyApplication.getAppContext().getResources().getDisplayMetrics().density;
    /**
     * 手机的分辨率
     */
    public static final int SCREEN_WIDTH = MyApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    /**
     * 手机的分辨率
     */
    public static final int SCREEN_HEIGHT = MyApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(float dpValue) {
        return (int) (dpValue * DENSITY + 0.5f);
    }  

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(float pxValue) {
        return (int) (pxValue / DENSITY + 0.5f);
    }  
} 