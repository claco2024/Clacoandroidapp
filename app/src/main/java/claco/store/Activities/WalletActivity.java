package claco.store.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class WalletActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>>walletList=new ArrayList<>();
    TextView tvHeaderText,tv_wallet;
RecyclerView rvWallet;
    WalletAdapter walletAdapter;
    ImageView iv_menu;
  Preferences preferences;
   LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        preferences = new Preferences( this);
        tvHeaderText=findViewById(R.id.tvHeaderText);
        tvHeaderText.setText("My Wallet");
        iv_menu=findViewById(R.id.iv_menu);
        rvWallet = findViewById(R.id.rvWallet);
        tv_wallet = findViewById(R.id.tv_wallet);
        linearLayoutManager = new LinearLayoutManager(WalletActivity.this,RecyclerView.VERTICAL,false);
        rvWallet.setLayoutManager(linearLayoutManager);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WalletActivity.this, DrawerActivity.class).putExtra("page","home");
                startActivity(i);

            }
        });
        HitWallet Hit  = new HitWallet();
        Hit .execute();
        new  HitWalletPoint().execute();
    }

    public class HitWallet extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.getRefferalCustomerWise(preferences.get(AppSettings.CustomerID), "getRefferalCustomerWise");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //loader.dismiss();
            Log.v("refresposne", " refresposne " + displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("refresposne");
                    if (jsonArray.length() == 0) {

                    } else {
                        walletList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("OrderId", object.getString("OrderId"));
                            map.put("OrderDate", object.getString("OrderDate"));
                            map.put("Name", object.getString("Name"));
                            map.put("MobileNo", object.getString("MobileNo"));
                            map.put("ItemCode", object.getString("ItemCode"));
                            map.put("Quantity", object.getString("Quantity"));
                            map.put("UnitRate", object.getString("UnitRate"));
                            map.put("TotalAmount", object.getString("TotalAmount"));
                            map.put("ref_Amount", object.getString("ref_Amount"));
                           walletList.add(map);
                        }
                       walletAdapter = (new  WalletAdapter(walletList));
                        rvWallet.setAdapter(walletAdapter);

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
    public class HitWalletPoint extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.HitWalletPoint(preferences.get(AppSettings.CustomerID), "WalletPoint");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("WalletPoint", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject obj = new JSONObject(displayText);
                    if (obj.getBoolean("Status")) {
                        String WalletPoint = obj.getString("WalletPoint");
                        preferences.set(AppSettings.walletpoint, "" + /*jsonArray.length()*/WalletPoint);
                        preferences.commit();
                        DrawerActivity.tv_points.setText("\u20B9 " + preferences.get(AppSettings.walletpoint));
                        tv_wallet.setText("\u20B9 " + preferences.get(AppSettings.walletpoint));
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


    private class WalletHolder extends RecyclerView.ViewHolder {

        TextView tvdate;
        TextView tv_title;
        TextView amount;



        public WalletHolder(View itemView) {
            super(itemView);

            tvdate = itemView.findViewById(R.id.tvdate);
            tv_title = itemView.findViewById(R.id.tv_title);
            amount = itemView.findViewById(R.id.amount);


        }
    }

    private class WalletAdapter extends RecyclerView.Adapter<WalletHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public WalletAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public WalletHolder  onCreateViewHolder(ViewGroup parent, int viewType) {

            return new WalletHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_items, parent, false));
            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final WalletHolder holder, final int position) {
            holder.tv_title.setText(""+data.get(position).get("OrderId") +
                    "\n"+data.get(position).get("ItemCode") +"("+  data.get(position).get("Quantity")
                    + " X "+ data.get(position).get("UnitRate")+ ")"
            );
            holder.tvdate.setText(data.get(position).get("OrderDate") );
            holder.amount.setText("+  \u20b9 "+data.get(position).get("ref_Amount") );

//OrderId": "ORD101000015",
//      "OrderDate": "21 Dec 2020",
//      "Name": "piyush",
//      "MobileNo": "8604806561",
//      "ItemCode": "KLSH0000022",
//      "Quantity": "1",
//      "UnitRate": "98.00",
//      "TotalAmount": "98.00",
//      "ref_Amount"

        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WalletActivity.this, DrawerActivity.class).putExtra("page","home");
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }
}
