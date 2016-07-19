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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.MyPationteActivity;
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
    private EditText et_search;
    private Button btn_add;
    private MyBrocastReceiver receiver;
    private LocalBroadcastManager broadcastManager;
    private String name;
    private String bedNo;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_diaocha, null);
        ll_diaoChaSort = (ListView) view.findViewById(R.id.ll_diaoChaSort);
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        et_search = (EditText) view.findViewById(R.id.et_search);
        btn_add = (Button) view.findViewById(R.id.btn_add);

        return view;
    }

    @Override
    public void InitData() {
        getNetData();
        ll_diaoChaSort.setOnItemClickListener(new MyOnItemClickListner());
        re_search.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    private void getNetData() {
        //请求调查类型
        OkHttpUtils.get().url(MyConstant.MYDIAOCHA_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                //  MyToast.showToast(DiaoChaFragment.this.getActivity(), "success" + result);
                parseJson(result);
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

    private void parseJson(String result) throws JSONException {
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

    public class MyOnItemClickListner implements AdapterView.OnItemClickListener {


        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           /*
           * 必须选择调查人，否则不能开始调查功能
           *TODO surveyed 既能输入又能选择
           * */
            if (name != null) {
                Intent intent = new Intent(context, SurveyActivity.class);

                rowsBean = rowsBeans.get(position);
                surveyNo = rowsBean.SurveyNo;
                surveyName = rowsBean.SurveyName;
                intent.putExtra("surveyNo", surveyNo);
                intent.putExtra("SurveyName", surveyName);
                intent.putExtra("Name", name);
                intent.putExtra("BedNo", bedNo);

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
            case R.id.btn_add:
                /*
                * click here jump to mypationte activity for surveyed's data
                * */
                startActivity(new Intent(context, MyPationteActivity.class));
                break;

        }
    }

    public class MyBrocastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            name = intent.getStringExtra("Name");
            bedNo = intent.getStringExtra("BedNo");
            MyLog.i("DATA", "Name：" + name + "   BedNo:" + bedNo);
            et_search.setText("姓名：" + name + "    床位号：" + bedNo);
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
        MyToast.showToast(getActivity(), "收到广播了");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(receiver);
    }


    public interface callBack{
        public void sendUserData(String userName,String keShi);

    }
}
