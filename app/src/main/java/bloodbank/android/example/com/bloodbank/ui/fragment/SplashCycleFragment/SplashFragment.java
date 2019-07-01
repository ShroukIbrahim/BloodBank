package bloodbank.android.example.com.bloodbank.ui.fragment.SplashCycleFragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    private static final long SPLASH_DISPLAY_LENGTH = 2000;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

       //handler after postdelay to run method
        new Handler().postDelayed(new Runnable(){



            @Override
            public void run() {
                SliderFragment sliderFragment = new SliderFragment();
                HelperMethod.replace(sliderFragment,getActivity().getSupportFragmentManager(),R.id.splash_activity_splashscreen,null,null);
            }
        }, SPLASH_DISPLAY_LENGTH);
        return view;

    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


}
