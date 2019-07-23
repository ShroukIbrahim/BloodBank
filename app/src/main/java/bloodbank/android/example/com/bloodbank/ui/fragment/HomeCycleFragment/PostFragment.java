package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.load.model.ModelLoader;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.PostAdapter;
import bloodbank.android.example.com.bloodbank.data.model.categories.Categories;
import bloodbank.android.example.com.bloodbank.data.model.categories.CategoriesData;
import bloodbank.android.example.com.bloodbank.data.model.postfavourite.PostFavourite;
import bloodbank.android.example.com.bloodbank.data.model.posts.PostData;
import bloodbank.android.example.com.bloodbank.data.model.posts.Posts;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.OnEndless;
import bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger;
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
public class PostFragment extends Fragment {


    @BindView(R.id.post_fragment_list_of_post)
    RecyclerView fragmentPostListofpost;
    Unbinder unbinder;
    ApiServices apiServices;
    PostAdapter postAdapter;
    @BindView(R.id.post_fragment_filter_spinner)
    Spinner postFragmentFilterSpinner;
    @BindView(R.id.post_fragment_search_keyword)
    TextInputEditText postFragmentSearchKeyword;
    @BindView(R.id.post_fragment_search)
    ImageButton postFragmentSearch;
    private LinearLayoutManager layoutManager;
    private int categories_id;
    SharedPreferencesManger sharedPreferencesManger;



    private OnEndless onEndless;
    private int maxPage;
    private List<PostData> postData = new ArrayList<>();
    private Posts postsList;


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        fragmentPostListofpost.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        fragmentPostListofpost.setLayoutManager(layoutManager);
        onEndless = new OnEndless(layoutManager, 10) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page>=0 && maxPage != 0 || current_page == 1) {
                    getAllPosts(current_page);
                    postFilter(current_page);
                }

            }
        };
        
        fragmentPostListofpost.addOnScrollListener(onEndless);
        postAdapter = new PostAdapter(getActivity(), postData);
        fragmentPostListofpost.setAdapter(postAdapter);
        getCategory();
        getAllPosts(1);
        return view;
    }



    private void getCategory() {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse( Call<Categories> call, Response<Categories> response ) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CategoriesData> categories = response.body().getData();
                        List<String> listSpinner = new ArrayList<String>();
                        final List<Integer> id = new ArrayList<Integer>();
                        listSpinner.add("Categories");
                        id.add(0);
                        for (int i = 0; i < categories.size(); i++) {
                            id.add(categories.get(i).getId());
                            listSpinner.add(categories.get(i).getName());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, listSpinner);
                        postFragmentFilterSpinner.setAdapter(adapter);
                        postFragmentFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                                if (i != 0) {
                                    categories_id =id.get(i);

                                }
                            }

                            @Override
                            public void onNothingSelected( AdapterView<?> adapterView ) {

                            }
                        });

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure( Call<Categories> call, Throwable t ) {

            }

        });
    }
    private void getAllPosts(int page) {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getAllPost(page,LoadData(getActivity(),USER_API_TOKEN) ).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse( Call<Posts> call, Response<Posts> response ) {
                if (response.body().getStatus().equals(1)) {
                    maxPage= response.body().getData().getCurrentPage();
                    postData.addAll(response.body().getData().getData());
                    postAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure( Call<Posts> call, Throwable t ) {

            }
        });
    }
    private void postFilter(int page) {
        apiServices = getClient().create(ApiServices.class);
        apiServices.getFilterPost(LoadData(getActivity(),USER_API_TOKEN), postFragmentSearchKeyword.getText().toString(), categories_id).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse( Call<Posts> call, Response<Posts> response ) {
                if (response.body().getStatus().equals(1)) {

                    postData.clear();
                    postAdapter.notifyDataSetChanged();

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




    @OnClick(R.id.post_fragment_search)
    public void onViewClicked() {
        postFilter(1);

    }
}
