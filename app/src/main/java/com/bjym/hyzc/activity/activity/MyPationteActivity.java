package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
 * Created by Administrator on 2016/7/3 0003.
 */
public class MyPationteActivity extends BaseActivity {

    private ListView lv_mypationte;
    private MyAdapter adpter;
    private List<PationteBean> pationtes;
    private TextView tv_mypationtenone;
    private SwipeRefreshLayout swipeRefresh;
    public static final int SWIPEREFRESH_COMPLETE = 1;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private RelativeLayout rela_no_wifi;

    class MyOnScrollListener implements AbsListView.OnScrollListener{


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {


        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem==0){
                swipeRefresh.setRefreshing(true);
            }else {
                swipeRefresh.setRefreshing(false);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SWIPEREFRESH_COMPLETE:
                    //pationtes.addAll(pationtes);
                    adpter.notifyDataSetChanged();
                    MyToast.showToast(MyPationteActivity.this, "刷新完成");
                    swipeRefresh.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_mypationte, null);
        lv_mypationte = (ListView) view.findViewById(R.id.lv_mypationte);
        tv_mypationtenone = (TextView) view.findViewById(R.id.tv_mypationtenone);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
        rela_no_wifi = (RelativeLayout) view.findViewById(R.id.Rela_no_wifi);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        lv_mypationte.setOnItemClickListener(new MyOnItemClickListner());
        lv_mypationte.setOnItemClickListener(new MyOnItemClickListner());

        return view;
    }


    @Override
    public void InitData() {
        swipeRefresh.setEnabled(false);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("患者详情");
        bt_titlebar_left.setOnClickListener(this);

        getNetData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(SWIPEREFRESH_COMPLETE,1000);
            }
        });

        lv_mypationte.setOnScrollListener(new MyOnScrollListener());
    }

    private void getNetData() {
        showDialogProgress("加载中...");
        OkHttpUtils.get().url(MyConstant.BASE_URL+MyConstant.MYPATIONTE_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                parseJson(result);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                dismiss();
                rela_no_wifi.setVisibility(View.VISIBLE);
                MyToast.showToast(MyPationteActivity.this, "服务器正忙，请稍后重试");
            }

            @Override
            public void onResponse(Object o, int i) {
                dismiss();
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
                view = View.inflate(context, R.layout.item_list_pationtesimplemsg, null);
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
                this.myPationte_data = (TextView) rootView.findViewById(R.id.myPationte_date);
            }

        }
    }

    private void parseJson(String result) throws JSONException {

        pationtes = new Gson().fromJson(result, new TypeToken<List<PationteBean>>() {
        }.getType());
        for (PationteBean pationte : pationtes) {
            System.out.println(pationte.toString());
        }
    }

    /*
    * 患者信息列表的点击监听
    * */
    public class MyOnItemClickListner implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /*
            * 1.点击条目，缓存病人姓名
            * 2.发送广播，传递数据给DiaoChaFragment
            * */
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);

            String name = pationtes.get(position).Name;
            String patientsNo = pationtes.get(position).PatientsNo;
            String cpwCode = pationtes.get(position).CPWCode;
            String deptCode = pationtes.get(position).DeptCode;
            String deptName = pationtes.get(position).DeptName;


            Intent intent = new Intent();
            intent.setAction("INTEN_MYPATIONTE");
            intent.putExtra("Name", name);
            intent.putExtra("patientsNo", patientsNo);
            manager.sendBroadcast(intent);

            Intent intentToMyTask = new Intent();
            intentToMyTask.putExtra("Name", name);
            intentToMyTask.putExtra("patientsNo", patientsNo);
            intentToMyTask.putExtra("cpwCode", cpwCode);
            intentToMyTask.putExtra("deptCode", deptCode);
            intentToMyTask.putExtra("deptName", deptName);
            setResult(RESULT_OK, intentToMyTask);
            finish();

            // startActivity(new Intent(MyPationteActivity.this,PationteDetailMsgActivity.class));
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }
}
