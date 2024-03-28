
package claco.store.APi.ApiResponse.recent;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Jewellery {

    @SerializedName("ProductCategory")
    @Expose
    private String productCategory;
    @SerializedName("ProductCode")
    @Expose
    private String productCode;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("MainCategoryCode")
    @Expose
    private String mainCategoryCode;
    @SerializedName("ProductMainImageUrl")
    @Expose
    private String productMainImageUrl;
    @SerializedName("Discper")
    @Expose
    private String discper;
    @SerializedName("pName")
    @Expose
    private String pName;
    @SerializedName("SalePrice")
    @Expose
    private String salePrice;
    @SerializedName("UnitName")
    @Expose
    private Object unitName;
    @SerializedName("RegularPrice")
    @Expose
    private String regularPrice;
    @SerializedName("SaveAmount")
    @Expose
    private Object saveAmount;
    @SerializedName("AttrId")
    @Expose
    private String attrId;
    @SerializedName("VarId")
    @Expose
    private String varId;
    @SerializedName("saleValue")
    @Expose
    private String saleValue;
    @SerializedName("proitemnewvar")
    @Expose
    private List<Object> proitemnewvar;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Jewellery() {
    }

    /**
     * 
     * @param unitName
     * @param salePrice
     * @param regularPrice
     * @param mainCategoryCode
     * @param productName
     * @param varId
     * @param productCategory
     * @param productCode
     * @param attrId
     * @param pName
     * @param proitemnewvar
     * @param productMainImageUrl
     * @param discper
     * @param saleValue
     * @param saveAmount
     */
    public Jewellery(String productCategory, String productCode, String productName, String mainCategoryCode, String productMainImageUrl, String discper, String pName, String salePrice, Object unitName, String regularPrice, Object saveAmount, String attrId, String varId, String saleValue, List<Object> proitemnewvar) {
        super();
        this.productCategory = productCategory;
        this.productCode = productCode;
        this.productName = productName;
        this.mainCategoryCode = mainCategoryCode;
        this.productMainImageUrl = productMainImageUrl;
        this.discper = discper;
        this.pName = pName;
        this.salePrice = salePrice;
        this.unitName = unitName;
        this.regularPrice = regularPrice;
        this.saveAmount = saveAmount;
        this.attrId = attrId;
        this.varId = varId;
        this.saleValue = saleValue;
        this.proitemnewvar = proitemnewvar;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMainCategoryCode() {
        return mainCategoryCode;
    }

    public void setMainCategoryCode(String mainCategoryCode) {
        this.mainCategoryCode = mainCategoryCode;
    }

    public String getProductMainImageUrl() {
        return productMainImageUrl;
    }

    public void setProductMainImageUrl(String productMainImageUrl) {
        this.productMainImageUrl = productMainImageUrl;
    }

    public String getDiscper() {
        return discper;
    }

    public void setDiscper(String discper) {
        this.discper = discper;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public Object getUnitName() {
        return unitName;
    }

    public void setUnitName(Object unitName) {
        this.unitName = unitName;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Object getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(Object saveAmount) {
        this.saveAmount = saveAmount;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }

    public String getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(String saleValue) {
        this.saleValue = saleValue;
    }

    public List<Object> getProitemnewvar() {
        return proitemnewvar;
    }

    public void setProitemnewvar(List<Object> proitemnewvar) {
        this.proitemnewvar = proitemnewvar;
    }

}
