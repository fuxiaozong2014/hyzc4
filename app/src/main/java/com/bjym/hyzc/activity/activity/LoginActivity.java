package com.bjym.hyzc.activity.activity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/6/27.
 */
public class LoginActivity extends BaseActivity {

    private static final int RELA_WAIT_LOADING = 1;
    private static final int WHAT_DISMISS_LOADING = 2;
    private Button btn_login;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    private EditText et_name;
    private EditText et_pwd;
    private String password;
    private String usercode;
    private SharedPreferences sp;
    private CheckBox cb;
    private LinearLayout ll_mainContent;
    private String[] hospitalNames;
    private String[] hospitalURLs;

    @Override
    public View setMainView() {

        sp = getSharedPreferences("Config", MODE_PRIVATE);
        View view = View.inflate(LoginActivity.this, R.layout.activity_login, null);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        btn_login = (Button) view.findViewById(R.id.btn_login);
        ll_mainContent = (LinearLayout) view.findViewById(R.id.ll_mainContent);

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        cb = (CheckBox) view.findViewById(R.id.cb);
        return view;
    }

    @Override
    public void InitData() {

        hospitalNames = new String[]{"测试ip","吴桥人民医院","河南省人民医院"};
        hospitalURLs = new String[]{"http://192.168.0.188","http://192.168.0.165","http://cp.sqallll.com"};

        bt_titlebar_left.setVisibility(View.GONE);
        bt_titlebar_right.setVisibility(View.VISIBLE);
        tv_titlebar_center.setText("登录");
        //上来检测有没有设置过IP
        dealSettingIP();
        et_name.setText(sp.getString("usercode", ""));
        et_pwd.setText(sp.getString("password", ""));
        cb.setChecked(sp.getBoolean("isChecked", false));

        btn_login.setOnClickListener(this);
        bt_titlebar_right.setOnClickListener(this);

    }

    private void dealSettingIP() {
        String hospitalURL=sp.getString("hospitalURL","");
        MyLog.i("hospitalURL::",hospitalURL);

        if (hospitalURL.equals("")) {
            MyToast.showToast(LoginActivity.this,"请点击'设置'您所在的医院");

        }else {
            MyConstant.BASE_URL=hospitalURL;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                getNetData();
                break;
            case R.id.bt_titlebar_right:
                showIPSetting();
                break;
            case R.id.cb:
                break;
            default:
                break;
        }
    }

    private void showIPSetting() {
        int which = sp.getInt("which", 0);
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("请选择：您所在的医院");
        builder.setSingleChoiceItems(hospitalNames, which, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sp.edit().putString("hospitalURL",hospitalURLs[which]).commit();
                sp.edit().putString("hospitalName",hospitalNames[which]).commit();
                sp.edit().putInt("which",which).commit();
                MyConstant.BASE_URL=hospitalURLs[which];
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void getNetData() {

        usercode = et_name.getText().toString().trim();
        password = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(usercode)) {
            ObjectAnimator.ofFloat(et_name, "translationX", 15f, -15f, 20f, -20f, 0).setDuration(300).start();

            MyToast.showToast(LoginActivity.this, "请输入用户名");
            return;
        } else if (TextUtils.isEmpty(password)) {
            ObjectAnimator.ofFloat(et_pwd, "translationX", 15f, -15f, 20f, -20f, 0).setDuration(300).start();
            MyToast.showToast(LoginActivity.this, "请输入密码");
            return;
        } else {
            //TODO
            showDialogProgress("登录中");
            MyLog.i("MyConstant.LOGIN_URL:::",MyConstant.BASE_URL+MyConstant.LOGIN_URL);
            OkHttpUtils.post()
                    .url(MyConstant.BASE_URL+MyConstant.LOGIN_URL)
                    .addParams("usercode", usercode)
                    .addParams("password", password)
                    .addParams("expired", "365")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();
                            parseJson(string);
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {
                            e.printStackTrace();
                            MyLog.i("e.printStackTrace();", e.toString());
                            dismiss();
                            MyToast.showToast(LoginActivity.this, "请检查网络设置或稍后再试");
                           // ll_mainContent.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onResponse(Object o, int i) {
                        }
                    });
        }
    }

    private void parseJson(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject.optBoolean("Succeed")) {
            dismiss();
            MyToast.showToast(LoginActivity.this, "登录成功");
            saveUserInfo();
            setAlias();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usercode",usercode);
            startActivity(intent);
            finish();
        } else {
            //TODO 这里其实有两种状态 密码有误  账户不存在
            //{"ResultID":50000,"ResultMsg":"账号不存在！","Succeed":false,"ResultData":null,"s":false,"emsg":"账号不存在！"}
            //{"ResultID":50000,"ResultMsg":"密码有误！","Succeed":false,"ResultData":null,"s":false,"emsg":"密码有误！"}
            MyToast.showToast(LoginActivity.this, "密码有误或账户不存在");
            dismiss();
            //ll_mainContent.setVisibility(View.VISIBLE);
        }
    }

    private void saveUserInfo() {

        sp.edit().putString("usercode", usercode).commit();
        sp.edit().putString("password", password).commit();
        sp.edit().putBoolean("isChecked", cb.isChecked()).commit();
        if (cb.isChecked()) {
            sp.edit().putString("password", password).commit();
        } else {
            sp.edit().putString("password", "").commit();
        }
    }


    private void setAlias() {
        HashSet<String> set = new HashSet<>();
        set.add("哈哈");

        JPushInterface.setAliasAndTags(LoginActivity.this, usercode, set, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                MyToast.showToast(LoginActivity.this, "Tag设置成功");
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    //再点一次腿退出
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


}
