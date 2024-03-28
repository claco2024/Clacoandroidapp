

package claco.store.retrofit.api;

import claco.store.retrofit.api.model.request.UsStatesRequestEnvelope;
import claco.store.retrofit.api.model.response.UsStatesResponseEnvelope;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface UsStatesApi {

    @Headers({
            "Content-Type: text/xml",
            "Accept-Charset: utf-8"
    })
   // @POST("/uszip.asmx")
    @POST("/Webservice.asmx")
    Call<UsStatesResponseEnvelope> requestStateInfo(@Body UsStatesRequestEnvelope body);

}
