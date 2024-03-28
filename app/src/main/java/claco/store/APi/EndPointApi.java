package claco.store.APi;

import com.google.gson.JsonObject;

import claco.store.APi.ApiResponse.CategoryObject;
import claco.store.APi.ApiResponse.LoginResponse;
import claco.store.APi.ApiResponse.ProductsLists;
import claco.store.APi.ApiResponse.recent.RecentList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPointApi {

    @GET("LastWeekAddedProductNew")
    Call<ProductsLists> getProductlist();

    @GET("GetMainCategory")
    Call<CategoryObject> getCategorylist();
    @GET("Recent")
    Call<RecentList> getRecentList();

    @POST("GetProductbyCategoryId")
    Call<Object> getIteamById( @Query("CategoryId") String CategoryId);

    @POST("Login")
    Call<LoginResponse> getLogin(@Body JsonObject jsonObject);

    @POST("AddMembershiptype")
    Call<Object> getAddMembershiptype(@Body JsonObject jsonObject);

    @POST("AddReview")
    Call<Object> getAddReview(@Body JsonObject jsonObject);

    @POST("ApplyCoupan")
    Call<Object> getApplyCoupan(@Body JsonObject jsonObject);

    @POST("CancelOrder")
    Call<Object> getCancelOrder(@Body JsonObject jsonObject);

    @POST("CancelProduct")
    Call<Object> getCancelProduct(@Body JsonObject jsonObject);

    @POST("ChangePassword")
    Call<Object> getChangePassword(@Body JsonObject jsonObject);

    @POST("CheckOut")
    Call<Object> getCheckOut(@Body JsonObject jsonObject);
    @POST("CoupanList")
    Call<Object> getCoupanList(@Body JsonObject jsonObject);

    @POST("DealsofTheDay")
    Call<Object> getDealsofTheDay(@Body JsonObject jsonObject);

    @POST("DeleteCart")
    Call<Object> getDeleteCart(@Body JsonObject jsonObject);

    @POST("DeleteCart")
    Call<Object> getForgetPassword(@Body JsonObject jsonObject);

    @POST("GetAccessToken")
    Call<Object> getGetAccessToken(@Body JsonObject jsonObject);

    @POST("GetAddress")
    Call<Object> getGetAddress(@Body JsonObject jsonObject);

    @POST("GetAppVersion")
    Call<Object> getAppVersion(@Body JsonObject jsonObject);

    @POST("GetColorMaster")
    Call<Object> getColorMaster(@Body JsonObject jsonObject);

    @POST("GetDeliveryType")
    Call<Object> getDeliveryType(@Body JsonObject jsonObject);

    @POST("GetDistrictList")
    Call<Object> getDistrictList(@Body JsonObject jsonObject);

    @POST("GetMainCategory")
    Call<Object> getMainCategory(@Body JsonObject jsonObject);

    @POST("GetMembershiptype")
    Call<Object> getMembershiptype(@Body JsonObject jsonObject);

    @POST("GetProductNew")
    Call<Object> getProductNew(@Body JsonObject jsonObject);

    @POST("GetProductSearch")
    Call<Object> getProductSearch(@Body JsonObject jsonObject);
    @POST("GetProductbyCategoryId")
    Call<Object> getProductbyCategoryId(@Body JsonObject jsonObject);

    @POST("GetProductlistByfilter")
    Call<Object> getProductlistByfilter(@Body JsonObject jsonObject);

    @POST("GetReturnReason")
    Call<Object> getReturnReason(@Body JsonObject jsonObject);

    @POST("GetSizeMaster")
    Call<Object> getSizeMaster(@Body JsonObject jsonObject);

    @POST("GetVendorDashboard")
    Call<Object> getVendorDashboard(@Body JsonObject jsonObject);

    @POST("LastWeekAddedProduct")
    Call<Object> getLastWeekAddedProduct(@Body JsonObject jsonObject);

    @POST("LastWeekAddedProductNew")
    Call<Object> getLastWeekAddedProductNew(@Body JsonObject jsonObject);

    @POST("MyWallet")
    Call<Object> getMyWallet(@Body JsonObject jsonObject);
    @POST("OfferList")
    Call<Object> getOfferList(@Body JsonObject jsonObject);

    @POST("Payment")
    Call<Object> getPayment(@Body JsonObject jsonObject);

    @POST("Redeemgift")
    Call<Object> gedeemgift(@Body JsonObject jsonObject);

    @POST("Registration")
    Call<Object> getRegistration(@Body JsonObject jsonObject);

    @POST("Registration1")
    Call<Object> getRegistration1(@Body JsonObject jsonObject);

    @POST("ReturnCustomerOrder")
    Call<Object> getReturnCustomerOrder(@Body JsonObject jsonObject);


    @POST("ReturnProduct")
    Call<Object> getReturnProduct(@Body JsonObject jsonObject);

    @POST("ReviewList")
    Call<Object> getReviewList(@Body JsonObject jsonObject);

    @POST("TrackingOrder")
    Call<Object> getTrackingOrder(@Body JsonObject jsonObject);

    @POST("UpdateAddress")
    Call<Object> getUpdateAddress(@Body JsonObject jsonObject);

    @POST("UpdateCartNew")
    Call<Object> getUpdateCartNew(@Body JsonObject jsonObject);

    @POST("UpdateProfile")
    Call<Object> getUpdateProfile(@Body JsonObject jsonObject);


    @POST("WalletPoint")
    Call<Object> getWalletPoint(@Body JsonObject jsonObject);
    @POST("addAddress")
    Call<Object> getaddAddress(@Body JsonObject jsonObject);

    @POST("addToCart")
    Call<Object> getaddToCart(@Body JsonObject jsonObject);

    @POST("checkPinCode")
    Call<Object> getCheckPinCode(@Body JsonObject jsonObject);

    @POST("demojson")
    Call<Object> getdemojson(@Body JsonObject jsonObject);

    @POST("fetchState")
    Call<Object> getFetchState(@Body JsonObject jsonObject);

    @POST("getBanner")
    Call<Object> getBanner(@Body JsonObject jsonObject);

    @POST("getBannerbootom")
    Call<Object> getBannerbootom(@Body JsonObject jsonObject);

    @POST("getCartList")
    Call<Object> getCartList(@Body JsonObject jsonObject);

    @POST("getCategory")
    Call<Object> getCategory(@Body JsonObject jsonObject);

    @POST("getCity")
    Call<Object> getCity(@Body JsonObject jsonObject);

    @POST("getDealsofTheDay")
    Call<Object> getNewDealsofTheDay(@Body JsonObject jsonObject);

    @POST("getDeliveryTimeSlot")
    Call<Object> getDeliveryTimeSlot(@Body JsonObject jsonObject);

    @POST("getMRPWiseDeliveryCharges")
    Call<Object> getMRPWiseDeliveryCharges(@Body JsonObject jsonObject);

    @POST("getManufacturerlist")
    Call<Object> getManufacturerlist(@Body JsonObject jsonObject);

    @POST("getOTP")
    Call<Object> getOTP(@Body JsonObject jsonObject);

    @POST("getOTPLogin")
    Call<Object> getOTPLogin(@Body JsonObject jsonObject);

    @POST("getPincodeWiseDeliveryCharges")
    Call<Object> getPincodeWiseDeliveryCharges(@Body JsonObject jsonObject);

    @POST("getPointOfferList")
    Call<Object> getPointOfferList(@Body JsonObject jsonObject);

    @POST("getProductdetail")
    Call<Object> getProductdetail(@Body JsonObject jsonObject);

    @POST("getRefferalCustomerWise")
    Call<Object> getRefferalCustomerWise(@Body JsonObject jsonObject);

    @POST("getSimilarProduct")
    Call<Object> getSimilarProduct(@Body JsonObject jsonObject);

    @POST("getofferlist")
    Call<Object> getofferlist(@Body JsonObject jsonObject);

    @POST("getsubCategory")
    Call<Object> getsubCategory(@Body JsonObject jsonObject);

    @POST("getsubCategoryList")
    Call<Object> getsubCategoryList(@Body JsonObject jsonObject);

    @POST("orderList")
    Call<Object> orderList(@Body JsonObject jsonObject);

}
