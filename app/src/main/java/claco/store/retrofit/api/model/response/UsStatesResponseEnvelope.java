

package claco.store.retrofit.api.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace( prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
        @Namespace( prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
        @Namespace( prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope")
})
public class UsStatesResponseEnvelope {

    @Element(required = false, name = "morder")
    private UsStatesResponseBody body;

    public UsStatesResponseBody getBody() {
        return body;
    }

    public void setBody(UsStatesResponseBody body) {
        this.body = body;
    }
}
