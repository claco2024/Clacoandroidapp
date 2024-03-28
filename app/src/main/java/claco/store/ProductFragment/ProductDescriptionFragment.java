package claco.store.ProductFragment;

import static claco.store.fragments.DashboardFragment.CatId;
import static claco.store.fragments.DashboardFragment.ProductCategory;
import static claco.store.fragments.DashboardFragment.ProductName;
import static claco.store.fragments.DashboardFragment.productId;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import claco.store.Activities.MyCartActivity;
import claco.store.Activities.SizeChartActivity;
import claco.store.Activities.WishlistActivity;
import claco.store.BuildConfig;
import claco.store.Main.DrawerActivity;
import claco.store.Main.LoginActivity;
import claco.store.Main.SplashActivity;
import claco.store.R;
import claco.store.adapters.SectionListDataAdapter;
import claco.store.fragments.DashboardFragment;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ProductDescriptionFragment extends Fragment implements View.OnClickListener, OnLikeListener, OnAnimationEndListener {

    public static String MainPicture;
    public static ArrayList<HashMap<String, String>> imagesList = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> imageList = new ArrayList<>();
    final long DELAY_MS = 600;
    final long PERIOD_MS = 3000;
    View view;
    ViewPager myviewpager;
    TabLayout tabDots;
    int currentPage = 0;
    RecyclerView productRecyclerView;
    GridLayoutManager mGridLayoutManager;
    LinearLayoutManager mLinearLayoutManager;
    GridLayoutManager mGridLayoutManager1;
    RatingBar ratingbar;
    //Textview
    TextView tvOldPrice;
    TextView tvAddToCart;
    TextView tvProductName;
    TextView tvFinalprice;
    TextView tvSave;
    TextView tvDescription;
    TextView tvBuynow;
    TextView tvVendorName;
    TextView tvMinQty;
    TextView tvRate;
    TextView tvViewAll;
    TextView tvName;
    TextView tvreviews;
    TextView tvRatings;
    TextView tvCount;
    TextView tvSize;
    TextView tvColor;
    TextView tvColorList;
    TextView tvWeight;
    TextView tvAvailbility;
    TextView tvFeature;
    TextView tvContinueShopping;
    TextView tvColor1;
    RecyclerView recyclerView;
    RecyclerView recyclerDescription;
    RecyclerView recyclerviewSize;
    RecyclerView recyclerviewColor;
    RecyclerView rv_varaiation;
    RecyclerView rv_images;
    RecyclerView rv_review_list_min;
    Preferences pref;
    JSONObject jsonObject;
    CustomLoader loader;
    ImageView img;
    String VendorName;
    String vendorEmail;
    String bussnessStartDate;
    String bussnessType;
    String StateName;
    String CityName;
    String storeLogo;
    String StoreName;
    String BusinessDesc;
    LinearLayout llMain;
    LinearLayout llDetails;
    LinearLayout linearlayout;
    LinearLayout llSizeChart;
    LinearLayout description_container;
    RelativeLayout llDiscount;
    Spinner spinnerQuantity;
    LikeButton icWishlist;
    LinearLayout llQuantity;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> descrList = new ArrayList<>();
    ArrayList<HashMap<String, String>> sizeList = new ArrayList<>();
    ArrayList<HashMap<String, String>> varList = new ArrayList<>();
    ArrayList<HashMap<String, String>> varColorList = new ArrayList<>();
    private Boolean isColorAvailable = false;
    private Boolean isSizeAvailable = false;

    ArrayList<HashMap<String, String>> similarproductList = new ArrayList<>();
    ArrayList<HashMap<String, String>> colorList = new ArrayList<>();
    ArrayList<HashMap<String, String>> quantitylist = new ArrayList<>();
    String ProductType;
    String ProductId;
    String inCart = "No";
    String Quantity = "1";
    String description;
    String rating = "";
    String size = "";
    String colorname = "";
    String colorimage = "";
    String rateid = "";
    String varId = "";
    String attrid = "";
    ImageView share;
    TextView tvDiscount;
    TextView tvDiscountoffer;
    LinearLayout llSimilar;
    private String status;
    private TextView nostock;
    private TextView tvDiscountnew;
    private TextView tvDiscountamount;

    private LinearLayout ll_var_size, ll_var_color;
    private RecyclerView rv_color_var;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_description, container, false);
        //Back
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        if (AppSettings.fromPage.equalsIgnoreCase("1")) {
                            replaceFragmentWithAnimation(new ProductListingFragment());
                        } else if (AppSettings.fromPage.equalsIgnoreCase("5")) {
                            replaceFragmentWithAnimation(new RecentProductFragment());
                        } else {

                            replaceFragmentWithAnimation(new DashboardFragment());
                         /*   Intent i = new Intent(getActivity(), DrawerActivity.class);
                            i.putExtra("page", "home");
                            startActivity(i);*/
                            ///   getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        }

                        return true;
                    }
                }
                return false;
            }
        });
        Log.v("vjjhjghhggh", AppSettings.dashboard);
        DrawerActivity.tvHeaderText.setText("");
        DrawerActivity.ivHome.setVisibility(View.GONE);
        DrawerActivity.rl_search.setVisibility(View.GONE);
        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        productRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerDescription = view.findViewById(R.id.recyclerDescription);
        recyclerviewSize = view.findViewById(R.id.recyclerviewSize);
        recyclerviewColor = view.findViewById(R.id.recyclerviewColor);
        rv_varaiation = view.findViewById(R.id.rv_varaiation);
        rv_review_list_min = view.findViewById(R.id.rv_review_list_min);
        rv_images = view.findViewById(R.id.rv_images);
        img = view.findViewById(R.id.img);
        share = view.findViewById(R.id.share);
        tvDiscountnew = view.findViewById(R.id.tvDiscountnew);
        tvDiscountoffer = view.findViewById(R.id.tvDiscountoffer);
        tvColor1 = view.findViewById(R.id.tvColor1);
        tvDescription = view.findViewById(R.id.tvDescription);
        description_container = view.findViewById(R.id.description_container);
        recyclerviewSize.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerviewColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv_varaiation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mGridLayoutManager1 = new GridLayoutManager(getActivity(), 1);
        recyclerDescription.setLayoutManager(mGridLayoutManager1);
        ratingbar = view.findViewById(R.id.ratingbar);
        llMain = view.findViewById(R.id.llMain);
        llDetails = view.findViewById(R.id.llDetails);
        linearlayout = view.findViewById(R.id.linearlayout);
        llSizeChart = view.findViewById(R.id.llSizeChart);
        spinnerQuantity = view.findViewById(R.id.spinnerQuantity);
        llQuantity = view.findViewById(R.id.llQuantity);
        llSimilar = view.findViewById(R.id.llSimilar);
        icWishlist = view.findViewById(R.id.ic_wishlist);
        icWishlist.setOnLikeListener(this);

        ll_var_size = view.findViewById(R.id.ll_var_size);
        ll_var_color = view.findViewById(R.id.ll_var_color);
        rv_color_var = view.findViewById(R.id.rv_color_var);

        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.iv_menu.setTag("arrow");
        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSettings.fromPage.equalsIgnoreCase("1")) {
                    replaceFragmentWithAnimation(new ProductListingFragment());
                } else if (AppSettings.fromPage.equalsIgnoreCase("5")) {
                    replaceFragmentWithAnimation(new RecentProductFragment());
                } else {
                    replaceFragmentWithAnimation(new DashboardFragment());
                    //   getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                }
            }
        });
        tvFeature = view.findViewById(R.id.tvFeature);
        llDiscount = view.findViewById(R.id.llDiscount);
        tvBuynow = view.findViewById(R.id.tvBuynow);
        tvVendorName = view.findViewById(R.id.tvVendorName);
        tvMinQty = view.findViewById(R.id.tvMinQty);
        tvRate = view.findViewById(R.id.tvRate);
        tvViewAll = view.findViewById(R.id.tvViewAll);
        tvName = view.findViewById(R.id.tvName);
