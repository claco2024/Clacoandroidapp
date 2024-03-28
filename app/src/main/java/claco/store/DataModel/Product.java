package claco.store.DataModel;

  public class Product {

      String ItemCode;

    String Quantity ;
    String Rate;
    String TotalAmount ;
    String Reason ;
    String VarId ;

      public String getVarId() {
          return VarId;
      }

      public void setVarId(String varId) {
          VarId = varId;
      }

      public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

     public       Product(String ItemCode,   String Quantity,  String Rate, String TotalAmount , String Reason, String VarId ){
        this.ItemCode= ItemCode;
        this.Quantity= Quantity;
        this.Rate= Rate;
        this.TotalAmount= TotalAmount;
        this.Reason= Reason;
        this.VarId= VarId;

    }
}
