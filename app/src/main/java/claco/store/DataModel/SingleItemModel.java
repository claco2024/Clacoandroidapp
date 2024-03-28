package claco.store.DataModel;


public class SingleItemModel {

    private String name;
    private String url;
    private String description;
    private String MinimumQuantity;
    private String ProductId;
    private String SalePrice;
    private String MainPicture;

    public SingleItemModel(String name, String minimumQuantity, String productId, String SalePrice, String mainPicture) {
        this.name = name;
        MinimumQuantity = minimumQuantity;
        ProductId = productId;
        SalePrice = SalePrice;
        MainPicture = mainPicture;
    }

    public String getMinimumQuantity() {
        return MinimumQuantity;
    }

    public void setMinimumQuantity(String minimumQuantity) {
        MinimumQuantity = minimumQuantity;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String SalePrice) {
        SalePrice = SalePrice;
    }

    public String getMainPicture() {
        return MainPicture;
    }

    public void setMainPicture(String mainPicture) {
        MainPicture = mainPicture;
    }

    public SingleItemModel() {
    }

    public SingleItemModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
