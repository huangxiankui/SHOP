package com.yida.ui;

import com.yida.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class Songcan1 extends Activity implements OnItemClickListener,OnItemSelectedListener{
	//菜的品种
	private static String[] canpingzhong=new String[]{"鸡腿饭","烧鸡饭","番茄鸡蛋饭","香肠饭","卤菜饭","红烧肉饭"};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.songcan1);
		ListView lvsongcan=(ListView)findViewById(R.id.lvSongcan);
		ArrayAdapter<String> songcan1adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,canpingzhong);
		lvsongcan.setAdapter(songcan1adapter);
		lvsongcan.setOnItemClickListener(this);
		lvsongcan.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
		
	}
	
}
