package com.bjym.hyzc.activity.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseHistoryBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/29.
 */
public class NurseHistoryActivity extends BaseActivity {
    private TextView tv_NRecordNo;
    private EditText et_NursingTime;
    private EditText et_NursingDate;
    private EditText et_Pulsation;
    private EditText et_Temperature;
    private EditText et_BPL;
    private EditText et_BPH;
    private EditText et_RecordContent;
    private Button btn_commit;
    private String name;
    private String patientsNo;
    private String newCode;

    private final static int DATE_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar c = null;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursehistory, null);
        tv_NRecordNo = (TextView) view.findViewById(R.id.tv_NRecordNo);

        et_NursingTime= (EditText) view.findViewById(R.id.et_NursingTime);
        et_NursingDate = (EditText) view.findViewById(R.id.et_NursingDate);

        et_Pulsation = (EditText) view.findViewById(R.id.et_Pulsation);
        et_Temperature = (EditText) view.findViewById(R.id.et_Temperature);
        et_BPL = (EditText) view.findViewById(R.id.et_BPL);
        et_BPH = (EditText) view.findViewById(R.id.et_BPH);
        et_RecordContent = (EditText) view.findViewById(R.id.et_RecordContent);
        btn_commit = (Button) view.findViewById(R.id.btn_commit);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("填写护理记录");
        bt_titlebar_left.setOnClickListener(this);

        btn_commit.setOnClickListener(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        patientsNo = intent.getStringExtra("patientsNo");
        MyLog.i("name", name + patientsNo);

        if (name != null && patientsNo != null) {
            getNewCode();
        }

        et_NursingDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        et_NursingTime.setOnClickListener(new View.OnClickListener() {
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
                dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                c.set(dp.getYear(), dp.getMonth(), dp
                                        .getDayOfMonth());
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String formatDate = sdf.format(c.getTime());
                                et_NursingDate.setText(formatDate);


                            }
                        },
                        c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );

                break;
            case TIME_DIALOG:
                c = Calendar.getInstance();
                dialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                tp.setIs24HourView(true);
                                String formatTime = getTimeStr(hourOfDay, minute);
                                et_NursingTime.setText(formatTime);

                            }
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false
                );

                break;

            default:
                break;
        }


        return dialog;
    }

    public String  getTimeStr(int hour,int minute){
        StringBuffer   timeStr=new StringBuffer();
        timeStr.append(hour<=9?"0"+hour:hour);
        timeStr.append(":");
        timeStr.append(minute<=9?"0"+minute:minute);
        return timeStr.toString();
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
                        JSONObject jsonObjecet = new JSONObject(result);
                        newCode = jsonObjecet.getString("newCode");
                        MyLog.i("newCode:::::", newCode);
                        tv_NRecordNo.setText("护理记录编码：" + newCode);
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
        switch (v.getId()) {
            case R.id.btn_commit:
                String NursingTime = et_NursingTime.getText().toString().trim();
                String NursingDate = et_NursingDate.getText().toString().trim();
                if (NursingDate == null || "".equals(NursingDate)) {
                    MyToast.showToast(this, "请选择护理日期");
                    return;
                }
                if (NursingTime == null || "".equals(NursingTime)) {
                    MyToast.showToast(this, "请选择护理时间");
                    return;
                }
                String NursingTimeData = NursingDate + " " + NursingTime;
                commitNueseHistory(NursingTimeData);
                break;
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void commitNueseHistory(String timeDate) {
        Gson gson = new Gson();
        //脉搏
        String Pulsation1 = et_Pulsation.getText().toString().trim();
        if (TextUtils.isEmpty(Pulsation1)) {
            MyToast.showToast(this, "请填写脉搏值");
            return;
        }
     //   int Pulsation = Integer.parseInt(Pulsation1);

        //体温
        String Temperature1 = et_Temperature.getText().toString().trim();
        if (TextUtils.isEmpty(Temperature1)) {
            MyToast.showToast(this, "请填写体温值");
            return;
        }
//        float Temperature = Integer.parseInt(Temperature1);

        //最高血压
        String BPL1 = et_BPL.getText().toString().trim();
        if (TextUtils.isEmpty(BPL1)) {
            MyToast.showToast(this, "请填写最高血压");
            return;
        }
     //   int BPL = Integer.parseInt(BPL1);

        //最低血压
        String BHP1 = et_BPH.getText().toString().trim();
        if (TextUtils.isEmpty(BHP1)) {
            MyToast.showToast(this, "请填写最低血压");
            return;
        }
      //  int BHP = Integer.parseInt(BHP1);

        //护理内容
        String RecordContent = et_RecordContent.getText().toString().trim();
        if (TextUtils.isEmpty(RecordContent)){
            MyToast.showToast(this, "请填写护理内容");
            return;
        }

        NurseHistoryBean nurseHistory = new NurseHistoryBean(RecordContent,BHP1,newCode,patientsNo, name,timeDate, Pulsation1, Temperature1, BPL1);

        String toJson = gson.toJson(nurseHistory);
        MyLog.i("NueseHistory::::", toJson);

        String url = MyConstant.NURSE_HISTORY_COMMIT;
        MyLog.i("url:::", url);

        OkHttpUtils.postString().mediaType(MediaType.parse("application/json")).url(MyConstant.NURSE_HISTORY_COMMIT).content(toJson)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                MyLog.i("回调response.body().toString();", response.body().string());
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NurseHistoryActivity.this, "commitNueseHistory提交错误" + e.toString());

                MyLog.i("NueseHistory", "提交错误" + e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {

                MyToast.showToast(NurseHistoryActivity.this, "护理记录提交成功" );
                MyLog.i("请求成功", "commitNueseHistory" + o);
                finish();
            }
        });
    }
}
