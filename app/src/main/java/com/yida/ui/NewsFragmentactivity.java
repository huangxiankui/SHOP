package com.yida.ui;

import java.util.ArrayList;
import java.util.List;

import com.yida.R;
import com.yida.pageslideexample.indicator.PageIndicator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class NewsFragmentactivity extends FragmentActivity implements OnClickListener{
	ViewPager mViewPager;
	TabPageAdapter mTabsAdapter;
	PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
		              WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏
		setContentView(R.layout.activity_main);
		initControl();
		initViewPager();
	}

	/**
	 * 预加载控制
	 */
	private void initControl() {
		mViewPager = (ViewPager) findViewById(R.id.pvr_user_pager);
		mViewPager.setOffscreenPageLimit(2);/* 预加载页面 */
		mIndicator = (PageIndicator) findViewById(R.id.pvr_user_indicator);
		mIndicator
				.setOnPageChangeListener(new IndicatorOnPageChangedListener());
	}

	/* 初始化ViewPager */
	private void initViewPager() {
		mTabsAdapter = new TabPageAdapter(this);
		mViewPager.setAdapter(mTabsAdapter);
		mIndicator.setViewPager(mViewPager);
		new ContentAsyncTask().execute();
	}

	/* 页卡框架 */
	public class TabPageAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments;
		public List<String> tabs = new ArrayList<String>();

		public TabPageAdapter(NewsFragmentactivity activity) {
			super(activity.getSupportFragmentManager());
			mFragments = new ArrayList<Fragment>();
		}

		public void addTab(String title, Fragment fragment) {
			mFragments.add(fragment);
			tabs.add(title);
			notifyDataSetChanged();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabs.get(position);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}
	}

	public class ContentAsyncTask extends AsyncTask<Integer, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		/* 
		 * tab标签设置
		 * 
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//新闻要闻
			mTabsAdapter.addTab(getString(R.string.pvrCompleted),
					new MyFragment(NewsFragmentactivity.this, 0));
			//国内要闻
			mTabsAdapter.addTab(getString(R.string.pvrUnfinished),
					new MyFragment(NewsFragmentactivity.this, 1));
			//社会与法
			mTabsAdapter.addTab(getString(R.string.pvrFailure), new MyFragment(
					NewsFragmentactivity.this, 2));
			//科普要闻
			mTabsAdapter.addTab(getString(R.string.kepuyaowen), new MyFragment(
					NewsFragmentactivity.this, 3));
			//军事要闻
			mTabsAdapter.addTab(getString(R.string.junshi), new MyFragment(
					NewsFragmentactivity.this, 4));
			//游戏天空
			mTabsAdapter.addTab(getString(R.string.youxi), new MyFragment(
					NewsFragmentactivity.this, 5));
			mTabsAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(1);
		}

		@Override
		protected String doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/* 指示器切换监听 */
	private class IndicatorOnPageChangedListener implements
			OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
		}
	}

	/* 页卡切换监听 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}


}
