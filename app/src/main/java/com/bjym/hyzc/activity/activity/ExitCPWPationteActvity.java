package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.ExitCPWPationtebean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/5.
 */
public class ExitCPWPationteActvity extends BaseActivity {

    private ListView lv;
    private List<ExitCPWPationtebean> exitCPWPationtebeanlists;



    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.actvity_exitcpwpationte, null);
        lv = (ListView) view.findViewById(R.id.lv);

        return view;
    }

    @Override
    public void InitData() {
        getExitCPWPationte();

    }

    private void getExitCPWPationte() {
        OkHttpUtils.get().url(MyConstant.PATIONTE_EXITCPW_MSG).build().execute(new Callback() {
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
                //MyToast.showToast(ExitCPWPationteActvity.this, "请求成功" + o);
            }
        });

    }


    private void parseJson(String o) {
        exitCPWPationtebeanlists = new Gson().fromJson(o, new TypeToken<List<ExitCPWPationtebean>>() {
        }.getType());
        MyLog.i("exitCPWPationtebeanlists", exitCPWPationtebeanlists.size() + "");
        lv.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return exitCPWPationtebeanlists.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder vh=null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_exit_pationtemsg, null);
                vh=new ViewHolder(view);
                view.setTag(vh);
            } else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }
            vh.tv_BeginTime.setText(exitCPWPationtebeanlists.get(position).BeginTime);
            vh.tv_CPWName.setText(exitCPWPationtebeanlists.get(position).CPWName);
            vh.tv_CardNo.setText(exitCPWPationtebeanlists.get(position).CardNo);
            vh.tv_Name.setText(exitCPWPationtebeanlists.get(position).Name);
            vh.tv_PatientsNo.setText(exitCPWPationtebeanlists.get(position).PatientsNo);
            vh.tv_InDate.setText(exitCPWPationtebeanlists.get(position).InDate);
            vh.tv_CPState.setText(exitCPWPationtebeanlists.get(position).CPState);
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
            public TextView tv_CPWName;
            public TextView tv_CardNo;
            public TextView tv_Name;
            public TextView tv_PatientsNo;
            public TextView tv_InDate;
            public TextView tv_BeginTime;
            public TextView tv_CPState;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_CPWName = (TextView) rootView.findViewById(R.id.tv_CPWName);
                this.tv_CardNo = (TextView) rootView.findViewById(R.id.tv_CardNo);
                this.tv_Name = (TextView) rootView.findViewById(R.id.tv_Name);
                this.tv_PatientsNo = (TextView) rootView.findViewById(R.id.tv_PatientsNo);
                this.tv_InDate = (TextView) rootView.findViewById(R.id.tv_InDate);
                this.tv_BeginTime = (TextView) rootView.findViewById(R.id.tv_BeginTime);
                this.tv_CPState = (TextView) rootView.findViewById(R.id.tv_CPState);
            }
        }
    }
}
