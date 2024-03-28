package claco.store.Activities;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.adapters.ListAdapter;

import claco.store.databinding.ActivitySearchBinding;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Model;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding activitySearchBinding;
    ListAdapter adapter;
    List<String> arrayList= new ArrayList<>();
    String SubcatId;
    CustomLoader loader;
    Preferences pref;
    public static JSONArray jsonResponse;

    String Search;

    ArrayList<HashMap<String,String>> arrayMap=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
         pref=new Preferences(this);

        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        activitySearchBinding.search.setActivated(true);
        activitySearchBinding.search.setQueryHint("Search here...");
        activitySearchBinding.search.onActionViewExpanded();
        activitySearchBinding.search.setIconified(false);
        activitySearchBinding.search.clearFocus();
        activitySearchBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Search = query;
                SubcatId = "";


                AppSettings.subcatname = Search;

                if (Utils.isNetworkConnectedMainThred(SearchActivity.this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(true);

                    HitProducts products = new HitProducts();
                    products.execute();

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.Filter(newText);
                return false;
            }
        });



        if (Utils.isNetworkConnectedMainThred(SearchActivity.this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);

            HitMainCat cat = new HitMainCat();
             cat.execute();

             HitSearch search = new HitSearch();
             search.execute();


             HitProducts1 search1 = new HitProducts1();
             search1.execute();
        }
        else {
            Toasty.error(SearchActivity.this, "No internet access", Toast.LENGTH_SHORT, true).show();
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

    //********************************Web Services************************//
    public class HitSearch extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetSearchSubcategories("getsubCategoryList");
              Log.e("displaySearch", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //loader.dismiss();
            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("subCategoryResponse")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("subCategoryResponse");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            Model model=new Model();
                            JSONObject object = jsonResponse.getJSONObject(i);
                            model.setId(object.getString("SubCategoryId"));
                            model.setName(object.getString("SubCategoryName"));
                           // activitySearchBinding.setModel(model);
                           // arrayList.add(model);
                            arrayList.add(object.getString("SubCategoryName"));



                            map.put("ProductId",object.getString("SubCategoryId"));
                            map.put("ProductTitle",object.getString("SubCategoryName"));
                            map.put("status", "1");


                            arrayMap.add(map);
                        }

                        adapter= new ListAdapter(arrayList);

                    }
                    activitySearchBinding.listView.setAdapter(adapter);

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

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    arrayList.clear();
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("Response")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject object = jsonResponse.getJSONObject(i);

                            map.put("ProductTitle", object.getString("MainCategoryName"));
                            map.put("ProductId", object.getString("MainCategoryId"));
                            map.put("status", "2");
                            arrayMap.add(map);
                            arrayList.add(object.getString("MainCategoryName"));

                            // arrayList.add(map);
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

    //***********************************API****************************************************//
    public class HitProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

         //   Log.v("subcatid1",SubcatId);
          //  Log.v("subcatid2",Search);

            displayText = WebService.GetProduct(SubcatId,"1",pref.get(AppSettings.District),Search,"GetProductNew");

            Log.e("displaytext12345", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    // arrayList.clear();
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("getProductResponse")) {
                        jsonResponse = jsonObject.getJSONArray("getProductResponse");
                        AppSettings.productFrom="2";
                        startActivity(new Intent(SearchActivity.this, DrawerActivity.class).putExtra("page","search"));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        loader.dismiss();

                        }

                    loader.dismiss();

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


    public class HitProducts1 extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProduct("","3",pref.get(AppSettings.District),"","GetProductNew");

            Log.e("displaytext12345", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    // arrayList.clear();
                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("getProductResponse")) {
                      JSONArray  jsonResponse = jsonObject.getJSONArray("getProductResponse");

                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject object = jsonResponse.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();

                            Log.e("displaytext12345", object.getString("ProductTitle"));

                            map.put("ProductId", object.getString("ProductId"));
                            map.put("ProductTitle", object.getString("ProductTitle"));
                            map.put("status", "2");
                            arrayMap.add(map);
                            arrayList.add(object.getString("ProductTitle"));

                        }
                        adapter= new ListAdapter(arrayList);
                        activitySearchBinding.listView.setAdapter(adapter);

                    }

                    loader.cancel();

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
       /// transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }


}