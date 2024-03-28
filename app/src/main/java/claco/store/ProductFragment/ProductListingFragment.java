package claco.store.ProductFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import claco.store.Activities.BrandNewActivity;
import claco.store.Activities.FilterActivity;
import claco.store.Activities.SearchActivity;
import claco.store.Main.DrawerActivity;
import claco.store.Main.LoginActivity;
import claco.store.R;
import claco.store.fragments.DashboardFragment;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ProductListingFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    GridLayoutManager mGridLayoutManager;
    TextView tvHeaderText;
    ArrayList<HashMap<String, String>> recentArray = new ArrayList<>();
    CustomLoader loader;

    JSONArray jsonResponse;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    String productId, ProductCategory, ProductName, CatId;
    String varId, attrid;


    Preferences pref;

    public static JSONObject jsonObject;

    LinearLayout layout_empty;

    Button bAddNew;
    String ProductId;
    String CartListID;
    String cartlistid;
    String rate_id;
    String quantity;

    Dialog dialogQuantity;
    RecyclerView recyclerviewQuantity;
    CardView cardviewCheck;
    ArrayList<HashMap<String, String>> cartlist = new ArrayList<>();
    ProductAdapter productAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_product_listing, container, false);


        recyclerView = view.findViewById(R.id.productRecyclerView);
        tvHeaderText = view.findViewById(R.id.tvHeaderText);
        if (AppSettings.productFrom.equalsIgnoreCase("8")) {
            DrawerActivity.tvHeaderText.setText(" " + BrandNewActivity.manufacturername);
        }
        if (AppSettings.productFrom.equalsIgnoreCase("10")) {
            DrawerActivity.tvHeaderText.setText(" ");
        } else if (AppSettings.productFrom.equalsIgnoreCase("4")) {
            DrawerActivity.tvHeaderText.setText(" " + DrawerActivity.et_search.getText().toString());
        } else {
            if (AppSettings.catname != null && !AppSettings.catname.equalsIgnoreCase("")) ;
            DrawerActivity.tvHeaderText.setText(" " + AppSettings.catname);
        }
        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.iv_menu.setTag("arrow");
        DrawerActivity.ivHome.setVisibility(View.GONE);
        DrawerActivity.rl_search.setVisibility(View.GONE);
        layout_empty = view.findViewById(R.id.layout_empty);
        bAddNew = view.findViewById(R.id.bAddNew);
        bAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                //   getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        pref = new Preferences(getActivity());
        new HitGetCart().execute();
        if (AppSettings.productFrom.equalsIgnoreCase("8") ||
                AppSettings.productFrom.equalsIgnoreCase("10") ||
                AppSettings.productFrom.equalsIgnoreCase("4")) {
            setDetails();
        } else {
            new HitProducts().execute();
        }


        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSettings.productFrom.equalsIgnoreCase("8") ||
                        AppSettings.productFrom.equalsIgnoreCase("10")) {
                    replaceFragmentWithAnimation(new DashboardFragment());
                    DrawerActivity.iv_menu.setImageResource(R.drawable.ic_menu);
                    DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                } else if (AppSettings.productFrom.equalsIgnoreCase("1")) {
                    replaceFragmentWithAnimation(new ProductSubcategoryFragment());
                } else if (AppSettings.productFrom.equalsIgnoreCase("4")) {
                    replaceFragmentWithAnimation(new DashboardFragment());
                    DrawerActivity.iv_menu.setImageResource(R.drawable.ic_menu);
                    DrawerActivity.ivHome.setVisibility(View.VISIBLE);

                } else {
                    Intent i = new Intent(getActivity(), SearchActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });

        //custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        //Back
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        if (AppSettings.productFrom.equalsIgnoreCase("8")) {
                            replaceFragmentWithAnimation(new DashboardFragment());
                            DrawerActivity.iv_menu.setImageResource(R.drawable.ic_menu);
                            DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                        }
                        if (AppSettings.productFrom.equalsIgnoreCase("10")) {
                            replaceFragmentWithAnimation(new DashboardFragment());
                            DrawerActivity.iv_menu.setImageResource(R.drawable.ic_menu);
                            DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                        } else if (AppSettings.productFrom.equalsIgnoreCase("1")) {
                            replaceFragmentWithAnimation(new ProductSubcategoryFragment());
                        } else if (AppSettings.productFrom.equalsIgnoreCase("4")) {
                            Intent intent = new Intent(getActivity(), DrawerActivity.class);
                            intent.putExtra("page", "home");
                            startActivity(intent);
                        } else {
                            Intent i = new Intent(getActivity(), SearchActivity.class);
                            startActivity(i);
                            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        return view;
    }

    private void setDetails() {
        try {
            if (AppSettings.productFrom.equalsIgnoreCase("8")) {
                jsonResponse = BrandNewActivity.jsonResponse;
            } else if (AppSettings.productFrom.equalsIgnoreCase("10")) {
                jsonResponse = FilterActivity.jsonResponse;
            } else if (AppSettings.productFrom.equalsIgnoreCase("4")) {
                jsonResponse = DashboardFragment.jsonResponse;
            }


            AppSettings.dashboard = "1";

            Log.v("jsonResponse", jsonResponse.toString());
            Log.v("jsonResponseDiscper", jsonResponse.getJSONObject(0).getString("Discper"));

            for (int i = 0; i < jsonResponse.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject object = jsonResponse.getJSONObject(i);
                map.put("Discper", object.getString("Discper"));
                Log.v("dgjjrjrjr", "   " + object.getString("Discper"));

                map.put("ProductCode", object.getString("ProductCode"));
                map.put("ProductName", object.getString("ProductName"));
                map.put("ProductCategory", object.getString("ProductCategory"));
                map.put("MainCategoryCode", object.getString("MainCategoryCode"));
                // map.put("ProductMainImageUrl", object.getString("ProductMainImageUrl"));

                String completeUrl = object.getString("ProductMainImageUrl");
                completeUrl = completeUrl.replace(" ", "%20");
                map.put("MainPicture", completeUrl);

                map.put("pName", object.getString("pName"));
                map.put("SalePrice", object.getString("SalePrice"));
                map.put("UnitName", object.getString("UnitName"));


                map.put("RegularPrice", object.getString("RegularPrice"));
                if (object.has("SaveAmount") && object.getString("SaveAmount") != null)
                    map.put("SaveAmount", object.getString("SaveAmount"));
                if (object.has("AttrId") && object.getString("AttrId") != null)
                    map.put("AttrId", object.getString("AttrId"));
                if (object.has("VarId") && object.getString("VarId") != null) {
                    map.put("VarId", object.getString("VarId"));
                } else {
                    map.put("VarId", "0");
                }

                if (object.has("ProductType") && object.getString("ProductType") != null)
                    map.put("ProductType", object.getString("ProductType"));

                if (object.has("proitemnewvar") && object.getJSONArray("proitemnewvar").length() > 0) {
                    map.put("VarArray", "" + object.getJSONArray("proitemnewvar").toString());
                }
                arrayList.add(map);
            }


            Log.v("arrayList", "arrayList" + arrayList);
//            productAdapter = new ProductAdapter(arrayList);
            RecentProductAdapterNew recentProductAdapterNew = new RecentProductAdapterNew(arrayList);
            recyclerView.setAdapter(recentProductAdapterNew);
        } catch (Exception e) {
        }

    }

    public static class FavNameHolderNew extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvRating;
        TextView tvDiscount;
        TextView tvFinalprice;
        TextView tvOldPrice;

        TextView tvSave;
        TextView tvSize;
        TextView tvAddtoCart;

        TextView tvProductName;

        LinearLayout layout_item;
        RelativeLayout llAddtocart;
        RelativeLayout rlAddtocart;
        RelativeLayout rlSize;
        ImageButton cart_quant_minus;
        ImageButton cart_quant_add;
        TextView cart_item_number;
        CardView cardView;
        TextView tvQuantity;
        RelativeLayout llDiscount;

        Spinner spinnerQuantity;
        JSONArray jArray;


        public FavNameHolderNew(View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvSave = itemView.findViewById(R.id.tvSave);
            tvAddtoCart = itemView.findViewById(R.id.tvAddtoCart);
            tvSize = itemView.findViewById(R.id.tvSize);
            rlSize = itemView.findViewById(R.id.rlSize);
            llDiscount = itemView.findViewById(R.id.llDiscount);


            tvProductName = itemView.findViewById(R.id.tvProductName);

            layout_item = itemView.findViewById(R.id.layout_item);

            tvQuantity = itemView.findViewById(R.id.tvQuantity);

            cardView = itemView.findViewById(R.id.cardView);
            llAddtocart = itemView.findViewById(R.id.llAddtocart);
            rlAddtocart = itemView.findViewById(R.id.rlAddtocart);
            cart_item_number = itemView.findViewById(R.id.cart_item_number);
            cart_quant_minus = itemView.findViewById(R.id.cart_quant_minus);
            cart_quant_add = itemView.findViewById(R.id.cart_quant_add);

        }
    }

    private class RecentProductAdapterNew extends RecyclerView.Adapter<FavNameHolderNew> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public RecentProductAdapterNew(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public RecentProductAdapterNew() {
        }

        public FavNameHolderNew onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolderNew(LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_list_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolderNew holder, final int position) {

            holder.tvSize.setText("1");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    varId = (data.get(position).get("VarId"));
                    attrid = (data.get(position).get("AttrId"));
                    productId = data.get(position).get("ProductCode");
                    ProductCategory = data.get(position).get("ProductCategory");
                    ProductName = data.get(position).get("ProductName");
                    CatId = data.get(position).get("MainCategoryCode");


                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitProductDetail().execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
            });

            holder.tvOldPrice.setText("\u20b9" + data.get(position).get("RegularPrice"));
            holder.tvProductName.setText(data.get(position).get("ProductName"));
            String URL = WebService.imageURL + data.get(position).get("ProductMainImageUrl");
            double saving = Double.parseDouble(data.get(position).get("RegularPrice")) -
                    Double.parseDouble(data.get(position).get("SalePrice"));
            holder.tvSave.setText("Save \u20b9 " + String.valueOf(data.get(position).get("SaveAmount")));
            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvOldPrice.setText("\u20b9 " + data.get(position).get("RegularPrice"));
            holder.tvFinalprice.setText("\u20b9 " + data.get(position).get("SalePrice"));
            double retailprice = Double.parseDouble(data.get(position).get("RegularPrice"));


            if (data.get(position).get("SalePrice").equalsIgnoreCase(data.get(position).get("RegularPrice"))
                    || data.get(position).get("RegularPrice").equalsIgnoreCase("0.0")
                    || data.get(position).get("RegularPrice").equalsIgnoreCase("0.00")
                    || data.get(position).get("RegularPrice").equalsIgnoreCase("00.00")
            ) {
                holder.tvOldPrice.setVisibility(View.GONE);
            }
            try {
                if (retailprice != 0) {
                    double res = (saving / retailprice) * 100;
                    roundTwoDecimals(res);
                    int i2 = (int) Math.round(roundTwoDecimals(res));
                    if (i2 > 0) {
                        holder.tvDiscount.setText(String.valueOf(i2) + " % off");
                        holder.llDiscount.setVisibility(View.VISIBLE);
                    } else {
                        holder.llDiscount.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
            }
//            try {
//                Glide.with(getActivity()).load(URL).resize(300, 300).placeholder(R.drawable.placeholder11).into(holder.ivProduct);
//            } catch (Exception e) {
//
//            }
            Glide.with(getActivity())
                    .load(WebService.imageURL + data.get(position).get("MainPicture"))
                    .placeholder(R.drawable.placeholder11).into(holder.ivProduct);

            holder.tvAddtoCart.setText("Add");
            if (!cartlist.isEmpty()) {

                for (int j = 0; j < cartlist.size(); j++) {

                    if (data.get(position).get("ProductCode").equals(cartlist.get(j).get("ProductID"))) {

                        holder.llAddtocart.setVisibility(View.VISIBLE);
                        holder.rlAddtocart.setVisibility(View.GONE);
                        holder.cart_item_number.setText(String.valueOf(cartlist.get(j).get("Quantity")));


                        break;
                    } else {

                        holder.llAddtocart.setVisibility(View.GONE);
                        holder.rlAddtocart.setVisibility(View.VISIBLE);
                        holder.cart_item_number.setText("1");

                    }
                }
            }


//
            holder.cart_quant_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                    count = count + 1;

                    holder.cart_item_number.setText(String.valueOf(count));

                    Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(100);

                    varId = (data.get(position).get("VarId"));
                    attrid = (data.get(position).get("AttrId"));
                    ProductId = data.get(position).get("ProductCode");
                    // rate_id = data.get(position).get("RateId");
                    holder.cart_item_number.setText(String.valueOf(count));
                    quantity = holder.cart_item_number.getText().toString();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            HitAddTocart cart = new HitAddTocart();
                            cart.execute();

                        }
                    }, 100);


                }
            });

            holder.cart_quant_minus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());

                    if (count > 1) {
                        int minQty = 1;
                        if (count > minQty) {
                            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(100);
                            count = count - 1;
                            ProductId = data.get(position).get("ProductCode");
                            //  rate_id = data.get(position).get("RateId");
                            holder.cart_item_number.setText(String.valueOf(count));
                            varId = (data.get(position).get("VarId"));
                            attrid = (data.get(position).get("AttrId"));
                            quantity = holder.cart_item_number.getText().toString();
                            //   ProductId = data.get(position).get("ProductCode");
                            CartListID = data.get(position).get("CartListID");

                            UpdateCartNew cart = new UpdateCartNew();
                            cart.execute();
                        } else {
                            Toasty.warning(getActivity(), "Can't Order less than " + minQty, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    }
                }
            });

            holder.rlAddtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                    } else {
                        if (holder.tvAddtoCart.getText().toString().equalsIgnoreCase("Out Of Stock")) {
                            Toasty.error(getActivity(), "Product is out of stock", Toast.LENGTH_SHORT, true).show();
                        } else {
                            AppSettings.cartStatus = "2";
                            varId = (data.get(position).get("VarId"));
                            attrid = (data.get(position).get("AttrId"));
                            ProductId = data.get(position).get("ProductCode");
                            //rate_id = data.get(position).get("RateId");
                            holder.rlAddtocart.setVisibility(View.GONE);
                            holder.llAddtocart.setVisibility(View.VISIBLE);
                            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(true);
                                HitAddTocart cart = new HitAddTocart();
                                cart.execute();
                            } else {
                                Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                            }
                            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(100);
                            new HitGetCart().execute();

                        }
                    }
                }
            });

            holder.rlAddtocart.setVisibility(View.GONE);
        }

        public int getItemCount() {
            return data.size();
//            if (data.size() > 30) {
//                return 30;
//            } else {
//                return data.size();
//            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    private class FavNameHolder extends RecyclerView.ViewHolder implements OnLikeListener, OnAnimationEndListener {
        TextView tvOldPrice;
        TextView tvFinalprice;
        TextView tvDiscount;
        TextView tvProductName;
        TextView tvSave;
        TextView tvVendorName;
        TextView tvQty;
        TextView tvSize;

        LikeButton icWishlist;

        LinearLayout layout_item;
        ImageView ivProduct;
        RelativeLayout llAddtocart;
        RelativeLayout rlAddtocart;
        ImageButton cart_quant_minus;
        ImageButton cart_quant_add;
        TextView cart_item_number;
        TextView tvAddtoCart;
        TextView tvQuantity;
        RelativeLayout rlSize;
        RelativeLayout llDiscount;

        public FavNameHolder(View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvSave = itemView.findViewById(R.id.tvSave);
            tvVendorName = itemView.findViewById(R.id.tvVendorName);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvSize = itemView.findViewById(R.id.tvSize);
            rlSize = itemView.findViewById(R.id.rlSize);
            llDiscount = itemView.findViewById(R.id.llDiscount);

            icWishlist = itemView.findViewById(R.id.ic_wishlist);
            layout_item = itemView.findViewById(R.id.layout_item);

            llAddtocart = itemView.findViewById(R.id.llAddtocart);
            rlAddtocart = itemView.findViewById(R.id.rlAddtocart);
            cart_item_number = itemView.findViewById(R.id.cart_item_number);
            cart_quant_minus = itemView.findViewById(R.id.cart_quant_minus);
            cart_quant_add = itemView.findViewById(R.id.cart_quant_add);
            tvAddtoCart = itemView.findViewById(R.id.tvAddtoCart);
            icWishlist.setOnLikeListener(this);
            icWishlist.setOnAnimationEndListener(this);
        }

        @Override
        public void onAnimationEnd(LikeButton likeButton) {

        }

        @Override
        public void liked(LikeButton likeButton) {

        }

        @Override
        public void unLiked(LikeButton likeButton) {

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

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {


            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tvProductName.setText(data.get(position).get("ProductName"));
            holder.tvFinalprice.setText("\u20b9 " + data.get(position).get("SalePrice"));
            String dd = (data.get(position).get("Discper"));
            Double discount = Double.parseDouble(dd);
            if (discount > 0) {
                holder.llDiscount.setVisibility(View.VISIBLE);
                holder.tvOldPrice.setVisibility(View.VISIBLE);
                holder.tvDiscount.setText(("" + data.get(position).get("Discper")) + " % OFF");
            } else {
                holder.tvOldPrice.setVisibility(View.GONE);
                holder.llDiscount.setVisibility(View.GONE);
            }
            if (data.get(position).get("SalePrice").equalsIgnoreCase(data.get(position).get("RegularPrice")) || data.get(position).get("RegularPrice").equalsIgnoreCase("0.0")
                    || data.get(position).get("RegularPrice").equalsIgnoreCase("0.00")
                    || data.get(position).get("RegularPrice").equalsIgnoreCase("00.00")) {
                holder.tvOldPrice.setVisibility(View.GONE);
            }


            holder.tvOldPrice.setText("\u20b9 " + data.get(position).get("RegularPrice"));
            double saving = Double.parseDouble(data.get(position).get("RegularPrice")) - Double.parseDouble(data.get(position).get("SalePrice"));
            holder.tvSave.setText("Save \u20b9 " + String.valueOf(saving));

            Glide.with(getActivity())
                    .load(WebService.imageURL + data.get(position).get("MainPicture"))
                    .placeholder(R.drawable.placeholder11).into(holder.ivProduct);

            holder.tvAddtoCart.setText("Add");
            if (!cartlist.isEmpty()) {

                for (int j = 0; j < cartlist.size(); j++) {

                    if (data.get(position).get("ProductCode").equals(cartlist.get(j).get("ProductID"))) {

                        holder.llAddtocart.setVisibility(View.VISIBLE);
                        holder.rlAddtocart.setVisibility(View.GONE);
                        holder.cart_item_number.setText(String.valueOf(cartlist.get(j).get("Quantity")));


                        break;
                    } else {

                        holder.llAddtocart.setVisibility(View.GONE);
                        holder.rlAddtocart.setVisibility(View.VISIBLE);
                        holder.cart_item_number.setText("1");

                    }
                }
            }


//
            holder.cart_quant_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                    count = count + 1;

                    holder.cart_item_number.setText(String.valueOf(count));

                    Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(100);

                    varId = (data.get(position).get("VarId"));
                    attrid = (data.get(position).get("AttrId"));
                    ProductId = data.get(position).get("ProductCode");
                    // rate_id = data.get(position).get("RateId");
                    holder.cart_item_number.setText(String.valueOf(count));
                    quantity = holder.cart_item_number.getText().toString();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            HitAddTocart cart = new HitAddTocart();
                            cart.execute();

                        }
                    }, 100);


                }
            });

            holder.cart_quant_minus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());

                    if (count > 1) {
                        int minQty = 1;
                        if (count > minQty) {
                            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(100);
                            count = count - 1;
                            ProductId = data.get(position).get("ProductCode");
                            //  rate_id = data.get(position).get("RateId");
                            holder.cart_item_number.setText(String.valueOf(count));
                            varId = (data.get(position).get("VarId"));
                            attrid = (data.get(position).get("AttrId"));
                            quantity = holder.cart_item_number.getText().toString();
                            //   ProductId = data.get(position).get("ProductCode");
                            CartListID = data.get(position).get("CartListID");

                            UpdateCartNew cart = new UpdateCartNew();
                            cart.execute();
                        } else {
                            Toasty.warning(getActivity(), "Can't Order less than " + minQty, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    }
                }
            });

            holder.rlAddtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                    } else {
                        if (holder.tvAddtoCart.getText().toString().equalsIgnoreCase("Out Of Stock")) {
                            Toasty.error(getActivity(), "Product is out of stock", Toast.LENGTH_SHORT, true).show();
                        } else {
                            AppSettings.cartStatus = "2";
                            varId = (data.get(position).get("VarId"));
                            attrid = (data.get(position).get("AttrId"));
                            ProductId = data.get(position).get("ProductCode");
                            //rate_id = data.get(position).get("RateId");
                            holder.rlAddtocart.setVisibility(View.GONE);
                            holder.llAddtocart.setVisibility(View.VISIBLE);
                            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(true);
                                HitAddTocart cart = new HitAddTocart();
                                cart.execute();
                            } else {
                                Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                            }
                            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(100);
                            new HitGetCart().execute();

                        }
                    }
                }
            });

            holder.layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    varId = (data.get(position).get("VarId"));
                    attrid = (data.get(position).get("AttrId"));
                    productId = data.get(position).get("ProductCode");
                    ProductCategory = data.get(position).get("ProductCategory");
                    ProductName = data.get(position).get("ProductName");
                    CatId = data.get(position).get("MainCategoryCode");
                    productId = data.get(position).get("ProductCode");
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitProductDetail().execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
            });

            holder.icWishlist.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                        holder.icWishlist.setLiked(false);
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    } else {
                        productId = data.get(position).get("ProductCode");

                        if (Utils.isNetworkConnectedMainThred(getActivity())) {
                            HitLike like = new HitLike(holder);
                            like.execute();
                        } else {
                            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    productId = data.get(position).get("ProductCode");
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        HitUnlike unlike = new HitUnlike();
                        unlike.execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
            });


            if (data.get(position).get("Status") != null && !data.get(position).get("Status").equalsIgnoreCase("1")) {
                holder.tvAddtoCart.setText("Out Of Stock");

            } else {
                holder.tvAddtoCart.setText("add to cart");

            }


            if (!cartlist.isEmpty()) {
                Log.v("gjglgkgkgkgk", String.valueOf(cartlist.size()));
                for (int j = 0; j < cartlist.size(); j++) {
                    if (data.get(position).get("ProductCode").equals(cartlist.get(j).get("ProductCode"))) {
                        holder.cart_item_number.setText(String.valueOf(cartlist.get(j).get("Quantity")));
                        break;
                    } else {
                        holder.cart_item_number.setText("1");

                    }
                }
            }

            holder.rlSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuantityPopup(data.get(position).get("ProductName"), data.get(position).get("VarArray"), String.valueOf(position));
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

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }

    public void QuantityPopup(String productname, String quantity, String position) {
        dialogQuantity = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialogQuantity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogQuantity.setContentView(R.layout.dialog_quantity);
        Window window = dialogQuantity.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialogQuantity.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //  dialog.show();
        dialogQuantity.setCancelable(true);
        dialogQuantity.setCanceledOnTouchOutside(true);
        dialogQuantity.getWindow().setBackgroundDrawableResource(R.color.blacktrans);

        ArrayList<HashMap<String, String>> quantitylist = new ArrayList<>();


        TextView tvProductName;
        tvProductName = dialogQuantity.findViewById(R.id.tvProductName);
        recyclerviewQuantity = dialogQuantity.findViewById(R.id.recyclerviewQuantity);

        recyclerviewQuantity.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvProductName.setText(productname);

        try {
            JSONArray jsonArray = new JSONArray(quantity);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<>();

                map.put("ProductId", jsonObject.getString("ProductId"));
                map.put("VarriationName", jsonObject.getString("VarriationName"));
                map.put("SalePrice", jsonObject.getString("SalePrice"));
                quantitylist.add(map);
            }
            recyclerviewQuantity.setAdapter(new QuantityAdapter(quantitylist, position));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        dialogQuantity.show();


    }

    private class FavNameQuantity extends RecyclerView.ViewHolder {

        TextView tvQuantity;
        TextView tvMRP;
        TextView tvSalePrice;
        RelativeLayout relativelayout;


        public FavNameQuantity(View itemView) {
            super(itemView);

            tvMRP = itemView.findViewById(R.id.tvMRP);
            tvSalePrice = itemView.findViewById(R.id.tvSellingPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            relativelayout = itemView.findViewById(R.id.relativelayout);


        }
    }

    private class QuantityAdapter extends RecyclerView.Adapter<FavNameQuantity> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String productposition;

        public QuantityAdapter(ArrayList<HashMap<String, String>> favList, String productposition) {
            this.data = favList;
            this.productposition = productposition;

        }


        public FavNameQuantity onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavNameQuantity(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_quantity, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameQuantity holder, final int position) {
            holder.tvQuantity.setText(data.get(position).get("VarriationName"));
            holder.tvMRP.setText("");
            holder.tvSalePrice.setText("\u20B9" + data.get(position).get("SalePrice"));
            holder.tvMRP.setPaintFlags(holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.relativelayout.setBackgroundColor(getResources().getColor(R.color.white));
            holder.tvQuantity.setTextColor(getResources().getColor(R.color.black));
            holder.tvMRP.setTextColor(getResources().getColor(R.color.grey_500));
            holder.tvSalePrice.setTextColor(getResources().getColor(R.color.grey_700));


            holder.relativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    arrayList.get(Integer.parseInt(productposition)).put("quantity", "1");
                    // arrayList.get(Integer.parseInt(productposition)).put("MRP",data.get(position).get("Regularprice"));
                    arrayList.get(Integer.parseInt(productposition)).put("SalePrice", data.get(position).get("SalePrice"));
                    arrayList.get(Integer.parseInt(productposition)).put("ProductId", data.get(position).get("ProductId"));

                    Log.v("jfhjfhjfhjf1", data.get(position).get("ProductId"));
                    productAdapter.notifyItemChanged(Integer.parseInt(productposition));
                    // recentProductAdapter.notifyDataSetChanged();


                    dialogQuantity.dismiss();
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
    //***********************************API****************************************************//

    public class HitProductDetail extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProductDetail(productId, ProductCategory, ProductName, CatId, "getProductdetail");

            //displayText = WebService.GetProductDetail(productId, pref.get(AppSettings.District),"getProductdetail");

            Log.e("GetProductDetailreq", " productId   " + productId + " ProductCategory   " + ProductCategory + "  ProductName  " + ProductName + "  CatId  " + CatId);
            Log.e("GetProductDetailres", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("plistitem")) {
                        Log.e("priceSetList", jsonObject + "");
                        JSONArray jsonResponse = jsonObject.getJSONArray("plistitem");
                        AppSettings.fromPage = "1";
                        replaceFragmentWithAnimation(new ProductDescriptionFragment());
                        loader.dismiss();
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

    public class HitLike extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        FavNameHolder holder;

        public HitLike(FavNameHolder holder) {

            this.holder = holder;
        }

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.LikeUnlike(pref.get(AppSettings.CustomerID), productId, "AddToWishlist");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    jsonObject = new JSONObject(displayText);

                    JSONObject Response = jsonObject.getJSONObject("Response");


                    if (Response.getString("Status").equalsIgnoreCase("Item added to wishlist successfully")) {

                        Toasty.success(getActivity(), "Item added to wishlist successfully", Toast.LENGTH_SHORT).show();


                    } else {
                        Toasty.error(getActivity(), "Item already in wishlist", Toast.LENGTH_SHORT).show();
                        holder.icWishlist.setLiked(false);
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

    public class HitUnlike extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.LikeUnlike(pref.get(AppSettings.CustomerID), productId, "DeleteWishList");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    jsonObject = new JSONObject(displayText);

                    JSONObject Response = jsonObject.getJSONObject("Response");


                    if (Response.getString("Status").equalsIgnoreCase("true")) {

                        Toasty.success(getActivity(), "Item removed from wishlist successfully", Toast.LENGTH_SHORT).show();

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

            displayText = WebService.GetCartList(pref.get(AppSettings.CustomerID), "1",
                    pref.get(AppSettings.District), "getCartList");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            Log.e("displayyyyy1", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getCartResponse");
                    cartlist.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        String ProdID = obj.getString("ProductCode");
                        String Quantity = obj.getString("Quantity");
                        String CartListID = obj.getString("CartListID");
                        //  String RateId = obj.getString("RateId");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductCode", ProdID);
                        map.put("Quantity", Quantity);
                        map.put("CartListID", CartListID);
                        //  map.put("RateId", Ra
                        //  teId); Log.v("cartlistException",""+cartlist.size() +  ""+ e.getMessage());
                        Log.v("cartlistSize", "" + cartlist.size() + " in loop ");
                        cartlist.add(map);
                    }

                    Log.v("cartlistSize ", "" + cartlist.size() + " after loop ");
                    pref.set(AppSettings.count, "" + /*jsonArray.length()*/cartlist.size());
                    pref.commit();
                    DrawerActivity.tvCount.setText("" + String.valueOf(cartlist.size()));
                    //  Toast.makeText(getContext()," "+cartlist.size() + "  "+ AppSettings.count,Toast.LENGTH_LONG).show();

                } catch (Exception e) {

                    Log.v("cartlistException", "" + cartlist.size() + "" + e.getMessage());
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

    public class HitProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            Log.v("mainCatId", "" + DashboardFragment.mainCategoryId);

            //displayText = WebService.GetProduct(product_name12,"1",pref.get(AppSettings.District),"","GetProductNew");
            displayText = WebService.GetProductNew(DashboardFragment.mainCategoryId, "GetProductbyCategoryId");
            Log.e("GetProductNew", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
//proitemnew
                    // arrayList.clear();
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("proitemnew")) {
                        jsonResponse = jsonObject.getJSONArray("proitemnew");
                        AppSettings.productFrom = "4";
                        AppSettings.dashboard = "1";

                        Log.v("jsonResponse", jsonResponse.toString());
                        Log.v("jsonResponseDiscper", jsonResponse.getJSONObject(0).getString("Discper"));

                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject object = jsonResponse.getJSONObject(i);
                            map.put("Discper", object.getString("Discper"));
                            Log.v("dgjjrjrjr", "   " + object.getString("Discper"));

                            map.put("ProductCode", object.getString("ProductCode"));
                            map.put("ProductName", object.getString("ProductName"));
                            map.put("ProductCategory", object.getString("ProductCategory"));
                            map.put("MainCategoryCode", object.getString("MainCategoryCode"));
                            // map.put("ProductMainImageUrl", object.getString("ProductMainImageUrl"));

                            String completeUrl = object.getString("ProductMainImageUrl");
                            completeUrl = completeUrl.replace(" ", "%20");
                            map.put("MainPicture", completeUrl);

                            map.put("pName", object.getString("pName"));
                            map.put("SalePrice", object.getString("SalePrice"));
                            map.put("UnitName", object.getString("UnitName"));


                            map.put("RegularPrice", object.getString("RegularPrice"));
                            if (object.has("SaveAmount"))
                                map.put("SaveAmount", object.getString("SaveAmount"));
                            if (object.has("AttrId"))
                                map.put("AttrId", object.getString("AttrId"));
                            if (object.has("VarId")) {
                                map.put("VarId", object.getString("VarId"));
                            } else {
                                map.put("VarId", "0");
                            }
                            // map.put("ProductType", object.getString("VarId"));

                            if (object.has("ProductType"))
                                map.put("ProductType", object.getString("ProductType"));
                            if (jsonObject.has("proitemnewvar"))
                                map.put("VarArray", jsonObject.getJSONArray("proitemnewvar").toString());
                            arrayList.add(map);
                        }


                        Log.v("arrayList", "arrayList" + arrayList);
                        productAdapter = new ProductAdapter(arrayList);
                        recyclerView.setAdapter(productAdapter);

                        loader.dismiss();

                    }/* if (jsonObject.has("getProductResponse")) {
                        jsonResponse = jsonObject.getJSONArray("getProductResponse");
                        AppSettings.productFrom="4";
                        AppSettings.dashboard="1";
                        replaceFragmentWithAnimation(new ProductListingFragment());

                        loader.dismiss();

                    }*/

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

    public class HitAddTocart extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            // displayText = WebService.GetCategory("-1", "GetCategory");
            Log.e("addTocartdashboard", pref.get(AppSettings.CustomerID) + "  ProductId " + ProductId + "   " + "1" + "   varId   " + varId + "  attrid  " + attrid + "   " + "" + "   " + rate_id + "   " + "addToCart");
            displayText = WebService.addTocart(pref.get(AppSettings.CustomerID), ProductId, "1", varId, attrid, "", rate_id, "", "","addToCart");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    Toasty.success(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                    new HitGetCart().execute();
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
            displayText = WebService.UpdateCartNew(pref.get(AppSettings.CustomerID), varId, ProductId, "1", "UpdateCartNew");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("UpdateCartNew", displayText + "pref.get(AppSettings.CustomerID" + pref.get(AppSettings.CustomerID));

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject ob = jsonObject.getJSONObject("Response");

                    if (ob.getBoolean("Status")/*.equalsIgnoreCase("True")*/) {
                        Toasty.success(getActivity(), "Product Removed", Toast.LENGTH_SHORT).show();

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

}
