package com.yida.ui;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

import com.baidu.mapapi.SDKInitializer;
import com.yida.R;
import com.yida.base.NetWorkStateUtil;
import com.yida.share.ShareModel;
import com.yida.share.SharePopupWindow;



/**
 * @author huangxiankui
 * 周围 分类 
 *
 */
public class Zhouwei  extends Activity implements OnClickListener,PlatformActionListener,
Callback {
	//百度地图
	private static final String LTAG = Zhouwei.class.getSimpleName();
	private SDKReceiver mReceiver;
	//Rss订购的新闻  新浪测试
   String path="http://rss.sina.com.cn/news/marquee/ddt.xml";
	//分享内容等的定义
	private String text = "易购平台,二手货物,公交,最新时讯,兼职,快餐,水电费一网打尽,快来下载使用吧!";
	private String imageurl = "http://www.wyl.cc/wp-content/uploads/2014/02/10060381306b675f5c5.jpg";
	private String title = "易购-帮您快乐购物";
	private String url = "http://www.eeetao.net//www//index.php";
	public static final String SHARE_APP_KEY = "21b0f35691b8";
	private SharePopupWindow share;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
		// 不要使用SDKInitializer.initialize(this);,报错,无法运行  
		  SDKInitializer.initialize(getApplicationContext());  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhouwei);
		ShareSDK.initSDK(this);
		
			
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
		
		
		
		Button kuaicanButton=(Button)findViewById(R.id.bt1);
          kuaicanButton.setOnClickListener(this);
		Button yishuiButton=(Button)findViewById(R.id.bt2);
		yishuiButton.setOnClickListener(this);
		Button jianzhiButton=(Button)findViewById(R.id.bt3);
		jianzhiButton.setOnClickListener(this);
		Button kebiaoButton=(Button)findViewById(R.id.bt4);
		kebiaoButton.setOnClickListener(this);
		Button xinwuButton=(Button)findViewById(R.id.bt5);
		xinwuButton.setOnClickListener(this);
		Button xinwenButton=(Button)findViewById(R.id.bt6);
		xinwenButton.setOnClickListener(this);
		Button gongjiaoButton=(Button)findViewById(R.id.bt7);
		gongjiaoButton.setOnClickListener(this);
		Button guanyuButton=(Button)findViewById(R.id.bt8);
		guanyuButton.setOnClickListener(this);
		Button nfcButton=(Button)findViewById(R.id.bt9);
		guanyuButton.setOnClickListener(this);
		Button fenxiangButton=(Button)findViewById(R.id.bt10);
        //分享到微信 朋友圈等
		fenxiangButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				share = new SharePopupWindow(Zhouwei.this);
				share.setPlatformActionListener(Zhouwei.this);
				ShareModel model = new ShareModel();
				model.setImageUrl(imageurl);
				model.setText(text);
				model.setTitle(title);
				model.setUrl(url);
				share.initShareParams(model);
				share.showShareWindow();
				// 显示窗口 (设置layout在PopupWindow中显示的位置)
				share.showAtLocation(
						Zhouwei.this.findViewById(R.id.bt10),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); 
			}
		});
		
	}
	
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Log.e("error", "key错误") ;
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Log.e("error"," 亲,请设置网络! o(╯□╰)o");
			}
		}
	}
	public void onClick(View v)
	{
		switch (v.getId()) {
		//快餐界面
		case R.id.bt1:
			Intent kuaicanintent=new Intent(this,Kuaican.class);
			startActivity(kuaicanintent);
			break;
		//兼职界面
		case R.id.bt3:
			Intent jianzhiintent=new Intent(this,CategoryActivity.class);
			startActivity(jianzhiintent);
			break;
		case R.id.bt4:
			break;
			
			//map公交
		case   R.id.bt7:
			Intent mapintent =new Intent(this,map.class);
			startActivity(mapintent);
			break;
			//新闻速递
		case R.id.bt6:
			Intent news=new Intent(this,NewsFragmentactivity.class);
			startActivity(news);
			break;
			
	   
			
		default:
			break;
		}
	}
	@Override
	public boolean handleMessage(Message msg ) {
		int what = msg.what;
		if (what == 1) {
			Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
		}
		if (share != null) {
			share.dismiss();
		}
		return false;
	}
	
	@Override
	public void onCancel(Platform arg0, int arg1) {
		Message msg = new Message();
		msg.what = 0;
		UIHandler.sendMessage(msg, this);
	}
	@Override
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}
	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		Message msg = new Message();
		msg.what = 1;
		UIHandler.sendMessage(msg, this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (share != null) {
			share.dismiss();
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消监听 SDK 广播
		unregisterReceiver(mReceiver);
	}

}
