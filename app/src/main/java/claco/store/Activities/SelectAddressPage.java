package claco.store.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class SelectAddressPage extends AppCompatActivity {

    //Recyclerview
    RecyclerView recyclerview;
    GridLayoutManager mGridLayoutManager;
    TextView tvHeaderText;
    Preferences pref;
    private int row_index = -1;
    CustomLoader loader;
    CardView cardAddaddress;
    TextView tvSubmit;
    private RadioButton lastCheckedRB = null;
    ArrayList<HashMap<String, String>> addressList = new ArrayList<>();
    String areaId = "";
    ImageView iv_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_page);
        recyclerview = findViewById(R.id.recyclerview);

        getPermissions();
        tvSubmit = findViewById(R.id.tvSubmit);
        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectAddressPage.this, MyAccountActivity.class));
                finish();
            }
        });
        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        pref = new Preferences(this);

        mGridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(mGridLayoutManager);


        cardAddaddress = findViewById(R.id.cardAddaddress);


        tvHeaderText = findViewById(R.id.tvHeaderText);
        tvHeaderText.setText("Select Address");


        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);

            HitGetAddress address = new HitGetAddress();
            address.execute();
        } else {

            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (areaId.equals("")) {
                    Toasty.warning(SelectAddressPage.this, "Please Select Address", Toast.LENGTH_SHORT).show();
                } else {
                    finish();

                }


            }
        });
        cardAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectAddressPage.this, AddNewAddress.class).putExtra("from", "new"));
                ///  overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                finish();

//                startActivity(new Intent(SelectAddressPage.this, AddAddressActivity.class).putExtra("from", "new"));
//                ///  overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
//                finish();
            }
        });
    }

    private void getPermissions() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                fineLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                            }
                            Boolean coarseLocationGranted = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                coarseLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            }
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

    private class FavNameHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAddress;
        TextView tvPhone;

        ImageView ivradio;
        ImageView ivradioOn;

        LinearLayout llAddress;

        public FavNameHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivradio = itemView.findViewById(R.id.ivradio);
            ivradioOn = itemView.findViewById(R.id.iv_radioOn);
            llAddress = itemView.findViewById(R.id.llAddress);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<FavNameHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public ProductAdapter() {

        }

        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            //   holder.radioButton.setChecked(position == lastCheckedPosition);
            //  IsDefaultAccount Default
            /// Log.e("IsDefaultAccount" ," IsDefaultAccount "+ data.get(position).get("IsDefaultAccount"));


            //  map.put("", obj.getString("SrNo"));
            //                        map.put("Name", obj.getString(""));
            //                        map.put("", obj.getString("MobileNo"));
            //                        map.put("", obj.getString("PinCode"));
            //                        map.put("", obj.getString("Locality"));
            //                        map.put("", obj.getString("Address"));
            //                        map.put("", obj.getString("State_name"));
            //                        map.put("", obj.getString("CityName"));
            //                        map.put("AddressType", obj.getString("AddressType"));
            //                        map.put("compAdd", obj.getString("compAdd"));
            //                        map.put("", obj.getString("IsDefaultAccount"));
            holder.tvName.setText(data.get(position).get("Name"));
            holder.tvPhone.setText(data.get(position).get("MobileNo"));
            String address = data.get(position).get("Locality") + "," + data.get(position).get("Address") + "," + data.get(position).get("State_name") + "," + data.get(position).get("CityName") + "," + data.get(position).get("PinCode");
            holder.tvAddress.setText(address);
            row_index = position;
            areaId = data.get(position).get("SrNo");
            if (("Default").equalsIgnoreCase(data.get(position).get("IsDefaultAccount"))) {
                holder.ivradioOn.setVisibility(View.VISIBLE);
                holder.ivradio.setVisibility(View.GONE);
            } else {
                holder.ivradioOn.setVisibility(View.GONE);
                holder.ivradio.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    row_index = position;
                    areaId = data.get(position).get("SrNo");
                    HitUpdateAddress hitUpdateAddress = new HitUpdateAddress();
                    hitUpdateAddress.execute();
                    notifyDataSetChanged();
                    ;


                }
            });


        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    public class HitGetAddress extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.GetAddress(pref.get(AppSettings.CustomerID), "GetAddress");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy12", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getAddressResponse");

                    if (jsonArray.length() == 0) {
                        tvSubmit.setVisibility(View.GONE);
                    } else {
                        tvSubmit.setVisibility(View.VISIBLE);
                    }
//"SrNo": "8",
//      "": "test",
//      "": "vb",
//      "": "Office",
//      "": "9795489032",
//      "": "226010",
//      "": "nbbb",
//      "": "vb",
//      "": "Daman and Diu",
//      "": "vb,nbbb,vb,Daman and Diu - ",
//      "":
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("SrNo", obj.getString("SrNo"));
                        map.put("Name", obj.getString("Name"));
                        map.put("MobileNo", obj.getString("MobileNo"));
                        map.put("PinCode", obj.getString("PinCode"));
                        map.put("Locality", obj.getString("Locality"));
                        map.put("Address", obj.getString("Address"));
                        map.put("State_name", obj.getString("State_name"));
                        map.put("CityName", obj.getString("CityName"));
                        map.put("AddressType", obj.getString("AddressType"));
                        map.put("compAdd", obj.getString("compAdd"));
                        map.put("IsDefaultAccount", obj.getString("IsDefaultAccount"));

                        // if (obj.getString("IsDefaultAccount").equalsIgnoreCase("Default"))
                        addressList.add(map);
                    }


                    recyclerview.setAdapter(new ProductAdapter(addressList));
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

    public class HitUpdateAddress extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            Log.v("UpdateAddressreq", "  cid" + pref.get(AppSettings.CustomerID) + " id  " + areaId);
            displayText = WebService.UpdateAddress(pref.get(AppSettings.CustomerID), areaId, "UpdateAddress");
            Log.v("UpdateAddressres", "  displayText   " + displayText);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy123", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONObject object = jsonObject.getJSONObject("Response");
                    if (object.getString("Status").equalsIgnoreCase("True")) {
                        Toasty.success(SelectAddressPage.this, "Address Updated Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SelectAddressPage.this, ConfirmAddressActivity.class));
                        ///  overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);

                    } else {
                        Toasty.error(SelectAddressPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
