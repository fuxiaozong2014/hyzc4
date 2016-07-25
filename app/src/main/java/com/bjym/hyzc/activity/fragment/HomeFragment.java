package com.bjym.hyzc.activity.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.pager.BasePager;
import com.bjym.hyzc.activity.pager.ForumPager;
import com.bjym.hyzc.activity.pager.NewsPager;
import com.bjym.hyzc.activity.pager.VedioPager;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class HomeFragment extends BaseFragment {
    private ViewPager viewPager;
    private List<BasePager> pagers = new ArrayList<>();
    private int lineWidth;//指示线宽度
    private TextView main_tv_news;
    private TextView main_tv_vedio;
    private TextView main_tv_forum;

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {

            //1.标签改变颜色
            int green = getResources().getColor(R.color.green);
            int halfwhite = getResources().getColor(R.color.halfwhite);
            //新闻
            main_tv_news.setTextColor(position == 0 ? green : halfwhite);
            //视频
            main_tv_vedio.setTextColor(position == 1 ? green : halfwhite);
            //论坛
            main_tv_forum.setTextColor(position == 2 ? green : halfwhite);

            //2.标签执行动画
            //新闻  属性动画: animate(执行动画的控件)  scale 缩放动画
            ViewPropertyAnimator.animate(main_tv_news).scaleX(position == 0 ? 1.2f : 1.0f).scaleY(position == 0 ? 1.2f : 1.0f);
            //视频
            ViewPropertyAnimator.animate(main_tv_vedio).scaleX(position == 1 ? 1.2f : 1.0f).scaleY(position == 1 ? 1.2f : 1.0f);
            //论坛
            ViewPropertyAnimator.animate(main_tv_forum).scaleX(position == 2 ? 1.2f : 1.0f).scaleY(position == 1 ? 1.2f : 1.0f);



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    }


    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_home, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        main_tv_news = (TextView) view.findViewById(R.id.main_tv_news);
        main_tv_vedio = (TextView) view.findViewById(R.id.main_tv_vedio);
        main_tv_forum = (TextView) view.findViewById(R.id.main_tv_forum);
        return view;
    }

    @Override
    public void InitData() {
        main_tv_news.setOnClickListener(this);
        main_tv_vedio.setOnClickListener(this);
        main_tv_forum.setOnClickListener(this);

        pagers.add(new NewsPager(getContext()));
        pagers.add(new ForumPager(getContext()));
        pagers.add(new VedioPager(getContext()));

        //指示线初始化  (屏幕的一半)
        int screenWidth = getResources().getDisplayMetrics().widthPixels;//屏幕宽度
        lineWidth = screenWidth / pagers.size();


        //TODO
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagers.size();
        }

        /**
         * 根据当前位置,获取对应的view,添加到container
         * 返回view
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("ContentFragment", "instantiateItem:" + position);
            BasePager currentPager = pagers.get(position);
            View view = currentPager.initView();//初始化view
            //初始化数据
            currentPager.initData();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("ContentFragment", "destroyItem:" + position);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            
            return super.getItemPosition(object);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_tv_news:
                //新闻
                viewPager.setCurrentItem(0);

                break;
            case R.id.main_tv_vedio:
                //视频
                viewPager.setCurrentItem(1);

                break;
            case R.id.main_tv_forum:
                //音频
                viewPager.setCurrentItem(2);

                break;
            default:
                break;
        }
    }



    /**
     * 对外暴露pagers集合
     */
    public List<BasePager> getPagers() {
        return pagers;
    }
}
