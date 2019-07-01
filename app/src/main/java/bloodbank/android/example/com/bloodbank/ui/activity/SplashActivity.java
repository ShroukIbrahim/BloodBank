package bloodbank.android.example.com.bloodbank.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.fragment.SplashCycleFragment.SplashFragment;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replace(splashFragment,getSupportFragmentManager(),R.id.splash_activity_splashscreen,null,null);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
