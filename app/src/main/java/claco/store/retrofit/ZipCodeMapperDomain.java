

package claco.store.retrofit;

import claco.store.retrofit.api.model.response.TableElement;
import claco.store.retrofit.domain.model.ZipCodeDomain;

import javax.inject.Inject;


public class ZipCodeMapperDomain extends EntityMapper<ZipCodeDomain,TableElement> {

    @Inject
    public ZipCodeMapperDomain() {
    }

    @Override
    public TableElement map(ZipCodeDomain element) {
        TableElement element1 = new TableElement();
        element1.setCity(element.getCity());
        element1.setTimeZone(element.getTimeZone());
        element1.setAreaCode(element.getAreaCode());
        element1.setState(element.getState());
        element1.setZip(element.getZipCode());
        return element1;
    }

    @Override
    public ZipCodeDomain reverseMap(TableElement element) {
        ZipCodeDomain codeData = new ZipCodeDomain();
        codeData.setCity(element.getCity());
        codeData.setState(element.getState());
        codeData.setAreaCode(element.getAreaCode());
        codeData.setTimeZone(element.getTimeZone());
        codeData.setZipCode(element.getZip());
        return codeData;
    }
}
