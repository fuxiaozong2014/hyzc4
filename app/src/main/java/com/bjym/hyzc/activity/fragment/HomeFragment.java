package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.DeptNewsWebViewActivity;
import com.bjym.hyzc.activity.activity.WebViewActivity;
import com.bjym.hyzc.activity.pager.BasePager;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.view.CycleViewPagerShi;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class HomeFragment extends BaseFragment {
    private List<BasePager> pagers = new ArrayList<>();
    private CycleViewPagerShi viewPager;
    private int[] images;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    /**
     * 轮播图图片url集合
     */
    private ArrayList<String> imgUrls = new ArrayList<>();
    /**
     * 轮播图图片对应链接url集合
     */
    private ArrayList<String> backUrls = new ArrayList<>();
    /**
     * 轮播图图片标题集合
     */
    private ArrayList<String> imgTitles = new ArrayList<>();
    private LinearLayout ll_healthySpace;
    private LinearLayout ll_deptNews;
    private LinearLayout ll_studyVideo;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_home, null);

        ll_healthySpace = (LinearLayout) view.findViewById(R.id.ll_healthySpace);
        ll_deptNews = (LinearLayout) view.findViewById(R.id.ll_deptNews);
        ll_studyVideo = (LinearLayout) view.findViewById(R.id.ll_StudyVideo);

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

        getImageData();
        ll_healthySpace.setOnClickListener(this);
        ll_deptNews.setOnClickListener(this);
        ll_studyVideo.setOnClickListener(this);

        bt_titlebar_left.setVisibility(View.GONE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("临床路径管理");

    }

    private void getImageData() {
        OkHttpUtils.get().url(MyConstant.LUNBOTU).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
               // Toast.makeText(HomeFragment.this.getActivity(),"请求错误", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String s, int code) {
               // MyToast.showToast(,"请求错误");
                if (null == s) {
                    return;
                }
                try {
                    JSONArray jsonArray = new JSONObject(s).optJSONArray("advert");
                    JSONObject obj;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        obj = jsonArray.optJSONObject(i);
                        if (obj != null) {
                            imgUrls.add(obj.optString("file_path"));
                            backUrls.add(obj.optString("url"));
                            imgTitles.add(obj.optString("title"));
                        }
                    }
                    if (imgUrls.size() > 0) {
                        viewPager.setAdapter(new MyPageAdapter());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgUrls.size();
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
            ImageView imageView =new ImageView(context);
            Picasso.with(getContext())
                    .load(imgUrls.get(position))
                    .error(R.mipmap.ic_launcher)
                    .resize(viewPager.getWidth(), viewPager.getHeight())
//                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imageView);
            container.addView(imageView);
            return imageView;
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
           case R.id.ll_healthySpace:
               startActivity(new Intent(HomeFragment.this.getActivity(), WebViewActivity.class));
               break;
           case R.id.ll_deptNews:
               startActivity(new Intent(HomeFragment.this.getActivity(), DeptNewsWebViewActivity.class));
               break;
           case R.id.ll_StudyVideo:
               //startActivity(new Intent(HomeFragment.this.getActivity(), StudyVideoWebViewActivity.class));
              // MyToast.showToast(HomeFragment.this.getActivity(),"此功能正在研发中，敬请期待！！！");
               Toast.makeText(HomeFragment.this.getActivity(),"此功能正在研发中，敬请期待！！！",Toast.LENGTH_SHORT).show();

               break;
            default:
                break;
        }
    }

}
