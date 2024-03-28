

package claco.store.retrofit.injection.zip;

import androidx.recyclerview.widget.RecyclerView;

import claco.store.retrofit.EntityMapper;
import claco.store.retrofit.api.model.response.TableElement;
import claco.store.retrofit.domain.interactors.search.SearchCodesByCityInteractor;
import claco.store.retrofit.domain.model.ZipCodeDomain;
import claco.store.retrofit.domain.threads.InteractorExecutor;
import claco.store.retrofit.domain.threads.MainThread;
import claco.store.retrofit.injection.ActivityScope;
import claco.store.retrofit.injection.application.ApplicationComponent;
import claco.store.retrofit.presenters.ZipCodesPresenter;
import claco.store.retrofit.ui.model.ZipCodeData;
import claco.store.retrofit.ui.views.MainActivity;

import dagger.Component;


@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = ZipCodeModule.class
)
public interface ZipCodeComponent {

    void inject(MainActivity activity);

    RecyclerView.Adapter providesZipCodesAdapter();

    ZipCodesPresenter providesPresenter();

    EntityMapper<ZipCodeData,ZipCodeDomain> providesUiMapper();

    EntityMapper<ZipCodeDomain,TableElement> providesDomainMapper();

    SearchCodesByCityInteractor providesInteractor();

    InteractorExecutor providesInteractorExecutor();

    MainThread providesMainThread();

}
