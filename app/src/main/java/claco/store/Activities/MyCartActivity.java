package claco.store.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import claco.store.Main.DrawerActivity;
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

public class MyCartActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerview, recyclerview2;
    GridLayoutManager mGridLayoutManager, mGridLayoutManager2;
    LinearLayout layout_cart_empty;
    LinearLayout llcartItem;
    //Textview
    TextView tvHeaderText;
    TextView tvProceed;
    TextView tvFinalprice1;
    Button bAddNew;
    //pref
    Preferences pref;
    CustomLoader loader;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    String ProductId;
    String CartListID;
    String varId = "";
    JSONArray jsonArrayaddress;
    String RateId = "";
    ImageView iv_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        layout_cart_empty = findViewById(R.id.layout_cart_empty);
        llcartItem = findViewById(R.id.llcartItem);
        bAddNew = findViewById(R.id.bAddNew);
        iv_menu = findViewById(R.id.iv_menu);

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        bAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyCartActivity.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        tvHeaderText = findViewById(R.id.tvHeaderText);
        tvProceed = findViewById(R.id.tvProceed);
        tvFinalprice1 = findViewById(R.id.tvFinalprice);
        tvProceed.setOnClickListener(this);
        tvHeaderText.setText("My Cart");
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview2 = findViewById(R.id.recyclerview2);

        mGridLayoutManager = new GridLayoutManager(this, 1);
        mGridLayoutManager2 = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(mGridLayoutManager);
        recyclerview2.setLayoutManager(mGridLayoutManager2);

        pref = new Preferences(this);
        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            HitGetCart cart = new HitGetCart();
            cart.execute();
        } else {
            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvProceed:
                if (jsonArrayaddress != null && jsonArrayaddress.length() == 0) {
                    startActivity(new Intent(this, AddNewAddress.class).putExtra("from", "cart"));
                } else {
                    startActivity(new Intent(this, ConfirmAddressActivity.class));

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (AppSettings.cartStatus.equalsIgnoreCase("1") || (AppSettings.cartStatus.equalsIgnoreCase("3"))) {
            Intent intent = new Intent(MyCartActivity.this, DrawerActivity.class);
            intent.putExtra("page", "home");
            startActivity(intent);
        }
        else
        {
            Intent i = new Intent(this, DrawerActivity.class);
            i.putExtra("page", "cart");
            startActivity(i);
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }
    }

    private class FavNameHolder extends RecyclerView.ViewHolder {
        TextView tvOldPrice;
        TextView cart_item_number;
        TextView tvSave;
        ImageButton cart_quant_minus;
        ImageButton cart_quant_add;
        TextView tvProductName;
        TextView tvFinalprice, tvSize, tvColor;
        ImageView cart_item_delete;
        ImageView cart_item_image;

        public FavNameHolder(View itemView) {
            super(itemView);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvColor = itemView.findViewById(R.id.tvColor);
            cart_item_number = itemView.findViewById(R.id.cart_item_number);
            cart_quant_minus = itemView.findViewById(R.id.cart_quant_minus);
            cart_quant_add = itemView.findViewById(R.id.cart_quant_add);
            tvSave = itemView.findViewById(R.id.tvSave);

            cart_item_image = itemView.findViewById(R.id.cart_item_image);

            cart_item_delete = itemView.findViewById(R.id.cart_item_delete);

            tvProductName = itemView.findViewById(R.id.tvProductName);

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
            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(MyCartActivity.this).load(WebService.imageURL + data.get(position).get("MainPicture")).placeholder(R.drawable.placeholder11).into(holder.cart_item_image);


            holder.tvProductName.setText(data.get(position).get("ProductName"));

            if (!data.get(position).get("sizename").isEmpty()) {
                holder.tvSize.setText("Size : " + data.get(position).get("sizename"));
            } else {
                holder.tvSize.setVisibility(View.GONE);
            }

            if (!data.get(position).get("colorname").isEmpty()) {
                holder.tvColor.setText("Color : " + data.get(position).get("colorname"));
            } else {
                holder.tvColor.setVisibility(View.GONE);
            }

            holder.cart_item_number.setText(data.get(position).get("Quantity"));
            Log.e("RateId", "Quantity while setting" + (data.get(position).get("Quantity")));

            try {
                holder.tvOldPrice.setText("\u20b9 " + String.valueOf(data.get(position).get("RegularPrice")));
                holder.tvFinalprice.setText("\u20b9 " + String.valueOf(data.get(position).get("SalePrice")));
            } catch (Exception e) {
            }
            holder.cart_quant_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    varId = (data.get(position).get("varId"));
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                    count = count + 1;

                    holder.cart_item_number.setText(String.valueOf(count));
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(100);
                    ProductId = data.get(position).get("ProductCode");
                    holder.cart_item_number.setText(String.valueOf(count));
                    UpdateCartNew cart = new UpdateCartNew();
                    cart.execute("Plus");

                }
            });

            holder.cart_quant_minus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());

                    if (count > 1) {

                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(100);


                        count = count - 1;
                        varId = (data.get(position).get("varId"));
                        ProductId = data.get(position).get("ProductCode");
                        CartListID = data.get(position).get("CartListID");

                        holder.cart_item_number.setText(String.valueOf(count));

                        UpdateCartNew cart = new UpdateCartNew();
                        cart.execute("minus");
                    } else {
                    }


                }
            });

            holder.cart_item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    varId = (data.get(position).get("varId"));
                    ProductId = data.get(position).get("ProductCode");
                    CartListID = data.get(position).get("CartListID");
                    //Call remove api
                    HitRemoveFromCart cart = new HitRemoveFromCart();
                    cart.execute();

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

    private class CartFreeItems extends RecyclerView.ViewHolder {
        TextView tvOldPrice;
        TextView cart_item_number;
        TextView tvSave;
        ImageButton cart_quant_minus;
        ImageButton cart_quant_add;
        TextView tvProductName;
        TextView tvFinalprice, tvSize;
        ImageView cart_item_delete;
        ImageView cart_item_image;

        public CartFreeItems(View itemView) {
            super(itemView);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvSize = itemView.findViewById(R.id.tvSize);
            cart_item_number = itemView.findViewById(R.id.cart_item_number);
            cart_quant_minus = itemView.findViewById(R.id.cart_quant_minus);
            cart_quant_add = itemView.findViewById(R.id.cart_quant_add);
            tvSave = itemView.findViewById(R.id.tvSave);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
            cart_item_delete = itemView.findViewById(R.id.cart_item_delete);
            tvProductName = itemView.findViewById(R.id.tvProductName);

        }
    }

    private class ProductAdapter2 extends RecyclerView.Adapter<CartFreeItems> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter2(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }


        public ProductAdapter2() {
        }

        public CartFreeItems onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CartFreeItems(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_free_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final CartFreeItems holder, final int position) {
            Log.e("imgPath", "" + WebService.imageURL + arrayList2.get(position).get("ProductImage"));
            Glide.with(MyCartActivity.this).load(WebService.imageURL + arrayList2.get(position).get("ProductImage")).placeholder(R.drawable.placeholder11).into(holder.cart_item_image);
            holder.tvProductName.setText(arrayList2.get(position).get("ProductName"));
        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }


    public class HitRemoveFromCart extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.deleteTocart(pref.get(AppSettings.CustomerID), CartListID, "1", "", "", "", RateId, "DeleteCart");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject ob = jsonObject.getJSONObject("Response");

                    if (ob.getString("Status").equalsIgnoreCase("True")) {
                        Toasty.success(MyCartActivity.this, "Product Removed", Toast.LENGTH_SHORT).show();

                        HitGetCart cart = new HitGetCart();
                        cart.execute();

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

    public class UpdateCartNew extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            Log.v("UpdateCartNew", pref.get(AppSettings.CustomerID) + "    " + varId + "    " + ProductId + "  1" + "   " + "UpdateCartNew");

            if (params[0].equalsIgnoreCase("minus")) {
                displayText = WebService.UpdateCartNew(pref.get(AppSettings.CustomerID), varId, ProductId, "-1", "UpdateCartNew");

            } else if (params[0].equalsIgnoreCase("Plus")) {
                displayText = WebService.UpdateCartNew(pref.get(AppSettings.CustomerID), varId, ProductId, "1", "UpdateCartNew");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("UpdateCartNew", displayText + "" + pref.get(AppSettings.CustomerID));

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject ob = jsonObject.getJSONObject("Response");

                    if (ob.getString("Status").equalsIgnoreCase("True")) {
                        Toasty.success(MyCartActivity.this, "Product Updated !!", Toast.LENGTH_SHORT).show();

                        HitGetCart cart = new HitGetCart();
                        cart.execute();
                    } else {
                        Toasty.success(MyCartActivity.this, "Product update failed !!", Toast.LENGTH_SHORT).show();
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

    public class HitGetCart extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.GetCartList(pref.get(AppSettings.CustomerID), "1", pref.get(AppSettings.District), "getCartList");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            Log.v("GetCartListPrabhat", displayText);
            arrayList.clear();
            arrayList2.clear();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getCartResponse");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        String ProdID = obj.getString("ProductCode");
                        String Quantity = obj.getString("Quantity");
                        String CartListID = obj.getString("CartListID");
                        String ProductName = obj.getString("ProductName");
                        String ProductCategory = obj.getString("ProductCategory");
                        String MainPicture = obj.getString("MainPicture");
                        String ProductType = obj.getString("ProductType");
                        String VarriationName = obj.getString("VarriationName");
                        String varId = obj.getString("varId");
                        String RegularPrice = obj.getString("RegularPrice");
                        String SalePrice = obj.getString("SalePrice");
                        String DiscPer = obj.getString("DiscPer");
                        String Totalprice = obj.getString("Totalprice");
                        String PayableAmt = obj.getString("PayableAmt");

                        String sizename = obj.getString("sizename");
                        String colorname = obj.getString("colorname");
                        //  String RateId = obj.getString("RateId");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductCode", ProdID);
                        map.put("Quantity", Quantity);
                        map.put("CartListID", CartListID);
                        map.put("ProductName", ProductName);
                        map.put("ProductCategory", ProductCategory);
                        map.put("MainPicture", MainPicture);
                        map.put("ProductType", ProductType);
                        map.put("VarriationName", VarriationName);
                        map.put("varId", varId);
                        map.put("RegularPrice", RegularPrice);
                        map.put("SalePrice", SalePrice);
                        map.put("DiscPer", DiscPer);
                        map.put("Totalprice", Totalprice);
                        map.put("PayableAmt", PayableAmt);
                        map.put("sizename", sizename);
                        map.put("colorname", colorname);
                        //  map.put("RateId", RateId);
                        arrayList.add(map);
                        Log.e("ProdID", ProdID);


                        Log.e("RateId", "new Quantitnmy" + Quantity);
                    }
                    pref.set(AppSettings.count, "" + arrayList.size()/* jsonArray.length()*/);
                    pref.commit();
                    // DrawerActivity.tvCount.setText(pref.get(AppSettings.count));
                    if (jsonObject.get("todayPoAmt") != null && !jsonObject.get("todayPoAmt").equals("") &&
                            !jsonObject.get("todayPoAmt").equals("null")) {

                        tvFinalprice1.setText("\u20b9 " + jsonObject.get("todayPoAmt"));
                    } else {
                        tvFinalprice1.setText("\u20b9 " + " 0.0");
                    }

                    if (arrayList.size() > 0) {
                        recyclerview.setAdapter(new ProductAdapter(arrayList));
                        recyclerview.setVisibility(View.VISIBLE);
                        layout_cart_empty.setVisibility(View.GONE);
                    } else {
                        tvFinalprice1.setText("\u20b9 " + " 0.0");
                        recyclerview.setVisibility(View.GONE);
                        layout_cart_empty.setVisibility(View.VISIBLE);
                    }

                    JSONArray jsonArray2 = jsonObject.getJSONArray("GetComboOfferresponse");
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject obj = jsonArray2.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductId", obj.getString("ProductId"));
                        map.put("varId", obj.getString("varId"));
                        map.put("qty", obj.getString("qty"));
                        map.put("ProductName", obj.getString("ProductName"));
                        map.put("ProductImage", obj.getString("ProductImage"));
                        arrayList2.add(map);
                        Log.e("ProdID", "" + arrayList2);
                    }

                    if (arrayList2.size() > 0) {
                        recyclerview2.setAdapter(new ProductAdapter2(arrayList));
                        recyclerview2.setVisibility(View.VISIBLE);
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




