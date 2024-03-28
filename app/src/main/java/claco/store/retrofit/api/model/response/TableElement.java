

package claco.store.retrofit.api.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "morder", strict = false)
public class TableElement {
    @ElementList(name = "getOrderList",required = false)
    List<TableElement> elements;

    @Element(name = "OrderId", required = false)
    private String city;

    @Element(name = "OrderDate", required = false)
    private String state;

    @Element(name = "GrossAmount", required = false)
    private String zip;

    @Element(name = "DeliveryCharges", required = false)
    private String timeZone;

    @Element(name = "PaymentMode", required = false)
    private String areaCode;
    @Element(name = "NetPayable", required = false)
    private String netPayable;
    @Element(name = "DeliveryStatus", required = false)
    private String deliveryStatus;

    public String getNetPayable() {
        return netPayable;
    }

    public void setNetPayable(String netPayable) {
        this.netPayable = netPayable;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
