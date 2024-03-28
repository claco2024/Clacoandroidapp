package claco.store.ProductFragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.fragments.DashboardFragment;
import claco.store.util.CustomLoader;
import claco.store.utils.AppSettings;
import claco.store.utils.ExpandableHeightGridView;
import claco.store.utils.Preferences;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ProductSubcategoryFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    GridLayoutManager mGridLayoutManager;
    NestedScrollView scrollview;
    JSONObject jsonObject;

    CustomLoader loader;

    String catId;
    String catName;

    String identifier;

    String subCatId;
    Preferences pref;

    public static JSONArray jsonResponse;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arraySubcat = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_category, container, false);
        //custom loader
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        pref = new Preferences(getActivity());
        recyclerView = v.findViewById(R.id.recyclerView);
        scrollview = v.findViewById(R.id.scrollview);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mGridLayoutManager);

        DrawerActivity.tvHeaderText.setText(AppSettings.catname);
        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.iv_menu.setTag("arrow");
        DrawerActivity.ivHome.setVisibility(View.GONE);
        DrawerActivity.rl_search.setVisibility(View.GONE);
        DrawerActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppSettings.status.equalsIgnoreCase("1")) {
                    if (AppSettings.dashboard.equalsIgnoreCase("1")) {
                        replaceFragmentWithAnimation(new DashboardFragment());
                    } else {
                        Intent intent = new Intent(getActivity(), DrawerActivity.class);
                        intent.putExtra("page", "home");
                        startActivity(intent);
                    }

                } else {

                    Intent intent = new Intent(getActivity(), DrawerActivity.class);
                    intent.putExtra("page", "cat");
                    startActivity(intent);
                    //replaceFragmentWithAnimation(new CategoriesFragment());
                }

            }
        });


        if (AppSettings.status.equalsIgnoreCase("1")) {
            jsonObject = DashboardFragment.jsoncategory;
        } else {
            jsonObject = CategoriesFragment.jsoncategory;
        }

        try {
            if (jsonObject.has("subCategoryResponse")) {
                JSONArray array = jsonObject.getJSONArray("subCategoryResponse");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    String completeUrl = WebService.imageURL + obj.getString("SubImage");
                    //Log.e("completeUrl",completeUrl);
                    map.put("CategoryName", obj.getString("SubCategoryName"));
                    map.put("CategoryId", obj.getString("SubCategoryId"));
                    map.put("Description", obj.getString("Description"));
                    map.put("CategoryImage", completeUrl);
                    arrayList.add(map);
                }
                recyclerView.setAdapter(new ProductAdapter(arrayList));
            } else {
                JSONArray array = jsonObject.getJSONArray("CatResponse");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    String completeUrl = WebService.imageURL + obj.getString("CategoryImage");
                    Log.e("completeUrl", completeUrl);
                    map.put("CategoryName", obj.getString("CategoryName"));
                    map.put("CategoryId", obj.getString("CategoryId"));
                    map.put("Description", obj.getString("Description"));
                    map.put("CategoryImage", completeUrl);
                    arrayList.add(map);
                }
                recyclerView.setAdapter(new ProductAdapter(arrayList));
            }
        }


        //{
        //  "": [
        //    {
        //      "SubCategoryId": 6,
        //      "SubCategoryName": "BATH & HAND WASH",
        //      "SubImage": "",
        //      "Description": ""
        //    }
        //  ]
        //}
        catch (Exception e) {

        }

        //Back
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        if (AppSettings.status.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(getActivity(), DrawerActivity.class);
                            intent.putExtra("page", "home");
                            startActivity(intent);
                        } else {
                            replaceFragmentWithAnimation(new CategoriesFragment());
                        }

                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public class HitSubcat extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        FavNameHolder holder;

        public HitSubcat(FavNameHolder holder) {
            this.holder = holder;
        }

        @Override
        protected Void doInBackground(String... params) {

            identifier = params[0];

            displayText = WebService.GetSubcat(catId, "getsubCategory");
            Log.e("Subcat", displayText);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();

            arraySubcat.clear();

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject object = new JSONObject(displayText);
                    JSONArray array = object.getJSONArray("subCategoryResponse");
                    for (int i = 0; i < array.length(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        JSONObject obj = array.getJSONObject(i);
                        String completeUrl = WebService.imageURL + obj.getString("SubImage");
                        Log.e("SubCategoryName", obj.getString("SubCategoryName"));
                        map.put("SubImage", completeUrl);
                        map.put("SubCategoryName", obj.getString("SubCategoryName"));
                        map.put("SubCategoryId", obj.getString("SubCategoryId"));
                        arraySubcat.add(map);
                    }

                    holder.adapter = new CustomGrid(getActivity(), arraySubcat);
                    holder.mGridView.setAdapter(holder.adapter);
                    holder.mGridView.setExpanded(true);
                    holder.adapter.notifyDataSetChanged();
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

    private class FavNameHolder extends RecyclerView.ViewHolder {

        GridView grid;
        ExpandableHeightGridView mGridView;
        RelativeLayout relGrid;
        ImageView ivArrow;
        LinearLayout llMain;
        TextView tvCategory;
        TextView tvDescription;


        CustomGrid adapter;

        ImageView ivCatImage;

        public FavNameHolder(View itemView) {
            super(itemView);
            grid = itemView.findViewById(R.id.grid);
            mGridView = itemView.findViewById(R.id.spotsView);
            relGrid = itemView.findViewById(R.id.relGrid);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            llMain = itemView.findViewById(R.id.llMain);

            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivCatImage = itemView.findViewById(R.id.ivCatImage);

            adapter = new CustomGrid();
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
            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subcat_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {

            String imageurl = data.get(position).get("CategoryImage");

            Log.e("caaaa", imageurl);

            Glide.with(getActivity()).load(imageurl).placeholder(R.drawable.placeholder11).into(holder.ivCatImage);

            holder.tvCategory.setText(data.get(position).get("CategoryName"));

            holder.tvDescription.setText(data.get(position).get("Description"));

            holder.llMain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    catId = data.get(position).get("CategoryId");
                    catName = data.get(position).get("CategoryName");
                    Log.v("catidvalue", catId);
                    AppSettings.subcatname = data.get(position).get("CategoryName");
                    //Hitting API
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitProducts().execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }


                    //   scrollToView(scrollview,holder.mGridView);
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

    public void setView(FavNameHolder holder) {
        CustomGrid adapter = new CustomGrid(getActivity(), arraySubcat);
        holder.mGridView.setAdapter(adapter);
        holder.mGridView.setExpanded(true);
        adapter.notifyDataSetChanged();

    }

    public class CustomGrid extends BaseAdapter {

        private Context mContext;
        CardView cardMain;

        ArrayList<HashMap<String, String>> data;

        public CustomGrid(Context c, ArrayList<HashMap<String, String>> arrayList) {
            mContext = c;
            data = arrayList;
        }

        public CustomGrid() {

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;

            TextView tvProductname;
            ImageView ivProductimage;

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_single, null);
                cardMain = grid.findViewById(R.id.cardMain);
                ivProductimage = grid.findViewById(R.id.ivProductimage);
                tvProductname = grid.findViewById(R.id.tvProductname);
                tvProductname.setText(data.get(position).get("SubCategoryName"));
                Glide.with(getActivity()).load((String) ((HashMap) data.get(position)).get("SubImage")).placeholder(R.drawable.placeholder11).into(ivProductimage);
            } else {
                grid = (View) convertView;
            }

            cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // replaceFragmentWithAnimation(new Products_Supplier());


                    subCatId = data.get(position).get("SubCategoryId");

                    AppSettings.subcatname = data.get(position).get("SubCategoryName");

                    //Hitting API
                    if (Utils.isNetworkConnectedMainThred(getActivity())) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(true);
                        new HitProducts().execute();
                    } else {
                        Toasty.error(getActivity(), "No internet access", Toast.LENGTH_SHORT, true).show();
                    }

                }
            });

            return grid;
        }
    }


    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);


    }

    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);


    }

    private void scrollToView(final NestedScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }


    //***********************************API****************************************************//
    public class HitProducts extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProduct(catName, "1", pref.get(AppSettings.District), "", "GetProductNew");

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

                        AppSettings.productFrom = "1";
                        replaceFragmentWithAnimation(new ProductListingFragment());

                        loader.dismiss();

//                        for (int i = 0; i < jsonResponse.length(); i++) {
//                            HashMap<String, String> map = new HashMap<>();
//                            JSONObject object = jsonResponse.getJSONObject(i);
////                            String completeUrl = WebService.imageURL + object.getString("mainCategoryImage");
////                            map.put("MainCategoryName", object.getString("MainCategoryName"));
////                            map.put("mainCategoryImage", completeUrl);
////                            arrayList.add(map);
//
//                        }

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
