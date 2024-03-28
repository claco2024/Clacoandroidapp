

package claco.store.retrofit;

import claco.store.retrofit.domain.model.ZipCodeDomain;
import claco.store.retrofit.ui.model.ZipCodeData;

import javax.inject.Inject;


public class ZipCodeMapperUi extends EntityMapper<ZipCodeData,ZipCodeDomain> {

    @Inject
    public ZipCodeMapperUi() {
    }

    @Override
    public ZipCodeDomain map(ZipCodeData element) {
        ZipCodeDomain element1 = new ZipCodeDomain();
        element1.setCity(element.getCity());
        element1.setTimeZone(element.getTimeZone());
        element1.setAreaCode(element.getAreaCode());
        element1.setState(element.getState());
        element1.setZipCode(element.getZipCode());
        return element1;
    }

    @Override
    public ZipCodeData reverseMap(ZipCodeDomain element) {
        ZipCodeData codeData = new ZipCodeData();
        codeData.setCity(element.getCity());
        codeData.setState(element.getState());
        codeData.setAreaCode(element.getAreaCode());
        codeData.setTimeZone(element.getTimeZone());
        codeData.setZipCode(element.getZipCode());
        return codeData;
    }

}
