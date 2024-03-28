

package claco.store.retrofit.api.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "orderList", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class UsStatesRequestData {

    @Element(name = "customerId", required = false)
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
