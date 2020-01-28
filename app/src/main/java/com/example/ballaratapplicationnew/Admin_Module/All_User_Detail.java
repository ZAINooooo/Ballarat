package com.example.ballaratapplicationnew.Admin_Module;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ballaratapplicationnew.BaseActivity;
import com.example.ballaratapplicationnew.PayPal.ConfirmationActivity;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;
import com.example.ballaratapplicationnew.User_Module.ActivityThanku;
import com.example.ballaratapplicationnew.User_Module.Utilss;
import com.facebook.all.All;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class All_User_Detail extends BaseActivity {


    int my_user_id;
    String access_token;
    SharedPreferences sharedPreferences;
    TextView m2,m4,m44,m444,m4444;
    Button sign_up;
String statusCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__user__detail);

        m2 = findViewById(R.id.m2);
        m4 = findViewById(R.id.m4);
        m44 = findViewById(R.id.m44);
        m444 = findViewById(R.id.m444);
        m4444 = findViewById(R.id.m4444);
        sign_up = findViewById(R.id.sign_up);


        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");

        my_user_id= getIntent().getIntExtra("user_id" , 0);
        Log.d("Querry_Id" , ""+my_user_id);

        getuserdetails();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog = Utilss.showSweetLoader(All_User_Detail.this, SweetAlertDialog.PROGRESS_TYPE, "Deleting User...");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://autohapa.oneviewcrm.com/ballarat/api/delete/"+my_user_id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ResponseIs111" , response);


                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            SweetAlertDialog pDialog = new SweetAlertDialog(All_User_Detail.this, SweetAlertDialog.SUCCESS_TYPE).setConfirmButton("OK" , new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    startActivity(new Intent(All_User_Detail.this , Admin_Home_Activity.class));
                                    finish();
                                }
                            });

                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText(success);
                            pDialog.setCancelable(true);
                            pDialog.show();


                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(final VolleyError error) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("ErrorIs" , error.toString());

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });


                                    }
                                });
                            }
                        }) {
                    @Override
                    public Map getHeaders() {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", access_token);
                        return headers;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Singleton.getInstance(All_User_Detail.this).getRequestQueue().add(stringRequest);
            }
        });
    }




    private void getuserdetails() {

        pDialog = Utilss.showSweetLoader(All_User_Detail.this, SweetAlertDialog.PROGRESS_TYPE, "Fetching User Details...");
        final String url = "http://autohapa.oneviewcrm.com/ballarat/api/showUser/+"+my_user_id;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response_All33" , ""+ response);

                        if (statusCode.equals("200"))
                        {
                            try {

                                JSONObject jsonObject = new JSONObject(response.toString());
                                m2.setText(jsonObject.getString("name"));
                                m4.setText(jsonObject.getString("email"));
                                m44.setText(jsonObject.getString("phone_no"));
                                m444.setText(jsonObject.getString("address"));

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

                        else if (statusCode.equals("401"))
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String Error_msg = jsonObject.getString("Error");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Utilss.hideSweetLoader(pDialog);
                                    }
                                });

                                Toast.makeText(All_User_Detail.this, "Error_Msg" +Error_msg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }



                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("ErrorIs", error.toString());

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Utilss.hideSweetLoader(pDialog);
                                    }
                                });
                            }
                        });
                    }

                })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
            {
                statusCode = String.valueOf(response.statusCode);
                Log.d("StatusCode3", statusCode);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization", access_token);
                return headers;
            }
        };


        Singleton.getInstance(All_User_Detail.this).getRequestQueue().add(getRequest);




    }
}
