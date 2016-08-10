package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.ExitCPWCauseActvity;
import com.bjym.hyzc.activity.activity.ExitCPWPationteActvity;

/*
 * Created by fushaoqing on 2016/6/28.
*/

public class TongJiFragment extends BaseFragment {

    private TextView tv_ExitCPWPationte;
    private TextView tv_ExitCPWCause;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_tongji, null);
        tv_ExitCPWPationte = (TextView) view.findViewById(R.id.tv_ExitCPWPationte);
        tv_ExitCPWCause = (TextView) view.findViewById(R.id.tv_ExitCPWCause);
        return view;
    }

    @Override
    public void InitData() {
        tv_ExitCPWPationte.setOnClickListener(this);
        tv_ExitCPWCause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_ExitCPWPationte:
                //跳转到退出患者页面
                startActivity(new Intent(context,ExitCPWPationteActvity.class));
                break;
            case R.id.tv_ExitCPWCause:
                //跳转到退出患者页面
                startActivity(new Intent(context,ExitCPWCauseActvity.class));
                break;
        }
    }
}
