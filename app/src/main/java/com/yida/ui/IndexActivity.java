package com.yida.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yida.R;


import com.yida.base.GetNetData;
import com.yida.base.LoadJson;
import com.yida.base.MyFragmentPagerAdapter;
import com.yida.base.MyGallery;
import com.yida.base.RemoteImageHelper;
import com.yida.fragment.FirstFragment;

import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleAdapter;

/**
 * @author huangxiankui
 *  商品信息显示界面
 */
public class IndexActivity extends FragmentActivity {
	
	
	//用于下面点点指示
	private LinearLayout ll_focus_indicator_container = null;
	//gallery的id
    private MyGallery gallery = null;
    //存储图片
    RemoteImageHelper lazyImageHelper = new RemoteImageHelper();
    LoadJson loadjson = new LoadJson();
    List<Map<String, String>> list = new ArrayList<Map<String,  String>>(); // 图片缓存
    public boolean timeFlag = true;
    //屏幕宽度
	WindowManager windowManager;
    int sw,sh;
    
    MyAdapter myAdapter;
    /**
     * 存储上一个选择项的Index
     */
    private int preSelImgIndex = 0;
    
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private RadioButton tvTabWeina, tvTabOulaiya, tvTabKashi, tvTabShihuakou,tvTabMeiqishi;

    private int currIndex = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private int position_four;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		
		//resources = getResources();
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		sw = windowManager.getDefaultDisplay().getWidth();
		//屏幕高度
		sh = windowManager.getDefaultDisplay().getHeight();
     //版本问题，在4.0之后在主线程里面执行Http请求都会报这个错，
        // 也许是怕Http请求时间太长造成程序假死的情况吧
        //在发起Http请求的Activity里面的onCreate函数里面添加如下代码：
        // http://www.cnblogs.com/sjrhero/articles/2606833.html
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode
                .VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        //
        initView();

        ll_focus_indicator_container = (LinearLayout) findViewById(R.id.ll_focus_indicator_container);
        InitFocusIndicatorContainer();
        gallery = (MyGallery) findViewById(R.id.banner_gallery);
        gallery.setAdapter(myAdapter);
        gallery.setFocusable(true);

