package claco.store.ProductFragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.customecomponent.CustomButton;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.DisplayCalendar;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

import static claco.store.utils.WebService.imageURL;

import com.bumptech.glide.Glide;

public class OrderPlacedFragment extends Fragment {
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
    public static String productarray;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog datePickerDialog;
    ImageView iv_search;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date1;
    View view;
    RecyclerView productRecyclerView;
    GridLayoutManager mGridLayoutManager;
    ProductAdapter proadapter;
    Preferences pref;
    CustomLoader loader;
    RelativeLayout layout_empty;
    CustomButton bAddNew;
    String filter = "";
    Spinner spinnerReason;
    EditText etComment, et_search;
    Object objCollection;
    JSONArray projsonArray = new JSONArray();
    String  cancelReason;


    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> CartList = new ArrayList<>();
    ArrayList<HashMap<String, String>> Ordereditemlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> reasonList = new ArrayList<>();
    ArrayList<String> reasonList1 = new ArrayList<>();

    RelativeLayout rlDelivered, rlOntheWay, rlPacked, rlProcess;
    TextView tv_filter;
    String tracking_status = "";
    TextView fromdate, todate;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.orderplacedfragment, container, false);
        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        productRecyclerView.setLayoutManager(mGridLayoutManager);
        DrawerActivity.tvHeaderText.setText("My Orders");
        DrawerActivity.ivHome.setVisibility(View.GONE);
        pref = new Preferences(getActivity());
        layout_empty = view.findViewById(R.id.ll);
        bAddNew = view.findViewById(R.id.bAddNew);
        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.ivHome.setVisibility(View.GONE);
        DrawerActivity.tv_points.setVisibility(View.GONE);
        // DrawerActivity.tvCount.setVisibility(View.GONE);
        fromdate = view.findViewById(R.id.fromdate);
        todate = view.findViewById(R.id.todate);
        DrawerActivity.iv_menu.setVisibility(View.VISIBLE);
        //DrawerActivity.tv_points.setText(pref.get(AppSettings.walletpoint));
        DrawerActivity.tvCount.setText(pref.get(AppSettings.count));

        DrawerActivity.rl_search.setVisibility(View.GONE);
        Log.e("test", pref.get(AppSettings.CustomerID));
        tv_filter = view.findViewById(R.id.tv_filter);
        et_search = view.findViewById(R.id.et_search);
        iv_search = view.findViewById(R.id.iv_search);
        bAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                //  getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });
        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DrawerActivity.iv_menu.setVisibility(View.GONE);
                    DrawerActivity.ivHome.setVisibility(View.VISIBLE);
                    Intent i = new Intent(getActivity(), DrawerActivity.class);
                    i.putExtra("page", "home");
                    startActivity(i);
                    ///  getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                } catch (Exception e) {
                }
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
                        ///  getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
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
            HitGetOrders order = new HitGetOrders();
            order.execute();

            HitGetCart order1 = new HitGetCart();
            order1.execute();

            HitGetReturnList return1 = new HitGetReturnList();
            return1.execute();
        } else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchApi();
            }
        });

        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   showApi();
            }
        });

        DisplayCalendar displayCalendar = new DisplayCalendar();
        date1 = displayCalendar.displayDateDialog(fromdate);
        date = displayCalendar.displayDateDialog(todate);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //.  datePickerDialog.getDatePicker().getD

                //  datePickerDialog.getDatePicker().setMinDate(twoDaysAgo.getTimeInMillis());
                //  datePickerDialog.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());
                updateLabel(fromdate, myCalendar);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));

            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //  datePickerDialog.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());
                updateLabel(todate, myCalendar);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));

            }
        });
