package claco.store.util;


//import com.google.gson.annotations.SerializedName;

public class GetOrderIDRequest {

   // @SerializedName("env")
    private String env;

  //  @SerializedName("buyer_name")
    private String buyerName;

 //   @SerializedName("buyer_email")
    private String buyerEmail;

 //   @SerializedName("buyer_phone")
    private String buyerPhone;

  //  @SerializedName("amount")
    private String amount;

 //   @SerializedName("description")
    private String description;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

  //  @SerializedName("access_token")
    private String access_token;

  //  @SerializedName("transaction_id")
    private String transaction_id;


    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
