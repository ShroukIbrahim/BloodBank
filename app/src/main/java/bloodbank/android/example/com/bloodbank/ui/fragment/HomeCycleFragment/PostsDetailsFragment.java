package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.post.Post;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
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
public class PostsDetailsFragment extends Fragment {


    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.count_num)
    TextView countNum;
    @BindView(R.id.notf_icon)
    RelativeLayout notfIcon;
    @BindView(R.id.posts_details_fragment_imagepost)
    ImageView postsDetailsFragmentImagepost;
    @BindView(R.id.posts_details_fragment_tittle)
    TextView postsDetailsFragmentTittle;
    @BindView(R.id.posts_details_fragment_add_favorite)
    ImageView postsDetailsFragmentAddFavorite;
    @BindView(R.id.posts_details_fragment_details)
    TextView postsDetailsFragmentDetails;
    Unbinder unbinder;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back_icon)
    RelativeLayout backIcon;
    @BindView(R.id.tittle)
    TextView tittle;
    private int id;
    private String postImage, Tittle;
    private boolean IsFavourite;

    public PostsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            postImage = bundle.getString("getThumbnailFullPath");
            IsFavourite = bundle.getBoolean("getIsFavourite");
            Tittle = bundle.getString("getCategory");

        }

        postsDetailsFragmentTittle.setText(Tittle);
        tittle.setText(Tittle);
        Glide.with(getActivity()).load(postImage)
                .into(postsDetailsFragmentImagepost);

        if (IsFavourite) {
            postsDetailsFragmentAddFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            postsDetailsFragmentAddFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.notification_icon, R.id.posts_details_fragment_add_favorite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_icon:
                NotifcationFragment notifcationFragment = new NotifcationFragment();
                HelperMethod.replace(notifcationFragment, getActivity().getSupportFragmentManager(), R.id.frame2, null, null);

                break;
            case R.id.posts_details_fragment_add_favorite:
                break;
        }
    }

    @OnClick(R.id.back_button)
    public void onViewClicked() {
    }
}
