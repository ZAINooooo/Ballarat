package com.example.ballaratapplicationnew.User_Module;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ballaratapplicationnew.Admin_Module.Admin_Home_Activity;
import com.example.ballaratapplicationnew.BaseActivity;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;
import com.facebook.login.Login;
import com.google.android.gms.dynamic.IFragmentWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    TextView multicolorLine,forgot_password;
    Button login_button;
    EditText email_login, phone_login;
    SharedPreferences sharedPreferences;
    String statusCode, id_user;

     String email;
     String phone ;
     String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        email_login = findViewById(R.id.email_login);
        phone_login = findViewById(R.id.phone_login);

        login_button = findViewById(R.id.login_button);
        multicolorLine = findViewById(R.id.multicolorline);
        forgot_password = findViewById(R.id.forgot_password);
        String text = "<font color='#0f75bc'>Don't have an account?</font> " + "<font color='#ed1c24'>SignUp</font>";
        multicolorLine.setText(Html.fromHtml(text));

        multicolorLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, Forgot_Password.class));

            }
        });





        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = Utilss.showSweetLoader(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Loging Up...");

                email = email_login.getText().toString();
                phone = phone_login.getText().toString();

                if (email.equals("") && phone.equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

                    SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Fields Are Required..!!");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return;
                } else if (email.equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Utilss.hideSweetLoader(pDialog);
                                }
                            });

                            SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Email Is Required..!!");
                            pDialog.setCancelable(true);
                            pDialog.show();
                            return;
                        }
                    });

                } else if (phone.equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Utilss.hideSweetLoader(pDialog);
                                }
                            });

                            SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Password Is Required..!!");
                            pDialog.setCancelable(true);
                            pDialog.show();
                            return;

                        }
                    });
                }


                else
                {
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    String url = "http://autohapa.oneviewcrm.com/ballarat/api/login/?email=" + email + "&password=" + phone;

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
//                                    Log.d("Login1111",response);
//                                    Toast.makeText(LoginActivity.this, "Credentials Are Right", Toast.LENGTH_SHORT).show();

                                    Log.d("Json-response1", response);
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject jsonObject3 = jsonObject.getJSONObject("user");
                                        id_user = jsonObject3.getString("id");
                                        String token_value = jsonObject.getString("token");
                                        String names = jsonObject3.getString("name");
                                        String role = jsonObject3.getString("role");
                                        Log.d("Role_Mine" , role);


                                        if (role.equals("admin"))
                                        {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("email", email);
                                            editor.putString("name", names);
                                            editor.putString("phone", phone);
                                            editor.putString("token", token_value);
                                            editor.putString("user_id", id_user);
                                            editor.putString("role", role);
                                            editor.apply();
                                            startActivity(new Intent(LoginActivity.this , Admin_Home_Activity.class ));
                                            finish();



                                        }

                                        else if (role.equals("user"))
                                        {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("email", email);
                                            editor.putString("name", names);
                                            editor.putString("phone", phone);
                                            editor.putString("token", token_value);
                                            editor.putString("user_id", id_user);
                                            editor.putString("role", role);
                                            editor.apply();

                                            startActivity(new Intent(LoginActivity.this ,HomeActivity.class ));
                                            finish();



                                        }



                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }
                                }

                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Utilss.hideSweetLoader(pDialog);
                                                }
                                            });

                                            SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialog.setTitleText("Wrong Credentials..!!");
                                            pDialog.setCancelable(true);
                                            pDialog.show();
                                            return;

                                        }
                                    });


                                }
                            }

                    ) {
                        @Override
                        protected Map<String,String>getParams(){

                            Map<String,String> params = new HashMap<String, String>();
                            params.put("email",email);
                            params.put("password",phone);
                            return params;

                        }
                    };
                    queue.add(postRequest);

                }



                }

        });
    }
}