package claco.store.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class ProfilePageActivity extends AppCompatActivity {


    //Textview
    TextView tvHeaderText;
    TextView tvSubmit;

    //EditText
    EditText edName;
    EditText edEmail;
    EditText etContact;

    //RelativeLayout
    RelativeLayout rrMen;
    RelativeLayout rrWomen;
    RelativeLayout rlChangePwd;


    //Imageview
   ImageView ivMen;
   ImageView ivWomen;

    ImageView iv_menu;

   //Pref
    Preferences pref;

    //Custom Loader
    CustomLoader loader;

    String gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        tvHeaderText=findViewById(R.id.tvHeaderText);
        tvSubmit=findViewById(R.id.tvSubmit);
        tvHeaderText.setText("Update Profile");

        edName=findViewById(R.id.edName);
        edEmail=findViewById(R.id.edEmail);
        etContact=findViewById(R.id.etContact);

        rrMen=findViewById(R.id.rrMen);
        rrWomen=findViewById(R.id.rrWomen);
        ivMen = (ImageView) findViewById(R.id.imageView11);
        ivWomen = (ImageView) findViewById(R.id.imageView12);
        rlChangePwd=findViewById(R.id.rlChangePwd);

        iv_menu=findViewById(R.id.iv_menu);

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfilePageActivity.this, MyAccountActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);



      pref=new Preferences(this);


      Log.v("22224444444",pref.get(AppSettings.CustomerID));


        try
        {
            //setvalues
            etContact.setText(pref.get(AppSettings.Phone1));
            edName.setText(pref.get(AppSettings.firstName));
            edEmail.setText(pref.get(AppSettings.Email));

            if(!(pref.get(AppSettings.Gender).equalsIgnoreCase("")))
            {
                Log.v("Gender","   Gender  "+ pref.get(AppSettings.Gender));

                if(pref.get(AppSettings.Gender).equalsIgnoreCase("Male"))
                {
                    ivMen.setImageResource(R.drawable.ic_check_box_selected);
                    ivWomen.setImageResource(R.drawable.ic_check_box_unselected);
                    gender = "Male";
                }
                else
                {
                    ivMen.setImageResource(R.drawable.ic_check_box_unselected);
                    ivWomen.setImageResource(R.drawable.ic_check_box_selected);
                    gender = "Female";
                }
            }

        }

        catch (Exception e)
        {
            Log.e("error",e.toString());
        }


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edName.getText().toString().isEmpty()) {

                    Toasty.warning(ProfilePageActivity.this, "Please Enter Name", Toast.LENGTH_LONG, true).show();
                }
                else if(gender.equalsIgnoreCase(""))
                {
                    Toasty.warning(ProfilePageActivity.this, "Please Select Gender", Toast.LENGTH_LONG, true).show();
                }
                else if (edEmail.getText().toString().isEmpty()) {

                    Toasty.warning(ProfilePageActivity.this, "Please Enter Email", Toast.LENGTH_LONG, true).show();
                }
                else
                {
                    if (Utils.isNetworkConnectedMainThred(ProfilePageActivity.this)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);

                        HitUpdateProfile updateProfile = new HitUpdateProfile();
                        updateProfile.execute();
                    }
                    else {
                        Toasty.error(ProfilePageActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }
        });


        rlChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfilePageActivity.this, ChangePasswordActivity.class));
            }
        });

        rrMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivMen.setImageResource(R.drawable.ic_check_box_selected);
                ivWomen.setImageResource(R.drawable.ic_check_box_unselected);
                gender = "Male";
            }
        });


        rrWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivMen.setImageResource(R.drawable.ic_check_box_unselected);
                ivWomen.setImageResource(R.drawable.ic_check_box_selected);
              gender = "Female";
            }
        });
    }

    public class HitUpdateProfile extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.UpdateProfile(pref.get(AppSettings.CustomerID),edName.getText().toString(), edEmail.getText().toString(),etContact.getText().toString(),gender, "UpdateProfile");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyyUpdate", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray array = jsonObject.getJSONArray("Response");

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject ob=array.getJSONObject(i);

                        if (ob.getString("Status").equalsIgnoreCase("true")) {

                            pref.set(AppSettings.Phone1, ob.getString("MobNo"));
                            pref.set(AppSettings.firstName, ob.getString("FirstName"));
                            pref.set(AppSettings.Gender, ob.getString("Gender"));
                            pref.set(AppSettings.Email, ob.getString("EmailId"));
                            pref.commit();
                            Toasty.success(ProfilePageActivity.this, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfilePageActivity.this, MyAccountActivity.class));
                        } else {
                            Toasty.warning(ProfilePageActivity.this, ""+ob.getString("Status"), Toast.LENGTH_SHORT).show();

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

}
