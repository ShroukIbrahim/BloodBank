package bloodbank.android.example.com.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.postfavourite.PostFavourite;
import bloodbank.android.example.com.bloodbank.data.model.posts.PostData;
import bloodbank.android.example.com.bloodbank.data.model.posts.Posts;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.NotifcationFragment;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.PostsDetailsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.SaveData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.setSharedPreferences;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostsViewHolder> {

    private Activity activity;
    List<PostData> posts = new ArrayList<>();
    ApiServices apiServices = getClient().create(ApiServices.class);
    Context context;

    public PostAdapter(Activity activity, List<PostData> posts) {
        this.activity = activity;
        this.posts = posts;
    }


    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_list_post, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsViewHolder holder, final int position) {

        holder.fragmentPostTittle.setText(posts.get(position).getCategory().getName());
        Glide.with(activity).load(posts.get(position).getThumbnailFullPath())
                .into(holder.fragmentPostImagePost);
        final int id= posts.get(position).getId();

        if (posts.get(position).getIsFavourite().equals(true)) {
            holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) activity;
                homeNaigationDrawerActivity.setVisibility(View.VISIBLE);
                PostsDetailsFragment postsDetailsFragment = new PostsDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("getThumbnailFullPath", posts.get(position).getThumbnailFullPath());
                bundle.putBoolean("getIsFavourite", posts.get(position).getIsFavourite());
                bundle.putString("getCategory", posts.get(position).getCategory().getName());
                postsDetailsFragment.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(postsDetailsFragment, manager, R.id.frame2, null, null);
            }
        });

        holder.fragmentPostAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiServices.add_removeFavourit(posts.get(position).getId()
                        , LoadData(activity, USER_API_TOKEN)).enqueue(new Callback<PostFavourite>() {
                    @Override
                    public void onResponse(Call<PostFavourite> call, Response<PostFavourite> response) {
                        if (response.body().getStatus().equals(1)) {

                            posts.get(position).setIsFavourite(!posts.get(position).getIsFavourite());

                            if (posts.get(position).getIsFavourite()) {
                                holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                            } else {
                                holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PostFavourite> call, Throwable t) {

                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.fragment_post_tittle)
        TextView fragmentPostTittle;
        @BindView(R.id.fragment_post_add_favorite)
        ImageView fragmentPostAddFavorite;
        @BindView(R.id.fragment_post_image_post)
        ImageView fragmentPostImagePost;


        public PostsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}