//        tvreviews = view.findViewById(R.id.tvreviews);
        tvRatings = view.findViewById(R.id.tvRatings);
        tvCount = view.findViewById(R.id.tvCount);
        tvSize = view.findViewById(R.id.tvSize);
        tvColor = view.findViewById(R.id.tvColor);
        tvColorList = view.findViewById(R.id.tvColor1);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvAvailbility = view.findViewById(R.id.tvAvailbility);
        nostock = view.findViewById(R.id.nostock);
        tvContinueShopping = view.findViewById(R.id.tvContinueShopping);
        tvDiscountamount = view.findViewById(R.id.tvDiscountamount);


        rv_review_list_min.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        new HitReviews().execute();


        llSimilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSettings.fromPage.equalsIgnoreCase("1")) {
                    replaceFragmentWithAnimation(new ProductListingFragment());
                } else if (AppSettings.fromPage.equalsIgnoreCase("5")) {
                    replaceFragmentWithAnimation(new RecentProductFragment());
                } else {
                    Intent i = new Intent(getActivity(), DrawerActivity.class);
                    i.putExtra("page", "home");
                    startActivity(i);
                    // getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Claco");
                    String shareMessage = "\nLet me recommend you this product from  Claco\n\n";
                    if (productId != null && !productId.equalsIgnoreCase("")) {
                        shareMessage = shareMessage + "http://Claco.page.link/?link=http://claco.in/"
                                + "ref" + "product" + "ref" + productId + "ref" + ProductCategory.replaceAll("", "").replaceAll(" ", "%20") + "ref" + ProductName.replaceAll(" ", "%20") + "ref" + CatId + "ref" + pref.get(AppSettings.CustomerID) + "&apn=" + BuildConfig.APPLICATION_ID;

                    } else {
                        shareMessage = shareMessage + "http://Claco.page.link/?link=http://claco.in/"
                                + "ref" + "product" + "ref" + ProductId + "ref" + ProductCategory + "ref" + ProductName + "ref" + CatId + "ref" + pref.get(AppSettings.CustomerID) + "&apn=" + BuildConfig.APPLICATION_ID;

                    }
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        //Custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref = new Preferences(getActivity());
        findid();
        tabDots.setupWithViewPager(myviewpager, true);

        final int NUM_PAGES = 5;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                myviewpager.setCurrentItem(currentPage++, true);
            }
        };

        // ********************************//Getting details***************************************
        Log.e("redirectionPage ", "" +AppSettings.fromPage);

        try {
            if (AppSettings.fromPage.equalsIgnoreCase("1")) {
                jsonObject = ProductListingFragment.jsonObject;
            } else if (AppSettings.fromPage.equalsIgnoreCase("2")) {
                jsonObject = WishlistActivity.jsonObject;
            } else if (AppSettings.fromPage.equalsIgnoreCase("3")) {
                jsonObject = DashboardFragment.jsonObject;
                ProductId = productId;
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    new HitProductDetail().execute("list");
                } else {
                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            } else if (AppSettings.fromPage.equalsIgnoreCase("4")) {
                jsonObject = SectionListDataAdapter.jsonObject;

            } else if (AppSettings.fromPage.equalsIgnoreCase("5")) {
                jsonObject = RecentProductFragment.jsonObject;
            } else if (AppSettings.fromPage.equalsIgnoreCase("8")) {
                jsonObject = SplashActivity.jsonObject;
                ProductCategory = SplashActivity.PCategory;
                productId = SplashActivity.ProductId;
                ProductName = SplashActivity.PName;
                CatId = SplashActivity.PCatid;
                HitProductDetail hitdetails = new HitProductDetail();
                hitdetails.execute();
            } else if (AppSettings.fromPage.equalsIgnoreCase("9")) {
                jsonObject = DrawerActivity.jsonObject;
            } else if (AppSettings.fromPage.equalsIgnoreCase("10")) {
                jsonObject = DashboardFragment.jsonrecentlproduct;
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    new HitProductDetail().execute("list");
                } else {
                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            } else {
                jsonObject = DashboardFragment.jsonObject;
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    new HitProductDetail().execute("list");
                } else {
                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            }

            JSONArray jsonResponse = jsonObject.getJSONArray("plistitem");
            Log.e("priceSet1", jsonObject + "");
            for (int i = 0; i < jsonResponse.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject object = jsonResponse.getJSONObject(i);
                Log.v("plistitem", "" + object);
                String dd = (object.getString("Discper"));
                Double discount = Double.parseDouble(dd);
                if (discount > 0) {
                    llDiscount.setVisibility(View.VISIBLE);
                    tvDiscount.setText((object.getString("Discper")) + " % off");
                    tvOldPrice.setText("\u20b9 " + object.getString("RegularPrice"));
                    Log.e("priceSet", tvOldPrice.getText() + "");
                } else {
                    llDiscount.setVisibility(View.GONE);
                    tvOldPrice.setVisibility(View.GONE);
                }

                tvFinalprice.setText("\u20b9 " + object.getString("SalePrice"));
//                if (object.has("RegularPrice"))
//                    tvOldPrice.setText("\u20b9 " + object.getString("RegularPrice"));
                if (object.has("minQuantity"))
                    tvMinQty.setText("1 set (" + object.getString("minQuantity") + " pcs )");
                tvProductName.setText(object.getString("ProductName"));
                ProductId = object.getString("ProductCode");
                Log.e("testProductId1 ", "" + ProductId);
                if (object.has("VarId"))
                    varId = (object.getString("VarId"));
                if (object.has("AttrId"))
                    attrid = (object.getString("AttrId"));

                if (object.has("ProductType"))
                    ProductType = (object.getString("ProductType"));
                if (object.has("Status"))
                    status = object.getString("Status");
                else
                    status = "";

                //  Toasty.success(  getActivity(), "attrid   "+ attrid +"   varId  "+ varId  );

                Log.e("ProductId", ProductId);
                if (object.has("minQuantity"))
                    if (object.getString("minQuantity").equalsIgnoreCase("0")) {
                        Quantity = "1";
                    } else {
                        Quantity = object.getString("minQuantity");
                    }
                if (object.has("MainPicture")) {
                    String Url = object.getString("MainPicture");
                    MainPicture = WebService.imageURL + Url.replace(" ", "%20");
                    Log.e("main", MainPicture);
                }
                JSONArray imageArray = jsonObject.getJSONArray("crimage");
                for (int j = 0; j < imageArray.length(); j++) {
                    if (j == 0) {
                        JSONObject jsonObject = imageArray.getJSONObject(i);

                        myviewpager.setVisibility(View.GONE);
                        img.setVisibility(View.VISIBLE);
                        Glide.with(getActivity())
                                .load(WebService.imageURL + jsonObject.getString("ImageUrl").replace(" ", "%20")
                                ).centerCrop().placeholder(R.drawable.placeholder11).into(img);
                    }
                }
                JSONArray similarArray = jsonObject.getJSONArray("prductmore");
                for (int j = 0; j < similarArray.length(); j++) {
                    HashMap<String, String> map1 = new HashMap<>();
                    JSONObject jsonObject = similarArray.getJSONObject(j);


                    map1.put("ProductMainImageUrl", jsonObject.getString("ProductMainImageUrl"));
                    map1.put("Discper", jsonObject.getString("Discper"));
                    map1.put("ProductCategory", jsonObject.getString("ProductCategory"));
                    map1.put("ProductCode", jsonObject.getString("ProductCode"));
                    map1.put("ProductName", jsonObject.getString("ProductName"));
                    map1.put("MainCategoryCode", jsonObject.getString("MainCategoryCode"));
                    map1.put("pName", jsonObject.getString("pName"));
                    map1.put("SalePrice", jsonObject.getString("SalePrice"));
                    map1.put("RegularPrice", jsonObject.getString("RegularPrice"));

                    similarproductList.add(map1);

                }
                if (similarproductList.size() > 0) {
                    productRecyclerView.setVisibility(View.VISIBLE);
                    productRecyclerView.setAdapter(new SimilarProductAdapter(similarproductList));
                } else {
                    productRecyclerView.setVisibility(View.GONE);
                }

                JSONArray varArray = new JSONArray();
//                if (jsonObject.has("prVarr")) {
//                    varArray = jsonObject.getJSONArray("prVarr");
//                }

                if (jsonObject.has("varsize")) {
                    varArray = jsonObject.getJSONArray("varsize");
                }
                for (int j = 0; j < varArray.length(); j++) {
                    JSONObject jsonObject = varArray.getJSONObject(i);

                }

                if (status.equalsIgnoreCase("0")) {
                    nostock.setText("No Stock Available");
                    nostock.setTextColor(getResources().getColor(R.color.red_900));
                    tvBuynow.setEnabled(false);
                    tvAddToCart.setEnabled(false);
                    tvBuynow.setAlpha(0.2f);
                    tvAddToCart.setAlpha(0.2f);
                } else {
                    nostock.setText("In Stock");
                    tvBuynow.setEnabled(true);
                    tvAddToCart.setEnabled(true);
                    nostock.setTextColor(getResources().getColor(R.color.green_900));

                }
                // recyclerDescription.setAdapter(new ProductAdapter1(descrList));
                tvDescription.setText("");
                String feature = "";
                if (object.has("ProductSpecification"))
                    feature = android.text.Html.fromHtml(object.getString("ProductSpecification").trim()).toString().trim();

                Log.v("gghghghjghgh", feature);

                if (feature.isEmpty()) {
                    tvFeature.setText(" ");
                } else {
                    tvFeature.setText(feature);

                }
                String description = "";
                if (object.has("ProductDescription"))
                    description = android.text.Html.fromHtml(object.getString("ProductDescription").trim()).toString().trim();

                Log.v("gghghghjghgh", description);

                if (description.isEmpty()) {
                    Log.v("gghghghjghgh2", tvDescription.getText() + "");
                    tvDescription.setText("No description given.");
                } else if (description.equalsIgnoreCase("")) {
                    Log.v("gghghghjghgh2", tvDescription.getText() + "");
                    tvDescription.setText("No description given.");
                } else {
                    Log.v("gghghghjghgh2", tvDescription.getText() + "");
                    tvDescription.setText(description);
                }
                double saving = Double.parseDouble(object.getString("RegularPrice")) - Double.parseDouble(object.getString("SalePrice"));
                tvSave.setText("Save \u20b9 " + String.valueOf(saving));
                double retailprice = Double.parseDouble(object.getString("RegularPrice"));

//               if (retailprice!=0){
//                   double res = (saving / retailprice) * 100;
//                   roundTwoDecimals(res);
//                   int i2 = (int) Math.round(roundTwoDecimals(res));
//                   tvDiscount.setText(String.valueOf(i2) + " % off");
//
//               }
                if (saving > 0)
                    tvDiscountamount.setText(" You will save total  Rs " + String.valueOf(saving) + " on this product.");
                else
                    tvDiscountamount.setText(" You will save total Rs 0 on this product.");

                String color = "";
                String size = "";
                String weight = "";
                if (object.has("Color"))
                    color = object.getString("Color");
                if (object.has("size"))
                    size = object.getString("size");
                if (object.has("weight"))
                    weight = object.getString("weight");

                if (color.equalsIgnoreCase("") || size.equalsIgnoreCase("")) {
                    llDetails.setVisibility(View.GONE);
                } else {
                    llDetails.setVisibility(View.VISIBLE);
                    tvSize.setText(size);
                    tvColor.setText(color);
                    tvWeight.setText(weight);
                }

            }

            JSONArray varArray = new JSONArray();

//            if (jsonObject.has("prVarr"))
//                varArray = jsonObject.getJSONArray("prVarr");

            if (jsonObject.has("varsize"))
                varArray = jsonObject.getJSONArray("varsize");


            Log.v("vararr", "   legth " + varArray);

            varList.clear();
            for (int i = 0; i < varArray.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject object = varArray.getJSONObject(i);
//              map.put("SalePrice", object.getString("SalePrice"));
                map.put("VarriationName", object.getString("sizename"));
//              map.put("VariationId", object.getString("VariationId"));

                varList.add(map);

            }
            if (varList.size() > 0) {
                tvAddToCart.setEnabled(false);
                tvAddToCart.setAlpha(0.1f);
                isSizeAvailable = true;
                rv_varaiation.setVisibility(View.VISIBLE);
                rv_varaiation.setAdapter(new VarAdapter(varList));
            } else {
                isSizeAvailable = false;
                rv_varaiation.setVisibility(View.GONE);
                ll_var_size.setVisibility(View.GONE);
            }


            JSONArray varColorObj = new JSONArray();
            if (jsonObject.has("varcolor")) {
                varColorObj = jsonObject.getJSONArray("varcolor");
            }
            varColorList.clear();
            for (int i = 0; i < varColorObj.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject object = varColorObj.getJSONObject(i);
                map.put("VarriationName", object.getString("ColorName"));
                varColorList.add(map);
            }

            Log.e("varColorList ", "" + varColorList);

            if (varColorList.size() > 0) {
                tvAddToCart.setEnabled(false);
                tvAddToCart.setAlpha(0.1f);

                isColorAvailable = true;
                rv_color_var.setAdapter(new ColorAdapter(varColorList));
            } else {
                isColorAvailable = false;
                ll_var_color.setVisibility(View.GONE);
            }


            JSONArray ImagesArray = new JSONArray();

            if (jsonObject.has("crimage"))
                ImagesArray = jsonObject.getJSONArray("crimage");
            imagesList.clear();
            for (int i = 0; i < ImagesArray.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject object = ImagesArray.getJSONObject(i);
                map.put("ImageUrl", object.getString("ImageUrl"));


                imagesList.add(map);

            }
            if (imagesList.size() > 0) {
                rv_images.setVisibility(View.VISIBLE);
                rv_images.setAdapter(new ImageAdapter(imagesList));
            } else {
                rv_images.setVisibility(View.GONE);
            }
            Log.v("imagesListsize", "" + imagesList.size());


        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        if (!pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(true);
                HitGetCart cart = new HitGetCart();
                cart.execute();
            } else {
                Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
            }
        }

        tvVendorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VendorDialog();
            }
        });
        tvViewAll.setOnClickListener(this);
        tvRate.setOnClickListener(this);
        tvBuynow.setOnClickListener(this);
        llSizeChart.setOnClickListener(this);
        tvContinueShopping.setOnClickListener(this);


        return view;
    }

    private void hitGetDetails() {

    }

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }

    public void findid() {
        myviewpager = view.findViewById(R.id.myviewpager);
        tabDots = view.findViewById(R.id.tabDots);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvProductName = view.findViewById(R.id.tvProductName);
        tvFinalprice = view.findViewById(R.id.tvFinalprice);
        tvSave = view.findViewById(R.id.tvSave);

        tvOldPrice = view.findViewById(R.id.tvOldPrice);
        tvAddToCart = view.findViewById(R.id.tvAddToCart);
        tvAddToCart.setOnClickListener(this);
    }

    private void VendorDialog() {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.activity_vendor_profile);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog1.getWindow().getAttributes());

        ImageView ivShop = dialog1.findViewById(R.id.ivShop);
        TextView tvShopName = dialog1.findViewById(R.id.tvShopName);
        TextView tvCityState = dialog1.findViewById(R.id.tvCityState);
        TextView tvName = dialog1.findViewById(R.id.tvName);
        TextView tvEmail = dialog1.findViewById(R.id.tvEmail);
        TextView tvBusiness = dialog1.findViewById(R.id.tvBusiness);
        TextView tvYear = dialog1.findViewById(R.id.tvYear);
        TextView tvDescription = dialog1.findViewById(R.id.tvDescription);
        TextView tvHeaderText = dialog1.findViewById(R.id.tvHeaderText);

        //Setting text
        tvShopName.setText(StoreName);
        tvCityState.setText(CityName + ", " + StateName);
        tvName.setText(VendorName);
        tvEmail.setText(vendorEmail);
        tvBusiness.setText(bussnessType);
        tvYear.setText(bussnessStartDate);
        tvDescription.setText(BusinessDesc);
        tvHeaderText.setText(StoreName);

        Glide.with(getActivity()).load(storeLogo).centerCrop().placeholder(R.drawable.shop).into(ivShop);
        RelativeLayout RlCancel = dialog1.findViewById(R.id.RlCancel);
        RlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorblacktrans)));
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog1.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvAddToCart:
                if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                } else {
                    if (tvAvailbility.getText().toString().equalsIgnoreCase("OUT OF STOCK")) {
                        Toasty.error(getActivity(), "Product is out of stock", Toast.LENGTH_SHORT, true).show();
                    } else {
                        AppSettings.cartStatus = "2";
                        if (tvAddToCart.getText().toString().equalsIgnoreCase("GO TO CART")) {
                            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(100);
                            startActivity(new Intent(getActivity(), MyCartActivity.class));
                            getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                        } else {

//                            if (sizeList.isEmpty()) {
//                                if (ProductType.equalsIgnoreCase("Attribute Product") &&
//                                        varId.equalsIgnoreCase("")) {
//                                    Toasty.warning(getActivity(), "Please select variation", Toast.LENGTH_SHORT, true).show();
//                                } else {
                                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                        loader.show();
                                        loader.setCancelable(false);
                                        loader.setCanceledOnTouchOutside(true);

                                        HitAddTocart cart = new HitAddTocart();
                                        cart.execute();
                                        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                        vibe.vibrate(100);
                                        startActivity(new Intent(getActivity(), MyCartActivity.class));
                                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                                    } else {
                                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                                    }
//                                }

//                            } else {
//                                if (size.isEmpty()) {
//                                    Toasty.warning(getActivity(), "Please select size", Toast.LENGTH_SHORT, true).show();
//                                } else {
//                                    if (!varId.equalsIgnoreCase("")) {
//                                        if (Utils.isNetworkConnectedMainThred(getActivity())) {
//                                            loader.show();
//                                            loader.setCancelable(false);
//                                            loader.setCanceledOnTouchOutside(true);
//                                            HitAddTocart cart = new HitAddTocart();
//                                            cart.execute();
//                                        } else {
//                                            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
//                                        }
//                                    } else {
//                                        Toasty.warning(getActivity(), "Please select variation", Toast.LENGTH_SHORT, true).show();
//
//                                    }
//
//                                    Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//                                    vibe.vibrate(100);
//                                    startActivity(new Intent(getActivity(), MyCartActivity.class));
//                                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
//                                }
//                            }


                        }
                    }
                }
                break;

            case R.id.tvBuynow:
                if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                } else {
                    if (tvAvailbility.getText().toString().equalsIgnoreCase("OUT OF STOCK")) {
                        Toasty.error(getActivity(), "Product is out of stock", Toast.LENGTH_SHORT, true).show();

                    } else {
                        AppSettings.cartStatus = "2";
                        if (inCart.equalsIgnoreCase("Yes")) {
                            startActivity(new Intent(getActivity(), MyCartActivity.class));
                            getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                        } else {

                            if (!varId.equalsIgnoreCase("")) {
                                if (Utils.isNetworkConnectedMainThred(getActivity())) {

                                    loader.show();
                                    loader.setCancelable(false);
                                    loader.setCanceledOnTouchOutside(true);

                                    HitAddTocart cart = new HitAddTocart();
                                    cart.execute();
                                } else {
                                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                                }
                            } else {
                                Toasty.warning(getActivity(), "Please select variation", Toast.LENGTH_SHORT, true).show();

                            }
                        }
                        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(100);
                        startActivity(new Intent(getActivity(), MyCartActivity.class));
                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                    }
                }
                break;

            case R.id.tvRate:
                if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                } else {
                    RateDialog();
                }
                break;


            case R.id.tvViewAll:
                RateListing();
                break;

            case R.id.tvContinueShopping:

