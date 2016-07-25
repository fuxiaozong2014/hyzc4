package com.bjym.hyzc.activity.pager;

import android.content.Context;
import android.view.View;

/**
 * 一级菜单 自定义pager的基类
 * @author wangdh
 *
 */
public abstract class BasePager {
    public Context context;
    public BasePager(Context context){
        this.context = context;
    }
    /**
     * 初始化view
     */
    public abstract View initView();
    /**
     * 初始化数据
     */
    public abstract void initData();

    
}
