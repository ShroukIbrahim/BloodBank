package bloodbank.android.example.com.bloodbank.ui.fragment.SplashCycleFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.SliderAdapter;
import bloodbank.android.example.com.bloodbank.ui.activity.UserCycleActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {

    @BindView(R.id.slider_fragment_btn_skip)
    Button sliderFragmentBtnSkip;
    Unbinder unbinder;
    @BindView(R.id.slider_fragment_viewpager)
    ViewPager sliderFragmentViewpager;
    @BindView(R.id.SliderDots)
    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;


    SliderAdapter sliderAdapter;


    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);
        sliderAdapter = new SliderAdapter(getActivity());
        sliderFragmentViewpager.setAdapter(sliderAdapter);

        dotscount = sliderAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_selected_indicator));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 0, 10, 0);

            SliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_indicator));

        sliderFragmentViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_selected_indicator));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_indicator));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        return view;

}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.slider_fragment_btn_skip)
    public void onViewClicked() {
        sliderFragmentBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skip_but = new Intent(getActivity(), UserCycleActivity.class);
                startActivity(skip_but);
            }
        });

    }
}
