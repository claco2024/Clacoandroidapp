package claco.store.util;

import android.os.Build;
import androidx.annotation.RequiresApi;

public class RazorpayException extends Exception {

    public RazorpayException(String message) {
        super(message);
    }

    public RazorpayException(String message, Throwable cause) {
        super(message, cause);
    }

    public RazorpayException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public RazorpayException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}