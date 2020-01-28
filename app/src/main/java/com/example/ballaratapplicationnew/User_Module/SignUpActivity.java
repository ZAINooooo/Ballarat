package com.example.ballaratapplicationnew.User_Module;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ballaratapplicationnew.BaseActivity;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.Singleton;
import com.example.ballaratapplicationnew.User_Module.HomeActivity;
import com.example.ballaratapplicationnew.User_Module.LoginActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class SignUpActivity extends BaseActivity {
    TextView multicolorLine;
    ImageButton facebook_login,google_login;
    Button sign_up;

    SharedPreferences sharedPreferences;


    String statusCode,id_user;
    //facebook
    EditText name_value, email_value,phone_value,password_value,address_value;
    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;


    String value2,value5, responses,responses1;

    //google
    private static final String TAG = "AndroidClarified";
    private GoogleSignInClient googleSignInClient;
    public static final String GOOGLE_ACCOUNT = "google_account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken("547448084005-fbh2on0lb25lhqbqqp8ok1dj87jiaebi.apps.googleusercontent.com").build();

        name_value = findViewById(R.id.name);
        email_value = findViewById(R.id.email);
        phone_value = findViewById(R.id.phone);
        password_value = findViewById(R.id.password);
        address_value = findViewById(R.id.address);


        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        sign_up = findViewById(R.id.sign_up);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apicall();
            }
        });


        callbackManager = CallbackManager.Factory.create();
        facebook_login = findViewById(R.id.facebook_login);
        google_login = findViewById(R.id.google_login);


        googleSignInClient = GoogleSignIn.getClient(this, gso);
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });


        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("email", "public_profile"));
            }
        });




        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        useLoginInformation(accessToken);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignUpActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignUpActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });





        multicolorLine = findViewById(R.id.multicolorline);
        String text = "<font color='#0f75bc'>Back To?</font> "+"<font color='#ed1c24'>Login</font>";
        multicolorLine.setText(Html.fromHtml(text));

        multicolorLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
                finish();
            }
        });




        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {

                } else {

                    Toast.makeText(SignUpActivity.this, "Not Logged In", Toast.LENGTH_SHORT).show();
                }
            }
        };


    }




    private void useLoginInformation(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    Log.d("First_Names" ,name+ " "+email);

                    apicallSocialLogin(name,email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }


    private void apicallSocialLogin(String name , String email) {
        pDialog = Utilss.showSweetLoader(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Signing Up...");
        final String url = "http://autohapa.oneviewcrm.com/ballarat/api/signup?name=" + name + "&email=" + email + "&password=" + "N/A" + "&phone_no=" + "00000000000" + "&address=" + "N/A";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String responses = response.toString();
                        Log.e("response", "onResponse(): " + responses);



                        try {
                            JSONObject  json = new JSONObject(responses);
                            value2 = json.getString("status");

                            if (statusCode.equals("200") && value2.equals("success")) {


                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONObject jsonObject3 = jsonObject.getJSONObject("user");
                                    id_user = jsonObject3.getString("id");
                                    String token_value = jsonObject3.getString("api_key");
                                    String email_address = jsonObject3.getString("email");
                                    String names = jsonObject3.getString("name");
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", token_value);
                                    editor.putString("name", names);
                                    editor.putString("email", email_address);
                                    editor.putString("user_id", id_user);
                                    editor.apply();

                                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                    finish();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Utilss.hideSweetLoader(pDialog);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                            else if (statusCode.equals("200") && value2.equals("error")) {


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Utilss.hideSweetLoader(pDialog);
                                    }
                                });


                                value5 = json.getString("message");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                        pDialog.setTitleText(value5);
                                        pDialog.setCancelable(true);
                                        pDialog.show();
                                        return;
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },                        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Utilss.hideSweetLoader(pDialog);
                    }
                });
            }
        }
        ) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = String.valueOf(response.statusCode);
                Log.d("StatusCode3", statusCode);
                //Handling logic
                return super.parseNetworkResponse(response);
            }
        };


        Singleton.getInstance(SignUpActivity.this).getRequestQueue().add(getRequest);
    }

    private void apicall()
    {
        pDialog = Utilss.showSweetLoader(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Signing Up...");
        final String url = "http://autohapa.oneviewcrm.com/ballarat/api/signup?name=" + name_value.getText().toString() + "&email=" + email_value.getText().toString()+"&password=" + password_value.getText().toString()+"&phone_no=" + phone_value.getText().toString()+"&address=" + address_value.getText().toString();

        Log.d("Response_All22" , ""+ url);



        if (name_value.getText().toString().equals("") && email_value.getText().toString().equals("") && password_value.getText().toString().equals("") && phone_value.getText().toString().equals("") &&address_value.getText().toString().equals("") )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fields Are Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }



else  if (phone_value.length()<11)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Phone Number must be of 11 digits..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }




        else  if (name_value.getText().toString().equals("") )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Name Is Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }




        else  if (email_value.getText().toString().equals("") )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Name Is Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }



        else  if (phone_value.getText().toString().equals("") )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Phone Number Is Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }


        else  if (password_value.getText().toString().equals("") )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Password Is Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }



        else  if (address_value.getText().toString().equals("") )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Address Is Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;
        }


        else
        {
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String responses = response.toString();
                            Log.e("response", "onResponse(): " + responses);



                            try {
                                JSONObject  json = new JSONObject(responses);
                                value2 = json.getString("status");

                                if (statusCode.equals("200") && value2.equals("success")) {


                                    try {
                                        JSONObject jsonObject = new JSONObject(response.toString());
                                        JSONObject jsonObject3 = jsonObject.getJSONObject("user");

                                        id_user = jsonObject3.getString("id");
                                        String token_value = jsonObject3.getString("api_key");
                                        String names = jsonObject3.getString("name");
                                        String email_address = jsonObject3.getString("email");

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("token", token_value);
                                        editor.putString("user_id", id_user);
                                        editor.putString("name", names);
                                        editor.putString("email", email_address);
                                        editor.putString("user_id", id_user);


                                        editor.apply();

                                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                        finish();

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }


                                else if (statusCode.equals("200") && value2.equals("error")) {


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utilss.hideSweetLoader(pDialog);
                                        }
                                    });


                                    value5 = json.getString("message");

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


                                            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialog.setTitleText(value5);
                                            pDialog.setCancelable(true);
                                            pDialog.show();
                                            return;
                                        }
                                    });

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    },                        new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });
                }
            }
            ) {

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    statusCode = String.valueOf(response.statusCode);
                    Log.d("StatusCode3", statusCode);
                    //Handling logic
                    return super.parseNetworkResponse(response);
                }
            };


            Singleton.getInstance(SignUpActivity.this).getRequestQueue().add(getRequest);
        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.

                        Toast.makeText(this, "jjjj", Toast.LENGTH_SHORT).show();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        String name= Objects.requireNonNull(account).getDisplayName();
                        String email= account.getEmail();

                        apicallSocialLogin(name,email);
                        Log.d("First_Names22" ,"  "+name+ " "+email);
//                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }


    public void onStart() {
        super.onStart();
    }



    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }




}
