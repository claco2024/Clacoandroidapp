package claco.store.ApiUtils;

import android.util.Log;

import androidx.loader.content.Loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import claco.store.APi.ApiClent;

import claco.store.APi.ApiResponse.CategoryObject;
import claco.store.APi.ApiResponse.LoginResponse;
import claco.store.APi.ApiResponse.ProductsLists;
import claco.store.APi.ApiResponse.recent.RecentList;
import claco.store.APi.EndPointApi;
import claco.store.util.CustomLoader;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public enum   APiUtils {

    INSTANSE;

     public void allProduct(CustomLoader loader, ApiCallBAck apiCallBAck){
         loader.show();
         EndPointApi endPointApi= ApiClent.getRetrofitInstance().create(EndPointApi.class);
         Call<ProductsLists> call=endPointApi.getProductlist();
         call.enqueue(new Callback<ProductsLists>() {
             @Override
             public void onResponse(Call<ProductsLists> call, Response<ProductsLists> response) {

                 if(response.code()==200){
                     loader.dismiss();

                     try {
                         apiCallBAck.onSuccess(response.body());








                     } catch (JsonSyntaxException e) {


                         Log.d("prabhatmsgerrrrresponse",e.getMessage());
                     }
                 } else {
                     loader.dismiss();
                     Log.d("prabhatmsgCode", String.valueOf(response.code()));
                 }

             }

             @Override
             public void onFailure(Call<ProductsLists> call, Throwable t) {
                 loader.dismiss();
                 Log.d("prabhatmsg",t.getMessage());

             }
         });


     }
    public void getLogin(ApiCallBAck apiCallBAck,String userId,String password){
     JsonObject request=   new JsonObject();
     request.addProperty(
             userId,password
     );
        EndPointApi endPointApi= ApiClent.getRetrofitInstance().create(EndPointApi.class);
        Call<LoginResponse> call=endPointApi.getLogin(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {




                    try {
                        if(response.code()==200){

                        apiCallBAck.onSuccess(response.body());


                        } else {
                            Log.d("prabhatmsgCode", String.valueOf(response.code()));
                        }





                    } catch (JsonSyntaxException e) {


                        Log.d("prabhatmsgerrrrresponse",e.getMessage());
                    }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d("prabhatmsg",t.getMessage());

            }
        });


    }

    public void recentProduct(CustomLoader loader, ApiCallBAck apiCallBAck){
        loader.show();
        EndPointApi endPointApi= ApiClent.getRetrofitInstance().create(EndPointApi.class);
        Call<RecentList> call=endPointApi.getRecentList();
        call.enqueue(new Callback<RecentList>() {
            @Override
            public void onResponse(Call<RecentList> call, Response<RecentList> response) {

                if(response.code()==200){


                    try {
                        apiCallBAck.onSuccess(response.body());








                    } catch (JsonSyntaxException e) {
                        loader.dismiss();

                        Log.d("prabhatmsgerrrrresponse",e.getMessage());
                    }
                } else {
                    loader.dismiss();
                    Log.d("prabhatmsgCode", String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<RecentList> call, Throwable t) {
                loader.dismiss();
                Log.d("prabhatmsg",t.getMessage());

            }
        });


    }
     public void homeCategory(CustomLoader loader,ApiCallBAck apiCallBAck){
         loader.show();
         EndPointApi endPointApi= ApiClent.getRetrofitInstance().create(EndPointApi.class);
         Call<CategoryObject> call=endPointApi.getCategorylist();
         call.enqueue(new Callback<CategoryObject>() {
             @Override
             public void onResponse(Call<CategoryObject> call, Response<CategoryObject> response) {

                 if(response.code()==200){

                     loader.dismiss();
                     try {
                         apiCallBAck.onSuccess(response.body());





                     } catch (JsonSyntaxException e) {

                         loader.dismiss();
                         Log.d("prabhatmsgerrrrresponse",e.getMessage());
                     }
                 } else {
                     loader.dismiss();
                     Log.d("prabhatmsgCode", String.valueOf(response.code()));
                 }

             }

             @Override
             public void onFailure(Call<CategoryObject> call, Throwable t) {
                 loader.dismiss();
                 Log.d("prabhatmsg",t.getMessage());

             }
         });


     }

     public  void okhttp(){

         OkHttpClient client = new OkHttpClient().newBuilder()
                 .build();
         MediaType mediaType = MediaType.parse("text/xml");
         RequestBody body = RequestBody.create(mediaType, "");
         Request request = new Request.Builder()
                 .url("https://www.claco.in/Webservice.asmx/Login?Userid=7233099069&password=847905")
                 .method("GET",body)
                 .addHeader("Content-Type", "text/xml")
                 .build();
         try {
             okhttp3.Response response = client.newCall(request).execute();

             Log.d("kkjhnjhn",String.valueOf(response));
         } catch (IOException e) {

         }
     }


    public interface ApiCallBAck {
        public void onSuccess(Object data) ;
        public void onFailed(String message) ;
    }
}
