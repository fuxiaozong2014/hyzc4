package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by fushaoqing on 2016/8/16.
 */
public class PationtNameListActivity extends BaseActivity {
    private ListView lv_mypationte;
    private PationtNameListActivity.MyAdapter adpter;
    private List<PationteBean> pationtes;
    private TextView tv_mypationtenone;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private RelativeLayout Rela_no_wifi;
    private SwipeRefreshLayout swipeRefresh;
    public static final int SWIPEREFRESH_COMPLETE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SWIPEREFRESH_COMPLETE:
                    adpter.notifyDataSetChanged();
                    MyToast.showToast(PationtNameListActivity.this, "刷新完成");
                    swipeRefresh.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

        @Override
        public View setMainView() {
            View view = View.inflate(context, R.layout.activity_pationtnamelist, null);
            lv_mypationte = (ListView) view.findViewById(R.id.lv_mypationte);
            swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
            tv_mypationtenone = (TextView) view.findViewById(R.id.tv_mypationtenone);
            Rela_no_wifi = (RelativeLayout) view.findViewById(R.id.Rela_no_wifi);

            bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
            bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
            tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
            return view;
        }

        @Override
        public void InitData() {
           // swipeRefresh.setEnabled(false);
            swipeRefresh.setColorSchemeResources(android.R.color.holo_green_dark,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

            bt_titlebar_left.setVisibility(View.VISIBLE);
            bt_titlebar_right.setVisibility(View.GONE);
            tv_titlebar_center.setText("患者列表");

            bt_titlebar_left.setOnClickListener(this);
            lv_mypationte.setOnItemClickListener(new MyOnItemClickListner());
            lv_mypationte.setOnItemClickListener(new MyOnItemClickListner());

            getNetData();

            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    handler.sendEmptyMessageDelayed(SWIPEREFRESH_COMPLETE, 1000);
                }
            });

        }

        private void getNetData() {
            showDialogProgress("加载中...");
            OkHttpUtils.get().url(MyConstant.MYPATIONTE_URL).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int i) throws Exception {
                    String result = response.body().string();
                    parseJson(result);
                    return null;
                }

                @Override
                public void onError(Call call, Exception e, int i) {

                    dismiss();
                    Rela_no_wifi.setVisibility(View.VISIBLE);
                    MyToast.showToast(PationtNameListActivity.this, "服务器正忙，请稍后重试");
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
                    view = View.inflate(context, R.layout.item_list_pationtenamelist, null);
                    holder = new ViewHolder(view);
                    view.setTag(holder);
                } else {
                    view = convertView;
                    holder = (ViewHolder) view.getTag();
                }
                holder.myPationte_name.setText(pationtes.get(position).Name);
                holder.myPationte_bednumber.setText(pationtes.get(position).BedNo);
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
                public TextView myPationte_name;
                public TextView myPationte_bednumber;

                public ViewHolder(View rootView) {
                    this.rootView = rootView;
                    this.myPationte_name = (TextView) rootView.findViewById(R.id.myPationte_name);
                    this.myPationte_bednumber = (TextView) rootView.findViewById(R.id.myPationte_bednumber);
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

                PationteBean pationteBean = pationtes.get(position);

                Intent intent = new Intent(context, PationteBestDetailMsgActivity.class);
                intent.putExtra("pationteBean", pationteBean);
                startActivity(intent);
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


