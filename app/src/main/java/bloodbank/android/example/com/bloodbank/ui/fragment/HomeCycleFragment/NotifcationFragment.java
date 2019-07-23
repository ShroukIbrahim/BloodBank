package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.adapter.NotificationAdapter;
import bloodbank.android.example.com.bloodbank.data.model.notifications.NotificationData;
import bloodbank.android.example.com.bloodbank.data.model.notifications.Notifications;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
import bloodbank.android.example.com.bloodbank.helper.HelperMethod;
import bloodbank.android.example.com.bloodbank.helper.OnEndless;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bloodbank.android.example.com.bloodbank.data.rest.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifcationFragment extends Fragment {


    @BindView(R.id.notification_fragment_list)
    RecyclerView notificationFragmentList;
    Unbinder unbinder;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back_icon)
    RelativeLayout backIcon;
    private LinearLayoutManager layoutManager;
    NotificationAdapter notificationAdapter;
    private List<NotificationData> notificationData = new ArrayList<>();
    private ApiServices apiServices = getClient().create(ApiServices.class);
    private OnEndless onEndless;
    private int maxPage;

    public NotifcationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifcation, container, false);
        unbinder = ButterKnife.bind(this, view);
        tittle.setText("Notification");
//        HomeNaigationDrawerActivity homeNaigationDrawerActivity = (HomeNaigationDrawerActivity) getActivity();
//        homeNaigationDrawerActivity.setVisibilityback(backIcon.VISIBLE);
        notificationFragmentList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        notificationFragmentList.setLayoutManager(layoutManager);
        notificationAdapter = new NotificationAdapter(getActivity(), notificationData);
        onEndless = new OnEndless(layoutManager, 10) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page >= 0 && maxPage != 0 || current_page == 1) {
                    getAllNotification(current_page);

                }

            }
        };

        notificationFragmentList.addOnScrollListener(onEndless);
        notificationFragmentList.setAdapter(notificationAdapter);
        getAllNotification(1);
        return view;
    }

    private void getAllNotification(int page) {
        apiServices.getNotificationsList("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl").enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                if (response.body().getStatus().equals(1)) {
                    maxPage = response.body().getData().getCurrentPage();
                    notificationData.addAll(response.body().getData().getData());
                    notificationAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back_button)
    public void onViewClicked() {
         }
}
