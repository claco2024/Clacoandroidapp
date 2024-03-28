package claco.store.Main;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import claco.store.Activities.HelpActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.Activities.Membership;
import claco.store.Activities.OfferFagment;
import claco.store.Activities.VendorRegistration;
import claco.store.Activities.MyAccountActivity;
import claco.store.Activities.MyCartActivity;
import claco.store.Activities.WishlistActivity;
import claco.store.BuildConfig;
import claco.store.ProductFragment.CategoriesFragment;
import claco.store.ProductFragment.OrderPlacedFragment;
import claco.store.ProductFragment.ProductDescriptionFragment;
import claco.store.ProductFragment.ProductListingFragment;
import claco.store.R;
import claco.store.fragments.DashboardFragment;
import claco.store.fragments.MyEnaamFragment;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
     public  static  LinearLayout ll_home_tab ,ll_category, ll_order,ll_offer;
    RelativeLayout rr_header;
    public static     RelativeLayout rl_search;
    public static ImageView iv_menu,ivHome,ivclose,ivfilter;
    public static   DrawerLayout drawer;
    public static LinearLayout llmain;
    public static  TextView tvCount;
    public static  TextView tv_points;
    public static TextView tvHeaderText;
    public static NavigationView navigationView;
    String status;
    public static JSONObject jsonObject;
    public static JSONObject jsonProductlist;
    Preferences pref;
    public static JSONObject jsonrecentlproduct;
    ImageView ivCart;
    List<String> arrayList= new ArrayList<>();
    ArrayList<HashMap<String,String>> arrayMap=new ArrayList<>();
    //other
    public static int backPressed = 0;

    RelativeLayout rlHome;
    RelativeLayout rlSearch;

    public  static EditText et_search;
    private ImageView homeMenuIcon, cartMenuIcon, orderMenuIcon, offersMenuIcon;
    private TextView homeMenuTxt, cartMenuTxt, orderMenuTxt, offersMenuTxt;

    CardView cd_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        llmain = findViewById(R.id.llmain);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvCount = findViewById(R.id.tvCount);
        ivHome = findViewById(R.id.ivHome);
        tv_points = findViewById(R.id.tv_points);
        View headerview = navigationView.getHeaderView(0);
        rlHome = headerview.findViewById(R.id.rlHome);
        rlHome.setOnClickListener(this);
        rlSearch = findViewById(R.id.rlSearch);
        rl_search  = findViewById(R.id.rl_search);
        ivfilter  = findViewById(R.id.ivfilter);
        rlSearch.setOnClickListener(this);
        ivclose = findViewById(R.id.ivclose);
        ivclose.setOnClickListener(this);
        ivfilter.setOnClickListener(this);
        ivCart = findViewById(R.id.ivCart);
        // bottom app bar
        homeMenuIcon = findViewById(R.id.home_menu_ico);
        homeMenuTxt = findViewById(R.id.home_menu_txt);

        cartMenuIcon = findViewById(R.id.cart_menu_ico);
        cartMenuTxt = findViewById(R.id.cart_menu_txt);

        orderMenuIcon = findViewById(R.id.order_menu_ico);
        orderMenuTxt = findViewById(R.id.order_menu_txt);

        offersMenuIcon = findViewById(R.id.offers_menu_ico);
        offersMenuTxt = findViewById(R.id.offers_menu_txt);

        pref = new Preferences(this);


        AppSettings.dashboard="0";

        Intent i = getIntent();

        et_search = findViewById(R.id.et_search);
        et_search.setOnClickListener(this);

        HitRecentlyAddedProducNew HitRecentlyAddedProducNew= new HitRecentlyAddedProducNew ();
        HitRecentlyAddedProducNew.execute();
        if (i.getStringExtra("page").equalsIgnoreCase("order")) {
            rl_search.setVisibility(View.GONE);

            replaceFragmentWithAnimation(new OrderPlacedFragment());
        } else if (i.getStringExtra("page").equalsIgnoreCase("cart")) {

            replaceFragmentWithAnimation(new ProductDescriptionFragment());
        } else if (i.getStringExtra("page").equalsIgnoreCase("wishlist")) {
            rl_search.setVisibility(View.GONE);
            replaceFragmentWithAnimation(new ProductDescriptionFragment());
        } else if (i.getStringExtra("page").equalsIgnoreCase("search")) {
            rl_search.setVisibility(View.GONE);
            replaceFragmentWithAnimation(new ProductListingFragment());
        } else if (i.getStringExtra("page").equalsIgnoreCase("account")) {
            rl_search.setVisibility(View.GONE);
            replaceFragmentWithAnimation(new OrderPlacedFragment());
        }
        else if (i.getStringExtra("page").equalsIgnoreCase("cat")) {
            rl_search.setVisibility(View.GONE);
            replaceFragmentWithAnimation(new CategoriesFragment());
        }
        else {
            rl_search.setVisibility(View.VISIBLE);
            replaceFragmentWithAnimation(new DashboardFragment());
        }
        ll_home_tab =  findViewById(R.id.ll_home_tab);
        ll_category =  findViewById(R.id.ll_category);
        cd_cart =  findViewById(R.id.cd_cart);
        ll_order =  findViewById(R.id.ll_order);
        ll_offer =  findViewById(R.id.ll_offer);
        rr_header = findViewById(R.id.rr_header);
        rr_header.setOnClickListener(this);
        tvHeaderText = findViewById(R.id.tvHeaderText);
        tvHeaderText.setText(getString(R.string.app_name));
        tvHeaderText.setTypeface(tvHeaderText.getTypeface(), Typeface.BOLD);
        iv_menu = findViewById(R.id.iv_menu);
        rl_search = findViewById(R.id.rl_search);
        iv_menu.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                status = "1";
                menu.findItem(R.id.my_account).setTitle("Login");
                menu.findItem(R.id.logout).setVisible(false);
            } else {
                status = "2";
                menu.findItem(R.id.my_account).setTitle("My Account");
                menu.findItem(R.id.logout).setVisible(true);
            }
            navigationView.setNavigationItemSelectedListener(this);
        }


        ll_home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//active
                homeMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_active), android.graphics.PorterDuff.Mode.SRC_IN);
                homeMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_active));

                //inactive
                orderMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_inactive), android.graphics.PorterDuff.Mode.SRC_IN);
                orderMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_inactive));

                offersMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_inactive), android.graphics.PorterDuff.Mode.SRC_IN);
                offersMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_inactive));
                replaceFragmentWithAnimation(new DashboardFragment());
            }
        });
        ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSettings.cartStatus="1";
                startActivity( new Intent( getApplicationContext(), MyCartActivity.class).putExtra("page","home"));

            }
        });
        cd_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSettings.cartStatus="1";
                startActivity( new Intent( getApplicationContext(), MyCartActivity.class).putExtra("page","home"));
            }
        });
        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //active
                orderMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_active), android.graphics.PorterDuff.Mode.SRC_IN);
                orderMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_active));

                //inactive
                homeMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_inactive), android.graphics.PorterDuff.Mode.SRC_IN);
                homeMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_inactive));

                offersMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_inactive), android.graphics.PorterDuff.Mode.SRC_IN);
                offersMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_inactive));
                replaceFragmentWithAnimation(new OrderPlacedFragment());
            }
        });
        ll_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offersMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_active), android.graphics.PorterDuff.Mode.SRC_IN);
                offersMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_active));

                //inactive
                homeMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_inactive), android.graphics.PorterDuff.Mode.SRC_IN);
                homeMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_inactive));

                orderMenuIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bottom_bar_inactive), android.graphics.PorterDuff.Mode.SRC_IN);
                orderMenuTxt.setTextColor(getResources().getColor(R.color.bottom_bar_inactive));
