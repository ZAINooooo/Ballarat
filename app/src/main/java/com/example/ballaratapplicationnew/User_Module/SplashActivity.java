package com.example.ballaratapplicationnew.User_Module;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;

import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ballaratapplicationnew.R;
import com.splunk.mint.Mint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mint.initAndStartSession(this.getApplication(), "41223dae");

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        String emails = sharedPreferences.getString("email",null);
        String names = sharedPreferences.getString("name",null);
        String phone_num = sharedPreferences.getString("phone",null);
        String user_token = sharedPreferences.getString("token",null);
        String users_id = sharedPreferences.getString("user_id",null);
        String roles = sharedPreferences.getString("role",null);



        if (emails!= null && names!= null && phone_num!= null && user_token!= null && users_id!= null && roles!= null)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
        }

        else
        {
//            Toast.makeText(this, "Already Cleared", Toast.LENGTH_SHORT).show();
            //Do Anything..!!
        }



        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.ballaratapplicationnew", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 5000);
    }
}
