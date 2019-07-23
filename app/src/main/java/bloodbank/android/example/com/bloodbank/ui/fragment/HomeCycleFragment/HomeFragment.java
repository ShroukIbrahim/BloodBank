package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.TapLayoutAdapter;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.home_navigation_drawer_taplayout)
    TabLayout homeNavigationDrawerTaplayout;
    @BindView(R.id.home_navigation_drawer_viewpager)
    ViewPager homeNavigationDrawerViewpager;
    @BindView(R.id.fragment_post_add_donation)
    ImageButton fragmentPostAddDonation;
    TapLayoutAdapter tapLayoutAdapter;
    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        tapLayoutAdapter = new TapLayoutAdapter(getActivity().getSupportFragmentManager());
        homeNavigationDrawerViewpager.setAdapter(tapLayoutAdapter);
        homeNavigationDrawerTaplayout.setupWithViewPager(homeNavigationDrawerViewpager);
        getActivity().setTitle("");
        HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) getActivity();
        homeNaigationDrawerActivity.setVisibilityicon(View.VISIBLE);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.fragment_post_add_donation)
    public void onViewClicked() {
        HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) getActivity();
        homeNaigationDrawerActivity.setVisibility(View.VISIBLE);
        DonationRequestFragment donationRequestFragment = new DonationRequestFragment();
        HelperMethod.replace(donationRequestFragment, getActivity().getSupportFragmentManager(), R.id.frame2, null, null);

    }


}
