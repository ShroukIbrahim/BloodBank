package bloodbank.android.example.com.bloodbank.data.rest;


import bloodbank.android.example.com.bloodbank.data.model.bloodtypes.BloodTypes;
import bloodbank.android.example.com.bloodbank.data.model.categories.Categories;
import bloodbank.android.example.com.bloodbank.data.model.donationrequests.DonationRequests;
import bloodbank.android.example.com.bloodbank.data.model.governorates.Governorates;
import bloodbank.android.example.com.bloodbank.data.model.login.Login;
import bloodbank.android.example.com.bloodbank.data.model.newpassword.NewPassword;
import bloodbank.android.example.com.bloodbank.data.model.postfavourite.PostFavourite;
import bloodbank.android.example.com.bloodbank.data.model.posts.Category;
import bloodbank.android.example.com.bloodbank.data.model.posts.Posts;
import bloodbank.android.example.com.bloodbank.data.model.register.Register;
import bloodbank.android.example.com.bloodbank.data.model.resetpassword.ResetPassword;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @POST("login")
    @FormUrlEncoded
    Call<Login> onLogin( @Field("phone") String phone, @Field("password") String pass );

    @POST("register")
    @FormUrlEncoded
    Call<Register> onRegister( @Field("name") String name,
                               @Field("email") String email,
                               @Field("birth_date") String birth_date,
                               @Field("city_id") int city_id,
                               @Field("phone") String phone ,
                               @Field("donation_last_date") String donation_last_date,
                               @Field("password") String password,
                               @Field("password_confirmation") String password_confirmation ,
                               @Field("blood_type_id") int blood_type_id );

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> resetPassword( @Field("phone") String phone );

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> newPassword( @Field("password") String password,
                                   @Field("password_confirmation") String confirm_password,
                                   @Field("pin_code") String pin_code,
                                    @Field("phone") String phone);

    @GET("governorates")
    Call<Governorates> getGovernorates();

    @GET("cities")
    Call<Governorates> getCities(@Query("governorate_id") int cities_id);

    @GET("cities")
    Call<Governorates> getAllCities();

    @GET("blood-types")
    Call<BloodTypes> getBloodTypes();

    @GET("categories")
    Call<Categories> getCategories();

    @GET("posts?api_token=Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27&page=1")
    Call<Posts> getAllPost();

    @GET("posts?api_token=Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27&page=1")
    Call<Posts> getFilterPost(@Query("keyword") String keyword,
                              @Query("category_id") int category_id);

    @GET("donation-requests?api_token=W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl&page=1")
    Call<DonationRequests> getAllDonation();

    @GET("donation-requests?api_token=W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl&page=1&")
    Call<DonationRequests> getDonationFilter(@Query("city_id") int city_id ,
                                             @Query("blood_type_id") int blood_type_id );



    @GET("post-toggle-favourite")
    Call<PostFavourite> add_removeFavourit( @Field("post_id") int post_id,
                                            @Field("api_token")String api_token );






//    @GET("notifications-count")
//    Call<Notificationcount> NotificationCount1( @Query("api_token") String api_token );


}
