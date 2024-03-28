package claco.store.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class OTP extends AppCompatActivity {


    TextView tvTimer, tvResendOtp, tvSubmit, tvMobileNumber;

    ImageView ivBack;

    public static    EditText et1, et2, et3, et4;

    String OTP;
    String referCode;
    Boolean isResend=false;

    JSONObject jsonObject;
    JSONArray jsonArray = null;
    String phone;

    Preferences pref;

    CustomLoader loader;

    LinearLayout rr_main;
 String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
          try{
               from = getIntent().getStringExtra("page");
          }catch (Exception e){

          }

        getId();
          if (from!=null && from.equalsIgnoreCase("login"))
          {
              OTP=getIntent().getStringExtra("OTP");
              referCode=getIntent().getStringExtra("referCode");
              tvMobileNumber.setText("+91-" + LoginActivity.etEmail.getText().toString());

//              String[] separated = OTP.split("(?!^)");
//              et1.setText(separated[0]);
//              et2.setText(separated[1]);
//              et3.setText(separated[2]);
//              et4.setText(separated[3]);

          } else if (from!=null && from.equalsIgnoreCase("Reg"))
          {
              OTP=getIntent().getStringExtra("OTP");
              referCode=getIntent().getStringExtra("referCode");
              tvMobileNumber.setText("+91-" + LoginActivity.etEmail.getText().toString());
              String[] separated = OTP.split("(?!^)");
              et1.setText(separated[0]);
              et2.setText(separated[1]);
              et3.setText(separated[2]);
              et4.setText(separated[3]);

          }else {
              OTP=Register.OTP;
              String[] separated = OTP.split("(?!^)");

              et1.setText(separated[0]);
              et2.setText(separated[1]);
              et3.setText(separated[2]);
              et4.setText(separated[3]);
             // tvMobileNumber.setText("+91-" + Register.etContact.getText().toString());
          }

        textWatcher();

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  if (from!=null && from.equalsIgnoreCase("Login")){
                    Intent i=new Intent(OTP.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                   /// overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
//                } else {
//                    Intent i=new Intent(OTP.this,Register.class);
//                    startActivity(i);
//                     finish();
//                    overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
//                }



            }
        });


        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnectedMainThred(OTP.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    HitGetOTP register = new HitGetOTP();
                    register.execute();
                }
                else {
                    Toasty.error(OTP.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
    private void getId()
    {

        tvSubmit = findViewById(R.id.tvSubmit);
        ivBack = findViewById(R.id.ivBack);
        tvResendOtp = findViewById(R.id.tvResendOtp);
        tvTimer = findViewById(R.id.tvTimer);
        tvMobileNumber = findViewById(R.id.tvMobileNumber);

        pref = new Preferences(this);
        rr_main = findViewById(R.id.rr_main);

        //loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
    }
    private void validation() {
        if (et1.getText().toString().isEmpty()) {
            et1.setError(getString(R.string.cannot_be_blank));
        } else if (et2.getText().toString().isEmpty()) {
            et2.setError(getString(R.string.cannot_be_blank));
        } else if (et3.getText().toString().isEmpty()) {
            et3.setError(getString(R.string.cannot_be_blank));
        } else if (et4.getText().toString().isEmpty()) {
            et4.setError(getString(R.string.cannot_be_blank));
        } else {
            String  otp = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
            if (OTP.trim().equalsIgnoreCase(otp)) {
                Log.e("matched", "OTP matched");
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                 if(from!=null && from.equalsIgnoreCase("Login")){
                     HitUserLogin register = new HitUserLogin();
                     register.execute();
                 } else {
                     HitUserRegister register = new HitUserRegister();
                     register.execute();
                 }

            }
            else {
                Toasty.error(OTP.this, "Invalid OTP, please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void textWatcher() {

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    et1.clearFocus();
                    et2.requestFocus();
                    et2.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et2.clearFocus();
                    et1.requestFocus();
                    et1.setCursorVisible(true);
                } else {
                    et2.clearFocus();
                    et3.requestFocus();
                    et3.setCursorVisible(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et3.clearFocus();
                    et2.requestFocus();
                    et2.setCursorVisible(true);
                } else {
                    et3.clearFocus();
                    et4.requestFocus();
                    et4.setCursorVisible(true);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et4.clearFocus();
                    et3.requestFocus();
                    et3.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public class HitUserRegister extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.Register(
                  /*  Register.etName.getText().toString(),
                    Register.etEmail.getText().toString(),*/
                    getIntent().getStringExtra("mob"),
                    referCode,
                    /*Register.etPassword.getText().toString()
                    ,Register.etConfirmPassword.getText().toString(),*/
                    "Registration");
            Log.e("sfndfhjdkf", getIntent().getStringExtra("referCode")+"///"+referCode);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonObject = new JSONObject(displayText);

                    Log.e("jsonobject123",jsonObject.toString());
                    if(jsonObject.has("Response"))
                    {
                        JSONObject jsonResponse=jsonObject.getJSONObject("Response");
                        if(jsonResponse.getString("status1").equalsIgnoreCase("True"))
                        {
                           // startActivity(new Intent(OTP.this, DrawerActivity.class).putExtra("page", "home"));
                          //  startActivity(new Intent(OTP.this, LoginActivity.class));
                                     //.putExtra("page", "home"));

//                            pref.set(AppSettings.CustomerID,jsonResponse.getString("CustomerId"));
//
//
//                            pref.set(AppSettings.Phone1, jsonResponse.getString("Mobile"));
//                            pref.set(AppSettings.firstName, jsonResponse.getString("Name"));
//                            pref.set(AppSettings.Gender, "");
//                            pref.set(AppSettings.Email, jsonResponse.getString("Email"));
//                            pref.commit();

                           // finishAffinity();

                            HitUserLogin register = new HitUserLogin();
                            register.execute();
                           // Toasty.success(OTP.this,"Registration Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else if(jsonResponse.getString("status1").equalsIgnoreCase("2"))
                        {
                            Toasty.error(OTP.this," Phone number already exists" , Toast.LENGTH_SHORT, true).show();
                        }
                        else
                        {
                            Toasty.error(OTP.this," " +jsonResponse.getString("status1"), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }
                catch (Exception e) {
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
             if (from!=null && from.equalsIgnoreCase("Login")){
                 displayText = WebService.HitGetLoginOTP(LoginActivity.etEmail.getText().toString(),  "getOTPLogin");
             } else {
                 displayText = WebService.GetOTP(getIntent().getStringExtra("mob"), "getOTP");
             }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("getResndOTP", "getResndOTP");

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonObject = new JSONObject(displayText);

                    JSONObject object=jsonObject.getJSONObject("Response");

                    if(object.getString("Status").equalsIgnoreCase("True"))
                    {
                        OTP=object.getString("otpR");
                        isResend =true;
                        String[] separated = OTP.split("(?!^)");

                        et1.setText(separated[0]);
                        et2.setText(separated[1]);
                        et3.setText(separated[2]);
                        et4.setText(separated[3]);
                        Toasty.success(OTP.this,"Otp send to your registered mobile number ",Toast.LENGTH_SHORT).show();
                       //requestSMSPermission();
                       // new SmsReceiver().setEditText(et1,et2,et3,et4);
                 //  fetchOTP();
                    }
                    else
                    {
                        Toasty.error(OTP.this,"Phone Number is already registered",Toast.LENGTH_SHORT).show();
                    }

                    Log.e("jsonobject123",jsonObject.toString());
                }
                catch (Exception e) {
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
    private void requestSMSPermission()
    {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list,1);
        }
    }

    public class HitUserLogin extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            // displayText = WebService.GetCategory("-1", "GetCategory");
            displayText = WebService.Login(LoginActivity.etEmail.getText().toString(), "" , "Login");
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
                            pref.commit();
                            //todo save refer
                            Toasty.success(OTP.this,"Login Successful",Toast.LENGTH_SHORT).show();
                           // if (object.getString("firstName")!=null && !object.getString("firstName").isEmpty()){
                                startActivity(new Intent(OTP.this, DrawerActivity.class).putExtra("page", "home"));
                           // }
                    //ProfilePageActivity

                        }
                        else  if ( object.getString("Response").equalsIgnoreCase("false")
                      &&   object.getString("CustomerID").equalsIgnoreCase("invalid user id and password!!") ){

                             if (!LoginActivity.etEmail.getText().toString().isEmpty() &&
                                     LoginActivity.etEmail.getText().toString().startsWith("6"))

                            Toasty.error(OTP.this, "Invalid Credentials", Toast.LENGTH_SHORT, true).show();


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


//    public class SmsReceiver extends BroadcastReceiver {
//        EditText e1;
//        EditText e2;
//        EditText e3;
//        EditText e4;
//
//        public void setEditText(EditText e1,  EditText e2, EditText e3,EditText e4)
//        {
////        SmsReceiver.e1 = e1;
////        SmsReceiver.e2 = e2;
////        SmsReceiver.e3 = e3;
////        SmsReceiver.e4 = e4;
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
//            String message = messages[0].getMessageBody();
//            Log.e("messages", "messages" + messages);
//            String numberOnly = message.replaceAll("[^0-9]", "");
//            Toast.makeText(context, "" + numberOnly, Toast.LENGTH_LONG).show();
//
//           // et1, et2, et3, et4;
//          e1.setText(Character.digit(numberOnly.charAt(0), 10));
//            e2.setText(Character.digit(numberOnly.charAt(1), 10));
//            e3.setText(Character.digit(numberOnly.charAt(2), 10));
//           e4.setText(Character.digit(numberOnly.charAt(3), 10));
//
//
//        }
//
//    }

}
