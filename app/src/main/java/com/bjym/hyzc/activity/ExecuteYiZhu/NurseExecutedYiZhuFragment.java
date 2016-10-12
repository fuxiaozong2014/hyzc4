package com.bjym.hyzc.activity.ExecuteYiZhu;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.ExecuteNursingCare.NurseUnExecuteBean;
import com.bjym.hyzc.activity.fragment.BaseFragment;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/9/6.
 */
public class NurseExecutedYiZhuFragment extends BaseFragment {
    private TextView tv_none_executeYiZhu;
    private ListView lv;
    private List<NurseUnExecuteBean.RowsBean> rows;
    private String stageCodeParent;
    private String patientsNo;
    private MyAdapter adapter;
    private String currentStageCode;
    @Override
    public View setMainView() {
        View view=View.inflate(context, R.layout.fragement_executedyizhu,null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_none_executeYiZhu = (TextView)view.findViewById(R.id.tv_none_executeYiZhu);
        return view;
    }

    @Override
    public void InitData() {
        Intent intent = getActivity().getIntent();
        stageCodeParent = intent.getStringExtra("stageCodeParent");
        String stageCodeChild = intent.getStringExtra("stageCodeChild");
        String stageCodeSun = intent.getStringExtra("stageCodeSun");

        patientsNo = intent.getStringExtra("patientsNo");
        MyLog.i("patientsNo:::::NurseExecutedYiZhuFragment", patientsNo);
        if (stageCodeParent == null && stageCodeChild == null) {
            currentStageCode=stageCodeSun;
        }
        if (stageCodeParent == null && stageCodeSun==null){
            currentStageCode=stageCodeChild;
        }
        if (stageCodeChild == null && stageCodeSun==null){
            currentStageCode=stageCodeParent;
        }

        getExecuteYiZhu(currentStageCode);
    }


    private void getExecuteYiZhu(String currentStageCode) {
        //String url=MyConstant.BASE_URL+ MyConstant.NURSE_EXECUTE_YIZHU+patientsNo+"&stagecode="+stageCodeParent;
       // MyLog.i("NurseExecutedYiZhuFragment-----------------url",url);
        OkHttpUtils.get()
                .url(MyConstant.BASE_URL+ MyConstant.NURSE_EXECUTE_YIZHU+patientsNo+"&stagecode="+currentStageCode)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {
                         //MyToast.showToast(NurseExecutedYiZhuFragment.this.getActivity(), response.body().string());
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        e.printStackTrace();
                        MyLog.i("e.printStackTrace();", e.toString());
                    }

                    @Override
                    public void onResponse(Object o, int i) {
                        //MyToast.showToast(NurseExecutedYiZhuFragment.this.getActivity(), (String) o);
                        parseJson((String) o);
                    }
                });
    }

    private void parseJson(String o) {
        NurseUnExecuteBean nurseUnExecuteBean = new Gson().fromJson(o, NurseUnExecuteBean.class);
        rows = nurseUnExecuteBean.getRows();
        if(rows.size()==0){
            lv.setVisibility(View.GONE);

            tv_none_executeYiZhu.setVisibility(View.VISIBLE);
        }
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
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
                viewHolder=new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder= (ViewHolder) view.getTag();
            }

            viewHolder.tv_OrderName.setText(rows.get(position).OrderName);
            viewHolder.tv_OrderType.setText(rows.get(position).OrderType);
            viewHolder.tv_Specs.setText(rows.get(position).Specs);
            viewHolder.tv_Unit.setText(rows.get(position).Unit);
            viewHolder.tv_Dose.setText(rows.get(position).Dose);
            viewHolder.tv_Way.setText(rows.get(position).Way);
            viewHolder.tv_Frequency.setText(rows.get(position).Frequency);
            viewHolder.tv_Duration.setText(rows.get(position).Duration);
            viewHolder.tv_DurationUnit.setText(rows.get(position).DurationUnit);

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
            public TextView tv_Duration;
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
                this.tv_Duration= (TextView) rootView.findViewById(R.id.tv_Duration);
                this.tv_DurationUnit = (TextView) rootView.findViewById(R.id.tv_DurationUnit);
            }
        }
    }
}
