

package claco.store.retrofit.domain.interactors.search;




import claco.store.retrofit.ZipCodeMapperDomain;
import claco.store.retrofit.api.UsStatesApi;
import claco.store.retrofit.api.model.request.UsStatesRequestBody;
import claco.store.retrofit.api.model.request.UsStatesRequestData;
import claco.store.retrofit.api.model.request.UsStatesRequestEnvelope;
import claco.store.retrofit.api.model.response.UsStatesResponseEnvelope;
import claco.store.retrofit.domain.threads.InteractorExecutor;
import claco.store.retrofit.domain.threads.MainThread;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;


public class SearchCodesByCityInteractorImpl implements SearchCodesByCityInteractor {

    private String cityName;

    private Callback callback;

    @Inject
    UsStatesApi usStatesApi;

    @Inject
    ZipCodeMapperDomain zipCodeMapperDomain;

    @Inject
    MainThread mainThread;

    @Inject
    InteractorExecutor interactorExecutor;

    @Inject
    public SearchCodesByCityInteractorImpl() {
    }

    @Override
    public void execute(String cityName, Callback callback) {

        this.cityName = cityName;

        this.callback = callback;

        interactorExecutor.run( this );

    }

    @Override
    public void run() {

        //Creation of the envelope and the message.
        UsStatesRequestEnvelope envelope = new UsStatesRequestEnvelope();

        UsStatesRequestBody body = new UsStatesRequestBody();

        UsStatesRequestData data = new UsStatesRequestData();

        data.setCity( "CUST000057" );

        body.setUsStatesRequestData(data);

        envelope.setBody(body);

        Call<UsStatesResponseEnvelope> call = usStatesApi.requestStateInfo(envelope);

        call.enqueue(new retrofit2.Callback<UsStatesResponseEnvelope>() {
            @Override
            public void onResponse(Call<UsStatesResponseEnvelope> call, final Response<UsStatesResponseEnvelope> response) {

                mainThread.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        callback.onSuccess(zipCodeMapperDomain.reverseMapList(response.body().getBody().getData().getData().getElements()));

                    }

                });

            }

            @Override
            public void onFailure(Call<UsStatesResponseEnvelope> call, Throwable t) {

                mainThread.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        callback.onError();

                    }
                });


            }
        });


    }
}
