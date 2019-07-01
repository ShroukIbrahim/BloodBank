package bloodbank.android.example.com.bloodbank.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequests;
import bloodbank.android.example.com.bloodbank.data.model.posts.Posts;
import bloodbank.android.example.com.bloodbank.helper.ItemClickListener;

public class DonationRequestAdapter extends RecyclerView.Adapter<DonationRequestViewHolder> {

    private Activity activity;
    private DonationRequests donationRequests;

    public DonationRequestAdapter( Activity activity, DonationRequests donationRequests ) {
        this.activity = activity;
        this.donationRequests = donationRequests;
    }

    @NonNull
    @Override
    public DonationRequestViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_list_donation, parent, false);
        return new DonationRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull DonationRequestViewHolder holder, int position ) {

        holder.fragmentdonationpatientname.setText(donationRequests.getData().getData().get(position).getPatientName());
        holder.fragmentdonationhospitalname.setText(donationRequests.getData().getData().get(position).getHospitalName());
        holder.fragmentdonationcityname.setText(donationRequests.getData().getData().get(position).getCity().getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick( View view, int position, boolean IsLongClick ) {

            }

        });
    }

    @Override
    public int getItemCount() {
        return donationRequests.getData().getData().size();
    }
}

class DonationRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fragmentdonationpatientname;
        TextView fragmentdonationhospitalname;
        TextView fragmentdonationcityname;
        ItemClickListener itemClickListener;
        public DonationRequestViewHolder( View itemView ) {
            super(itemView);
            fragmentdonationpatientname =itemView.findViewById(R.id.fragment_request_donation_patient_name);
            fragmentdonationhospitalname =itemView.findViewById(R.id.fragment_donation_request_hospital_name);
            fragmentdonationcityname =itemView.findViewById(R.id.fragment_donation_request_city_name);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener( ItemClickListener itemClickListener ) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick( View view ) {

        }
    }
