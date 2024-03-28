package claco.store;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;


public class ReferrerReceiver extends BroadcastReceiver {
    public static final String ACTION_UPDATE_DATA = "ACTION_UPDATE_DATA";
    private static final String ACTION_INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";
    private static final String KEY_REFERRER = "referrer";

    Preferences preferences;

    public ReferrerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        preferences=new Preferences(context);
        if (intent == null) {
            Log.e("ReferrerReceiver", "Intent is null");
            return;
        }


        String referrerId = intent.getStringExtra("referrer");

        Log.v("referredid2",referrerId);

        preferences.set(AppSettings.ReferralValue,referrerId);
        preferences.commit();



        if (referrerId == null){
            return;
        }


      /*  if (!ACTION_INSTALL_REFERRER.equals(intent.getAction())) {
            Log.e("ReferrerReceiver", "Wrong action! Expected: " + ACTION_INSTALL_REFERRER + " but was: " + intent.getAction());
            return;
        }
        Bundle extras = intent.getExtras();
        if (intent.getExtras() == null) {
            Log.e("ReferrerReceiver", "No data in intent");
            return;
        }

        Application.setReferrerDate(context.getApplicationContext(), new Date().getTime());
        Application.setReferrerData(context.getApplicationContext(), (String) extras.get(KEY_REFERRER));*/

        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ACTION_UPDATE_DATA));
    }
}
