package claco.store.fragments;

import static claco.store.utils.WebService.imageURL;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import claco.store.APi.ApiClent;
import claco.store.APi.ApiResponse.CategoryIteam;
import claco.store.APi.ApiResponse.CategoryObject;
import claco.store.APi.ApiResponse.ProductsLists;
import claco.store.APi.ApiResponse.Proitemnew;
import claco.store.APi.ApiResponse.recent.RecentList;
import claco.store.Activities.BrandNewActivity;
import claco.store.Activities.FilterActivity;
import claco.store.Activities.WalletActivity;
import claco.store.ApiUtils.APiUtils;
import claco.store.DataModel.SectionDataModel;
import claco.store.Main.DrawerActivity;
import claco.store.Main.LoginActivity;
import claco.store.ProductFragment.OrderDetailsFragment;
import claco.store.ProductFragment.ProductDescriptionFragment;
import claco.store.ProductFragment.ProductListingFragment;
import claco.store.ProductFragment.ProductSubcategoryFragment;
import claco.store.ProductFragment.RecentProductFragment;
import claco.store.R;
import claco.store.adapters.BannerAdapter;
import claco.store.adapters.CardPagerAdapterBottom;
import claco.store.adapters.CategoryAdapter;
import claco.store.adapters.OurProductAdapter;
import claco.store.adapters.RecentAdapter;
import claco.store.adapters.SliderAdapter;
import claco.store.model.QuantityModel;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar;

public class DashboardFragment extends Fragment {
    public static JSONObject jsonObject;
    public static JSONObject jsonProductlist;
    CustomLoader loader;
    OurProductAdapter ourProductAdapter;
    public static JSONObject jsoncategory;
    public static JSONObject jsonrecentlproduct;
    public static JSONObject jsonmaincategory;
    public static JSONObject jsondistrict;
    public static JSONObject jsonbanner;
    public static JSONArray jsonResponse;
    public static String productId, ProductCategory, ProductName, CatId;
    public static String mainCategoryId;
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
    public static String ProductIdOrder;
    public static String Url;
    public static String PaymentMode;
    public static String size;
    public static String timeslot;
    Spinner spinnerReason;
    EditText etComment;
    long DELAY_MS = 1000;
    long DELAY_MS1 = 1000;
    long PERIOD_MS = 3000;
    long PERIOD_MS1 = 3000;
    String track_order_id, cancelReason;
    ArrayList<HashMap<String, String>> reasonList = new ArrayList<>();
    String SubcatId = "";
    //view
    View v;
    ArrayList<SectionDataModel> allSampleData = new ArrayList<>();
    ImageView ivBanner;
    FrameLayout framelayout;
    String filter;
    //Viewpager
    ViewPager mViewPager;
    //Arraylist
    //Textview
    TextView tvViewAll;
    int currentPage = 0;
    int currentPage1 = 0;
    Timer timer, timer1;
    String product_name12;
    String rate_id = "";
    Object objCollection;
    JSONArray projsonArray = new JSONArray();
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    EditText etPincode;
    TextView tvDeliveryAvailable, tvDeliveryNotAvailable, tvSubmit;
    ArrayList<HashMap<String, String>> recentArray = new ArrayList<>();
    //Recyclerview
    RecyclerView categoriesRecyclerView;
    RecyclerView recViewDeals;
    //    RecyclerView productRecyclerView;

    GridLayoutManager mGridLayoutManager;
    ArrayList<HashMap<String, String>> arrayListMain = new ArrayList<>();
    ArrayList<String> bannerList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> arrayBanner = new ArrayList<>();
    ArrayList<String> arrayBannerMain = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayBannerbelow = new ArrayList<>();
    ArrayList<HashMap<String, String>> dealArray = new ArrayList<>();
    ArrayList<HashMap<String, String>> productArray = new ArrayList<>();
    ArrayList<HashMap<String, String>> cartlist = new ArrayList<>();
    ArrayList<QuantityModel> quantityvaluelist = new ArrayList<>();
    String mainCatId;
    int count = 0;
    RecyclerView recyclerviewQuantity;
    RecyclerView ourProductRecyclerView;
    AlertDialog.Builder builder;
    Preferences pref;
    String ProductId;
    String varId = "";
    String attrid = "";
    String quantity;
    String CartListID;
    Dialog dialogQuantity;
    Dialog dialog;
    CardView cardviewCheck;
    Spinner spinnerDistrict;
    LinearLayout llDealOfDay;
    LinearLayout container_order_history, container_categories, container_recently_added;
    ArrayList<HashMap<String, String>> cityList = new ArrayList<>();
    RecentProductAdapterNew recentProductAdapterNew;
    ViewPager viewpagerDown;
    OrderAdapter proadapter;
//    RecyclerView rv_recentorder,g_recyclerView,b_recyclerView,h_recyclerView,j_recyclerView,k_recyclerView,m_recyclerView,u_recyclerView,w_recyclerView;
    SliderView sliderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.homefragment, container, false);
        DrawerActivity.ivHome.setVisibility(View.VISIBLE);
        DrawerActivity.tvHeaderText.setText(R.string.app_name);
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
loader.show();
        categoriesRecyclerView = v.findViewById(R.id.categoriesRecyclerView);
        ourProductRecyclerView = v.findViewById(R.id.ourProductRecyclerView);
//        b_recyclerView = v.findViewById(R.id.beauty_product_recyclerView);
//        g_recyclerView = v.findViewById(R.id.groceryRecyclerView);
//        h_recyclerView = v.findViewById(R.id.home_decoration_recyclerView);
//        j_recyclerView = v.findViewById(R.id.jewellery_recyclerView);
//        k_recyclerView = v.findViewById(R.id.kid_recyclerView);
//        u_recyclerView = v.findViewById(R.id.uniform_recyclerView);
//        m_recyclerView = v.findViewById(R.id.man_recyclerView);
//        w_recyclerView = v.findViewById(R.id.woman_recyclerView);

        ourProductRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        w_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        m_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        g_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        k_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        u_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        h_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        b_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        j_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        tvViewAll = v.findViewById(R.id.tvViewAll);
        ivBanner = v.findViewById(R.id.ivBanner);
        framelayout = v.findViewById(R.id.framelayout);
        spinnerDistrict = v.findViewById(R.id.spinnerDistrict);
        DrawerActivity.rl_search.setVisibility(View.VISIBLE);
        cardviewCheck = v.findViewById(R.id.cardviewCheck);
        sliderView = v.findViewById(R.id.imageSlider);

        DrawerActivity.ivfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFilterDialog();
                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });

