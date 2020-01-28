package com.example.ballaratapplicationnew.User_Module;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ballaratapplicationnew.R;
import com.github.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.facebook.FacebookSdk.getCacheDir;


public class Home_Fragment extends Fragment {

Button what_does_nds_provide,ndis_database,searchButton3;

    private static final String FILENAME = "NDIS.pdf";
    private WebView webView;
    ProgressDialog progress;
    ImageView imageView2;


    public Home_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home_Fragment newInstance(String param1, String param2) {
        Home_Fragment fragment = new Home_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragmentndis_database

        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        what_does_nds_provide= view.findViewById(R.id.what_does_nds_provide);
        ndis_database= view.findViewById(R.id.ndis_database);
        searchButton3= view.findViewById(R.id.searchButton3);
        imageView2= view.findViewById(R.id.imageView2);


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.roaronline.com.au/login/index.php"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        ndis_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Functionality Will Be Available Soon..!!", Toast.LENGTH_SHORT).show();
            }
        });

        webView = view.findViewById(R.id.webview);




        what_does_nds_provide.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         OpenfileFromAsset();
                                                     }
                                                 });

        searchButton3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ballaratgenealogy.org.au/research/ballarat-hospitals"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        return view;
    }

    private void OpenfileFromAsset() {
            File file = new File(getCacheDir(), FILENAME);
            if (!file.exists()) {

                try {
                    InputStream asset = Objects.requireNonNull(getActivity()).getAssets().open(FILENAME);
                    FileOutputStream output = null;
                    output = new FileOutputStream(file);
                    final byte[] buffer = new byte[1024];
                    int size;
                    while ((size = asset.read(buffer)) != -1) {
                        output.write(buffer, 0, size);
                    }
                    asset.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            OpenPdfActivity(file.getAbsolutePath());
        }

    private void OpenPdfActivity(String absolutePath) {

        PDFView.with(getActivity())
                .fromfilepath(absolutePath)
                .swipeHorizontal(false)
                .start();
    }


}

