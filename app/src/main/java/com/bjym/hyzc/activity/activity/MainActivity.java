package com.bjym.hyzc.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.MyselfBean;
import com.bjym.hyzc.activity.fragment.DiaoChaFragment;
import com.bjym.hyzc.activity.fragment.HomeFragment;
import com.bjym.hyzc.activity.fragment.MyFragment;
import com.bjym.hyzc.activity.fragment.TongJiFragment;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {


    private TextView tv_my;
    private TextView tv_research;
    private TextView tv_home;
    private TextView tv_count;
    List<Fragment> fragments=new ArrayList<>();


    private FragmentManager manager;
    private DrawerLayout drawer;

    private List<MyselfBean> myselefLists;
    private static String departmentCode;
    private static String realName;
    // private Bundle bundle;
    private String userCode;
    private String usercode;
    private int userType;
    private SharedPreferences sp;
    private ViewPager vp;

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    resetHomeView();
                    break;
                case 1:
                    resetDiaoChaView();
                    break;
                case 2:
                    resetTongJiView();
                    break;
                case 3:
                    resetMyView();
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyFragmentPageAdpter extends FragmentPagerAdapter {

        public MyFragmentPageAdpter(FragmentManager fm) {
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
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_main, null);
        tv_my = (TextView) view.findViewById(R.id.tv_accountcenter);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        tv_research = (TextView) view.findViewById(R.id.tv_diaocha);
        tv_count = (TextView) view.findViewById(R.id.tv_tongji);
        vp = (ViewPager) view.findViewById(R.id.vp);
        defaultHomeView();
        defaultReplaceHomeFragment();

        return view;
    }


    @Override
    public void InitData() {
        fragments.clear();
        fragments.add(new HomeFragment());
        fragments.add(new DiaoChaFragment());
        fragments.add(new TongJiFragment());
        fragments.add(new MyFragment());

        vp.setAdapter(new MyFragmentPageAdpter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        Intent intent = getIntent();
        if (intent != null) {
            usercode = intent.getStringExtra("usercode");
        }
        tv_my.setOnClickListener(this);
        tv_research.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        getNetData();
    }


    private void defaultReplaceHomeFragment() {
        vp.setCurrentItem(0);
    }


    private void defaultHomeView() {
        //默认主页为绿色
        Drawable home_hover = getResources().getDrawable(R.mipmap.home_hover);
        home_hover.setBounds(0, 0, home_hover.getMinimumWidth(), home_hover.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_home.setCompoundDrawables(null, home_hover, null, null);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.tv_home:
                vp.setCurrentItem(0);
                break;

            case R.id.tv_diaocha:
                vp.setCurrentItem(1);
                break;
            case R.id.tv_tongji:
                vp.setCurrentItem(2);
                break;
            case R.id.tv_accountcenter:
                vp.setCurrentItem(3);
                break;
            default:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    * 1.得到用户信息设置菜单页面的用户名
    * 2.传递给accountfragment 和 researchFragment
    * */
    private void getNetData() {
        OkHttpUtils.get().url(MyConstant.BASE_URL + MyConstant.MYMSG_URL + usercode).build().execute(new Callback() {

            private String qq;

            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String json = response.body().string();
                parseMyselfJson(json);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(MainActivity.this, "请求失败");
            }

            @Override
            public void onResponse(Object o, int in) {
                for (int i = 0; i < myselefLists.size(); i++) {
                    MyselfBean myself = myselefLists.get(i);
                    realName = myself.RealName;
                    departmentCode = myself.DepartmentCode;
                    userCode = myself.UserCode;
                    userType = myself.UserType;
                    qq = myself.QQ;

                }
                sp = getSharedPreferences("MyselfConfig", Context.MODE_PRIVATE);
                sp.edit().putString("departmentCode", departmentCode).commit();
                sp.edit().putString("userCode", userCode).commit();
                sp.edit().putString("realName", realName).commit();
                sp.edit().putString("qq", qq).commit();
                sp.edit().putInt("userType", userType).commit();
            }
        });
    }

    private void parseMyselfJson(String json) {

        myselefLists = new Gson().fromJson(json, new TypeToken<List<MyselfBean>>() {
        }.getType());
    }

    private void resetHomeView() {

        //默认主页为绿色
        defaultHomeView();

        //tv_research为灰色
        Drawable research = getResources().getDrawable(R.mipmap.research);
        research.setBounds(0, 0, research.getMinimumWidth(), research.getMinimumHeight());
        tv_research.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_research.setCompoundDrawables(null, research, null, null);

        //tv_count为灰色
        Drawable count = getResources().getDrawable(R.mipmap.count);
        count.setBounds(0, 0, count.getMinimumWidth(), count.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_count.setCompoundDrawables(null, count, null, null);


        //tv_my为灰色
        Drawable my = getResources().getDrawable(R.mipmap.my);
        my.setBounds(0, 0, my.getMinimumWidth(), my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_my.setCompoundDrawables(null, my, null, null);
    }

    private void resetDiaoChaView() {
        //默认主页为绿色
        Drawable research_hover = getResources().getDrawable(R.mipmap.research_hover);
        research_hover.setBounds(0, 0, research_hover.getMinimumWidth(), research_hover.getMinimumHeight());
        tv_research.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_research.setCompoundDrawables(null, research_hover, null, null);

        //tv_research为灰色
        Drawable home = getResources().getDrawable(R.mipmap.home);
        home.setBounds(0, 0, home.getMinimumWidth(), home.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_home.setCompoundDrawables(null, home, null, null);

        //tv_count为灰色
        Drawable count = getResources().getDrawable(R.mipmap.count);
        count.setBounds(0, 0, count.getMinimumWidth(), count.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_count.setCompoundDrawables(null, count, null, null);


        //tv_my为灰色
        Drawable my = getResources().getDrawable(R.mipmap.my);
        my.setBounds(0, 0, my.getMinimumWidth(), my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_my.setCompoundDrawables(null, my, null, null);

    }

    private void resetMyView() {

        //默认主页为绿色
        Drawable my_hover = getResources().getDrawable(R.mipmap.my_hover);
        my_hover.setBounds(0, 0, my_hover.getMinimumWidth(), my_hover.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_my.setCompoundDrawables(null, my_hover, null, null);

        //tv_research为灰色
        Drawable home = getResources().getDrawable(R.mipmap.home);
        home.setBounds(0, 0, home.getMinimumWidth(), home.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_home.setCompoundDrawables(null, home, null, null);

        //tv_count为灰色
        Drawable count = getResources().getDrawable(R.mipmap.count);
        count.setBounds(0, 0, count.getMinimumWidth(), count.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_count.setCompoundDrawables(null, count, null, null);


        //tv_research为灰色
        Drawable research = getResources().getDrawable(R.mipmap.research);
        research.setBounds(0, 0, research.getMinimumWidth(), research.getMinimumHeight());
        tv_research.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_research.setCompoundDrawables(null, research, null, null);

    }


    private void resetTongJiView() {
        //默认主页为绿色
        Drawable count2 = getResources().getDrawable(R.mipmap.count_hover);
        count2.setBounds(0, 0, count2.getMinimumWidth(), count2.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_count.setCompoundDrawables(null, count2, null, null);

        //tv_research为灰色
        Drawable home = getResources().getDrawable(R.mipmap.home);
        home.setBounds(0, 0, home.getMinimumWidth(), home.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_home.setCompoundDrawables(null, home, null, null);

        //tv_my为灰色
        Drawable my = getResources().getDrawable(R.mipmap.my);
        my.setBounds(0, 0, my.getMinimumWidth(), my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_my.setCompoundDrawables(null, my, null, null);


        //tv_research为灰色
        Drawable research = getResources().getDrawable(R.mipmap.research);
        research.setBounds(0, 0, research.getMinimumWidth(), research.getMinimumHeight());
        tv_research.setTextColor(getResources().getColor(R.color.myPationte_title_color));
        tv_research.setCompoundDrawables(null, research, null, null);

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragments.get(vp.getCurrentItem());
        fragment.onActivityResult(requestCode,resultCode,data);
    }
}
