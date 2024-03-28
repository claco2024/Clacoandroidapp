package claco.store.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.R;
import claco.store.customecomponent.CustomEdittext;
import claco.store.util.CustomLoader;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class MembershipApplication extends AppCompatActivity {
    //Textview
    TextView tvHeaderText;

    //EditText
    EditText edName;
    EditText edMobile;
    EditText edLocality;
    EditText edPincode;
    EditText edLandmark;
    EditText edAddress,etMemberShip,etPaymentMode;
    CustomEdittext Alternatemobileno;
    //Radio button
    RadioButton rhome ,  roffice  , rother;
    //Spinner
    Spinner spinnerState;
    EditText spinnerCity;

    Button btnSave;


    CustomLoader loader;


    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> cityList = new ArrayList<>();

    String stateId,AddressType;



    Preferences pref;

    ImageView iv_menu;

    String from,memebership,paymentmode;





@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_application);
paymentmode= getIntent().getStringExtra("PaymentMode");
memebership= getIntent().getStringExtra("member");

        findID();

        from=getIntent().getStringExtra("from");

        //settext


        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);


    iv_menu=findViewById(R.id.iv_menu);
    iv_menu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MembershipApplication.this, MyAccountActivity.class));
            finish();
        }
    });


        btnSave.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        Validation();

        }
        });

        }
public void findID() {
        tvHeaderText = findViewById(R.id.tvHeaderText);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerCity = findViewById(R.id.spinnerCity);
        edName = findViewById(R.id.edName);
        edMobile = findViewById(R.id.edMobile);
        edLocality = findViewById(R.id.edLocality);
        edPincode = findViewById(R.id.edPincode);
        edLandmark = findViewById(R.id.edLandmark);
        edAddress = findViewById(R.id.edAddress);
        Alternatemobileno = findViewById(R.id.edAlternatemobileno);
    etMemberShip = findViewById(R.id.etMemberShip);
    etMemberShip.setText(memebership);
    etMemberShip.setEnabled(false);
        rhome = findViewById(R.id.rhome);
        roffice = findViewById(R.id.roffice);
        rother = findViewById(R.id.rother);
        //RAddresstype
        btnSave=findViewById(R.id.btnSave);
        pref=new Preferences(this);

        }
public class HitSubmit extends AsyncTask<String, Void, Void> {

    String displayText;
    String msg;

    @Override
    protected Void doInBackground(String... params) {

        displayText = WebService.AddMembershiptype(etMemberShip.getText().toString(),paymentmode,"",edName.getText().toString(),
                edMobile.getText().toString(),edLocality.getText().toString(),edAddress.getText().toString(), "AddMembershiptype");
        Log.e("AddMembershiptype", "req "+etMemberShip.getText().toString()+  ", "+paymentmode+  ", "+   ", "+edName.getText().toString()+  ", "+
                edMobile.getText().toString()+  ", "+edLocality.getText().toString()+  ", "+edAddress.getText().toString());
        Log.e("AddMembershiptype", displayText);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        loader.dismiss();


        if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
            try {
//"":false,"Msg":
                JSONObject jsonObject = new JSONObject(displayText);
                if (jsonObject.has("Status")) {

 if (jsonObject.getBoolean("Status")){
     Toasty.success(MembershipApplication.this, ""+jsonObject.getString("Msg"), Toast.LENGTH_LONG, true).show();
finish(); } else {
     Toasty.error(MembershipApplication.this, ""+jsonObject.getString("Msg"), Toast.LENGTH_LONG, true).show();

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


    public void Validation()
    {
        if (edName.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please Enter Name", Toast.LENGTH_LONG, true).show();
        }
        else if(edMobile.getText().toString().isEmpty() || edMobile.getText().toString().length()!=10)
        {
            Toasty.warning(this, "Please Enter Valid Mobile Number", Toast.LENGTH_LONG, true).show();
        }
        else if(edLocality.getText().toString().isEmpty())
        {
            Toasty.warning(this, "Please Enter Email", Toast.LENGTH_LONG, true).show();
        }
        else if(edAddress.getText().toString().isEmpty())
        {
            Toasty.warning(this, "Please Enter Password", Toast.LENGTH_LONG, true).show();
        }
//        else if(spinnerState.getSelectedItemPosition()==0)
//        {
//            Toasty.warning(this, "Please Select etMemberShip", Toast.LENGTH_LONG, true).show();
//        }
//        else if(edPincode.getText().toString().isEmpty())
//        {
//            Toasty.warning(this, "Please Enter Pincode", Toast.LENGTH_LONG, true).show();
//        }  //rother
//        else if(!roffice.isChecked() && !rhome.isChecked() && !rother.isChecked())
//        {
//            Toasty.warning(this, "Please Select Address Type", Toast.LENGTH_LONG, true).show();
//        }
        else
        {

            if (Utils.isNetworkConnectedMainThred(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(true);
                HitSubmit submit= new HitSubmit();
                submit.execute();
            }
            else {
                Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
            }
        }

    }
}
