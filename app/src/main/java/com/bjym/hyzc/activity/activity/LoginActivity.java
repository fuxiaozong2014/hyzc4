package com.bjym.hyzc.activity.activity;

import android.animation.ObjectAnimator;
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
    //  private Button btn_exit;
    private String password;
    private String usercode;
    private SharedPreferences sp;
    private CheckBox cb;
    private LinearLayout rela_wait_loading;


    /* private Handler handler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             switch (msg.what) {
                 case RELA_WAIT_LOADING:
                     if (rela_wait_loading != null) {
                         rela_wait_loading.setVisibility(View.VISIBLE);
                     }

                     break;
                 case WHAT_DISMISS_LOADING:
                     if (rela_wait_loading != null) {
                         rela_wait_loading.setVisibility(View.GONE);
                     }
                     break;
             }

         }
     };
 */
    @Override
    public View setMainView() {


        sp = getSharedPreferences("Config", MODE_PRIVATE);
        View view = View.inflate(LoginActivity.this, R.layout.activity_login, null);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView)view.findViewById(R.id.tv_titlebar_center);

        btn_login = (Button) view.findViewById(R.id.btn_login);
        rela_wait_loading = (LinearLayout) view.findViewById(R.id.Rela_wait_loading);

        // btn_exit = (Button) view.findViewById(R.id.btn_exit);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        cb = (CheckBox) view.findViewById(R.id.cb);
        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.GONE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("登录");
         /*
        * 记住密码后，回显账号和密码，以及isChecked
        * */
        et_name.setText(sp.getString("usercode", ""));
        et_pwd.setText(sp.getString("password", ""));
        cb.setChecked(sp.getBoolean("isChecked", false));

        btn_login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login:

                getNetData();
                break;

            case R.id.cb:


                break;
            default:
                break;
        }
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
            //handler.sendEmptyMessageDelayed(RELA_WAIT_LOADING, 500);
            OkHttpUtils.post()
                    .url(MyConstant.LOGIN_URL)
                    .addParams("usercode", usercode)
                    .addParams("password", password)
                    .addParams("expired", "365")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            //dismissWaitingDialog();
                            String string = response.body().string();
                            parseJson(string);
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {
                            e.printStackTrace();
                            MyLog.i("e.printStackTrace();", e.toString());
                            MyToast.showToast(LoginActivity.this, "请检查网络设置或稍后再试");
                            //dismissWaitingDialog();
                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            //dismissWaitingDialog();
                        }
                    });
        }
    }

    private void goToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void parseJson(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject.optBoolean("Succeed")) {
            MyToast.showToast(LoginActivity.this, "登录成功");
            saveUserInfo();
            setAlias();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
             finish();
        } else {
            //TODO 这里其实有两种状态 密码有误  账户不存在
            //{"ResultID":50000,"ResultMsg":"账号不存在！","Succeed":false,"ResultData":null,"s":false,"emsg":"账号不存在！"}
            //{"ResultID":50000,"ResultMsg":"密码有误！","Succeed":false,"ResultData":null,"s":false,"emsg":"密码有误！"}
            MyToast.showToast(LoginActivity.this, "密码有误或账户不存在");
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

    /*private void dismissWaitingDialog() {
        handler.sendEmptyMessage(WHAT_DISMISS_LOADING);
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
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
}
