package claco.store.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import claco.store.DataModel.Product;
import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


public class ConfirmAddressActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    public static String OrderId;
    public static String Email;
    public static String selectedAddressId;
    public static String Paymode;
    public static TextView tvCouponAmt;
    public String PaymentMode = "COD";
    RecyclerView recyclerview;
    GridLayoutManager mGridLayoutManager;
    TextView tvHeaderText;
    TextView tvProceed;
    TextView tvPrice;
    TextView tvPayable;
    TextView tvTotalAmount;
    TextView tvGstAmount;
    TextView tvSave;
    TextView tvCartItemCount;
    TextView tvItemCount;
    TextView tvName;
    TextView tvAddress;
    TextView tvPhoneNumber;
    TextView tvChange;
    TextView tvGST;
    TextView tvDelCharges;
    TextView tvTimeslot;
    TextView tv_forgot;

    ImageView iv_menu;
    ImageView ivCash;
    ImageView ivOnline;

    View view;

    RelativeLayout rrCod;
    RelativeLayout rrOnline;
    RelativeLayout rrApply;
    RelativeLayout rrCoupon;

    Preferences pref;
    CustomLoader loader;
    double del_charge = 0.0;
    double finalAmount = 0.0;
    String order_id;
    String PaymentStatus;
    String PaymentOrderId;
    String PaymentId;
    String Entity;
    String CreatedAt;
    String Amount;
    String Method;
    String Amount_Refund;
    String Refund_Status;
    String CardId;
    String Bank;
    String Wallet;
    String ContactNo;
    String payment_method;
    String isCouponApplied = "0";

    Object objCollection, objCollection2;
    JSONArray projsonArray = new JSONArray();
    JSONArray freeJsonArray = new JSONArray();
    String GrossPayable;
    String DeliveryTo;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> freeItemList2 = new ArrayList<>();
    ArrayList<Product> productArrList = new ArrayList<>();
    ArrayList<FreeProduct> freeItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address);
        Checkout.preload(getApplicationContext());
        recyclerview = findViewById(R.id.recyclerview);
        tvHeaderText = findViewById(R.id.tvHeaderText);
        tvDelCharges = findViewById(R.id.tvDelCharges);
        tv_forgot = findViewById(R.id.tv_forgot);

        rrCod = findViewById(R.id.rrCod);
        rrOnline = findViewById(R.id.rrOnline);

        ivCash = findViewById(R.id.imageView8);
        ivOnline = findViewById(R.id.imageView9);
        tvTimeslot = findViewById(R.id.tvTimeslot);
        tvProceed = findViewById(R.id.tvProceed);
        tvCouponAmt = findViewById(R.id.tvCouponAmt);
        tvPrice = findViewById(R.id.tvPrice);
        tvName = findViewById(R.id.tvName);
        rrApply = findViewById(R.id.rrApply);
        rrCoupon = findViewById(R.id.rrCoupon);
        tvPayable = findViewById(R.id.tvPayable);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvGstAmount = findViewById(R.id.tvGstAmount);
        tvSave = findViewById(R.id.tvSave);
        view = findViewById(R.id.view);
        tvChange = findViewById(R.id.tvChange);
        tvGST = findViewById(R.id.tvGST);
        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmAddressActivity.this, MyCartActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });
        tvCartItemCount = findViewById(R.id.tvCartItemCount);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        //Custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref = new Preferences(this);
        tvHeaderText.setText("Delivery");
        tvTimeslot.setText(ChooseDeliverySpeed.current_date + " " + ChooseDeliverySpeed.time_slot);

        mGridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(mGridLayoutManager);
        recyclerview.setAdapter(new ProductAdapter());
        tvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvName.getText().toString().equalsIgnoreCase("Select Address")) {
                    Toasty.error(ConfirmAddressActivity.this, "Please Select Address", Toast.LENGTH_SHORT, true).show();
                } else {
                    if (Utils.isNetworkConnectedMainThred(ConfirmAddressActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        HitCheckout cart = new HitCheckout();
                        cart.execute();
                    }


                }


            }
        });


        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmAddressActivity.this, SelectAddressPage.class));
            }
        });


        rrApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmAddressActivity.this, PromoCodeActivity.class);
                intent.putExtra("Orderamount", String.valueOf(GrossPayable));
                startActivity(intent);
            }
        });
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmAddressActivity.this, ForgotPassword.class));

            }
        });


        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            HitGetCart cart = new HitGetCart();
            cart.execute();
            HitGetAddress address = new HitGetAddress();
            address.execute();
        } else {
            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }


        if (AppSettings.amount.equalsIgnoreCase("")) {
            rrCoupon.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        } else {
            rrCoupon.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            tvCouponAmt.setText(" - \u20b9 " + AppSettings.amount);
            Log.e("coupCode", ""+AppSettings.amount);

            isCouponApplied = "1";


        }

        //Listner on Payment Mode

        rrCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PaymentMode.equalsIgnoreCase("CARD")) {
                    ivOnline.setImageResource(R.drawable.ic_radio_button_unchecked);
                    ivCash.setImageResource(R.drawable.ic_radio_button_checked);
                    PaymentMode = "COD";
                    PaymentMode = "COD";
                }


            }
        });


        rrOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PaymentMode.equalsIgnoreCase("COD")) {
                    ivOnline.setImageResource(R.drawable.ic_radio_button_checked);
                    ivCash.setImageResource(R.drawable.ic_radio_button_unchecked);
                    PaymentMode = "CARD";
                }

            }
        });

    }

    public void startPayment() throws RazorpayException {

        final Activity activity = this;
        final Checkout co = new Checkout();

        String amt = tvTotalAmount.getText().toString().replace("\u20b9", "").trim();
        Double amount = Double.parseDouble(amt) * 100;
        //   Double amount = 100.0;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Claco");
            options.put("description", "Online Payment");
            int image = R.drawable.logo;
            options.put("currency", "INR");
            //options.put("amount", amount);
            options.put("amount", amount);
            options.put("order_id", order_id);
            JSONObject preFill = new JSONObject();
            preFill.put("email", pref.get(AppSettings.Email));
            preFill.put("contact", tvPhoneNumber.getText().toString());
            options.put("prefill", preFill);

            Log.e("Razorpayexception1", preFill.toString());

            co.setImage(image);
            co.open(activity, options);
            co.setFullScreenDisable(true);

        } catch (Exception e) {

            Log.e("Razorpayexception", e.toString());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public void orderid() throws RazorpayException {

        String amt = tvTotalAmount.getText().toString().replace("\u20b9", "").trim();
        Double amount = Double.parseDouble(amt) * 100;
        // Double amount = 100.00;

        //RazorpayClient razorpay = new RazorpayClient("rzp_live_Tnhgrxogc6V58u", "SFIRmXeOjvjtV8HtJBk22Aep");
        RazorpayClient razorpay = new RazorpayClient("rzp_live_YLFVZ7b8lmrSEm", "wSVmR8YaCbAwbAChVs36aKSh");

        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "test_1");
            orderRequest.put("payment_capture", false);
            Order order = razorpay.Orders.create(orderRequest);
            JSONObject jsonObj = new JSONObject("" + order);
            order_id = jsonObj.getString("id");
            startPayment();
            Log.e("order11", "" + jsonObj);
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void SuccessPopup() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //  dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(R.color.blacktrans);
        dialog.show();

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();
                    pref.set(AppSettings.count, "0");
                    pref.commit();
                    Intent intent = new Intent(ConfirmAddressActivity.this, OrderConfirmationActivity.class);
                    startActivity(intent);
                    //  overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                    finishAffinity();
                }
            }
        };

        timerThread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MyCartActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData data) {


        try {
            //    Toast.makeText(this, "Payment Successful: " + data.getPaymentId(), Toast.LENGTH_SHORT).show();
            final String paymentId = data.getPaymentId();
            String signature = data.getSignature();
            String orderId = data.getOrderId();
            String contact = data.getUserContact();
            String email = data.getUserEmail();


            // SuccessPopup();

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        //Get all details
                        fetchPayment(paymentId);
                        //Your code goes here
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        Toasty.error(ConfirmAddressActivity.this, "Payment failed", Toast.LENGTH_LONG).show();


        Intent in = new Intent(ConfirmAddressActivity.this, DrawerActivity.class);
        in.putExtra("page", "Home");
        startActivity(in);
        finish();
    }

    public void fetchPayment(String payment_id) throws RazorpayException {


        RazorpayClient razorpay = new RazorpayClient("rzp_live_YLFVZ7b8lmrSEm", "wSVmR8YaCbAwbAChVs36aKSh");
        try {
            Payment payment = razorpay.Payments.fetch(payment_id);
            Log.e("payyyy", "" + payment);

            try {
                JSONObject object = new JSONObject("" + payment);
                PaymentStatus = object.getString("status");
                PaymentOrderId = object.getString("order_id");
                PaymentId = object.getString("id");
                Entity = object.getString("entity");
                CreatedAt = getDate(Long.parseLong(object.getString("created_at")));
                Amount = object.getString("amount");
                Method = object.getString("method");
                Amount_Refund = object.getString("amount_refunded");
                Refund_Status = object.getString("refund_status");
                CardId = object.getString("card_id");
                Bank = object.getString("bank");
                Wallet = object.getString("wallet");
                ContactNo = object.getString("contact");
                payment_method = object.getString("method");

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        if (Utils.isNetworkConnectedMainThred(ConfirmAddressActivity.this)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(true);
                            HitPayment payAPI = new HitPayment();
                            payAPI.execute();
                        } else {
                            Toasty.error(ConfirmAddressActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return date;
    }


    //=============================Adapter====================================================//
    private class FavNameHolder extends RecyclerView.ViewHolder {
        TextView tvOldPrice;
        TextView cart_item_number;
        TextView tvSave;
        ImageButton cart_quant_minus;
        ImageButton cart_quant_add;
        TextView tvProductName;
        TextView tvFinalprice;
        ImageView cart_item_delete;
        ImageView cart_item_image;

        TextView tvDelCharge;
        TextView tvSize;

        public FavNameHolder(View itemView) {
            super(itemView);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            cart_item_number = itemView.findViewById(R.id.cart_item_number);
            cart_quant_minus = itemView.findViewById(R.id.cart_quant_minus);
            cart_quant_add = itemView.findViewById(R.id.cart_quant_add);
            tvSave = itemView.findViewById(R.id.tvSave);
            tvDelCharge = itemView.findViewById(R.id.tvDelCharge);
            tvSize = itemView.findViewById(R.id.tvSize);

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

            holder.cart_quant_add.setVisibility(View.GONE);
            holder.cart_quant_minus.setVisibility(View.GONE);
            holder.cart_item_delete.setVisibility(View.GONE);
            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            //Setting Values
            // Glide.with(ConfirmAddressActivity.this).load((String) ((HashMap) data.get(position)).get("MainPicture")).centerCrop().placeholder(R.drawable.placeholder11).into(holder.cart_item_image);
            holder.tvOldPrice.setText(data.get(position).get("ProductTitle"));
            holder.cart_item_number.setText(data.get(position).get("Quantity"));
            holder.tvFinalprice.setText(" \u20b9 " + data.get(position).get("SalePrice"));

            Double finalretail = Double.parseDouble(data.get(position).get("SalePrice")) *
                    Integer.parseInt(data.get(position).get("Quantity"));
            holder.tvOldPrice.setText("\u20b9 " + String.valueOf(data.get(position).get("RegularPrice")));
            //  Double gst=Double.parseDouble(data.get(position).get("TotalPrice"))*percent;
            Log.e("RateId", "Quantity while setting" + (data.get(position).get("Quantity")));
            double saving = 0.0;
            if (Double.parseDouble(data.get(position).get("SalePrice")) > 0)
                saving = Double.parseDouble(data.get(position).get("RegularPrice")) - Double.parseDouble(data.get(position).get("SalePrice"));
            holder.tvSave.setText("Save \u20b9 " + String.valueOf(saving));

            ///  public string WalletBal { get; set; }

            holder.tvOldPrice.setText("\u20b9 " + String.valueOf(data.get(position).get("RegularPrice")));
            Glide.with(ConfirmAddressActivity.this).load(WebService.imageURL + data.get(position).get("MainPicture")).placeholder(R.drawable.placeholder11).into(holder.cart_item_image);


            holder.tvProductName.setText(data.get(position).get("ProductTitle"));

            holder.cart_item_number.setText(data.get(position).get("Quantity"));

            holder.cart_quant_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());
                    count++;
                    holder.cart_item_number.setText(String.valueOf(count));
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(100);
                    holder.cart_item_number.setText(String.valueOf(count));

                }
            });


            holder.cart_quant_minus.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(holder.cart_item_number.getText().toString());

                    if (count > 1) {
                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(100);
                        count--;

                    } else {
                        // ProductId = data.get(position).get("ProductID");
                        //Call remove api
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


    //=====================================API=========================================================//

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
            arrayList.clear();
            freeItemList2.clear();
            loader.dismiss();
            Log.e("GetCartList", displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getCartResponse");
                    tvCartItemCount.setText("CART ITEM( " + String.valueOf(jsonArray.length() + " )"));
                    tvItemCount.setText("(" + String.valueOf(jsonArray.length() + " items ") + ")");
                    Double sum = 0.0;
                    Double save = 0.0;
                    Double GSTSum = 0.0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String ProdID = obj.getString("ProductCode");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ProductID", obj.getString("ProductCode"));
                        map.put("ProductName", obj.getString("ProductName"));
                        map.put("SalePrice", obj.getString("SalePrice"));

                        map.put("Quantity", obj.getString("Quantity"));

                        map.put("Totalprice", obj.getString("Totalprice"));
                        map.put("RegularPrice", obj.getString("RegularPrice"));
                        map.put("CartListID", obj.getString("CartListID"));
                        map.put("varId", obj.getString("varId"));

                        map.put("sizename", obj.getString("sizename"));
                        map.put("colorname", obj.getString("colorname"));

                        String completeUrl = obj.getString("MainPicture");
                        completeUrl = completeUrl.replace(" ", "%20");
                        map.put("MainPicture", completeUrl);


                        productArrList.add(new Product(obj.getString("ProductCode"),
                                obj.getString("Quantity"),
                                obj.getString("SalePrice"),
                                String.valueOf(Double.parseDouble(obj.getString("SalePrice")) * Double.parseDouble(obj.getString("Quantity"))), "", obj.getString("varId")));


                        map.put("delivery", "" + roundTwoDecimals(del_charge));
                        arrayList.add(map);

                    }


                    GrossPayable = String.valueOf(jsonObject.get("todayPoAmt"));
                    Log.e("GetMRPWiseDeliveryCharges", GrossPayable+"");
                    tvPrice.setText("\u20b9  " + GrossPayable);
                    tvTotalAmount.setText("\u20b9  " + GrossPayable);
                    tvPayable.setText("\u20b9  " + GrossPayable);

                    tvGstAmount.setText(String.valueOf(jsonObject.get("gstamount")));
                    //   try {
                    if (!AppSettings.amount.equalsIgnoreCase("0")) {
                        String amount = String.valueOf((finalAmount) - Double.parseDouble(AppSettings.amount));
                        Log.v("amount", " " + amount);
                        tvTotalAmount.setText("\u20b9  " + amount);
                        tvPayable.setText("\u20b9 " + amount);
                    }

//                    } catch (Exception e) {
//                    }
                    recyclerview.setAdapter(new ProductAdapter(arrayList));

                    JSONArray jsonArray2 = jsonObject.getJSONArray("GetComboOfferresponse");
                    Log.e("freeItemList", "" + jsonArray2);

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject obj = jsonArray2.getJSONObject(i);
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("ProductId", obj.getString("ProductId"));
                        map2.put("varId", obj.getString("varId"));
                        map2.put("qty", obj.getString("qty"));
                        map2.put("ProductName", obj.getString("ProductName"));
                        map2.put("ProductImage", obj.getString("ProductImage"));
                        freeItemList2.add(map2);
                        Log.e("freeItemList", "" + freeItemList2);
                    }

                    new GetDeliveryCharges().execute("" + GrossPayable);
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

            Log.e("displayyyyy12", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
//
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("getAddressResponse");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        if (obj.getString("IsDefaultAccount").equalsIgnoreCase("Default")) {


                            if (obj.has("LanMark") && obj.getString("LanMark").equalsIgnoreCase("")) {
                                DeliveryTo = obj.getString("Locality") + "," + obj.getString("Address") + "," + obj.getString("StateId") + "," + obj.getString("CityId") + "," + obj.getString("PinCode");
                            } else {
                                DeliveryTo = obj.getString("Locality") + "," + obj.getString("Address") + "," + obj.getString("State_name") + "," + obj.getString("CityName") + "," + obj.getString("PinCode");

                            }

                            tvName.setText(obj.getString("Name"));
                            tvPhoneNumber.setText(obj.getString("MobileNo"));
                            tvAddress.setText(DeliveryTo);
                            selectedAddressId = obj.getString("SrNo");


                            Log.v("selectedAddressId", "  " + obj.getString("SrNo"));
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

    public class GetDeliveryCharges extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            Log.e("params", params[0]);
            displayText = WebService.GetMRPWiseDeliveryCharges(params[0], "getMRPWiseDeliveryCharges");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.v("GetMRPWiseDeliveryCharges", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
//
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.getBoolean("Status")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Delivecgarge");
                        JSONObject obj = jsonArray.getJSONObject(0);

                        tvDelCharges.setText("\u20b9 " + obj.getString("DeliveryCharges") != null ? "\u20b9 " + obj.getString("DeliveryCharges") : "\u20b9 " + "0.0");
                        Double deliveryCharges = Double.parseDouble(obj.getString("DeliveryCharges"));

                        finalAmount = Double.parseDouble(GrossPayable) + deliveryCharges;
                        Log.v("finalAmount", "" + finalAmount);
                        tvPayable.setText("\u20b9 " + finalAmount);
                        tvTotalAmount.setText("\u20b9 " + finalAmount);


                        if (!AppSettings.amount.equalsIgnoreCase("0")) {
                            String amount = String.valueOf((finalAmount) - Double.parseDouble(AppSettings.amount));
                            Log.v("amount", " " + amount);
                            tvTotalAmount.setText("\u20b9  " + amount);
                            tvPayable.setText("\u20b9 " + amount);
                        }
                    }


                } catch (Exception e) {
                    tvDelCharges.setText("\u20b9 " + "0.0");

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

    public class HitCheckout extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            Double subTotal = 0.0;
            Double finalTotal = 0.0;
            Double discountAmoun = 0.0;
            Double DeliveryCharge = 0.0;
            Double cpouponAmt = 0.0;
            for (int i = 0; i < arrayList.size(); i++) {
                final JSONObject obj1 = new JSONObject();
                try {
                    obj1.put("ItemCode", arrayList.get(i).get("ProductID"));
                    Double tot = (Double.parseDouble(arrayList.get(i).get("Quantity"))) * ((Double.parseDouble(arrayList.get(i).get("SalePrice"))));
                    obj1.put("Rate", arrayList.get(i).get("SalePrice"));
                    obj1.put("TotalAmount", tot);
                    obj1.put("Quantity", arrayList.get(i).get("Quantity"));
                    obj1.put("Reason", "");
                    obj1.put("ProductID", arrayList.get(i).get("varId"));
                    obj1.put("sizename", arrayList.get(i).get("sizename"));
                    obj1.put("colorname", arrayList.get(i).get("colorname"));

                    objCollection = obj1;
                    subTotal = subTotal + tot;
                    projsonArray.put(obj1);
                } catch (JSONException e) {
                    Toast.makeText(ConfirmAddressActivity.this, " ex" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                if (freeItemList2.size() > 0) {
                    final JSONObject obj2 = new JSONObject();
                    try {
                        obj2.put("ItemCode", freeItemList2.get(i).get("ProductId"));
                        obj2.put("qty", freeItemList2.get(i).get("qty"));
                        obj2.put("varIdL", freeItemList2.get(i).get("varId"));
                        objCollection2 = obj2;
                        freeJsonArray.put(obj2);
                        Log.e("obj", ""+obj2);
                    } catch (JSONException e) {
                        Toast.makeText(ConfirmAddressActivity.this, " ex" + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
            if (PaymentMode.equalsIgnoreCase("COD")) {
                Log.e("arr", ""+freeJsonArray);
                displayText = WebService.Checkout(pref.get(AppSettings.CustomerID), String.valueOf(subTotal), "CheckOut", tvDelCharges.getText().toString().replace("\u20b9", "").trim(), isCouponApplied, AppSettings.amount, tvCouponAmt.getText().toString().replace("\u20b9", "").replaceAll("-", "").trim(), selectedAddressId, "COD", "Success", tvPayable.getText().toString().replace("\u20b9", "").replaceAll("-", "").trim() /*String.valueOf(subTotal) */, "0", projsonArray + "", freeJsonArray+"");
                Log.e("req", pref.get(AppSettings.CustomerID) + ", " + String.valueOf(subTotal) + ", CheckOut" + "" + ", 0" + ", " + ",  , " + selectedAddressId + ", COD" + ", Success, " + String.valueOf(subTotal) + " ,    " + projsonArray + "");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.e("cvvvvvvvv", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.getBoolean("Status")) {

                        OrderId = jsonObject.getString("OrderId");
                        Paymode = "COD";
                        AppSettings.amount = "";
                        SuccessPopup();
                    } else {
                        Toasty.error(ConfirmAddressActivity.this, "Checkout failed !!", Toast.LENGTH_SHORT, true).show();
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


    public class HitPayment extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;


        @Override
        protected Void doInBackground(String... params) {
            // displayText = WebService.Payment(OrderId, Entity, ContactNo, CreatedAt, Wallet, Bank, Amount, CardId, Method, Amount_Refund, Refund_Status, PaymentStatus, PaymentOrderId, PaymentId, "Payment");

            Log.v("12345677", OrderId + " " + PaymentId + " " + CreatedAt + " " + PaymentOrderId + " " + Entity + " " + Amount + " " + "" + " " + "" + " " + CreatedAt + " " + Entity + " " + pref.get(AppSettings.CustomerID) + " " + CreatedAt + " " + "" + " " + CardId + " " + "" + " " + "" + " " + pref.get(AppSettings.Phone1) + " " + Entity);
            displayText = WebService.Payment(OrderId, PaymentId, CreatedAt, PaymentOrderId, "success", Amount, Amount, "0", CreatedAt, ChooseDeliverySpeed.deliveryId, pref.get(AppSettings.CustomerID), CreatedAt, "", CardId, "", "", pref.get(AppSettings.Phone1), ChooseDeliverySpeed.current_date, ChooseDeliverySpeed.time_slot, "CARD", "Payment");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.e("displayyyyy123333333", displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONObject jobject = jsonObject.getJSONObject("Response");

                    if (jobject.getString("Status").equalsIgnoreCase("True")) {
                        SuccessPopup();
                    } else {

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
