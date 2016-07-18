package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.Question;
import com.bjym.hyzc.activity.bean.QuestionOption;
import com.bjym.hyzc.activity.fragment.BaseFragment;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    // http://hyzc.tpddns.cn:6060/cpw/CPW_Topic/getlistbysurvey?Survey=1111
    public static final String url = "http://192.168.0.168/cpw/CPW_Topic/getlistbysurvey?Survey=";
    public static final String optionUrl = "http://192.168.0.168/cpw/CPW_Choice/getlistbytopicno?topic=";
    private String contents;
    private String topicNo;
    private int number;
    public List<Fragment> fragments = new ArrayList<>();
    public MyFragmentPageAdpter adpter;
    public static HashMap<String, String> answers = new HashMap<>();
    private String url1;
    private String surveyNo;


    public class MyFragmentPageAdpter extends FragmentPagerAdapter {


        public MyFragmentPageAdpter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

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
        surveyNo = intent.getStringExtra("surveyNo");

        getQuesionData();
        adpter = new MyFragmentPageAdpter(getSupportFragmentManager());
        vg.setAdapter(adpter);
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
                if (position == fragments.size() - 1) {
                    btn_next.setClickable(false);
                    btn_commit.setVisibility(View.VISIBLE);

                } else {
                    btn_next.setClickable(true);
                }
            }
        });


    }

    private void getQuesionData() {

        url1 = url + surveyNo;
        OkHttpUtils.get().url(url1).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(Object o, int i) {
                parseJson((String) o);
                MyToast.showToast(SurveyActivity1.this, "请求成功");
            }
        });
    }

    private void parseJson(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        List<Question.RowsBean> rows = question.getRows();
        for (int i = 0; i < rows.size(); i++) {
            Question.RowsBean rowsBean = rows.get(i);
            contents = rowsBean.Contents;
            topicNo = rowsBean.TopicNo;
            number = rowsBean.Number;
            SurveyFragment1 surveyFragment1 = new SurveyFragment1();
            Bundle bundle = new Bundle();
            bundle.putString("Contents", contents);
            bundle.putString("TopicNo", topicNo);
            bundle.putInt("Number", number);
            surveyFragment1.setArguments(bundle);
            fragments.add(surveyFragment1);
            adpter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v instanceof Button) {
            Toast.makeText(this, ((Button) v).getText().toString().trim(), Toast.LENGTH_SHORT).show();
        }
        switch (v.getId()) {
            case R.id.btn_pre:
                if (position != 0) {
                    vg.setCurrentItem(--position);
                }
                break;
            case R.id.btn_next:
                if (position != vg.getChildCount() - 1) {
                    vg.setCurrentItem(++position);
                }
                break;
            case R.id.btn_commit:
                commitAnswers();
                break;
            default:
                break;
        }
    }

    private void commitAnswers() {
        if (answers.size() > 0) {
            Iterator iter = answers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                MyLog.i("survey", entry.getKey() + ":" + entry.getValue());
            }
        }
    }

    public static class SurveyFragment1 extends BaseFragment {

        private RadioGroup rg;
        private TextView tv_questions;
        private String topicNo;
        private List<String> choiceNum = new ArrayList<>();

        @Override
        public View setMainView() {
            View view = View.inflate(context, R.layout.fragment_survey1, null);
            rg = (RadioGroup) view.findViewById(R.id.rg);
            tv_questions = (TextView) view.findViewById(R.id.tv_questions);
            return view;
        }

        @Override
        public void InitData() {
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    answers.put(topicNo, choiceNum.get(checkedId));
                }
            });
            Bundle bundle = getArguments();
            if (bundle != null) {
                String contents = bundle.getString("Contents");
                topicNo = bundle.getString("TopicNo");
                int number = bundle.getInt("Number");
                tv_questions.setText(number + "." + contents);
                getOptions();
            }
        }

        private void getOptions() {
            String url = optionUrl + topicNo;

            OkHttpUtils.get().url(url).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int i) throws Exception {

                    return response;
                }

                @Override
                public void onError(Call call, Exception e, int i) {

                }

                @Override
                public void onResponse(Object response, int i) {
                    if (response instanceof Response) {
                        try {
                            String result = ((Response) response).body().string();
                            parseResult(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

        }

        private void parseResult(String result) {
            Gson gson = new Gson();
            QuestionOption questionOption = gson.fromJson(result, QuestionOption.class);
            List<QuestionOption.Option> options = questionOption.getRows();
            int total = questionOption.getTotal();
            RadioGroup.LayoutParams params = null;
            RadioButton rb = null;
            rg.removeAllViews();
            choiceNum.clear();
            if (total > 0) {

                for (int i = 0; i < options.size(); i++) {

                    QuestionOption.Option option = options.get(i);
                    String choiceNo = option.ChoiceNo;
                    String contents = option.Contents;
                    rb = new RadioButton(context);
                    choiceNum.add(choiceNo);
                    rb.setText(contents);
                    rb.setId(i);
                    params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.SCROLL_AXIS_VERTICAL);
                    // params.leftMargin = DensityUtil.dip2px(20);
                    rg.addView(rb, i, params);

                }
            }

        }

    }
}
