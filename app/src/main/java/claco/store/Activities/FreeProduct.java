package claco.store.Activities;

public class FreeProduct {
    String ItemCode;
    String qty;
    String varIdL;

    public FreeProduct(String itemCode, String qty, String varIdL) {
        ItemCode = itemCode;
        this.qty = qty;
        this.varIdL = varIdL;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getVarIdL() {
        return varIdL;
    }

    public void setVarIdL(String varIdL) {
        this.varIdL = varIdL;
    }
}
