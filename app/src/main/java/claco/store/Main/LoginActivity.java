package claco.store.Main;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.Activities.ForgotPassword;
import claco.store.R;
import claco.store.databinding.ActivityLogin2Binding;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {


    //Textview
    TextView tvSignupbtn;
    TextView tvLoginButton;
    TextView tvForgotPassword;

    //loader
    CustomLoader loader;

    //LinearLayout
    LinearLayout llmain;
    LinearLayout ll_nmber;
    LinearLayout ll_OTP;
    private boolean playAnimations = true;

    //Textview
    public static EditText etEmail;
    public static TextView etReferCode;
    EditText etPassword;
    TextView haveARefrralCode;
    String ReferCode = "";
    Preferences pref;
ActivityLogin2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
      binding= DataBindingUtil.setContentView(this,R.layout.activity_login2);

        llmain = findViewById(R.id.llmain);
        tvLoginButton = findViewById(R.id.tvLoginButton);

        etEmail = findViewById(R.id.etEmail);

        etPassword = findViewById(R.id.et_Password);

        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        ll_nmber = findViewById(R.id.ll_nmber);
        ll_OTP = findViewById(R.id.ll_OTP);

        pref = new Preferences(this);
        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        View llmain = findViewById(R.id.llmain);
        /*Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        llmain.startAnimation(animation);*/

        tvSignupbtn = findViewById(R.id.tvSignupbtn);
        tvSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Register.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });




        tvLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etEmail.getText().toString().isEmpty()) {

                    etEmail.setError("Enter Valid User Id");
                    etEmail.requestFocus();

                }else if(etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Enter Valid Password");
                    etPassword.requestFocus();
                }else {


                }

                /*else if (etPassword.getText().toString().isEmpty()) {

                    Toasty.warning(LoginActivity.this, "Please Enter Password", Toast.LENGTH_LONG, true).show();
                } */ /*else {*/
                   /* if (Utils.isNetworkConnectedMainThred(LoginActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
//                        HitUserLogin login = new HitUserLogin();
//                        login.execute();

                        new HitLoginOTPNew().execute();

//                        HitGetLoginOTP login = new HitGetLoginOTP();
//                        login.execute();
                    } else {
                        Toasty.error(LoginActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                    }*/
                }

        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && playAnimations) {
            showOtherItems();
            playAnimations = false;
        }
    }


    public class HitUserLogin extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            // displayText = WebService.GetCategory("-1", "GetCategory");
            displayText = WebService.Login(etEmail.getText().toString(), etPassword.getText().toString(), "Login");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");

                    // Log.e("jarray123", "" + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("Response").equalsIgnoreCase("true")) {
                            pref.set(AppSettings.CustomerID, object.getString("CustomerID"));
                            pref.set(AppSettings.Phone1, object.getString("Phone1"));
                            pref.set(AppSettings.firstName, object.getString("firstName"));
                            pref.set(AppSettings.Gender, object.getString("Gender"));
                            pref.set(AppSettings.Email, object.getString("Email"));
//                            pref.set(AppSettings.ReferralValue, object.getString("referCode"));
                            pref.commit();
                            Toasty.success(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DrawerActivity.class).putExtra("page", "home"));
                        } else {
                            if (jsonArray != null && jsonArray.length() != 0) {
                                JSONObject objectq = jsonArray.getJSONObject(0);
                                Toasty.error(LoginActivity.this, objectq.getString("CustomerID"), Toast.LENGTH_SHORT, true).show();
                            } else {
                                Toasty.error(LoginActivity.this, " Some thing went wrong", Toast.LENGTH_SHORT, true).show();
                            }

                        }
                    }
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

    public class HitGetLoginOTP extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            // displayText = WebService.GetCategory("-1", "GetCategory");
            displayText = WebService.HitGetLoginOTP(etEmail.getText().toString(), "getOTPLogin");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONObject jsonArray = jsonObject.getJSONObject("Response");

                    // Log.e("jarray123", "" + jsonArray);
                    // {"Response":{"Status":"True","otpR":"7547"}}

///CustomerID
                    if (jsonArray.getString("Status").equalsIgnoreCase("True")) {
                        // jsonArray.getString("otpR")
                        // Toasty.success(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                        // ll_nmber.setVisibility(View.GONE);
                        //ll_OTP.setVisibility(View.VISIBLE);
                        Log.e("sfndfhjdkf", "///" + ReferCode);
                        startActivity(new Intent(LoginActivity.this, OTP.class)
                                .putExtra("page", "login")
                                .putExtra("referCode", ReferCode)
                                .putExtra("OTP", jsonArray.getString("otpR"))
                        );
                        // finish();
                    } else {
                        HitGetOTP HitGetOTP = new HitGetOTP();
                        HitGetOTP.execute();
                        /// T oasty.error(LoginActivity.this,""+  jsonArray.getString("Status"), Toast.LENGTH_SHORT, true).show();


                    }

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

    public class HitGetOTP extends AsyncTask<String, Void, Void> {

        String displayText;

        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetOTP(LoginActivity.etEmail.getText().toString(), "getOTP");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("getResndOTP", "getResndOTP");

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject object = jsonObject.getJSONObject("Response");

                    if (object.getString("Status").equalsIgnoreCase("True")) {

                        startActivity(new Intent(LoginActivity.this, OTP.class)
                                .putExtra("page", "Reg")
                                .putExtra("referCode", ReferCode)
                                .putExtra("mob", LoginActivity.etEmail.getText().toString())
                                .putExtra("OTP", object.getString("otpR"))
                        );
                        Toasty.success(LoginActivity.this, "Otp send to your registered mobile number ", Toast.LENGTH_SHORT).show();
                        //requestSMSPermission();
                        // new SmsReceiver().setEditText(et1,et2,et3,et4);
                        //  fetchOTP();
                    } else {
                        // Toasty.error(LoginActivity.this,"Phone Number is already registered",Toast.LENGTH_SHORT).show();
                    }

                    Log.e("jsonobject123", jsonObject.toString());
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

    private void showOtherItems() {
        float startXhello = 0 - llmain.getWidth();
        float endXhello = llmain.getX();
        ObjectAnimator Animhello = ObjectAnimator.ofFloat(llmain, View.X, startXhello, endXhello);
        Animhello.setDuration(1000);
        llmain.setVisibility(View.VISIBLE);
        Animhello.start();
    }


    public class HitLoginOTPNew extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) { // displayText = WebService.GetCategory("-1", "GetCategory");
            displayText = WebService.HitGetLoginNEW(etEmail.getText().toString(), "Registration1");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("Registration1 ", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONObject jsonArray = jsonObject.getJSONObject("Response");

                     Log.e("Registration1 ", "" + jsonArray);
                    // {"Response":{"Status":"True","otpR":"7547"}}

///CustomerID
                    if (jsonArray.getString("Status").equalsIgnoreCase("True")) {
                        // jsonArray.getString("otpR")
                        // Toasty.success(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                        // ll_nmber.setVisibility(View.GONE);
                        //ll_OTP.setVisibility(View.VISIBLE);
                        Log.e("sfndfhjdkf", "///" + ReferCode);
                        startActivity(new Intent(LoginActivity.this, OTP.class)
                                .putExtra("page", "login")
                                .putExtra("referCode", ReferCode)
                                .putExtra("OTP", jsonArray.getString("otp"))
                        );
                        // finish();
                    } else {
                        HitGetOTP HitGetOTP = new HitGetOTP();
                        HitGetOTP.execute();
                        /// T oasty.error(LoginActivity.this,""+  jsonArray.getString("Status"), Toast.LENGTH_SHORT, true).show();


                    }

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
