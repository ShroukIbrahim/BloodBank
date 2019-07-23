package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypeData;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.createdonationrequest.CreateDonationRequest;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.ui.activity.MapsActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationRequestFragment extends Fragment {


    @BindView(R.id.request_donation_fragment_name)
    TextInputEditText requestDonationFragmentName;
    @BindView(R.id.request_donation_fragment_age)
    TextInputEditText requestDonationFragmentAge;
    @BindView(R.id.request_donation_fragment_num_of_package)
    Spinner requestDonationFragmentNumOfPackeg;
    @BindView(R.id.request_donation_fragment_hospital_name)
    TextInputEditText requestDonationFragmentHospitalName;
    @BindView(R.id.request_donation_fragment_governorates)
    Spinner requestDonationFragmentGovernorates;
    @BindView(R.id.request_donation_fragment_cities)
    Spinner requestDonationFragmentCities;
    @BindView(R.id.request_donation_fragment_hospiyal_adress)
    TextInputEditText requestDonationFragmentHospiyalAdress;
    @BindView(R.id.request_donation_fragment_open_map)
    ImageView requestDonationFragmentOpenMap;
    @BindView(R.id.request_donation_fragment_phone_number)
    TextInputEditText requestDonationFragmentPhoneNumber;
    @BindView(R.id.request_donation_fragment_nots)
    TextInputEditText requestDonationFragmentNots;
    @BindView(R.id.request_donation_fragment_send)
    Button requestDonationFragmentSend;
    Unbinder unbinder;
    @BindView(R.id.request_donation_fragment_blood_type)
    Spinner requestDonationFragmentBloodType;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back_icon)
    RelativeLayout backIcon;
    @BindView(R.id.tittle)
    TextView tittle;
    private ApiServices apiServices;
    private int bloodtype_id;
    private int governorates_id;
    private int cities_id;
    private int numOfPackage;
    public static double latitude, longitude, latitudePoint, longitudePoint;

    public DonationRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        tittle.setText("Donation Request");
        backButton.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        SpinnerBloodType();
        getGovernorates();
        getNumOfPackage();
        getCities(governorates_id);
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
                        requestDonationFragmentBloodType.setAdapter(adapter);
                        requestDonationFragmentBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    bloodtype_id = bloodtypeId.get(i);
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
                        requestDonationFragmentGovernorates.setAdapter(adapter);
                        requestDonationFragmentGovernorates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void getNumOfPackage(){
        // Spinner Drop down elements
        final List<Integer> NumOfPackage = new ArrayList<Integer>();
        NumOfPackage.add(1);
        NumOfPackage.add(2);
        NumOfPackage.add(3);
        NumOfPackage.add(4);
        NumOfPackage.add(5);
        NumOfPackage.add(6);
        NumOfPackage.add(7);
        NumOfPackage.add(8);
        NumOfPackage.add(9);
        NumOfPackage.add(10);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, NumOfPackage);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestDonationFragmentNumOfPackeg.setPrompt("Number of Package");
       requestDonationFragmentNumOfPackeg.setAdapter(dataAdapter);
       requestDonationFragmentNumOfPackeg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if (i != 0) {
                   numOfPackage = NumOfPackage.get(i);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

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
                        requestDonationFragmentCities.setAdapter(adapter);

                        requestDonationFragmentCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void sendDonation() {
        String UserName = requestDonationFragmentName.getText().toString();
        String Age = requestDonationFragmentAge.getText().toString();
        String HospitalName = requestDonationFragmentHospitalName.getText().toString();
        String HospitalAddress = requestDonationFragmentHospiyalAdress.getText().toString();
        String Phone = requestDonationFragmentPhoneNumber.getText().toString();
        String Note= requestDonationFragmentNots.getText().toString();
        apiServices.createDonationRequest(LoadData(getActivity(), USER_API_TOKEN), UserName, Age, bloodtype_id, numOfPackage, HospitalName
                , HospitalAddress, cities_id, Phone, Note, latitude, longitude).enqueue(new Callback<CreateDonationRequest>() {
            @Override
            public void onResponse(Call<CreateDonationRequest> call, Response<CreateDonationRequest> response) {

                if (response.body().getStatus() == 1) {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CreateDonationRequest> call, Throwable t) {


            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.request_donation_fragment_open_map, R.id.request_donation_fragment_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.request_donation_fragment_open_map:
                Intent openMap = new Intent(getActivity(), MapsActivity.class);
                startActivity(openMap);
//                HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) getActivity();
//                homeNaigationDrawerActivity.setVisibility(View.VISIBLE);
//                CurrentLocationFragment currentLocationFragment = new CurrentLocationFragment();
//                HelperMethod.replace(currentLocationFragment,getActivity().getSupportFragmentManager(),R.id.frame2,null,null);
                break;
            case R.id.request_donation_fragment_send:
                sendDonation();
                break;
        }
    }


    @OnClick(R.id.back_button)
    public void onViewClicked() {

    }
}
