package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseHistory;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/29.
 */
public class NurseHistoryActivity extends BaseActivity {
    private TextView tv_NRecordNo;
    private TextView tv_NursingTime;
    private EditText et_Pulsation;
    private EditText et_Temperature;
    private EditText et_BPL;
    private EditText et_BHP;
    private EditText et_RecordContent;
    private Button btn_commit;
    private String name;
    private String patientsNo;
    private String newCode;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursehistory, null);
        tv_NRecordNo = (TextView) view.findViewById(R.id.tv_NRecordNo);
        tv_NursingTime = (TextView) view.findViewById(R.id.tv_NursingTime);
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
    }

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
        String NursingTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

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
