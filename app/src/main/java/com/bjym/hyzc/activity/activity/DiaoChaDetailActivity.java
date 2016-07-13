package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;

/**
 * Created by fushaoqing on 2016/7/12.
 */
public class DiaoChaDetailActivity extends BaseActivity {
    private TextView title;
    private LinearLayout title_content;
    private LinearLayout content;
    private Button bt_pre_ti;
    private Button bt_next_ti;
    private LinearLayout linearLayout;

    @Override
    public View setMainView() {
        View view = View.inflate(DiaoChaDetailActivity.this, R.layout.activity_diaochadetail, null);
        title= (TextView)view.findViewById(R.id.title);
        title_content= (LinearLayout)view.findViewById(R.id.title_content);
        content= (LinearLayout)view.findViewById(R.id.content);
        linearLayout= (LinearLayout)view.findViewById(R.id.linearLayout);
        bt_pre_ti= (Button)view.findViewById(R.id.bt_pre_ti);
        bt_next_ti= (Button)view.findViewById(R.id.bt_next_ti);
        bt_pre_ti.setOnClickListener(this);
        bt_next_ti.setOnClickListener(this);
        return view;
    }

    @Override
    public void InitData() {
        //得到这些问题
        getQuestions();
        //List<Question> questions=new ArrayList<>();

    }

    private void getQuestions() {


    }



    //题的下标
    int index=-1;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_pre_ti:
                if (index>0){
                   // addViewByTi();

                }

        }
    }
}
