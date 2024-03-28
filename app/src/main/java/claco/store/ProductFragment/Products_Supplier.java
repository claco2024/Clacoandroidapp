package claco.store.ProductFragment;


import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.adapters.CardAdapter;

import com.imagezoom.ImageAttacher;

import java.util.ArrayList;
import java.util.HashMap;

public class Products_Supplier extends Fragment {

    View view;
    RecyclerView recyclerView;
    GridLayoutManager mGridLayoutManager;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_product_listing, container, false);


        //Back
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        replaceFragmentWithAnimation(new ProductSubcategoryFragment());

                        return true;
                    }
                }
                return false;
            }
        });

        recyclerView=view.findViewById(R.id.productRecyclerView);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(new ProductAdapter());
        DrawerActivity.rl_search.setVisibility(View.GONE);
        DrawerActivity.tvHeaderText.setText("Watches");
        DrawerActivity.iv_menu.setImageResource(R.drawable.ic_back);
        DrawerActivity.iv_menu.setTag("arrow");
        DrawerActivity.ivHome.setVisibility(View.GONE);


        return view;
    }

    private class FavNameHolder extends RecyclerView.ViewHolder {

        TextView tvOldPrice;
        LinearLayout layout_item;
        ViewPager myviewpager;

        RecyclerView productRecyclerView1;

        public FavNameHolder(View itemView) {
            super(itemView);
            tvOldPrice=itemView.findViewById(R.id.tvOldPrice);
            layout_item=itemView.findViewById(R.id.layout_item);
            myviewpager=itemView.findViewById(R.id.myviewpager);

            productRecyclerView1=itemView.findViewById(R.id.productRecyclerView1);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<FavNameHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public ProductAdapter()
        {

        }
        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_products_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, int position) {


            CardPagerAdapter1 mCardAdapter = new CardPagerAdapter1(getActivity()) {
                @Override
                protected void onCategoryClick(View view, String str) {
                    //  Log.e("***SliderId", sliderList.get(Integer.parseInt(str)).get("SliderId"));
                }
            };


            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            holder.productRecyclerView1.setLayoutManager(linearLayoutManager2);
            holder.productRecyclerView1.setAdapter(new ProductAdapter1());

//            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            holder.layout_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                 //   replaceFragmentWithAnimation(new ProductDescriptionFragment());
//
//                }
//            });

        }

        public int getItemCount() {
            return 6 ;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    public void replaceFragmentWithAnimation(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
      //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    private class FavNameHolder2 extends RecyclerView.ViewHolder {

        LinearLayout layout_item;

        public FavNameHolder2(View itemView) {
            super(itemView);

            layout_item=itemView.findViewById(R.id.layout_item);

        }
    }

    private class ProductAdapter1 extends RecyclerView.Adapter<FavNameHolder2> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public ProductAdapter1(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }
        public ProductAdapter1()
        {

        }
        public FavNameHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavNameHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_supplier_items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FavNameHolder2 holder, int position) {

          holder.layout_item.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  replaceFragmentWithAnimation(new Product_supplier_description());
              }
          });

        }
        public int getItemCount() {
            return 4 ;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public abstract class CardPagerAdapter1 extends PagerAdapter implements CardAdapter {

        private Context mContext;
        private float mBaseElevation;
        public View.OnClickListener clickListener;
        // ArrayList<HashMap<String,String>> array;
        int [] array;

        protected abstract void onCategoryClick(View view, String str);

        public CardPagerAdapter1(Context context, int[] array) {
            mContext = context;
            this.array=array;
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryClick(v, String.valueOf(v.getTag()));
                }
            };
        }

        public CardPagerAdapter1(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_supplier_items, container, false);
            container.addView(view);
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            if (this.mBaseElevation == 0.0f) {
                this.mBaseElevation = cardView.getCardElevation();
            }

            //  Glide.with(this.mContext).load((String) ((HashMap) this.array.get(position)).get("SliderImg")).into(iv);

//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
//        int size = (int) Math.ceil(Math.sqrt(786432.0d));

         //   iv.setImageResource(array[position]);

            cardView.setMaxCardElevation(this.mBaseElevation * ((float) 3));

            view.setTag(position);
            view.setOnClickListener(clickListener);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public float getBaseElevation() {
            return 0;
        }

        @Override
        public CardView getCardViewAt(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void usingSimpleImage(ImageView imageView) {
            ImageAttacher mAttacher = new ImageAttacher(imageView);
            ImageAttacher.MAX_ZOOM = 4.0f; // times the current Size
            ImageAttacher.MIN_ZOOM = 1.0f; // Half the current Size
            MatrixChangeListener mMaListener = new MatrixChangeListener();
            mAttacher.setOnMatrixChangeListener(mMaListener);
        }
        private class MatrixChangeListener implements ImageAttacher.OnMatrixChangedListener {
            @Override
            public void onMatrixChanged(RectF rect) {

            }
        }


    }
}
