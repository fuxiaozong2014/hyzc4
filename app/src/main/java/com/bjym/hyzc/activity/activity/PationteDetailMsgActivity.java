package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.PationteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/6.
 */
public class PationteDetailMsgActivity extends BaseActivity {

    private ListView lv;
    private List<PationteBean> pationtes;
    private TextView tv_mypationtenone;
    private MyAdapter adpter;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_pationtedetailmsg, null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_mypationtenone = (TextView) view.findViewById(R.id.tv_mypationtenone);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView)view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("患者详情");
        bt_titlebar_left.setOnClickListener(this);
        getNetData();
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return pationtes.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_pationdetailmsg, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.myPationte_number.setText(pationtes.get(position).PatientsNo);
            holder.myPationte_name.setText(pationtes.get(position).Name);
            holder.myPationte_sex.setText(pationtes.get(position).Sex);
            holder.myPationte_phone.setText(pationtes.get(position).Mobile);
            holder.myPationte_bednumber.setText(pationtes.get(position).BedNo);
            holder.myPationte_CPWName.setText(pationtes.get(position).CPWName);
            holder.myPationte_date.setText(pationtes.get(position).InDate);
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        public  class ViewHolder {
            public View rootView;
            public TextView myPationte_name;
            public TextView myPationte_sex;
            public TextView myPationte_PYM;
            public TextView myPationte_IDNo;
            public TextView myPationte_phone;
            public TextView myPationte_number;
            public TextView myPationte_bednumber;
            public TextView myPationte_CardNo;
            public TextView myPationte_date;
            public TextView myPationte_DeptName;
            public TextView myPationte_DeptCode;
            public TextView myPationte_NuseCellCode;
            public TextView myPationte_NuseCellName;
            public TextView myPationte_InDocCode;
            public TextView myPationte_InDocName;
            public TextView myPationte_ChargeDocCode;
            public TextView myPationte_ChargeDocName;
            public TextView myPationte_ChifeDocCode;
            public TextView myPationte_ChifeDocName;
            public TextView myPationte_DutyNuseName;
            public TextView myPationte_DutyNuseCode;
            public TextView myPationte_CPWName;
            public TextView myPationte_DiagnosisName;
            public TextView myPationte_CPWCode;
            public TextView myPationte_Source;
            public TextView myPationte_BeginDays;
            public TextView myPationte_BeginTime;
            public TextView myPationte_PatientState;
            public TextView myPationte_CPState;
            public TextView myPationte_StandardCosts;
            public TextView myPationte_Remark;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.myPationte_name = (TextView) rootView.findViewById(R.id.myPationte_name);
                this.myPationte_sex = (TextView) rootView.findViewById(R.id.myPationte_sex);
                this.myPationte_PYM = (TextView) rootView.findViewById(R.id.myPationte_PYM);
                this.myPationte_IDNo = (TextView) rootView.findViewById(R.id.myPationte_IDNo);
                this.myPationte_phone = (TextView) rootView.findViewById(R.id.myPationte_phone);
                this.myPationte_number = (TextView) rootView.findViewById(R.id.myPationte_number);
                this.myPationte_bednumber = (TextView) rootView.findViewById(R.id.myPationte_bednumber);
                this.myPationte_CardNo = (TextView) rootView.findViewById(R.id.myPationte_CardNo);
                this.myPationte_date = (TextView) rootView.findViewById(R.id.myPationte_date);
                this.myPationte_DeptName = (TextView) rootView.findViewById(R.id.myPationte_DeptName);
                this.myPationte_DeptCode = (TextView) rootView.findViewById(R.id.myPationte_DeptCode);
                this.myPationte_NuseCellCode = (TextView) rootView.findViewById(R.id.myPationte_NuseCellCode);
                this.myPationte_NuseCellName = (TextView) rootView.findViewById(R.id.myPationte_NuseCellName);
                this.myPationte_InDocCode = (TextView) rootView.findViewById(R.id.myPationte_InDocCode);
                this.myPationte_InDocName = (TextView) rootView.findViewById(R.id.myPationte_InDocName);
                this.myPationte_ChargeDocCode = (TextView) rootView.findViewById(R.id.myPationte_ChargeDocCode);
                this.myPationte_ChargeDocName = (TextView) rootView.findViewById(R.id.myPationte_ChargeDocName);
                this.myPationte_ChifeDocCode = (TextView) rootView.findViewById(R.id.myPationte_ChifeDocCode);
                this.myPationte_ChifeDocName = (TextView) rootView.findViewById(R.id.myPationte_ChifeDocName);
                this.myPationte_DutyNuseName = (TextView) rootView.findViewById(R.id.myPationte_DutyNuseName);
                this.myPationte_DutyNuseCode = (TextView) rootView.findViewById(R.id.myPationte_DutyNuseCode);
                this.myPationte_CPWName = (TextView) rootView.findViewById(R.id.myPationte_CPWName);
                this.myPationte_DiagnosisName = (TextView) rootView.findViewById(R.id.myPationte_DiagnosisName);
                this.myPationte_CPWCode = (TextView) rootView.findViewById(R.id.myPationte_CPWCode);
                this.myPationte_Source = (TextView) rootView.findViewById(R.id.myPationte_Source);
                this.myPationte_BeginDays = (TextView) rootView.findViewById(R.id.myPationte_BeginDays);
                this.myPationte_BeginTime = (TextView) rootView.findViewById(R.id.myPationte_BeginTime);
                this.myPationte_PatientState = (TextView) rootView.findViewById(R.id.myPationte_PatientState);
                this.myPationte_CPState = (TextView) rootView.findViewById(R.id.myPationte_CPState);
                this.myPationte_StandardCosts = (TextView) rootView.findViewById(R.id.myPationte_StandardCosts);
                this.myPationte_Remark = (TextView) rootView.findViewById(R.id.myPationte_Remark);
            }

        }
    }


    private void getNetData() {
        OkHttpUtils.get().url(MyConstant.MYPATIONTE_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                parseJson(result);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
               // dismissWaitingDialog();
                MyToast.showToast(PationteDetailMsgActivity.this, "服务器正忙，请稍后重试");
            }

            @Override
            public void onResponse(Object o, int i) {

                //dismissWaitingDialog();
                if (pationtes.size() == 0) {
                    lv.setVisibility(View.GONE);
                    tv_mypationtenone.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    tv_mypationtenone.setVisibility(View.GONE);
                    if (adpter == null) {
                        adpter = new MyAdapter();
                        lv.setAdapter(adpter);
                    } else {
                        adpter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

    private void parseJson(String result) throws JSONException {

        pationtes = new Gson().fromJson(result, new TypeToken<List<PationteBean>>() {
        }.getType());
        for (PationteBean pationte : pationtes) {
            System.out.println(pationte.toString());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }
}
