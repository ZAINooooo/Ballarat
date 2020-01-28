package com.example.ballaratapplicationnew.Admin_Module;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ballaratapplicationnew.R;


public class User_Setting_Fragment extends Fragment {


    public User_Setting_Fragment() {

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
        return inflater.inflate(R.layout.fragment_user__setting_, container, false);
    }

}
