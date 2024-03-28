package claco.store.ProductFragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class CategoriesFragment extends Fragment {

    View view;

    GridLayoutManager mGridLayoutManager;
    RecyclerView recyclerView;

    CustomLoader loader;

    String mainCatId;

    public static JSONObject jsoncategory;;

    //Arraylist
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    String[] name = {"Women", "Men", "Kids", "Electronics", "Beauty", "Sports", "Others"};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maincategory, container, false);

        //custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        DrawerActivity.ivHome.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recyclerView);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(new ProductAdapter());
        DrawerActivity.tvHeaderText.setText("Categories");
        DrawerActivity.rl_search.setVisibility(View.GONE);

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


        //Hitting API
        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
            new HitMainCat().execute();
        }
        else {
            Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
        }
        return view;
    }


    private class FavNameHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvCategory;
        TextView tvProductsCount;
        RelativeLayout rlMain;

        public FavNameHolder(View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvProductsCount = itemView.findViewById(R.id.tvProductsCount);
            rlMain = itemView.findViewById(R.id.rlMain);
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

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorypage_list, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull FavNameHolder holder, final int position) {

            Glide.with(getActivity()).load((String) ((HashMap) arrayList.get(position)).get("mainCategoryImage")).centerCrop().placeholder(R.drawable.placeholder11).into(holder.ivCategory);
            holder.tvCategory.setText(arrayList.get(position).get("MainCategoryName"));
            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mainCatId=arrayList.get(position).get("MainCategoryId");
                    AppSettings.status="2";
                    AppSettings.catname=arrayList.get(position).get("MainCategoryName");
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        HitCategory cat=new HitCategory();
                        cat.execute();
                    }
                    else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }


                }
            });
        }


        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
       // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
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

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    // arrayList.clear();

                    JSONObject jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("Response")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("Response");

                        for (int i = 0; i < jsonResponse.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject object = jsonResponse.getJSONObject(i);
                            String completeUrl = WebService.imageURL + object.getString("mainCategoryImage");
                            map.put("MainCategoryName", object.getString("MainCategoryName"));
                            map.put("mainCategoryImage", completeUrl);
                            map.put("MainCategoryId",object.getString("MainCategoryId"));
                            arrayList.add(map);
                        }

                        recyclerView.setAdapter(new ProductAdapter(arrayList));
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
    public class HitCategory extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.Getcategory(mainCatId,"getCategory");

            Log.e("disssssss123",displayText);

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

          //  AppSettings.status="1";

            replaceFragmentWithAnimation(new ProductSubcategoryFragment());

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    jsoncategory = new JSONObject(displayText);
                }
                catch (Exception e) {
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
