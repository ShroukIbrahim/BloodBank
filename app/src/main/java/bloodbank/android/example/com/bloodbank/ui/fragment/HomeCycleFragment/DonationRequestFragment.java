package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bloodbank.android.example.com.bloodbank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationRequestFragment extends Fragment {


    public DonationRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donation_request, container, false);
    }

}
