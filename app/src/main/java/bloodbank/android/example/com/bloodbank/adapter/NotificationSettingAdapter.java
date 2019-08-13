package bloodbank.android.example.com.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.load.model.ModelLoader;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;

public class NotificationSettingAdapter extends RecyclerView.Adapter<NotificationSettingAdapter.NotificationViewHolder> {

    private Context context;
    private List<GovernoratesData> governoratesData = new ArrayList<>();
    private List<Integer> governorates_Bloodtype_Ids = new ArrayList<>();
    public List<Integer> newSettingIds = new ArrayList<>();

    public NotificationSettingAdapter(Context context, List<GovernoratesData> governoratesData, List<Integer> governorates_Bloodtype_Ids) {
        this.context = context;
        this.governoratesData = governoratesData;
        this.governorates_Bloodtype_Ids = governorates_Bloodtype_Ids;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_notficationsetting, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        holder.itemCheckBox.setText(governoratesData.get(position).getName());

        for (int i = 0; i < governorates_Bloodtype_Ids.size(); i++) {

            holder.itemCheckBox.setChecked(false);

            if (governorates_Bloodtype_Ids.get(i).equals(governoratesData.get(position).getId())) {
                holder.itemCheckBox.setChecked(true);
                newSettingIds.add(governoratesData.get(position).getId());
                break;
            }
        }

        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    newSettingIds.add(governoratesData.get(position).getId());
                } else {
                    newSettingIds.remove(governoratesData.get(position).getId());
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return governoratesData.size();
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_check_box)
        CheckBox itemCheckBox;
        View view;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}
