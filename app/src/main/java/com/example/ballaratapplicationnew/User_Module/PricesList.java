package com.example.ballaratapplicationnew.User_Module;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ballaratapplicationnew.BaseActivity;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PricesList extends BaseActivity {

    RecyclerView parts_recycler_view;
    ArrayList<PriceList_Pojo> mFlowerList;
String access_token;
    SharedPreferences sharedPreferences;
    ItemArrayAdapter itemArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices_list);


        mFlowerList= new ArrayList<>();


        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");


        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        parts_recycler_view = findViewById(R.id.parts_recycler_view);

         itemArrayAdapter = new ItemArrayAdapter(PricesList.this, mFlowerList);
        parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(PricesList.this, DividerItemDecoration.VERTICAL, 10));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        parts_recycler_view.setLayoutManager(mLayoutManager);
        parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
        parts_recycler_view.setAdapter(itemArrayAdapter);


        
        getallpackages();

    }

    private void getallpackages()
    {
        mFlowerList.clear();
        pDialog = Utilss.showSweetLoader(PricesList.this, SweetAlertDialog.PROGRESS_TYPE, "Fetching Data...");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://autohapa.oneviewcrm.com/ballarat/api/package/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ResponseIs" , response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray obj = jsonObject.getJSONArray("result");



                    if (obj.length()==0)
                    {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });
                        SweetAlertDialog pDialog = new SweetAlertDialog(PricesList.this, SweetAlertDialog.ERROR_TYPE).setConfirmButton("OK" , new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                            }
                        });

                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("No Data Found");
                        pDialog.setCancelable(true);
                        pDialog.show();
                    }

                    else
                    {

                        for (int i = 0; i < obj.length(); i++) {


//                            mFlowerList.clear();
                            JSONObject jsonObject3 = new JSONObject(String.valueOf(obj.get(i)));
                            PriceList_Pojo dataObject = new PriceList_Pojo();

                            dataObject.setId(jsonObject3.getString("id"));
                            dataObject.setPkg_name(jsonObject3.getString("pkg_name"));
                            dataObject.setPkg_price(jsonObject3.getString("pkg_price"));
                            dataObject.setPkg_image(jsonObject3.getString("pkg_image"));

                            mFlowerList.add(dataObject);
                            itemArrayAdapter.notifyDataSetChanged();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });

                    }

                    itemArrayAdapter = new ItemArrayAdapter(PricesList.this, mFlowerList);
                    parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(PricesList.this, DividerItemDecoration.VERTICAL, 10));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    parts_recycler_view.setLayoutManager(mLayoutManager);
                    parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
                    parts_recycler_view.setAdapter(itemArrayAdapter);

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
        Singleton.getInstance(PricesList.this).getRequestQueue().add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