//        APiUtils.INSTANSE.recentProduct(loader,new APiUtils.ApiCallBAck() {
//            @Override
//            public void onSuccess(Object data) {
//                RecentList list=(RecentList) data;
//
//                if(list.getBeutiProduct()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getBeutiProduct(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"BeutiProduct");
//                                b_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getGrocery()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getGrocery(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"Grocery");
//                    g_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getHomeDecoration()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getHomeDecoration(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"HomeDecoration");
//                    h_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getJewellery()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getJewellery(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"Jewellery");
//                    j_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getKids()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getKids(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"Kids");
//                    k_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getMens()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getMens(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"Mens");
//                    m_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getUniform()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getUniform(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"Uniform");
//                    u_recyclerView.setAdapter(recentAdapter);
//                }
//                if(list.getWomen()!=null){
//                    RecentAdapter recentAdapter = new  RecentAdapter(getContext(),list.getWomen(), new APiUtils.ApiCallBAck() {
//                        @Override
//                        public void onSuccess(Object data) {
//
//                        }
//
//                        @Override
//                        public void onFailed(String message) {
//
//                        }
//                    },"Women");
//                    w_recyclerView.setAdapter(recentAdapter);
//                }
//            }
//
//
//
//
//
//
//
//
//
//            @Override
//            public void onFailed(String message) {
//
//            }
//        });

        APiUtils.INSTANSE.allProduct(loader, new APiUtils.ApiCallBAck() {
            @Override
            public void onSuccess(Object data) {
                ProductsLists productsLists = (ProductsLists) data;
                List<Proitemnew> productList = productsLists.getProitemnew();
                if (productList != null && !productList.isEmpty()) {
                    OurProductAdapter ourProductAdapter = new OurProductAdapter(getContext(), productList, new APiUtils.ApiCallBAck() {
                        @Override
                        public void onSuccess(Object data) {
                            Proitemnew proitemnew = (Proitemnew) data;
                            varId = proitemnew.getVarId();
                            attrid = proitemnew.getAttrId();
                            productId = proitemnew.getProductCode();
                            ProductCategory = proitemnew.getProductCategory();
                            ProductName = proitemnew.getProductName();
                            CatId = proitemnew.getMainCategoryCode();

                            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(true);
                                new HitProductDetail().execute();
                            } else {
                                Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                            }
                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ourProductRecyclerView.setAdapter(ourProductAdapter);
                } else {
                    Toast.makeText(getContext(), "Product list is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(getContext(), "Failed to fetch products: " + message, Toast.LENGTH_SHORT).show();
            }
        });



        APiUtils.INSTANSE.homeCategory(loader, new APiUtils.ApiCallBAck() {
            @Override
            public void onSuccess(Object data) {
                CategoryObject categoryObject = (CategoryObject) data;
                List<CategoryIteam> categoryList = categoryObject.getResponse();
                if (categoryList != null && !categoryList.isEmpty()) {
                    categoriesRecyclerView.setAdapter(new CategoryAdapter(getContext(), categoryList, new APiUtils.ApiCallBAck() {
                        @Override
                        public void onSuccess(Object data) {
                            CategoryIteam categoryItem = (CategoryIteam) data;
                            AppSettings.dashboard = "1";
                            mainCatId = String.valueOf(categoryItem.getMainCategoryId());
                            mainCategoryId = String.valueOf(categoryItem.getMainCategoryId());
                            AppSettings.catname = categoryItem.getMainCategoryName();

                            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(true);
                                HitProducts cat = new HitProducts();
                                cat.execute();
                            } else {
                                Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                            }
                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                        }
                    }));
                } else {
                    Toast.makeText(getContext(), "Category list is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(getContext(), "Failed to fetch categories: " + message, Toast.LENGTH_SHORT).show();
            }
        });


        DrawerActivity.et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
                    if (TextUtils.isEmpty(DrawerActivity.et_search.getText().toString())) {
                        Toast.makeText(getActivity(), "Please enter text", Toast.LENGTH_LONG).show();
                    } else {
                        if (isNetworkConnected(getActivity())) {
                            new HitSearch().execute();
                        } else {
                            Toast.makeText(getActivity(), "No internet access", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });













        llDealOfDay = v.findViewById(R.id.llDealOfDay);
        viewpagerDown = v.findViewById(R.id.viewpagerDown);

        pref = new Preferences(getActivity());

// Show custom loader
        loader.show();

        container_categories = v.findViewById(R.id.container_categories);
        container_recently_added = v.findViewById(R.id.container_recently_added);

// Set up RecyclerViews
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        recViewDeals = v.findViewById(R.id.recViewDeals);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recViewDeals.setLayoutManager(linearLayoutManager2);

        mViewPager = v.findViewById(R.id.myviewpager);
        TabLayout tabLayout = v.findViewById(R.id.tabDots);

        tabLayout.setupWithViewPager(mViewPager, true);

// Set menu icon click listener
        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_menu);
        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });

// Set banner
        setBanner();

// Check network connectivity and execute background tasks
        if (Utils.isNetworkConnectedMainThred(requireActivity())) {
            loader.show(); // Show loader
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);

            // Execute background tasks
            new HitBanner().execute();
            new HitGetCart().execute();
            new HitWalletPoint().execute();
            new HitBannerBottom().execute();
            new HitRecentlyAddedProducNew().execute();
            // new HitCategory().execute(); // Uncomment if needed

        } else {
            // Show error toast for no internet access
            Toasty.error(getContext(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }





        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentWithAnimation(new RecentProductFragment());
            }
        });

        DrawerActivity.tv_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }
        });

        cardviewCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryLocationPopup();
            }
        });

        ourProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                count = linearLayoutManager.findFirstVisibleItemPosition();
            }
        });

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                pref.set(AppSettings.District, cityList.get(position).get("id"));
                pref.set(AppSettings.DistrictName, cityList.get(position).get("name"));
                pref.commit();
                Log.v("vjjhjghhggh123", AppSettings.dashboard);

                if (AppSettings.dashboard.equals("0")) {
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        HitRecentlyAddedProduct product = new HitRecentlyAddedProduct();
                        product.execute();
                        new HitBanner().execute();
                    }
                } else {
                    HitRecentlyAddedProduct product = new HitRecentlyAddedProduct();
                    product.execute();
                    new HitBanner().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout1, cityList);
        adapter.notifyDataSetChanged();
        spinnerDistrict.setAdapter(adapter);

        return v;

    }


    // Method to check network connectivity
    private boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showFilterDialog() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filterdialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        String[] filtertypes = {"Select filter type ", "Brand", "Rate", "Offer"};
        String filtertype = "";
        RangeSeekBar seekbar = dialog.findViewById(R.id.rangeSeekBar);
        LinearLayout llRate = dialog.findViewById(R.id.llRate);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView tvFrom = (TextView) dialog.findViewById(R.id.tvFrom);
        TextView tvTo = (TextView) dialog.findViewById(R.id.tvTo);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);

        final Spinner spinner1 = dialog.findViewById(R.id.sp_filter);

        ArrayAdapter apr = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, filtertypes);
        apr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(apr);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter = spinner1.getSelectedItem().toString();
                Log.v("prefix", "" + filter);
                if (filter.equalsIgnoreCase("Brand")) {

                    llRate.setVisibility(View.GONE);
                    startActivity(new Intent(getActivity(), BrandNewActivity.class));
                    dialog.dismiss();
                } else if (filter.equalsIgnoreCase("Rate")) {

                    llRate.setVisibility(View.VISIBLE);

                } else {
                    llRate.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged(
                    final RangeSeekBar seekBar, final int progressStart, final int progressEnd, final boolean fromUser) {
                tvFrom.setText("From " + progressStart);
                tvTo.setText("To " + progressEnd);
            }

            @Override
            public void onStartTrackingTouch(final RangeSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(final RangeSeekBar seekBar) {
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void setBanner() {
        BannerAdapter mCardAdapter = new BannerAdapter(getActivity(), bannerList) {
            @Override
            protected void onCategoryClick(View view, String str) {
                // Handle category click if needed
            }
        };

        mViewPager.setAdapter(mCardAdapter);
        mCardAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setClipToPadding(false);
        mViewPager.setCurrentItem(0, true);
        mViewPager.setPageMargin(10);

        final int NUM_PAGES = bannerList.size();

        final Handler handler = new Handler();

        final Runnable updateRunnable = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(updateRunnable);
            }
        }, DELAY_MS, PERIOD_MS);
    }



    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public void DeliveryLocationPopup() {
        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delivery_location_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(R.color.blacktrans);

        Spinner spinner = dialog.findViewById(R.id.spinnerDistrict);
        spinner.setVisibility(View.GONE);
        etPincode = dialog.findViewById(R.id.etPincode);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        tvDeliveryAvailable = dialog.findViewById(R.id.tvDeliveryAvailable);
        tvDeliveryNotAvailable = dialog.findViewById(R.id.tvDeliveryNotAvailable);
        tvSubmit = dialog.findViewById(R.id.tvSubmit);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout, cityList);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);
        for (int i = 0; i < cityList.size(); i++) {
            if (pref.get(AppSettings.DistrictName) != null) {
                if (pref.get(AppSettings.DistrictName).equals(cityList.get(i).get("name"))) {
                    spinner.setSelection(i);
                    break;
                }
            } else {
                break;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                pref.set(AppSettings.District, cityList.get(position).get("id"));
                pref.set(AppSettings.DistrictName, cityList.get(position).get("name"));
                pref.commit();

                spinnerDistrict.setSelection(position);

                new HitBanner().execute();
                HitRecentlyAddedProduct product = new HitRecentlyAddedProduct();
                product.execute();

                HitDealOfDayProduct deal = new HitDealOfDayProduct();
                deal.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    new HitPincode().execute();
                } else {
                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        dialog.show();
    }

    //==========================Dialog====================================================//

    public void QuantityPopup(String productname, String quantity, String position, String value) {
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
                map.put("ID", jsonObject.getString("Id"));
                map.put("Particular", jsonObject.getString("Particular"));
                map.put("Regularprice", jsonObject.getString("Regularprice"));
                map.put("SalePrice", jsonObject.getString("SalePrice"));
                quantitylist.add(map);
            }
            recyclerviewQuantity.setAdapter(new QuantityAdapter(quantitylist, position, value));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        dialogQuantity.show();


    }

    public void setCatClik(String pID,String rateId){
        productId = pID;
        rate_id=rateId;
        Log.e("product", productId);
        //Hitting API
        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            new HitProductDetail().execute();
        } else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void ReturnPopup(final String s) {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.return_request);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        ImageView ivCross = dialog.findViewById(R.id.ivCross);
        TextView tvOrderId = dialog.findViewById(R.id.tvOrderId);
        TextView tvProductName = dialog.findViewById(R.id.tvProductName);
        TextView tvOrderQuantity = dialog.findViewById(R.id.tvOrderQuantity);
        TextView tvOrderPrice = dialog.findViewById(R.id.tvOrderPrice);
        ImageView productImage = dialog.findViewById(R.id.productImage);
        spinnerReason = dialog.findViewById(R.id.spinnerReason);
        etComment = dialog.findViewById(R.id.etComment);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout, reasonList);
        adapter.notifyDataSetChanged();
        spinnerReason.setAdapter(adapter);


        tvOrderId.setText(OrderId);
        tvProductName.setText(ProductTitle);
        tvOrderQuantity.setText("Quantiy: " + Quantity);
        tvOrderPrice.setText("\u20B9 " + TotalPrice);
        Glide.with(getActivity()).load(Url).placeholder(R.drawable.placeholder11).into(productImage);

