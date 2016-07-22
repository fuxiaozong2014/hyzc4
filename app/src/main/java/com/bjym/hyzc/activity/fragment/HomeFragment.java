package com.bjym.hyzc.activity.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.view.CycleViewPager;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class HomeFragment extends BaseFragment{
    private CycleViewPager viewPager;
    private int[] images;

    @Override
    public View setMainView() {
        View view=View.inflate(getContext(), R.layout.fragment_home,null);
        viewPager = (CycleViewPager) view.findViewById(R.id.viewPager);
        images = new int[] { R.mipmap.d1, R.mipmap.d1,
                R.mipmap.d1, R.mipmap.d1};
        return view;
    }

    @Override
    public void InitData() {

        viewPager.setAdapter(new MyPageAdapter());
    }

    class MyPageAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return images.length;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
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
                iv = new ImageView(context);
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
}
