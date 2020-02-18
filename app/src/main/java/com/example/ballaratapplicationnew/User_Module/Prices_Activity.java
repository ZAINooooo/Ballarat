package com.example.ballaratapplicationnew.User_Module;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.ballaratapplicationnew.BaseActivity;
import com.example.ballaratapplicationnew.PayPal.ConfirmationActivity;
import com.example.ballaratapplicationnew.PayPal.PayPalConfig;
import com.example.ballaratapplicationnew.R;
import com.facebook.share.Share;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Prices_Activity extends BaseActivity {


    RelativeLayout mk4;
//    String User_Ids;
    //Payment Amount
//    private String paymentAmount;
    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    String total_price,pkg_ids   , pkg_name,pkg_image;
    private static PayPalConfiguration config = new PayPalConfiguration() .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPalConfig.PAYPAL_CLIENT_ID);
SharedPreferences sharedPreferences;
String user_id;
CheckBox paypal_check_box;
TextView m3,mh , m1;
ImageView profile_image;
boolean isCheckede=false;
TextView multicolorline2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices_);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");

        pkg_ids = getIntent().getStringExtra("Package_Id1");
        total_price = getIntent().getStringExtra("User_price");

        pkg_name = getIntent().getStringExtra("User_Name");
        pkg_image = getIntent().getStringExtra("User_Image");

        Log.d("Package_Id" , pkg_name + pkg_image);
        mk4 = findViewById(R.id.mk4);
        m3 = findViewById(R.id.m2);
        mh = findViewById(R.id.mh);

        multicolorline2= findViewById(R.id.multicolorline2);
        paypal_check_box= findViewById(R.id.paypal_check_box);
        m1 = findViewById(R.id.m1);
        profile_image = findViewById(R.id.profile_image);


        String parsedStr = pkg_name.replaceAll("(.{21})", "$1\n");
        String parsedStr22 = parsedStr.replace("\n ", "\n");
        m1.setText(parsedStr22);



//        if (pkg_name.length() >21)
//        {
//            Toast.makeText(context, "More then 21", Toast.LENGTH_SHORT).show();
//
//        }
//
//        else
//        {
//            Toast.makeText(context, "Less then 21", Toast.LENGTH_SHORT).show();
//
//        }
//        m1.setText(pkg_name);
        Glide.with(Prices_Activity.this).load(pkg_image).into(profile_image);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        m3.setText("  $"+total_price);
        mh.setText("TOTAL : $"+total_price);



        if (paypal_check_box.isChecked())
        {
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
        }

//        else
//        {
//            SweetAlertDialog pDialog = new SweetAlertDialog(Prices_Activity.this, SweetAlertDialog.ERROR_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Select Terms Conditions First");
//            pDialog.setCancelable(true);
//            pDialog.show();
//            return;
//        }



        multicolorline2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/us/webapps/mpp/ua/useragreement-full"));
            startActivity(browserIntent);

            }
        });


//        if (paypal_check_box.isChecked())
//        {
//            Toast.makeText(context, "Its Clicked..!!", Toast.LENGTH_SHORT).show();
//
//        }
//
//        else
//        {
//            Toast.makeText(context, "Its Not Clicked..!!", Toast.LENGTH_SHORT).show();
//        }


        paypal_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
//                    Toast.makeText(context, "Its Clicked..!!", Toast.LENGTH_SHORT).show();
//                    isCheckede=true;
                }

                else
                {
//                    Toast.makeText(context, "Its not..!!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        mk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                submitpayment();

//                if (total_price.equals("0.00"))
//                {
//                    final SweetAlertDialog pDialog = new SweetAlertDialog(Prices_Activity.this, SweetAlertDialog.ERROR_TYPE);
//                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pDialog.setTitleText("Amount Can't Be Zero..!!");
//                    pDialog.setCancelable(true);
//                    pDialog.show();
//                }
//
//                else
//                {
//                    submitpayment();
//                }
//




            }
        });



    }

    //use
    private void submitpayment() {
        //Creating a paypalpayment

        if (paypal_check_box.isChecked())
        {
            PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(total_price)), "USD", "Ballarat Application", PayPalPayment.PAYMENT_INTENT_SALE);
            //Creating Paypal Payment activity intent
            Intent intent = new Intent(this, PaymentActivity.class);
            //putting the paypal configuration to the intent
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            //Puting paypal payment to the intent
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            //Starting the intent activity for result
            //the request code will be used on the method onActivityResult
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        }

        else
        {
            SweetAlertDialog pDialog = new SweetAlertDialog(Prices_Activity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Select Terms Conditions First");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {

                        if (paypal_check_box.isChecked())
                        {
                            //Getting the payment details
                            String paymentDetails = confirm.toJSONObject().toString(4);
                            Log.i("paymentExample", paymentDetails);

                            Intent intent = new Intent(this, ConfirmationActivity.class);
                            intent.putExtra("PaymentDetails", paymentDetails);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("package_id", pkg_ids);
                            intent.putExtra("PaymentAmount", total_price);
                            startActivity(intent);

//                        Toast.makeText(this, "Hereee..!!", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            SweetAlertDialog pDialog = new SweetAlertDialog(Prices_Activity.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Select Terms Conditions First");
                            pDialog.setCancelable(true);
                            pDialog.show();
                            return;
                        }

                    }

                    catch (JSONException e)
                    {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }




    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


}
