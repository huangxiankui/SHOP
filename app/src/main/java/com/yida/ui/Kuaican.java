package com.yida.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yida.R;

/**
 * @author huangxiankui
 *listview显示送餐家,点击查看菜色
 */
public class Kuaican extends Activity implements OnItemClickListener,OnItemSelectedListener {
	//标题
	private static String[] songcantitle=new String[]{ " 送餐阿姨张","送餐阿姨李","送餐叔叔张","送餐叔叔李"};
    //内容
	private static String[] content=new String[]{"餐饭供应:鸡腿","餐饭供应:香肠","餐饭供应:火腿","餐饭供应:肉"};
	int [] resIds={R.drawable.icon,R.drawable.icon,R.drawable.icon,R.drawable.icon}; 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.kuaican);
		ListView lvCommonListView =(ListView)findViewById(R.id.lvCommonListView);
		//使用android.R.layout.simple_expandable_list_item_1布局则不会.而不是R.layout
 ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,songcantitle);
 lvCommonListView.setAdapter(adapter);
 lvCommonListView.setOnItemClickListener(this);
 lvCommonListView.setOnItemSelectedListener(this);
	}

 //单击列表调用该方法
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
		long id) {
	// TODO Auto-generated method stub
		
}

	

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	//选择列表调用该方法
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		//送餐张阿姨家菜色介绍
		if(position==0)
		{ 
			Intent songcan1intent=new Intent(this,Songcan1.class);
			startActivity(songcan1intent);
		}
		if(position==1)
		{
			
		}
		if(position==2)
		{
			
		}
		if(position==3)
		{
			
		}
	
		
	}


}
