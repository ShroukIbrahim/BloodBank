//package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;
//
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import bloodbank.android.example.com.bloodbank.R;
//import bloodbank.android.example.com.bloodbank.helper.GPSTracker;
//import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//
//import static android.content.Context.LOCATION_SERVICE;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class CurrentLocationFragment extends Fragment implements OnMapReadyCallback {
//
//
//    @BindView(R.id.map)
//    MapView map;
//    Unbinder unbinder;
//    @BindView(R.id.current_location)
//    Button currentLocation;
//    private GoogleMap mgoogleMap;
//    private LatLng myPosition;
//
//    public CurrentLocationFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_current_location, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        if (map != null) {
//            map.onCreate(null);
//            map.onResume();
//            map.getMapAsync(this);
//        }
//    }
//
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        MapsInitializer.initialize(getActivity());
////        googleMap.setMyLocationEnabled(true);
////        googleMap.addMarker(new MarkerOptions().position(myPosition).title("Marker"));
////        new GPSTracker(getContext(),getActivity());
////
////
////
////         mgoogleMap = googleMap;
////         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
////         googleMap.addMarker(new MarkerOptions().position(new LatLng(1,1)).title("").snippet(""));
////        CameraPosition Library = CameraPosition.builder().target(new LatLng(1,1)).zoom(16).bearing(0).tilt(45).build();
////        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Library));
////
////    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @OnClick(R.id.current_location)
//    public void onViewClicked() {
////        Criteria criteria = new Criteria();
////        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
////        String provider = locationManager.getBestProvider(criteria, true);
////        Location location = locationManager.getLastKnownLocation(provider);
////        double latitude = location.getLatitude();
////        double longitude = location.getLongitude();
////        myPosition = new LatLng(latitude, longitude);
//    }
//}
