package com.bjym.hyzc.activity.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * Created by fushaoqing on 2016/9/6.
 */
public class ExecutedYiZhuFragment extends BaseFragment{
    @Override
    public View setMainView() {
        TextView tv=new TextView(context);
        tv.setText("我是已执行医嘱界面");
        return tv;
    }

    @Override
    public void InitData() {

    }
}
