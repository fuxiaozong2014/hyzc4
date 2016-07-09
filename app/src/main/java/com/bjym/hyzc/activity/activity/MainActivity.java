package com.bjym.hyzc.activity.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.fragment.AccenterFragment;
import com.bjym.hyzc.activity.fragment.DiaoChaFragment;
import com.bjym.hyzc.activity.fragment.HomeFragment;
import com.bjym.hyzc.activity.fragment.TongJiFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView tv_my;
    private TextView tv_diaocha;
    private TextView tv_home;
    private TextView tv_tongji;

    private LinearLayout ll_content;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_main, null);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_my = (TextView) view.findViewById(R.id.tv_accountcenter);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        tv_diaocha = (TextView) view.findViewById(R.id.tv_diaocha);
        tv_tongji = (TextView) view.findViewById(R.id.tv_tongji);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);

        //默认主页为绿色
        Drawable home2=getResources().getDrawable(R.mipmap.home2);
        home2.setBounds(0,0,home2.getMinimumWidth(),home2.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_home.setCompoundDrawables(null,home2,null,null);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        return view;
    }

    @Override
    public void InitData() {
        tv_my.setOnClickListener(this);
        tv_diaocha.setOnClickListener(this);
        tv_tongji.setOnClickListener(this);
        tv_home.setOnClickListener(this);
    }

    private int flag=-1;

    @Override
    public void onClick(View v) {
        if (v.getId()==flag){
            return;
        }
        flag =v.getId();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_accountcenter:
                resetMyView();
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, new AccenterFragment()).commit();

                break;
            case R.id.tv_diaocha:
                resetDiaoChaView();
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, new DiaoChaFragment()).commit();
                break;
            case R.id.tv_home:
                resetHomeView();
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, new HomeFragment()).commit();
                break;
            case R.id.tv_tongji:
                resetTongJiView();
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, new TongJiFragment()).commit();
                break;
            default:
                break;
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    private void resetHomeView() {

        //默认主页为绿色
        Drawable home2=getResources().getDrawable(R.mipmap.home2);
        home2.setBounds(0,0,home2.getMinimumWidth(),home2.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_home.setCompoundDrawables(null,home2,null,null);

        //tv_diaocha为灰色
        Drawable diaocha=getResources().getDrawable(R.mipmap.diaocha);
        diaocha.setBounds(0,0,diaocha.getMinimumWidth(),diaocha.getMinimumHeight());
        tv_diaocha.setTextColor(getResources().getColor(R.color.Hui));
        tv_diaocha.setCompoundDrawables(null,diaocha,null,null);

        //tv_tongji为灰色
        Drawable tongji=getResources().getDrawable(R.mipmap.tongji);
        tongji.setBounds(0,0,tongji.getMinimumWidth(),tongji.getMinimumHeight());
        tv_tongji.setTextColor(getResources().getColor(R.color.Hui));
        tv_tongji.setCompoundDrawables(null,tongji,null,null);


        //tv_my为灰色
        Drawable my=getResources().getDrawable(R.mipmap.my);
        my.setBounds(0,0,my.getMinimumWidth(),my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.Hui));
        tv_my.setCompoundDrawables(null,my,null,null);
    }

    private void resetDiaoChaView() {
        //默认主页为绿色
        Drawable diaocha2=getResources().getDrawable(R.mipmap.diaocha2);
        diaocha2.setBounds(0,0,diaocha2.getMinimumWidth(),diaocha2.getMinimumHeight());
        tv_diaocha.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_diaocha.setCompoundDrawables(null,diaocha2,null,null);

        //tv_diaocha为灰色
        Drawable home=getResources().getDrawable(R.mipmap.home);
        home.setBounds(0,0,home.getMinimumWidth(),home.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.Hui));
        tv_home.setCompoundDrawables(null,home,null,null);

        //tv_tongji为灰色
        Drawable tongji=getResources().getDrawable(R.mipmap.tongji);
        tongji.setBounds(0,0,tongji.getMinimumWidth(),tongji.getMinimumHeight());
        tv_tongji.setTextColor(getResources().getColor(R.color.Hui));
        tv_tongji.setCompoundDrawables(null,tongji,null,null);


        //tv_my为灰色
        Drawable my=getResources().getDrawable(R.mipmap.my);
        my.setBounds(0,0,my.getMinimumWidth(),my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.Hui));
        tv_my.setCompoundDrawables(null,my,null,null);

    }

    private void resetMyView() {

        //默认主页为绿色
        Drawable my2=getResources().getDrawable(R.mipmap.my2);
        my2.setBounds(0,0,my2.getMinimumWidth(),my2.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_my.setCompoundDrawables(null,my2,null,null);

        //tv_diaocha为灰色
        Drawable home=getResources().getDrawable(R.mipmap.home);
        home.setBounds(0,0,home.getMinimumWidth(),home.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.Hui));
        tv_home.setCompoundDrawables(null,home,null,null);

        //tv_tongji为灰色
        Drawable tongji=getResources().getDrawable(R.mipmap.tongji);
        tongji.setBounds(0,0,tongji.getMinimumWidth(),tongji.getMinimumHeight());
        tv_tongji.setTextColor(getResources().getColor(R.color.Hui));
        tv_tongji.setCompoundDrawables(null,tongji,null,null);


        //tv_diaocha为灰色
        Drawable diaocha=getResources().getDrawable(R.mipmap.diaocha);
        diaocha.setBounds(0,0,diaocha.getMinimumWidth(),diaocha.getMinimumHeight());
        tv_diaocha.setTextColor(getResources().getColor(R.color.Hui));
        tv_diaocha.setCompoundDrawables(null,diaocha,null,null);

    }


    private void resetTongJiView() {
        //默认主页为绿色
        Drawable tongji2=getResources().getDrawable(R.mipmap.tongji2);
        tongji2.setBounds(0,0,tongji2.getMinimumWidth(),tongji2.getMinimumHeight());
        tv_tongji.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_tongji.setCompoundDrawables(null,tongji2,null,null);

        //tv_diaocha为灰色
        Drawable home=getResources().getDrawable(R.mipmap.home);
        home.setBounds(0,0,home.getMinimumWidth(),home.getMinimumHeight());
        tv_home.setTextColor(getResources().getColor(R.color.Hui));
        tv_home.setCompoundDrawables(null,home,null,null);

        //tv_my为灰色
        Drawable my=getResources().getDrawable(R.mipmap.my);
        my.setBounds(0,0,my.getMinimumWidth(),my.getMinimumHeight());
        tv_my.setTextColor(getResources().getColor(R.color.Hui));
        tv_my.setCompoundDrawables(null,my,null,null);



        //tv_diaocha为灰色
        Drawable diaocha=getResources().getDrawable(R.mipmap.diaocha);
        diaocha.setBounds(0,0,diaocha.getMinimumWidth(),diaocha.getMinimumHeight());
        tv_diaocha.setTextColor(getResources().getColor(R.color.Hui));
        tv_diaocha.setCompoundDrawables(null,diaocha,null,null);


    }
}
