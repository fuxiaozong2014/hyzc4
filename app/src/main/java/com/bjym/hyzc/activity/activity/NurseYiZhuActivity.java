package com.bjym.hyzc.activity.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.fragment.ExecutedYiZhuFragment;
import com.bjym.hyzc.activity.fragment.UnexecuteYiZhuFragment;

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

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if(position==0){
                btn_unexecute_yizhu.setTextColor(getResources().getColor(
                        R.color.Hui));
                btn_execute_yizhu.setTextColor(getResources().getColor(
                        R.color.colorPrimary));
            }else {
                btn_unexecute_yizhu.setTextColor(getResources().getColor(
                        R.color.colorPrimary));
                btn_execute_yizhu.setTextColor(getResources().getColor(
                        R.color.Hui));
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
        vp = (ViewPager) view.findViewById(R.id.vp);
        btn_unexecute_yizhu = (Button) view.findViewById(R.id.btn_unexecute_yizhu);
        btn_execute_yizhu = (Button) view.findViewById(R.id.btn_execute_yizhu);

        return view;
    }

    @Override
    public void InitData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new UnexecuteYiZhuFragment());
        fragmentList.add(new ExecutedYiZhuFragment());

        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        btn_unexecute_yizhu.setOnClickListener(this);
        btn_execute_yizhu.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.btn_unexecute_yizhu:
                btn_unexecute_yizhu.setTextColor(getResources().getColor(
                        R.color.colorPrimary));
                btn_execute_yizhu.setTextColor(getResources().getColor(
                        R.color.Hui));
                break;
            case R.id.btn_execute_yizhu:
                btn_unexecute_yizhu.setTextColor(getResources().getColor(
                        R.color.Hui));
                btn_execute_yizhu.setTextColor(getResources().getColor(
                        R.color.colorPrimary));
                break;

            default:

                break;

        }
    }
}
