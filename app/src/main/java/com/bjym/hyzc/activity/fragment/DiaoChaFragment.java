package com.bjym.hyzc.activity.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.DiaoChaSortBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class DiaoChaFragment extends BaseFragment {

    private List<DiaoChaSortBean> diaoChaSortList;
    private ListView ll_diaoChaSort;
    private List<DiaoChaSortBean.RowsBean> rows;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_diaocha, null);
        ll_diaoChaSort = (ListView) view.findViewById(R.id.ll_diaoChaSort);
        return view;
    }

    @Override
    public void InitData() {
        getNetData();
        DiaoChaSortBean bean=new DiaoChaSortBean();
        List<DiaoChaSortBean.RowsBean> rows = bean.getRows();
    }

    private void getNetData() {
        //请求调查类型
        OkHttpUtils.get().url(MyConstant.MYDIAOCHA_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                //MyLog.i(DiaoChaFragment.this.getActivity().getClass(), "请求成功" + result);
                MyToast.showToast(DiaoChaFragment.this.getActivity(),"请求成功" + result);
                parseJson(result);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object o, int i) {
                MyAdapter adpter=null;
                if (adpter==null){
                    adpter= new MyAdapter();
                    ll_diaoChaSort.setAdapter(adpter);
                }else{
                    adpter.notifyDataSetChanged();
                }


            }
        });
    }

    private List<DiaoChaSortBean.RowsBean> parseJson(String result) throws JSONException {
        //TODO 这里解析用户信息
        List<DiaoChaSortBean.RowsBean> list=new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        //得到json数组
        JSONArray rowLists=jsonObject.optJSONArray("rows");
        for (int i = 0; i <rowLists.length() ; i++) {
            JSONObject rowBean = rowLists.getJSONObject(i);
            //获取每一个对象的值
               MyToast.showToast(DiaoChaFragment.this.getActivity(),rowBean.optString("SurveyName"));
        }
      return list;

    }

    public class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 1;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            TextView tv_diaoChaSort;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_diaochasort, null);

            } else {
                view = convertView;
            }
            tv_diaoChaSort = (TextView) view.findViewById(R.id.tv_diaoChaSort);
            tv_diaoChaSort.setText(rows.get(position).SurveyName);
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
}
