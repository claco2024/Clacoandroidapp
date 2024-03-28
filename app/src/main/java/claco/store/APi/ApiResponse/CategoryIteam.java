
package claco.store.APi.ApiResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CategoryIteam {

    @SerializedName("MainCategoryId")
    @Expose
    private Integer mainCategoryId;
    @SerializedName("MainCategoryName")
    @Expose
    private String mainCategoryName;
    @SerializedName("mainCategoryImage")
    @Expose
    private String mainCategoryImage;


    public CategoryIteam() {
    }




    public Integer getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getMainCategoryImage() {
        return mainCategoryImage;
    }

    public void setMainCategoryImage(String mainCategoryImage) {
        this.mainCategoryImage = mainCategoryImage;
    }

}
