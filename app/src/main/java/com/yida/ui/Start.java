package com.yida.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.yida.R;
import com.yida.parallaxpager.ParallaxContainer;

/**
 * @author huangxiankui 2015-6-29
 */

public class Start extends Activity {

    ImageView iv_man;
    ImageView rl_weibo;
    ParallaxContainer parallaxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final  int DELAY_TIME=3000;
        super.onCreate(savedInstanceState);
        //不显示程序的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

        /**
         * 动画支持11以上sdk,11以下默认不显示动画
         * 若需要支持11以下动画,也可导入https://github.com/JakeWharton/NineOldAndroids
         */
        if (android.os.Build.VERSION.SDK_INT > 10) {
            iv_man = (ImageView) findViewById(R.id.iv_man);
            parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

            if (parallaxContainer != null) {
                parallaxContainer.setImage(iv_man);
                parallaxContainer.setLooping(false);

                iv_man.setVisibility(View.VISIBLE);
                parallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1, R.layout.view_intro_2,
                        R.layout.view_intro_3, R.layout.view_intro_4,
                        R.layout.view_intro_5, R.layout.view_login);

//                new Thread(new Runnable()
//                {
//
//                    @Override
//                    public void run()
//                    {
//                        // TODO Auto-generated method stub
//                        try
//                        {
//                            Thread.sleep(DELAY_TIME);
//
//                        } catch (InterruptedException e)
//                        {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        Intent intent =new Intent();
//                        intent.setClass(Start.this, HomeActivity.class);
//                        startActivity(intent);
//                        Start.this.finish();
//                    }
//                }).start();

            }
        }
        else{
            //版本小于11  开启loadActivity启动登录
            Intent intent =new Intent();
            intent.setClass(Start.this, LoadActivity.class);
            startActivity(intent);
            Start.this.finish();

        }


        rl_weibo = (ImageView) findViewById(R.id.rl_weibo);
        rl_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    Uri uri = Uri.parse("market://details?id=com.xingin.xhs");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    Intent intent =new Intent();
                    intent.setClass(Start.this, HomeActivity.class);
                    startActivity(intent);
                    Start.this.finish();
                } catch (ActivityNotFoundException e) {
                }
            }
        });
    }
}
