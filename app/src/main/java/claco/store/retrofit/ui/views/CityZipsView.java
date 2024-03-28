

package claco.store.retrofit.ui.views;

import claco.store.retrofit.ui.model.ZipCodeData;

import java.util.List;


public interface CityZipsView {

    void setCityZips(List<ZipCodeData> cityZips);

    void showWaitingDialog();

    void hideWaitingDialog();

    void showCityNeededErrorMessage();

    void showErrorInRequest();

}
