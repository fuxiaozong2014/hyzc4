package com.bjym.hyzc.activity.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseHistory;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/29.
 */
public class NurseHistoryActivity extends BaseActivity {
    private TextView tv_NRecordNo;
    private TextView tv_NursingTime;
    private TextView tv_NursingDate;
    private EditText et_Pulsation;
    private EditText et_Temperature;
    private EditText et_BPL;
    private EditText et_BHP;
    private EditText et_RecordContent;
    private Button btn_commit;
    private String name;
    private String patientsNo;
    private String newCode;

    private EditText et=null;
    private final static int DATE_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar c = null;


    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursehistory, null);
        tv_NRecordNo = (TextView) view.findViewById(R.id.tv_NRecordNo);
        tv_NursingTime = (TextView) view.findViewById(R.id.tv_NursingTime);
        tv_NursingDate = (TextView)view.findViewById(R.id.tv_NursingDate);
        et_Pulsation = (EditText) view.findViewById(R.id.et_Pulsation);
        et_Temperature = (EditText) view.findViewById(R.id.et_Temperature);
        et_BPL = (EditText) view.findViewById(R.id.et_BPL);
        et_BHP = (EditText) view.findViewById(R.id.et_BHP);
        et_RecordContent = (EditText) view.findViewById(R.id.et_RecordContent);
        btn_commit = (Button) view.findViewById(R.id.btn_commit);

        return view;
    }

    @Override
    public void InitData() {
        btn_commit.setOnClickListener(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        patientsNo = intent.getStringExtra("patientsNo");
        MyLog.i("name", name + patientsNo);

        if (name != null&&patientsNo!=null) {
            getNewCode();
        }

        tv_NursingDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        tv_NursingTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                showDialog(TIME_DIALOG);
            }
        });

    }

    /**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG:
                c = Calendar.getInstance();
                dialog= new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                               tv_NursingDate.setText(year + "-" + (month+1) + "-" + dayOfMonth );

                            }
                        },
                        c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );

                break;
            case TIME_DIALOG:
                c=Calendar.getInstance();
                dialog=new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener(){
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tv_NursingTime.setText(hourOfDay+":"+minute+"");
                            }
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false
                );

                break;
        }
        return dialog;
    }

    /*
    * 得到记录编码 用于提交数据至服务器
    * */
    private void getNewCode() {
        OkHttpUtils.get().url(MyConstant.NURSE_HISTORY).build().execute(new Callback() {



            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                return response;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object response, int i) {
                if (response instanceof Response) {
                    try {
                        String result = ((Response) response).body().string();
                        JSONObject jsonObjecet=new JSONObject(result);
                        newCode = jsonObjecet.getString("newCode");
                        MyLog.i("newCode:::::", newCode);
                        tv_NRecordNo.setText("护理记录编码："+ newCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_commit:
                commitNueseHistory();

        }
    }

    private void commitNueseHistory() {
        Gson gson=new Gson();

        String NursingTime = tv_NursingTime.getText().toString().trim();
        String NursingDate = tv_NursingDate.getText().toString().trim();
        if (NursingDate == null ||"".equals(NursingDate)) {
            MyToast.showToast(this,"请选择护理日期");
        }
        if (NursingTime == null ||"".equals(NursingTime)) {
            MyToast.showToast(this,"请选择护理时间");
        }

        MyLog.i("NursingTime;;;;",NursingTime);
        MyLog.i("NursingDate;;;;",NursingDate);

        NurseHistory nurseHistory=new NurseHistory(newCode,patientsNo,name,NursingTime,1,1,2,3,"jfid");

        String toJson = gson.toJson(nurseHistory);
        MyLog.i("NueseHistory::::",toJson);
        String url=MyConstant.NURSE_HISTORY_COMMIT;
        MyLog.i("url:::",url);
        OkHttpUtils.postString().url(MyConstant.NURSE_HISTORY_COMMIT).content(toJson)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                MyLog.i("response.body().toString();",response.body().string());
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NurseHistoryActivity.this, "commitNueseHistory提交错误" + e.toString());

                MyLog.i("NueseHistory", "提交错误" + e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(NurseHistoryActivity.this, "提交成功"+"commitNueseHistory");
                MyLog.i("提交成功le", "commitNueseHistory"+o);
            }
        });
    }
}
