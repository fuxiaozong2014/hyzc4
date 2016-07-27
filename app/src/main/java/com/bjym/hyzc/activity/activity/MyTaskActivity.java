package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.fragment.BaseFragment;
import com.bjym.hyzc.activity.fragment.TaskedFragment;
import com.bjym.hyzc.activity.fragment.TaskingFragment;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/7/27.
 */
public class MyTaskActivity extends BaseActivity {

    private static final int REQUST_CODE =1 ;
    private ViewPager viewPager;
    List<BaseFragment> fragments = null;
    private TextView tv_tasked;
    private TextView tv_tasking;
    private TextView tv_search;


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //1.标签改变颜色
            int green = getResources().getColor(R.color.btn_color);
            int grey = getResources().getColor(R.color.click_diaocha_search);
            //未执行ing
            tv_tasking.setTextColor(position == 0 ? green : grey);
            //已执行
            tv_tasked.setTextColor(position == 1 ? green : grey);


            //2.标签执行动画
            //已执行任务  属性动画: animate(执行动画的控件)  scale 缩放动画
            ViewPropertyAnimator.animate(tv_tasking).scaleX(position == 0 ? 1.1f : 1.0f).scaleY(position == 0 ? 1.1f : 1.0f);
            //未执行任务
            ViewPropertyAnimator.animate(tv_tasked).scaleX(position == 1 ? 1.1f : 1.0f).scaleY(position == 1 ? 1.1f : 1.0f);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    }

    @Override
    public View setMainView() {

        fragments = new ArrayList<>();
        View view = View.inflate(context, R.layout.activity_mytask, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tv_tasked = (TextView) view.findViewById(R.id.tv_tasked);
        tv_tasking = (TextView) view.findViewById(R.id.tv_tasking);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        tv_tasking.setTextColor(getResources().getColor(R.color.btn_color));

        return view;
    }

    @Override
    public void InitData() {
        tv_tasking.setOnClickListener(this);
        tv_tasked.setOnClickListener(this);
        tv_search.setOnClickListener(this);


        fragments.add(new TaskingFragment());
        fragments.add(new TaskedFragment());

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.tv_tasking:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_tasked:
                viewPager.setCurrentItem(1);
                break;

            case R.id.tv_search:
                Intent intent=new Intent();
                intent.setClass(MyTaskActivity.this, MyPationteActivity.class);
                startActivityForResult(intent,REQUST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case  RESULT_OK:
                String name = data.getStringExtra("Name");
                String patientsNo = data.getStringExtra("patientsNo");
                tv_search.setText("姓名：" + name + "    患者编号：" + patientsNo);
                break;
        }
    }
}
