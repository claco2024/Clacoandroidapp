

package claco.store.retrofit.domain.threads;

import android.os.Handler;
import android.os.Looper;

import javax.inject.Inject;


public class MainThreadImpl implements MainThread {

    private Handler handler;

    @Inject
    public MainThreadImpl() {
        handler = new Handler( Looper.getMainLooper() );
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        handler.post( runnable );
    }

}
