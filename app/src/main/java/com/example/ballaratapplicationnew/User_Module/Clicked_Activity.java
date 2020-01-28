package com.example.ballaratapplicationnew.User_Module;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ballaratapplicationnew.R;

public class Clicked_Activity extends AppCompatActivity {

    Button nj;
    boolean connected = true;
    SharedPreferences sharedPreferences;
   boolean result_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("connected_value", connected))
        {
            Toast.makeText(this, "Do Nothing", Toast.LENGTH_SHORT).show();
        }
else
        {
            Toast.makeText(this, "You are connected", Toast.LENGTH_SHORT).show();
        }


        nj = findViewById(R.id.nj);

        nj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPreferences.getBoolean("connected_value", connected))
                {
                    connected=false;
                    Log.d("Connected_Value" , ""+connected);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("connected_value", connected);
                    editor.apply();
                }

                else
                {

                    connected=true;
                    Log.d("Connected_Value" , ""+connected);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("connected_value", connected);
                    editor.apply();
                }
            }
        });
    }



//    HomeFragment.fragment_home.setImageResource(R.drawable.mybutton);
//                ((Activity) mContext).finish();
}
