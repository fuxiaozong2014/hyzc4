package com.bjym.hyzc.activity.pager;

import android.content.Context;
import android.view.View;

import com.bjym.hyzc.R;

/**
 * Created by fushaoqing on 2016/7/25.
 */
public class ForumPager extends BasePager {

    public ForumPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_forum, null);
        return view;
    }

    @Override
    public void initData() {

    }

}
