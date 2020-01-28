package com.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.vpn_app.R;

public class DummyActivity extends AppCompatActivity {

    private WebView webView;
    ProgressDialog progress;

    Button clicked_1;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        webView = findViewById(R.id.webview);
        clicked_1 = findViewById(R.id.clicked_1);

        clicked_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progress= new ProgressDialog(DummyActivity.this);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DummyActivity.this);
                LayoutInflater inflater = DummyActivity.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_popup2, null);

                WebView wv = dialogView.findViewById(R.id.webview);

                WebSettings webSettings = wv.getSettings();
                webSettings.setJavaScriptEnabled(true);
                wv.getSettings().setBuiltInZoomControls(true);

                WebViewClient2 webViewClient = new WebViewClient2(DummyActivity.this);
                wv.setWebViewClient(webViewClient);

                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {

                        progress.setMessage("Please wait...");
                        progress.setIndeterminate(false);
                        progress.setCancelable(false);
                        progress.show();
                    }

                    @Override
                    public void onPageFinished(WebView view, final String url) {
                        progress.dismiss();
                    }
                });

                wv.loadUrl("https://www.journaldev.com");

                dialogBuilder.setView(dialogView);
                Dialog markerPopUpDialog = dialogBuilder.create();
                markerPopUpDialog.show();
            }
            
        });


    }
}
