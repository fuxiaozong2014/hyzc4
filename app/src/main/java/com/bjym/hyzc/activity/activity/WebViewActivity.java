package com.bjym.hyzc.activity.activity;


import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bjym.hyzc.R;

public class WebViewActivity extends BaseActivity {

	private WebSettings webViewSettings;
	private WebView webview;

	@Override
	public View setMainView() {
		View view=View.inflate(context,R.layout.activtiy_webview,null);
		webview = (WebView)view.findViewById(R.id.webView1);
		return view;
	}

	@Override
	public void InitData() {
		//showDialogProgress(null);
		// 获得webView的设置
		webViewSettings = webview.getSettings();
		// 设置可以使用JavaScript
		webViewSettings.setJavaScriptEnabled(true);
		// 设置默认缩放级别// 高强度
		webViewSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置按钮缩放
		webViewSettings.setBuiltInZoomControls(true);// 打开缩放按钮

		webview.loadUrl("http://m.hyzczg.com/");
		//dismiss();
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);

				return true;
			}
		});

	}
}
