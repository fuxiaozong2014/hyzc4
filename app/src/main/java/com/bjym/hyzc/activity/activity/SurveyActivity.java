package com.bjym.hyzc.activity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.receiver.MyApplication;
import com.bjym.hyzc.activity.utils.DensityUtil;
import com.bjym.hyzc.activity.utils.MyLog;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private Button preSurvey;
    private Button nextSurvey;
    private Button bt_submit;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    private static final String SERVEY_TOPIC_LIST = "http://192.168.0.168/cpw/CPW_Topic/getlistbysurvey?Survey=PSI0000001";
    private static final String SERVEY_GET_OPTION = "http://192.168.0.168/cpw/CPW_Choice/getlistbytopicno?toptic=";
    private static HashMap<String, String> answers = new HashMap<>();
    private int position;

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
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

    public static class SurveyFragment extends Fragment {

        private TextView tv_title;
        private RadioGroup rg;
        private ArrayList<String> ChoiceNos = new ArrayList<>();
        private String topicNo;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = View.inflate(MyApplication.getAppContext(), R.layout.fragment_survey, null);
            initView(view);
            setListener();
            initData();
            return view;
        }

        private void initView(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            rg = (RadioGroup) view.findViewById(R.id.rg);
        }

        private void setListener() {
            /**
             * 当选项选中时，把题目id和答案id放入map集合中
             */
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    answers.put(topicNo, ChoiceNos.get(checkedId));
                }
            });
        }

        /**
         * 从arguments中获得题目信息
         */
        private void initData() {
            Bundle bundle = getArguments();
            if (bundle != null) {
                topicNo = bundle.getString("TopicNo");
                String contents = bundle.getString("Contents");
                String number = bundle.getString("Number");
                tv_title.setText(number + "." + contents);
                getOptions();
            }
        }

        /**
         * 获得题目对应的选项信息，每个选项对应一个radiobutton，并且添加到radiogroup中，默认选中第一个选项，
         * 将选项对应的id添加到选项id集合ChoiceNos中
         */
        private void getOptions() {
            OkHttpUtils.get()
                    .url(SERVEY_GET_OPTION + topicNo)
                    .build()
                    .execute(new com.zhy.http.okhttp.callback.Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int i) {//子线程
                    return response;
                }

                @Override
                public void onError(Call call, Exception e, int i) {

                }

                @Override
                public void onResponse(Object response, int i) {//主线程
                    if (response instanceof Response) {
                        try {
                            String string = ((Response) response).body().string();
                            JSONObject object = new JSONObject(string);
                            if (object.optInt("total") > 0) {
                                JSONArray array = object.optJSONArray("rows");
                                JSONObject arrayJSONObject;
                                RadioButton rb;
                                RadioGroup.LayoutParams params;
                                rg.removeAllViews();
                                ChoiceNos.clear();
                                for (int j = 0; j < array.length(); j++) {
                                    arrayJSONObject = array.getJSONObject(j);
                                    ChoiceNos.add(arrayJSONObject.optString("ChoiceNo"));
                                    rb = new RadioButton(MyApplication.getAppContext());
                                    rb.setText(arrayJSONObject.optString("Contents"));
                                    rb.setTextColor(Color.RED);
                                    rb.setId(j);
                                    params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                                    params.leftMargin = DensityUtil.dip2px(20);
                                    rg.addView(rb, j, params);
                                }
                                rg.check(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        preSurvey = (Button) findViewById(R.id.preSurvey);
        nextSurvey = (Button) findViewById(R.id.nextSurvey);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    private void setListener() {
        preSurvey.setOnClickListener(this);
        nextSurvey.setOnClickListener(this);
        bt_submit.setOnClickListener(this);

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                SurveyActivity.this.position = position;
                if (position == 0) {
                    preSurvey.setClickable(false);
                } else {
                    preSurvey.setClickable(true);
                }
                if (position == fragments.size() - 1) {
                    nextSurvey.setClickable(false);
                    bt_submit.setVisibility(View.VISIBLE);
                } else {
                    nextSurvey.setClickable(true);
                }
            }
        });
    }

    /**
     * 获得所有的问题，每个问题创建一个fragment，并添加到viewPager的数据源集合中，notify
     */
    private void initData() {
        OkHttpUtils.get()
                .url(SERVEY_TOPIC_LIST)
                .build()
                .execute(new com.zhy.http.okhttp.callback.Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) {//子线程
                try {
                    String string = response.body().string();
                    JSONObject object = new JSONObject(string);
                    if (object.optInt("total") > 0) {
                        JSONArray array = object.optJSONArray("rows");
                        JSONObject arrayJSONObject;
                        Bundle bundle;
                        for (int j = 0; j < array.length(); j++) {
                            arrayJSONObject = array.getJSONObject(j);
                            final Fragment fragment = new SurveyFragment();
                            bundle = new Bundle();
                            bundle.putString("TopicNo", arrayJSONObject.optString("TopicNo"));
                            bundle.putString("Contents", arrayJSONObject.optString("Contents"));
                            bundle.putString("Number", arrayJSONObject.optString("Number"));
                            fragment.setArguments(bundle);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fragments.add(fragment);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object o, int i) {//主线程

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Toast.makeText(this, ((Button) v).getText().toString().trim(), Toast.LENGTH_SHORT).show();
        }
        switch (v.getId()) {
            case R.id.btn_pre://上一题
                if (position != 0) {
                    viewPager.setCurrentItem(--position);
                }

                break;
            case R.id.btn_next://下一题
                if (position != viewPager.getChildCount() - 1) {
                    viewPager.setCurrentItem(++position);
                }
                break;
            case R.id.bt_submit://提交
                submitAnswers();
                break;
        }
    }

    /**
     * 提交答题结果
     */
    private void submitAnswers() {
        if (answers.size() > 0) {
            Iterator iter = answers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                MyLog.i("survey", entry.getKey() + ":" + entry.getValue());
            }
        }
    }
}
