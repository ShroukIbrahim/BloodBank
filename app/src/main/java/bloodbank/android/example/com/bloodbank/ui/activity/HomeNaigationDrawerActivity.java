package bloodbank.android.example.com.bloodbank.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.notificationscount.NotificationsCount;
import bloodbank.android.example.com.bloodbank.data.model.registertoken.RegisterToken;
import bloodbank.android.example.com.bloodbank.data.model.removetoken.RemoveToken;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.AboutFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.ConnectUsFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.FavouriteFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.HomeFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.NotifcationFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.NotifcationSettingFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.ProfileFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.clean;

public class HomeNaigationDrawerActivity extends AppCompatActivity {
    private static int navItemIndex = 0;
    @BindView(R.id.navigation_view)
    NavigationView navView;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.home_navigation_drawer_toolbar)
    Toolbar homeNavigationDrawerToolbar;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.frame2)
    FrameLayout frame2;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.count_num)
    TextView countNum;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.notf_icon)
    RelativeLayout notfIcon;
    @BindView(R.id.back_icon)
    RelativeLayout backIcon;
    private ApiServices apiServices = getClient().create(ApiServices.class);

    private ActionBarDrawerToggle toggle;
    //private TextView count;
//    private int countnum = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_naigation_drawer);
        ButterKnife.bind(this);
        HomeFragment homeFragment = new HomeFragment();
        HelperMethod.replace(homeFragment, getSupportFragmentManager(), R.id.frame, null, null);
        setNavigationDrawer();
        getnotificationcount();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setNavigationDrawer() {
        setSupportActionBar(homeNavigationDrawerToolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, homeNavigationDrawerToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.rgb(47, 63, 81));
        toggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    fragment = new ProfileFragment();
                    setVisibilityicon(View.INVISIBLE);
                    tittle.setText("Edit Profile");
                    navItemIndex = 0;
                } else if (id == R.id.nav_notification_settings) {
                    setVisibilityicon(View.INVISIBLE);
                    tittle.setText("Notfication Settings");
                    fragment = new NotifcationSettingFragment();
                    navItemIndex = 1;

                } else if (id == R.id.nav_favorite) {
                    setVisibilityicon(View.VISIBLE);
                    tittle.setText("Favorites");
                    fragment = new FavouriteFragment();
                    navItemIndex = 2;

                } else if (id == R.id.nav_home) {
                    setVisibilityicon(View.VISIBLE);
                    tittle.setText(" ");
                    fragment = new HomeFragment();
                    navItemIndex = 3;

                } else if (id == R.id.nav_instructions) {


                } else if (id == R.id.nav_connect_us) {
                    setVisibilityicon(View.INVISIBLE);
                    tittle.setText("Connect Us");
                    fragment = new ConnectUsFragment();
                    navItemIndex = 7;

                } else if (id == R.id.nav_about) {
                    setVisibilityicon(View.INVISIBLE);
                    tittle.setText("About");
                    fragment = new AboutFragment();
                    navItemIndex = 6;

                } else if (id == R.id.nav_rating) {

                } else if (id == R.id.nav_logout) {
                    Toast.makeText(getApplication(), "Logout ...", Toast.LENGTH_LONG).show();
                    clean(HomeNaigationDrawerActivity.this);
                    startActivity(new Intent(HomeNaigationDrawerActivity.this, SplashActivity.class));
                    //RemoveToken();
                }

                if (fragment != null) {
                    HelperMethod.replace(fragment, getSupportFragmentManager(), R.id.frame, null, null);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });
    }



    public void getnotificationcount() {
        ApiServices apiServices = getClient().create(ApiServices.class);
        apiServices.getNotificationCount("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl").enqueue(new Callback<NotificationsCount>() {
            @Override
            public void onResponse(Call<NotificationsCount> call, Response<NotificationsCount> response) {
                if (response.body().getStatus().equals(1)) {
                    if (countNum != null) {
                        if (response.body().getData().getNotificationsCount() == 0) {
                            if (countNum.getVisibility() != View.GONE) {
                                countNum.setVisibility(View.GONE);
                            }
                        } else {
                            countNum.setText(String.valueOf(response.body().getData().getNotificationsCount()));
                            if (countNum.getVisibility() != View.VISIBLE) {
                                countNum.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationsCount> call, Throwable t) {

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (frame2.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
            setVisibility(View.GONE);
        } else {
            HomeFragment homeFragment = new HomeFragment();
            HelperMethod.replace(homeFragment, getSupportFragmentManager(), R.id.frame, null, null);

        }

    }

    public void setVisibility(Integer visibility) {
        frame2.setVisibility(visibility);


    }

    public void setVisibilityicon(Integer visibility) {
        notfIcon.setVisibility(visibility);
        getnotificationcount();
    }

    public void setVisibilityback(Integer visibility) {
        backIcon.setVisibility(visibility);

    }

    @OnClick(R.id.notification_icon)
    public void onViewClicked() {
        setVisibility(View.VISIBLE);
        NotifcationFragment notifcationFragment = new NotifcationFragment();
        HelperMethod.replace(notifcationFragment, getSupportFragmentManager(), R.id.frame2, null, null);


    }

    }
