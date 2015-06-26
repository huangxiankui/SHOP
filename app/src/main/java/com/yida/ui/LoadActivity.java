package com.yida.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yida.R;
import com.yida.base.LoadJson;
/**
 * @author huangxiankui
 *初始状态&加载界面
 */
public class LoadActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		final  int DELAY_TIME=3000;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				try
				{
					Thread.sleep(DELAY_TIME);
				//	loadjson.getJson(adUrl);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent =new Intent();
				intent.setClass(LoadActivity.this, HomeActivity.class);
				startActivity(intent);
				LoadActivity.this.finish();
			}
		}).start();
	}

	

    

    
   
}
