package com.bjym.hyzc.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NursingContentBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/9.
 */
public class NursingContentSelectActivity extends BaseActivity {
    private ListView lv;
    private String cpwCode;
    private List<NursingContentBean.RowsBean> rows;
    private List<NursingContentBean.RowsBean> existingNursingContents = null;
    private TextView tv_none_nurseContent;
    private TextView tv_search;


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("确定执行此护理内容？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NursingContentBean.RowsBean rowsBean = rows.get(position);
                    if (existingNursingContents == null) {
                        existingNursingContents = new ArrayList<>();
                    }
                    //把得到的集合传递给下一个现有页面
                    existingNursingContents.add(rowsBean);
                    MyLog.i("existingNursingContents", existingNursingContents.size() + "");

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            builder.show();

        }
    }

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursingcontentselect, null);
        tv_none_nurseContent = (TextView) view.findViewById(R.id.tv_none_nurseContent);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        lv = (ListView) view.findViewById(R.id.lv);

        return view;
    }

    @Override
    public void InitData() {

        cpwCode = getIntent().getStringExtra("cpwCode");

        getNursingContentData();

        lv.setOnItemClickListener(new MyOnItemClickListener());
        tv_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                Intent existingNursingContentIntent = new Intent(this, ExistingNursingContentActivity.class);
                existingNursingContentIntent.putExtra("existingNursingContents", (Serializable) existingNursingContents);
                startActivity(existingNursingContentIntent);
                break;
        }
    }

    private void getNursingContentData() {
        OkHttpUtils.get().url(MyConstant.NURSING_CONTENT_SELECT + cpwCode).build().execute(new Callback() {

            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                MyToast.showToast(NursingContentSelectActivity.this, "请求成功" + "getNursingContentData");

                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NursingContentSelectActivity.this, "请求失败");
            }

            @Override
            public void onResponse(Object o, int in) {
                parseMyselfJson((String) o);
            }
        });
    }

    private void parseMyselfJson(String o) {
        NursingContentBean nursingContentBean = new Gson().fromJson(o, NursingContentBean.class);
        rows = nursingContentBean.getRows();
        MyLog.i("rows", rows.size() + "");
        /*for (int i = 0; i <rows.size() ; i++) {
            String stageCode = rows.get(i).StageCode;
            if(stageCode.equals("JD000022")){
                String activitiesType = rows.get(i).ActivitiesType;
                String contentCode = rows.get(i).ContentCode;
                String orderType = rows.get(i).OrderType;
                String contentName = rows.get(i).ContentName;
                String orderCategory = rows.get(i).OrderCategory;
                String medicalRecord = rows.get(i).MedicalRecord;
                String cPWCode = rows.get(i).CPWCode;
                String cPWType = rows.get(i).CPWType;
                String contentType = rows.get(i).ContentType;
                int tempid = rows.get(i).tempid;
                NursingContentBean.RowsBean rowsBean=new NursingContentBean.RowsBean(contentCode,contentName,stageCode,contentType,cPWType,orderType,activitiesType,orderCategory,medicalRecord,cPWCode,tempid);
                RowsBeans=new ArrayList<>();
                RowsBeans.add(rowsBean);
                MyLog.i("RowsBeans",RowsBeans.size()+"");
            }
        }*/
        if (rows.size() == 0) {
            tv_none_nurseContent.setVisibility(View.VISIBLE);
        } else {
            lv.setAdapter(new MyAdapter());
        }


    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return rows.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder vh = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_nursecontent_select, null);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }
            vh.tv_ContentName.setText(rows.get(position).ContentName);
            vh.tv_ContentCode.setText(rows.get(position).ContentCode);
            vh.tv_StageCode.setText(rows.get(position).StageCode);
            vh.tv_ContentType.setText(rows.get(position).ContentType);
            vh.tv_CPWType.setText(rows.get(position).CPWType);
            vh.tv_OrderType.setText(rows.get(position).OrderType);
            vh.tv_OrderCategory.setText(rows.get(position).OrderCategory);
            vh.tv_CPWCode.setText(rows.get(position).CPWCode);
            vh.tv_MedicalRecord.setText(rows.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(rows.get(position).ActivitiesType);
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


        public class ViewHolder {
            public View rootView;
            public TextView tv_ContentName;
            public TextView tv_ContentCode;
            public TextView tv_StageCode;
            public TextView tv_ContentType;
            public TextView tv_CPWType;
            public TextView tv_OrderType;
            public TextView tv_OrderCategory;
            public TextView tv_CPWCode;
            public TextView tv_ActivitiesType;
            public TextView tv_MedicalRecord;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_ContentName = (TextView) rootView.findViewById(R.id.tv_ContentName);
                this.tv_ContentCode = (TextView) rootView.findViewById(R.id.tv_ContentCode);
                this.tv_StageCode = (TextView) rootView.findViewById(R.id.tv_StageCode);
                this.tv_ContentType = (TextView) rootView.findViewById(R.id.tv_ContentType);
                this.tv_CPWType = (TextView) rootView.findViewById(R.id.tv_CPWType);
                this.tv_OrderType = (TextView) rootView.findViewById(R.id.tv_OrderType);
                this.tv_OrderCategory = (TextView) rootView.findViewById(R.id.tv_OrderCategory);
                this.tv_CPWCode = (TextView) rootView.findViewById(R.id.tv_CPWCode);
                this.tv_ActivitiesType = (TextView) rootView.findViewById(R.id.tv_ActivitiesType);
                this.tv_MedicalRecord = (TextView) rootView.findViewById(R.id.tv_MedicalRecord);
            }

        }
    }
}
