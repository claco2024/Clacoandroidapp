package claco.store.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.imagezoom.ImageAttacher;
import claco.store.ProductFragment.ProductDescriptionFragment;
import claco.store.R;
import claco.store.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryActivity extends AppCompatActivity {


    ViewPager myviewpager;

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery2);

        myviewpager = findViewById(R.id.myviewpager);


        arrayList.clear();


        arrayList= ProductDescriptionFragment.imagesList;

        //Back
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        replaceFragmentWithAnimation(new ProductDescriptionFragment());
//                        DrawerActivity.llmain.setVisibility(View.VISIBLE);
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        TabLayout tabLayout = (TabLayout)  findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(myviewpager, true);

        CardPagerAdapter1 mCardAdapter = new  CardPagerAdapter1(getApplicationContext(), arrayList) {
            @Override
            protected void onCategoryClick(View view, String str) {
                //  Log.e("***SliderId", sliderList.get(Integer.parseInt(str)).get("SliderId"));
            }
        };

        myviewpager.setAdapter(mCardAdapter);
        myviewpager.setOffscreenPageLimit(2);
        myviewpager.setClipToPadding(false);
        //mViewPager.setPadding(40, 0, 40, 0);
        myviewpager.setCurrentItem(0, true);
        myviewpager.setPageMargin(10);

    }
    public void usingSimpleImage(ImageView imageView) {
        ImageAttacher mAttacher = new ImageAttacher(imageView);
        ImageAttacher.MAX_ZOOM = 4.0f; // times the current Size
        ImageAttacher.MIN_ZOOM = 1.0f; // Half the current Size
        MatrixChangeListener mMaListener = new  MatrixChangeListener();
        mAttacher.setOnMatrixChangeListener(mMaListener);
       PhotoTapListener mPhotoTap = new  PhotoTapListener();
        mAttacher.setOnPhotoTapListener(mPhotoTap);
    }

    private class MatrixChangeListener implements ImageAttacher.OnMatrixChangedListener {
        @Override
        public void onMatrixChanged(RectF rect) {

        }
    }

    private class PhotoTapListener implements ImageAttacher.OnPhotoTapListener {
        @Override
        public void onPhotoTap(View view, float x, float y) {

            // slide();
        }
    }

    public abstract class CardPagerAdapter1 extends PagerAdapter implements CardAdapter {

        private Context mContext;
        private float mBaseElevation;
        public View.OnClickListener clickListener;
        ArrayList<HashMap<String,String>> arrayList;
        //int[] array;

        protected abstract void onCategoryClick(View view, String str);

//        public CardPagerAdapter1(Context context, int[] array) {
//            mContext = context;
//            this.array = array;
//            clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onCategoryClick(v, String.valueOf(v.getTag()));
//                }
//            };
//        }


        public CardPagerAdapter1(Context context,  ArrayList<HashMap<String,String>> arrayList) {
            mContext = context;
            this.arrayList = arrayList;
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryClick(v, String.valueOf(v.getTag()));
                }
            };
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
            container.addView(view);
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            final ImageView iv = (ImageView) view.findViewById(R.id.iv);
            if (this.mBaseElevation == 0.0f) {
                this.mBaseElevation = cardView.getCardElevation();
            }
            usingSimpleImage(iv);

            //  Glide.with(this.mContext).load((String) ((HashMap) this.arrayList.get(position)).get("SliderImg")).into(iv);

//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
//        int size = (int) Math.ceil(Math.sqrt(786432.0d));

            //iv.setImageResource(a[position]);


            try
            {
                Glide.with(mContext).load((String) ((HashMap) arrayList.get(position)).get("ImageURL")).into(iv);

            }
            catch (Exception e)
            {
                Log.e("ee",e.toString());
            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {


                    usingSimpleImage(iv);

                }
            }, 100);


            cardView.setMaxCardElevation(this.mBaseElevation * ((float) this.arrayList.size()));

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
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }
}