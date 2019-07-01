package bloodbank.android.example.com.bloodbank.ui.fragment.UserCycleFragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.newpassword.NewPassword;
import bloodbank.android.example.com.bloodbank.data.model.resetpassword.ResetPassword;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordStep2Fragment extends Fragment {


    @BindView(R.id.fragment_forget_password_step2_validation_code)
    TextInputEditText fragmentForgetPasswordStep2ValidationCode;
    @BindView(R.id.fragment_forget_password_step2_new_password)
    TextInputEditText fragmentForgetPasswordStep2NewPassword;
    @BindView(R.id.fragment_forget_password_step2_confirm_new_password)
    TextInputEditText fragmentForgetPasswordStep2ConfirmNewPassword;
    @BindView(R.id.fragment_forget_password_step2_change_password)
    Button fragmentForgetPasswordStep2ChangePassword;
    Unbinder unbinder;
    ApiServices apiServices;
    Bundle bundle = getArguments();
    private String phoneNum;


    public ForgetPasswordStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (bundle != null) {
            phoneNum = bundle.getString("PhoneNum");

        }
            return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_forget_password_step2_change_password)
    public void onViewClicked() {
        setNewPassword();
    }

    private void setNewPassword() {
        apiServices = getClient().create(ApiServices.class);
        String validationCode = fragmentForgetPasswordStep2ValidationCode.getText().toString();
        String NewPasswword = fragmentForgetPasswordStep2NewPassword.getText().toString();
        String confirmNewPassword = fragmentForgetPasswordStep2ConfirmNewPassword.getText().toString();
        apiServices.newPassword(NewPasswword, confirmNewPassword, validationCode, phoneNum).enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse( Call<NewPassword> call, Response<NewPassword> response ) {
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(getActivity(), "Change Password Successfully.......", Toast.LENGTH_SHORT).show();
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replace(loginFragment, getActivity().getSupportFragmentManager(), R.id.user_cycle_activity_usercycle, null, null);

                } else {

                    Toast.makeText(getActivity(), "The Code is anviled", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure( Call<NewPassword> call, Throwable t ) {


            }
        });


    }
}

