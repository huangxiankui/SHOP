package com.yida.ui;

import com.yida.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class NewsContent extends Activity{
	/* 变量声明 */
	  private TextView mTitle;
	  private TextView mDesc;
	  private WebView webView;

	 
	@Override
	  public void onCreate(Bundle savedInstanceState)
	  {
	    super.onCreate(savedInstanceState);
	    /* 设置layout为newscontent.xml */
	    setContentView(R.layout.newscontent);
	    /* 初始化对象 */
	    mTitle=(TextView) findViewById(R.id.myTitle);
	    mDesc=(TextView) findViewById(R.id.myDesc);
	    //设置滚动条
	    mDesc.setMovementMethod(ScrollingMovementMethod.getInstance());
	   //
	    webView=(WebView)findViewById(R.id.myLink);
	  
	    /* 取得Intent中的Bundle对象 */
	    Intent intent=this.getIntent();
	    Bundle bunde = intent.getExtras();
	    /* 取得Bundle对象中的数据 */
	    
	    mTitle.setText(bunde.getString("title"));
	    mDesc.setText(bunde.getString("desc")+"....");
	  String url=bunde.getString("link");
	  webView.loadUrl(url);
	WebSettings setting=webView.getSettings();
	setting.setJavaScriptEnabled(true);
	setting.setPluginState(PluginState.ON);

	  setting.setJavaScriptCanOpenWindowsAutomatically(true);
	  setting.setAllowFileAccess(true);
	  setting.setDefaultTextEncodingName("UTF-8");
	 setting.setLoadWithOverviewMode(true);
	 setting.setUseWideViewPort(true);
	
	 //优先使用缓存
	setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	  // webView.getSettings().setJavaScriptEnabled(true);
	  //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
    webView.setWebViewClient(new WebViewClient(){
        @Override
     public boolean shouldOverrideUrlLoading(WebView view, String url) {
         // TODO Auto-generated method stub
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
          view.loadUrl(url);
         return true;
     }
    });
	  
	  
	    //  mLink.setText(bunde.getString("link"));
	    /* 设置mLink为网页连接 */
	  //  Linkify.addLinks(mLink,Linkify.WEB_URLS);
	  }
}
