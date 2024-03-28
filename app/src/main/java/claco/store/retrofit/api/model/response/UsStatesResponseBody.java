

package claco.store.retrofit.api.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "morder", strict = false)
public class UsStatesResponseBody {

    @Element(name = "morder", required = false)
    private UsStatesResponseData data;

    public UsStatesResponseData getData() {
        return data;
    }

    public void setData(UsStatesResponseData data) {
        this.data = data;
    }
}
