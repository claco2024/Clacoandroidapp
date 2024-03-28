package claco.store.model;

public class QuantityModel {
    String Id;
    String Particular;
    String Regularprice;
    String Sellingprice;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParticular() {
        return Particular;
    }

    public void setParticular(String particular) {
        Particular = particular;
    }

    public String getRegularprice() {
        return Regularprice;
    }

    public void setRegularprice(String regularprice) {
        Regularprice = regularprice;
    }

    public String getSellingprice() {
        return Sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        Sellingprice = sellingprice;
    }

    public QuantityModel(String Id,String Particular ,String Regularprice,String Sellingprice)
    {
        this.Id=Id;
        this.Particular=Particular;
        this.Regularprice=Regularprice;
        this.Sellingprice=Sellingprice;

    }

}
