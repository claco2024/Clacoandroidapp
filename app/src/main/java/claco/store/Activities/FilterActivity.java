package claco.store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    public static JSONArray jsonResponse;
    TextView tvClearFilter, tvApply, tvBrand, tvRate, tvOffer, tvCategory, tvFrom, tvTo;
    RecyclerView rvBrand, rvOffer, rvCategory;
    CustomLoader loader;
    LinearLayout llRate;
    GridLayoutManager mGridLayoutManager;
    BrandAdapter brandAdapter;
    CategoryAdapter categoryAdapter;
    // OfferAdapter categoryAdapter;
    RangeSeekBar seekbar;
    String manufacturername = "";
    String catename = "";
    ArrayList<HashMap<String, String>> brandarrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> categoryList = new ArrayList<>();
    ArrayList<String> selectedcategoryList = new ArrayList<>();
    ArrayList<String> selectedbrandarrayList = new ArrayList<>();
    String SelectedCategories;
    String SelectedBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getId();
        setListner();
        hitApi();
    }

    private void hitApi() {
        HitGetBrand order = new HitGetBrand();
        order.execute();
        HitGetOfffer hiGetOfffer = new HitGetOfffer();
        hiGetOfffer.execute();

        HitCategory hitcategory = new HitCategory();
        hitcategory.execute();
    }

    private void getId() {
        tvClearFilter = findViewById(R.id.tvClearFilter);
        tvBrand = findViewById(R.id.tvBrand);
        tvApply = findViewById(R.id.tvApply);
        tvRate = findViewById(R.id.tvRate);
        tvOffer = findViewById(R.id.tvOffer);
        tvCategory = findViewById(R.id.tvCategory);
        rvBrand = findViewById(R.id.rvBrand);
        rvOffer = findViewById(R.id.rvOffer);
        rvCategory = findViewById(R.id.rvCategory);
        seekbar = findViewById(R.id.rangeSeekBar);
        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);
        llRate = findViewById(R.id.llRate);

    }

    private void setListner() {
        loader = new CustomLoader(FilterActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mGridLayoutManager = new GridLayoutManager(FilterActivity.this, 1);
        rvBrand.setLayoutManager(mGridLayoutManager);
        mGridLayoutManager = new GridLayoutManager(FilterActivity.this, 1);
        rvOffer.setLayoutManager(mGridLayoutManager);
        mGridLayoutManager = new GridLayoutManager(FilterActivity.this, 1);
        rvCategory.setLayoutManager(mGridLayoutManager);
        tvClearFilter.setOnClickListener(this::onClick);
        tvBrand.setOnClickListener(this::onClick);
        tvApply.setOnClickListener(this::onClick);
        tvRate.setOnClickListener(this::onClick);
        tvOffer.setOnClickListener(this::onClick);
        tvCategory.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvClearFilter) {
            if (Utils.isNetworkConnectedMainThred(FilterActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(true);
                HitGetBrand order = new HitGetBrand();
                order.execute();
                HitCategory orde2r = new HitCategory();
                orde2r.execute();
                tvTo.setText("");
                tvFrom.setText("");
                 manufacturername = "";
                  catename = "";
            } else {
                Toasty.error(FilterActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
            }
        } else if (v.getId() == R.id.tvApply) {

            if (Utils.isNetworkConnectedMainThred(FilterActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(true);
                HitProducts hitProducts = new HitProducts();
                hitProducts.execute();

            } else {
                Toasty.error(FilterActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
            }


        } else if (v.getId() == R.id.tvBrand) {
            if (brandarrayList.size() == 0) {
                if (Utils.isNetworkConnectedMainThred(FilterActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);
                    HitGetBrand order = new HitGetBrand();
                    order.execute();

                } else {
                    Toasty.error(FilterActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            }
            tvBrand.setBackground(getResources().getDrawable(R.drawable.bg_features_selected_square));
            tvRate.setBackground(null);
            tvOffer.setBackground(null);
            tvCategory.setBackground(null);
            rvBrand.setVisibility(View.VISIBLE);
            llRate.setVisibility(View.GONE);
            rvCategory.setVisibility(View.GONE);
            rvOffer.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tvRate) {
            tvRate.setBackground(getResources().getDrawable(R.drawable.bg_features_selected_square));
            tvBrand.setBackground(null);
            tvOffer.setBackground(null);
            tvCategory.setBackground(null);
            rvBrand.setVisibility(View.GONE);
            llRate.setVisibility(View.VISIBLE);
            rvCategory.setVisibility(View.GONE);
            rvOffer.setVisibility(View.GONE);
            setRate();

        } else if (v.getId() == R.id.tvOffer) {
            tvOffer.setBackground(getResources().getDrawable(R.drawable.bg_features_selected_square));
            tvRate.setBackground(null);
            tvBrand.setBackground(null);
            tvCategory.setBackground(null);
            rvBrand.setVisibility(View.GONE);
            rvCategory.setVisibility(View.GONE);
            rvOffer.setVisibility(View.VISIBLE);
            llRate.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tvCategory) {
            tvCategory.setBackground(getResources().getDrawable(R.drawable.bg_features_selected_square));
            tvRate.setBackground(null);
            tvOffer.setBackground(null);
            tvBrand.setBackground(null);
            rvBrand.setVisibility(View.GONE);
            rvCategory.setVisibility(View.VISIBLE);
            rvOffer.setVisibility(View.GONE);
            llRate.setVisibility(View.GONE);
        }
    }

    private void setRate() {
        seekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged(
                    final RangeSeekBar seekBar, final int progressStart, final int progressEnd, final boolean fromUser) {
                tvFrom.setText(String.valueOf(progressStart));
                tvTo.setText(String.valueOf(progressEnd));
            }

            @Override
            public void onStartTrackingTouch(final RangeSeekBar seekBar) {
//                tvFrom.setText("" + progressStart);
//                tvTo.setText("" + progressEnd);
            }

            @Override
            public void onStopTrackingTouch(final RangeSeekBar seekBar) {
//                tvFrom.setText("" + seekBar.get);
//                tvTo.setText("" + progressEnd);
            }
        });
    }

    public class HitGetBrand extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.getManufacturerlist("", "getManufacturerlist");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.v("getofferlist", " getofferlist " + displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("ManufacturesResponse");
                    if (jsonArray.length() == 0) {

                    } else {
                        brandarrayList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("ManufacturerId", object.getString("ManufacturerId"));
                            map.put("ManufacturerName", object.getString("ManufacturerName"));
                            map.put("ManufacturerImage", object.getString("ManufacturerImage"));
                            brandarrayList.add(map);
                        }
                        brandAdapter = (new BrandAdapter(brandarrayList));
                        rvBrand.setAdapter(brandAdapter);

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

    private class BrandNameHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_title;
        CheckBox checkbox;


        public BrandNameHolder(View itemView) {
            super(itemView);

            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            checkbox = itemView.findViewById(R.id.checkbox);


        }
    }

    private class BrandAdapter extends RecyclerView.Adapter<BrandNameHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public BrandAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public BrandNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new BrandNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manufacturer_items, parent, false));
            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final BrandNameHolder holder, final int position) {
            holder.tv_title.setText(data.get(position).get("ManufacturerName") /*+ " ("+data.get(position).get("CouponId")+")"*/);

            Glide.with(FilterActivity.this).load(WebService.imageURL + data.get(position).get("ManufacturerImage")).placeholder(R.drawable.placeholder11).into(holder.iv_img);

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = data.get(position).get("ManufacturerId");
                    if (holder.checkbox.isChecked()) {
                        if (selectedbrandarrayList.size() > 0) {
                            for (int i = 0; i < selectedbrandarrayList.size(); i++) {
                                if (selectedbrandarrayList.get(i).equalsIgnoreCase(id)) {
                                } else {
                                    selectedbrandarrayList.add(data.get(position).get("ManufacturerId"));
                                }
                            }
                            //manufacturername= (data.get(position).get("ManufacturerId"));
                        }/*else if ( manufacturername.contains(data.get(position).get("ManufacturerId")+",")){
                            manufacturername.replace(","+data.get(position).get("ManufacturerId"),"");
                        }*/ else {
                            selectedbrandarrayList.add(data.get(position).get("ManufacturerId"));
                        }
                    } else {
                        selectedbrandarrayList.remove(data.get(position).get("ManufacturerId"));
                    }
                }
            });

            //if (manufacturername.isEmpty()){
            //                         manufacturername =  data.get(position).get("ManufacturerId");
            //                     }else {
            //                         manufacturername =manufacturername+","+ data.get(position).get("ManufacturerId");
            //                     }
        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


    }


    public class HitCategory extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.getManufacturerlist("", "getCategory");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.v("getCategory", " getCategory " + displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("CatResponse");
                    if (jsonArray.length() == 0) {

                    } else {
                        categoryList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("CategoryId", object.getString("CategoryId"));
                            map.put("CategoryName", object.getString("CategoryName"));
                            map.put("CategoryImage", object.getString("CategoryImage"));
                            categoryList.add(map);
                        }
                        categoryAdapter = (new CategoryAdapter(categoryList));
                        rvCategory.setAdapter(categoryAdapter);

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

    private class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_title;
        CheckBox checkbox;


        public CategoryHolder(View itemView) {
            super(itemView);

            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            checkbox = itemView.findViewById(R.id.checkbox);


        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public CategoryAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new CategoryHolder(LayoutInflater.from(FilterActivity.this).inflate(R.layout.manufacturer_items, parent, false));
            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final CategoryHolder holder, final int position) {
            holder.tv_title.setText(data.get(position).get("CategoryName"));

            Glide.with(FilterActivity.this).load(WebService.imageURL + data.get(position).get("CategoryImage")).placeholder(R.drawable.placeholder11).into(holder.iv_img);

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = data.get(position).get("CategoryId");
                    if (holder.checkbox.isChecked()) {
                        if (selectedcategoryList.size() > 0) {
                            for (int i = 0; i < selectedcategoryList.size(); i++) {
                                if (selectedcategoryList.get(i).equalsIgnoreCase(id)) {
                                    selectedcategoryList.remove(data.get(position).get("CategoryId"));
                                } else {
                                    selectedcategoryList.add(data.get(position).get("CategoryId"));
                                }
                            }
                            //manufacturername= (data.get(position).get("ManufacturerId"));
                        }/*else if ( manufacturername.contains(data.get(position).get("ManufacturerId")+",")){
                            manufacturername.replace(","+data.get(position).get("ManufacturerId"),"");
                        }*/ else {
                            selectedcategoryList.add(data.get(position).get("CategoryId"));
                        }
                    } else {
                        selectedcategoryList.remove(data.get(position).get("CategoryId"));
                    }

//                    if (holder.checkbox.isChecked()) {
//                        if (catename.isEmpty()) {
//                            catename = (data.get(position).get("CategoryId"));
//                        } else {
//                            catename = catename + "," + (data.get(position).get("CategoryId"));
//                        }
//                    } else {
//                        if (catename.contains("," + data.get(position).get("CategoryId"))) {
//                            catename.replace("," + data.get(position).get("CategoryId"), "");
//                        } else {
//
//                        }
//                    }
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


    public class HitGetOfffer extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.getManufacturerlist("", "OfferList");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.v("getofferlist", " getofferlist " + displayText);
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray jsonArray = jsonObject.getJSONArray("ManufacturesResponse");
                    if (jsonArray.length() == 0) {

                    } else {
                        brandarrayList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("ManufacturerId", object.getString("ManufacturerId"));
                            map.put("ManufacturerName", object.getString("ManufacturerName"));
                            map.put("ManufacturerImage", object.getString("ManufacturerImage"));
                            brandarrayList.add(map);
                        }
                        brandAdapter = (new BrandAdapter(brandarrayList));
                        rvBrand.setAdapter(brandAdapter);

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

//    private class BrandNameHolder extends RecyclerView.ViewHolder {
//
//        ImageView iv_img;
//        TextView tv_title;
//
//
//        public BrandNameHolder(View itemView) {
//            super(itemView);
//
//            iv_img = itemView.findViewById(R.id.iv_img);
//            tv_title = itemView.findViewById(R.id.tv_title);
//
//
//        }
//    }
//
//    private class BrandAdapter extends RecyclerView.Adapter<BrandNameHolder> {
//
//        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//
//        public BrandAdapter(ArrayList<HashMap<String, String>> favList) {
//            data = favList;
//        }
//
//        public BrandNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            return new BrandNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manufacturer_items, parent, false));
//            // return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderplaced_items_new, parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final BrandNameHolder holder, final int position) {
//            holder.tv_title.setText(data.get(position).get("ManufacturerName") /*+ " ("+data.get(position).get("CouponId")+")"*/);
//
//            Glide.with(FilterActivity.this).load(WebService.imageURL + data.get(position).get("ManufacturerImage")).placeholder(R.drawable.placeholder11).into(holder.iv_img);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    manufacturername = data.get(position).get("ManufacturerName");
////                    if (Utils.isNetworkConnectedMainThred(FilterActivity.this)) {
////                        loader.show();
////                        loader.setCancelable(false);
////                        loader.setCanceledOnTouchOutside(true);
////                       // new  HitProducts().execute(data.get(position).get("ManufacturerId"));
////                    }
////                    else {
////                        Toasty.error(FilterActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
////                    }
//
//                }
//            });
//        }
//
//        public int getItemCount() {
//            return data.size();
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }
//
//
//    }


    public class HitProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {
            for (int i=0 ;i<selectedcategoryList.size();i++){
                 if ( catename.isEmpty()){
                     catename= selectedcategoryList.get(i);
                 }else {
                     catename= catename+","+selectedcategoryList.get(i);
                 }
            }
            for (int i=0 ;i<selectedbrandarrayList.size();i++){
                 if ( manufacturername.isEmpty()){
                     manufacturername= selectedbrandarrayList.get(i);
                 }else  if (manufacturername.contains(selectedbrandarrayList.get(i)))
                 {}
                 else {
                     manufacturername= manufacturername+","+selectedbrandarrayList.get(i);
                 }
            }
                displayText = WebService.GetProductlistByfilter(manufacturername, "", tvFrom.getText().toString(), tvTo.getText().toString(), catename, "GetProductlistByfilter");

            Log.e("GetProductlistByfilter", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    // arrayList.clear();

                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("proitemnew")) {
                        jsonResponse = jsonObject.getJSONArray("proitemnew");
                        if (jsonResponse.length() > 0) {

                            AppSettings.productFrom = "10";
                            startActivity(new Intent(FilterActivity.this, DrawerActivity.class).putExtra("page", "search"));

                        } else {
                            Toasty.error(FilterActivity.this, "No Data Found !!", Toast.LENGTH_SHORT, true).show();
                        }


                    } else {
                        Toasty.error(FilterActivity.this, "No Data Found !!", Toast.LENGTH_SHORT, true).show();
                    }
                } catch (Exception e) {
                    Toasty.error(FilterActivity.this, "No Data Found !!", Toast.LENGTH_SHORT, true).show();
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