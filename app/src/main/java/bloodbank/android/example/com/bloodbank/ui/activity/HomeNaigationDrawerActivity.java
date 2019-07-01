package bloodbank.android.example.com.bloodbank.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.TapLayoutAdapter;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.DonationRequestFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeNaigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.home_navigation_drawer_toolbar)
    Toolbar homeNavigationDrawerToolbar;
    @BindView(R.id.home_navigation_drawer_taplayout)
    TabLayout homeNavigationDrawerTaplayout;
    //    @BindView(R.id.fragment_home_navigation_drawer_filter_spinner)
//    Spinner fragmentHomeNavigationDrawerFilterSpinner;
    @BindView(R.id.home_navigation_drawer_viewpager)
    ViewPager homeNavigationDrawerViewpager;
    //    @BindView(R.id.fragment_home_navigation_drawer_add)
//    FloatingActionButton fragmentHomeNavigationDrawerAdd;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    TapLayoutAdapter tapLayoutAdapter;
    @BindView(R.id.fragment_post_add_donation)
    ImageButton fragmentPostAddDonation;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_naigation_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(homeNavigationDrawerToolbar);
        tapLayoutAdapter = new TapLayoutAdapter(getSupportFragmentManager());
        homeNavigationDrawerViewpager.setAdapter(tapLayoutAdapter);
        homeNavigationDrawerTaplayout.setupWithViewPager(homeNavigationDrawerViewpager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, homeNavigationDrawerToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notifcation_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notification_icon) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected( MenuItem item ) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_notification_settings) {

        } else if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_instructions) {

        } else if (id == R.id.nav_connect_us) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_rating) {

        } else if (id == R.id.nav_logout) {
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fragment_post_add_donation)
    public void onViewClicked() {
        DonationRequestFragment donationRequestFragment = new DonationRequestFragment();
        HelperMethod.replace(donationRequestFragment,getSupportFragmentManager(),R.id.drawer_layout,null,null);
    }
}
