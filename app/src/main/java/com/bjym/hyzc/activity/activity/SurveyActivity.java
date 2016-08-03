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

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.PatiSurveyNo;
import com.bjym.hyzc.activity.bean.Question;
import com.bjym.hyzc.activity.bean.QuestionOption;
import com.bjym.hyzc.activity.bean.SubmitorMsg;
import com.bjym.hyzc.activity.bean.SurveyAnswer;
import com.bjym.hyzc.activity.fragment.BaseFragment;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.bjym.hyzc.activity.view.NoScrollViewPager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/13.
 */
public class SurveyActivity extends BaseActivity {
    public NoScrollViewPager vg;
    public RadioGroup rg;
    public Button btn_pre;
    public Button btn_next;
    public Button btn_submit;
    public int position;
    // http://hyzc.tpddns.cn:6060/cpw/CPW_Topic/getlistbysurvey?Survey=1111

    private String contents;
    private int number;
    public List<Fragment> fragments = new ArrayList<>();
    public MyFragmentPageAdpter adpter;
    /*
    * 存放题号和用户所选答案
    * */
    public static HashMap<String, String> answers = new HashMap<>();
    /*
    * 存放题号和分数 第一题有四个选项，四个分数
    * */
    public static HashMap<String, Integer> scoreMap = new HashMap<>();

    //总分
    // int score=0;

