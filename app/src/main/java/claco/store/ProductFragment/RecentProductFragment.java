package claco.store.ProductFragment;


import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import claco.store.DataModel.SectionDataModel;
import claco.store.DataModel.SingleItemModel;
import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.adapters.RecyclerViewDataAdapter;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class RecentProductFragment extends Fragment {

    View view;

    RecyclerView recyclerview;
    GridLayoutManager mGridLayoutManager;

    String productId;

    public static JSONObject jsonObject;

    ArrayList<HashMap<String, String>> recentArray = new ArrayList<>();

    ArrayList<SectionDataModel> allSampleData=new ArrayList<>();
    CustomLoader loader;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recentprouct, container, false);


        recyclerview = view.findViewById(R.id.recyclerview);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setLayoutManager(mGridLayoutManager);

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
                      //  getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        return true;
                    }
                }
                return false;
            }
        });
        DrawerActivity.rl_search.setVisibility(View.GONE);
        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.iv_menu.setTag("arrow");
        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "backkkk", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(getActivity(), DrawerActivity.class);
                mIntent.putExtra("page", "home");
                startActivity(mIntent);
               // getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        DrawerActivity.ivHome.setVisibility(View.GONE);


        //custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            AppSettings.statusvendor="2";
            HitRecentlyAddedProduct product = new HitRecentlyAddedProduct();
            product.execute();

        }
        else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }

        return view;
    }

    public class HitRecentlyAddedProduct extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.RecentlyAddedProduct("20", "","LastWeekAddedProduct");

            Log.e("disssssss123", displayText);
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            AppSettings.status = "1";
            //   replaceFragmentWithAnimation(new ProductSubcategoryFragment());
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonrecentlproduct = new JSONObject(displayText);

                    JSONArray jsonArray=jsonrecentlproduct.getJSONArray("getProductResponse");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        HashMap<String,String> map=new HashMap<>();
                        map.put("ProductId",object.getString("ProductId"));
                        map.put("ProductTitle",object.getString("ProductTitle"));
                        map.put("MainPicture",object.getString("MainPicture"));
                        map.put("MRP",object.getString("MRP"));
                        map.put("SalePrice",object.getString("SalePrice"));
                        recentArray.add(map);
                    }

                    recyclerview.setHasFixedSize(true);
                    // recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerview.setAdapter(new RecentProductAdapter(recentArray));

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

    public class HitVendorProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetCat("GetVendorDashboard");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);
                    JSONArray array = jsonObject.getJSONArray("getVendorDashboard");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        JSONArray ProductArray=object.getJSONArray("ProductDetails");

                        if(ProductArray.length() !=0)
                        {
                            String completeUrl = object.getString("StoreImage");
                            completeUrl = completeUrl.replace(" ", "%20");
                            SectionDataModel dm = new SectionDataModel();
                            dm.setHeaderTitle(object.getString("shopName"));
                            dm.setCityName(object.getString("CityName"));
                            dm.setStateName(object.getString("StateName"));
                            dm.setStoreImage(completeUrl);

                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                            for (int j = 0; j <ProductArray.length(); j++) {
                                JSONObject object1=ProductArray.getJSONObject(j);
                                String Url = object1.getString("MainPicture");
                                Url = Url.replace(" ", "%20");
                                map.put("MainPicture", completeUrl);

                                singleItem.add(new SingleItemModel(object1.getString("ProductTitle"), object1.getString("MinimumQuantity"),object1.getString("ProductId"),object1.getString("SalePrice"),Url));
                            }

                            dm.setAllItemsInSection(singleItem);
                            allSampleData.add(dm);
                        }
                    }
                    recyclerview.setHasFixedSize(true);
                    RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData);
                    recyclerview.setAdapter(adapter);

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

    public static class FavNameHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvDiscount;
        TextView tvFinalprice;
        TextView tvOldPrice;
        TextView tvRating;
        TextView tvSave;
        TextView tvProductName;
        LinearLayout layout_item;
        CardView cardView;
        public FavNameHolder(View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvSave=itemView.findViewById(R.id.tvSave);

            tvProductName=itemView.findViewById(R.id.tvProductName);

            layout_item=itemView.findViewById(R.id.layout_item);

            cardView=itemView.findViewById(R.id.cardView);
        }
    }
    private class RecentProductAdapter extends RecyclerView.Adapter<FavNameHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public RecentProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }
        public RecentProductAdapter() {
        }
        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            holder.layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productId = data.get(position).get("ProductId");

                    Log.e("product",productId);

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
            });


            holder.tvOldPrice.setText("\u20b9" +data.get(position).get("MRP"));
            holder.tvProductName.setText(data.get(position).get("ProductTitle"));
            String URL=WebService.imageURL+data.get(position).get("MainPicture");
            double saving = Double.parseDouble(data.get(position).get("MRP")) - Double.parseDouble(data.get(position).get("SalePrice"));
            holder.tvSave.setText("Save \u20b9 " + String.valueOf(saving));
            holder.tvOldPrice.setPaintFlags( holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tvOldPrice.setText("\u20b9 " + data.get(position).get("MRP"));
            holder.tvFinalprice.setText("\u20b9 " + data.get(position).get("SalePrice"));

            double retailprice = Double.parseDouble(data.get(position).get("MRP"));
            if (retailprice!=0) {
                double res = (saving / retailprice) * 100;
                roundTwoDecimals(res);
                int i2 = (int) Math.round(roundTwoDecimals(res));
                holder.tvDiscount.setText(String.valueOf(i2) + " % off");
            }
            try{
                Glide.with(getActivity()).load(URL).placeholder(R.drawable.placeholder11).into(holder.ivProduct);
            }
            catch (Exception e)
            {

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

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }

    public class HitProductDetail extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProductDetail(productId, "","","","getProductdetail");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("getProductResponse")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("getProductResponse");
                        AppSettings.fromPage = "5";
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

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
     //   transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }
}