//  Url=imageURL + data.get(position).get("ImageURL");
//                    ProductId = data.get(position).get("ProductCode");
//                    OrderId = data.get(position).get("OrderId");
//                    Quantity = data.get(position).get("Quantity");
//                    orderDate = data.get(position).get("orderDate");
//                    ProductTitle = data.get(position).get("ProductTitle");
//                    TotalPrice = data.get(position).get("TotalPrice");
        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.v("reasonlistvalue", reasonList.get(spinnerReason.getSelectedItemPosition()).get("ReasonId"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    HitReturnOrder order = new HitReturnOrder();
                    order.execute(s);
                } else {
                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                }

                dialog.dismiss();
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    public void CancelPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertyesno_order);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvYes = dialog.findViewById(R.id.tvOk);
        TextView tvCancel = dialog.findViewById(R.id.tvcancel);
        EditText et = dialog.findViewById(R.id.et);
        TextView tvReason = dialog.findViewById(R.id.textView22);
        TextView tvAlertMsg = dialog.findViewById(R.id.tvAlertMsg);

        tvAlertMsg.setText("Confirmation Alert..!!!");
        tvReason.setText("Are you sure want to Cancel this order ?");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReason = et.getText().toString(); // Moved inside the onClick to get the latest text
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    HitCancelOrder order = new HitCancelOrder();
                    order.execute();
                } else {
                    Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                }
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    //==============================Adapter======================================================//



    public static class FavNameHolder extends RecyclerView.ViewHolder {

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
        RelativeLayout rlSpinner;

        Spinner spinnerQuantity;
        JSONArray jArray;

        public FavNameHolder(View itemView) {
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


    public static class FavNameHolderDeal extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvRating;
        TextView tvDiscount;
        TextView tvFinalprice;
        TextView tvOldPrice;

        TextView tvSave;
        TextView tvAddtoCart;

        TextView tvProductName;

        LinearLayout layout_item;
        LinearLayout llAddtocart;
        RelativeLayout rlAddtocart;
        ImageButton cart_quant_minus;
        ImageButton cart_quant_add;
        TextView cart_item_number;
        CardView cardView;
        TextView tvQuantity;
        RelativeLayout rlSpinner;

        Spinner spinnerQuantity;
        JSONArray jArray;


        public FavNameHolderDeal(View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvSave = itemView.findViewById(R.id.tvSave);
            tvAddtoCart = itemView.findViewById(R.id.tvAddtoCart);
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
          public void onBindViewHolder(@NonNull final FavNameHolderNew holder, int position) {
                holder.tvSize.setText("1");
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  varId = (data.get(adapterPosition).get("VarId"));
                                  attrid = (data.get(adapterPosition).get("AttrId"));
                                  productId = data.get(adapterPosition).get("ProductCode");
                                  ProductCategory = data.get(adapterPosition).get("ProductCategory");
                                  ProductName = data.get(adapterPosition).get("ProductName");
                                  CatId = data.get(adapterPosition).get("MainCategoryCode");

                                  if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                        loader.show();
                                        loader.setCancelable(false);
                                        loader.setCanceledOnTouchOutside(true);
                                        new HitProductDetail().execute();
                                  } else {
                                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                                  }
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
                          || data.get(position).get("RegularPrice").equalsIgnoreCase("00.00")) {
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

                try {
                      Glide.with(getActivity()).load(URL).placeholder(R.drawable.placeholder11).into(holder.ivProduct);
                } catch (Exception e) {

                }

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

                holder.cart_quant_add.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                                  count = count + 1;

                                  holder.cart_item_number.setText(String.valueOf(count));

                                  Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                  vibe.vibrate(100);

                                  varId = (data.get(adapterPosition).get("VarId"));
                                  attrid = (data.get(adapterPosition).get("AttrId"));
                                  productId = data.get(adapterPosition).get("ProductCode");
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
                      }
                });

                holder.cart_quant_minus.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                                  if (count > 1) {
                                        int minQty = 1;
                                        if (count > minQty) {
                                              Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                              vibe.vibrate(100);
                                              count = count - 1;
                                              ProductId = data.get(adapterPosition).get("ProductCode");
                                              holder.cart_item_number.setText(String.valueOf(count));
                                              varId = (data.get(adapterPosition).get("VarId"));
                                              attrid = (data.get(adapterPosition).get("AttrId"));
                                              quantity = holder.cart_item_number.getText().toString();
                                              CartListID = data.get(adapterPosition).get("CartListID");
                                              UpdateCartNew cart = new UpdateCartNew();
                                              cart.execute();
                                        } else {
                                              Toasty.warning(getActivity(), "Can't Order less than " + minQty, Toast.LENGTH_SHORT).show();
                                        }
                                  }
                            }
                      }
                });

                holder.rlAddtocart.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                                  } else {
                                        if (holder.tvAddtoCart.getText().toString().equalsIgnoreCase("Out Of Stock")) {
                                              Toasty.error(getActivity(), "Product is out of stock", Toast.LENGTH_SHORT, true).show();
                                        } else {
                                              AppSettings.cartStatus = "2";
                                              varId = (data.get(adapterPosition).get("VarId"));
                                              attrid = (data.get(adapterPosition).get("AttrId"));
                                              ProductId = data.get(adapterPosition).get("ProductCode");
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
                      }
                });
          }






