package com.bjym.hyzc.activity.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.MyselfBean;
import com.bjym.hyzc.activity.fragment.MyFragment;
import com.bjym.hyzc.activity.fragment.DiaoChaFragment;
import com.bjym.hyzc.activity.fragment.HomeFragment;
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

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView tv_my;
    private TextView tv_research;
    private TextView tv_home;
    private TextView tv_count;
    private HomeFragment home;
    private DiaoChaFragment diaoCha;
    private MyFragment accenter;
    private TongJiFragment tongJi;

    private TextView tv_menu_accout;
    private TextView tv_menu_keshi;

    private FragmentManager manager;
    private DrawerLayout drawer;

    private List<MyselfBean> myselefLists;
    private static String departmentCode;
    private static String realName;
    private Bundle bundle;
    private String userCode;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_main, null);

        Toolbar toolbar = initView(view);

        toolbar.setTitle("测试标题");
        //getSupportActionBar(toolbar);
       /* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                        break;
                   /* case R.id.action_share:
                        Toast.makeText(MainActivity.this, "action_share", 0).show();
                        break;*/
                    default:
                        break;
                }
                return true;
            }
        });

        defaultHomeView();
        defaultReplaceHomeFragment();
        dealFloatingButton(view);

        dealDrawerLayout(view, toolbar);

        dealNavigation(view);

        return view;
    }

    private void dealNavigation(View view) {
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /*
       *  在这里处理菜单头布局的逻辑
       */

        View headerView = navigationView.getHeaderView(0);
        tv_menu_keshi = (TextView) headerView.findViewById(R.id.tv_menu_keshi);
        tv_menu_accout = (TextView) headerView.findViewById(R.id.tv_menu_accout);
    }


    @Override
    public void InitData() {
        tv_my.setOnClickListener(this);
        tv_research.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        getNetData();
    }

    private Toolbar initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_my = (TextView) view.findViewById(R.id.tv_accountcenter);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        tv_research = (TextView) view.findViewById(R.id.tv_diaocha);
        tv_count = (TextView) view.findViewById(R.id.tv_tongji);
        return toolbar;
    }

    private void dealDrawerLayout(View view, Toolbar toolbar) {
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void dealFloatingButton(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        //   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*
    * 1.得到用户信息设置菜单页面的用户名
    * 2.传递给accountfragment 和 researchFragment
    * */
    private void getNetData() {
        OkHttpUtils.get().url(MyConstant.MYMSG_URL).build().execute(new Callback() {

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

                }

                tv_menu_accout.setText("用户名：" + userCode);
                tv_menu_keshi.setText("科室名：" + departmentCode);

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


}
