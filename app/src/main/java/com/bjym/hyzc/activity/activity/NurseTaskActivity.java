package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.executeNursingCare.NursingContentParentStageActivity;
import com.bjym.hyzc.activity.ExecuteYiZhu2.ExecuteYiZhuParentStageActivity;
import com.bjym.hyzc.activity.bean.PationteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.bjym.hyzc.activity.zxing.code.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/27.
 * 这是护士的任务类，包含执行医嘱，护理记录，执行护理，新增护理四个功能按钮
 */
public class NurseTaskActivity extends BaseActivity {

    private static final int REQUST_CODE_PATIONTEMSG = 1;
    private static final int REQUST_CODE_SCANBAR =0 ;
    private TextView tv_search;
    private TextView tv_indays;
    private LinearLayout ll_nurseHistory;
    private LinearLayout ll_nurseExecuteYiZhu;
    private LinearLayout ll_addNursingCare;
    private TextView tv_nursingContent_executed;
    private LinearLayout ll_ExecuteNursingCare;
    private String name;
    private String patientsNo;
    private String cpwCode;
    private String deptName;
    private String deptCode;
    private ImageView iv_search;
    private String codeinformation;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private LinearLayout ll_yiZhuManager;
    private SharedPreferences SpPationteMsgConfig;
    private String transName;//传递给surveyActivity的调查人名字，用于传递过去，调查结果的提交
    private String transPationteNo;//传递给surveyActivity的调查人编号，用于传递过去，调查结果的提交
    private String transCpwCode;//传递给surveyActivity的调查人编号，用于传递过去，调查结果的提交
    private String transDeptName;
    private String transDeptCode;

    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_nursetask, null);
        ll_nurseHistory = (LinearLayout) view.findViewById(R.id.ll_nurseHistory);
        ll_nurseExecuteYiZhu = (LinearLayout) view.findViewById(R.id.ll_nurseExecuteYiZhu);
        ll_addNursingCare = (LinearLayout) view.findViewById(R.id.ll_addNursingCare);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);

        ll_ExecuteNursingCare = (LinearLayout) view.findViewById(R.id.ll_ExecuteNursingCare);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_indays = (TextView) view.findViewById(R.id.tv_indays);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {


        SpPationteMsgConfig = getSharedPreferences("PationteMsgConfig", MODE_PRIVATE);
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("我的任务");
        tv_search.setOnClickListener(this);
        ll_nurseHistory.setOnClickListener(this);
        ll_nurseExecuteYiZhu.setOnClickListener(this);
        // tv_nursingContent_executed.setOnClickListener(this);
        ll_addNursingCare.setOnClickListener(this);
        ll_ExecuteNursingCare.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        //  ll_yiZhuManager.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            /*
            *搜索框，二维码扫描
            * */
            case R.id.tv_search:
                Intent intent = new Intent();
                intent.setClass(NurseTaskActivity.this, MyPationteActivity.class);
                startActivityForResult(intent, REQUST_CODE_PATIONTEMSG);
                break;
            /*
            * 护理记录
            * */
            case R.id.ll_nurseHistory:
                Intent NurseHistoryActivityIntent = new Intent(this, NurseHistoryActivity.class);
                if (transName == null || transPationteNo == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    NurseHistoryActivityIntent.putExtra("Name", transName);
                    NurseHistoryActivityIntent.putExtra("patientsNo", transPationteNo);
                    startActivity(NurseHistoryActivityIntent);
                }
                break;
            /*
            * 执行医嘱
            * */
            case R.id.ll_nurseExecuteYiZhu:
                //Intent NurseExecuteActivityIntent = new Intent(this, NurseSelectStageYiZhuActivity.class);//只有一级路径阶段
                 Intent NurseExecuteActivityIntent = new Intent(this, ExecuteYiZhuParentStageActivity.class);
                if (transCpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                }
                if (transCpwCode.equals("")) {
                    MyToast.showToast(this, "此患者还没有配置路径");
                    return;
                } else {
                    NurseExecuteActivityIntent.putExtra("cpwCode", transCpwCode);
                    NurseExecuteActivityIntent.putExtra("patientsNo", transPationteNo);
                    startActivity(NurseExecuteActivityIntent);
                }
                break;
            /*
            * 新增护理
            * */
            case R.id.ll_addNursingCare:
                startActivity(new Intent(this, AddNursingCareActivity.class));
                MyToast.showToast(NurseTaskActivity.this, "此功能正在完善中...");
                break;
            /*
            * 执行护理
            * */
            case R.id.ll_ExecuteNursingCare:
                Intent tv_nursingContentStageIntent = new Intent(this, NursingContentParentStageActivity.class);
                // Intent tv_nursingContentStageIntent = new Intent(this, NursingContentParentStageActivity2.class);
                if (transCpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                }
                if (transCpwCode.equals("")) {
                    MyToast.showToast(this, "此患者还没有配置路径");
                    return;
                } else {
                    tv_nursingContentStageIntent.putExtra("cpwCode", transCpwCode);
                    tv_nursingContentStageIntent.putExtra("patientsNo", transPationteNo);
                    startActivity(tv_nursingContentStageIntent);
                }
                break;
           /* case R.id.tv_nursingContent_executed:
                startActivity(new Intent(this,ExistingNursingContentActivity.class));
                break;*/
            case R.id.bt_titlebar_left:
                finish();
                break;
            case R.id.iv_search:
                Intent openCameraIntent = new Intent(NurseTaskActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
          /*  case R.id.ll_YiZhuManager:
                Intent YiZhuManagerActivityIntent = new Intent(this, DoctorYiZhuManagerActivity.class);
                if (name == null || patientsNo == null || cpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    YiZhuManagerActivityIntent.putExtra("cpwCode", cpwCode);
                    YiZhuManagerActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(YiZhuManagerActivityIntent);
                }
                break;*/
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUST_CODE_PATIONTEMSG:
                if (data!=null){
                    name = data.getStringExtra("Name");
                    patientsNo = data.getStringExtra("patientsNo");
                    deptCode = data.getStringExtra("deptCode");
                    deptName = data.getStringExtra("deptName");
                    cpwCode=data.getStringExtra("cpwCode");

                    transCpwCode=cpwCode;
                    transName=name;
                    transPationteNo=patientsNo;
                    transDeptName=deptName;
                    transDeptCode=deptCode;

                    SpPationteMsgConfig.edit().putString("patientsNo", transPationteNo).commit();
                    SpPationteMsgConfig.edit().putString("Name", transName).commit();
                    SpPationteMsgConfig.edit().putString("deptCode", transDeptCode).commit();
                    SpPationteMsgConfig.edit().putString("deptName", transDeptName).commit();


                    MyLog.i("cpwCode", transCpwCode);
                    tv_search.setText("姓名：" + transName + "   编号：" + transPationteNo);
                    getIndaysAndEnterCPWDays(transPationteNo);
                }

                break;
            case REQUST_CODE_SCANBAR:
                if (resultCode == -1) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        // 结果
                        codeinformation = bundle.getString("result");
                        MyToast.showToast(NurseTaskActivity.this, codeinformation);
                        getPationteName();
                    } else {
                        MyToast.showToast(NurseTaskActivity.this, "没有结果");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getIndaysAndEnterCPWDays(String transPationteNo) {
        OkHttpUtils.get().url(MyConstant.BASE_URL + MyConstant.INDAYSANDENTERCPWDAYS+transPationteNo).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                // dismissWaitingDialog();
                //  String result = response.body().string();


                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NurseTaskActivity.this, "请检查网络设置或稍后再试");

            }

            @Override
            public void onResponse(Object o, int i) {
               // parseSinglePationteJson((String) o);
                parseIndaysAndentercpwdaysJson((String) o);
            }
        });
    }

    private void parseIndaysAndentercpwdaysJson(String o) {
        try {
            JSONObject jsonObject = new JSONObject(o);
            String enterCPWDays = jsonObject.optString("EnterCPWDays");
            MyLog.i("enterCPWDays::", enterCPWDays);
            String inDays = jsonObject.optString("InDays");
            MyLog.i("InDays::", inDays);
            tv_indays.setText("该患者进入路径"+enterCPWDays+"天,"+"住院"+inDays+"天");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void getPationteName() {
        OkHttpUtils.get().url(MyConstant.BASE_URL + MyConstant.PATIONTE_SINGLE + codeinformation).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                // dismissWaitingDialog();
                //  String result = response.body().string();


                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NurseTaskActivity.this, "请检查网络设置或稍后再试");

            }

            @Override
            public void onResponse(Object o, int i) {
                parseSinglePationteJson((String) o);
            }
        });
    }

    private void parseSinglePationteJson(String o) {
        ArrayList<PationteBean> pationteSingle = new Gson().fromJson(o, new TypeToken<List<PationteBean>>() {
        }.getType());
        if (pationteSingle.size() > 0) {
            for (int i = 0; i < pationteSingle.size(); i++) {
                String name = pationteSingle.get(i).Name;
                String cpwCode = pationteSingle.get(i).CPWCode;
                String deptCode = pationteSingle.get(i).DeptCode;
                String deptName = pationteSingle.get(i).DeptName;

                transName=name;
                transPationteNo=codeinformation;
                transCpwCode=cpwCode;
                transDeptCode=deptCode;
                transDeptName=deptName;
                tv_search.setText("姓名: " + transName + "编号: " + codeinformation);
                getIndaysAndEnterCPWDays(transPationteNo);
                MyLog.i("codeinformation::", codeinformation);
            }
        } else {
            tv_search.setText(codeinformation);
            transName=null;
            MyToast.showToast(NurseTaskActivity.this,"该患者不存在！");
            tv_indays.setText("该患者不存在");
        }
    }
}