//        end





        public int getItemCount() {
//            return data.size();
            if (data.size() > 100) {
                return 100;
            } else {
                return data.size();
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }








    private class DealProductAdapter extends RecyclerView.Adapter<FavNameHolderDeal> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public DealProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public DealProductAdapter() {
        }

        public FavNameHolderDeal onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolderDeal(LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_deal, parent, false));
        }








          @Override
          public void onBindViewHolder(@NonNull final FavNameHolderDeal holder, int position) {

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  productId = data.get(adapterPosition).get("ProductId");

                                  Log.e("product", productId);
                                  //Hitting API
                                  if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                        loader.show();
                                        loader.setCancelable(false);
                                        loader.setCanceledOnTouchOutside(true);
                                        new HitProductDetail().execute();
                                  } else {
                                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                                  }
                            }
                      }
                });

                holder.tvOldPrice.setText("\u20b9" + data.get(position).get("MRP"));
                holder.tvProductName.setText(data.get(position).get("ProductTitle"));
                String URL = WebService.imageURL + data.get(position).get("MainPicture");
                double saving = Double.parseDouble(data.get(position).get("MRP")) - Double.parseDouble(data.get(position).get("SalePrice"));
                holder.tvSave.setText("Save \u20b9 " + String.valueOf(saving));
                holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvOldPrice.setText("\u20b9 " + data.get(position).get("MRP"));
                holder.tvFinalprice.setText("\u20b9 " + data.get(position).get("SalePrice"));
                double retailprice = Double.parseDouble(data.get(position).get("MRP"));
                if (retailprice != 0) {
                      double res = (saving / retailprice) * 100;
                      roundTwoDecimals(res);
                      int i2 = (int) Math.round(roundTwoDecimals(res));
                      holder.tvDiscount.setText(String.valueOf(i2) + " % off");
                }
                try {
                      Glide.with(getActivity()).load(URL).placeholder(R.drawable.placeholder11).into(holder.ivProduct);
                } catch (Exception e) {

                }

                Log.v("fffffffn", data.get(position).get("Status"));

                if (data.get(position).get("Status").equalsIgnoreCase("1")) {
                      holder.tvAddtoCart.setText("Add to Cart");
                } else {
                      holder.tvAddtoCart.setText("Out Of Stock");
                }

                if (!cartlist.isEmpty()) {
                      for (int j = 0; j < cartlist.size(); j++) {
                            if (data.get(position).get("ProductId").equalsIgnoreCase(cartlist.get(j).get("ProductID"))) {
                                  holder.llAddtocart.setVisibility(View.VISIBLE);
                                  holder.rlAddtocart.setVisibility(View.GONE);
                                  holder.cart_item_number.setText(String.valueOf(cartlist.get(j).get("Quantity")));
                            }
                      }
                }

                holder.cart_quant_add.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                                  if (data.get(adapterPosition).get("minQuantity").equalsIgnoreCase("0")) {
                                        count = count + 1;
                                  } else {
                                        count = count + Integer.parseInt(data.get(adapterPosition).get("minQuantity"));
                                  }

                                  holder.cart_item_number.setText(String.valueOf(count));

                                  Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                  vibe.vibrate(100);

                                  varId = (data.get(adapterPosition).get("VarId"));
                                  attrid = (data.get(adapterPosition).get("AttrId"));
                                  ProductId = data.get(adapterPosition).get("ProductCode");
                                  holder.cart_item_number.setText(String.valueOf(count));
                                  quantity = holder.cart_item_number.getText().toString();
                                  new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {

                                              HitUpdateQuantity cart = new HitUpdateQuantity();
                                              cart.execute();

                                        }
                                  }, 100);
                            }
                      }
                });

                holder.cart_quant_minus.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                                  if (count > 1) {
                                        int minQty = 1;
                                        if (count > minQty) {
                                              Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                              vibe.vibrate(100);

                                              count = count - 1;
                                              varId = (data.get(adapterPosition).get("VarId"));
                                              attrid = (data.get(adapterPosition).get("AttrId"));
                                              ProductId = data.get(adapterPosition).get("ProductCode");
                                              holder.cart_item_number.setText(String.valueOf(count));

                                              quantity = holder.cart_item_number.getText().toString();
                                              HitUpdateQuantity cart = new HitUpdateQuantity();
                                              cart.execute();
                                        } else {
                                              Toasty.warning(getActivity(), "Can't Order less than " + minQty, Toast.LENGTH_SHORT).show();
                                        }
                                  }
                            }
                      }
                });


                holder.rlAddtocart.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Obtain position here
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                  if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                                  } else {
                                        if (holder.tvAddtoCart.getText().toString().equalsIgnoreCase("Out Of Stock")) {
                                              Toasty.error(getActivity(), "Product is out of stock", Toast.LENGTH_SHORT, true).show();
                                        } else {
                                              AppSettings.cartStatus = "2";
                                              varId = (data.get(adapterPosition).get("VarId"));
                                              attrid = (data.get(adapterPosition).get("AttrId"));
                                              ProductId = data.get(adapterPosition).get("ProductCode");
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
                      }
                });
          }




