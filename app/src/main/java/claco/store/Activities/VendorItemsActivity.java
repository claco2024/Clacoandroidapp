package claco.store.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class VendorItemsActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    GridLayoutManager mGridLayoutManager;


    CustomLoader loader;

    ImageView iv_menu;

    TextView tvHeaderText;


    ArrayList<SectionDataModel> allSampleData=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_items);

        iv_menu=findViewById(R.id.iv_menu);

        tvHeaderText=findViewById(R.id.tvHeaderText);

        tvHeaderText.setText("All Products");

        recyclerview = findViewById(R.id.recyclerview);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(mGridLayoutManager);


        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VendorItemsActivity.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //custom loader
        loader = new CustomLoader(VendorItemsActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        if (Utils.isNetworkConnectedMainThred(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);

            AppSettings.statusvendor="2";
            HitVendorProducts product = new HitVendorProducts();
            product.execute();
        }
        else {
            Toasty.error(this, "No internet access", Toast.LENGTH_SHORT, true).show();
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
                    RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(VendorItemsActivity.this, allSampleData);
                    recyclerview.setLayoutManager(new LinearLayoutManager(VendorItemsActivity.this, LinearLayoutManager.VERTICAL, false));
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

       Intent i = new Intent(this, DrawerActivity.class);
        i.putExtra("page", "home");
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
