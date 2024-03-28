

package claco.store.retrofit.domain.threads;

import claco.store.retrofit.domain.interactors.Interactor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


public class InteractorExecutorImpl implements InteractorExecutor{

    private static final int CORE_POOL_SIZE = 2;

    private static final int MAXIMUM_POOL_SIZE = 2;

    private static final long LIFE_TIME = 60;

    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private static final BlockingQueue QUEUE = new LinkedBlockingQueue();

    private ThreadPoolExecutor threadPoolExecutor;

    @Inject
    public InteractorExecutorImpl() {

        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                LIFE_TIME,
                TIME_UNIT,
                QUEUE
        );

    }

    @Override
    public void run(final Interactor interactor) {

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {

                interactor.run();

            }
        });

    }
}