//                Intent intent = new Intent(getActivity(), DrawerActivity.class);
//                intent.putExtra("page","home");
//                startActivity(intent);

             /*   if (AppSettings.fromPage.equalsIgnoreCase("1")) {
                    replaceFragmentWithAnimation(new ProductListingFragment());
                } else if (AppSettings.fromPage.equalsIgnoreCase("5")) {
                    replaceFragmentWithAnimation(new RecentProductFragment());
                } else {
                    Intent i = new Intent(getActivity(), DrawerActivity.class);
                    i.putExtra("page", "home");
                    startActivity(i);
                    // getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                }*/


                break;

            case R.id.llSizeChart:
                startActivity(new Intent(getActivity(), SizeChartActivity.class).putExtra("SizeChart", WebService.imageURL + "/SizeChart/" + sizeList.get(0).get("SizeChart")));
                break;
        }
    }

    @Override
    public void onAnimationEnd(LikeButton likeButton) {
    }

    @Override
    public void liked(LikeButton likeButton) {

        if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
            icWishlist.setLiked(false);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                HitLike like = new HitLike();
                like.execute();
            } else {
                Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            HitUnlike unlike = new HitUnlike();
            unlike.execute();
        } else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }

    //=============================Adapter===============================================//

    public void replaceFragmentWithAnimation(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

    //===============================Dialog==============================================//
    private void RateDialog() {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.rateproduct);

        ImageView ivCross = dialog1.findViewById(R.id.ivCross);


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        String name = tvProductName.getText().toString();
        final EditText etReviews = dialog1.findViewById(R.id.etReviews);
        TextView tvSubmit = dialog1.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etReviews.getText().toString().trim().isEmpty()) {
                    Toasty.error(getActivity(), "Please Add Reviews", Toast.LENGTH_SHORT).show();
                } else {
                    description = etReviews.getText().toString();

                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        HitAddReviews reviews = new HitAddReviews();
                        reviews.execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }
        });


        //********Animation
