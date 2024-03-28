
package claco.store.APi.ApiResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProductsLists {

    @SerializedName("proitemnew")
    @Expose
    private List<Proitemnew> proitemnew;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Msg")
    @Expose
    private Object msg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductsLists() {
    }

    /**
     *
     * @param proitemnew
     * @param msg
     * @param status
     */
    public ProductsLists(List<Proitemnew> proitemnew, Boolean status, Object msg) {
        super();
        this.proitemnew = proitemnew;
        this.status = status;
        this.msg = msg;
    }

    public List<Proitemnew> getProitemnew() {
        return proitemnew;
    }

    public void setProitemnew(List<Proitemnew> proitemnew) {
        this.proitemnew = proitemnew;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}
