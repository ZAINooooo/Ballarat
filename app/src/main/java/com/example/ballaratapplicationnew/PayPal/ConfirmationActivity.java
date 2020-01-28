package com.example.ballaratapplicationnew.PayPal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ballaratapplicationnew.BaseActivity;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;
import com.example.ballaratapplicationnew.User_Module.ActivityThanku;
import com.example.ballaratapplicationnew.Utilss;

import org.json.JSONException;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ConfirmationActivity extends BaseActivity {
    String statusCode;
    SharedPreferences sharedPreferences;
    String user_id, pkg_id, access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");


        user_id = getIntent().getStringExtra("user_id");
        pkg_id = getIntent().getStringExtra("package_id");

        Log.d("Valuessss", user_id + pkg_id);

        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
//        TextView textViewId = (TextView) findViewById(R.id.paymentId);
//        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
//        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        String json_id = jsonDetails.getString("id");
        String json_state = jsonDetails.getString("state");

        String payment = paymentAmount + " USD";
        apicall(json_id, json_state);

        //Showing the details from json object
//        textViewId.setText(jsonDetails.getString("id"));
//        textViewStatus.setText(jsonDetails.getString("state"));
//        textViewAmount.setText(paymentAmount+" USD");
    }

    private void apicall(String payment_id, String payment_state) {
        pDialog = Utilss.showSweetLoader(ConfirmationActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Payment In Progress...");
        final String url = " http://autohapa.oneviewcrm.com/ballarat/api/payments?user_id=" + user_id + "&pkg_id=" + pkg_id + "&payment_status=" + payment_state + "&transaction_id=" + payment_id;


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response_All33" , ""+ response);

                        if (statusCode.equals("200"))
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());

                                String names = jsonObject.getString("status");
//                                Toast.makeText(context, ""+names, Toast.LENGTH_SHORT).show();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Utilss.hideSweetLoader(pDialog);
                                    }
                                });

                                final Intent mainIntent = new Intent(ConfirmationActivity.this, ActivityThanku.class);
                                startActivity(mainIntent);




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

                                Toast.makeText(ConfirmationActivity.this, "Error_Msg" +Error_msg, Toast.LENGTH_SHORT).show();
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


        Singleton.getInstance(ConfirmationActivity.this).getRequestQueue().add(getRequest);


    }
}