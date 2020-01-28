package com.example.ballaratapplicationnew;

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
import com.example.ballaratapplicationnew.Admin_Module.PackageList_Pojo;
import com.example.ballaratapplicationnew.Admin_Module.Package_Adapter;
import com.example.ballaratapplicationnew.Admin_Module.User_Setting_Fragment;
import com.example.ballaratapplicationnew.R;
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


public class Admin_Package_Fragment extends Fragment {




    RecyclerView parts_recycler_view;
    ArrayList<PackageList_Pojo> mFlowerList;
    String access_token;
    SharedPreferences sharedPreferences;
    Package_Adapter itemArrayAdapter;

    SweetAlertDialog pDialog;


    public Admin_Package_Fragment() {

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

        View view=inflater.inflate(R.layout.activity_package_admin_, container, false);

        mFlowerList= new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");

        parts_recycler_view = view.findViewById(R.id.parts_recycler_view);

        itemArrayAdapter = new Package_Adapter(getActivity(), mFlowerList);
        parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        parts_recycler_view.setLayoutManager(mLayoutManager);
        parts_recycler_view.setItemAnimator(new DefaultItemAnimator());
        parts_recycler_view.setAdapter(itemArrayAdapter);

        getallpackages();


        return view;
    }

    private void getallpackages()
    {

        mFlowerList.clear();
        pDialog = Utilss.showSweetLoader(getActivity(), SweetAlertDialog.PROGRESS_TYPE, "Fetching Data...");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://autohapa.oneviewcrm.com/ballarat/api/package/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ResponseIs" , response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray obj = jsonObject.getJSONArray("result");



                    if (obj.length()==0)
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

                        for (int i = 0; i < obj.length(); i++) {


//                            mFlowerList.clear();
                            JSONObject jsonObject3 = new JSONObject(String.valueOf(obj.get(i)));
                            PackageList_Pojo dataObject = new PackageList_Pojo();

                            dataObject.setId(jsonObject3.getString("id"));
                            dataObject.setPkg_name(jsonObject3.getString("pkg_name"));
                            dataObject.setPkg_price(jsonObject3.getString("pkg_price"));
                            dataObject.setPkg_image(jsonObject3.getString("pkg_image"));

                            mFlowerList.add(dataObject);
                            itemArrayAdapter.notifyDataSetChanged();
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });

                    }

                    itemArrayAdapter = new Package_Adapter(getActivity(), mFlowerList);
                    parts_recycler_view.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 10));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
