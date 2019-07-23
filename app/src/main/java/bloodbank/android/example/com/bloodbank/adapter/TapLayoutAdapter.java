package bloodbank.android.example.com.bloodbank.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.DonationsFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.PostFragment;



public class TapLayoutAdapter extends FragmentPagerAdapter {

    String[] tabarray = new String[]{"Posts" ,"Donation",};
    Integer tabnumber=2;


    public TapLayoutAdapter( FragmentManager fm ) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle( int position  ) {
        return tabarray [position];
    }


    @Override
    public Fragment getItem( int position)
    {
        switch (position)
        {
            case 0:
                PostFragment post = new PostFragment();
                return  post;
            case 1:
                DonationsFragment Donation = new DonationsFragment();
                return  Donation;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}


