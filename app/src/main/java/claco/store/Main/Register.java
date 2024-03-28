package claco.store.Main;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llMain;

    private boolean playAnimations = true;

    public static String OTP;

    //views
    public static EditText etName;
    public static EditText etEmail;
    public static EditText etContact;
    public static EditText etPassword;
    public static EditText etConfirmPassword;
    TextInputLayout referralBox;
    CheckBox checkbox;
    TextView tvRegisterButton;
    TextView tvSignIn,referralBtn;

    CustomLoader loader;
    ImageView ivBack;

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        View llMain = findViewById(R.id.llMain);

        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        llMain.startAnimation(animation);

        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        findId();
    }

    public void findId() {
        llMain = findViewById(R.id.llMain);
        ivBack = findViewById(R.id.ivBack);
        referralBtn = findViewById(R.id.reffral_btn);
        etName = findViewById(R.id.etName);
        referralBox = findViewById(R.id.refrral_box);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        checkbox = findViewById(R.id.checkbox);
        tvRegisterButton = findViewById(R.id.tvRegisterButton);
        tvSignIn = findViewById(R.id.tvSignIn);

        settinglistener();
        referralBtn.setOnClickListener(view -> {

            if(referralBox.getVisibility()==View.VISIBLE){
                referralBox.setVisibility(View.GONE);
                referralBtn.setText("Have Referral code?");
            }else {
                referralBox.setVisibility(View.VISIBLE);
                referralBtn.setText("Don't Have Referral code?");
            }


        });
        ivBack.setOnClickListener(view -> onBackPressed());
    }



    public void settinglistener() {
        tvRegisterButton.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && playAnimations) {
            showOtherItems();
            playAnimations = false;
        }
    }

    private void showOtherItems() {
        float startXhello = 0 - llMain.getWidth();
        float endXhello = llMain.getX();
        ObjectAnimator Animhello = ObjectAnimator.ofFloat(llMain, View.X, startXhello, endXhello);
        Animhello.setDuration(1000);
        llMain.setVisibility(View.VISIBLE);
        Animhello.start();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvRegisterButton:
                if (etName.getText().toString().isEmpty()) {
                    Toasty.warning(this, "Please Enter Name", Toast.LENGTH_LONG, true).show();
                }
             /*  else if(etEmail.getText().toString().isEmpty())
                {
                    Toasty.warning(this, "Please Enter Email", Toast.LENGTH_LONG, true).show();
                }
                else if (!Utils.isEmailValid(etEmail.getText().toString())) {
                    Toasty.warning(this, "Invalid Email", Toast.LENGTH_LONG, true).show();
                }*/
//                else if(etContact.getText().toString().isEmpty())
//                {
//                    Toasty.warning(this, "Please Enter Phone Number", Toast.LENGTH_LONG, true).show();
//                }
//                else if (etContact.getText().toString().trim().length() < 10) {
//
//                    Toasty.warning(this, "Please Enter 10 digit Phone Number", Toast.LENGTH_LONG, true).show();
//                }
//                else if(etPassword.getText().toString().isEmpty())
//                {
//                    Toasty.warning(this, "Please Enter Password", Toast.LENGTH_LONG, true).show();
//                }
               /* else if(etConfirmPassword.getText().toString().isEmpty())
                {
                    Toasty.warning(this, "Please Confirm Password", Toast.LENGTH_LONG, true).show();
                }*/
                /*else if(!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())))
                {
                    Toasty.warning(this, "Password does not match", Toast.LENGTH_LONG, true).show();
                }*/

                else if (etContact.getText().toString().isEmpty()) {
                    Toasty.warning(this, "Please Enter Phone Number", Toast.LENGTH_LONG, true).show();
                } else if (etContact.getText().toString().trim().length() < 10) {

                    Toasty.warning(this, "Please Enter 10 digit Phone Number", Toast.LENGTH_LONG, true).show();
                } else if (etPassword.getText().toString().isEmpty()) {
                    Toasty.warning(this, "Please Enter Password", Toast.LENGTH_LONG, true).show();
                } else if (etConfirmPassword.getText().toString().isEmpty()) {
                    Toasty.warning(this, "Please Confirm Password", Toast.LENGTH_LONG, true).show();
                } else if (!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))) {
                    Toasty.warning(this, "Password does not match", Toast.LENGTH_LONG, true).show();
                } else {
                    if (Utils.isNetworkConnectedMainThred(this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
//                        HitGetOTP register = new HitGetOTP();
//                        register.execute();
                        new HitRegisterApi().execute();
                    } else {
                        Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
                break;

            case R.id.tvSignIn:
                startActivity(new Intent(Register.this, LoginActivity.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_left);

                break;
        }
    }
    //******************************************************************

    public class HitGetOTP extends AsyncTask<String, Void, Void> {

        String displayText;

        String msg;

        @Override
        protected Void doInBackground(String... params) {
            Log.e("GetOTP", "  " + etContact.getText().toString());
            displayText = WebService.GetOTP(etContact.getText().toString(), "getOTP");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy12345", "dddddd");

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonObject = new JSONObject(displayText);

                    JSONObject object = jsonObject.getJSONObject("Response");

                    if (object.getString("Status").equalsIgnoreCase("True")) {
                        OTP = object.getString("otpR");

                        Intent i = new Intent(Register.this, OTP.class)
                                .putExtra("page", "reg")
                                .putExtra("OTP", object.getString("otpR"));
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                    } else {
                        Toasty.error(Register.this, "Phone Number is already registered", Toast.LENGTH_SHORT).show();
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

    public class HitRegisterApi extends AsyncTask<String, Void, Void> {

        String displayText;

        String msg;

        @Override
        protected Void doInBackground(String... params) {
            Log.e("GetOTP", "  " + etContact.getText().toString());
            displayText = WebService.RegistrationApi("" + etContact.getText(), "" + etEmail.getText(),
                    "" + etName.getText(), "" + etPassword.getText(), "", "Registration");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("Registration ", "" + displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonObject = new JSONObject(displayText);
                    JSONObject object = jsonObject.getJSONObject("Response");
                    Log.e("RegistrationObj", "" + object);
                    if (object.getBoolean("Status")) {
                        startActivity(new Intent(Register.this, LoginActivity.class));
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                    } else {
                        Toasty.error(Register.this, "Phone Number is already registered", Toast.LENGTH_SHORT).show();
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
