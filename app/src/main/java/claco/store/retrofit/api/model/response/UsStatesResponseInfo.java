

package claco.store.retrofit.api.model.response;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "orderList",strict = false)
public class UsStatesResponseInfo {

    @ElementList(name = "morder",required = false)
    List<TableElement> elements;

    public List<TableElement> getElements() {
        return elements;
    }

    public void setElements(List<TableElement> elements) {
        this.elements = elements;
    }

}
