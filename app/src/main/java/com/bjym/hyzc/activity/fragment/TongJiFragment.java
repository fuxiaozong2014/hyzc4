package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.ExitCPWCauseActvity;
import com.bjym.hyzc.activity.activity.ExitCPWPationteActvity;

/*
 * Created by fushaoqing on 2016/6/28.
*/

public class TongJiFragment extends BaseFragment {

    private Button btn_exitCPWPationte;
    private Button btn_ExitCPWCause;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_tongji, null);
        btn_exitCPWPationte = (Button) view.findViewById(R.id.btn_ExitCPWPationte);
        btn_ExitCPWCause = (Button) view.findViewById(R.id.btn_ExitCPWCause);
        return view;
    }

    @Override
    public void InitData() {
        btn_exitCPWPationte.setOnClickListener(this);
        btn_ExitCPWCause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_ExitCPWPationte:
                //跳转到退出患者页面
                startActivity(new Intent(context,ExitCPWPationteActvity.class));
                break;
            case R.id.btn_ExitCPWCause:
                //跳转到退出患者页面
                startActivity(new Intent(context,ExitCPWCauseActvity.class));
                break;
        }
    }
}
