package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.NotificationSettingAdapter;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypeData;
import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.governorates.GovernoratesData;
import bloodbank.android.example.com.bloodbank.data.model.notificationssettings.NotificationsSettings;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.LoadData;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifcationSettingFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.count_num)
    TextView countNum;
    @BindView(R.id.notification_setting_blodtype)
    RecyclerView notificationSettingBlodtype;
    @BindView(R.id.notification_setting_gonvertment)
    RecyclerView notificationSettingGonvertment;
    @BindView(R.id.notification_setting_save)
    Button notificationSettingSave;
    private View view;
    private ApiServices apiServices;

    private NotificationSettingAdapter governoratsAdapter;
    private NotificationSettingAdapter bloodTypeAdapter;

    private List<GovernoratesData> governortareList = new ArrayList<>();
    private List<BloodTypeData> bloodTypeList = new ArrayList<>();

    private List<NotificationsSettings> governorates_Bloodtype_Ids = new ArrayList<>();

    private List<Integer> bloodType_id;
    private List<Integer> governorates_id;


    private LinearLayoutManager layoutManager, layoutManager2;
    NotificationSettingAdapter notificationSettingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifcation_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClient().create(ApiServices.class);

        notificationSettingBlodtype.setHasFixedSize(true);
        notificationSettingGonvertment.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager2 = new LinearLayoutManager(getActivity());
        notificationSettingAdapter = new NotificationSettingAdapter(getActivity(), governortareList, bloodType_id);

        notificationSettingBlodtype.setLayoutManager(layoutManager);
        notificationSettingGonvertment.setLayoutManager(layoutManager2);

        notificationSettingGonvertment.setAdapter(notificationSettingAdapter);
        notificationSettingBlodtype.setAdapter(notificationSettingAdapter);

        governortareList = new ArrayList<>();

        bloodType_id = new ArrayList<>();
        governorates_id = new ArrayList<>();

        getDataBloodTypeAndGovernorates();

        return view;
    }

    public void getDataBloodTypeAndGovernorates() {

        apiServices.getNotificationsSettings(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<NotificationsSettings>() {
            @Override
            public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {

                Log.i(TAG, "onFailure: ");
                try {
                    if (response.body().getStatus() == 1) {
                        for (int i = 0; i < response.body().getData().getBloodTypes().size(); i++) {
                            bloodType_id.add(Integer.valueOf(response.body().getData().getBloodTypes().get(i)));
                        }
                        for (int i = 0; i < response.body().getData().getGovernorates().size(); i++) {
                            governorates_id.add(Integer.valueOf(response.body().getData().getGovernorates().get(i)));
                        }

                        BloodTypes();
                        Governorate();
                    }else {

                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<NotificationsSettings> call, Throwable t) {

                Log.i(TAG, "onFailure: ");

            }
        });

    }

    private void BloodTypes() {
        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                try {
                    Log.i(TAG, "onFailure: ");
                    if (response.body().getStatus().equals(1)) {
                        bloodTypeList.addAll(response.body().getData());
                        notificationSettingAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {

            }
        });

    }

    private void Governorate() {
        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {
                    Log.i(TAG, "onFailure: ");
                    if (response.body().getStatus().equals(1)) {
                        governortareList.addAll(response.body().getData());
                        notificationSettingAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.notification_setting_save)
    public void onViewClicked() {

        apiServices.ChangeNotificationsSettings(LoadData(getActivity(), USER_API_TOKEN)
                , governoratsAdapter.newSettingIds, bloodTypeAdapter.newSettingIds)
                .enqueue(new Callback<NotificationsSettings>() {
                    @Override
                    public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {
                        try {
                            if (response.body().getStatus().equals(1)) {
                                Toast.makeText(getActivity(), "Msg" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Status" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }

                    }

                    @Override
                    public void onFailure(Call<NotificationsSettings> call, Throwable t) {
                        Log.d("responsess", t.getMessage());
                    }
                });
    }
}
