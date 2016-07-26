package com.bjym.hyzc.activity.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyConstant;
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

    private Button btn_login;
    private EditText et_name;
    private EditText et_pwd;
  //  private Button btn_exit;
    private String password;
    private String usercode;
    private SharedPreferences sp;
    private CheckBox cb;

    @Override
    public View setMainView() {
        sp = getSharedPreferences("Config", MODE_PRIVATE);
        View view = View.inflate(LoginActivity.this, R.layout.activity_login, null);
        btn_login = (Button) view.findViewById(R.id.btn_login);
       // btn_exit = (Button) view.findViewById(R.id.btn_exit);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        cb = (CheckBox) view.findViewById(R.id.cb);
        return view;
    }

    @Override
    public void InitData() {
         /*
        * 记住密码后，回显账号和密码，以及isChecked
        * */
        et_name.setText(sp.getString("usercode",""));
        et_pwd.setText(sp.getString("password",""));
        cb.setChecked(sp.getBoolean("isChecked",false));

        btn_login.setOnClickListener(this);
       // btn_exit.setOnClickListener(this);

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
        if (TextUtils.isEmpty(usercode) || TextUtils.isEmpty(password)) {
            ObjectAnimator.ofFloat(et_name, "translationX", 15f, -15f, 20f, -20f, 0).setDuration(300).start();
            ObjectAnimator.ofFloat(et_pwd, "translationX", 15f, -15f, 20f, -20f, 0).setDuration(300).start();
            MyToast.showToast(LoginActivity.this, "请输入用户名或密码");
            return;
        } else {
            OkHttpUtils.post()
                    .url(MyConstant.LOGIN_URL)
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
                            //  MyToast.showToast(LoginActivity.this, "请求失败");
                        }

                        @Override
                        public void onResponse(Object o, int i) {

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
        sp.edit().putBoolean("isChecked", true).commit();
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
}