//        float current = ratingbar.getRating();
//        ObjectAnimator anim = ObjectAnimator.ofFloat(ratingbar, "rating", current, 5f);
//        anim.setDuration(1000);
//        anim.start();

        SmileRating smileRating = (SmileRating) dialog1.findViewById(R.id.smile_rating);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {

                switch (smiley) {
                    case SmileRating.BAD:
                        rating = "2";
                        break;
                    case SmileRating.GOOD:
                        rating = "4";
                        break;
                    case SmileRating.GREAT:
                        rating = "5";
                        break;
                    case SmileRating.OKAY:
                        rating = "3";
                        break;
                    case SmileRating.TERRIBLE:
                        rating = "1";
                        break;
                }
            }
        });

        TextView tvProductName = dialog1.findViewById(R.id.tvProductName);
        tvProductName.setText(name);
        ImageView ivImage = dialog1.findViewById(R.id.ivImage);

//        Glide.with(getActivity())
//                .load(WebService.imageURL + jsonObject.getString("ImageUrl").replace(" ", "%20")
//                ).placeholder(R.drawable.placeholder11).into(img);

        Glide.with(getActivity()).load(MainPicture).centerCrop().placeholder(R.drawable.placeholder11).into(ivImage);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog1.getWindow().getAttributes());
        dialog1.getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog1.show();
    }

    private void RateListing() {

        GridLayoutManager mGridLayoutManager;

        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.listing_reviews);
        recyclerView = dialog1.findViewById(R.id.recyclerView);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mGridLayoutManager);
