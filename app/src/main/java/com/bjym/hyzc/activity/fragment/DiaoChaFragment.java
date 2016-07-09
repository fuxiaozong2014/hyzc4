package com.bjym.hyzc.activity.fragment;

import android.view.View;
import android.widget.ListView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class DiaoChaFragment extends BaseFragment {

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_diaocha, null);
        ListView ll_diaoChaSort = (ListView) view.findViewById(R.id.ll_diaoChaSort);
        return view;
    }

    @Override
    public void InitData() {
        //getNetData();
    }

    private void getNetData() {
        //请求调查类型
        OkHttpUtils.get().url(MyConstant.MYMSG_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String result = response.body().string();
                MyToast.showToast(DiaoChaFragment.this.getActivity(), "请求成功" + result);
                parseJson(result);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object o, int i) {

            }
        });
    }

    private void parseJson(String result) {
        //TODO 这里解析用户信息
    }
}
