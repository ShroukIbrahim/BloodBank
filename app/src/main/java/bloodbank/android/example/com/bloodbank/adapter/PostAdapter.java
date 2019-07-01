package bloodbank.android.example.com.bloodbank.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.postfavourite.PostFavourite;
import bloodbank.android.example.com.bloodbank.data.model.posts.Posts;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostsViewHolder> {

    private Activity activity;
    private Posts posts;
    ApiServices apiServices = getClient().create(ApiServices.class);

    public PostAdapter( Activity activity, Posts posts ) {
        this.activity = activity;
        this.posts = posts;


    }


    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_list_post, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull final PostsViewHolder holder, final int position ) {

        holder.fragmentPostTittle.setText(posts.getData().getData().get(position).getTitle());
        Picasso.with(activity).load(posts.getData().getData().get(position).getThumbnailFullPath())
                .into(holder.fragmentPostImagePost);

        if (posts.getData().getData().get(position).getIsFavourite()) {
            holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_call_black_24dp);
        } else {
            holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {


            }
        });

        holder.fragmentPostAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                apiServices.add_removeFavourit(posts.getData().getData().get(position).getId()
                        , posts.getData().getData().get(position).getCategoryId()).enqueue(new Callback<PostFavourite>() {
                    @Override
                    public void onResponse( Call<PostFavourite> call, Response<PostFavourite> response ) {
                        if (response.body().getStatus().equals(1)) {

                            posts.getData().getData().get(position)
                                    .setIsFavourite(!posts.getData().getData().get(position).getIsFavourite());

                            if (posts.getData().getData().get(position).getIsFavourite()) {
                                holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_call_black_24dp);
                            } else {
                                holder.fragmentPostAddFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            }
                        }


                    }

                    @Override
                    public void onFailure( Call<PostFavourite> call, Throwable t ) {

                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.getData().getData().size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.fragment_post_tittle)
        TextView fragmentPostTittle;
        @BindView(R.id.fragment_post_add_favorite)
        ImageView fragmentPostAddFavorite;
        @BindView(R.id.fragment_post_image_post)
        ImageView fragmentPostImagePost;
        View view;

        public PostsViewHolder( View itemView ) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}