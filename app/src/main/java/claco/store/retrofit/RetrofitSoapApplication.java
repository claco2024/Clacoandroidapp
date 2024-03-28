

package claco.store.retrofit;

import android.app.Application;

import claco.store.retrofit.injection.application.ApplicationComponent;
import claco.store.retrofit.injection.application.ApplicationModule;
import claco.store.retrofit.injection.application.DaggerApplicationComponent;


public class RetrofitSoapApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component =  DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
