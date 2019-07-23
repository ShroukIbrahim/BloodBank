package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.PostAdapter;
import bloodbank.android.example.com.bloodbank.data.model.categories.Categories;
import bloodbank.android.example.com.bloodbank.data.model.categories.CategoriesData;
import bloodbank.android.example.com.bloodbank.data.model.posts.PostData;
import bloodbank.android.example.com.bloodbank.data.model.posts.Posts;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.OnEndless;
import bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger;
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
public class FavouriteFragment extends Fragment {


    @BindView(R.id.favourite_fragment_list)
    RecyclerView favouriteFragmentList;
    ApiServices apiServices;
    PostAdapter postAdapter;
    private LinearLayoutManager layoutManager;
    private List<PostData> postData = new ArrayList<>();


    Unbinder unbinder;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle("Favourite");
        favouriteFragmentList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        favouriteFragmentList.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(getActivity(), postData);
        favouriteFragmentList.setAdapter(postAdapter);
        HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) getActivity();
        homeNaigationDrawerActivity.setVisibilityicon(View.VISIBLE);
        getFavouritePost();
        return view;
    }

    private void getFavouritePost() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getFavouritePost(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response ) {

                if (response.body().getStatus().equals(1)) {
                    postData.addAll(response.body().getData().getData());
                    postAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure( Call<Posts> call, Throwable t ) {

            }
        });
    }

    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