//
        return view;
    }

    private void SearchApi() {
        HitGetOrders appointment = new HitGetOrders();
        appointment.execute();
    }

    private void updateLabel(TextView textView, Calendar calendar) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        textView.setText(sdf.format(calendar.getTime()));
        //tvDate.setText(sdf.format(calendar.getTime()));
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
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
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvYes = (TextView) dialog.findViewById(R.id.tvOk);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvcancel);
        EditText et = dialog.findViewById(R.id.et);
        cancelReason = et.getText().toString();
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

    void filter(String text) {
        if (arrayList.size() > 0) {

            //+data.get(position).get("OrderId")
            ArrayList<HashMap<String, String>> temp = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).get("OrderId")
                        .toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))
                        || arrayList.get(i).get("OrderId").toLowerCase(Locale.getDefault()).contains(
                        text.toLowerCase(Locale.getDefault()))
                ) {

                    temp.add(arrayList.get(i));
                }
            }
            //update recyclerview
            try {
                proadapter.updateList(temp);
            } catch (Exception e) {
            }
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
            SimpleDateFormat formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                formatter = new SimpleDateFormat("YYYY-MM-DDTkk:mm:ss.SSSZ");
            }
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

    public void showApi() {
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
        String[] filtertypes = {"Product name", "Brand", "Rate", "Offer"};

        TextView tvYes = (TextView) dialog.findViewById(R.id.tv_ok);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        tvYes.setOnClickListener(new View.OnClickListener() {
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

    private class FavNameHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        ImageView productImage;
        TextView tvProductName;
        TextView tvOrderDate;
        TextView tvCancel;
        TextView tvReturn;
        TextView tvRemark;
        TextView tvTrack;
        TextView tvOrderId, tvDeliveryCharges, tvPaymentMode, tvNetPayable, tvDeliveryStatus;
        CardView returnCard, detailsCard, cancelCard;

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
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvDeliveryCharges = itemView.findViewById(R.id.tvDeliveryCharges);
            tvPaymentMode = itemView.findViewById(R.id.tvPaymentMode);
            tvNetPayable = itemView.findViewById(R.id.tvNetPayable);
            tvDeliveryStatus = itemView.findViewById(R.id.tvDeliveryStatus);
            returnCard = itemView.findViewById(R.id.card1);
            detailsCard = itemView.findViewById(R.id.card2);
            cancelCard = itemView.findViewById(R.id.card3);
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

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items, parent, false));
            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            holder.tvOrderId.setText(" " + data.get(position).get("OrderId"));
            holder.tvDeliveryCharges.setText("" + data.get(position).get("DeliveryCharges"));
            holder.tvPaymentMode.setText("" + data.get(position).get("PaymentMode"));
            holder.tvNetPayable.setText("\u20b9 " + data.get(position).get("NetPayable"));
            Log.e("DeliveryStatus", "found" + data.get(position).get("DeliveryStatus"));
            if (!data.get(position).get("DeliveryStatus").toString().equals("Delivered") || !data.get(position).get("DeliveryStatus").equals("cancelled")) {
                holder.tvDeliveryStatus.setText("" + data.get(position).get("DeliveryStatus"));
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.cancelCard.setVisibility(View.VISIBLE);
            } else {
                holder.tvCancel.setVisibility(View.GONE);
                holder.cancelCard.setVisibility(View.GONE);
            }
            if (data.get(position).get("DeliveryStatus").equals("cancelled")) {
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvReturn.setVisibility(View.GONE);
                holder.returnCard.setVisibility(View.GONE);
                holder.cancelCard.setVisibility(View.GONE);
                holder.tvDeliveryStatus.setTextColor(getActivity().getResources().getColor(R.color.red_500));
            }
            holder.tvOrderDate.setText("Ordered on : " + data.get(position).get("OrderDate"));


            if (holder.tvCancel.getText().equals("Cancel Order")) {

                holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OrderId = data.get(position).get("OrderId");
                        ProductCode = data.get(position).get("ProductCode");
                        CancelPopup();
                    }
                });
            }


            holder.tvReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Url = imageURL + data.get(position).get("ImageURL");
                    ProductId = data.get(position).get("ProductCode");
                    OrderId = data.get(position).get("OrderId");
                    Quantity = data.get(position).get("Quantity");
                    orderDate = data.get(position).get("orderDate");
                    ProductTitle = data.get(position).get("ProductTitle");
                    TotalPrice = data.get(position).get("TotalPrice");
                    getOrderList = data.get(position).get("getOrderList");
                    try {
                        JSONArray jsonObject = new JSONArray(getOrderList);

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject obj1 = new JSONObject();
                            obj1 = jsonObject.getJSONObject(i);
                            JSONObject obj2 = new JSONObject();
                            try {
                                obj2.put("ItemCode", obj1.getString("ItemCode"));


                                objCollection = obj2;
                                projsonArray.put(obj2);
                            } catch (JSONException e) {
                                Log.e("err1", "" + e.getMessage());
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("err2", "" + e.getMessage());
                    }


                    ReturnPopup("" + projsonArray);
                }
            });


            holder.tvTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderId = data.get(position).get("OrderId");
                    // Quantity = data.get(position).get("Quantity");
                    orderDate = data.get(position).get("OrderDate");
                    //ProductTitle = data.get(position).get("ProductTitle");
                    TotalPrice = data.get(position).get("NetPayable");
                    TotalPrice = data.get(position).get("NetPayable");
                    //  Address = data.get(position).get("Locality") + "," + data.get(position).get("Address") + "," + data.get(position).get("statename") + "," + data.get(position).get("CityName") + "," + data.get(position).get("PinCode");
                    //Name = data.get(position).get("Name");
                    // MobileNo1 = data.get(position).get("MobileNo1");
                    status = data.get(position).get("DeliveryStatus");
                    PaymentMode = data.get(position).get("PaymentMode");
                    productarray = data.get(position).get("getOrderList");
                    //  size = data.get(position).get("size");
                    //  timeslot=  data.get(position).get("TimeSlotWithDate");
                    pref.set(AppSettings.from, "Order");
                    pref.commit();
                    pref.set(AppSettings.hasFreeItem, data.get(position).get("IsfreeItem"));
                    pref.commit();
                    pref.set(AppSettings.hasFreeItemList, data.get(position).get("getfreeitemlist"));
                    pref.commit();
                    Log.e("hasFreeItem", ""+data.get(position).get("IsfreeItem") + pref.get(AppSettings.hasFreeItem) + pref.get(AppSettings.hasFreeItemList));

                    replaceFragmentWithAnimation(new OrderDetailsFragment());
