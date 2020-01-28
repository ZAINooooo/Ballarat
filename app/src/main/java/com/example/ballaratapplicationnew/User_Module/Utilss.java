package com.example.ballaratapplicationnew.User_Module;


import android.content.Context;
import android.graphics.Color;

import androidx.annotation.Keep;
import cn.pedant.SweetAlert.SweetAlertDialog;


@Keep
public class Utilss {

    public static SweetAlertDialog showSweetLoader(Context context, int dialogtype, String title) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, dialogtype);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#0D5D98"));
        pDialog.setTitleText(title);
        pDialog.setCancelable(false);
        pDialog.show();

        return pDialog;
    }

    public static void hideSweetLoader(SweetAlertDialog pDialog) {
        pDialog.dismissWithAnimation();
    }

}
