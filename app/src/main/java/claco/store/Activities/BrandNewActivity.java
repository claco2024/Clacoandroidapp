package claco.store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.customecomponent.CustomButton;
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

public class BrandNewActivity extends AppCompatActivity {
 RecyclerView rv_brand;

    View view;
    RecyclerView productRecyclerView;
    GridLayoutManager mGridLayoutManager;
    // ProductAdapter proadapter;
    Preferences pref;

    CustomLoader loader;
public  static  String manufacturername="";
    LinearLayout layout_empty;

    CustomButton bAddNew;

    ProductAdapter productAdapter;
    Spinner spinnerReason;
    EditText etComment,et_search;

public  static  JSONArray jsonResponse;
    //String
    public static String OrderId;
    public static String Quantity;
    public static String orderDate;
    public static String ProductTitle;
    public static String TotalPrice;
    public static String getOrderList;
    public static String Address;
    public static String Name;
    public static String MobileNo1;
    public static String ProductCode;
    public static String status;
    public static String ProductId;
    public static String Url;
    public static String PaymentMode;
    public static String size;
    public static String timeslot;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> Ordereditemlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> reasonList = new ArrayList<>();
    ArrayList<String> reasonList1 = new ArrayList<>();

    RelativeLayout rlDelivered,rlOntheWay,rlPacked,rlProcess;
    TextView tv_filter;
    String tracking_status="";
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_new);
        rv_brand= findViewById(R.id.rv_brand);
        loader = new CustomLoader(BrandNewActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mGridLayoutManager = new GridLayoutManager(BrandNewActivity.this, 1);
        rv_brand.setLayoutManager(mGridLayoutManager);
         findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  finish();
             }
         });
        if (Utils.isNetworkConnectedMainThred(BrandNewActivity.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            HitGetOffer order = new  HitGetOffer();
            order.execute();

        } else {
            Toasty.error(BrandNewActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }
    public class HitGetOffer extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.getManufacturerlist("", "getManufacturerlist");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.v("getofferlist"," getofferlist "+displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("ManufacturesResponse");
                    if (jsonArray.length() == 0) {
                        
                    } else {
                        arrayList.clear();
                        
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("ManufacturerId", object.getString("ManufacturerId"));
                            map.put("ManufacturerName", object.getString("ManufacturerName"));
                            map.put("ManufacturerImage", object.getString("ManufacturerImage"));
                              arrayList.add(map);
                        }
                        productAdapter = (new  ProductAdapter(arrayList));
                        rv_brand.setAdapter( productAdapter);

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
    private class FavNameHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_title;




        public FavNameHolder(View itemView) {
            super(itemView);

            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);


        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<FavNameHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manufacturer_items, parent, false));
            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {
            holder.tv_title.setText(data.get(position).get("ManufacturerName") /*+ " ("+data.get(position).get("CouponId")+")"*/);

            Glide.with(BrandNewActivity.this).load(WebService.imageURL+data.get(position).get("ManufacturerImage")).placeholder(R.drawable.placeholder11).into(holder.iv_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manufacturername= data.get(position).get("ManufacturerName");
                if (Utils.isNetworkConnectedMainThred(BrandNewActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    new  HitProducts().execute(data.get(position).get("ManufacturerId"));
                }
                else {
                    Toasty.error(BrandNewActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                }

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



    public class HitProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProductlistByfilter(String.valueOf(params[0]),"","","","","GetProductlistByfilter");

            Log.e("GetProductlistByfilter", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    // arrayList.clear();

                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("proitemnew")) {
                        jsonResponse = jsonObject.getJSONArray("proitemnew");
 if (jsonResponse.length()>0) {

     AppSettings.productFrom = "8";
     startActivity(new Intent(BrandNewActivity.this, DrawerActivity.class).putExtra("page", "search"));

 }else {
     Toasty.error(BrandNewActivity.this, "No Data Found !!", Toast.LENGTH_SHORT, true).show();
 }


                    }
else {
                        Toasty.error(BrandNewActivity.this, "No Data Found !!", Toast.LENGTH_SHORT, true).show();
                    }
                } catch (Exception e) {
                    Toasty.error(BrandNewActivity.this, "No Data Found !!", Toast.LENGTH_SHORT, true).show();
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