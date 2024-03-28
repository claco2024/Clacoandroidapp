

package claco.store.retrofit.injection.application;

import claco.store.retrofit.RetrofitSoapApplication;
import claco.store.retrofit.api.UsStatesApi;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


@Module

public class ApplicationModule {

    private RetrofitSoapApplication application;

    public ApplicationModule(RetrofitSoapApplication application) {
        this.application = application;
    }

    @Provides

    public UsStatesApi providesApi(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Strategy strategy = new AnnotationStrategy();

        Serializer serializer = new Persister(strategy);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit =  new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .baseUrl("http://Kifayatistore.com/")
                .client(okHttpClient)
                .build();

        return retrofit.create( UsStatesApi.class);

    }

}
