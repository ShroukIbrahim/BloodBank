package bloodbank.android.example.com.bloodbank.ui.fragment.UserCycleFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.login.Login;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.CHECK_BOX;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadBoolean;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.SaveData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_EMAIL;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_ID;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_PASSWORD;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_PHONE;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.setSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    @BindView(R.id.login_fragment_phone_num)
    EditText loginFragmentPhoneNum;
    @BindView(R.id.login_fragment_password)
    EditText loginFragmentPassword;
    @BindView(R.id.login_fragment_forget_password)
    TextView loginFragmentForgetPassword;
    @BindView(R.id.login_fragment_remmber_me)
    CheckBox loginFragmentRemmberMe;
    @BindView(R.id.login_fragment_login)
    Button loginFragmentLogin;
    @BindView(R.id.login_fragment_create_new_account)
    Button loginFragmentCreateNewAccount;
    Unbinder unbinder;




    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        setSharedPreferences(getActivity());
        dataUserShrPreferences();
        return view;
    }

    private void dataUserShrPreferences() {
        loginFragmentPhoneNum.setText(LoadData(getActivity(), USER_PHONE));
        loginFragmentPassword.setText(LoadData(getActivity(),USER_PASSWORD));

        // check is checkBox is Checked
        if (LoadBoolean(getActivity(), String.valueOf(loginFragmentRemmberMe))) {
            Log.d("response", "true");
            loginFragmentRemmberMe.setChecked(true);
        } else {
            loginFragmentRemmberMe.setChecked(false);
            Log.d("response", "false");

        }
    }

    private void SharedPreferencesSaveData(int id, String ApiToken, String name, String email, String phone,String  password) {
        SaveData(getActivity(), USER_API_TOKEN, String.valueOf(ApiToken));
        SaveData(getActivity(), USER_ID, String.valueOf(id));
        SaveData(getActivity(), USER_NAME, String.valueOf(name));
        SaveData(getActivity(), USER_EMAIL, String.valueOf(email));
        SaveData(getActivity(), USER_PHONE, String.valueOf(phone));
        SaveData(getActivity(), USER_PASSWORD, String.valueOf(password));
    }

    private void Login()
    {
            ApiServices apiServices = getClient().create(ApiServices.class);
            String Password = loginFragmentPassword.getText().toString();
            String PhoneNum = loginFragmentPhoneNum.getText().toString();

            apiServices.onLogin(PhoneNum, Password).enqueue(new Callback<Login>() {
                @Override
                public void onResponse( Call<Login> call, Response<Login> response ) {

                    if (response.body().getStatus().equals(1)) {

                        Toast.makeText(getActivity(), response.body().getMsg() + "..", Toast.LENGTH_SHORT).show();
                        // check is checkBox is Checked
                        if (loginFragmentRemmberMe.isChecked()) {
                            // save data login user
                            SharedPreferencesSaveData(response.body().getData().getClient().getId()
                                    , response.body().getData().getApiToken()
                                    , response.body().getData().getClient().getName(),
                                    response.body().getData().getClient().getEmail(),
                                    response.body().getData().getClient().getPhone()
                                    , loginFragmentPassword.getText().toString());

                        }
                            Intent Login = new Intent(getActivity(), HomeNaigationDrawerActivity.class);
                            startActivity(Login);

                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure( Call<Login> call, Throwable t ) {

                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_fragment_forget_password, R.id.login_fragment_remmber_me, R.id.login_fragment_login, R.id.login_fragment_create_new_account})
    public void onViewClicked( View view ) {
        switch (view.getId()) {
            case R.id.login_fragment_forget_password:
                ForgetPasswordStep1Fragment forgetPassword1Fragment = new ForgetPasswordStep1Fragment();
                HelperMethod.replace(forgetPassword1Fragment,getActivity().getSupportFragmentManager(),R.id.user_cycle_activity_usercycle,null,null);
                break;
            case R.id.login_fragment_remmber_me:

                loginFragmentRemmberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged( CompoundButton buttonView, boolean isChecked) {
                        SaveData(getActivity(), CHECK_BOX,isChecked);
                    }
                });

                break;
            case R.id.login_fragment_login:
                Login();

                break;
            case R.id.login_fragment_create_new_account:
                RegisterFragment registerFragment = new RegisterFragment();
                HelperMethod.replace(registerFragment,getActivity().getSupportFragmentManager(),R.id.user_cycle_activity_usercycle,null,null);
                break;
        }
    }
}
