package com.example.sofra.data.api;


import com.example.sofra.data.model.contactUs.ContactUs;
import com.example.sofra.data.model.listOfTown.GeneralResponse;
import com.example.sofra.data.model.listRestaurantItem.FoodItems;
import com.example.sofra.data.model.restaurantAddMenuItem.RestaurantAddMenuItem;
import com.example.sofra.data.model.restaurantAddNewCategory.RestaurantAddNewCategory;
import com.example.sofra.data.model.restaurantAddOffer.RestaurantAddOffer;
import com.example.sofra.data.model.restaurantCategory.CategoriesNotPaginated;
import com.example.sofra.data.model.restaurantCategory.CategoriesPaginated;
import com.example.sofra.data.model.restaurantChangePassword.RestaurantChangePassword;
import com.example.sofra.data.model.restaurantChangeState.RestaurantChangeState;
import com.example.sofra.data.model.restaurantCommission.RestaurantCommission;
import com.example.sofra.data.model.restaurantDeleteCategory.RestaurantDeleteCategory;
import com.example.sofra.data.model.restaurantDeleteMenuItem.RestaurantDeleteMenuItem;
import com.example.sofra.data.model.restaurantDeleteOffer.RestaurantDeleteOffer;
import com.example.sofra.data.model.restaurantEditMenuItem.RestaurantEditMenuItem;
import com.example.sofra.data.model.restaurantEditOffer.RestaurantEditOffer;
import com.example.sofra.data.model.restaurantEditProfile.RestaurantEditProfile;
import com.example.sofra.data.model.restaurantList.RestaurantList;
import com.example.sofra.data.model.restaurantListWithFilter.RestaurantListWithFilter;
import com.example.sofra.data.model.restaurantLogin.AuthRestaurant;
import com.example.sofra.data.model.restaurantOffer.RestaurantOffer;
import com.example.sofra.data.model.restaurantRegister.RestaurantRegister;
import com.example.sofra.data.model.restaurantUpdateCategory.RestaurantUpdateCategory;
import com.example.sofra.data.model.userAddReview.UserAddReview;
import com.example.sofra.data.model.userDeclineOrder.UserDeclineOrder;
import com.example.sofra.data.model.userDetailsOffer.UserDetailsOffer;
import com.example.sofra.data.model.userLogin.UserLogin;
import com.example.sofra.data.model.userNewOrder.UserNewOrder;
import com.example.sofra.data.model.userNewPassword.UserNewPassword;
import com.example.sofra.data.model.userOffer.UserOffer;
import com.example.sofra.data.model.userOrders.UserOrders;
import com.example.sofra.data.model.userRegister.UserRegister;
import com.example.sofra.data.model.userResetPassword.UserResetPassword;
import com.example.sofra.data.model.userRestaurantReview.UserRestaurantReview;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cities-not-paginated")
    Call<GeneralResponse> getCity();

    @GET("regions-not-paginated")
    Call<GeneralResponse> getTown(@Query("city_id") int cityId);

    @GET("categories")
    Call<CategoriesNotPaginated> getUserCategory(@Query("restaurant_id") int restaurantId);

    @GET("restaurant/my-categories")
    Call<CategoriesPaginated> getRestaurantCategory(@Query("api_token") String apiToken,
                                                    @Query("page") int page);

    @GET("client/my-orders")
    Call<UserOrders> getUserOrders(@Query("api_token") String apiToken,
                                   @Query("state") String state,
                                   @Query("page") int page);

    @GET("restaurant/my-orders")
    Call<UserOrders> getRestaurantOrders(@Query("api_token") String apiToken,
                                         @Query("state") String state,
                                         @Query("page") int page);

    @GET("items")
    Call<FoodItems> getRestaurantItem(@Query("restaurant_id") int restaurantId,
                                      @Query("category_id") int categoryId,
                                      @Query("page") int page);

    @GET("restaurants")
    Call<RestaurantList> getRestaurant(@Query("page") int page);

    @GET("restaurant/reviews")
    Call<UserRestaurantReview> getUserRestaurantReview(@Query("api_token") String apiToken,
                                                       @Query("restaurant_id") int restaurantId,
                                                       @Query("page") int page);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<AuthRestaurant> getRestaurantLogin(@Field("email") String emial,
                                            @Field("password") String password);

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> getContactUs(@Field("name") String name,
                                 @Field("phone") String phone,
                                 @Field("email") String email,
                                 @Field("type") String type,
                                 @Field("content") String content);

    @GET("restaurant/commissions")
    Call<RestaurantCommission> getRestaurantCommission(@Query("api_token") String apiToken);


    @GET("restaurant/my-offers")
    Call<RestaurantOffer> getRestaurantOffer(@Query("api_token") String apiToken,
                                             @Query("page") int page);

    @GET("offers")
    Call<UserOffer> getUserOffer(@Query("page") int page);

    @GET("offer")
    Call<UserDetailsOffer> getUserOfferDetails(@Query("offer_id") int offerId);

    @GET("restaurant/my-items")
    Call<FoodItems> getRestaurantMenu(@Query("api_token") String apiToken,
                                      @Query("category_id") int categoryId,
                                      @Query("page") int page);

    @GET("restaurants")
    Call<RestaurantListWithFilter> getRestaurantListWithFilter(@Query("keyword") String keyword,
                                                               @Query("region_id") int regionId);


    @POST("restaurant/sign-up")
    @Multipart
    Call<RestaurantRegister> getRestaurantRegister(@Part("name") RequestBody name,
                                                   @Part("email") RequestBody email,
                                                   @Part("password") RequestBody password,
                                                   @Part("password_confirmation") RequestBody passwordConfirmation,
                                                   @Part("phone") RequestBody phone,
                                                   @Part("whatsapp") RequestBody whatsapp,
                                                   @Part("region_id") RequestBody regionId,
                                                   @Part("delivery_cost") RequestBody deliveryCost,
                                                   @Part("minimum_charger") RequestBody minimumCharger,
                                                   @Part("photo") MultipartBody.Part photo,
                                                   @Part("delivery_time") RequestBody deliveryTime);

    @POST("restaurant/sign-up")
    @Multipart
    Call<RestaurantEditProfile> getRestaurantEditProfile(@Part("email") RequestBody email,
                                                         @Part("name") RequestBody name,
                                                         @Part("phone") RequestBody phone,
                                                         @Part("region_id") RequestBody regionId,
                                                         @Part("delivery_cost") RequestBody deliveryCost,
                                                         @Part("minimum_charger") RequestBody minimumCharger,
                                                         @Part("availability") RequestBody availability,
                                                         @Part MultipartBody.Part photo,
                                                         @Part("api_token") RequestBody apiToken,
                                                         @Part("delivery_time") RequestBody deliveryTime);

    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<RestaurantChangeState> getRestaurantChangeState(@Field("state") String state,
                                                         @Field("api_token") String apiToken);

    @POST("restaurant/new-category")
    @Multipart
    Call<RestaurantAddNewCategory> getRestaurantAddNewCategory(@Part("name") RequestBody name,
                                                               @Part MultipartBody.Part photo,
                                                               @Part("api_token") RequestBody apiToken);

    @POST("restaurant/update-category")
    @Multipart
    Call<RestaurantUpdateCategory> getRestaurantUpdateCategory(@Part("name") RequestBody name,
                                                               @Part MultipartBody.Part photo,
                                                               @Part("api_token") RequestBody apiToken,
                                                               @Part("category_id") RequestBody categoryId);

    @POST("restaurant/delete-category")
    @FormUrlEncoded
    Call<RestaurantDeleteCategory> getRestaurantDeleteCategory(@Field("api_token") String apiToken,
                                                               @Field("category_id") int categoryId);

    @POST("restaurant/new-item")
    @Multipart
    Call<RestaurantAddMenuItem> getRestaurantAddMenuItem(@Part("description") RequestBody description,
                                                         @Part("price") RequestBody price,
                                                         @Part("preparing_time") RequestBody preparing_time,
                                                         @Part("name") RequestBody name,
                                                         @Part MultipartBody.Part photo,
                                                         @Part("api_token") RequestBody apiToken,
                                                         @Part("offer_price") RequestBody offerPrice,
                                                         @Part("category_id") RequestBody category_id);

    @POST("restaurant/update-item")
    @Multipart
    Call<RestaurantEditMenuItem> getRestaurantEditMenuItem(@Part("name") RequestBody name,
                                                           @Part MultipartBody.Part photo,
                                                           @Part("api_token") RequestBody apiToken,
                                                           @Part("category_id") RequestBody categoryId);

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<RestaurantDeleteMenuItem> getRestaurantDeleteMenuItem(@Field("api_token") String apiToken,
                                                               @Field("item_id") int itemId);

    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<RestaurantChangePassword> getRestaurantChangePassword(@Field("api_token") String apiToken,
                                                               @Field("old_password") String oldPassword,
                                                               @Field("password") String password,
                                                               @Field("password_confirmation") String password_confirmation);

    @POST("restaurant/new-offer")
    @Multipart
    Call<RestaurantAddOffer> getRestaurantAddOffer(@Part("description") RequestBody description,
                                                   @Part("starting_at") RequestBody startingAt,
                                                   @Part("name") RequestBody name,
                                                   @Part MultipartBody.Part photo,
                                                   @Part("ending_at") RequestBody endingAt,
                                                   @Part("api_token") RequestBody apiToken,
                                                   @Part("offer_price") RequestBody offerPrice,
                                                   @Part("price") RequestBody Price);

    @POST("restaurant/update-offer")
    @Multipart
    Call<RestaurantEditOffer> getRestaurantEditOffer(@Part("description") RequestBody description,
                                                     @Part("starting_at") RequestBody startingAt,
                                                     @Part("name") RequestBody name,
                                                     @Part MultipartBody.Part photo,
                                                     @Part("ending_at") RequestBody endingAt,
                                                     @Part("offer_id") RequestBody offerId,
                                                     @Part("api_token") RequestBody apiToken);

    @POST("restaurant/delete-offer")
    @FormUrlEncoded
    Call<RestaurantDeleteOffer> getRestaurantDeleteOffer(@Field("offer_id") int offerId,
                                                         @Field("api_token") String apiToken);

    @POST("client/reset-password")
    @FormUrlEncoded
    Call<UserResetPassword> getUserResetPassword(@Field("email") String email);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<UserNewPassword> getUserNewPassword(@Field("code") String code,
                                             @Field("password") String password,
                                             @Field("password_confirmation") String password_confirmation);

    @POST("client/login")
    @FormUrlEncoded
    Call<UserLogin> getUserLogin(@Field("email") String email,
                                 @Field("password") String password);

    @POST("client/decline-order")
    @FormUrlEncoded
    Call<UserDeclineOrder> getUserDeclineOrder(@Field("order_id") int orderId,
                                               @Field("api_token") String apiToken);

    @POST("client/new-order")
    @FormUrlEncoded
    Call<UserNewOrder> getUserNewOrder(@Field("restaurant_id") int restaurantId,
                                       @Field("note") String note,
                                       @Field("address") String address,
                                       @Field("payment_method_id") int paymentMethodId,
                                       @Field("phone") String phone,
                                       @Field("name") String name,
                                       @Field("api_token") String apiToken,
                                       @Field("items") ArrayList<Integer> items,
                                       @Field("quantities") ArrayList<Integer> quantities,
                                       @Field("notes") ArrayList<String> notes);

    @POST("client/sign-up")
    @Multipart
    Call<UserRegister> getUserRegister(@Part("name") RequestBody name,
                                       @Part("email") RequestBody email,
                                       @Part("password") RequestBody password,
                                       @Part("password_confirmation") RequestBody passwordConfirmation,
                                       @Part("phone") RequestBody phone,
                                       @Part("region_id") RequestBody regionId,
                                       @Part("profile_image") MultipartBody.Part profileImage);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<UserAddReview> getUserAddReview(@Field("rate") int rate,
                                         @Field("comment") String comment,
                                         @Field("restaurant_id") int restaurantId,
                                         @Field("api_token") String apiToken);

}
// @Query("category") ArrayList<String> categories