

package claco.store.retrofit.injection.application;

import claco.store.retrofit.api.UsStatesApi;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    UsStatesApi providesApi();



}
