package claco.store.model;

import java.util.ArrayList;

public class ProductModel {

    String ProductId;
    String ProductTitle;
    String MainPicture;
    String MRP;
    String SellingPrice;
    String Status;
    String minQuantity;
    ArrayList<QuantityModel>arrayList;


    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getMainPicture() {
        return MainPicture;
    }

    public void setMainPicture(String mainPicture) {
        MainPicture = mainPicture;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(String minQuantity) {
        this.minQuantity = minQuantity;
    }

    public ArrayList<QuantityModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<QuantityModel> arrayList) {
        this.arrayList = arrayList;
    }


    public ProductModel(String ProductId,String ProductTitle ,String MainPicture,String MRP,String SellingPrice,String Status,String minQuantity, ArrayList<QuantityModel>arrayList)
    {
        this.ProductId=ProductId;
        this.ProductTitle=ProductTitle;
        this.MainPicture=MainPicture;
        this.MRP=MRP;
        this.SellingPrice=SellingPrice;
        this.Status=Status;
        this.minQuantity=minQuantity;
        this.arrayList=arrayList;

    }

}
