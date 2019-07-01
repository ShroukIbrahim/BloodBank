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
public class ForgetPasswordStep1Fragment extends Fragment {


    @BindView(R.id.fragment_forget_password_step1_phone_num)
    TextInputEditText fragmentForgetPasswordStep1PhoneNum;
    @BindView(R.id.fragment_forget_password_step1_send_code)
    Button fragmentForgetPasswordStep1SendCode;
    Unbinder unbinder;
    ApiServices apiServices;


    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void resetPassword()
    {
        apiServices = getClient().create(ApiServices.class);
        final String PhoneNum = fragmentForgetPasswordStep1PhoneNum.getText().toString();
        apiServices.resetPassword(PhoneNum).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse( Call<ResetPassword> call, Response<ResetPassword> response ) {
                if(response.body().getStatus().equals(1)) {
                    Toast.makeText(getActivity(), "Please Check Your Phone.......", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("pinCode",String.valueOf(response.body().getData().getPinCodeForTest()));
                    bundle.putString("PhoneNum",PhoneNum);
                    ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
                    if(getFragmentManager()!=null){
                        HelperMethod.replace(forgetPasswordStep2Fragment,getActivity().getSupportFragmentManager(),R.id.user_cycle_activity_usercycle,null,null);
//                        forgetPasswordStep2Fragment.setArguments(bundle);
                    }

                }
                else {
                    Toast.makeText(getActivity(), "The Phone Number is anviled", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure( Call<ResetPassword> call, Throwable t ) {



            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_forget_password_step1_send_code)
    public void onViewClicked() {
        resetPassword();

    }
}
