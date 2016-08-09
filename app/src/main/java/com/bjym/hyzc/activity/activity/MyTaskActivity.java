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
    private Button btn_nurseHistory;
    private Button btn_nurseStage;
    private Button btn_addNursingCare;
    private Button btn_nursingContentSelect;
    private String name;
    private String patientsNo;
    private String cpwCode;


    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_mytask, null);
        btn_nurseHistory = (Button) view.findViewById(R.id.btn_nurseHistory);
        btn_nurseStage = (Button) view.findViewById(R.id.btn_nurseStage);
        btn_addNursingCare = (Button) view.findViewById(R.id.btn_addNursingCare);
        btn_nursingContentSelect = (Button) view.findViewById(R.id.btn_nursingContentSelect);

        tv_search = (TextView) view.findViewById(R.id.tv_search);


        return view;
    }

    @Override
    public void InitData() {
        tv_search.setOnClickListener(this);
        btn_nurseHistory.setOnClickListener(this);
        btn_nurseStage.setOnClickListener(this);
        btn_addNursingCare.setOnClickListener(this);
        btn_nursingContentSelect.setOnClickListener(this);

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
            case R.id.btn_nurseHistory:
                Intent NurseHistoryActivityIntent = new Intent(this, NurseHistoryActivity.class);
                if (name==null||patientsNo==null){
                    MyToast.showToast(this,"请选择患者");
                    return;
                }else{
                    NurseHistoryActivityIntent.putExtra("Name", name);
                    NurseHistoryActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(NurseHistoryActivityIntent);

                }
                break;

            case R.id.btn_nurseStage:
                Intent NurseExecuteActivityIntent = new Intent(this, NurseSelectStageActivity.class);
                if (name==null||patientsNo==null||cpwCode==null){
                    MyToast.showToast(this,"请选择患者");
                    return;
                }else{
                    NurseExecuteActivityIntent.putExtra("cpwCode", cpwCode);
                    NurseExecuteActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(NurseExecuteActivityIntent);
                }

                break;
            case R.id.btn_addNursingCare:
                startActivity(new Intent(this,AddNursingCareActivity.class));

                break;

            case R.id.btn_nursingContentSelect:
                Intent NursingContentSelectIntent = new Intent(this, NursingContentSelectActivity.class);
                if (cpwCode==null){
                    MyToast.showToast(this,"请选择患者");
                    return;
                }else{
                    NursingContentSelectIntent.putExtra("cpwCode", cpwCode);
                    startActivity(NursingContentSelectIntent);
                }

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
                MyLog.i("cpwCode",cpwCode);
                tv_search.setText("姓名：" + name + "    患者编号：" + patientsNo);
                break;
        }
    }
}
