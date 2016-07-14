package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.SurveyActivity1;
import com.bjym.hyzc.activity.bean.DiaoChaSortBean;
import com.bjym.hyzc.activity.utils.MyConstant;
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
    private String surveyNo1;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_diaocha, null);
        ll_diaoChaSort = (ListView) view.findViewById(R.id.ll_diaoChaSort);
        return view;
    }

    @Override
    public void InitData() {
        getNetData();
        ll_diaoChaSort.setOnItemClickListener(new MyOnItemClickListner());

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
                MyAdapter adpter = null;
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


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO click one item jump to detail S
            Intent intent = new Intent(context, SurveyActivity1.class);
            intent.putExtra("SurveyNo", surveyNo);
            startActivity(intent);
        }
    }
}
