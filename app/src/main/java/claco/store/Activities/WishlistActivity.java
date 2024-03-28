package claco.store.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class WishlistActivity extends AppCompatActivity {


    RecyclerView recyclerview;
    GridLayoutManager mGridLayoutManager;

    TextView tvHeaderText;

    LinearLayout layout_empty;

    Preferences pref;

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

    CustomLoader loader;

    ImageView iv_menu;

    String productId,  ProductCategory , ProductName , CatId ;

    Button btnBrowse;

    public static  JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        recyclerview = findViewById(R.id.recyclerview);
        layout_empty=findViewById(R.id.layout_empty);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(mGridLayoutManager);
        tvHeaderText=findViewById(R.id.tvHeaderText);
        tvHeaderText.setText("My WishList");
        iv_menu=findViewById(R.id.iv_menu);

        btnBrowse=findViewById(R.id.btnBrowse);

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WishlistActivity.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });


        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WishlistActivity.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        pref=new Preferences(this);


        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            new HitGetWishlist().execute();
        }
        else {
            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, DrawerActivity.class);
        i.putExtra("page", "home");
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }

    private class FavNameHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        ImageView ivDelete;
        TextView tvProductName;
        TextView tvFinalprice;
        TextView tvOldPrice;
        TextView tvSave;
        TextView tvBuynow;

        LinearLayout llmain;

        public FavNameHolder(View itemView) {
            super(itemView);
            ivImage=itemView.findViewById(R.id.ivImage);
            ivDelete=itemView.findViewById(R.id.ivDelete);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvFinalprice=itemView.findViewById(R.id.tvFinalprice);
            tvOldPrice=itemView.findViewById(R.id.tvOldPrice);
            tvSave=itemView.findViewById(R.id.tvSave);
            tvBuynow=itemView.findViewById(R.id.tvBuynow);
            llmain=itemView.findViewById(R.id.llmain);
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

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            //Setting Values

            holder.tvProductName.setText(data.get(position).get("ProductTitle"));
            holder.tvFinalprice.setText(data.get(position).get("SalePrice"));
            holder.tvOldPrice.setText(data.get(position).get("RetailerPrice"));
            Double finalretail=Double.parseDouble(data.get(position).get("RetailerPrice"));

            double saving = finalretail - Double.parseDouble(data.get(position).get("SalePrice"));

            holder.tvBuynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    productId = data.get(position).get("ProductCode");
                    ProductCategory = data.get(position).get("ProductCategory");
                    ProductName = data.get(position).get("ProductName");
                    CatId = data.get(position).get("MainCategoryCode");

                    //Hitting API
                    if (Utils.isNetworkConnectedMainThred(WishlistActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitProductDetail().execute();
                    }
                    else {
                        Toasty.error(WishlistActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                    }

                }
            });


            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    productId=data.get(position).get("ProductID");

                    if (Utils.isNetworkConnectedMainThred(WishlistActivity.this)) {
                        HitUnlike unlike = new HitUnlike();
                        unlike.execute();

                    }
                    else {
                        Toasty.error(WishlistActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
                    }

                }
            });

            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvSave.setText("Save \u20b9 " + String.valueOf(saving));
            Glide.with(WishlistActivity.this).load( WebService.imageURL + data.get(position).get("ImageURL")).placeholder(R.drawable.placeholder11).into(holder.ivImage);
        }

        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public class HitGetWishlist extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetWishList(pref.get(AppSettings.CustomerID),"wishList");

            Log.e("displaytext1234", displayText);

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                  JSONObject  jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("getwishList")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("getwishList");

                        if(jsonResponse.length()==0)
                        {
                            layout_empty.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                        }
                        else
                        {
                            layout_empty.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);

                            for(int i=0;i<jsonResponse.length();i++)
                            {
                                JSONObject object=jsonResponse.getJSONObject(i);
                                HashMap<String,String> map=new HashMap<>();
                                map.put("ProductID",object.getString("ProductID"));
                                map.put("ProductTitle",object.getString("ProductTitle"));
                                map.put("SalePrice",object.getString("SalePrice"));
                                map.put("RetailerPrice",object.getString("RetailerPrice"));
                                String completeUrl = object.getString("ImageURL");
                                completeUrl = completeUrl.replace(" ", "%20");
                                map.put("ImageURL", completeUrl);

                                arrayList.add(map);
                            }
                            recyclerview.setAdapter(new ProductAdapter(arrayList));
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

    public class HitUnlike extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.LikeUnlike(pref.get(AppSettings.CustomerID),productId,"DeleteWishList");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                   JSONObject jsonObject = new JSONObject(displayText);

                    JSONObject Response=jsonObject.getJSONObject("Response");


                    if (Response.getString("Status").equalsIgnoreCase("true")) {


                        new HitGetWishlist().execute();


                        Intent i = new Intent(WishlistActivity.this, WishlistActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);


                        Toasty.success(WishlistActivity.this,"Item removed from wishlist",Toast.LENGTH_SHORT).show();

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

    public class HitProductDetail extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProductDetail(productId, ProductCategory,ProductName,CatId,"getProductdetail");

          //  displayText = WebService.GetProductDetail(productId,pref.get(AppSettings.District),"getProductdetail");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("getProductResponse")) {
                        JSONArray  jsonResponse = jsonObject.getJSONArray("getProductResponse");


                        Intent i = new Intent(WishlistActivity.this, DrawerActivity.class);
                        i.putExtra("page", "wishlist");
                        startActivity(i);

                        AppSettings.fromPage="2";
                       // replaceFragmentWithAnimation(new ProductDescriptionFragment());


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

    public void replaceFragmentWithAnimation(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      ///  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

}
