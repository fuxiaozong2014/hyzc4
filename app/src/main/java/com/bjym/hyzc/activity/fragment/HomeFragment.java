package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.WebViewActivity;
import com.bjym.hyzc.activity.pager.BasePager;
import com.bjym.hyzc.activity.view.CycleViewPagerShi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class HomeFragment extends BaseFragment {
    private List<BasePager> pagers = new ArrayList<>();
    private TextView tv_healthySpace;
    private CycleViewPagerShi viewPager;
    private int[] images;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_home, null);
        tv_healthySpace=(TextView)view.findViewById(R.id.tv_healthySpace);

        viewPager = (CycleViewPagerShi) view.findViewById(R.id.vp);
        images = new int[] { R.mipmap.z3, R.mipmap.oh,
                R.mipmap.user, R.mipmap.setting, R.mipmap.home};

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView)view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {

        tv_healthySpace.setOnClickListener(this);

        bt_titlebar_left.setVisibility(View.GONE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("临床路径管理");

        viewPager.setAdapter(new MyPageAdapter());
    }

    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            if (arg0 == arg1) {
                return true;
            }
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 实例化条目
            // 请求数据
            ImageView iv = null;
            try {
                iv = new ImageView(HomeFragment.this.getActivity());
                iv.setImageResource(images[position]);
                container.addView(iv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
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
           case R.id.tv_healthySpace:
               startActivity(new Intent(HomeFragment.this.getActivity(), WebViewActivity.class));
               break;
            default:
                break;
        }
    }

}
