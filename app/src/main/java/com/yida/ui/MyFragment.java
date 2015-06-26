package com.yida.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.yida.R;
import com.yida.news.until.MyAdapter;
import com.yida.news.until.MyHandler;
import com.yida.news.until.News;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyFragment extends Fragment implements OnItemClickListener{
	private Activity mActivity;
	private View mView;
	private int mIndex;
	private Resources resources;
	private SimpleAdapter list;
	private ListView mListView;
	
	 private TextView mText;
	  private String title="";
	  private List<News> li=new ArrayList<News>();
	/* mList是用来存放要显示的数据 */
	private List<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();

	public MyFragment(Activity activity, int index) {
		mActivity = activity;
		mIndex = index;
		resources = mActivity.getResources();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		
		mView=inflater.inflate(R.layout.newslist, null);
		mListView = (ListView) mView.findViewById(R.id.list);
	
		//实现onitemclicklistener
		mListView.setOnItemClickListener(this);
		/* 给各个页卡不同的背景以示区别 */
		if (mIndex == 0) {
			mView.setBackgroundColor(0xffFCDAD5);
			//新闻要闻
			String path0="http://rss.sina.com.cn/news/marquee/ddt.xml";
			initList(path0);
		} else if (mIndex == 1) {
			mView.setBackgroundColor(0xffFFFAB3);
			//国内要闻
			String path1="http://rss.sina.com.cn/news/china/focus15.xml";
			initList(path1);
		} 
		else if(mIndex==2)
		{
			mView.setBackgroundColor(0xffCAE1FF);
			//社会与法
			String path2="http://rss.sina.com.cn/news/society/law15.xml";
		initList(path2);
			mView.setBackgroundColor(0xffCAE1FF);
		}
		else if(mIndex==3)
		{
			mView.setBackgroundColor(0xffE3E3E3);
			//科技要闻
			String path3="http://rss.sina.com.cn/tech/rollnews.xml";
			initList(path3);
			
		}
		else if(mIndex==4)
		{
			mView.setBackgroundColor(0xffE6E6FA);
			//军事要闻
			String path4="http://rss.sina.com.cn/roll/mil/hot_roll.xml";
			initList(path4);
			
		}
		else if(mIndex==5)
		{
			mView.setBackgroundColor(0xffF0F8FF);
			//游戏天空
			String path5="http://rss.sina.com.cn/games/wlyx.xml";
			initList(path5);
			
		}
		else {
			mView.setBackgroundColor(0xffC8E2B1);
		}
		return mView;
	}

	/* 然后， 我们来给mList添加一些要显示的数据 */
	private void initList(String path) {
		
		li=getRss(path);
		//  mText.setText(title);
		 mListView.setAdapter(new MyAdapter(mActivity, li));
	}
	
	  /* 解析XML的方法 */
	  private List<News> getRss(String path)
	  {
	    List<News> data=new ArrayList<News>();
	    URL url = null;
	    try
	    {  
	      url = new URL(path);
	      /* 产生SAXParser对象 */ 
	      SAXParserFactory spf = SAXParserFactory.newInstance();
	      SAXParser sp = spf.newSAXParser(); 
	      /* 产生XMLReader对象 */ 
	      XMLReader xr = sp.getXMLReader(); 
	      /* 设置自定义的MyHandler给XMLReader */ 
	      MyHandler myExampleHandler = new MyHandler(); 
	      xr.setContentHandler(myExampleHandler);
	      /* 解析XML */
	      xr.parse(new InputSource(url.openStream()));
	      /* 取得RSS标题与内容列表 */
	      data =myExampleHandler.getParsedData(); 
	      title=myExampleHandler.getRssTitle();
	    }
	    catch (Exception e)
	    {
	      /* 发生错误时返回result回上一个activity */
	      Intent intent=new Intent();
	      Bundle bundle = new Bundle();
	      bundle.putString("error",""+e);
	      intent.putExtras(bundle);
	      /* 错误的返回值设置为99 */
	    }
	    return data;
	  }
	  
	@Override
	public void onItemClick(AdapterView<?> l, View view, int position, long id) {
		// TODO Auto-generated method stub
		 Log.e("  我被点击了哈哈哈", "点击了");
		 /* 取得News对象 */
	    News ns=(News)li.get(position);
	    /* new一个Intent对象，并指定class */
	    Intent intent = new Intent();
	    intent.setClass(mActivity,NewsContent.class);
	    /* new一个Bundle对象，并将要传递的数据传入 */
	    Bundle bundle = new Bundle();
	    bundle.putString("title",ns.getTitle());
	    bundle.putString("desc",ns.getDesc());
	    bundle.putString("link",ns.getLink());
	    /* 将Bundle对象assign给Intent */
	    intent.putExtras(bundle);
	    /* 调用Activity EX08_13_2 */
	    startActivity(intent);
	    Log.e("  我是新闻内容", "我被启动了");
	}
}
