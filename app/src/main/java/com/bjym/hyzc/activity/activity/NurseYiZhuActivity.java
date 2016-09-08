package com.bjym.hyzc.activity.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.fragment.NurseExecutedYiZhuFragment;
import com.bjym.hyzc.activity.fragment.NurseUnexecuteYiZhuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/9/6.
 */
public class NurseYiZhuActivity extends BaseActivity {

    private ViewPager vp;
    private Button btn_unexecute_yizhu;
    private Button btn_execute_yizhu;
    private List<Fragment> fragmentList;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;

 class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if(position==0){
                resetUnExecuteYiZhuFragmeng(getResources().getColor(
                        R.color.colorPrimary), getResources().getColor(
                        R.color.background_white));
            }else {
                resetUnExecuteYiZhuFragmeng(getResources().getColor(
                        R.color.background_white), getResources().getColor(
                        R.color.colorPrimary));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nuseyizhu, null);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        vp = (ViewPager) view.findViewById(R.id.vp);
        btn_unexecute_yizhu = (Button) view.findViewById(R.id.btn_unexecute_yizhu);
        btn_execute_yizhu = (Button) view.findViewById(R.id.btn_execute_yizhu);

        return view;
    }

    @Override
    public void InitData() {
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        resetUnExecuteYiZhuFragmeng(getResources().getColor(
                R.color.colorPrimary), getResources().getColor(
                R.color.background_white));

        btn_execute_yizhu.setVisibility(View.VISIBLE);
        btn_unexecute_yizhu.setVisibility(View.VISIBLE);

        fragmentList = new ArrayList<>();
        fragmentList.clear();
        fragmentList.add(new NurseUnexecuteYiZhuFragment());
        fragmentList.add(new NurseExecutedYiZhuFragment());


        //默认填充未执行的医嘱界面
        vp.setCurrentItem(0);
        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        btn_unexecute_yizhu.setOnClickListener(this);
        btn_execute_yizhu.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //FragmentTransaction transaction = manager.beginTransaction();

        switch (v.getId()) {
           case R.id.btn_unexecute_yizhu:
               vp.setCurrentItem(0);
                break;
            case R.id.btn_execute_yizhu:
                vp.setCurrentItem(1);
                break;
            case R.id.bt_titlebar_left:
                finish();
                break;

            default:

                break;
        }
    }

    private void resetUnExecuteYiZhuFragmeng(int color, int color2) {
        btn_unexecute_yizhu.setTextColor(color);
        btn_execute_yizhu.setTextColor(color2);
    }
}
