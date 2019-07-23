package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ResourceBundle;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.donationrequest.DonationRequestt;
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
import static bloodbank.android.example.com.bloodbank.helper.HelperMethod.makePhoneCall;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationDetailsFragment extends Fragment implements OnMapReadyCallback {


    @BindView(R.id.donation_details_fragment_name)
    TextView donationDetailsFragmentName;
    @BindView(R.id.donation_details_fragment_age)
    TextView donationDetailsFragmentAge;
    @BindView(R.id.donation_details_fragment_bloodtype)
    TextView donationDetailsFragmentBloodtype;
    @BindView(R.id.donation_details_fragment_no_of_package)
    TextView donationDetailsFragmentNoOfPackage;
    @BindView(R.id.donation_details_fragment_name_of_hospita1)
    TextView donationDetailsFragmentNameOfHospita1;
    @BindView(R.id.donation_details_fragment_address_of_hospital)
    TextView donationDetailsFragmentAddressOfHospital;
    @BindView(R.id.donation_details_fragment_phone_number)
    TextView donationDetailsFragmentPhoneNumber;
    @BindView(R.id.donation_details_fragment_datalis)
    TextView donationDetailsFragmentDatalis;
    @BindView(R.id.donation_details_fragment_map)
    MapView donationDetailsFragmentMap;
    @BindView(R.id.donation_details_fragment_call)
    Button donationDetailsFragmentCall;
    Unbinder unbinder;
    Bundle bundle;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tittle)
    TextView tittle;
    private int id;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    Double longitude = null;
    Double latiude = null;
    private String savePhone;
    private static final int REQUEST_CALL = 1;


    // ApiServices apiServices =getClient().create(ApiServices.class);

    public DonationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              final  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        backButton.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        bundle = getArguments();
        if ((bundle != null)) {
            id = bundle.getInt("id");
            Toast.makeText(getActivity(), id + "", Toast.LENGTH_SHORT).show();
            getDonationDetails();
        }
        return view;
    }

    private void getDonationDetails() {
        ApiServices apiServices = getClient().create(ApiServices.class);
        apiServices.getDonationDetails(LoadData(getActivity(), USER_API_TOKEN), id).enqueue(new Callback<DonationRequestt>() {
            @Override
            public void onResponse(Call<DonationRequestt> call, Response<DonationRequestt> response) {
                if (response.body().getStatus().equals(1)) {
                    tittle.setText("Donation Request of: "+response.body().getData().getPatientName());
                    donationDetailsFragmentName.setText(response.body().getData().getPatientName());
                    donationDetailsFragmentAge.setText(response.body().getData().getPatientAge());
                    donationDetailsFragmentBloodtype.setText(response.body().getData().getBloodType().getName());
                    donationDetailsFragmentAddressOfHospital.setText(response.body().getData().getHospitalAddress());
                    donationDetailsFragmentNameOfHospita1.setText(response.body().getData().getHospitalName());
                    donationDetailsFragmentNoOfPackage.setText(response.body().getData().getBagsNum());
                    donationDetailsFragmentPhoneNumber.setText(response.body().getData().getPhone());
                    savePhone = response.body().getData().getPhone();
                    //donationDetailsFragmentPhoneNumber.append(savePhone);
                    latiude = Double.parseDouble(response.body().getData().getLatitude());
                    longitude = Double.parseDouble(response.body().getData().getLongitude());
                    Bundle mapViewBundle = null;
                    donationDetailsFragmentMap.onCreate(mapViewBundle);
                    donationDetailsFragmentMap.getMapAsync(DonationDetailsFragment.this);
                    donationDetailsFragmentMap.onStart();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<DonationRequestt> call, Throwable t) {

            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        donationDetailsFragmentMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.donation_details_fragment_call)
    public void onViewClicked() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            makePhoneCall(getActivity(), savePhone);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        if (longitude != null || latiude != null) {
            LatLng latLng = new LatLng(longitude, latiude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } else {
            Toast.makeText(getActivity()," error", Toast.LENGTH_SHORT).show();
        }

    }

}
