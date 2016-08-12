package com.bjym.hyzc.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.MyPationteSimpleMsgActivity;
import com.bjym.hyzc.activity.activity.SurveyActivity;
import com.bjym.hyzc.activity.bean.DiaoChaSortBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class DiaoChaFragment extends BaseFragment {

    private ListView ll_diaoChaSort;
    private List<DiaoChaSortBean.RowsBean> rowsBeans;
    private DiaoChaSortBean.RowsBean rowsBean;
    private String surveyNo;
    private String surveyName;
    private MyAdapter adpter;
    private RelativeLayout re_search;
    private TextView tv_search;
    private MyBrocastReceiver receiver;
    private LocalBroadcastManager broadcastManager;
    private String name;
    private String patientsNo;
    private String userCode;
    private String realName;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_diaocha, null);
        ll_diaoChaSort = (ListView) view.findViewById(R.id.ll_diaoChaSort);
        //re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        ll_diaoChaSort.setOnItemClickListener(new MyOnItemClickListner());
        tv_search.setOnClickListener(this);
        return view;
    }

    @Override
    public void InitData() {

        /*
        * 得到用户名和真实姓名，提交使用
        * */
        Bundle bundle = getArguments();
        if (bundle != null) {
            userCode = bundle.getString("userCode");
            realName = bundle.getString("realName");
        }



        
         /*
        * 得到调查问卷的类型
        * */
        getSurveySortData();
    }

    private void getSurveySortData() {
        //请求调查类型
        OkHttpUtils.get().url(MyConstant.MYDIAOCHA_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                //  MyToast.showToast(DiaoChaFragment.this.getActivity(), "success" + result);
                parseSurveySortJson(result);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object o, int i) {
                if (adpter == null) {
                    adpter = new MyAdapter();
                    ll_diaoChaSort.setAdapter(adpter);
                } else {
                    adpter.notifyDataSetChanged();
                }
            }
        });
    }

    private void parseSurveySortJson(String result) throws JSONException {
        //TODO here parse diaocha sort
        Gson gson = new Gson();
        DiaoChaSortBean diaoChaSortBean = gson.fromJson(result, DiaoChaSortBean.class);
        rowsBeans = diaoChaSortBean.getRows();
        for (int i = 0; i < rowsBeans.size(); i++) {
            rowsBean = rowsBeans.get(i);
            surveyNo = rowsBean.SurveyNo;
        }
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return rowsBeans.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            TextView tv_diaoChaSort = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_diaochasort, null);
            } else {
                view = convertView;
            }
            tv_diaoChaSort = (TextView) view.findViewById(R.id.tv_diaoChaSort);
            tv_diaoChaSort.setText(rowsBeans.get(position).SurveyName);
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
    }

    /*
    *用户点击调查问卷条目的监听
    * */
    public class MyOnItemClickListner implements AdapterView.OnItemClickListener {


        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           /*
           * 1.必须选择调查人，否则不能开始调查功能
           *TODO surveyed 既能输入又能选择
           * 2.传递调查者姓名和编号，患者姓名和编号以及问卷编码到surveyActivity 用于提交数据
           * */
            if (name != null) {
                Intent intent = new Intent(context, SurveyActivity.class);
                rowsBean = rowsBeans.get(position);
                surveyNo = rowsBean.SurveyNo;
                surveyName = rowsBean.SurveyName;
                /*
                * 调查表的名字
                * */
                intent.putExtra("SurveyName", surveyName);
                /*
                * 调查表的编号
                * */
                intent.putExtra("surveyNo", surveyNo);

                /*
                * 患者姓名和患者编号
                * */
                intent.putExtra("Name", name);
                intent.putExtra("patientsNo", patientsNo);

                /*
                * 用户编码和真实姓名
                * */
                intent.putExtra("userCode", userCode);
                intent.putExtra("realName", realName);

                startActivity(intent);
            } else {
                MyToast.showToast(DiaoChaFragment.this.getActivity(), "请选择调查人");
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                /*
                * click here jump to mypationte activity for surveyed's data
                * */
                startActivity(new Intent(context, MyPationteSimpleMsgActivity.class));
                break;
        }
    }

    /*
    * 收到患者详情发来的广播，得到患者姓名和患者编号
    * */
    public class MyBrocastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
            * 得到患者姓名和患者标号
            * */
            name = intent.getStringExtra("Name");
            patientsNo = intent.getStringExtra("patientsNo");
            MyLog.i("DATA", "Name：" + name + "   patientsNo:" + patientsNo);
            tv_search.setText("姓名：" + name + "    患者编号：" + patientsNo);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        receiver = new MyBrocastReceiver();
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("INTEN_MYPATIONTE");
        broadcastManager.registerReceiver(receiver, intentFilter);
      //  MyToast.showToast(getActivity(), "收到广播了");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(receiver);
    }


}
