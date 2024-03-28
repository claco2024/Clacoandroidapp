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
import android.widget.EditText;
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

public class PromoCodeActivity extends AppCompatActivity {


    //Imageview
    ImageView iv_menu;

    TextView tvHeaderText;
    TextView tvApply;
    TextView tvCoupon;
    TextView tvValid;
    TextView tvDescription;
    TextView tvNote;

    TextView tvUseCoupon;
    EditText etPromoCode;
    Preferences pref;
    CustomLoader loader;
    RecentProductAdapter recentProductAdapter;

    RecyclerView productRecyclerView;
    ArrayList<HashMap<String,String>> couponlist = new ArrayList<>();
    TextView tv_or;

    LinearLayout ll_coupon;
    LinearLayout llNoCoupon;
    String finalAmt = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);
        iv_menu = findViewById(R.id.iv_menu);
        tvHeaderText = findViewById(R.id.tvHeaderText);
        tvApply = findViewById(R.id.tvApply);
        tvCoupon = findViewById(R.id.tvCoupon);
        tvValid = findViewById(R.id.tvValid);
        tvNote = findViewById(R.id.tvNote);
        ll_coupon = findViewById(R.id.ll_coupon);
        tvUseCoupon = findViewById(R.id.tvUseCoupon);
        tvDescription = findViewById(R.id.tvDescription);
        llNoCoupon = findViewById(R.id.llNoCoupon);

        tv_or = findViewById(R.id.tv_or);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(  PromoCodeActivity.this));

        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        tvHeaderText.setText("Promo Code");
        etPromoCode = findViewById(R.id.etPromoCode);

        pref = new Preferences(this);

        finalAmt = getIntent().getStringExtra("Orderamount");
        Log.e("getIntent1", finalAmt+"");

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PromoCodeActivity.this, ConfirmAddressActivity.class));
               // overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
            }
        });

        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            HitGetCoupon coupon = new HitGetCoupon();
            coupon.execute();
        } else {
            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PromoCodeActivity.this, ConfirmAddressActivity.class));
     }



    public class HitGetCoupon extends AsyncTask<String, Void, Void> {

        String displayText;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.GetCoupon(pref.get(AppSettings.CustomerID), "CoupanList");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.v("GetCoupon", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        HashMap<String, String> map = new HashMap<>();

                        map.put("CoupanNo", object.getString("CoupanNo"));
                        // Log.e("jsgggg", "" + object.getString("ProductId"));
                        map.put("CoupanAmount", object.getString("CoupanAmount"));
                        map.put("ExDate", object.getString("ExDate"));
                        map.put("Status", object.getString("Status"));
                        map.put("CouponId", object.getString("CouponId"));
                        map.put("OfferTitle", object.getString("OfferTitle"));
                        map.put("DiscountType", object.getString("DiscountType"));
                        map.put("PromoCode", object.getString("PromoCode"));
                        map.put("ApplyMRPFrom", object.getString("ApplyMRPFrom"));
                        map.put("ApplyMRPTo", object.getString("ApplyMRPTo"));
         couponlist.add(map);

                    }

                     if (couponlist.size()>0){
                         recentProductAdapter=new  RecentProductAdapter(couponlist);
                         productRecyclerView.setAdapter(recentProductAdapter);
                         llNoCoupon.setVisibility(View.GONE);
                         productRecyclerView.setVisibility(View.VISIBLE);
                     }else{
                         llNoCoupon.setVisibility(View.VISIBLE);
                         productRecyclerView.setVisibility(View.GONE);
                     }





                } catch (Exception e) {
                    llNoCoupon.setVisibility(View.VISIBLE);
                    productRecyclerView.setVisibility(View.GONE);
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

    public static class FavNameHolder extends RecyclerView.ViewHolder {


        TextView tvCoupon;
        TextView tvDescription;
        TextView tvNote;
        TextView tvValid;
        TextView tvPromocode;

        TextView tvUseCoupon;




        public FavNameHolder(View itemView) {
            super(itemView);

            tvCoupon = itemView.findViewById(R.id.tvCoupon);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvValid = itemView.findViewById(R.id.tvValid);
            tvUseCoupon = itemView.findViewById(R.id.tvUseCoupon);
            tvPromocode = itemView.findViewById(R.id.tvPromocode);


        }
    }

    private class RecentProductAdapter extends RecyclerView.Adapter< FavNameHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public RecentProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public RecentProductAdapter() {
        }

        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_list_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            holder.tvCoupon.setText(data.get(position).get("PromoCode") + " ("+data.get(position).get("CouponId")+")");
            holder.tvPromocode.setText(data.get(position).get("OfferTitle") );
            holder.tvDescription.setText(
                    "Get instant discount worth \u20b9" + data.get(position).get("CoupanAmount") +
                    " "+"\n"+"This coupon can be applied when your cart amount would lie between  \u20b9 " + data.get(position).get("ApplyMRPFrom")
                    + " and  \u20b9 "+ data.get(position).get("ApplyMRPTo")  );
            holder.tvNote.setText("This Coupon expires on " + data.get(position).get("ExDate"));
            holder.tvValid.setText(data.get(position).get("Status"));
            if (data.get(position).get("Status").equalsIgnoreCase("Available")) {
                tvValid.setTextColor(getResources().getColor(R.color.green_700));
                tvUseCoupon.setTextColor(getResources().getColor(R.color.green_700));

            }
            else {
                holder.tvUseCoupon.setText("Invalid");
                holder. tvValid.setTextColor(getResources().getColor(R.color.red_800));
                holder. tvUseCoupon.setTextColor(getResources().getColor(R.color.red_800));
            }
            holder. tvUseCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.get(position).get("Status").equalsIgnoreCase("Available"))
                    {
                        HitApplyCoupon hitApplyCoupon = new HitApplyCoupon();
                        String coupon = data.get(position).get("PromoCode");
                        hitApplyCoupon.execute(coupon);

                    }else {
                        Toast.makeText(getApplicationContext(),"Coupon is Not Valid",Toast.LENGTH_LONG).show();
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


    public class HitApplyCoupon extends AsyncTask<String, Void, Void> {

        String displayText;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.ApplyCoupon(pref.get(AppSettings.CustomerID),params[0], finalAmt, "ApplyCoupan");
            Log.e("sent", pref.get(AppSettings.CustomerID)+params[0]+ finalAmt+ "--"+displayText +"//"+finalAmt +"//" +params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("ApplyCouponreq", pref.get(AppSettings.CustomerID) +"    " +AppSettings.coupon + finalAmt );
            Log.e("ApplyCouponres", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("Status").equalsIgnoreCase("Applied Successfully!!")){
                            AppSettings.amount =object.getString("CoupanAmount") ;
                            Log.e("coupCode1", ""+AppSettings.amount);
                            //  AppSettings.coupon = object.getString("CoupanNo");
                            Toasty.success(PromoCodeActivity.this, "Coupon Applied Successfully", Toast.LENGTH_SHORT,true).show();
                            ConfirmAddressActivity.tvCouponAmt.setText(AppSettings.amount);
                            ConfirmAddressActivity.tvCouponAmt.setVisibility(View.VISIBLE);
                        }else {
                            Toasty.error(PromoCodeActivity.this, "Not Applicable...", Toast.LENGTH_SHORT,true).show();

                        }
                         }

                    startActivity(new Intent(PromoCodeActivity.this, ConfirmAddressActivity.class));


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
