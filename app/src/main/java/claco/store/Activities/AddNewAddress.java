package claco.store.Activities;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import claco.store.R;
import claco.store.databinding.ActivityAddNewAddressBinding;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class AddNewAddress extends AppCompatActivity implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    //Textview
    TextView tvHeaderText;

    //EditText
    TextInputEditText edName, edMobile, edLocality, edPincode, edLandmark, edAddress, Alternatemobileno, spinnerCity;

    //Radio button
    RadioButton rhome, roffice, rother;
    //Spinner
    Spinner spinnerState;

    MaterialButton btnSave;
    CustomLoader loader;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> cityList = new ArrayList<>();
    String stateId, AddressType;
    Preferences pref;
    String from;
    private FusedLocationProviderClient fusedLocationClient;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private ActivityAddNewAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViews();
    }

    private void setupViews() {
        findID();

        from = getIntent().getStringExtra("from");

        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyA3CNuWvHEjGMzlkcmS5EPqesrg0t1kX00", Locale.US);
        }

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.containerSearch.setOnClickListener(v -> {
            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

        // check if GPS enabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getLocationPerms();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        fusedLocationClient = getFusedLocationProviderClient(this);

//        getFusedLocation();

        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            HitState state = new HitState();
            state.execute();
        } else {
            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                stateId = arrayList.get(position).get("StateId");
//                if (Utils.isNetworkConnectedMainThred(AddNewAddress.this)) {
//                    loader.show();
//                    loader.setCancelable(false);
//                    loader.setCanceledOnTouchOutside(true);
//                   // HitCity city = new HitCity();
//                  //  city.execute();
//                } else {
//                    Toasty.error(AddNewAddress.this, "No internet access", Toast.LENGTH_SHORT, true).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnSave.setOnClickListener(view -> Validation());

        binding.btnReset.setOnClickListener(v -> {
            binding.edAddress.setText("");
            binding.edAddress.setFocusableInTouchMode(true);

            binding.edLocality.setText("");
            binding.edLocality.setFocusableInTouchMode(true);

            binding.spinnerCity.setText("");
            binding.spinnerCity.setFocusableInTouchMode(true);

            binding.edPincode.setText("");
            binding.edPincode.setFocusableInTouchMode(true);

            binding.edFlat.setText("");
            binding.edAlternatemobileno.setText("");
            binding.edName.setText("");
            binding.edMobile.setText("");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e("MAPTAG", "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("MAPTAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        rhome = findViewById(R.id.rhome);
        roffice = findViewById(R.id.roffice);
        rother = findViewById(R.id.rother);
        //RAddresstype
        btnSave = findViewById(R.id.btnSave);
        pref = new Preferences(this);

    }

    public void Validation() {
        if (edName.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please Enter Name", Toast.LENGTH_LONG, true).show();
        } else if (edMobile.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please Enter Mobile Number", Toast.LENGTH_LONG, true).show();
        } else if (edLocality.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please Enter Locality", Toast.LENGTH_LONG, true).show();
        } else if (edAddress.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please Enter Address", Toast.LENGTH_LONG, true).show();
        } else if (spinnerState.getSelectedItemPosition() == 0) {
            Toasty.warning(this, "Please Select State", Toast.LENGTH_LONG, true).show();
        } else if (edPincode.getText().toString().isEmpty()) {
            Toasty.warning(this, "Please Enter Pincode", Toast.LENGTH_LONG, true).show();
        }  //rother
        else if (!roffice.isChecked() && !rhome.isChecked() && !rother.isChecked()) {
            Toasty.warning(this, "Please Select Address Type", Toast.LENGTH_LONG, true).show();
        } else {
            if (roffice.isChecked())
            {
                AddressType = "Office";
            } else if (rhome.isChecked()) {
                AddressType = "Home";
            } else if (rhome.isChecked()) {
                AddressType = "Other";
            }
            if (Utils.isNetworkConnectedMainThred(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(true);

                HitPincode address = new HitPincode();
                address.execute();

            } else {
                Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
            }
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public class HitState extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetState("fetchState");
            //  Log.e("displaytext12345", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();


            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("response")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("response");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();

                            JSONObject object = jsonResponse.getJSONObject(i);
                            map.put("StateId", object.getString("StateId"));
                            map.put("key", object.getString("StateName"));

                            arrayList.add(map);

                        }


                        spinnerState.setAdapter(new SpinnerAdapter(AddNewAddress.this, R.layout.spinner_layout, arrayList));


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

    //    public class HitCity extends AsyncTask<String, Void, Void> {
//        String displayText;
//        String msg;
//
//        @Override
//        protected Void doInBackground(String... params) {
//
//            displayText = WebService.GetCity(stateId, "getCity");
//            //  Log.e("displaytext12345", displayText);
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            loader.dismiss();
//
//            cityList.clear();
//
//
//            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
//                try {
//
//                    JSONObject jsonObject = new JSONObject(displayText);
//                    if (jsonObject.has("response")) {
//                        JSONArray jsonResponse = jsonObject.getJSONArray("response");
//                        for (int i = 0; i < jsonResponse.length(); i++) {
//                            HashMap<String, String> map = new HashMap<>();
//
//                            JSONObject object = jsonResponse.getJSONObject(i);
//                            map.put("ID", object.getString("ID"));
//                            map.put("key", object.getString("CityName"));
//                            cityList.add(map);
//                        }
//
//                        SpinnerAdapter adapter=new SpinnerAdapter(AddNewAddress.this, R.layout.spinner_layout, cityList);
//                        adapter.notifyDataSetChanged();
//                        spinnerCity.setAdapter(adapter);
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//    } IKUJ7Y6TFT
    public class SpinnerAdapter extends ArrayAdapter<HashMap<String, String>> {

        ArrayList<HashMap<String, String>> list;

        public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> list) {

            super(context, textViewResourceId, list);

            this.list = list;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.spinner_layout, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            //label.setTypeface(typeface3);
            label.setText(list.get(position).get("key"));
            return row;
        }
    }

    public class HitAddAddress extends AsyncTask<String, Void, Void> {
        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {


            displayText = WebService.addAddress(pref.get(AppSettings.CustomerID), edName.getText().toString(), edMobile.getText().toString(), edPincode.getText().toString(), edLocality.getText().toString(), edAddress.getText().toString(), arrayList.get(spinnerState.getSelectedItemPosition()).get("StateId"), spinnerCity.getText().toString(), edLandmark.getText().toString(), Alternatemobileno.getText().toString(), AddressType, "", "","", "addAddress");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("Status")) {


                        if (jsonObject.getBoolean("Status")) {
                            Toasty.success(AddNewAddress.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();

                            if (from.equalsIgnoreCase("cart")) {
                                startActivity(new Intent(AddNewAddress.this, ChooseDeliverySpeed.class));
                            } else {
                                //startActivity(new Intent(AddNewAddress.this, SelectAddressPage.class));
                                startActivity(new Intent(AddNewAddress.this, ConfirmAddressActivity.class));
                            }

                            ///  overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                            finish();
                            Log.e("AddAddress", "Done");
                        } else {
                            Toasty.error(AddNewAddress.this, jsonObject.has("msg") + "", Toast.LENGTH_LONG).show();
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

    public class HitPincode extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetPincode(edPincode.getText().toString(), "checkPinCode");

            Log.e("disssssss123", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONObject ob = jsonObject.getJSONObject("Response");
                    if (ob.getString("Status").equalsIgnoreCase("False")) {
                        Toast.makeText(AddNewAddress.this, "Delivery is not available on this pincode.", Toast.LENGTH_SHORT).show();
                    } else {
                        HitAddAddress address = new HitAddAddress();
                        address.execute();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getLocationPerms() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

//            Toast.makeText(this, currentLatitude + " WORKS 1 " + currentLongitude + "", Toast.LENGTH_LONG).show();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                String country = addresses.get(0).getCountryName();
//                Toast.makeText(this, currentLatitude + " WORKS3 - " + address + "//", Toast.LENGTH_LONG).show();
                Log.e("location_data", city + address + country + state + zip + "//// " + currentLatitude + " - " + currentLongitude);

                binding.tvFullAddress.setText(address);

                setDetails(address, addresses.get(0).getSubLocality(), city, zip);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to get location, please check gps permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDetails(String address, String subLocality, String city, String zip) {
        binding.containerCurrentLoc.setOnClickListener(v -> {
            binding.edAddress.setText(address);
            binding.edAddress.setFocusable(false);

            binding.edLocality.setText(subLocality);
            binding.edLocality.setFocusable(false);

            binding.spinnerCity.setText(city);
            binding.spinnerCity.setFocusable(false);

            binding.edPincode.setText(zip);
            binding.edPincode.setFocusable(false);
        });

        binding.edAddress.setOnClickListener(v -> {
            Toast.makeText(this, "You can't edit this field", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     */

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS 2 " + currentLongitude + "", Toast.LENGTH_LONG).show();
//        List<Address> addresses  = geocoder.getFromLocation(currentLatitude,currentLongitude, 1);
//        String address = addresses.get(0).getAddressLine(0);
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String zip = addresses.get(0).getPostalCode();
//        String country = addresses.get(0).getCountryName();
    }
}
