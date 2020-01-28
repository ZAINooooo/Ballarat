package com.example.ballaratapplicationnew.Admin_Module;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;
import com.example.ballaratapplicationnew.User_Module.MyDividerItemDecoration;
import com.example.ballaratapplicationnew.User_Module.Utilss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class QueriesFragment extends Fragment {

//





    RecyclerView parts_recycler_view;
    ArrayList<Queries_List_Pojo> mFlowerList;
    String access_token;
    SharedPreferences sharedPreferences;
    Queries_Adapter blogs_Adapter;
    SweetAlertDialog pDialog;

    JSONObject jObject ;

    public QueriesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QueriesFragment newInstance(String param1, String param2) {
        QueriesFragment fragment = new QueriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_admin_queries, container, false);

        mFlowerList= new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");

//        parts_recycler_view = view.findViewById(R.id.parts_recycler_view);
//        blogs_Adapter = new Queries_Adapter(getActivity(), mFlowerList);
//        parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        parts_recycler_view.setLayoutManager(mLayoutManager);
//        parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
//        parts_recycler_view.setAdapter(blogs_Adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        blogs_Adapter = new Queries_Adapter(getActivity(), mFlowerList);
        parts_recycler_view = view.findViewById(R.id.parts_recycler_view);
        parts_recycler_view.setLayoutManager(mLayoutManager);
        parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
        parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
        parts_recycler_view.setAdapter(blogs_Adapter);
////


        getallqueries();

        return view;
    }



    private void getallqueries()
    {
        mFlowerList.clear();
        pDialog = Utilss.showSweetLoader(getActivity(), SweetAlertDialog.PROGRESS_TYPE, "Fetching Blogs...");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://autohapa.oneviewcrm.com/ballarat/api/payments ", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ResponseIs111" , response);

                try {

                    JSONArray jsonObject = new JSONArray(response);
                    if (jsonObject.length()==0)
                    {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });
                        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setConfirmButton("OK" , new SweetAlertDialog.OnSweetClickListener() {
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





                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject c = jsonObject.getJSONObject(i);
                            try {
                                Queries_List_Pojo dataObject = new Queries_List_Pojo();
                                dataObject.setId(c.getInt("id"));
                                dataObject.setName(c.getString("name"));
                                dataObject.setPkg_name(c.getString("pkg_name"));
                                dataObject.setPayment_status(c.getString("payment_status"));
                                mFlowerList.add(dataObject);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });

                    }

                    blogs_Adapter = new Queries_Adapter(getActivity(), mFlowerList);
                    parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    parts_recycler_view.setLayoutManager(mLayoutManager);
                    parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
                    parts_recycler_view.setAdapter(blogs_Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("ErrorIs" , error.toString());

                                getActivity().runOnUiThread(new Runnable() {
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
        Singleton.getInstance(getActivity()).getRequestQueue().add(stringRequest);
    }












}
