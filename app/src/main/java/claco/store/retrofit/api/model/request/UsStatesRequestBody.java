

package claco.store.retrofit.api.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class UsStatesRequestBody {

    @Element(name = "orderList",required = false)
    private UsStatesRequestData usStatesRequestData;

    public UsStatesRequestData getUsStatesRequestData() {
        return usStatesRequestData;
    }

    public void setUsStatesRequestData(UsStatesRequestData usStatesRequestData) {
        this.usStatesRequestData = usStatesRequestData;
    }

}
