package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.ExitCPWCauseBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/5.
 */
public class ExitCPWCauseActvity extends BaseActivity {


    private ListView lv;
    private List<ExitCPWCauseBean.RowsBean> rows;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.actvity_exitcpwacause, null);
        lv = (ListView) view.findViewById(R.id.lv);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView)view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("退出路径的原因分析");
        bt_titlebar_left.setOnClickListener(this);
        getExitCPWCauseData();

    }

    private void getExitCPWCauseData() {
        OkHttpUtils.get().url(MyConstant.PATIONTE_EXITCPW_CAUSE).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(Object o, int i) {
                parseJson((String) o);
              //  MyToast.showToast(ExitCPWCauseActvity.this, "请求成功" + o);
                MyLog.i("ExitCPWCauseData", o + "");
            }
        });
    }

    private void parseJson(String o) {
        ExitCPWCauseBean exitCPWCauseBean = new Gson().fromJson(o, ExitCPWCauseBean.class);
        rows = exitCPWCauseBean.getRows();
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
            ViewHolder vh=null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_exit_cause, null);
                vh=new ViewHolder(view);
                view.setTag(vh);
            }else{
                view=convertView;
            }
            vh.tv_VariationNo.setText(rows.get(position).VariationNo);
            vh.tv_PatientsNo.setText(rows.get(position).PatientsNo);
            vh.tv_Name.setText(rows.get(position).Name);
            vh.tv_VariationCode.setText(rows.get(position).VariationCode);
            vh.tv_VariationType.setText(rows.get(position).VariationType);
            vh.tv_VariationName.setText(rows.get(position).VariationName);
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
            public TextView tv_VariationNo;
            public TextView tv_PatientsNo;
            public TextView tv_Name;
            public TextView tv_VariationCode;
            public TextView tv_VariationType;
            public TextView tv_VariationName;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_VariationNo = (TextView) rootView.findViewById(R.id.tv_VariationNo);
                this.tv_PatientsNo = (TextView) rootView.findViewById(R.id.tv_PatientsNo);
                this.tv_Name = (TextView) rootView.findViewById(R.id.tv_Name);
                this.tv_VariationCode = (TextView) rootView.findViewById(R.id.tv_VariationCode);
                this.tv_VariationType = (TextView) rootView.findViewById(R.id.tv_VariationType);
                this.tv_VariationName = (TextView) rootView.findViewById(R.id.tv_VariationName);
            }

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