    private String url1;
    private String surveyNo;
    private String surveyName;
    private String name;
    private String patientsNo;
    private String userCode;
    private String realName;
    private String newCode;


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
        View view = View.inflate(context, R.layout.activity_survey, null);
        vg = (NoScrollViewPager) view.findViewById(R.id.vg);
        btn_pre = (Button) view.findViewById(R.id.btn_pre);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        rg = (RadioGroup) view.findViewById(R.id.rg);


        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void InitData() {
          /*
        * 获取调查编号，用于提交
        * */
        getPatiSurveryNo();

        Intent intent = getIntent();
        /*
        * 调查表编号
        * */
        surveyNo = intent.getStringExtra("surveyNo");

        /*
        * 患者姓名和编号
        * */
        name = intent.getStringExtra("Name");
        patientsNo = intent.getStringExtra("patientsNo");

        /*
        * 用户账号和姓名
        * */
        userCode = intent.getStringExtra("userCode");
        realName = intent.getStringExtra("realName");
        /*
        * 调查表名字，设置为title
        * */
        surveyName = intent.getStringExtra("SurveyName");
        setTitle(surveyName);

        /*
        * 根据调查表编号，获取问题题干
        * */
        getQuesionData();


        adpter = new MyFragmentPageAdpter(getSupportFragmentManager());
        vg.setAdapter(adpter);
        vg.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                SurveyActivity.this.position = position;
                if (position == 0) {
                    btn_pre.setClickable(false);
                    MyToast.showToast(SurveyActivity.this, "已经是第一题");
                } else {
                    btn_pre.setClickable(true);
                }
                if (position == fragments.size() - 1) {
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.GONE);
                    MyToast.showToast(SurveyActivity.this, "已经是最后一题");
                } else {
                    btn_next.setClickable(true);
                    btn_submit.setVisibility(View.GONE);
                    btn_next.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /*
    * 获取调查编码用于提交
    * */
    private void getPatiSurveryNo() {
        OkHttpUtils.get().url(MyConstant.PATISURVERYNO_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                // MyLog.i("success", "请求成功le");
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(SurveyActivity.this, "请求失败");
            }

            @Override
            public void onResponse(Object o, int i) {
                parsePatiSurveyNo((String) o);
                // MyLog.i("success", "请求成功");

            }

        });

    }

    private void parsePatiSurveyNo(String o) {

        Gson gson = new Gson();
        PatiSurveyNo patiSurveyNo = gson.fromJson(o, PatiSurveyNo.class);
        newCode = patiSurveyNo.newCode;
        // MyLog.i("newCode:", newCode);
    }

    /*
    * 根据调查表编号获取问题题干
    * */
    private void getQuesionData() {

        url1 = MyConstant.QUESTIONLIST_URL + surveyNo;
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
                //  MyToast.showToast(SurveyActivity.this, "请求成功");
            }
        });
    }

    private void parseJson(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        List<Question.RowsBean> rows = question.getRows();
        for (int i = 0; i < rows.size(); i++) {
            Question.RowsBean rowsBean = rows.get(i);
            String contents = rowsBean.Contents;
            String topicNo = rowsBean.TopicNo;
            number = rowsBean.Number;
            SurveyFragment surveyFragment = new SurveyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Contents", contents);//选项内容
            bundle.putString("TopicNo", topicNo);//选项编码
            bundle.putInt("Number", number);//选项排序
            surveyFragment.setArguments(bundle);
            fragments.add(surveyFragment);
            adpter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int score = 0;
        super.onClick(v);
       /* if (v instanceof Button && position!=0&&position!=vg.getChildCount() - 1) {
            Toast.makeText(this, ((Button) v).getText().toString().trim(), Toast.LENGTH_SHORT).show();
        }*/
        switch (v.getId()) {
            case R.id.btn_pre:
                if (position != 0) {
                    vg.setCurrentItem(--position);

                } else {
                    MyToast.showToast(SurveyActivity.this, "已经是第一题");
                }
                break;
            case R.id.btn_next:
                if (position != vg.getChildCount() - 1) {
                    vg.setCurrentItem(++position);
                } else {
                    MyToast.showToast(SurveyActivity.this, "已经是最后一题");
                }
                break;
            case R.id.btn_submit:

                //得到每道题 对应的选项的得分

                if (scoreMap.size() > 0) {
                    Iterator iterScore = scoreMap.entrySet().iterator();
                    while (iterScore.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterScore.next();
                        score += (Integer) entry.getValue();
                    }
                    MyLog.i("总分：", "" + score);
                }
                String pationpMsg = new Gson().toJson(new SubmitorMsg(newCode, surveyNo, patientsNo, name, userCode, realName, "" + score, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))));
                MyLog.i("pationpMsg", pationpMsg);


                /*
                * 提交调查基本信息
                * */
                postPationMsg(pationpMsg);
                scoreMap.clear();
                /*
                * 1.提交调查结果至服务器
                * 2.关闭自身页面
                * */
                commitAnswers();
                finish();
                break;

            default:
                break;
        }
    }


    /*
    * 提交调查结果至服务器
    * */
    private void commitAnswers() {

        if (answers.size() > 0) {
            Iterator iter = answers.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                MyLog.i("survey", entry.getKey() + ":" + entry.getValue());
                String topicNo = (String) entry.getKey();
                String choiceNum = (String) entry.getValue();

                String answer = new Gson().toJson(new SurveyAnswer(newCode, topicNo, choiceNum, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))));

                /*
                * 提交答案
                * */
                postAnswers(answer);
                MyLog.i("answer:::::", answer);
            }
            answers.clear();
        }
    }

    private void postPationMsg(String pationpMsg) {
        OkHttpUtils.postString().url(MyConstant.SUBMITORMSG_URL).content(pationpMsg)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                //   MyLog.i("PationMsgresponse",response.toString());
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(SurveyActivity.this, "postPationMsg提交错误" + e.toString());

                MyLog.i("postPationMsg", "提交错误" + e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(SurveyActivity.this, "提交成功" + "postPationMsg");
                MyLog.i("提交成功le", "postPationMsg" + o);
            }
        });
    }

    private void postAnswers(String answer) {

        OkHttpUtils.postString().url(MyConstant.ANSWERS_URL).content(answer)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                MyLog.i("response:", response.toString());
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(SurveyActivity.this, "提交错误postAnswers" + e.toString());
                MyLog.i("提交错误postAnswers", e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(SurveyActivity.this, "提交成功" + "postAnswers");
            }
        });
    }








    public static class SurveyFragment extends BaseFragment {

        private RadioGroup rg;
        private TextView tv_questions;
       private String topicNo;
        private List<String> choiceNums = new ArrayList<>();
        private List<Integer> scoreLists = new ArrayList<>();

        @Override
        public View setMainView() {
            View view = View.inflate(context, R.layout.fragment_survey, null);
            rg = (RadioGroup) view.findViewById(R.id.rg);
            tv_questions = (TextView) view.findViewById(R.id.tv_questions);
            return view;
        }

        @Override
        public void InitData() {


            Bundle bundle = getArguments();
            if (bundle != null) {
                String contents = bundle.getString("Contents");
                topicNo = bundle.getString("TopicNo");
                int number = bundle.getInt("Number");
                tv_questions.setText(number + "." + contents);
                getOptions();
            }

            //给RadioGroup设置选项改变的监听
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //存放答案
                    answers.put(topicNo, choiceNums.get(checkedId));

                    //存放得分
                    scoreMap.put(topicNo, scoreLists.get(checkedId));

                }
            });

        }


        /*
        * 根据题干编号获取选项
        * */
        private void getOptions() {
            final String url = MyConstant.OPTION_URL + topicNo;


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
                        } catch (Exception e) {
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
            choiceNums.clear();
            if (total > 0) {
                for (int i = 0; i < options.size(); i++) {
                    QuestionOption.Option option = options.get(i);
                    String choiceNo = option.ChoiceNo;  //用户选项
                    String contents = option.Contents;  //选项内容
                    int score = option.Score;    //选项的分
                    rb = new RadioButton(context);
                    scoreLists.add(score);
                    choiceNums.add(choiceNo);
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
