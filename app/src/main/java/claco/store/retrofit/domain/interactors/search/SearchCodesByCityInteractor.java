

package claco.store.retrofit.domain.interactors.search;


import claco.store.retrofit.domain.interactors.Interactor;
import claco.store.retrofit.domain.model.ZipCodeDomain;

import java.util.List;


public interface SearchCodesByCityInteractor extends Interactor {

    interface Callback{

        void onSuccess(List<ZipCodeDomain> zipCodes);

        void onError();

    }

    void execute(String cityName, Callback callback);


}
