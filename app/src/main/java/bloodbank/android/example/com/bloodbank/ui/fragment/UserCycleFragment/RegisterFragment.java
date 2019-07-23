package bloodbank.android.example.com.bloodbank.ui.fragment.UserCycleFragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypeData;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.model.register.Register;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.DateModel;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.SaveData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_BID;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_BLOOD_TYPE_ID;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_BLOOD_TYPE_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_CITY_ID;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_CITY_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_DLD;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_EMAIL;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_ID;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_PASSWORD;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_PHONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    @BindView(R.id.register_fragment_date_of_birth)
    TextInputEditText registerFragmentDateOfBirth;
    @BindView(R.id.register_fragment_last_date_of_donation)
    TextInputEditText registerFragmentLastDateOfDonation;
    Unbinder unbinder;
    @BindView(R.id.register_fragment_governorates)
    Spinner registerFragmentGovernorates;
    @BindView(R.id.register_fragment_cities)
    Spinner registerFragmentCities;
    @BindView(R.id.register_fragment_name)
    TextInputEditText registerFragmentName;
    @BindView(R.id.register_fragment_email)
    TextInputEditText registerFragmentEmail;
    @BindView(R.id.register_fragment_phone_num)
    TextInputEditText registerFragmentPhoneNum;
    @BindView(R.id.register_fragment_password)
    TextInputEditText registerFragmentPassword;
    @BindView(R.id.register_fragment_confiram_password)
    TextInputEditText registerFragmentConfiramPassword;
    @BindView(R.id.register_fragment_singup)
    Button registerFragmentSingup;
    @BindView(R.id.register_fragment_blood_type)
    Spinner registerFragmentBloodType;
    @BindView(R.id.spinner_layout2)
    RelativeLayout spinnerLayout2;
    @BindView(R.id.spinner_layout3)
    RelativeLayout spinnerLayout3;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.notf_icon)
    RelativeLayout notfIcon;

    private int governorates_id;
    private int cities_id;
    private int bloodtype_id;

    private ApiServices apiServices;

    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;

    SharedPreferencesManger sharedPreferencesManger;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        tittle.setText("Create New Account");
        SpinnerBloodType();
        getGovernorates();
        getCities(governorates_id);
        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);

        registerFragmentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.date_of_birth), registerFragmentDateOfBirth, dateModel1);
            }
        });
        registerFragmentLastDateOfDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.last_date_of_donation), registerFragmentLastDateOfDonation, dateModel2);
            }
        });

        return view;
    }

    private void SpinnerBloodType() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                try {

                    if (response.body().getStatus().equals(1)) {

                        final List<BloodTypeData> bloodTypes = response.body().getData();
                        List<String> bloodtypeTxt = new ArrayList<String>();
                        final List<Integer> bloodtypeId = new ArrayList<Integer>();

                        bloodtypeTxt.add("Blood Type");
                        bloodtypeId.add(0);

                        for (int i = 0; i < bloodTypes.size(); i++) {
                            bloodtypeTxt.add(bloodTypes.get(i).getName());
                            bloodtypeId.add(bloodTypes.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, bloodtypeTxt);
                        registerFragmentBloodType.setAdapter(adapter);
                        registerFragmentBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    bloodtype_id = bloodtypeId.get(i);
                                    Toast.makeText(getActivity(), bloodtype_id + "", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {

            }
        });

    }

    private void getGovernorates() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<GovernoratesData> governorates = response.body().getData();
                        List<String> governoratTxt = new ArrayList<String>();
                        final List<Integer> governoratId = new ArrayList<Integer>();
                        governoratId.add(0);
                        governoratTxt.add("Governorates");


                        for (int i = 0; i < governorates.size(); i++) {
                            governoratTxt.add(governorates.get(i).getName());
                            governoratId.add(governorates.get(i).getId());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, governoratTxt);
                        registerFragmentGovernorates.setAdapter(adapter);
                        registerFragmentGovernorates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    governorates_id = governoratId.get(i);
                                    getCities(governoratId.get(i));
                                    Toast.makeText(getActivity(), governorates_id + "", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }

        });
    }

    private void getCities(int id) {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getCities(id).enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<GovernoratesData> citiesData = response.body().getData();
                        List<String> cityTxt = new ArrayList<String>();
                        final List<Integer> cityid = new ArrayList<Integer>();
                        cityid.add(0);
                        cityTxt.add("City");
                        for (int i = 0; i < citiesData.size(); i++) {
                            cityTxt.add(citiesData.get(i).getName());
                            cityid.add(citiesData.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, cityTxt);
                        registerFragmentCities.setAdapter(adapter);

                        registerFragmentCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {

                                    cities_id = cityid.get(i);
                                    Toast.makeText(getActivity(), cities_id + "", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });

    }


    private void Register() {

        apiServices = getClient().create(ApiServices.class);
        String name = registerFragmentName.getText().toString();
        String email = registerFragmentEmail.getText().toString();
        String dataOfBirth = registerFragmentDateOfBirth.getText().toString();
        String lastDateOfDonation = registerFragmentLastDateOfDonation.getText().toString();
        String phoneNum = registerFragmentPhoneNum.getText().toString();
        String password = registerFragmentPassword.getText().toString();
        String confirmPassword = registerFragmentConfiramPassword.getText().toString();


        //validating inputs
        if (TextUtils.isEmpty(name)) {
            registerFragmentName.setError("Please enter your Name");
            registerFragmentName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            registerFragmentEmail.setError("Please enter your Email");
            registerFragmentEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneNum)) {
            registerFragmentPhoneNum.setError("Please enter your Phone Number");
            registerFragmentPhoneNum.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            registerFragmentPassword.setError("Please enter your Password");
            registerFragmentPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword) && (password != confirmPassword)) {
            registerFragmentConfiramPassword.setError("Please enter your ConfirmPassword");
            registerFragmentConfiramPassword.requestFocus();
            return;
        }


        apiServices.onRegister(name, email, dataOfBirth, cities_id, phoneNum,
                lastDateOfDonation, password, confirmPassword, bloodtype_id).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus().equals(1)) {
                    SaveData(getActivity(), USER_API_TOKEN, response.body().getData().getApiToken());
                    SaveData(getActivity(), USER_ID, response.body().getData().getClient().getId().toString());
                    SaveData(getActivity(), USER_NAME, response.body().getData().getClient().getName());
                    SaveData(getActivity(), USER_EMAIL, response.body().getData().getClient().getEmail());
                    SaveData(getActivity(), USER_PHONE, response.body().getData().getClient().getPhone());
                    SaveData(getActivity(), USER_BLOOD_TYPE_ID, response.body().getData().getClient().getBloodTypeId());
                    SaveData(getActivity(), USER_BLOOD_TYPE_NAME, response.body().getData().getClient().getBloodType().getName());
                    SaveData(getActivity(), USER_CITY_ID, response.body().getData().getClient().getCityId());
                    SaveData(getActivity(), USER_CITY_NAME, response.body().getData().getClient().getCity().getName());
                    SaveData(getActivity(), USER_DLD, response.body().getData().getClient().getDonationLastDate());
                    SaveData(getActivity(), USER_BID, response.body().getData().getClient().getBirthDate());
                    SaveData(getActivity(), USER_PASSWORD, registerFragmentPassword.getText().toString());
                    SaveData(getActivity(), USER_PASSWORD, registerFragmentConfiramPassword.getText().toString());

                    Intent Register = new Intent(getActivity(), HomeNaigationDrawerActivity.class);
                    startActivity(Register);
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.register_fragment_singup)
    public void onViewClicked() {
        Register();

    }


}
