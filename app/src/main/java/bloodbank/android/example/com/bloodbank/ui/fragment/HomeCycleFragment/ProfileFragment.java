package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypeData;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.model.login.Login;
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
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_BLOOD_TYPE_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_GOVERMENT_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_PASSWORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @BindView(R.id.profile_fragment_name)
    TextInputEditText profileFragmentName;
    @BindView(R.id.profile_fragment_email)
    TextInputEditText profileFragmentEmail;
    @BindView(R.id.profile_fragment_date_of_birth)
    TextInputEditText profileFragmentDateOfBirth;
    @BindView(R.id.profile_fragment_blood_type)
    Spinner profileFragmentBloodType;
    @BindView(R.id.profile_fragment_last_date_of_donation)
    TextInputEditText profileFragmentLastDateOfDonation;
    @BindView(R.id.profile_fragment_governorates)
    Spinner profileFragmentGovernorates;
    @BindView(R.id.profile_fragment_cities)
    Spinner profileFragmentCities;
    @BindView(R.id.profile_fragment_phone_num)
    TextInputEditText profileFragmentPhoneNum;
    @BindView(R.id.profile_fragment_password)
    TextInputEditText profileFragmentPassword;
    @BindView(R.id.profile_fragment_confiram_password)
    TextInputEditText profileFragmentConfiramPassword;
    @BindView(R.id.profile_fragment_edit)
    Button profileFragmentEdit;
    Unbinder unbinder;
    private int cities_id;
    private int governorates_id;
    private ApiServices apiServices = getClient().create(ApiServices.class);
    private Integer bloodtype_id;
    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        getProfileData();
        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);

        profileFragmentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.date_of_birth), profileFragmentDateOfBirth, dateModel1);
            }
        });
        profileFragmentLastDateOfDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.last_date_of_donation), profileFragmentLastDateOfDonation, dateModel2);
            }
        });

        return view;
    }


    private void SpinnerBloodType(final Integer id) {

        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                try {

                    if (response.body().getStatus().equals(1)) {

                        final List<BloodTypeData> bloodTypes = response.body().getData();
                        List<String> bloodtypeTxt = new ArrayList<String>();
                        final List<Integer> bloodtypeId = new ArrayList<Integer>();

                        int pos = 0;
                        for (int i = 0; i < bloodTypes.size(); i++) {
                            bloodtypeTxt.add(bloodTypes.get(i).getName());
                            bloodtypeId.add(bloodTypes.get(i).getId());
                            if (bloodTypes.get(i).getId().equals(id)) {
                                pos = i + 1;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, bloodtypeTxt);
                        profileFragmentBloodType.setAdapter(adapter);

                        profileFragmentBloodType.setSelection(pos);

                        profileFragmentBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void getGovernorates(Integer gid, Integer cid) {

        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {
                    int gid = 0;


                    if (response.body().getStatus().equals(1)) {
                        final List<GovernoratesData> governorates = response.body().getData();
                        List<String> governoratTxt = new ArrayList<String>();
                        final List<Integer> governoratId = new ArrayList<Integer>();


                        for (int i = 0; i < governorates.size(); i++) {
                            governoratTxt.add(governorates.get(i).getName());
                            governoratId.add(governorates.get(i).getId());
                            if (governorates.get(i).getId().equals(gid)) {
                                gid = i + 1;
                            }
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, governoratTxt);
                        profileFragmentGovernorates.setAdapter(adapter);
                        profileFragmentGovernorates.setSelection(gid);
                        profileFragmentGovernorates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        apiServices.getCities(id).enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<GovernoratesData> citiesData = response.body().getData();
                        List<String> cityTxt = new ArrayList<String>();
                        final List<Integer> cityid = new ArrayList<Integer>();
                        for (int i = 0; i < citiesData.size(); i++) {
                            cityTxt.add(citiesData.get(i).getName());
                            cityid.add(citiesData.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, cityTxt);
                        profileFragmentCities.setAdapter(adapter);

                        profileFragmentCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    cities_id = cityid.get(i);

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

    private void getProfileData() {
        apiServices.getUserData(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getStatus().equals(1)) {
                    profileFragmentName.setText(response.body().getData().getClient().getName());
                    profileFragmentEmail.setText(response.body().getData().getClient().getEmail());
                    profileFragmentDateOfBirth.setText(response.body().getData().getClient().getBirthDate());
                    profileFragmentBloodType.setPrompt(response.body().getData().getClient().getBloodType().getName());
                    profileFragmentLastDateOfDonation.setText(response.body().getData().getClient().getDonationLastDate());
                    profileFragmentGovernorates.setPrompt(response.body().getData().getClient().getCity().getGovernorate().getName());
                    //profileFragmentCities.setPrompt(response.body().getData().getClient().getCity().getName());
                    profileFragmentPhoneNum.setText(response.body().getData().getClient().getPhone());
                    profileFragmentPassword.setText(LoadData(getActivity(), USER_PASSWORD));
                    profileFragmentConfiramPassword.setText(LoadData(getActivity(), USER_PASSWORD));

                    SpinnerBloodType(response.body().getData().getClient().getBloodType().getId());
                    getGovernorates(response.body().getData().getClient().getCity().getGovernorate().getId()
                            , response.body().getData().getClient().getCity().getId());

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });

    }

    private void editProfileData() {
        String name = profileFragmentName.getText().toString();
        String email = profileFragmentEmail.getText().toString();
        String dataOfBirth = profileFragmentDateOfBirth.getText().toString();
        String lastDateOfDonation = profileFragmentLastDateOfDonation.getText().toString();
        String phoneNum = profileFragmentPhoneNum.getText().toString();
        String password = profileFragmentPassword.getText().toString();
        String confirmPassword = profileFragmentConfiramPassword.getText().toString();
        apiServices.editUserData(name, email, dataOfBirth, cities_id, phoneNum, lastDateOfDonation, password, confirmPassword,
                bloodtype_id, LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(getActivity(), response.body().getMsg() + " ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.profile_fragment_edit)
    public void onViewClicked() {
        editProfileData();
    }


}
