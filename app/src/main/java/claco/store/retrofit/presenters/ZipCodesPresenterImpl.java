

package claco.store.retrofit.presenters;

import claco.store.retrofit.ZipCodeMapperUi;
import claco.store.retrofit.domain.interactors.search.SearchCodesByCityInteractor;
import claco.store.retrofit.domain.model.ZipCodeDomain;
import claco.store.retrofit.ui.views.CityZipsView;


import java.util.List;

import javax.inject.Inject;


public class ZipCodesPresenterImpl implements ZipCodesPresenter, SearchCodesByCityInteractor.Callback {

    @Inject
    CityZipsView view;

    @Inject
    ZipCodeMapperUi zipCodeMapper;

    @Inject
    SearchCodesByCityInteractor searchCodesByCityInteractor;

    @Inject
    public ZipCodesPresenterImpl() {

    }

    @Override
    public void makeSearch(String city) {

        if( city == null || city.isEmpty() ){

            view.showCityNeededErrorMessage();

        }else{

            view.showWaitingDialog();

            searchCodesByCityInteractor.execute( city, this );

        }
    }

    @Override
    public void onSuccess(List<ZipCodeDomain> list) {

        view.hideWaitingDialog();

        view.setCityZips( zipCodeMapper.reverseMapList( list ) );

    }

    @Override
    public void onError() {

        view.hideWaitingDialog();

        view.showErrorInRequest();

    }
}
