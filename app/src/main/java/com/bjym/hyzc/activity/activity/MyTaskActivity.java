package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyToast;

/**
 * Created by fushaoqing on 2016/7/27.
 */
public class MyTaskActivity extends BaseActivity {

    private static final int REQUST_CODE = 1;
    private TextView tv_search;
    private Button btn_nurseHistory;
    private String name;
    private String patientsNo;


    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_mytask, null);
        btn_nurseHistory = (Button) view.findViewById(R.id.btn_nurseHistory);

        tv_search = (TextView) view.findViewById(R.id.tv_search);


        return view;
    }

    @Override
    public void InitData() {
        tv_search.setOnClickListener(this);
        btn_nurseHistory.setOnClickListener(this);


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
                Intent intent1 = new Intent(this, NurseHistoryActivity.class);
                if (name==null||patientsNo==null){
                    MyToast.showToast(this,"请选择护理对象");

                }else{
                    intent1.putExtra("Name", name);
                    intent1.putExtra("patientsNo", patientsNo);

                    startActivity(intent1);

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
                tv_search.setText("姓名：" + name + "    患者编号：" + patientsNo);
                break;
        }
    }
}
