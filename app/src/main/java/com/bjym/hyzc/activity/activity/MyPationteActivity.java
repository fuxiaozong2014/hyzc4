package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.Myself;
import com.bjym.hyzc.activity.bean.Pationte;
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
 * Created by Administrator on 2016/7/3 0003.
 */
public class MyPationteActivity extends BaseActivity {

    private ListView lv_mypationte;
    private List<Myself> myselefLists;
    private MyAdapter adpter;
    private List<Pationte> pationtes;
    private TextView tv_mypationtenone;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_mypationte, null);
        lv_mypationte = (ListView) view.findViewById(R.id.lv_mypationte);
        tv_mypationtenone = (TextView) view.findViewById(R.id.tv_mypationtenone);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
        lv_mypationte.setOnItemClickListener(new MyOnItemClickListner());
        lv_mypationte.setOnItemClickListener(new MyOnItemClickListner());

        return view;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            swipeRefresh.setRefreshing(false);
            MyToast.showToast(MyPationteActivity.this, "刷新完成");
        }
    };

    @Override
    public void InitData() {

        getNetData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetData();
                handler.sendEmptyMessage(0);
            }
        });


    }

    private void getNetData() {
        OkHttpUtils.get().url(MyConstant.MYPATIONTE_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                //  MyToast.showToast(MyPationteActivity.this, "请求成功" + result);
                parseJson(result);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(MyPationteActivity.this, "服务器正忙，请稍后重试");
            }

            @Override
            public void onResponse(Object o, int i) {

                if (pationtes.size() == 0) {
                    lv_mypationte.setVisibility(View.GONE);
                    tv_mypationtenone.setVisibility(View.VISIBLE);
                } else {
                    lv_mypationte.setVisibility(View.VISIBLE);
                    tv_mypationtenone.setVisibility(View.GONE);
                    if (adpter == null) {
                        adpter = new MyAdapter();
                        lv_mypationte.setAdapter(adpter);
                    } else {
                        adpter.notifyDataSetChanged();
                    }
                }

            }
        });
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
                view = View.inflate(context, R.layout.item_list_pationtemsg, null);
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
            holder.myPationte_data.setText(pationtes.get(position).InDate);
            return view;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        public class ViewHolder {
            public View rootView;
            public TextView myPationte_number;
            public TextView myPationte_name;
            public TextView myPationte_sex;
            public TextView myPationte_phone;
            public TextView myPationte_bednumber;
            public TextView myPationte_CPWName;
            public TextView myPationte_data;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.myPationte_number = (TextView) rootView.findViewById(R.id.myPationte_number);
                this.myPationte_name = (TextView) rootView.findViewById(R.id.myPationte_name);
                this.myPationte_sex = (TextView) rootView.findViewById(R.id.myPationte_sex);
                this.myPationte_phone = (TextView) rootView.findViewById(R.id.myPationte_phone);
                this.myPationte_bednumber = (TextView) rootView.findViewById(R.id.myPationte_bednumber);
                this.myPationte_CPWName = (TextView) rootView.findViewById(R.id.myPationte_CPWName);
                this.myPationte_data = (TextView) rootView.findViewById(R.id.myPationte_data);
            }

        }
    }

    private void parseJson(String result) throws JSONException {

        pationtes = new Gson().fromJson(result, new TypeToken<List<Pationte>>() {
        }.getType());
        for (Pationte pationte : pationtes) {
            System.out.println(pationte.toString());
        }


    }

    public class MyOnItemClickListner implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //跳转到病人详情页面
            startActivity(new Intent(context, PationteDetailMsgActivity.class));
        }
    }

}
