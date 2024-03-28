package claco.store.ProductFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.fragments.DashboardFragment;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;


public class OrderDetailsFragment extends Fragment {
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    //view
    View view, view1, view2, view3, view10;

    TextView tvOrderId;
    TextView tvOrderDate;
    TextView tvProductname;
    TextView tvOrderTotal;
    TextView tvOrderStatus;

    TextView tvName;
    TextView tvAddress;
    TextView tvPhone;

    String Status = "";

    ArrayList<HashMap<String, String>> reasonList = new ArrayList<>();
    Spinner spinnerReason;
    EditText etComment;
    //Imageview
    ImageView ivRecived;
    ImageView ivCancel;
    ImageView ivDelivered;
    ImageView iv_shipped;
    ImageView iv_packed;
    Preferences pref;
    TextView tvPaymentMode;
    TextView tvSize;
    TextView tvTimeslot;
    TextView tv_packed, inprocess;
    RecyclerView rvProduct;
    LinearLayout llSize;
    LinearLayout llTimeSlot;
    CustomLoader loader;
    RelativeLayout rlDelivered, rlOntheWay, RlCancel, rlProcess, rlinprocess, rlPacked;
    public static String OrderId;
    public static String Quantity;
    public static String orderDate;
    public static String ProductTitle;
    public static String TotalPrice;
    public static String DeliveryDate;
    public static String Address;
    public static String Name;
    public static String MobileNo1;
    public static String ProductCode;
    public static String status;
    public static String ProductId;
    public static String Url;
    public static String PaymentMode;
    public static String size;
    public static String DeliveryDatee;
    public static String timeslot;
    public static String tracking_status;
    LinearLayout free_item_container;
    boolean showFreeItem;
    ArrayList<HashMap<String, String>> freeItemArray = new ArrayList<>();
    RecyclerView freeProductRecyclerView;
    GridLayoutManager mGridLayoutManager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_details, container, false);

        DrawerActivity.tvHeaderText.setText("Order Details");

        DrawerActivity.ivHome.setVisibility(View.GONE);
        pref = new Preferences(getActivity());
        tvTimeslot = view.findViewById(R.id.tvTimeslot);
        tvOrderId = view.findViewById(R.id.tvOrderId);
        tvOrderDate = view.findViewById(R.id.tvOrderDate);
        tvProductname = view.findViewById(R.id.tvProductname);
        tvOrderTotal = view.findViewById(R.id.tvOrderTotal);
        tvOrderStatus = view.findViewById(R.id.tvOrderStatus);
        tvPaymentMode = view.findViewById(R.id.tvPaymentMode);
        tvSize = view.findViewById(R.id.tvSize);
        rvProduct = view.findViewById(R.id.rvProduct);

        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvPhone = view.findViewById(R.id.tvPhone);
        iv_packed = view.findViewById(R.id.iv_cancel);


        llSize = view.findViewById(R.id.llSize);
        llTimeSlot = view.findViewById(R.id.llTimeSlot);
        tv_packed = view.findViewById(R.id.tv_cancel);


        ivRecived = view.findViewById(R.id.ivRecived);
        ivCancel = view.findViewById(R.id.ivCancel);
        ivDelivered = view.findViewById(R.id.ivDelivered);
        iv_shipped = view.findViewById(R.id.iv_shipped);


        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);

        rlDelivered = view.findViewById(R.id.rlDelivered);
        rlOntheWay = view.findViewById(R.id.rlOntheWay);
        RlCancel = view.findViewById(R.id.RlCancel);
        rlProcess = view.findViewById(R.id.rlProcess);
        inprocess = view.findViewById(R.id.inprocess);
        rlinprocess = view.findViewById(R.id.rlinprocess);
        view10 = view.findViewById(R.id.view10);

        free_item_container = view.findViewById(R.id.free_item_container);
        freeProductRecyclerView = view.findViewById(R.id.rvFreeProduct);
        checkFreeItems();

        try {
            if ((pref.get(AppSettings.from) != null && pref.get(AppSettings.from).equalsIgnoreCase("dashboard"))) {
                Status = DashboardFragment.status;
                tvPaymentMode.setText(DashboardFragment.PaymentMode);


                tvOrderId.setText("#" + DashboardFragment.OrderId);

                tvOrderDate.setText(DashboardFragment.orderDate);
                tvOrderTotal.setText("\u20b9 " + DashboardFragment.TotalPrice);
            } else {
                Status = OrderPlacedFragment.status;
                tvPaymentMode.setText(OrderPlacedFragment.PaymentMode);


                tvOrderId.setText("#" + OrderPlacedFragment.OrderId);

                tvOrderDate.setText(OrderPlacedFragment.orderDate);
                tvOrderTotal.setText("\u20b9 " + OrderPlacedFragment.TotalPrice);
            }


            if (Status.equalsIgnoreCase("cancelled")) {
                // rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
                rlOntheWay.setBackground(getResources().getDrawable(R.drawable.circle_green));
                RlCancel.setBackground(getResources().getDrawable(R.drawable.red_circle));
                view1.setBackgroundColor(getResources().getColor(R.color.green_500));
                // view2.setBackgroundColor(getResources().getColor(R.color.re));
                ivCancel.setImageResource(R.drawable.ic_radio_button_checked);
                iv_packed.setImageResource(R.drawable.cancel);
                tv_packed.setText("Cancelled");
                RlCancel.setVisibility(View.VISIBLE);
            } else if (Status.equalsIgnoreCase("delivered")) {
                ivDelivered.setImageResource(R.drawable.ic_radio_button_checked);
                //   rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
                RlCancel.setVisibility(View.GONE);
                tv_packed.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view10.setVisibility(View.VISIBLE);
                view10.setBackgroundColor(getResources().getColor(R.color.green_500));
                rlinprocess.setVisibility(View.VISIBLE);
                rlinprocess.setBackground(getResources().getDrawable(R.drawable.circle_green));
                inprocess.setVisibility(View.VISIBLE);

                rlOntheWay.setBackground(getResources().getDrawable(R.drawable.circle_green));
                rlDelivered.setBackground(getResources().getDrawable(R.drawable.circle_green));
                view1.setBackgroundColor(getResources().getColor(R.color.green_500));
                view2.setBackgroundColor(getResources().getColor(R.color.green_500));
                //view3.setBackgroundColor(getResources().getColor(R.color.green_500));
            } else if (Status.equalsIgnoreCase("Failed")) {
                ivRecived.setImageResource(R.drawable.ic_radio_button_checked);
                tvOrderStatus.setText("Failed");

            } else if (Status.equalsIgnoreCase("On the Way")) {
                iv_shipped.setImageResource(R.drawable.ic_radio_button_checked);
                //   tvOrderStatus.setText("On the Way");
                //  rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));

                rlOntheWay.setBackground(getResources().getDrawable(R.drawable.circle_green));
                // view1.setBackgroundColor(getResources().getColor(R.color.green_500));
                // view2.setBackgroundColor(getResources().getColor(R.color.green_500));
                // view3.setBackgroundColor(getResources().getColor(R.color.green_500));
            } else {
                ivRecived.setImageResource(R.drawable.ic_radio_button_checked);
                rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
                view1.setBackgroundColor(getResources().getColor(R.color.green_500));
            }


        } catch (Exception e) {
        }

        try {
            if (OrderPlacedFragment.timeslot == null && OrderPlacedFragment.timeslot.isEmpty()) {
                llTimeSlot.setVisibility(View.GONE);
            } else {
                llTimeSlot.setVisibility(View.VISIBLE);
                tvTimeslot.setText(OrderPlacedFragment.timeslot);
            }
        } catch (Exception e) {
            llTimeSlot.setVisibility(View.GONE);
        }

        try {
            JSONArray jsonArray = new JSONArray(OrderPlacedFragment.productarray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<>();
                map.put("OrderId", object.getString("OrderId"));
                map.put("CustomerId", object.getString("CustomerId"));
                map.put("ProductName", object.getString("ProductName"));
                map.put("VarriationName", object.getString("VarriationName"));
                map.put("Quantity", object.getString("Quantity"));
                map.put("ItemCode", object.getString("ItemCode"));

                String completeUrl = object.getString("MainImage");
                completeUrl = completeUrl.replace(" ", "%20");
                map.put("ImageURL", completeUrl);
                arrayList.add(map);
            }
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rvProduct.setLayoutManager(linearLayoutManager2);
            rvProduct.setAdapter(new ProductAdapter(arrayList));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent mIntent = new Intent(getActivity(), DrawerActivity.class);
                        mIntent.putExtra("page", "account");
                        startActivity(mIntent);
                        //  getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        return true;
                    }
                }
                return false;
            }
        });


        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if ((pref.get(AppSettings.from) != null && pref.get(AppSettings.from).equalsIgnoreCase("dashboard"))) {
                        DrawerActivity.iv_menu.setVisibility(View.GONE);
                        DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                        Intent i = new Intent(getActivity(), DrawerActivity.class);
                        i.putExtra("page", "home");
                        startActivity(i);
                    } else {
                        DrawerActivity.iv_menu.setVisibility(View.GONE);
                        DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                        Intent i = new Intent(getActivity(), DrawerActivity.class);
                        i.putExtra("page", "account");
                        startActivity(i);
                    }
                    ///  getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                } catch (Exception e) {
                }
            }
        });


