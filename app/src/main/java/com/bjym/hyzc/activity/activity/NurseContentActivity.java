package com.bjym.hyzc.activity.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.fragment.NurseExecutedContentFragment;
import com.bjym.hyzc.activity.fragment.NurseUnexecuteContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/9/7.
 *
 */
public class NurseContentActivity extends BaseActivity{
    private ViewPager vp;
    private Button btn_unexecute_content;
    private Button btn_execute_content;
    private List<Fragment> fragmentList;
    private FragmentManager manager;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if(position==0){
                resetUnExecuteContentFragmeng(getResources().getColor(
                        R.color.colorPrimary), getResources().getColor(
                        R.color.background_white));
            }else {
                resetUnExecuteContentFragmeng(getResources().getColor(
                        R.color.background_white), getResources().getColor(
                        R.color.colorPrimary));
            }
           // currentPosition=position;
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
        View view=View.inflate(context, R.layout.activity_nursecontent,null);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        vp = (ViewPager) view.findViewById(R.id.vp);
        btn_unexecute_content = (Button) view.findViewById(R.id.btn_unexecute_content);
        btn_execute_content = (Button) view.findViewById(R.id.btn_execute_content);
        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        resetUnExecuteContentFragmeng(getResources().getColor(
                R.color.colorPrimary), getResources().getColor(
                R.color.background_white));

        btn_execute_content.setVisibility(View.VISIBLE);
        btn_unexecute_content.setVisibility(View.VISIBLE);

        fragmentList = new ArrayList<>();
        fragmentList.clear();
        fragmentList.add(new NurseUnexecuteContentFragment());
        fragmentList.add(new NurseExecutedContentFragment());

        vp.setCurrentItem(0);
        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        btn_unexecute_content.setOnClickListener(this);
        btn_execute_content.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //FragmentTransaction transaction = manager.beginTransaction();

        switch (v.getId()) {
            
            case R.id.bt_titlebar_left:
                finish();
                break;

            default:

                break;
        }
        // transaction.commit();

        //vp.setCurrentItem(v.getId());
    }

    private void resetUnExecuteContentFragmeng(int color, int color2) {
        btn_unexecute_content.setTextColor(color);
        btn_execute_content.setTextColor(color2);
    }
}