//        end

        public int getItemCount() {
//            return data.size();

            if (data.size() > 100) {
                return 100;
            } else {
                return data.size();
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }








    private class FavNameHolder1 extends RecyclerView.ViewHolder {
        //  LinearLayout rrMain;
        ImageView image;
        TextView tvCatName;


        public FavNameHolder1(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvCatName = itemView.findViewById(R.id.tvCatName);
            //  rrMain = itemView.findViewById(R.id.rrMain);


        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<FavNameHolder1> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public ProductAdapter() {

        }

        public FavNameHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavNameHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items, parent, false));
        }







          @Override
          public void onBindViewHolder(@NonNull final FavNameHolder1 holder, final int position) {

                Log.e("main1", arrayListMain.get(holder.getAdapterPosition()).get("mainCategoryImage"));

                try {
                      Glide.with(getActivity()).load((String) ((HashMap) arrayListMain.get(holder.getAdapterPosition())).get("mainCategoryImage"))
                                .placeholder(R.drawable.placeholder11).into(holder.image);
                      holder.tvCatName.setText(arrayListMain.get(holder.getAdapterPosition()).get("MainCategoryName"));
                } catch (Exception e) {
                      Log.e("Exception", e.toString());
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            AppSettings.dashboard = "1";
                            mainCatId = arrayListMain.get(holder.getAdapterPosition()).get("MainCategoryId");
                            mainCategoryId = arrayListMain.get(holder.getAdapterPosition()).get("MainCategoryId");
                            AppSettings.catname = arrayListMain.get(holder.getAdapterPosition()).get("MainCategoryName");

                            if (Utils.isNetworkConnectedMainThred(getActivity())) {
                                  loader.show();
                                  loader.setCancelable(false);
                                  loader.setCanceledOnTouchOutside(true);

                                  HitProducts cat = new HitProducts();
                                  cat.execute();
                            } else {
                                  Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                            }
                      }
                });
          }


        public int getItemCount() {
            return arrayListMain.size();
            // return 3;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }








    private class FavNameQuantity extends RecyclerView.ViewHolder {

        TextView tvQuantity;
        TextView tvMRP;
        TextView tvSellingPrice;
        RelativeLayout relativelayout;


        public FavNameQuantity(View itemView) {
            super(itemView);

            tvMRP = itemView.findViewById(R.id.tvMRP);
            tvSellingPrice = itemView.findViewById(R.id.tvSellingPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            relativelayout = itemView.findViewById(R.id.relativelayout);


        }
    }





    private class QuantityAdapter extends RecyclerView.Adapter<FavNameQuantity> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String productposition;
        String value;

        public QuantityAdapter(ArrayList<HashMap<String, String>> favList, String productposition, String value) {
            this.data = favList;
            this.productposition = productposition;
            this.value = value;
        }


        public FavNameQuantity onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavNameQuantity(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_quantity, parent, false));
        }




         @Override
          public void onBindViewHolder(@NonNull final FavNameQuantity holder, final int position) {

                holder.tvQuantity.setText(data.get(holder.getAdapterPosition()).get("Particular"));
                holder.tvMRP.setText("\u20B9" + data.get(holder.getAdapterPosition()).get("Regularprice"));
                holder.tvSellingPrice.setText("\u20B9" + data.get(holder.getAdapterPosition()).get("Sellingprice"));
                holder.tvMRP.setPaintFlags(holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                if (value.equals(data.get(holder.getAdapterPosition()).get("Particular"))) {
                      holder.relativelayout.setBackgroundColor(getResources().getColor(R.color.grey_800));
                      holder.tvQuantity.setTextColor(getResources().getColor(R.color.white));
                      holder.tvMRP.setTextColor(getResources().getColor(R.color.white));
                      holder.tvSellingPrice.setTextColor(getResources().getColor(R.color.white));
                } else {
                      holder.relativelayout.setBackgroundColor(getResources().getColor(R.color.white));
                      holder.tvQuantity.setTextColor(getResources().getColor(R.color.black));
                      holder.tvMRP.setTextColor(getResources().getColor(R.color.grey_500));
                      holder.tvSellingPrice.setTextColor(getResources().getColor(R.color.grey_700));
                }

                holder.relativelayout.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            recentArray.get(Integer.parseInt(productposition)).put("quantity", data.get(holder.getAdapterPosition()).get("Particular"));
                            recentArray.get(Integer.parseInt(productposition)).put("MRP", data.get(holder.getAdapterPosition()).get("Regularprice"));
                            recentArray.get(Integer.parseInt(productposition)).put("SellingPrice", data.get(holder.getAdapterPosition()).get("Sellingprice"));
                            recentArray.get(Integer.parseInt(productposition)).put("RateId", data.get(holder.getAdapterPosition()).get("ID"));

                            Log.v("jfhjfhjfhjf1", data.get(holder.getAdapterPosition()).get("ID"));
                            recentProductAdapterNew.notifyItemChanged(Integer.parseInt(productposition));
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
            View row = inflater.inflate(R.layout.spinner_district, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            //label.setTypeface(typeface3);
            label.setText(list.get(position).get("name"));
            return row;
        }
    }




    public class SpinnerQuantityAdapter extends ArrayAdapter<HashMap<String, String>> {

        ArrayList<HashMap<String, String>> quantitylist;

        public SpinnerQuantityAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> list) {

            super(context, textViewResourceId, list);

            this.quantitylist = list;

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
            label.setText(quantitylist.get(position).get("Particular"));
            return row;
        }
    }








    //***********************************API****************************************************//
    public class HitMainCat extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetCat("GetMainCategory");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    arrayListMain.clear();
                    jsonmaincategory = new JSONObject(displayText);
                    if (jsonmaincategory.has("Response")) {
                        JSONArray jsonResponse = jsonmaincategory.getJSONArray("Response");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject object = jsonResponse.getJSONObject(i);
                            String completeUrl = WebService.imageURL + object.getString("mainCategoryImage");

                            Log.e("com", completeUrl);
                            map.put("MainCategoryName", object.getString("MainCategoryName"));
                            map.put("MainCategoryId", object.getString("MainCategoryId"));
                            map.put("mainCategoryImage", completeUrl);
                            arrayListMain.add(map);
                        }


                        categoriesRecyclerView.setAdapter(new ProductAdapter(arrayListMain));

                    } else {
                        container_categories.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    container_categories.setVisibility(View.GONE);
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

    public class HitDistrictList extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetCat("GetDistrictList");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    cityList.clear();
                    jsondistrict = new JSONObject(displayText);
                    if (jsondistrict.has("Responce")) {
                        JSONArray jsonResponse = jsondistrict.getJSONArray("Responce");

                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject object = jsonResponse.getJSONObject(i);


                            map.put("id", object.getString("DistrictCode"));
                            map.put("name", object.getString("DistrictName"));

                            cityList.add(map);
                        }
                        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout, cityList);
                        adapter.notifyDataSetChanged();
                        spinnerDistrict.setAdapter(adapter);
                        for (int i = 0; i < cityList.size(); i++) {
                            if (pref.get(AppSettings.DistrictName) != null) {
                                if (pref.get(AppSettings.DistrictName).equals(cityList.get(i).get("name"))) {
                                    spinnerDistrict.setSelection(i);
                                    pref.set(AppSettings.District, cityList.get(i).get("id"));
                                    pref.set(AppSettings.DistrictName, cityList.get(i).get("name"));
                                    pref.commit();
                                    break;
                                }
                            } else {
                                pref.set(AppSettings.District, cityList.get(0).get("id"));
                                pref.set(AppSettings.DistrictName, cityList.get(0).get("name"));
                                pref.commit();
                                break;
                            }
                        }


                        HitRecentlyAddedProduct product = new HitRecentlyAddedProduct();
                        product.execute();

                        //HitDealOfDayProduct deal = new HitDealOfDayProduct();
                        // deal.execute();

                        new HitBanner().execute();

                        //   loader.dismiss();
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

    public class HitDeleteCart extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.deleteTocart(pref.get(AppSettings.CustomerID), CartListID, "1", "", "", "", rate_id, "DeleteCart");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("deleteTocart", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject ob = jsonObject.getJSONObject("Response");

                    if (ob.getString("Status").equalsIgnoreCase("True")) {
                        Toasty.success(getActivity(), "Product Removed", Toast.LENGTH_SHORT).show();
                        new HitGetCart().execute();
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

    public class HitUpdateQuantity extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.UpdateQuantity(pref.get(AppSettings.CustomerID), ProductId, quantity, rate_id, "addToCart");
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
                        Toasty.success(getActivity(), "Quantity Updated", 1000).show();

                        new HitGetCart().execute();
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




    public class HitAddTocart extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            // displayText = WebService.GetCategory("-1", "GetCategory");
            Log.e("addTocartdashboard", pref.get(AppSettings.CustomerID) + "  ProductId " + ProductId + "   " + "1" + "   varId   " + varId + "  attrid  " + attrid + "   " + "" + "   " + rate_id + "   " + "addToCart");
            displayText = WebService.addTocart(pref.get(AppSettings.CustomerID), ProductId, "1", varId, attrid, "", rate_id, "", "", "addToCart");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    Toasty.success(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
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

            Log.e("GetCartList", displayText);

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
                        map.put("ProductID", ProdID);
                        map.put("Quantity", Quantity);
                        // map.put("RateId", RateId);

                        cartlist.add(map);
                        Log.e("ProdID", ProdID);


                    }
                    pref.set(AppSettings.count, "" + /*jsonArray.length()*/cartlist.size());
                    pref.commit();
                    DrawerActivity.tvCount.setText(pref.get(AppSettings.count));
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
            displayText = WebService.HitWalletPoint(pref.get(AppSettings.CustomerID), "WalletPoint");
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
                        pref.set(AppSettings.walletpoint, "" + /*jsonArray.length()*/WalletPoint);
                        pref.commit();
                        DrawerActivity.tv_points.setText("\u20B9 " + pref.get(AppSettings.walletpoint));
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

            displayText = WebService.GetPincode(etPincode.getText().toString(), "checkPinCode");

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
                        tvDeliveryAvailable.setVisibility(View.GONE);
                        tvDeliveryNotAvailable.setVisibility(View.VISIBLE);
                        Toasty.error(getActivity(), "Delivery is not available on this pincode").show();
                        dialog.dismiss();
                    } else {
                        tvDeliveryAvailable.setVisibility(View.VISIBLE);
                        tvDeliveryNotAvailable.setVisibility(View.GONE);
                        Toasty.success(getActivity(), "Delivery is  available on this pincode").show();
                        dialog.dismiss();
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




    public class HitBanner extends AsyncTask<String, Void, Void> {
        String displayText;

        @Override
        protected Void doInBackground(String... params) {
//            displayText = WebService.GetBanner(pref.get(AppSettings.District), "getBanner");
            displayText = WebService.GetBanner("abc", "getBanner");
            Log.e("getBannerResponse", displayText + pref.get(AppSettings.District).toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    arrayBannerMain.clear();
                    jsonbanner = new JSONObject(displayText);
//                    Log.e("get_BannerOb", " " + jsonbanner);
                    JSONArray jsonArray = jsonbanner.getJSONArray("getBannerResponse");
//                    Log.e("get_BannerArr", " " + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        arrayBannerMain.add(obj.getString("BannerImage"));
//                        Log.e("get_Banner", " : " + arrayBannerMain + " / " + WebService.imageURL + arrayBannerMain);
                    }
                    SliderAdapter adapter = new SliderAdapter(getActivity());
                    adapter.addItem(arrayBannerMain);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.startAutoCycle();
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :

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

    public class HitBannerBottom extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.getBannerbootom(pref.get(AppSettings.District), "getBannerbootom");
            Log.e("fhfjgfjgfjgff", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }


            currentPage = 0;
            currentPage1 = 0;
            DELAY_MS = 1000;
            DELAY_MS1 = 1000;
            PERIOD_MS = 3000;
            PERIOD_MS1 = 3000;

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    arrayBanner.clear();
                    arrayBannerbelow.clear();

                    jsonbanner = new JSONObject(displayText);
                    if (jsonbanner.has("getBannerResponsebot")) {
                        JSONArray jsonResponse = jsonbanner.getJSONArray("getBannerResponsebot");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();

                            JSONObject object = jsonResponse.getJSONObject(i);
                            String completeUrl = WebService.imageURL + object.getString("BannerImages");
                            map.put("Bannertitle", object.getString("Bannertitle"));
                            map.put("BannerImage", completeUrl);
                            map.put("DiscountType", object.getString("DiscountType"));
                            map.put("DiscountValue", object.getString("DiscountValue"));
                            map.put("ProductCategory", object.getString("ProductCategory"));


                            arrayBannerbelow.add(map);
                            Log.v("jkghjhgjhg", String.valueOf(arrayBannerbelow.size()));
                        }

                        if (arrayBannerbelow.isEmpty()) {
                            ivBanner.setVisibility(View.VISIBLE);
                            framelayout.setVisibility(View.GONE);
                        } else {
                            ivBanner.setVisibility(View.GONE);
                            framelayout.setVisibility(View.VISIBLE);
                        }


                        CardPagerAdapterBottom mCardAdapterbelow = new CardPagerAdapterBottom(getActivity(), arrayBannerbelow) {
                            @Override
                            protected void onCategoryClick(View view, String str) {


                            }
                        };
                        viewpagerDown.setAdapter(mCardAdapterbelow);
                        mCardAdapterbelow.notifyDataSetChanged();
                        viewpagerDown.setOffscreenPageLimit(2);
                        viewpagerDown.setClipToPadding(false);
                        viewpagerDown.setCurrentItem(0, true);
                        viewpagerDown.setPageMargin(10);
                        //Set view pager here
                        viewpagerDown.setVisibility(View.VISIBLE);


                        final int NUM_PAGES1 = arrayBannerbelow.size();

                        final Handler handler1 = new Handler();


                        final Runnable Update1 = new Runnable() {
                            public void run() {
                                if (currentPage1 == NUM_PAGES1) {
                                    currentPage1 = 0;
                                }

                                viewpagerDown.setCurrentItem(currentPage1++, true);
                            }
                        };


                        timer1 = new Timer(); // This will create a new Thread

                        timer1.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler1.post(Update1);
                            }
                        }, DELAY_MS1, PERIOD_MS1);


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

    public class HitCategory extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            //displayText = WebService.Getcategory(mainCatId, "getCategory");
            // displayText = WebService.Getcategory(mainCatId, "getsubCategory");
            displayText = WebService.GetSubcat(mainCatId, "getsubCategory");

            Log.e("disssssss123", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            AppSettings.status = "1";
            replaceFragmentWithAnimation(new ProductSubcategoryFragment());
            // replaceFragmentWithAnimation(new ProductListingFragment());


            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsoncategory = new JSONObject(displayText);
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

            Log.e("GetProductDetail", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonObject = new JSONObject(displayText);
                    jsonProductlist = new JSONObject(displayText);
                    if (jsonObject.has("Status")) {
                        if (jsonObject.getBoolean("Status")) {
                            loader.dismiss();
                            AppSettings.fromPage = "3";

                            AppSettings.dashboard = "1";
                            replaceFragmentWithAnimation(new ProductDescriptionFragment());
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

    public class HitRecentlyAddedProduct extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.RecentlyAddedProduct("60", pref.get(AppSettings.District), "LastWeekAddedProduct");

            Log.e("disssssss123456", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            AppSettings.status = "1";


            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonrecentlproduct = new JSONObject(displayText);
                    recentArray.clear();
                    JSONArray jsonArray = jsonrecentlproduct.getJSONArray("getProductResponse");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);


                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductId", object.getString("ProductId"));
                        // Log.e("jsgggg", "" + object.getString("ProductId"));
                        map.put("ProductTitle", object.getString("ProductTitle"));
                        map.put("MainPicture", object.getString("MainPicture"));
                        map.put("MRP", object.getString("MRP"));
                        map.put("SellingPrice", object.getString("SellingPrice"));
                        map.put("Status", object.getString("Status"));
                        map.put("minQuantity", object.getString("minQuantity"));
                        map.put("RateId", object.getString("RateId"));

                        if (object.getJSONArray("WeekendAreaWiseRate").length() == 0) {
                            map.put("WeekendAreaWiseRate", "");
                            map.put("quantity", "");
                        } else {
                            map.put("WeekendAreaWiseRate", object.getJSONArray("WeekendAreaWiseRate").toString());
                            map.put("quantity", object.getJSONArray("WeekendAreaWiseRate").getJSONObject(0).getString("Particular"));
                        }

                        recentArray.add(map);


                    }

                    // Collections.reverse(recentArray);
                    recentProductAdapterNew = new RecentProductAdapterNew(recentArray);
//                    productRecyclerView.setAdapter(recentProductAdapterNew);

                } catch (Exception e) {
                    e.printStackTrace();
                    container_recently_added.setVisibility(View.GONE);
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

    public class HitRecentlyAddedProducNew extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.LastWeekAddedProductNew("60", pref.get(AppSettings.District), "LastWeekAddedProductNew");

            Log.e("LastWeekAddedProductNewReq ", "" + displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            AppSettings.status = "10";


            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonrecentlproduct = new JSONObject(displayText);
                    recentArray.clear();
                    JSONArray jsonArray = jsonrecentlproduct.getJSONArray("proitemnew");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductCategory", object.getString("ProductCategory"));
                        map.put("ProductCode", object.getString("ProductCode"));
                        // map.put("CartListID", object.getString("CartListID"));

                        map.put("ProductName", object.getString("ProductName"));
                        map.put("ProductMainImageUrl", object.getString("ProductMainImageUrl"));
                        map.put("MainCategoryCode", object.getString("MainCategoryCode"));
                        map.put("pName", object.getString("pName"));
                        map.put("UnitName", object.getString("UnitName"));
                        map.put("SalePrice", object.getString("SalePrice"));
                        map.put("AttrId", object.getString("AttrId"));
                        map.put("VarId", object.getString("VarId"));
                        map.put("RegularPrice", object.getString("RegularPrice"));
                        map.put("SaveAmount", object.getString("SaveAmount"));
//                        map.put("AttrId", object.getString("AttrId"));
//                        map.put("VarId", object.getString("VarId"));


                        recentArray.add(map);


                    }

                    // Collections.reverse(recentArray);
                    recentProductAdapterNew = new RecentProductAdapterNew(recentArray);
//                    productRecyclerView.setAdapter(recentProductAdapterNew);
//                    productRecyclerView.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    Log.e("ex", " " + e.getMessage());
                    e.printStackTrace();
                    container_recently_added.setVisibility(View.GONE);
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

    public class HitDealOfDayProduct extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.DealofDayProduct(pref.get(AppSettings.District), "DealsOftheDaysProduct");

            Log.e("disssssss123", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            dealArray.clear();
            AppSettings.status = "1";


            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonrecentlproduct = new JSONObject(displayText);
                    JSONArray jsonArray = jsonrecentlproduct.getJSONArray("getProductResponse");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);


                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductId", object.getString("ProductId"));
                        // Log.e("jsgggg", "" + object.getString("ProductId"));
                        map.put("ProductTitle", object.getString("ProductTitle"));
                        map.put("MainPicture", object.getString("MainPicture"));
                        map.put("MRP", object.getString("MRP"));
                        map.put("SellingPrice", object.getString("SellingPrice"));
                        map.put("Status", object.getString("Status"));
                        map.put("minQuantity", object.getString("minQuantity"));
                        map.put("RateId", object.getString("RateId"));

                        if (object.getJSONArray("WeekendAreaWiseRate").length() == 0) {
                            map.put("WeekendAreaWiseRate", "");
                        } else {
                            map.put("WeekendAreaWiseRate", object.getJSONArray("WeekendAreaWiseRate").toString());
                        }

                        dealArray.add(map);


                    }

                    Collections.reverse(dealArray);
                    recViewDeals.setAdapter(new DealProductAdapter(dealArray));

                    if (dealArray.isEmpty()) {
                        llDealOfDay.setVisibility(View.GONE);
                    } else {
                        llDealOfDay.setVisibility(View.VISIBLE);
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

    public class HitProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            Log.v("mainCatId", mainCatId);

            //displayText = WebService.GetProduct(product_name12,"1",pref.get(AppSettings.District),"","GetProductNew");
            displayText = WebService.GetProductNew(mainCatId, "GetProductbyCategoryId");
            Log.e("GetProductNew", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    loader.dismiss();
                    // arrayList.clear();
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.getBoolean("Status")) {
                        if (jsonObject.has("proitemnew")) {
                            jsonResponse = jsonObject.getJSONArray("proitemnew");
                            AppSettings.productFrom = "4";
                            AppSettings.dashboard = "1";
                            replaceFragmentWithAnimation(new ProductListingFragment());

                            loader.dismiss();

                        }
                    } else {
                        loader.dismiss();
                        Toast.makeText(getActivity(), "No item found !!!", Toast.LENGTH_LONG).show();
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

    public class HitSearch extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            Log.v("GetProductSearch", "" + DrawerActivity.et_search.getText().toString());

            displayText = WebService.GetProductSearch(DrawerActivity.et_search.getText().toString(), "GetProductSearch");
            Log.v("GetProductSearch", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    loader.dismiss();
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.getBoolean("Status")) {
                        if (jsonObject.has("proitemnew")) {
                            jsonResponse = jsonObject.getJSONArray("proitemnew");
                            AppSettings.productFrom = "4";
                            AppSettings.dashboard = "1";
                            replaceFragmentWithAnimation(new ProductListingFragment());

                            loader.dismiss();

                        }
                    } else {
                        loader.dismiss();
                        Toast.makeText(getActivity(), "No item found !!!", Toast.LENGTH_LONG).show();
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

    private class OrderHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        ImageView productImage;
        TextView tvProductName;
        TextView tvOrderDate;
        TextView tvCancel;
        TextView tvReturn;
        TextView tvRemark;
        TextView tvTrack;
        TextView tvOrderId, tvDeliveryCharges, tvPaymentMode, tvNetPayable, tvDeliveryStatus;

        public OrderHolder(View itemView) {
            super(itemView);
            llMain = itemView.findViewById(R.id.llMain);
            productImage = itemView.findViewById(R.id.productImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvCancel = itemView.findViewById(R.id.tvCancel);
            tvReturn = itemView.findViewById(R.id.tvReturn);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvTrack = itemView.findViewById(R.id.tvTrack);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvDeliveryCharges = itemView.findViewById(R.id.tvDeliveryCharges);
            tvPaymentMode = itemView.findViewById(R.id.tvPaymentMode);
            tvNetPayable = itemView.findViewById(R.id.tvNetPayable);
            tvDeliveryStatus = itemView.findViewById(R.id.tvDeliveryStatus);


        }
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public OrderAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public OrderAdapter() {
        }

        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items, parent, false));
            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
        }

          @Override
          public void onBindViewHolder(@NonNull final OrderHolder holder, final int position) {

                holder.tvOrderId.setText(" " + data.get(holder.getAdapterPosition()).get("OrderId"));
                holder.tvDeliveryCharges.setText("" + data.get(holder.getAdapterPosition()).get("DeliveryCharges"));
                holder.tvPaymentMode.setText("" + data.get(holder.getAdapterPosition()).get("PaymentMode"));
                holder.tvNetPayable.setText("\u20b9 " + data.get(holder.getAdapterPosition()).get("NetPayable"));

                String deliveryStatus = data.get(holder.getAdapterPosition()).get("DeliveryStatus");
                holder.tvDeliveryStatus.setText(deliveryStatus);
                holder.tvCancel.setVisibility((deliveryStatus.equals("Delivered") || deliveryStatus.equals("cancelled")) ? View.GONE : View.VISIBLE);
                holder.tvDeliveryStatus.setTextColor((deliveryStatus.equals("cancelled")) ? getActivity().getResources().getColor(R.color.red_500) : getActivity().getResources().getColor(android.R.color.primary_text_dark));

                holder.tvOrderDate.setText("Ordered on : " + data.get(holder.getAdapterPosition()).get("OrderDate"));

                holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            OrderId = data.get(holder.getAdapterPosition()).get("OrderId");
                            ProductCode = data.get(holder.getAdapterPosition()).get("ProductCode");
                            CancelPopup();
                      }
                });

                holder.tvReturn.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            // Handle return action
                            // Define and initialize variables here

                            // Proceed with return action
                            ReturnPopup("" + projsonArray);
                      }
                });

                holder.tvTrack.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            OrderId = data.get(holder.getAdapterPosition()).get("OrderId");
                            orderDate = data.get(holder.getAdapterPosition()).get("OrderDate");
                            TotalPrice = data.get(holder.getAdapterPosition()).get("NetPayable");
                            status = data.get(holder.getAdapterPosition()).get("DeliveryStatus");
                            PaymentMode = data.get(holder.getAdapterPosition()).get("PaymentMode");
                            pref.set(AppSettings.from, "dashboard");
                            pref.commit();
                            replaceFragmentWithAnimation(new OrderDetailsFragment());
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

        public void updateList(ArrayList<HashMap<String, String>> list) {
            arrayList = list;
            notifyDataSetChanged();
        }
    }

    public class HitReturnOrder extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.ReturnOrder(OrderId, params[0], etComment.getText().toString(), reasonList.get(spinnerReason.getSelectedItemPosition()).get("ReasonId"), pref.get(AppSettings.CustomerID), "ReturnCustomerOrder");
            Log.e("ReturnOrderReq", OrderId + "  " + params[0] + "  " + etComment.getText().toString() + "  " + reasonList.get(spinnerReason.getSelectedItemPosition()).get("ReasonId") + "  " + pref.get(AppSettings.CustomerID));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("ReturnOrderReq", displayText);
            Log.e("displayyyyy1234", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    Log.e("listing", "" + jsonObject);
//{"Status":true,"msg":"Success","OrderId":"ORD101000031"}
                    //JSONObject object = jsonObject.getBoolean("Response");

                    if (jsonObject.getBoolean("Status")) {
                        Intent i = new Intent(getActivity(), DrawerActivity.class);
                        i.putExtra("page", "order");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                        Toasty.success(getActivity(), "Requested Return Successfully", Toast.LENGTH_SHORT).show();
                    } else if (jsonObject.getString("Status").equalsIgnoreCase("ELECTRONICS")) {
                        Toasty.error(getActivity(), "You can't return electronics items", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(getActivity(), jsonObject.getBoolean("msg") + " ", Toast.LENGTH_SHORT).show();
                    }
                    // JSONArray jsonArray = jsonObject.getJSONArray("getOrderList");

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

    public class HitCancelOrder extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.CancelOrder(OrderId, cancelReason, "CancelOrder");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy1234", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    //  JSONObject object = jsonObject.getJSONObject("Response");

                    if (jsonObject.getBoolean("Status")) {

                        Intent i = new Intent(getActivity(), DrawerActivity.class);
                        i.putExtra("page", "order");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                        Toasty.success(getActivity(), "Order Cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(getActivity(), "You can't cancel this order", Toast.LENGTH_SHORT).show();
                    }
                    // JSONArray jsonArray = jsonObject.getJSONArray("getOrderList");

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


