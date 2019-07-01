package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.DonationRequestAdapter;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypeData;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequests;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
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
public class DonationsFragment extends Fragment {


    @BindView(R.id.fragment_donation_list_of_donation_request)
    RecyclerView fragmentDonationListOfDonationRequest;
    Unbinder unbinder;

    ApiServices apiServices;
    DonationRequestAdapter donationRequestAdapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.donation_request_fragment_filter_spinner)
    Spinner donationRequestFragmentFilterSpinner;
    @BindView(R.id.donation_request_fragment_search_keyword)
    TextInputEditText donationRequestFragmentSearchKeyword;
    @BindView(R.id.donation_fragment_filter_spinner)
    Spinner donationFragmentFilterSpinner;
    @BindView(R.id.donation_fragment_search_keyword)
    TextInputEditText donationFragmentSearchKeyword;
    @BindView(R.id.donation_fragment_search)
    ImageButton donationFragmentSearch;
    private Integer governorates_id;
    private String governorattex;
    private Integer cities_id;
    private String citiestex;
    private Integer bloodtype_id;

    public DonationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donations, container, false);
        unbinder = ButterKnife.bind(this, view);
        fragmentDonationListOfDonationRequest.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentDonationListOfDonationRequest.setLayoutManager(linearLayoutManager);
        getAllDonationRequest();
        return view;
    }

    private void getAllDonationRequest() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getAllDonation().enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse( Call<DonationRequests> call, Response<DonationRequests> response ) {
                if (response.body().getStatus().equals(1)) {
                    donationRequestAdapter = new DonationRequestAdapter(getActivity(), response.body());
                    donationRequestAdapter.notifyDataSetChanged();
                    fragmentDonationListOfDonationRequest.setAdapter(donationRequestAdapter);
                }
            }

            @Override
            public void onFailure( Call<DonationRequests> call, Throwable t ) {

            }
        });
    }
    private void SpinnerBloodType() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse( Call<BloodTypes> call, Response<BloodTypes> response ) {
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
                        donationFragmentFilterSpinner.setAdapter(adapter);
                        donationFragmentFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                                if (i != 0) {
                                    bloodtype_id = bloodtypeId.get(i);
                                    }
                            }

                            @Override
                            public void onNothingSelected( AdapterView<?> adapterView ) {

                            }
                        });
                    } else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure( Call<BloodTypes> call, Throwable t ) {

            }
        });

    }

//    private void getGovernorates() {
//        apiServices = getClient().create(ApiServices.class);
//        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
//            @Override
//            public void onResponse( Call<Governorates> call, Response<Governorates> response ) {
//                try {
//
//                    if (response.body().getStatus().equals(1)) {
//                        final List<GovernoratesData> governorates = response.body().getData();
//                        final List<String> governoratTxt = new ArrayList<String>();
//                        final List<Integer> governoratId = new ArrayList<Integer>();
//                        governoratId.add(0);
//                        governoratTxt.add("Governorates");
//
//
//                        for (int i = 0; i < governorates.size(); i++) {
//                            governoratTxt.add(governorates.get(i).getName());
//                            governoratId.add(governorates.get(i).getId());
//                        }
//                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                                android.R.layout.simple_spinner_item, governoratTxt);
//                        donationFragmentFilterSpinner.setAdapter(adapter);
//                        donationFragmentFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
//                                if (i != 0) {
//                                    governorates_id = governoratId.get(i);
//                                    governorattex = governoratTxt.get(i);
//                                    donationFragmentSearchKeyword.setText(governorattex);
//                                    getCities(governoratId.get(i));
//                                     }
//                            }
//
//                            @Override
//                            public void onNothingSelected( AdapterView<?> adapterView ) {
//
//                            }
//                        });
//
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onFailure( Call<Governorates> call, Throwable t ) {
//
//            }
//
//        });
//    }

    private void getCities() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getAllCities().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse( Call<Governorates> call, Response<Governorates> response ) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<GovernoratesData> citiesData = response.body().getData();
                        final List<String> cityTxt = new ArrayList<String>();
                        final List<Integer> cityid = new ArrayList<Integer>();
//                        cityid.add(0);
//                        cityTxt.add("City");
                        for (int i = 0; i < citiesData.size(); i++) {
                            cityTxt.add(citiesData.get(i).getName());
                            cityid.add(citiesData.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, cityTxt);
                        donationRequestFragmentFilterSpinner.setAdapter(adapter);

                        donationRequestFragmentFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                          public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                                if (i != 0) {

                                    cities_id = cityid.get(i);
                                    citiestex = cityTxt.get(i);
                                    donationRequestFragmentSearchKeyword.setText(citiestex);

                                }
                            }

                            @Override
                            public void onNothingSelected( AdapterView<?> adapterView ) {

                            }
                        });
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure( Call<Governorates> call, Throwable t ) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.donation_fragment_search)
    public void onViewClicked() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getDonationFilter(cities_id,bloodtype_id).enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse( Call<DonationRequests> call, Response<DonationRequests> response ) {
                if (response.body().getStatus().equals(1)) {
                    donationRequestAdapter = new DonationRequestAdapter(getActivity(), response.body());
                    donationRequestAdapter.notifyDataSetChanged();
                    fragmentDonationListOfDonationRequest.setAdapter(donationRequestAdapter);
                }
            }

            @Override
            public void onFailure( Call<DonationRequests> call, Throwable t ) {

            }
        });

    }
}
