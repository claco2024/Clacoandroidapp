
package claco.store.APi.ApiResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CategoryObject {

    @SerializedName("Response")
    @Expose
    private List<CategoryIteam> response;


    public CategoryObject() {
    }




    public List<CategoryIteam> getResponse() {
        return response;
    }

    public void setResponse(List<CategoryIteam> response) {
        this.response = response;
    }

}
