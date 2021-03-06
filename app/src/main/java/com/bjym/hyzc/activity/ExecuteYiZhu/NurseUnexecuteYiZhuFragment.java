package com.bjym.hyzc.activity.ExecuteYiZhu;

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
import com.bjym.hyzc.activity.executeNursingCare.NurseUnExecuteBean;
import com.bjym.hyzc.activity.fragment.BaseFragment;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/9/6.
 */
public class NurseUnexecuteYiZhuFragment extends BaseFragment {

    private String stageCodeParent;
    private String patientsNo;
    private List<NurseUnExecuteBean.RowsBean> rows;
    private TextView tv_none_unExecuteYiZhu;
    private ListView lv;
    private MyAdapter adapter;
    private String currentStageCode;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            //条目被点击之后，得到OrderNo，弹出对话框，询问是否执行此医嘱
            final String orderNo = rows.get(position).OrderNo;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("确定执行此医嘱？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    postUnExecuteYiZhu(orderNo);
                    rows.remove(rows.get(position));
                    adapter.notifyDataSetChanged();
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

    private void postUnExecuteYiZhu(String orderNo) {
        //TODO 确定提交的内容
        OkHttpUtils.postString().url(MyConstant.BASE_URL+ MyConstant.NURSE_UNEXECUTEYIZHU_COMIT+orderNo).content("").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NurseUnexecuteYiZhuFragment.this.getActivity(),"请求网络失败，请稍后再试");
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(NurseUnexecuteYiZhuFragment.this.getActivity(),"执行医嘱成功");
            }
        });

    }
    @Override
    public View setMainView() {
        View view=View.inflate(context, R.layout.fragement_unexecuteyizhu,null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_none_unExecuteYiZhu = (TextView)view.findViewById(R.id.tv_none_unExecuteYiZhu);
        return view;
    }

    @Override
    public void InitData() {
        Intent intent = getActivity().getIntent();
        stageCodeParent = intent.getStringExtra("stageCodeParent");
        String stageCodeChild = intent.getStringExtra("stageCodeChild");
        String stageCodeSun = intent.getStringExtra("stageCodeSun");

        patientsNo = intent.getStringExtra("patientsNo");
       // MyLog.i("stageCodeParent:::::", stageCodeParent);
        MyLog.i("patientsNo:::::", patientsNo);
        if (stageCodeParent == null && stageCodeChild == null) {
            currentStageCode=stageCodeSun;
        }
        if (stageCodeParent == null && stageCodeSun==null){
            currentStageCode=stageCodeChild;
        }
        if (stageCodeChild == null && stageCodeSun==null){
            currentStageCode=stageCodeParent;
        }

        getUnExecuteStage(currentStageCode);
        lv.setOnItemClickListener(new MyOnItemClickListener());
    }

    private void getUnExecuteStage(String currentStageCode) {
        OkHttpUtils.post()
                .url(MyConstant.BASE_URL+ MyConstant.NURSE_UNEXECUTE_YIZHU)
                .addParams("PatientsNo", patientsNo)
                .addParams("stagecode", currentStageCode)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {
                        //  MyToast.showToast(NurseUnxecuteYiZhuActivity.this, response.body().string());
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        e.printStackTrace();
                        MyLog.i("e.printStackTrace();", e.toString());
                    }

                    @Override
                    public void onResponse(Object o, int i) {
                        //MyToast.showToast(NurseUnxecuteYiZhuActivity.this, (String) o);
                        parseJson((String) o);
                    }
                });
    }

    private void parseJson(String o) {
        NurseUnExecuteBean nurseUnExecuteBean = new Gson().fromJson(o, NurseUnExecuteBean.class);
        rows = nurseUnExecuteBean.getRows();
        if(rows.size()==0){
            lv.setVisibility(View.GONE);

            tv_none_unExecuteYiZhu.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_titlebar_left:
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
