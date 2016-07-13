package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.Question;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/13.
 */
public class SurveyActivity1 extends BaseActivity {
    public ViewPager vg;
    public Button btn_pre;
    public Button btn_next;
    public Button btn_commit;
    public int position;
    public static final String url = "http://192.168.0.168/cpw/CPW_Topic/getlistbysurvey?";
    private String surveyNo;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_survey1, null);
        vg = (ViewPager) view.findViewById(R.id.vg);
        btn_pre = (Button) view.findViewById(R.id.btn_pre);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_commit = (Button) view.findViewById(R.id.btn_commit);

        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        return view;
    }

    @Override
    public void InitData() {

        Intent intent = getIntent();
        surveyNo = intent.getStringExtra("SurveyNo");
        MyLog.i(SurveyActivity1.class, surveyNo);
        MyToast.showToast(SurveyActivity1.this, surveyNo);
        vg.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                SurveyActivity1.this.position = position;
                if (position == 0) {
                    btn_pre.setClickable(false);
                    MyToast.showToast(SurveyActivity1.this, "已经是第一题");
                } else {
                    btn_pre.setClickable(true);
                }

                //TODO
            }
        });

        getQuesionData();

    }

    private void getQuesionData() {

        OkHttpUtils.post().url(url).addParams("Survey", "PSI0000001").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String Json = response.body().string();
                MyToast.showToast(SurveyActivity1.this, "请求成功" + Json);
                parseJson(Json);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(SurveyActivity1.this, "请求成功");

            }
        });

    }

    private void parseJson(String json) {
        Gson gson=new Gson();
        Question question = gson.fromJson(json, Question.class);
        List<Question.RowsBean> rows = question.getRows();
        for (int i = 0; i < rows.size(); i++) {
            Question.RowsBean rowsBean = rows.get(i);
            String contents = rowsBean.Contents;
            String topicNo = rowsBean.TopicNo;
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_pre:


                break;
            case R.id.btn_next:

                break;
            case R.id.btn_commit:

                break;
            default:
                break;
        }
    }


}
