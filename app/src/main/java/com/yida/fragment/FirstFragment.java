package com.yida.fragment;

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
import com.yida.base.RemoteImageHelper;
import com.yida.ui.TaobaoActivity;
import com.yida.ui.TestFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FirstFragment extends Fragment {

	private Context context;
	private View view;
	
	GridView ggv ;
	gvAdapter gvadapter;
	
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();
	
	List<Map<String, String>> list = new ArrayList<Map<String,  String>>();	
	 
	WindowManager windowManager;
    int sw,sh;
    
    String ccid;
    String defaultCid="19";
    
    public static FirstFragment newInstance(String s) {
    	FirstFragment newFragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cid", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		Bundle args = getArguments();
		ccid = args != null ? args.getString("cid") : defaultCid;
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_fashion, container,false);
		
		windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		sw = windowManager.getDefaultDisplay().getWidth();
		//屏幕高度
		sh = windowManager.getDefaultDisplay().getHeight();
		
		ggv = (GridView) view.findViewById(R.id.ggv);
		
		String[] from = { "img", "sale", "discount", "price", "title", "url" };
		
		int[] to = { R.id.ggv_img, R.id.ggv_sale, R.id.ggv_discount,
				R.id.ggv_price, R.id.ggv_title, R.id.ggv_url };		
		
		int cccid = Integer.parseInt(ccid);
		gvadapter = new gvAdapter(context,getGoods(cccid),R.layout.item_goods, from, to);
		
		ggv.setAdapter(gvadapter);
		ggvItemClick();
		return view;
	}
	
	private void ggvItemClick() {
		ggv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GridView gv = (GridView) parent;
				HashMap<String, Object> map = (HashMap<String, Object>) gv.getItemAtPosition(position);
				String url = map.get("url").toString();
				//link(url);
				Intent it = new Intent();
				Intent intent = new Intent(getActivity(),TaobaoActivity.class);
				intent.putExtra("url", url);	
				startActivity(intent);	
			}

		});
	}
	
	private class gvAdapter extends SimpleAdapter{

		public gvAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			 View view = super.getView(position, convertView, parent);
			 Map<String,Object> record = (Map<String, Object>) getItem(position);
			 TextView price = (TextView)view.findViewById(R.id.ggv_price);
			 price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
			 ImageView imageView = (ImageView) view.findViewById(R.id.ggv_img);			 
			 lazyImageHelper.loadImage(imageView, record.get("img").toString());
		     LayoutParams params = imageView.getLayoutParams();
		     int imgWidth = (int) Math.ceil(sw*0.45);
		     int imgHeight =  imgWidth;
		     params.width = imgWidth;
		     params.height=imgHeight;  		     
		     imageView.setLayoutParams(params);  
			 return view;
		 }		
	}

    private ArrayList<Map<String,String>> getGoods(int cateId){
    	
        ArrayList<Map<String,String>> ls = new ArrayList<Map<String,String>>();
        //读取的网址接口
        
    	String url= "http://www.ankobeauty.com/anko/index.php?a=index&m=Index&g=API";
		try {
			String jsonstring = GetNetData.getResultForHttpGet(url);
			JSONObject result = new JSONObject(jsonstring);
		    JSONArray goodsList = result.getJSONArray("goodslist");	
		    int length = goodsList.length();	    
		    for(int i=0;i<length;i++){
		    	JSONObject oj = goodsList.getJSONObject(i); 
		    	String cidStr =  oj.getString("cid");//取出属性 
		    	String[] arr = cidStr.split(",");
		    	//对比属性
		    	for(int j=0;j < arr.length;j++){	    		
		    		if( cateId == Integer.parseInt(arr[j])){	    			
		    			Map<String,String> map = new HashMap<String,String>();
						map.put("title", oj.getString("title").toString());
						map.put("price", "￥" + oj.getString("price").toString());
						map.put("sale", "￥" + oj.getString("sale").toString());
						map.put("img","http://www.ankobeauty.com/anko" + oj.getString("img")+"_500x1000.jpg");
						map.put("discount", oj.getString("discount") + "折");
						map.put("url", oj.getString("url").toString());
		    			ls.add(map);		    			
		    		}
		    	}	
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
		return ls;    	
    }

}
