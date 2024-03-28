

package claco.store.retrofit.injection.zip;

import androidx.recyclerview.widget.RecyclerView;

import claco.store.retrofit.EntityMapper;
import claco.store.retrofit.ZipCodeMapperDomain;
import claco.store.retrofit.ZipCodeMapperUi;
import claco.store.retrofit.api.model.response.TableElement;
import claco.store.retrofit.domain.interactors.search.SearchCodesByCityInteractor;
import claco.store.retrofit.domain.interactors.search.SearchCodesByCityInteractorImpl;
import claco.store.retrofit.domain.model.ZipCodeDomain;
import claco.store.retrofit.domain.threads.InteractorExecutor;
import claco.store.retrofit.domain.threads.InteractorExecutorImpl;
import claco.store.retrofit.domain.threads.MainThread;
import claco.store.retrofit.domain.threads.MainThreadImpl;
import claco.store.retrofit.injection.ActivityScope;
import claco.store.retrofit.presenters.ZipCodesPresenter;
import claco.store.retrofit.presenters.ZipCodesPresenterImpl;
import claco.store.retrofit.ui.adapter.ZipCodesAdapter;
import claco.store.retrofit.ui.model.ZipCodeData;
import claco.store.retrofit.ui.views.CityZipsView;
import claco.store.retrofit.ui.views.MainActivity;


import dagger.Module;
import dagger.Provides;


@Module
public class ZipCodeModule {

    private MainActivity activity;

    public ZipCodeModule(MainActivity activity) {
        this.activity = activity;
    }


    @Provides
    @ActivityScope
    public CityZipsView providesView(){
        return activity;
    }

    @Provides
    @ActivityScope
    public ZipCodesPresenter providesPresenter(ZipCodesPresenterImpl zipCodesPresenter){
        return zipCodesPresenter;
    }

    @Provides
    @ActivityScope
    public EntityMapper<ZipCodeData,ZipCodeDomain> providesMapper(ZipCodeMapperUi mapper){
        return  mapper;
    }

    @Provides
    @ActivityScope
    public EntityMapper<ZipCodeDomain,TableElement> providesDomainMapper(ZipCodeMapperDomain mapper){
        return  mapper;
    }


    @Provides
    @ActivityScope
    public SearchCodesByCityInteractor providesInteractor(SearchCodesByCityInteractorImpl interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    public RecyclerView.Adapter providesAdapter(ZipCodesAdapter adapter){
        return adapter;
    }


    @Provides
    @ActivityScope
    public InteractorExecutor providesInteractorExecutor(InteractorExecutorImpl interactorExecutor){
        return interactorExecutor;
    }

    @Provides
    @ActivityScope
    public MainThread providesMainThread(MainThreadImpl mainThread){
        return mainThread;
    }
}
