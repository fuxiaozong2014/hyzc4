package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;

/**
 * Created by fushaoqing on 2016/7/27.
 */
public class MyTaskActivity extends BaseActivity {

    private static final int REQUST_CODE = 1;
    private TextView tv_search;
    private TextView tv_nurseHistory;
    private TextView tv_nurseStage;
    private TextView tv_addNursingCare;
    private TextView tv_nursingContentSelect;
    private String name;
    private String patientsNo;
    private String cpwCode;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_mytask, null);
        tv_nurseHistory = (TextView) view.findViewById(R.id.tv_nurseHistory);
        tv_nurseStage = (TextView) view.findViewById(R.id.tv_nurseStage);
        tv_addNursingCare = (TextView) view.findViewById(R.id.tv_addNursingCare);
        tv_nursingContentSelect = (TextView) view.findViewById(R.id.tv_nursingContentSelect);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("我的任务");
        tv_search.setOnClickListener(this);
        tv_nurseHistory.setOnClickListener(this);
        tv_nurseStage.setOnClickListener(this);
        tv_addNursingCare.setOnClickListener(this);
        tv_nursingContentSelect.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent();
                intent.setClass(MyTaskActivity.this, MyPationteActivity.class);
                startActivityForResult(intent, REQUST_CODE);
                break;
            case R.id.tv_nurseHistory:
                Intent NurseHistoryActivityIntent = new Intent(this, NurseHistoryActivity.class);
                if (name == null || patientsNo == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    NurseHistoryActivityIntent.putExtra("Name", name);
                    NurseHistoryActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(NurseHistoryActivityIntent);
                }
                break;
            case R.id.tv_nurseStage:
                Intent NurseExecuteActivityIntent = new Intent(this, NurseSelectStageActivity.class);
                if (name == null || patientsNo == null || cpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    NurseExecuteActivityIntent.putExtra("cpwCode", cpwCode);
                    NurseExecuteActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(NurseExecuteActivityIntent);
                }
                break;
            case R.id.tv_addNursingCare:
                startActivity(new Intent(this, AddNursingCareActivity.class));
                break;

            case R.id.tv_nursingContentSelect:
                Intent NursingContentSelectIntent = new Intent(this, NursingContentSelectActivity.class);
                if (cpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    NursingContentSelectIntent.putExtra("cpwCode", cpwCode);
                    startActivity(NursingContentSelectIntent);
                }

                break;

            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                name = data.getStringExtra("Name");
                patientsNo = data.getStringExtra("patientsNo");
                cpwCode = data.getStringExtra("cpwCode");
                MyLog.i("cpwCode", cpwCode);
                tv_search.setText("姓名：" + name + "    患者编号：" + patientsNo);
                break;
        }
    }
}
