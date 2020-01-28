package com.example.ballaratapplicationnew.Admin_Module;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ballaratapplicationnew.BaseActivity;
import  com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;
import com.example.ballaratapplicationnew.User_Module.MyDividerItemDecoration;
import com.example.ballaratapplicationnew.User_Module.Utilss;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailsActivity extends BaseActivity {

    int my_query_id;
    String access_token;
SharedPreferences sharedPreferences;

TextView m2,m4,m44,m444,m4444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        m2 = findViewById(R.id.m2);
        m4 = findViewById(R.id.m4);
        m44 = findViewById(R.id.m44);
        m444 = findViewById(R.id.m444);
        m4444 = findViewById(R.id.m4444);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");

         my_query_id= getIntent().getIntExtra("query_id" , 0);
        Log.d("Querry_Id" , ""+my_query_id);


        getalldetails();
    }

    private void getalldetails()
    {
        pDialog = Utilss.showSweetLoader(DetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Fetching Details...");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://autohapa.oneviewcrm.com/ballarat/api/payments/"+my_query_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ResponseIs11122" , response);

                try {

                    JSONArray jsonObject = new JSONArray(response);
                    for (int i = 0; i < jsonObject.length(); i++) {

                        JSONObject c = jsonObject.getJSONObject(i);
                        try {

                            String name = c.getString("name");
                            String Email = c.getString("email");
                            String Contact_Number = c.getString("phone_no");
                            String Address = c.getString("address");
                            String Paymint = c.getString("payment_status");


                            m2.setText(name);
                            m4.setText(Email);
                            m44.setText(Contact_Number);
                            m444.setText(Address);
                            m4444.setText(Paymint);

//                            TextView m2,m4,m44,m444,m4444;
//
                            Log.d("MyNames" , name+Email+Contact_Number+Address+Paymint);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    if (jsonObject.length()==0)
                    {

                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });
                        SweetAlertDialog pDialog = new SweetAlertDialog(DetailsActivity.this, SweetAlertDialog.ERROR_TYPE).setConfirmButton("OK" , new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                            }
                        });

                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("No Details Found");
                        pDialog.setCancelable(true);
                        pDialog.show();
                    }

                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });

                    }

                } catch (JSONException e) {
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
        Singleton.getInstance(DetailsActivity.this).getRequestQueue().add(stringRequest);



    }
}
