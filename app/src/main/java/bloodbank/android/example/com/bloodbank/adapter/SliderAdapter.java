package bloodbank.android.example.com.bloodbank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bloodbank.android.example.com.bloodbank.R;
import butterknife.BindView;

public class SliderAdapter extends PagerAdapter {


    private Context context;
    private LayoutInflater layoutInflater;
    private int[] sliderImage = {R.drawable.slider1,R.drawable.slider2};

    public SliderAdapter( Context context )

    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderImage.length;
    }

    @Override
    public boolean isViewFromObject( View view, Object object ) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem( ViewGroup container, final int position ) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_of_slider, container, false);
        ImageView sliderimage = (ImageView) view.findViewById(R.id.sliderimage);
        sliderimage.setImageResource(sliderImage[position]);
        ViewPager viewPager =(ViewPager) container;
        viewPager.addView(view,0);

        //container.addView(view);
        return view;

    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object ) {

        ViewPager viewPager =(ViewPager) container;
        View view =(View) object;
        viewPager.removeView(view);

        //container.removeView((LinearLayout) object);
    }
}


