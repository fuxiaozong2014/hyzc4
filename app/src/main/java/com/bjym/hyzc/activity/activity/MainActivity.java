package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
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

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {


    private TextView tv_my;
    private TextView tv_research;
    private TextView tv_home;
    private TextView tv_count;
    private HomeFragment home;
    private DiaoChaFragment diaoCha;
    private MyFragment accenter;
    private TongJiFragment tongJi;


    private FragmentManager manager;
    private DrawerLayout drawer;

    private List<MyselfBean> myselefLists;
    private static String departmentCode;
    private static String realName;
    private Bundle bundle;
    private String userCode;
    private String usercode;
    private int userType;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_main, null);
        tv_my = (TextView) view.findViewById(R.id.tv_accountcenter);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        tv_research = (TextView) view.findViewById(R.id.tv_diaocha);
        tv_count = (TextView) view.findViewById(R.id.tv_tongji);
        defaultHomeView();
        defaultReplaceHomeFragment();

        return view;
    }



    @Override
    public void InitData() {
        Intent intent = getIntent();
        if (intent!=null){
            usercode = intent.getStringExtra("usercode");

        }
        tv_my.setOnClickListener(this);
        tv_research.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        getNetData();
    }



    private void defaultReplaceHomeFragment() {
        //默认填充主页fragment
        manager = getSupportFragmentManager();
        FragmentTransaction transition = manager.beginTransaction();
        if (home == null) {
            home = new HomeFragment();

        }
        transition.replace(R.id.ll_content, home).commit();
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
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.tv_accountcenter:
                if (accenter == null) {
                    accenter = new MyFragment();
                    accenter.setArguments(bundle);

                }
                resetMyView();
                transaction.replace(R.id.ll_content, accenter, "MyFragment");
                break;
            case R.id.tv_diaocha:
                if (diaoCha == null) {
                    diaoCha = new DiaoChaFragment();
                    diaoCha.setArguments(bundle);
                }
                resetDiaoChaView();
                transaction.replace(R.id.ll_content, diaoCha, "DiaoChaFragment");
                break;
            case R.id.tv_home:
                if (home == null) {
                    home = new HomeFragment();
                }
                resetHomeView();
                transaction.replace(R.id.ll_content, home, "HomeFragment");
                break;
            case R.id.tv_tongji:
                if (tongJi == null) {
                    tongJi = new TongJiFragment();
                }
                resetTongJiView();
                transaction.replace(R.id.ll_content, tongJi, "TongJiFragment");
                break;
            default:
                break;
        }
        transaction.commit();

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
        OkHttpUtils.get().url(MyConstant.MYMSG_URL+usercode).build().execute(new Callback() {

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

                }

                /*
                * 通过Bundle给acconteFragment和researchFragment传值
                * */
                bundle = getBundle();


            }
        });
    }

    @NonNull
    private Bundle getBundle() {
        Bundle bundle = new Bundle();

        bundle.putString("departmentCode", departmentCode);
        bundle.putString("realName", realName);
        bundle.putString("userCode", userCode);
        bundle.putInt("userType",userType);
        return bundle;
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
        tv_research.setTextColor(getResources().getColor(R.color.Hui));
        tv_research.setCompoundDrawables(null, research, null, null);

        //tv_count为灰色
        Drawable count = getResources().getDrawable(R.mipmap.count);
        count.setBounds(0, 0, count.getMinimumWidth(), count.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.Hui));
        tv_count.setCompoundDrawables(null, count, null, null);


        //tv_my为灰色
        Drawable my = getResources().getDrawable(R.mipmap.my);
        my.setBounds(0, 0, my.getMinimumWidth(), my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.Hui));
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
        tv_home.setTextColor(getResources().getColor(R.color.Hui));
        tv_home.setCompoundDrawables(null, home, null, null);

        //tv_count为灰色
        Drawable count = getResources().getDrawable(R.mipmap.count);
        count.setBounds(0, 0, count.getMinimumWidth(), count.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.Hui));
        tv_count.setCompoundDrawables(null, count, null, null);


        //tv_my为灰色
        Drawable my = getResources().getDrawable(R.mipmap.my);
        my.setBounds(0, 0, my.getMinimumWidth(), my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.Hui));
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
        tv_home.setTextColor(getResources().getColor(R.color.Hui));
        tv_home.setCompoundDrawables(null, home, null, null);

        //tv_count为灰色
        Drawable count = getResources().getDrawable(R.mipmap.count);
        count.setBounds(0, 0, count.getMinimumWidth(), count.getMinimumHeight());
        tv_count.setTextColor(getResources().getColor(R.color.Hui));
        tv_count.setCompoundDrawables(null, count, null, null);


        //tv_research为灰色
        Drawable research = getResources().getDrawable(R.mipmap.research);
        research.setBounds(0, 0, research.getMinimumWidth(), research.getMinimumHeight());
        tv_research.setTextColor(getResources().getColor(R.color.Hui));
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
        tv_home.setTextColor(getResources().getColor(R.color.Hui));
        tv_home.setCompoundDrawables(null, home, null, null);

        //tv_my为灰色
        Drawable my = getResources().getDrawable(R.mipmap.my);
        my.setBounds(0, 0, my.getMinimumWidth(), my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.Hui));
        tv_my.setCompoundDrawables(null, my, null, null);


        //tv_research为灰色
        Drawable research = getResources().getDrawable(R.mipmap.research);
        research.setBounds(0, 0, research.getMinimumWidth(), research.getMinimumHeight());
        tv_research.setTextColor(getResources().getColor(R.color.Hui));
        tv_research.setCompoundDrawables(null, research, null, null);

    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
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

}
