package com.example.ballaratapplicationnew.User_Module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ballaratapplicationnew.R;
import com.facebook.login.Login;

public class Forgot_Password extends AppCompatActivity {

    TextView back_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        back_login = findViewById(R.id.back_login);

        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Forgot_Password.this, LoginActivity.class));
finish();
            }
        });
    }
}
