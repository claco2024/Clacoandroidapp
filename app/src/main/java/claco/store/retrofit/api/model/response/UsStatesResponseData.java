

package claco.store.retrofit.api.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "orderList", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class UsStatesResponseData {

    @Element(name = "orderList", required = false)
    private UsStatesResponseInfo data;

    public UsStatesResponseInfo getData() {
        return data;
    }

    public void setData(UsStatesResponseInfo data) {
        this.data = data;
    }
}
