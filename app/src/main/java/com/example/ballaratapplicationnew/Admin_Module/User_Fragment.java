package com.example.ballaratapplicationnew.Admin_Module;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.ballaratapplicationnew.User_Module.BlogsAdapter;
import com.example.ballaratapplicationnew.User_Module.MyDividerItemDecoration;
import com.example.ballaratapplicationnew.User_Module.Utilss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;


public class User_Fragment extends Fragment {



    RecyclerView parts_recycler_view;
    ArrayList<All_User_Pojo> mFlowerList;
    ArrayList<All_User_Pojo> mFlowerList2;

    String access_token;
    SharedPreferences sharedPreferences;
    All_Users_Adapter blogs_Adapter;
    SweetAlertDialog pDialog;
    JSONObject jObject ;
SwipeRefreshLayout mSwipeRefreshLayout;
    public User_Fragment() {

    }

    public static User_Setting_Fragment newInstance(String param1, String param2) {
        User_Setting_Fragment fragment = new User_Setting_Fragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.activity_queries_users_, container, false);

        mFlowerList= new ArrayList<>();
        mFlowerList2= new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");
//        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        parts_recycler_view = view.findViewById(R.id.parts_recycler_view);
        blogs_Adapter = new All_Users_Adapter(getActivity(), mFlowerList);
        parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        parts_recycler_view.setLayoutManager(mLayoutManager);
        parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
        parts_recycler_view.setAdapter(blogs_Adapter);

        getallusers();



//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                updateOperation();
//
//            }
//        });


        return view;
    }

//    private void updateOperation()
//    {
//        mFlowerList2.clear();
//
//        pDialog = Utilss.showSweetLoader(getActivity(), SweetAlertDialog.PROGRESS_TYPE, "Fetching Users...");
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://autohapa.oneviewcrm.com/ballarat/api/allUsers/", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.d("ResponseIs111" , response);
//
//                try {
//
//                    JSONArray jsonObject = new JSONArray(response);
//                    if (jsonObject.length()==0)
//                    {
//
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Utilss.hideSweetLoader(pDialog);
//                            }
//                        });
//                        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setConfirmButton("OK" , new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                            }
//                        });
//
//                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                        pDialog.setTitleText("No Data Found");
//                        pDialog.setCancelable(true);
//                        pDialog.show();
//                    }
//
//                    else
//                    {
//
//
//
//
//
//                        for (int i = 0; i < jsonObject.length(); i++) {
//
//                            JSONObject c = jsonObject.getJSONObject(i);
//                            try {
//                                All_User_Pojo dataObject = new All_User_Pojo();
//                                dataObject.setId(c.getInt("id"));
//                                dataObject.setName(c.getString("name"));
//                                dataObject.setEmail(c.getString("email"));
//                                dataObject.setPhone_no(c.getInt("phone_no"));
//                                mFlowerList2.add(dataObject);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Utilss.hideSweetLoader(pDialog);
//                            }
//                        });
//
//                    }
//
//                    blogs_Adapter = new All_Users_Adapter(getActivity(), mFlowerList2);
//                    parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                    parts_recycler_view.setLayoutManager(mLayoutManager);
//                    parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
//                    parts_recycler_view.setAdapter(blogs_Adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(final VolleyError error) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("ErrorIs" , error.toString());
//
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        Utilss.hideSweetLoader(pDialog);
//                                    }
//                                });
//
//
//                            }
//                        });
//                    }
//                }) {
//            @Override
//            public Map getHeaders() {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", access_token);
//                return headers;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Singleton.getInstance(getActivity()).getRequestQueue().add(stringRequest);
//        mSwipeRefreshLayout.setRefreshing(false);
//    }




    private void getallusers()
    {
        mFlowerList.clear();
        pDialog = Utilss.showSweetLoader(getActivity(), SweetAlertDialog.PROGRESS_TYPE, "Fetching Users...");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://autohapa.oneviewcrm.com/ballarat/api/allUsers/", new Response.Listener<String>() {
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
                                All_User_Pojo dataObject = new All_User_Pojo();
                                dataObject.setId(c.getInt("id"));
                                dataObject.setName(c.getString("name"));
                                dataObject.setEmail(c.getString("email"));
                                dataObject.setPhone_no(c.getInt("phone_no"));
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

                    blogs_Adapter = new All_Users_Adapter(getActivity(), mFlowerList);
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