//        new HitReviews().execute();

        ImageView ivCross = dialog1.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog1.getWindow().getAttributes());
        dialog1.getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog1.show();
    }

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
            label.setText(list.get(position).get("Particular"));
            return row;
        }
    }

    private class FavNameHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RatingBar ratingbar;

        TextView tvReviews;


        public FavNameHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ratingbar = itemView.findViewById(R.id.ratingbar);
            tvReviews = itemView.findViewById(R.id.tvReviews);
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
            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, int position) {

            holder.tvName.setText(data.get(position).get("NameOrEmail"));
            LayerDrawable stars = (LayerDrawable) holder.ratingbar.getProgressDrawable();

            if (data.get(position).get("Ratting").equalsIgnoreCase("1")) {
                stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            }

            if (data.get(position).get("Ratting").equalsIgnoreCase("")) {
                holder.ratingbar.setRating(0);
            } else {
                holder.ratingbar.setRating(Float.parseFloat(data.get(position).get("Ratting")));
            }
            holder.tvReviews.setText(data.get(position).get("Review"));
        }

        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView tvFeature;
        TextView tvDescription;

        public Holder(View itemView) {
            super(itemView);
            tvFeature = itemView.findViewById(R.id.tvFeature);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }

    private class ProductAdapter1 extends RecyclerView.Adapter<Holder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter1(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public ProductAdapter1() {
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feature_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, int position) {

            holder.tvFeature.setText(data.get(position).get("FeatureName"));
            holder.tvDescription.setText(data.get(position).get("FeatureValue"));

        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    private class SizeHolder extends RecyclerView.ViewHolder {

        TextView tvSize;
        RelativeLayout relativelayout;


        public SizeHolder(View itemView) {
            super(itemView);
            tvSize = itemView.findViewById(R.id.tvSize);
            relativelayout = itemView.findViewById(R.id.relativelayout);

        }
    }

    private class SizeAdapter extends RecyclerView.Adapter<SizeHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        int selected_position = -1;

        public SizeAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public SizeAdapter() {
        }

        public SizeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SizeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_size, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final SizeHolder holder, final int position) {

            if (position == selected_position) {
                holder.relativelayout.getBackground().setColorFilter(getResources().getColor(R.color.red_900), PorterDuff.Mode.SRC_ATOP);
            } else {
                holder.relativelayout.setBackground(getResources().getDrawable(R.drawable.rectangle));
            }

            holder.tvSize.setText(data.get(position).get("Size"));


            holder.relativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    size = data.get(position).get("Size");
                    selected_position = position;
                    //  holder.relativelayout.getBackground().setColorFilter(getResources().getColor(R.color.red_900), PorterDuff.Mode.SRC_ATOP);
                    notifyDataSetChanged();
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

    private class ColorHolder extends RecyclerView.ViewHolder {

        // TextView tvSize;
        ImageView imageview, imageView7;
        CardView cardView;
        ConstraintLayout constraintLayout;

        public ColorHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            cardView = itemView.findViewById(R.id.card_color);
            constraintLayout = itemView.findViewById(R.id.ll_bg);
        }
    }

    private class ColorAdapter extends RecyclerView.Adapter<ColorHolder> {

        Integer isselected = -1;
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ColorAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public ColorAdapter() {
        }

        public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ColorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_color, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ColorHolder holder, final int position) {

            if (isselected == position) {
//                holder.cardView.setCardBackgroundColor(Color.parseColor("#ED1A63"));
                holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            } else {
//                holder.cardView.setCardBackgroundColor(Color.parseColor("#E8F4FA"));
                holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            }

            holder.cardView.setOnClickListener(view -> {
                colorname = data.get(position).get("VarriationName");
                isselected = position;
                Log.e("selectedColor ", colorname);
                notifyDataSetChanged();

                tvAddToCart.setEnabled(true);
                tvAddToCart.setAlpha(1f);

//                for (int i = 0; i < imageList.size(); i++) {
//                    if (imageList.get(i).get("ImageURL").equals(data.get(position).get("ImageURL"))) {
//                        myviewpager.setCurrentItem(i, true);
//                        colorname = data.get(position).get("VarriationName");
////                        colorimage = data.get(position).get("ColorImage");
//                        isselected = position;
//                        Log.v("fhhfhffhfhfhfh ", colorname);
//                    }
//                }
            });
            try {
                holder.imageview.setColorFilter(Color.parseColor(data.get(position).get("VarriationName")));
            } catch (Exception e) {
                Log.e("coloRaArr ", "" + e);
            }
        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    private class VarHolder extends RecyclerView.ViewHolder {

        // TextView tvSize;
        TextView tv_var;


        public VarHolder(View itemView) {
            super(itemView);
            tv_var = itemView.findViewById(R.id.tv_var);

        }
    }

    private class VarAdapter extends RecyclerView.Adapter<VarHolder> {
        Integer isselected = -1;
        ArrayList<HashMap<String, String>> Vardata = new ArrayList<HashMap<String, String>>();

        public VarAdapter(ArrayList<HashMap<String, String>> favList) {
            Vardata = favList;
        }

        public VarAdapter() {
        }

        public VarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VarHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_var, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final VarHolder holder, final int position) {
            Log.v("setting value in ", " ad   " + Vardata.get(position).get("VarriationName"));

            holder.tv_var.setBackground(getResources().getDrawable(R.drawable.bg_features));

            if (isselected == position) {
                holder.tv_var.setBackground(getResources().getDrawable(R.drawable.bg_features_selected));
            } else {
                holder.tv_var.setBackground(getResources().getDrawable(R.drawable.bg_features));
            }
            holder.itemView.setOnClickListener(view -> {
                varId = Vardata.get(position).get("VarriationName");
//                    varId = Vardata.get(position).get("VariationId");
                //  varSalePrice = Vardata.get(position).get("SalePrice");
                // holder.tv_var.setBackground( getResources(). getDrawable(R.drawarble.bg_selected));
//                    tvFinalprice.setText("\u20b9 " + Vardata.get(position).get("SalePrice"));
                isselected = position;
                notifyDataSetChanged();
                Log.v("isselected value in ", " ad   " + isselected);

                if (isColorAvailable) {
                    Toast.makeText(getContext(), "Please select your color !", Toast.LENGTH_SHORT).show();
                } else {
                    tvAddToCart.setEnabled(true);
                    tvAddToCart.setAlpha(1f);
                }
            });

            Log.v("isselected value in ", " ad   " + isselected);
            holder.tv_var.setText(Vardata.get(position).get("VarriationName"));

        }

        public int getItemCount() {
            return Vardata.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    private class ImageHolder extends RecyclerView.ViewHolder {

        // TextView tvSize;
        ImageView ivProduct;
        RelativeLayout rl_transview;
        TextView tv_count;

        public ImageHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            rl_transview = itemView.findViewById(R.id.rl_transview);
            tv_count = itemView.findViewById(R.id.tv_count);

        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {
        Integer isselected = -1;
        ArrayList<HashMap<String, String>> Vardata = new ArrayList<HashMap<String, String>>();

        public ImageAdapter(ArrayList<HashMap<String, String>> favList) {
            Vardata = favList;
        }

        public ImageAdapter() {
        }

        public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_images, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ImageHolder holder, final int position) {
            Log.v("ImageUrl", " ad   " + Vardata.get(position).get("ImageUrl"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myviewpager.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    Glide.with(getActivity())
                            .load(WebService.imageURL + Vardata.get(position).get("ImageUrl")
                                    .replace(" ", "%20")
                            ).centerCrop().placeholder(R.drawable.placeholder11).into(img);


                }
            });
            holder.rl_transview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    replaceFragmentWithAnimation(new GalleryFragment());
                }
            });
            if (position == 3) {
                Glide.with(holder.itemView.getContext()).load(WebService.imageURL + Vardata.get(position).get("ImageUrl")).placeholder(R.drawable.placeholder11).into(holder.ivProduct);
                holder.rl_transview.setVisibility(View.VISIBLE);
                holder.tv_count.setText(" + " + (Vardata.size() - 3) + "\n More Images");

            } else {
                Glide.with(holder.itemView.getContext()).load(WebService.imageURL + Vardata.get(position).get("ImageUrl")).placeholder(R.drawable.placeholder11).into(holder.ivProduct);
                holder.rl_transview.setVisibility(View.GONE);
            }

        }

        public int getItemCount() {
            if (Vardata.size() > 4) {
                return 4;
            } else {
                return Vardata.size();
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    private class SimilarHolder extends RecyclerView.ViewHolder {

        TextView tvProductName;
        TextView tvFinalprice;
        TextView tvDiscount;
        TextView tvOldPrice;
        ImageView ivProduct;
        RelativeLayout llDiscount;


        public SimilarHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            llDiscount = itemView.findViewById(R.id.llDiscount);

        }
    }

    private class SimilarProductAdapter extends RecyclerView.Adapter<SimilarHolder> {
        Integer isselected = -1;
        ArrayList<HashMap<String, String>> Vardata = new ArrayList<HashMap<String, String>>();

        public SimilarProductAdapter(ArrayList<HashMap<String, String>> favList) {
            Vardata = favList;
        }

        public SimilarProductAdapter() {
        }

        public SimilarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimilarHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_products_list_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final SimilarHolder holder, final int position) {
            //  Log.v("ImageUrl"," ad   "+Vardata.get(position).get("ImageUrl"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    productId = Vardata.get(position).get("ProductCode");
                    ProductCategory = Vardata.get(position).get("ProductCategory");
                    ProductName = Vardata.get(position).get("ProductName");
                    CatId = Vardata.get(position).get("MainCategoryCode");

                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitProductDetail().execute("similar");
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }

                }
            });

            holder.tvProductName.setText(Vardata.get(position).get("ProductName"));
            String dd = (Vardata.get(position).get("Discper"));
            Double discount = Double.parseDouble(dd);
            if (discount > 0) {
                holder.llDiscount.setVisibility(View.VISIBLE);
                holder.tvDiscount.setText(Vardata.get(position).get("Discper") + " % off");
            } else {
                holder.llDiscount.setVisibility(View.GONE);
            }


            holder.tvFinalprice.setText("\u20b9  " + Vardata.get(position).get("SalePrice"));
            holder.tvOldPrice.setText("\u20b9  " + Vardata.get(position).get("RegularPrice"));
            holder.tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            Glide.with(holder.itemView.getContext()).load(WebService.imageURL + Vardata.get(position).get("ProductMainImageUrl")).
                    placeholder(R.drawable.placeholder11).into(holder.ivProduct);

        }

        public int getItemCount() {
            return Vardata.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    //==================================API========================================================//
    public class HitAddTocart extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            // displayText = WebService.GetCategory("-1", "GetCategory");
            displayText = WebService.addTocart(pref.get(AppSettings.CustomerID), ProductId,
                    Quantity, varId, attrid, colorimage, rateid, "" + colorname, "" + varId, "addToCart");

            Log.e("addToCartReq ", "" + displayText);
            Log.e("addToCartReq ", "" + pref.get(AppSettings.CustomerID) + " // " + ProductId + " // " + Quantity + " // " + varId + " // " + attrid + " // " + colorimage + " // " + rateid + " // " + "" + colorname + " // " + "" + varId + " // " + "addToCart");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.e("addToCartRes ", "" + displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    Toasty.success(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
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
            Log.e("getCartList ", "" + displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getCartResponse");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String ProdID = obj.getString("ProductCode");

                        String RateId = "";
                        if (obj.has("RateId")) {
                            RateId = obj.getString("RateId");
                        }


                        if (ProdID.equalsIgnoreCase(ProductId) && RateId.equalsIgnoreCase(rateid)) {
//                            tvAddToCart.setText("GO TO CART");
                            tvAddToCart.setText("Add to Cart");


                            inCart = "Yes";

                            Log.e("ProdID", ProdID);
                            Log.e("ProdID", RateId);
                            Log.e("ProdID", ProductId);
                            Log.e("ProdID", rateid);
                        } else {
                            tvAddToCart.setText("Add to Cart");
                            inCart = "No";
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

    public class HitReviews extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.ReviewListing(ProductId, "ReviewList");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            arrayList.clear();

            Log.e("reviewList1", productId + displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    boolean status = jsonObject.getBoolean("Status");

                    if (status) {
                        ArrayList<String> array = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");

                        Log.e("reviewListArr", jsonArray + "");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("Review", obj.getString("Review"));
                            map.put("Ratting", obj.getString("Ratting"));
                            map.put("Name", obj.getString("Name"));
                            map.put("UserId", obj.getString("UserId"));
                            map.put("Ratingtime", obj.getString("Ratingtime"));
                            arrayList.add(map);
//                        sum = sum + Double.parseDouble(obj.getString("ReviewStatus"));
//                        array.add(obj.getString("ReviewStatus"));
                        }
                        llMain.setVisibility(View.VISIBLE);
                        tvRatings.setText(jsonObject.getString("TotalNumberOfRate"));
                        tvCount.setText(jsonObject.getString("TotalUser") + " users reviewed this product");

                        rv_review_list_min.setAdapter(new ProductAdapter(arrayList));
                        recyclerView.setAdapter(new ProductAdapter(arrayList));
                    } else {
                        llMain.setVisibility(View.GONE);
                    }

//                    Double sum = 0.0;
//                    Double avg;
//                    avg = sum / jsonArray.length();
//
//                    if (jsonArray.length() == 0) {
//                        tvRatings.setText("0");
//
//                        tvCount.setText("No Reviews");
//
//                        llMain.setVisibility(View.GONE);
//                    } else {
//                        tvRatings.setText(String.valueOf(roundTwoDecimals(avg)));
//
//                        tvCount.setText("" + jsonArray.length() + " reviews and ratings");
//
//                        llMain.setVisibility(View.VISIBLE);
//                    }
//
//                    String maxm = Collections.max(array);
//
//                    for (int j = 0; j < arrayList.size(); j++) {
//                        if (maxm.equalsIgnoreCase(arrayList.get(j).get("ReviewStatus"))) {
//                            tvName.setText(arrayList.get(j).get("NameOrEmail"));
//                            tvreviews.setText(arrayList.get(j).get("ReviewDiscription"));
//                            ratingbar.setRating(Float.parseFloat(arrayList.get(j).get("ReviewStatus")));
//                        }
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("reviewListErr", e + "");
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

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.LikeUnlike(pref.get(AppSettings.CustomerID), ProductId, "AddToWishlist");

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
                        icWishlist.setLiked(false);
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

            displayText = WebService.LikeUnlike(pref.get(AppSettings.CustomerID), ProductId, "DeleteWishList");


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

    public class HitAddReviews extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            Log.e("rating", rating);
            // displayText = WebService.GetCategory("-1", "GetCategory");
            displayText = WebService.AddReviews(pref.get(AppSettings.CustomerID), rating, ProductId, description, "AddReview");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    Toasty.success(getActivity(), "Reviews Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), DrawerActivity.class);
                    i.putExtra("page", "wishlist");
                    startActivity(i);
                    // getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
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

    public class HitProductDetail extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProductDetail(productId, ProductCategory, ProductName, CatId, "getProductdetail");

            Log.e("getProductDetailReq ", productId + "/" + ProductCategory + "/" + ProductName + "/" + CatId + displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            imagesList.clear();
            varList.clear();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonObject = new JSONObject(displayText);
                    //  jsonProductlist = new JSONObject(displayText);
                    if (jsonObject.has("Status")) {
                        if (jsonObject.getBoolean("Status")) {
                            loader.dismiss();
                            JSONArray jsonResponse = jsonObject.getJSONArray("plistitem");

                            for (int i = 0; i < jsonResponse.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                JSONObject object = jsonResponse.getJSONObject(i);
                                Log.e("object", "" + object);
                                tvFinalprice.setText("\u20b9 " + object.getString("SalePrice"));
                                tvOldPrice.setText("\u20b9 " + object.getString("RegularPrice"));
                                //  tvMinQty.setText("1 set (" + object.getString("minQuantity") + " pcs )");
                                tvProductName.setText(object.getString("ProductName"));
                                Log.v("hhdfhdhdjdj", "" + object.getString("Discper"));
                                tvDiscount.setText((object.getString("Discper")) + " % off");
                                ProductId = object.getString("ProductCode");
                                Log.e("testProductId2 ", "" + ProductId);
//                                if (object.has("VarId"))
//                                    varId = (object.getString("VarId"));
//                                if (object.has("AttrId"))
//                                    attrid = (object.getString("AttrId"));
                                String Url = object.getString("ProductMainImageUrl");
                                MainPicture = WebService.imageURL + Url.replace(" ", "%20");


                                // recyclerDescription.setAdapter(new ProductAdapter1(descrList));
                                tvDescription.setText(object.getString("ProductDescription"));


                                String feature = android.text.Html.fromHtml(object.getString("ProductSpecification").trim()).toString().trim();

                                Log.v("gghghghjghgh", feature);

                                if (feature.isEmpty()) {
                                    tvFeature.setText("No Feature");
                                    tvFeature.setVisibility(View.GONE);
                                } else {
                                    tvFeature.setText(feature);
                                    tvFeature.setVisibility(View.VISIBLE);

                                }


                                String description = android.text.Html.fromHtml(object.getString("ProductDescription").trim()).toString().trim();

                                tvDescription.setText(description);
                                double saving = Double.parseDouble(object.getString("RegularPrice")) - Double.parseDouble(object.getString("SalePrice"));
                                tvSave.setText("Save \u20b9 " + String.valueOf(saving));
                                double retailprice = Double.parseDouble(object.getString("RegularPrice"));

//                                if (tvFinalprice.getText().toString().equalsIgnoreCase(tvOldPrice.getText().toString())) {
//                                    Log.e("equal", "");
//                                }
//                                 if (retailprice!=0.0) {
//                                     double res = (saving / retailprice) * 100;
//                                     roundTwoDecimals(res);
//                                     int i2 = (int) Math.round(roundTwoDecimals(res));
//                                     tvDiscount.setText(String.valueOf(i2) + " % off");
//
//                                 }
                            }

                            JSONArray imageArray = jsonObject.getJSONArray("crimage");
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (i == 0) {
                                    JSONObject jsonObject = imageArray.getJSONObject(i);

                                    myviewpager.setVisibility(View.GONE);
                                    img.setVisibility(View.VISIBLE);
                                    Glide.with(getActivity())
                                            .load(WebService.imageURL + jsonObject.getString("ImageUrl").replace(" ", "%20")
                                            ).centerCrop().placeholder(R.drawable.placeholder11).into(img);
                                }
                            }


                            imageList.clear();


                            sizeList.clear();


                            colorList.clear();


                            if (imageArray.length() == 0) {

                            } else {
                                for (int i = 0; i < imageArray.length(); i++) {
                                    HashMap<String, String> map = new HashMap<>();
                                    JSONObject object = imageArray.getJSONObject(i);
                                    String completeUrl = object.getString("ImageUrl");

                                    if (completeUrl.equals("")) {
                                        map.put("ColorCode", "1");
                                        map.put("ImageURL", MainPicture);
                                        imageList.add(map);
                                    } else {
                                        completeUrl = WebService.imageURL + completeUrl.replace(" ", "%20");
                                        map.put("ColorCode", "1");
                                        map.put("ImageURL", completeUrl);
                                        Log.e("completeUrl", completeUrl);
                                        imageList.add(map);
                                    }
                                }
                            }
                            JSONArray varArray = new JSONArray();

                            if (jsonObject.has("varsize"))
                                varArray = jsonObject.getJSONArray("varsize");

//                            if (jsonObject.has("prVarr"))
//                                varArray = jsonObject.getJSONArray("prVarr");


                            Log.v("vararr", "   legth " + varArray);


                            for (int i = 0; i < varArray.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                JSONObject object = varArray.getJSONObject(i);
//                                map.put("SalePrice", object.getString("SalePrice"));
                                map.put("VarriationName", object.getString("sizename"));
//                                map.put("VariationId", object.getString("VariationId"));

                                varList.add(map);

                            }
                            if (varList.size() > 0) {
                                rv_varaiation.setVisibility(View.VISIBLE);
                                rv_varaiation.setAdapter(new VarAdapter(varList));
                            } else {
                                rv_varaiation.setVisibility(View.GONE);
                            }

                            JSONArray varsizeArray = new JSONArray();
                            if (jsonObject.has("varsize"))
                                varsizeArray = jsonObject.getJSONArray("prVarr");

                            Log.e("varsizeArray ", "" + varsizeArray);

                            JSONArray ImagesArray = new JSONArray();

                            if (jsonObject.has("crimage"))
                                ImagesArray = jsonObject.getJSONArray("crimage");
                            imagesList.clear();
                            for (int i = 0; i < ImagesArray.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                JSONObject object = ImagesArray.getJSONObject(i);
                                map.put("ImageUrl", object.getString("ImageUrl"));


                                imagesList.add(map);

                            }
                            if (imagesList.size() > 0) {
                                rv_images.setVisibility(View.VISIBLE);
                                rv_images.setAdapter(new ImageAdapter(imagesList));
                            } else {
                                rv_images.setVisibility(View.GONE);
                            }
                            Log.v("imagesListsize", "" + imagesList.size());


                        }

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
}
