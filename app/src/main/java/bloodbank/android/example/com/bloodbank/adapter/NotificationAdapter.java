package bloodbank.android.example.com.bloodbank.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.model.notifications.NotificationData;
import bloodbank.android.example.com.bloodbank.data.model.notifications.Notifications;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {



    private Activity activity;
    private List<NotificationData> notifications = new ArrayList<>();

    public NotificationAdapter(Activity activity, List<NotificationData> notifications) {
        this.activity = activity;
        this.notifications = notifications;
    }



    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_notification_list, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
       holder.notificationText.setText(notifications.get(position).getTitle());
       holder.date.setText(notifications.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_text)
        TextView notificationText;
        @BindView(R.id.date)
        TextView date;
        View view;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}