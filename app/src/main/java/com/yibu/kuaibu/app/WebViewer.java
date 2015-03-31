package com.yibu.kuaibu.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.utils.NetWorkUtil;
import com.yibu.kuaibu.utils.StringUtils;

public class WebViewer extends Activity implements View.OnClickListener {

    private WebView webviewer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        /*register onclick*/
        findViewById(R.id.head_title_left).setOnClickListener(this);
        TextView head= (TextView) findViewById(R.id.main_head_title);

        webviewer = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webviewer.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        if (NetWorkUtil.isNetWork(WebViewer.this)) {
            Bundle bundle=getIntent().getExtras();
            String url=bundle.getString(this.getString(R.string.webview_key));
            if(!url.equals("")) {
                /*
                set the head title
                * */
                String headTitles=null;
                if(url.equals(this.getString(R.string.html_about)))
                {
                    headTitles=this.getString(R.string.html_about_title);
                }
                if(url.equals(this.getString(R.string.html_help)))
                {
                    headTitles=this.getString(R.string.html_help_title);
                }
                if(url.equals(this.getString(R.string.html_service)))
                {
                    headTitles=this.getString(R.string.html_service_title);
                }
                head.setText(headTitles);



                 webviewer.loadUrl(url);
            }
        }else{

                Toast.makeText(getApplicationContext(),
                        R.string.network_not_connected, 0).show();
            finish();

        }
        //设置Web视图
        webviewer.setWebViewClient(new HelloWebViewClient ());
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webviewer.canGoBack()) {
            webviewer.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.head_title_left){
            finish();
        }

    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
