package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseUnExecuteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/4.
 */
public class NurseUnxecuteYiZhuActivitye extends BaseActivity {

    private String stageCode;
    private String patientsNo;
    private List<NurseUnExecuteBean.RowsBean> rows;
    private ListView lv;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nurseselectexecute, null);
        lv = (ListView) view.findViewById(R.id.lv);
        return view;
    }

    @Override
    public void InitData() {
        Intent intent = getIntent();
        stageCode = intent.getStringExtra("StageCode");
        patientsNo = intent.getStringExtra("patientsNo");
        MyLog.i("stageCode:::::", stageCode);
        MyLog.i("patientsNo:::::", patientsNo);
        getUnExecuteStage();


    }

    private void getUnExecuteStage() {
        OkHttpUtils.post()
                .url(MyConstant.NURSE_UNEXECUTE_STAGE)
                .addParams("PatientsNo", patientsNo)
                .addParams("stagecode", stageCode)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {
                        MyToast.showToast(NurseUnxecuteYiZhuActivitye.this, response.body().string());
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        e.printStackTrace();
                        MyLog.i("e.printStackTrace();", e.toString());
                    }

                    @Override
                    public void onResponse(Object o, int i) {
                        parseJson((String) o);

                    }
                });
    }

    private void parseJson(String o) {
        NurseUnExecuteBean nurseUnExecuteBean = new Gson().fromJson(o, NurseUnExecuteBean.class);
        rows = nurseUnExecuteBean.getRows();
        lv.setAdapter(new MyAdapter());

    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return rows.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder viewHolder=null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_nurse_unexecuteyizhu, null);

            } else {
                view = convertView;
            }


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
            public TextView tv_search;
            public TextView tv_OrderName;
            public TextView tv_OrderType;
            public TextView tv_Specs;
            public TextView tv_Unit;
            public TextView tv_Dose;
            public TextView tv_Way;
            public TextView tv_Frequency;
            public TextView tv_DurationDuration;
            public TextView tv_DurationUnit;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_search = (TextView) rootView.findViewById(R.id.tv_search);
                this.tv_OrderName = (TextView) rootView.findViewById(R.id.tv_OrderName);
                this.tv_OrderType = (TextView) rootView.findViewById(R.id.tv_OrderType);
                this.tv_Specs = (TextView) rootView.findViewById(R.id.tv_Specs);
                this.tv_Unit = (TextView) rootView.findViewById(R.id.tv_Unit);
                this.tv_Dose = (TextView) rootView.findViewById(R.id.tv_Dose);
                this.tv_Way = (TextView) rootView.findViewById(R.id.tv_Way);
                this.tv_Frequency = (TextView) rootView.findViewById(R.id.tv_Frequency);
                this.tv_DurationDuration = (TextView) rootView.findViewById(R.id.tv_DurationDuration);
                this.tv_DurationUnit = (TextView) rootView.findViewById(R.id.tv_DurationUnit);
            }

        }
    }

}
