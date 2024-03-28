package claco.store.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import claco.store.Activities.OfferFagment;
import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;


public class ProductFrag extends Fragment {

    View view;
    RecyclerView productRecyclerView;
    GridLayoutManager mGridLayoutManager;
    Preferences pref;

    CustomLoader loader;

    LinearLayout layout_empty;

    ProductAdapter productAdapter;


    //String
    public static String OrderId;
    public static String Quantity;
    public static String orderDate;
    public static String ProductTitle;
    public static String TotalPrice;
    public static String getOrderList;
    public static String Address;
    public static String Name;
    public static String ProductCode;
    public static String status;
    public static String ProductId;
    public static String Url;
    public static String PaymentMode;
    public static String size;
    public static String timeslot;
 TextView NoData;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_fragment, container, false);
        productRecyclerView = view.findViewById(R.id.recyclerView);
        NoData = view.findViewById(R.id.NoData);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        productRecyclerView.setLayoutManager(mGridLayoutManager);

        DrawerActivity.ivHome.setVisibility(View.GONE);
        pref = new Preferences(getActivity());
        layout_empty = view.findViewById(R.id.layout_empty);

        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.ivHome.setVisibility(View.GONE);
        DrawerActivity.iv_menu.setVisibility(View.VISIBLE);
        DrawerActivity.rl_search.setVisibility(View.GONE);




        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerActivity.iv_menu.setVisibility(View.GONE);
                DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                Intent i = new Intent(getActivity(), DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                /// getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        //Custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        //Back
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent mIntent = new Intent(getActivity(), DrawerActivity.class);
                        mIntent.putExtra("page", "home");
                        startActivity(mIntent);

                        return true;
                    }
                }
                return false;
            }
        });



        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            HitGetOffer order = new HitGetOffer();
            order.execute();

        } else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }



        return view;
    }


    private class FavNameHolder extends RecyclerView.ViewHolder {

        TextView tvCoupon;
        TextView tvDescription;
        TextView tvNote;
        TextView tvValid;
        TextView tvPromocode;

        TextView tvUseCoupon;
        ImageView image;




        public FavNameHolder(View itemView) {
            super(itemView);

            tvCoupon = itemView.findViewById(R.id.tvCoupon);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvValid = itemView.findViewById(R.id.tvValid);
            tvUseCoupon = itemView.findViewById(R.id.tvUseCoupon);
            tvPromocode = itemView.findViewById(R.id.tvPromocode);
            image = itemView.findViewById(R.id.image);


        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<FavNameHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_items, parent, false));
          }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {
            holder.tvPromocode.setText(data.get(position).get("OfferCode") /*+ " ("+data.get(position).get("CouponId")+")"*/);
   holder.tvDescription.setText("Get up to " + data.get(position).get("DiscountValue") +
                    " % off on all products. Applicable from price Rs. "
                    + data.get(position).get("ApplyMRPFrom")+"  "
                    + " to Rs. "+ data.get(position).get("ApplyMRPTo")+""  );
            holder.tvNote.setText("Valid  from " + data.get(position).get("ValidStartDate") + " To "+data.get(position).get("ValidEndDate"));
   Glide.with(getActivity()).load(WebService.imageURL+data.get(position).get("Image")).centerCrop()
                    .into(holder.image);

        }
        public int getItemCount() {
            return data.size();
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }


    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public class HitGetOffer extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.getofferlist("Product", "getofferlist");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.v("getofferlist"," getofferlist   Product   "+ OfferFagment.from+ "    "+ displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("OfferRes");
                    if (jsonArray.length() == 0) {
                        NoData.setVisibility(View.VISIBLE);
                        productRecyclerView.setVisibility(View.GONE);
                    } else {
                        arrayList.clear();
                        NoData.setVisibility(View.VISIBLE);
                        productRecyclerView.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("OfferId", object.getString("OfferId"));
                            map.put("OfferCode", object.getString("OfferCode"));
                            map.put("ItemCode", object.getString("ItemCode"));
                            map.put("PurchaseAmount", object.getString("PurchaseAmount"));
                            map.put("CashBackAmount", object.getString("CashBackAmount"));
                            map.put("ValidStartDate", object.getString("ValidStartDate"));
                            map.put("ValidEndDate", object.getString("ValidEndDate"));
                            map.put("points", ""+object.getString("points"));
                            map.put("AmountPerPoint", ""+object.getString("AmountPerPoint"));
                            map.put("CompanyCode", ""+object.getString("CompanyCode"));
                            map.put("CenterCode", ""+object.getString("CenterCode"));
                            map.put("IsFirstPurchase", ""+object.getString("IsFirstPurchase"));
                            map.put("IsfreeItem", ""+object.getString("IsfreeItem"));
                            map.put("FreeItemCode", ""+object.getString("FreeItemCode"));
                            map.put("FreeQuantity", ""+object.getString("FreeQuantity"));
                            map.put("OfferFor", ""+object.getString("OfferFor"));
                            map.put("DiscountType", ""+object.getString("DiscountType"));
                            map.put("DiscountValue", ""+object.getString("DiscountValue"));
                            map.put("Image", ""+object.getString("Image"));
                            map.put("PromoCode", ""+object.getString("PromoCode"));
                            map.put("ApplyMRPFrom", ""+object.getString("ApplyMRPFrom"));
                            map.put("ApplyMRPTo", ""+object.getString("ApplyMRPTo"));

                            arrayList.add(map);
                        }
                        productAdapter = (new ProductAdapter(arrayList));
                        productRecyclerView.setAdapter( productAdapter);

                    }



                } catch (Exception e) {
                    NoData.setVisibility(View.VISIBLE);
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
}
