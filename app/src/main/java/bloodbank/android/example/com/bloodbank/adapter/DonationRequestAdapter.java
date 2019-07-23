package bloodbank.android.example.com.bloodbank.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequestData;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequests;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
import bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment.DonationDetailsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;

public class DonationRequestAdapter extends RecyclerView.Adapter<DonationRequestAdapter.DonationRequestViewHolder> {


    private Activity activity;
    private List<DonationRequestData> donationRequestDataList = new ArrayList<>();
    private  ApiServices apiServices = getClient().create(ApiServices.class);

    public DonationRequestAdapter(Activity activity, List<DonationRequestData> donationRequestDataList) {
        this.activity = activity;
        this.donationRequestDataList = donationRequestDataList;
    }

    @NonNull
    @Override
    public DonationRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_list_donation, parent, false);
        return new DonationRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationRequestViewHolder holder, final int position) {
        holder.fragmentRequestDonationBloodtype.setText(donationRequestDataList.get(position).getBloodType().getName());
        holder.fragmentRequestDonationPatientName.setText(donationRequestDataList.get(position).getPatientName());
        holder.fragmentDonationRequestHospitalName.setText(donationRequestDataList.get(position).getHospitalName());
        holder.fragmentDonationRequestCityName.setText(donationRequestDataList.get(position).getCity().getName());
        final int id = donationRequestDataList.get(position).getId();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) activity;
                homeNaigationDrawerActivity.setVisibility(View.VISIBLE);
                DonationDetailsFragment donationDetailsFragment = new DonationDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",id);
                donationDetailsFragment.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(donationDetailsFragment, manager, R.id.frame2, null, null);



            }
        });
    }

    @Override
    public int getItemCount() {
        return donationRequestDataList.size();
    }

    @OnClick({R.id.fragment_donation_request_details, R.id.fragment_donation_request_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_donation_request_details:

                break;
            case R.id.fragment_donation_request_call:
                break;
        }
    }


    public class DonationRequestViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_request_donation_bloodtype)
        TextView fragmentRequestDonationBloodtype;
        @BindView(R.id.fragment_request_donation_patient_name)
        TextView fragmentRequestDonationPatientName;
        @BindView(R.id.fragment_donation_request_hospital_name)
        TextView fragmentDonationRequestHospitalName;
        @BindView(R.id.fragment_donation_request_city_name)
        TextView fragmentDonationRequestCityName;
        @BindView(R.id.fragment_donation_request_details)
        Button fragmentDonationRequestDetails;
        @BindView(R.id.fragment_donation_request_call)
        Button fragmentDonationRequestCall;
        View view;

        public DonationRequestViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}