//                replaceFragmentWithAnimation(new OfferFagment());
                replaceFragmentWithAnimation(new OfferFagment());
            }
        });



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_item1) {
            Intent mIntent = new Intent(DrawerActivity.this, VendorRegistration.class);
            startActivity(mIntent);

        } else if (id == R.id.nav_item6) {
            replaceFragmentWithAnimation(new CategoriesFragment());

        } else if (id == R.id.my_orders) {
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                replaceFragmentWithAnimation(new OrderPlacedFragment());
            }

        } else if (id == R.id.my_enaam) {
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                replaceFragmentWithAnimation(new MyEnaamFragment());
            }

        } else if (id == R.id.my_account) {
            if (status.equals("1")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, MyAccountActivity.class));

            }

         //   overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
        } else if (id == R.id.MemberShip) {
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, Membership.class));
            }

         //   overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
        }
        else if (id == R.id.cancel_product) {
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                replaceFragmentWithAnimation(new OrderPlacedFragment());
            }
          //  overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
        }
        else if (id == R.id.return_product) {
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                replaceFragmentWithAnimation(new OrderPlacedFragment());
            }
            //  overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
        }


        else if (id == R.id.my_cart) {

            AppSettings.cartStatus = "1";
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                Intent i = new Intent(this, MyCartActivity.class);
                startActivity(i);
            }

         //   overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.my_wishlist) {
            if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {

                startActivity(new Intent(this, LoginActivity.class));
            } else {
                Intent i = new Intent(this, WishlistActivity.class);
                startActivity(i);
            }

        //    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }
        else if (id == R.id.logout) {

            logout();
        }


        else if (id==R.id.share)
        {
            sendReferral();
        }

        else if (id==R.id.contact)
        {
            startActivity(new Intent(this, HelpActivity.class));
        }


        else if(id==R.id.chat)
        {
            String smsNumber =   getResources().getString(R.string.whatsapp_number);
            boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
            if (isWhatsappInstalled) {

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

                startActivity(sendIntent);
            } else {
                Uri uri = Uri.parse("market://details?id=com.whatsapp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                Toast.makeText(this, "WhatsApp not Installed",
                        Toast.LENGTH_SHORT).show();
                startActivity(goToMarket);
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    public void sendReferral() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"&referrer="+pref.get(AppSettings.CustomerID)+" . I'm inviting you to use Claco, an app for purchasing products, clothes, jewellery, decorations.");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, " . I'm inviting you to use Claco, an app for purchasing products, clothes, jewellery, decorations.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,"Share link!"));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_menu:
                if (iv_menu.getTag().equals("arrow")) {
                    Log.e("gettagggg",iv_menu.getTag()+"");
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
                break;
 case R.id.ivclose:
                 et_search.setText("");
     replaceFragmentWithAnimation(new DashboardFragment());
                break;

            case R.id.ivCart:
                AppSettings.cartStatus = "3";
                Intent i = new Intent(DrawerActivity.this, MyCartActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
                break;

            case R.id.rlHome:
                i = new Intent(this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;

            case R.id.rlSearch:
               // i = new Intent(this, SearchActivity.class);
                //startActivity(i);
               // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public void logout() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertyesno);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);


        TextView tvYes = (TextView) dialog.findViewById(R.id.tvOk);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvcancel);
        TextView tvReason = (TextView) dialog.findViewById(R.id.textView22);
        TextView tvAlertMsg = (TextView) dialog.findViewById(R.id.tvAlertMsg);

        tvAlertMsg.setText("Confirmation Alert..!!!");
        tvReason.setText("Are you sure want to logout?");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        tvYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawerActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                pref.set(AppSettings.CustomerID, "");
                pref.commit();
                Toasty.success(DrawerActivity.this, "Logged out..!!!", Toast.LENGTH_SHORT).show();
                finishAffinity();
                dialog.dismiss();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(DrawerActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(5000, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    backPressed = 0;
                }
            }.start();
        }

        if (backPressed == 2) {
            backPressed = 0;

            finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    public class HitRecentlyAddedProducNew extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.LastWeekAddedProductNew("60",pref.get(AppSettings.District), "LastWeekAddedProductNew");

            Log.e("LastWeekAddedProductNew", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            AppSettings.status = "1";

            arrayList.clear();
            arrayMap.clear();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsonrecentlproduct = new JSONObject(displayText);

                    JSONArray jsonArray = jsonrecentlproduct.getJSONArray("proitemnew");
                    for (int i = 0; i <  jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductCode", object.getString("ProductCode"));
                        map.put("ProductName", object.getString("ProductName"));
                        map.put("MainCategoryCode", object.getString("MainCategoryCode"));
                        map.put("ProductCategory", object.getString("ProductCategory"));
                        map.put("status", "2");


                        arrayMap.add(map);
                        arrayList.add(object.getString("ProductName"));




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


    @Override
    protected void onResume() {
        super.onResume();

        if (!pref.get(AppSettings.count).isEmpty()) {
            tvCount.setText(pref.get(AppSettings.count));
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if (!pref.get(AppSettings.count).isEmpty()) {
            tvCount.setText(pref.get(AppSettings.count));
        }
    }

    public class HitProductDetail extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {


            displayText = WebService.GetProductDetail(params[1], params[3],params[0],params[2],"getProductdetail");

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
                        if (jsonObject.getBoolean("Status") ){

                            AppSettings.fromPage = "9";

                            AppSettings.dashboard="1";
                            replaceFragmentWithAnimation(new ProductDescriptionFragment());
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
}



