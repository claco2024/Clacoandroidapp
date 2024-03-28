package claco.store.Main;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import claco.store.Application;
import claco.store.R;
import claco.store.ReferrerReceiver;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class SplashActivity extends AppCompatActivity {

    ImageView ivLogo, ivLogo1, ivLogo2;
    Double OnlineVersion;

    PackageInfo pInfo;

    Double verCode;
    TextView tv_version;
    AlertDialog.Builder builder;
    ImageView ivBanner;
    Preferences pref;

    private final BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateData();
        }
    };

    ProgressDialog loader;

    public static JSONObject jsonObject;
    public static JSONObject jsonProductlist;

    public static JSONObject jsoncategory;
    public static String ProductId, PCategory, PName, PCatid, PCustid;
    public static String MainCatID, SCatID, ScateName, Sid, Scatimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
      /*  FirebaseApp.initializeApp(SplashActivity.this);
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            try {
                                if (deepLink != null && !deepLink.equals("")) {
                                    try {
                                        String[] link = deepLink.toString().split("ref");

                                        String producttype = link[1];
                                        if (producttype.equalsIgnoreCase("product")) {
                                            ProductId = link[2];
                                            PCategory = link[3];
                                            PCategory = PCategory.replaceAll("%20", " ");
                                            PName = link[4];
                                            PName = PName.replaceAll("%20", " ");
                                            PCatid = link[5];
                                            PCustid = link[6];
                                        } else {

                                            MainCatID = link[2];
                                            SCatID = link[3];
                                            ScateName = link[4];
                                            ScateName = ScateName.replaceAll("%20", " ");
                                            Scatimg = link[5];
                                            Sid = link[6];
                                            PCustid = link[7];


                                        }


                                    } catch (Exception e) {

                                    }

                                }
                            } catch (Exception e) {

                            } finally {

                            }
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Toast.makeText(getApplicationContext(),"getDynamicLink:onFailure"+""+e.toString(),Toast.LENGTH_LONG).show();

                        //  Log.e("", "getDynamicLink:onFailure", e);
                    }
                });*/


        tv_version = findViewById(R.id.tv_version);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        pref = new Preferences(this);
        ivLogo = findViewById(R.id.ivLogo);


        ivLogo.setVisibility(View.VISIBLE);
        Animation rotate = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate_scale);
        ivLogo.startAnimation(rotate);
        builder = new AlertDialog.Builder(this);

        if (Utils.isNetworkConnectedMainThred(this)) {

            new HitAPPVersion().execute();
        } else {

            Toasty.error(this, "No Internet Connnection");

        }


    }

    private void updateData() {
        boolean isReferrerDetected = Application.isReferrerDetected(getApplicationContext());
        String firstLaunch = Application.getFirstLaunch(getApplicationContext());
        String referrerDate = Application.getReferrerDate(getApplicationContext());
        String referrerDataRaw = Application.getReferrerDataRaw(getApplicationContext());
        String referrerDataDecoded = Application.getReferrerDataDecoded(getApplicationContext());

        StringBuilder sb = new StringBuilder();
        sb.append("<b>First launch:</b>")
                .append("<br/>")
                .append(firstLaunch)
                .append("<br/><br/>")
                .append("<b>Referrer detection:</b>")
                .append("<br/>")
                .append(referrerDate);
        if (isReferrerDetected) {
            sb.append("<br/><br/>")
                    .append("<b>Raw referrer:</b>")
                    .append("<br/>")
                    .append(referrerDataRaw);

            if (referrerDataDecoded != null) {
                sb.append("<br/><br/>")
                        .append("<b>Decoded referrer:</b>")
                        .append("<br/>")
                        .append(referrerDataDecoded);
            }
        }

        tv_version.setText(Html.fromHtml(sb.toString()));
        tv_version.setMovementMethod(new LinkMovementMethod());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mUpdateReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mUpdateReceiver, new IntentFilter(ReferrerReceiver.ACTION_UPDATE_DATA));
        super.onResume();
    }


    public class HitAPPVersion extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetVersion("GetAppVersion");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, DrawerActivity.class);
                            intent.putExtra("page", "home");
                            startActivity(intent);
                            finish();


                        }
                    }, 2000);

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


}
