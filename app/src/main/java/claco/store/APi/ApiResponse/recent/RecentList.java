
package claco.store.APi.ApiResponse.recent;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RecentList {

    @SerializedName("Kids")
    @Expose
    private List<BeutiProduct> kids;
    @SerializedName("proitemnew")
    @Expose
    private Object proitemnew;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Msg")
    @Expose
    private Object msg;
    @SerializedName("Mens")
    @Expose
    private List<BeutiProduct> mens;
    @SerializedName("Women")
    @Expose
    private List<BeutiProduct> women;
    @SerializedName("Uniform")
    @Expose
    private List<BeutiProduct> uniform;
    @SerializedName("BeutiProduct")
    @Expose
    private List<BeutiProduct> beutiProduct;
    @SerializedName("Jewellery")
    @Expose
    private List<BeutiProduct> jewellery;
    @SerializedName("HomeDecoration")
    @Expose
    private List<BeutiProduct> homeDecoration;
    @SerializedName("Grocery")
    @Expose
    private List<BeutiProduct> grocery;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RecentList() {
    }

    /**
     * 
     * @param proitemnew
     * @param msg
     * @param uniform
     * @param jewellery
     * @param beutiProduct
     * @param homeDecoration
     * @param grocery
     * @param mens
     * @param kids
     * @param status
     * @param women
     */
    public RecentList(List<BeutiProduct> kids, Object proitemnew, Boolean status, Object msg, List<BeutiProduct> mens, List<BeutiProduct> women, List<BeutiProduct> uniform, List<BeutiProduct> beutiProduct, List<BeutiProduct> jewellery, List<BeutiProduct> homeDecoration, List<BeutiProduct> grocery) {
        this.kids = kids;
        this.proitemnew = proitemnew;
        this.status = status;
        this.msg = msg;
        this.mens = mens;
        this.women = women;
        this.uniform = uniform;
        this.beutiProduct = beutiProduct;
        this.jewellery = jewellery;
        this.homeDecoration = homeDecoration;
        this.grocery = grocery;
    }

    public List<BeutiProduct> getHomeDecoration() {
        return homeDecoration;
    }

    public void setHomeDecoration(List<BeutiProduct> homeDecoration) {
        this.homeDecoration = homeDecoration;
    }

    public List<BeutiProduct> getKids() {
        return kids;
    }

    public void setKids(List<BeutiProduct> kids) {
        this.kids = kids;
    }

    public Object getProitemnew() {
        return proitemnew;
    }

    public void setProitemnew(Object proitemnew) {
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

    public List<BeutiProduct> getMens() {
        return mens;
    }

    public void setMens(List<BeutiProduct> mens) {
        this.mens = mens;
    }

    public List<BeutiProduct> getWomen() {
        return women;
    }

    public void setWomen(List<BeutiProduct> women) {
        this.women = women;
    }

    public List<BeutiProduct> getUniform() {
        return uniform;
    }

    public void setUniform(List<BeutiProduct> uniform) {
        this.uniform = uniform;
    }

    public List<BeutiProduct> getBeutiProduct() {
        return beutiProduct;
    }

    public void setBeutiProduct(List<BeutiProduct> beutiProduct) {
        this.beutiProduct = beutiProduct;
    }

    public List<BeutiProduct> getJewellery() {
        return jewellery;
    }

    public void setJewellery(List<BeutiProduct> jewellery) {
        this.jewellery = jewellery;
    }



    public List<BeutiProduct> getGrocery() {
        return grocery;
    }

    public void setGrocery(List<BeutiProduct> grocery) {
        this.grocery = grocery;
    }
}
