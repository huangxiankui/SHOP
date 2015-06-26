package com.yida.ui;

import org.apache.http.HttpHost;
import org.json.JSONObject;

import com.aibang.open.client.AibangApi;
import com.aibang.open.client.exception.AibangIOException;
import com.aibang.open.client.exception.AibangServerException;
import com.yida.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Gongjiao extends Activity implements OnClickListener {
	  private AibangApi mAibang;
	    private AibangAsyncTask mAsyncTask;
		//edittext 中对应的各个editext,获取edittext中的string数值,并将其放置到AibangAsyncTask对应的查询地址上
	    public    String shi1="";
	    public  String shi2="";
	    public  String shi3="";
	   
	    // 申请的API KEY   dac3229a3308362a5e6264af9469d687   私钥 788fe0708e9326dc
	  private static final String API_KEY = "dac3229a3308362a5e6264af9469d687";
	  // private static final String API_KEY =" f41c8afccc586de03a99c86097e98ccb";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gongjiao);
		mAibang = new AibangApi(API_KEY);
		
		//起始地址
	 //	EditText shistart=(EditText)findViewById(R.id.et1);
		//edittext 中对应的各个editext,获取edittext中的string数值,并将其放置到AibangAsyncTask对应的查询地址上
	//shi1=shistart.getText().toString();
		//EditText qishi=(EditText)findViewById(R.id.qishidizhi);
	// shi2=qishi.getText().toString();
		//终点地址
	//	EditText shiend=(EditText)findViewById(R.id.mudidi);
	//	 shi3=shiend.getText().toString();
		
		Button bus=(Button)findViewById(R.id.bus);
				bus.setOnClickListener(this);
		
	}
	public void onClick(View v)
	{
		if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
        }
		 switch (v.getId()) {
	    
	        
	        case R.id.bus:
	            mAsyncTask = new AibangAsyncTask("bus");
	            break;
		 }
		 if (mAsyncTask != null) {
	            mAsyncTask.execute();
	        }
		
	}
    private class AibangAsyncTask extends AsyncTask<Void, Void, JSONObject> {
        public AibangAsyncTask(String action) {
            mAction = action;
        }

        protected void onPreExecute() {
            Toast.makeText(Gongjiao.this, "正在请求...", Toast.LENGTH_SHORT)
                    .show();
        }

        protected void onPostExecute(JSONObject result) {
            if (result == null) {
                String err = "位置错误";
                if (mException != null) {
                    if (mException instanceof AibangServerException) {
                        AibangServerException e = (AibangServerException) mException;
                        err = e.getStatusCode() + "\n" + e.getMessage() + "\n "
                                + e.getStackTrace();
                    } else if (mException instanceof AibangIOException) {
                        err = "网络错误\n" + mException.getStackTrace();
                    } else {
                        err = "" + mException.getStackTrace();
                    }
                }
                TextView tv = (TextView) findViewById(R.id.result);
                tv.setText(err);
                Log. e("-----------2", shi1.toString());
                Toast.makeText(Gongjiao.this, "出错了", Toast.LENGTH_SHORT)
                        .show();
            } else {
         TextView tv = (TextView) findViewById(R.id.result);
                tv.setText(result.toString());
            }
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
        	
            String result = null;
            mAibang.setProxy(getProxy());
            try {
//                if ("search".equals(mAction)) {
//                	  
//                	 //result = mAibang.search("江门", "五邑大学", null, null, null,
//                           	// result = mAibang.search("北京", "五道口", null, null, null,
//                	result = mAibang.search(shi1, shi2, null, null, null,
//                	//result = mAibang.search(Gongjiao.shi1, Gongjiao.shi2, null, null, null,
//                            null, null, null, null, null);
//            }
                	//else if ("biz".equals(mAction)) {
//                    result = mAibang.biz("829423550-695247739");
            //    } 
            	EditText shistart=(EditText)findViewById(R.id.et1);
        		//edittext 中对应的各个editext,获取edittext中的string数值,并将其放置到AibangAsyncTask对应的查询地址上
        	shi1=shistart.getText().toString();
        	EditText qishi=(EditText)findViewById(R.id.qishidizhi);
       	 shi2=qishi.getText().toString();
       		//终点地址
       		EditText shiend=(EditText)findViewById(R.id.mudidi);
       		 shi3=shiend.getText().toString();
             if ("bus".equals(mAction)) {
                	
               // result = mAibang.bus("江门", "五邑大学", "蓬江区政府", null, null, null,
                //   result = mAibang.bus("北京", "五道口", "中关村", null, null, null,
            	 result = mAibang.bus(shi1, shi2,shi3, null, null, null,
            	
                            null, null, null, null);
               } 
                    //else if ("post_comment".equals(mAction)) {
//                   result = mAibang.postComment("username", "829423550-695247739", 5,
//                //	result = mAibang.postComment("huangxiankui", "788fe0708e9326dc", 5,
//                	null, "哇，好白痴的程序呀～～～");
//                }
            } catch (Exception e) {
                mException = e;
            }
            try {
                return new JSONObject(result);
            } catch (Exception e) {
                mException = e;
            }
            return null;
        }

        private String mAction;
        private Exception mException;
    }
    
    
    //    
    private HttpHost getProxy() {
    ConnectivityManager cm = (ConnectivityManager)getSystemService(
            Context.CONNECTIVITY_SERVICE);
    HttpHost none_host = null;
    if (cm == null) {
        return none_host;
    }

    NetworkInfo ni = cm.getActiveNetworkInfo();
    if (ni == null) {
        return none_host;
    }

    if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
        return null;
    } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
        String extra = ni.getExtraInfo();
        if (TextUtils.isEmpty(extra)) {
            return none_host;
        }

        extra = extra.toLowerCase();
        if (extra.contains("cmnet") || extra.contains("ctnet")
                || extra.contains("uninet") || extra.contains("3gnet")) {
            return none_host;
        } else if (extra.contains("cmwap") || extra.contains("uniwap")
                || extra.contains("3gwap")) {
            return new HttpHost("10.0.0.172");
        } else if (extra.contains("ctwap")) {
            return new HttpHost("10.0.0.200");
        }
    }

    return none_host;
}
    
}
