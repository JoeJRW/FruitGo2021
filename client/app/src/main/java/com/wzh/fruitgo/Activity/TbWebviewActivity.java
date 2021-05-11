package com.wzh.fruitgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.wzh.fruitgo.R;

/**
 * @author: wzh
 * @date：2021/5/11 14:40
 */
public class TbWebviewActivity extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_webview);
        getSupportActionBar().hide();

        webView=findViewById(R.id.webview);

        url=getUrl();

        if (url!=null){
            webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //是否可以后退
            webView.canGoBack();
            //设置自适应屏幕，两者合用
            webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
            webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            //缩放操作
            webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
            webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放

            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }


    }

    String getUrl(){
        Intent intent=this.getIntent();
        String url=
                intent.getStringExtra("url");

        if (url!=null)
            return url;
        else
            return null;
    }
}
