package claco.store.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.Main.LoginActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import static android.widget.Toast.LENGTH_SHORT;

public class ChangePasswordActivity extends AppCompatActivity {


    TextView etEmail;
    TextView etContact;
    TextView tvSubmit;
    TextView tvHeaderText;

    EditText etPassword;
    EditText etNewPassword;
    EditText etConfirmPassword;


    Preferences pref;

    CustomLoader loader;

    ImageView iv_menu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);

        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        pref = new Preferences(this);

        initialize();

        tvHeaderText.setText("Change Password");


        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this, ProfilePageActivity.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        etEmail.setText(pref.get(AppSettings.Email));

        etContact.setText(pref.get(AppSettings.Phone1));

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnectedMainThred(ChangePasswordActivity.this)) {
                    if (etPassword.getText().toString().isEmpty()) {
                        Toasty.warning(ChangePasswordActivity.this, "Please enter old password", Toast.LENGTH_SHORT).show();
                    } else if (etNewPassword.getText().toString().isEmpty()) {
                        Toasty.warning(ChangePasswordActivity.this, "Please enter new password", Toast.LENGTH_SHORT).show();
                    } else if (etConfirmPassword.getText().toString().isEmpty()) {
                        Toasty.warning(ChangePasswordActivity.this, "Please reconfirm password", Toast.LENGTH_SHORT).show();
                    } else if (!(etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString()))) {
                        Toasty.warning(ChangePasswordActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    } else {
                        loader.show();
                        HitChangePwd pwd = new HitChangePwd();
                        pwd.execute();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "No Internet Connection.", LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initialize() {
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvSubmit = findViewById(R.id.tvSubmit);
        tvHeaderText = findViewById(R.id.tvHeaderText);


        iv_menu = findViewById(R.id.iv_menu);
    }

    public class HitChangePwd extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.Changepassword(pref.get(AppSettings.Phone1), etPassword.getText().toString(), etConfirmPassword.getText().toString(), "ChangePassword");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyyUpdate", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONObject ob = jsonObject.getJSONObject("Response");
                    if (ob.getString("Status").equalsIgnoreCase("true")) {
                        Toasty.success(ChangePasswordActivity.this, "Password Changed Succesfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        pref.set(AppSettings.CustomerID, "");
                        pref.commit();
                        //Toasty.success(ChangePasswordActivity.this, "Logged out..!!!", Toast.LENGTH_SHORT).show();
                        finishAffinity();

                     //   startActivity(new Intent(ChangePasswordActivity.this, ProfilePageActivity.class));
                    } else {
                        Toasty.error(ChangePasswordActivity.this, "Password is not correct", Toast.LENGTH_SHORT).show();
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