		//
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int selIndex, long arg3) {
				// 修改上一次选中项的背景
				selIndex = selIndex % list.size();

				ImageView preSelImg = (ImageView) ll_focus_indicator_container
						.findViewById(preSelImgIndex);
				preSelImg.setImageDrawable(IndexActivity.this.getResources()
						.getDrawable(R.drawable.ic_focus));
				// 修改当前选中项的背景
				ImageView curSelImg = (ImageView) ll_focus_indicator_container
						.findViewById(selIndex);
				curSelImg.setImageDrawable(IndexActivity.this.getResources()
						.getDrawable(R.drawable.ic_focus_select));
				preSelImgIndex = selIndex;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		InitWidth();
        InitTextView();
        InitViewPager();		
	}
	
    private void InitWidth() {
    	//获取宽度
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        position_one = (int) (sw / 5.0);
        LayoutParams para;
        para = ivBottomLine.getLayoutParams();     
        para.width=position_one;
        para.height= 2;      
        ivBottomLine.setLayoutParams(para);
        position_two = position_one * 2;
        position_three = position_one * 3;
        position_four = position_one * 4;
    }
    
	private void InitTextView() {
		tvTabWeina = (RadioButton) findViewById(R.id.tv_tab_activity);
		tvTabOulaiya = (RadioButton) findViewById(R.id.tv_tab_groups);
		tvTabKashi = (RadioButton) findViewById(R.id.tv_tab_friends);
		tvTabShihuakou = (RadioButton) findViewById(R.id.tv_tab_chat);		
		tvTabMeiqishi = (RadioButton) findViewById(R.id.tv_tab_mei);
		
		
		tvTabWeina.setOnClickListener(new MyOnClickListener(0));
		tvTabOulaiya.setOnClickListener(new MyOnClickListener(1));
		tvTabKashi.setOnClickListener(new MyOnClickListener(2));
		tvTabShihuakou.setOnClickListener(new MyOnClickListener(3));
		tvTabMeiqishi.setOnClickListener(new MyOnClickListener(4));
	}

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        Fragment weinaFragment = FirstFragment.newInstance("16");
        Fragment oulaiyaFragment = FirstFragment.newInstance("17");
        Fragment kashiFragment=FirstFragment.newInstance("18");
        Fragment shihuakouFragment=FirstFragment.newInstance("19");
        Fragment meiqishiFragment=FirstFragment.newInstance("20");

        fragmentsList.add(weinaFragment);
        fragmentsList.add(oulaiyaFragment);
        fragmentsList.add(kashiFragment);
        fragmentsList.add(shihuakouFragment);
        fragmentsList.add(meiqishiFragment);
        
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
      
    }
    

    
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};
    
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
            case 0:

                if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, 0, 0, 0);                 
                    tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(
									getResources().getDrawable(R.drawable.special_icon), null,null, null);

                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, 0, 0, 0);                 
                    tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.recommend_icon), null,null, null);                                      
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, 0, 0, 0);                  
                    tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.tuan_icon), null,null, null);                 
                }else if (currIndex == 4) {
                    animation = new TranslateAnimation(position_four, 0, 0, 0);                  
                    tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.mei_icon), null,null, null);                 
                }                   
                tvTabWeina.setTextColor(getResources().getColor(R.color.pink));
                tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(
						getResources().getDrawable(R.drawable.fashion_icon_s),null, null, null);			          
                break;
            case 1:
            	
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_one, 0, 0);                 
                    tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                    
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two,position_one, 0, 0);                 
                    tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.recommend_icon), null,null, null);                                                        
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_one, 0, 0);                  
                    tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.tuan_icon), null,null, null);                     
                }else if (currIndex == 4) {
                    animation = new TranslateAnimation(position_four, position_one, 0, 0);                  
                    tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.mei_icon), null,null, null);                 
                }  
                tvTabOulaiya.setTextColor(getResources().getColor(R.color.pink));               
                tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(
						getResources().getDrawable(R.drawable.special_icon_s),null, null, null);                              
                break;
            case 2:           	
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_two, 0, 0);                 
                    tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                    
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_two, 0, 0);                 
                    tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(
									getResources().getDrawable(R.drawable.special_icon), null,null, null);
                   
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_two, 0, 0);                  
                    tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.tuan_icon), null,null, null);   
                    
                }else if (currIndex == 4) {
                    animation = new TranslateAnimation(position_four, position_two, 0, 0);                  
                    tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.mei_icon), null,null, null);                 
                }  
                tvTabKashi.setTextColor(getResources().getColor(R.color.pink));
                tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(
                		getResources().getDrawable(R.drawable.recommend_icon_s),null, null, null);               
                break;
            case 3:
            	
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_three, 0, 0);                 
                    tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                    
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_three, 0, 0);
                    tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                    tvTabOulaiya.setButtonDrawable(R.drawable.special_icon);
                    
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_three, 0, 0);                 
                    tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.recommend_icon), null,null, null);   
                    
                }else if (currIndex == 4) {
                    animation = new TranslateAnimation(position_four, position_three, 0, 0);                  
                    tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.mei_icon), null,null, null);                 
                }  
                tvTabShihuakou.setTextColor(getResources().getColor(R.color.pink));
                tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(
                		getResources().getDrawable(R.drawable.tuan_icon_s),null, null, null);
               
                break;
            case 4:
            	
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_four, 0, 0);                 
                    tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                    
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_four, 0, 0);
                    tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                    tvTabOulaiya.setButtonDrawable(R.drawable.special_icon);
                    
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_four, 0, 0);                 
                    tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.recommend_icon), null,null, null);   
                    
                }else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_four, 0, 0);                  
                    tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(
                    		getResources().getDrawable(R.drawable.tuan_icon), null,null, null);   
                    
                } 
                tvTabMeiqishi.setTextColor(getResources().getColor(R.color.pink));
                tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(
                		getResources().getDrawable(R.drawable.mei_icon_s),null, null, null);
               
                break; 
                
            }
            
            
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }
      
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
	private void initView(){
		
		String[] from = {"img","name","url"};
		int[] to ={R.id.gallery_image,R.id.gallery_text,R.id.gallery_url};
		myAdapter = new MyAdapter(this,getCate(),R.layout.item,from,to);
	}
	
	private void InitFocusIndicatorContainer() {
		
		for (int i = 0; i < list.size(); i++) {
			ImageView localImageView = new ImageView(this);
			localImageView.setId(i);
			ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
			localImageView.setScaleType(localScaleType);
			LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
					15, 15);
			localImageView.setLayoutParams(localLayoutParams);
			localImageView.setPadding(5, 5, 5, 5);
			localImageView.setImageResource(R.drawable.ic_focus);
			localImageView.setBackgroundColor(getResources().getColor(R.color.trans));
			this.ll_focus_indicator_container.addView(localImageView);
		}
	}
	

/*viewpage数据解析*/
    public List<Map<String,String>> getCate(){
    	    	    	
    	String url = "http://www.ankobeauty.com/anko/index.php/Index/Index/aad";
		try {
			String jsonstring = GetNetData.getResultForHttpGet(url);
			//String jsonstring = loadjson.getJson(url);
			JSONObject result = new JSONObject(jsonstring);
			JSONArray cateList = result.getJSONArray("adlist");	
			int length = cateList.length();
			for(int i=0 ;i<length;i++){
				Map<String, String> map = new HashMap<String, String>();
				JSONObject oj = cateList.getJSONObject(i);

                map.put("img", "http://www.ankobeauty.com"+oj.getString("img"));
                map.put("name", oj.getString("name"));
                map.put("url", oj.getString("url"));
                list.add(map);                            
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		

    }
    


   private class MyAdapter extends SimpleAdapter{
	   
		public MyAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view =super.getView(position, convertView, parent);
			Map<String,Object> record = (Map<String, Object>) getItem(position);
			final String checkUrl = record.get("url").toString();
			ImageView imageView = (ImageView) view.findViewById(R.id.gallery_image);
			lazyImageHelper.loadImage(imageView, record.get("img").toString());
			 LayoutParams params = imageView.getLayoutParams();
		     int imgWidth = sw;
		     int imgHeight =  (int) Math.ceil( (imgWidth * 240) / 640);
		     params.width = imgWidth;
		     params.height=imgHeight;  		     
		     imageView.setLayoutParams(params);  
			 imageView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent it = new Intent();
					Intent intent = new Intent(IndexActivity.this,TaobaoActivity.class);
					intent.putExtra("url", checkUrl);	
					startActivity(intent);	
				}
				
			});
            return view;		
		}
		
   }
   
	private void link(String url) {

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.setData(Uri.parse(url));

		startActivity(intent);

   }


}
