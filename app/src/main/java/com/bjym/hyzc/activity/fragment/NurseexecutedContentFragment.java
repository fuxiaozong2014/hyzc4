package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NursingContentBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/9/7.
 */
public class NurseExecutedContentFragment extends BaseFragment{

    private TextView tv_none_nurseContent;
    private ListView lv;
    private String currentStageCode;
    private String patientsNo;
    private List<NursingContentBean.RowsBean> rowsExecutedContent;

    @Override
    public View setMainView() {
       View view=View.inflate(context, R.layout.fragment_nurseexecutedcontent,null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_none_nurseContent = (TextView) view.findViewById(R.id.tv_none_nurseContent);

        return view;
    }

    @Override
    public void InitData() {
        Intent intent=getActivity().getIntent();
        String stageCodeParent = intent.getStringExtra("StageCodeParent");
        String stageCodeChild = intent.getStringExtra("stageCodeChild");
        patientsNo = intent.getStringExtra("patientsNo");
        if (stageCodeParent == null) {
            currentStageCode=stageCodeChild;
        }else {
            currentStageCode=stageCodeParent;
        }

        getNurseExecutedContent();
    }

    private void getNurseExecutedContent() {
        OkHttpUtils.get()
                .url(MyConstant.BASE_URL+ MyConstant.NURSE_EXECUTE_CONTENT+patientsNo+"&stagecode="+currentStageCode)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {
                       // MyToast.showToast(NurseExecutedContentFragment.this.getActivity(), response.body().string());
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        e.printStackTrace();
                        MyLog.i("e.printStackTrace();", e.toString());
                    }

                    @Override
                    public void onResponse(Object o, int i) {
                        //MyToast.showToast(NurseExecutedContentFragment.this.getActivity(), (String) o);
                        parseJson((String) o);
                    }
                });

    }

    private void parseJson(String o) {
        NursingContentBean nursingContentBean = new Gson().fromJson(o, NursingContentBean.class);
        rowsExecutedContent = nursingContentBean.getRows();
        if (rowsExecutedContent.size()==0){
            lv.setVisibility(View.GONE);
            tv_none_nurseContent.setVisibility(View.VISIBLE);
        }

        MyLog.i("rowsSundfgfg", rowsExecutedContent.size() + "fdfdf");
        lv.setAdapter(new MyAdapter());

    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return rowsExecutedContent.size();
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
            vh.tv_ContentName.setText(rowsExecutedContent.get(position).ContentName);
            vh.tv_ContentType.setText(rowsExecutedContent.get(position).ContentType);
            vh.tv_OrderType.setText(rowsExecutedContent.get(position).OrderType);
            vh.tv_OrderCategory.setText(rowsExecutedContent.get(position).OrderCategory);
            vh.tv_MedicalRecord.setText(rowsExecutedContent.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(rowsExecutedContent.get(position).ActivitiesType);
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
