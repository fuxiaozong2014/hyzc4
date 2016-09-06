package com.bjym.hyzc.activity.activity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bjym.hyzc.R;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class DeptNewsWebViewActivity extends BaseActivity {
    private WebSettings webViewSettings;
    private WebView webview;
    private LinearLayout ll_wait_loading;
    @Override
    public View setMainView() {
        View view=View.inflate(context, R.layout.activtiy_deptnewswebview,null);
        webview = (WebView)view.findViewById(R.id.webView1);
        ll_wait_loading = (LinearLayout) view.findViewById(R.id.ll_wait_loading);
        return view;
    }

    @Override
    public void InitData() {


        webview.loadUrl("http://m.hyzczg.com/news/55.html");
        initWebSetting();
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                System.out.println(url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ll_wait_loading.setVisibility(View.VISIBLE);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                ll_wait_loading.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 对webview进行设置
     */
    private void initWebSetting() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);

        webview.setLayerType(View.LAYER_TYPE_HARDWARE, new Paint());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        try {
            webview.getClass().getMethod("onResume").invoke(webview, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        try {
            webview.getClass().getMethod("onPause").invoke(webview, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ((ViewGroup) getWindow().getDecorView()).removeAllViews();
        super.onDestroy();
    }
}
