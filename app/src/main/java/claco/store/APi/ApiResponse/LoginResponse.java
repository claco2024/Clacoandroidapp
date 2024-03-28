package claco.store.APi.ApiResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import claco.store.APi.ApiResponse.recent.ResposneItem;


public class LoginResponse {

    @SerializedName("Response")
    @Expose
    private List<ResposneItem> response;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginResponse() {
    }

    /**
     *
     * @param response
     */
    public LoginResponse(List<ResposneItem> response) {
        super();
        this.response = response;
    }

    public List<ResposneItem> getResponse() {
        return response;
    }

    public void setResponse(List<ResposneItem> response) {
        this.response = response;
    }

}
