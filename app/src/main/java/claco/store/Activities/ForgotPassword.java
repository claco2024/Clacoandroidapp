package claco.store.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.Main.LoginActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ForgotPassword extends AppCompatActivity {

    EditText etPhone;
    TextView tvSubmit;
    CustomLoader loader;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etPhone = findViewById(R.id.etPhone);
        tvSubmit = findViewById(R.id.tvSubmit);

        ivBack = findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
//                overridePendingTransition(R.anim.enter_from_right,R.anim.enter_from_left);
//                finish();
            }
        });

        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        tvSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (etPhone.getText().toString().isEmpty()) {
                    Toasty.warning(ForgotPassword.this, "Please Enter Phone Number", Toast.LENGTH_LONG, true).show();
                } else {
                    if (Utils.isNetworkConnectedMainThred(ForgotPassword.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitForgotPwd().execute();
                    } else {
                        Toasty.error(ForgotPassword.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }
        });


    }

    public class HitForgotPwd extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.ForgotPassword(etPhone.getText().toString(), "ForgetPassword");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject resopnse = jsonObject.getJSONObject("Response");


//                    if (resopnse.getString("Status").equalsIgnoreCase("true")) {
                    if (resopnse.getString("Response").equalsIgnoreCase("true")) {

//                        Toasty.success(ForgotPassword.this,"Your current password is "+
//                                resopnse.getString("otpR"),Toast.LENGTH_SHORT).show();

                        Toasty.success(ForgotPassword.this, "Your current password has been sent to your registered mobile number.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_left);
                        finish();

                    } else {

                        Toasty.error(ForgotPassword.this, "This phone number is not registered !", Toast.LENGTH_SHORT).show();


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

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
//        overridePendingTransition(R.anim.enter_from_right,R.anim.enter_from_left);
        finish();
    }
}
