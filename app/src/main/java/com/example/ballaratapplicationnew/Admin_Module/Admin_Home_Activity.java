package com.example.ballaratapplicationnew.Admin_Module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ballaratapplicationnew.Admin_Package_Fragment;
import com.example.ballaratapplicationnew.R;
import com.example.ballaratapplicationnew.User_Module.About_Us_Activity;
import com.example.ballaratapplicationnew.User_Module.HomeActivity;
import com.example.ballaratapplicationnew.User_Module.Home_Fragment;
import com.example.ballaratapplicationnew.User_Module.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Admin_Home_Activity extends AppCompatActivity {

    Button searchButton,searchButton2;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    TextView m1;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static int navItemIndex = 0;

    private static final String TAG_HOMES= "Queries";
    private static final String TAG_USERS = "User";
    private static final String ABOUT_US = "About_Us";
    private static final String LOGIN = "LogOut";



    SharedPreferences sharedPreferences;
    public static String CURRENT_TAG = TAG_HOMES;
    private TextView txtName, txtWebsite;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private Toolbar toolbar;
    private String[] activityTitles;
    private Handler mHandler;
    String email_Address,name;
    Typeface face,face2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        toolbar = findViewById(R.id.toolbarss);


        face = Typeface.createFromAsset(Admin_Home_Activity.this.getAssets(),"ptsanswebbold.ttf");
        face2 = Typeface.createFromAsset(Admin_Home_Activity.this.getAssets(),"ptsanswebregular.ttf");

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        email_Address = sharedPreferences.getString("email", "");
        name = sharedPreferences.getString("name", "");
        mHandler = new Handler();


        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer);
        m1 = findViewById(R.id.m1);

        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtWebsite = navHeader.findViewById(R.id.website);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_admin);

        loadNavHeader();

        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOMES;
            loadHomeFragment();
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void loadNavHeader() {
        // name, website

        txtName.setText(name);
        txtWebsite.setText(email_Address);

        Picasso.get().load("https://www.pngkey.com/png/full/349-3499617_person-placeholder-person-placeholder.png").noFade().into(imgProfile);
//
//        Glide.with(getApplicationContext()).load("https://www.pngkey.com/png/full/349-3499617_person-placeholder-person-placeholder.png").into(imgProfile);
        // showing dot next to notifications label
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_dot);
    }



    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOMES;
                        break;

                    case R.id.nav_users:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_USERS;
                        break;

                    case R.id.nav_about_uss:
                        startActivity(new Intent(Admin_Home_Activity.this, About_Us_Activity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.logout:

                        new SmartDialogBuilder(Admin_Home_Activity.this)
                                .setTitle("Logout")
                                .setSubTitle("Do You Want To Logout")
                                .setCancalable(true)
                                .setTitleFont(face)
                                .setSubTitleFont(face2).setPositiveButton("OK", new SmartDialogClickListener() {
                            @Override
                            public void onClick(SmartDialog smartDialog) {

                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.remove("email").apply();
                                editor.remove("name").apply();
                                editor.remove("phone").apply();
                                editor.remove("token").apply();
                                editor.remove("user_id").apply();
                                editor.remove("role").apply();

                                smartDialog.dismiss();
                                startActivity(new Intent(Admin_Home_Activity.this , LoginActivity.class));
                                finish();

                            }
                        }).setNegativeButton("NO" , new SmartDialogClickListener()
                        {
                            @Override
                            public void onClick(SmartDialog smartDialog) {

                                smartDialog.dismiss();
                            }
                        }).build().show();

                        drawer.closeDrawers();
                        return true;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


         actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


//
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment =  getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fl_MainContainer2, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        drawer.closeDrawers();
        // refresh toolbar menu
//        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                return new QueriesFragment();

            case 1:
                // photos
                return new User_Fragment();

            case 2:
                // movies fragment
//                return new User_Setting_Fragment();

//                return new About_Us_Fragment();

            case 3:
                // movies fragment
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

            default:
                return new QueriesFragment();
        }
    }


    @Override
    public void onBackPressed() {

    }

    private void setToolbarTitle() {
        m1.setText(activityTitles[navItemIndex]);
        Log.d("text" ,activityTitles[navItemIndex] );
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


}
