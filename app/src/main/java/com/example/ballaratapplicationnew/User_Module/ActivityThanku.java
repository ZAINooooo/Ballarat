package com.example.ballaratapplicationnew.User_Module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ballaratapplicationnew.PayPal.ConfirmationActivity;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.User_Module.HomeActivity;

public class ActivityThanku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final Intent mainIntent = new Intent(ActivityThanku.this, HomeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 5000);


    }
}
