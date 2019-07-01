package bloodbank.android.example.com.bloodbank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.fragment.UserCycleFragment.LoginFragment;

public class UserCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replace(loginFragment,getSupportFragmentManager(),R.id.user_cycle_activity_usercycle,null,null);


    }
}
