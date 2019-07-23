package bloodbank.android.example.com.bloodbank.ui.fragment.HomeCycleFragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.data.model.contact.Contact;
import bloodbank.android.example.com.bloodbank.data.model.settings.Settings;
import bloodbank.android.example.com.bloodbank.data.rest.ApiServices;
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
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_EMAIL;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_NAME;
import static bloodbank.android.example.com.bloodbank.helper.SharedPreferencesManger.USER_PHONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectUsFragment extends Fragment {


    @BindView(R.id.facebook)
    ImageView facebook;
    @BindView(R.id.twitter)
    ImageView twitter;
    @BindView(R.id.youtube)
    ImageView youtube;
    @BindView(R.id.instagram)
    ImageView instagram;
    @BindView(R.id.whatsapp)
    ImageView whatsapp;
    @BindView(R.id.google)
    ImageView google;
    @BindView(R.id.connectus)
    Button connectus;
    @BindView(R.id.connectus_fragment_name)
    TextInputEditText connectusFragmentName;
    @BindView(R.id.connectus_fragment_email)
    TextInputEditText connectusFragmentEmail;
    @BindView(R.id.connectus_fragment_phonenum)
    TextInputEditText connectusFragmentPhonenum;
    @BindView(R.id.connectus_fragment_tittle)
    TextInputEditText connectusFragmentTittle;
    @BindView(R.id.connectus_fragment_massage)
    TextInputEditText connectusFragmentMassage;
    @BindView(R.id.connectus_fragment_send)
    Button connectusFragmentSend;
    Unbinder unbinder;
    ApiServices apiServices = getClient().create(ApiServices.class);
    @BindView(R.id.connectus_fragment_tphonenum)
    TextView connectusFragmentTphonenum;
    @BindView(R.id.connectus_fragment_temail)
    TextView connectusFragmentTemail;

    public ConnectUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle("Connect Us");
        connectusFragmentName.setText(LoadData(getActivity(), USER_NAME));
        connectusFragmentEmail.setText(LoadData(getActivity(), USER_EMAIL));
        connectusFragmentPhonenum.setText(LoadData(getActivity(), USER_PHONE));
        getData();
        return view;
    }

    private void getData() {
        apiServices.getSetting(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.body().getStatus().equals(1)) {
                    connectusFragmentTphonenum.setText(response.body().getData().getPhone());
                    connectusFragmentTemail.setText(response.body().getData().getEmail());

                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {

            }
        });
    }

    private void contactMethod() {

        String tittle = connectusFragmentTittle.getText().toString();
        String massage = connectusFragmentMassage.getText().toString();
        apiServices.contact(LoadData(getActivity(), USER_API_TOKEN), tittle, massage).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @OnClick({R.id.facebook, R.id.twitter, R.id.youtube, R.id.instagram, R.id.whatsapp, R.id.google, R.id.connectus, R.id.connectus_fragment_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.facebook:
                apiServices.getSetting(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Settings>() {

                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        if (response.body().getStatus().equals(1)) {
                            String Facebook = response.body().getData().getFacebookUrl();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Facebook));
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {

                    }
                });
            break;
            case R.id.twitter:
                apiServices.getSetting(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Settings>() {

                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        if (response.body().getStatus().equals(1)) {
                            String Twitter = response.body().getData().getTwitterUrl();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Twitter));
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {

                    }
                });
                break;
            case R.id.youtube:
                apiServices.getSetting(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Settings>() {

                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        if (response.body().getStatus().equals(1)) {
                            String Youtube = response.body().getData().getYoutubeUrl();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Youtube));
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {

                    }
                });
                break;
            case R.id.instagram:
                apiServices.getSetting(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Settings>() {

                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        if (response.body().getStatus().equals(1)) {
                            String Instagram = response.body().getData().getInstagramUrl();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Instagram));
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {

                    }
                });
                break;
            case R.id.whatsapp:
                apiServices.getSetting(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Settings>() {

                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        if (response.body().getStatus().equals(1)) {
                            String Whatsapp = response.body().getData().getWhatsapp();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Whatsapp));
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {

                    }
                });
                break;
            case R.id.google:
                apiServices.getSetting(LoadData(getActivity(),USER_API_TOKEN)).enqueue(new Callback<Settings>() {

                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        if (response.body().getStatus().equals(1)) {
                            String Google = response.body().getData().getGoogleUrl();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Google));
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {

                    }
                });
                break;
            case R.id.connectus:
                break;
            case R.id.connectus_fragment_send:
                contactMethod();

                break;
        }
    }


}
