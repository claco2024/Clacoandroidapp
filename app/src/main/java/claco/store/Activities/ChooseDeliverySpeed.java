package claco.store.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


public class ChooseDeliverySpeed extends AppCompatActivity {

    //RadioButton
    RadioButton rbNormal;
    RadioButton rbPrime;
    RadioButton rbSuper;


    //Textview
    TextView tvHeaderText;
    TextView tvDate;
    TextView tvSubmit;
    TextView tvDeliverySlot;
    TextView tvDeliverySlot1;
    TextView tvDeliverySlot2;

    Spinner spinnerTimeSlot;


    //Imageview
    ImageView iv_menu;

    CustomLoader loader;

    ArrayList<String>deliveryslot=new ArrayList<>();
    ArrayList<String>deliveryslot1=new ArrayList<>();
    ArrayList<String>deliveryslot2=new ArrayList<>();

    public static String deliveryId;

    public static  String DeliveryFixCharge;
    public static  String DeliveryPerKgCharge;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat dateFormat;

    public static String current_date;
    public static String time_slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery_speed);

        rbNormal = findViewById(R.id.rbNormal);
        rbPrime = findViewById(R.id.rbPrime);
        rbSuper = findViewById(R.id.rbSuper);
        tvDate = findViewById(R.id.tvDate);
        spinnerTimeSlot = findViewById(R.id.spinnerTimeSlot);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        current_date = dateFormat.format(myCalendar.getTime());

       // tvDate.setText(current_date);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(tvDate,myCalendar);
            }

        };

        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        tvHeaderText   = findViewById(R.id.tvHeaderText);
        tvSubmit       = findViewById(R.id.tvSubmit);
        tvDeliverySlot = findViewById(R.id.tvDeliverySlot);
        tvDeliverySlot1 = findViewById(R.id.tvDeliverySlot1);
        tvDeliverySlot2 = findViewById(R.id.tvDeliverySlot2);
        tvHeaderText.setText(R.string.app_name);

        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseDeliverySpeed.this, MyCartActivity.class));
                ///overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
            }
        });

        rbNormal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbNormal.setChecked(true);
                rbPrime.setChecked(false);
                rbSuper.setChecked(false);
                deliveryId=""+rbNormal.getTag();
                DeliveryFixCharge=""+rbNormal.getTag(R.id.rbNormal);
                DeliveryPerKgCharge=""+rbNormal.getTag(R.id.charges);

            }
        });

        rbPrime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbSuper.setChecked(false);
                rbPrime.setChecked(true);
                rbNormal.setChecked(false);

                deliveryId=""+rbPrime.getTag();
                DeliveryFixCharge=""+rbPrime.getTag(R.id.rbPrime);
                DeliveryPerKgCharge=""+rbPrime.getTag(R.id.charges);

            }
        });

        rbSuper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbNormal.setChecked(false);
                rbPrime.setChecked(false);
                rbSuper.setChecked(true);

                deliveryId=""+rbSuper.getTag();
                DeliveryFixCharge=""+rbSuper.getTag(R.id.rbSuper);

                DeliveryPerKgCharge=""+rbSuper.getTag(R.id.charges);

            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (tvDate.getText().toString().trim().equals("Select Date")) {
                        Toasty.error(ChooseDeliverySpeed.this, "Select Delivery Date").show();
                    } else {
                        current_date = tvDate.getText().toString();
                        time_slot = spinnerTimeSlot.getSelectedItem().toString();
                        startActivity(new Intent(ChooseDeliverySpeed.this, ConfirmAddressActivity.class));
                       // overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                    }
                }catch (Exception e) {}

            }
        });


        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   new DatePickerDialog(ChooseDeliverySpeed.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseDeliverySpeed.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        if (Utils.isNetworkConnectedMainThred(ChooseDeliverySpeed.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            new HitDeliverySpeed().execute();
            new HitDeliverySlot().execute();
        }
        else {
            Toasty.error(ChooseDeliverySpeed.this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }



    private void updateLabel(TextView textView,Calendar calendar) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        textView.setText(sdf.format(calendar.getTime()));
    }

    public class HitDeliverySpeed extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetDeliveryType("GetDeliveryType");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray array=jsonObject.getJSONArray("Response");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String Deliverytype=object.getString("DeliveryType");

                        if(i == 0)
                        {
                            rbNormal.setText(Deliverytype);
                            rbNormal.setTag(object.getString("DeliveryId"));
                            deliveryId=""+rbNormal.getTag();
                            rbNormal.setTag(R.id.rbNormal,object.getString("DeliveryFixCharge"));
                            DeliveryFixCharge=object.getString("DeliveryFixCharge");
                            DeliveryPerKgCharge=object.getString("DeliveryPerKgCharge");
                            rbNormal.setTag(R.id.charges,object.getString("DeliveryPerKgCharge"));

                        }
                        else if(i==1)
                        {
                            rbPrime.setText(Deliverytype);
                            rbPrime.setTag(object.getString("DeliveryId"));
                            rbPrime.setTag(R.id.rbPrime,object.getString("DeliveryFixCharge"));
//                            DeliveryFixCharge=object.getString("DeliveryFixCharge");
//                            DeliveryPerKgCharge=object.getString("DeliveryPerKgCharge");
                            rbPrime.setTag(R.id.charges,object.getString("DeliveryPerKgCharge"));
                        }
                        else
                        {
                            rbSuper.setText(Deliverytype);
                            rbSuper.setTag(object.getString("DeliveryId"));
                            rbSuper.setTag(R.id.rbSuper,object.getString("DeliveryFixCharge"));
                            rbSuper.setTag(R.id.charges,object.getString("DeliveryPerKgCharge"));

//                            DeliveryFixCharge=object.getString("DeliveryFixCharge");
//                            DeliveryPerKgCharge=object.getString("DeliveryPerKgCharge");
                        }
                    }
                    //  Log.e("response1111",""+jsonObject);

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

    public class HitDeliverySlot extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.GetDeliverySlot( "getDeliveryTimeSlot");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy1234", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    Log.e("listing",""+jsonObject);

                    JSONArray jsonArray = jsonObject.getJSONArray("getTimeSlotResponse");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        HashMap<String,String> map=new HashMap<>();

                        map.put("DeliveryType",jsonObject1.getString("DeliveryType"));
                        map.put("Name",jsonObject1.getString("Name"));

                        deliveryslot.add(jsonObject1.getString("Name"));

                     /*   if(jsonObject1.getString("DeliveryType").equals("1"))
                        {
                            deliveryslot.add(jsonObject1.getString("Name"));
                        }
                        if(jsonObject1.getString("DeliveryType").equals("2"))
                        {
                            deliveryslot1.add(jsonObject1.getString("Name"));
                        }
                        if(jsonObject1.getString("DeliveryType").equals("3"))
                        {
                            deliveryslot2.add(jsonObject1.getString("Name"));
                        }*/


                        ArrayAdapter genderAdapter = new ArrayAdapter(ChooseDeliverySpeed.this,android.R.layout.simple_spinner_item,deliveryslot);
                        spinnerTimeSlot.setAdapter(genderAdapter);


                    }

                    for (int i=0;i<=deliveryslot.size();i++)
                    {
                        tvDeliverySlot.setText(tvDeliverySlot.getText().toString()+"\n"+deliveryslot.get(i));
                    }

                    for (int i=0;i<=deliveryslot1.size();i++)
                    {
                        tvDeliverySlot1.setText(tvDeliverySlot1.getText().toString()+"\n"+deliveryslot1.get(i));
                    }

                    for (int i=0;i<=deliveryslot2.size();i++)
                    {
                        tvDeliverySlot2.setText(tvDeliverySlot2.getText().toString()+"\n"+deliveryslot2.get(i));
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