//                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(true);
//                        OrderId = data.get(position).get("OrderId");
//                        HitTrackOrder hitTrackOrder=new HitTrackOrder();
//                        hitTrackOrder.execute();
//                    } else {
//                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
//                    }
                }
            });


            Log.v("fhfhfhffhfhfhhfhf", getDate("2019-12-04T15:50:35.214000"));
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

    public class HitGetOrders extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.OrderList(pref.get(AppSettings.CustomerID), fromdate.getText().toString(),
                    todate.getText().toString(), "orderList");

            Log.e("sentData", pref.get(AppSettings.CustomerID)+ "/"+fromdate.getText().toString()+ "/"+todate.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            Log.e("orderList", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    // JSONArray jsonArray = jsonObject.getJSONArray("getOrderList");
                    JSONArray jsonArray = jsonObject.getJSONArray("morder");

                    if (jsonArray.length() == 0) {
                        layout_empty.setVisibility(View.VISIBLE);
                        //ll.setVisibility(View.VISIBLE);
                        productRecyclerView.setVisibility(View.GONE);
                    } else {
                        arrayList.clear();

                        layout_empty.setVisibility(View.GONE);
                        productRecyclerView.setVisibility(View.VISIBLE);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("OrderId", object.getString("OrderId"));
                            map.put("OrderDate", object.getString("OrderDate"));
                            map.put("GrossAmount", object.getString("GrossAmount"));
                            map.put("DeliveryCharges", object.getString("DeliveryCharges"));
                            map.put("PaymentMode", object.getString("PaymentMode"));
                            map.put("NetPayable", object.getString("NetPayable"));
                            map.put("DeliveryStatus", object.getString("DeliveryStatus"));
                            map.put("getOrderList", "" + object.getJSONArray("getOrderList"));
//                            map.put("getfreeitemlist", "" + object.getJSONArray("getfreeitemlist"));
//                            map.put("IsfreeItem", "" + object.getBoolean("IsfreeItem"));
                            Log.e("DeliveryStatus", "putt " + object.getString("DeliveryStatus"));

                            arrayList.add(map);
                        }
                        //  Collections.reverse(arrayList);
                        proadapter = (new ProductAdapter(arrayList));
                        productRecyclerView.setAdapter(proadapter);

                    }

                    et_search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                            filter(text);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                            filter(text);
                        }
                    });

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

    public class HitGetReturnList extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.GetReturnList("GetReturnReason");

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
//Responce
                    JSONArray jsonArray = jsonObject.getJSONArray("Responce");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        HashMap<String, String> map = new HashMap<>();

                        map.put("ReasonId", jsonObject1.getString("ReasonId"));
                        map.put("ReasonName", jsonObject1.getString("ReasonName"));

                        reasonList.add(map);
                        reasonList1.add(jsonObject1.getString("ReasonName"));
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
            Log.e("GetCartList", displayText);
            CartList.clear();
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
                        //  map.put("RateId", RateId);
                        CartList.add(map);
                        Log.e("ProdID", ProdID);


                        Log.e("RateId", "new Quantitnmy" + Quantity);
                    }
                    pref.set(AppSettings.count, "" + CartList.size()/* jsonArray.length()*/);
                    pref.commit();
                    DrawerActivity.tvCount.setText(pref.get(AppSettings.count));

//

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
