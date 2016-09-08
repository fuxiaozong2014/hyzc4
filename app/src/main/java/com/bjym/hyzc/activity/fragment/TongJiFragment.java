package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.ExitCPWCauseActvity;
import com.bjym.hyzc.activity.activity.ExitCPWPationteActvity;

/*
 * Created by fushaoqing on 2016/6/28.
*/

public class TongJiFragment extends BaseFragment {

    private LinearLayout ll_ExitCPWPationte;
    private LinearLayout ll_ExitCPWCause;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private TextView tv_search;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_tongji, null);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        ll_ExitCPWPationte = (LinearLayout) view.findViewById(R.id.ll_ExitCPWPationte);
        ll_ExitCPWCause = (LinearLayout) view.findViewById(R.id.ll_ExitCPWCause);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView)view.findViewById(R.id.tv_titlebar_center);

        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.GONE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("统计");
        ll_ExitCPWPationte.setOnClickListener(this);
        ll_ExitCPWCause.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_ExitCPWPationte:
                //跳转到退出患者页面
                startActivity(new Intent(context,ExitCPWPationteActvity.class));
                break;
            case R.id.ll_ExitCPWCause:
                //跳转到退出患者页面
                startActivity(new Intent(context,ExitCPWCauseActvity.class));
                break;
            case R.id.tv_search:
                //跳转到退出患者页面
                //startActivity(new Intent(context,ExitCPWCauseActvity.class));
                Toast.makeText(TongJiFragment.this.getActivity(),"预留按钮",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