// Url=imageURL + data.get(position).get("ImageURL");
//                    ProductIdOrder = data.get(position).get("ProductCode");
//                    OrderId = data.get(position).get("OrderId");
//                    Quantity = data.get(position).get("Quantity");
//                    ProductTitle = data.get(position).get("ProductTitle");
//                    TotalPrice = data.get(position).get("TotalPrice");
//                    Address = data.get(position).get("Locality") + "," + data.get(position).get("Address") + "," + data.get(position).get("statename") + "," + data.get(position).get("CityName") + "," + data.get(position).get("PinCode");
//                    Name = data.get(position).get("Name");
//                    MobileNo1 = data.get(position).get("MobileNo1");
//                    status = data.get(position).get("Status");
//                    PaymentMode = data.get(position).get("PaymentMode");
//                    size = data.get(position).get("size");
        return view;
    }

    private void checkFreeItems() {
        ProductAdapter2 productAdapter2;
        showFreeItem = Boolean.parseBoolean(pref.get(AppSettings.hasFreeItem));
        if (showFreeItem) {
            free_item_container.setVisibility(View.VISIBLE);
            String data = pref.get(AppSettings.hasFreeItemList);
            Log.e("data", " " + data);
            try {
                JSONArray jsonArray = new JSONArray(data);
                freeItemArray.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("ProductId", object.getString("ProductId"));
                    map.put("varId", object.getString("varId"));
                    map.put("qty", object.getString("qty"));
                    map.put("ProductName", object.getString("ProductName"));
                    map.put("ProductImage", object.getString("ProductImage"));
                    freeItemArray.add(map);
                }
                productAdapter2 = (new ProductAdapter2(freeItemArray));
                mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
                freeProductRecyclerView.setLayoutManager(mGridLayoutManager);
                freeProductRecyclerView.setAdapter(productAdapter2);
            } catch (Exception e) {
                Log.e("Exception", " " + e);
            }

        } else {
            free_item_container.setVisibility(View.GONE);
        }
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    private class FavNameHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        ImageView productImage;
        TextView tvProductName;
        TextView tvOrderDate;
        TextView tvCancel;
        TextView tvReturn;
        TextView tvRemark;
        TextView tvTrack;

        public FavNameHolder(View itemView) {
            super(itemView);
            llMain = itemView.findViewById(R.id.llMain);
            productImage = itemView.findViewById(R.id.productImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvCancel = itemView.findViewById(R.id.tvCancel);
            tvReturn = itemView.findViewById(R.id.tvReturn);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvTrack = itemView.findViewById(R.id.tvTrack);
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

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            Glide.with(getActivity()).load(WebService.imageURL + data.get(position).get("ImageURL")).placeholder(R.drawable.placeholder11).into(holder.productImage);


            holder.tvOrderDate.setText("Ordered on " + data.get(position).get("orderDate"));
            holder.tvProductName.setText(data.get(position).get("ProductName"));


            holder.tvReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Url = WebService.imageURL + data.get(position).get("ImageURL");
                    ProductId = data.get(position).get("ProductCode");
                    OrderId = data.get(position).get("OrderId");
                    Quantity = data.get(position).get("Quantity");
                    orderDate = data.get(position).get("orderDate");
                    ProductTitle = data.get(position).get("ProductTitle");
                    TotalPrice = data.get(position).get("TotalPrice");
                    ReturnPopup();
                }
            });


            holder.tvTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        OrderId = data.get(position).get("OrderId");
                        HitTrackOrder hitTrackOrder = new HitTrackOrder();
                        hitTrackOrder.execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
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

    public class HitTrackOrder extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.TrackOrder(OrderId, "TrackingOrder");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy1234", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("Response");

                    if (jsonObject1.getString("Status").equals("True")) {
                        tracking_status = jsonObject1.getString("Res");
                        Log.v("fhhfjhghghgh", tracking_status);
                        TrackOrderPopup();
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

    public void CancelPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
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
        tvReason.setText("Are you sure want to Cancel this order ?");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        tvYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

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
    }

    public class HitCancelOrder extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.CancelOrder(OrderId, ProductCode, "CancelProduct");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy1234", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject object = jsonObject.getJSONObject("Response");

                    if (object.getString("Status").equalsIgnoreCase("True")) {

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

    public void ReturnPopup() {
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
        tvOrderQuantity.setText("Quantity: " + Quantity);
        tvOrderPrice.setText("\u20B9 " + TotalPrice);
        Glide.with(getActivity()).load(Url).placeholder(R.drawable.placeholder11).into(productImage);


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
                    order.execute();
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

    public class HitReturnOrder extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.ReturnOrder(OrderId, ProductId, etComment.getText().toString(), reasonList.get(spinnerReason.getSelectedItemPosition()).get("ReasonId"), pref.get(AppSettings.CustomerID), "ReturnCustomerOrder");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("displayyyyy1234", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    Log.e("listing", "" + jsonObject);

                    JSONObject object = jsonObject.getJSONObject("Response");

                    if (object.getString("Status").equalsIgnoreCase("True")) {
                        Intent i = new Intent(getActivity(), DrawerActivity.class);
                        i.putExtra("page", "order");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                        Toasty.success(getActivity(), "Requested Return Successfully", Toast.LENGTH_SHORT).show();
                    } else if (object.getString("Status").equalsIgnoreCase("ELECTRONICS")) {
                        Toasty.error(getActivity(), "You can't return electronics items", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(getActivity(), "Days limit exceeded to return this order", Toast.LENGTH_SHORT).show();
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

    public void TrackOrderPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_track_order);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        ImageView ivClose;
        View view1, view2, view3;

        ivClose = dialog.findViewById(R.id.ivClose);
        view1 = dialog.findViewById(R.id.view1);
        view2 = dialog.findViewById(R.id.view2);
        view3 = dialog.findViewById(R.id.view3);

        rlDelivered = dialog.findViewById(R.id.rlDelivered);
        rlOntheWay = dialog.findViewById(R.id.rlOntheWay);
        rlPacked = dialog.findViewById(R.id.rlPacked);
        rlProcess = dialog.findViewById(R.id.rlProcess);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (tracking_status.equals("1")) {
            rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
            view1.setBackgroundColor(getResources().getColor(R.color.green_500));
        } else if (tracking_status.equals("2")) {
            rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
            rlPacked.setBackground(getResources().getDrawable(R.drawable.circle_green));
            view1.setBackgroundColor(getResources().getColor(R.color.green_500));
            view2.setBackgroundColor(getResources().getColor(R.color.green_500));
        } else if (tracking_status.equals("3")) {
            rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
            rlPacked.setBackground(getResources().getDrawable(R.drawable.circle_green));
            rlOntheWay.setBackground(getResources().getDrawable(R.drawable.circle_green));
            view1.setBackgroundColor(getResources().getColor(R.color.green_500));
            view2.setBackgroundColor(getResources().getColor(R.color.green_500));
            view3.setBackgroundColor(getResources().getColor(R.color.green_500));
        } else if (tracking_status.equals("4")) {
            rlProcess.setBackground(getResources().getDrawable(R.drawable.circle_green));
            rlPacked.setBackground(getResources().getDrawable(R.drawable.circle_green));
            rlOntheWay.setBackground(getResources().getDrawable(R.drawable.circle_green));
            rlDelivered.setBackground(getResources().getDrawable(R.drawable.circle_green));
            view1.setBackgroundColor(getResources().getColor(R.color.green_500));
            view2.setBackgroundColor(getResources().getColor(R.color.green_500));
            view3.setBackgroundColor(getResources().getColor(R.color.green_500));
        }

    }

    private String getDate(String ourDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DDTkk:mm:ss.SSSZ");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            Log.v("ourDate", ourDate);
        } catch (Exception e) {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
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
            label.setText(list.get(position).get("ReasonName"));
            return row;
        }
    }

    private class FreeItemHodler extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        ImageView productImage;
        TextView tvProductName;

        public FreeItemHodler(View itemView) {
            super(itemView);
            llMain = itemView.findViewById(R.id.llMain);
            productImage = itemView.findViewById(R.id.productImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
        }
    }

    private class ProductAdapter2 extends RecyclerView.Adapter<FreeItemHodler> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter2(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public ProductAdapter2() {
        }

        public FreeItemHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FreeItemHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item_free, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FreeItemHodler holder, final int position) {
            holder.tvProductName.setText(" " + data.get(position).get("ProductName"));
            Glide.with(getActivity()).load(WebService.imageURL + data.get(position).get("ProductImage")).placeholder(R.drawable.placeholder11).into(holder.productImage);
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
}
