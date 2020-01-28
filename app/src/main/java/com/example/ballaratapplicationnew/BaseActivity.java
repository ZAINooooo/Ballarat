package com.example.ballaratapplicationnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected Context context;
    private ProgressDialog progressDialog;
    protected SweetAlertDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;

//        Util.e(TAG, "FCM ID " + FirebaseInstanceId.getInstance().getToken());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        LogUtil.e(TAG, "BaseActivity onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void showDialog() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        dismissDialog();

        super.onDestroy();
    }


}
