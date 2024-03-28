package claco.store.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class AddressListActivity extends AppCompatActivity {


    TextView tvHeaderText;
    RecyclerView recyclerview;
    GridLayoutManager mGridLayoutManager;
    CustomLoader loader;
    Preferences pref;
    ImageView iv_menu;

    ArrayList<HashMap<String, String>> addressList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        pref=new Preferences(this);
        iv_menu=findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressListActivity.this, MyAccountActivity.class));
                finish();
            }
        });

        tvHeaderText=findViewById(R.id.tvHeaderText);
        tvHeaderText.setText("My Address");
        recyclerview=findViewById(R.id.recyclerview);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(mGridLayoutManager);

        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);


        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);

            HitGetAddress address = new HitGetAddress();
            address.execute();
        }

        else {

            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
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

            Log.e("GetAddress", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getAddressResponse");

                    if(jsonArray.length()==0)
                    {
                        Toasty.error(AddressListActivity.this, "No Address Found", Toast.LENGTH_SHORT, true).show();
                    }


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Name", obj.getString("Name"));
                        map.put("MobileNo", obj.getString("MobileNo"));
                        map.put("PinCode", obj.getString("PinCode"));
                        map.put("Locality", obj.getString("Locality"));
                        map.put("Address", obj.getString("Address"));
                        map.put("StateId", obj.getString("StateId"));
                        map.put("CityId", obj.getString("CityId"));
                        map.put("SrNo", obj.getString("SrNo"));
                        map.put("IsActive", obj.getString("IsActive"));
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


    private class FavNameHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAddress;
        TextView tvPhone;

        ImageView ivradio;
        ImageView ivradioOn;

        LinearLayout llAddress;

        public FavNameHolder(View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            ivradio=itemView.findViewById(R.id.ivradio);
            ivradioOn=itemView.findViewById(R.id.iv_radioOn);
            llAddress=itemView.findViewById(R.id.llAddress);
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


            holder.tvName.setText(data.get(position).get("Name"));
            holder.tvPhone.setText(data.get(position).get("MobileNo"));
            String address=data.get(position).get("Locality")+ "," +data.get(position).get("Address") +
                    "," +data.get(position).get("StateId") + "," +data.get(position).get("CityId") + ","
                    +data.get(position).get("PinCode") ;
            holder.tvAddress.setText(address);

        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}
