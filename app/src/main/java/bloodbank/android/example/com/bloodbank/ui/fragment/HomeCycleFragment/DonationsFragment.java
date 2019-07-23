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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.DonationRequestAdapter;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypeData;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.createdonationrequest.DonationRequest;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequestData;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequests;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.OnEndless;
import bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger;
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
public class DonationsFragment extends Fragment {


    @BindView(R.id.fragment_donation_list_of_donation_request)
    RecyclerView fragmentDonationListOfDonationRequest;
    Unbinder unbinder;

    ApiServices apiServices;
    DonationRequestAdapter donationRequestAdapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.donation_fragment_filter_spinner_city)
    Spinner donationFragmentFilterSpinnerCity;
    @BindView(R.id.donation_fragment_search_keyword_city)
    TextInputEditText donationFragmentSearchKeywordCity;
    @BindView(R.id.donation_fragment_filter_spinner_bloodtype)
    Spinner donationFragmentFilterSpinnerBloodType;
    @BindView(R.id.donation_fragment_search_keyword_bloodtype)
    TextInputEditText donationFragmentSearchKeywordBloobType;
    @BindView(R.id.donation_fragment_search)
    ImageView donationFragmentSearch;
    private Integer cities_id;
    private String citiestex;
    private Integer bloodtype_id;
    private Integer maxPage;
    private OnEndless onEndless;


    private List<DonationRequestData> donationRequestData = new ArrayList<>();

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
        onEndless = new OnEndless(linearLayoutManager, 10) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page>=0 && maxPage != 0 || current_page == 1) {
                    getAllDonationRequest(current_page);

                }

            }
        };

        fragmentDonationListOfDonationRequest.addOnScrollListener(onEndless);
        donationRequestAdapter = new DonationRequestAdapter(getActivity(), donationRequestData);
        fragmentDonationListOfDonationRequest.setAdapter(donationRequestAdapter);

        getCities();
        SpinnerBloodType();
        getAllDonationRequest(1);
        return view;
    }


    private void SpinnerBloodType() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse( Call<BloodTypes> call, Response<BloodTypes> response ) {
                try {

                    if (response.body().getStatus().equals(1)) {

                        final List<BloodTypeData> bloodTypes = response.body().getData();
                        final List<String> bloodtypeTxt = new ArrayList<String>();
                        final List<Integer> bloodtypeId = new ArrayList<Integer>();

                        bloodtypeTxt.add("Blood Type");
                        bloodtypeId.add(0);

                        for (int i = 0; i < bloodTypes.size(); i++) {
                            bloodtypeTxt.add(bloodTypes.get(i).getName());
                            bloodtypeId.add(bloodTypes.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, bloodtypeTxt);
                        donationFragmentFilterSpinnerBloodType.setAdapter(adapter);
                        donationFragmentFilterSpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                                if (i != 0) {
                                    bloodtype_id = bloodtypeId.get(i);
                                    String BloodtypeTxt = bloodtypeTxt.get(i);
                                    donationFragmentSearchKeywordBloobType.setText(BloodtypeTxt);
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
                        cityid.add(0);
                        cityTxt.add("City");
                        for (int i = 0; i < citiesData.size(); i++) {
                            cityTxt.add(citiesData.get(i).getName());
                            cityid.add(citiesData.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, cityTxt);
                        donationFragmentFilterSpinnerCity.setAdapter(adapter);

                        donationFragmentFilterSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                          public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                                if (i != 0) {

                                    cities_id = cityid.get(i);
                                    citiestex = cityTxt.get(i);
                                    donationFragmentSearchKeywordCity.setText(citiestex);

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
    private void getAllDonationRequest(int page) {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getAllDonation((LoadData(getActivity(),USER_API_TOKEN)),page).enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse( Call<DonationRequests> call, Response<DonationRequests> response ) {
                if (response.body().getStatus().equals(1)) {
                    maxPage= response.body().getData().getCurrentPage();
                    donationRequestData.addAll(response.body().getData().getData());
                    donationRequestAdapter.notifyDataSetChanged();
                    fragmentDonationListOfDonationRequest.setAdapter(donationRequestAdapter);

                }
            }

            @Override
            public void onFailure( Call<DonationRequests> call, Throwable t ) {

            }
        });
    }
    private void DonationFilter(int page) {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getDonationFilter(LoadData(getActivity(),USER_API_TOKEN),bloodtype_id,cities_id).enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse( Call<DonationRequests> call, Response<DonationRequests> response ) {
                if (response.body().getStatus().equals(1)) {
                donationRequestData.clear();
                    donationRequestAdapter.notifyDataSetChanged();

                    donationRequestData.addAll(response.body().getData().getData());
                    donationRequestAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure( Call<DonationRequests> call, Throwable t ) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.donation_fragment_search)
    public void onViewClicked()
    {
        DonationFilter(1);
    }


}
