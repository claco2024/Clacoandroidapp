package claco.store.APi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiClent {


    private static Retrofit retrofit;
    //define base url here

    private static final String BASE_URL = "https://www.claco.in/Webservice.asmx/";

    public static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
