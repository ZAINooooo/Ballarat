package com.example.ballaratapplicationnew.User_Module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebViewClient1 extends WebViewClient {

    private Activity activity = null;

    public WebViewClient1(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if(url.contains("ballaratgenealogy.org.au/research/ballarat-hospitals")) return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }



}