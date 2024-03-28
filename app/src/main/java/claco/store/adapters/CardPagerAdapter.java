package claco.store.adapters;


import android.content.Context;
import android.graphics.RectF;
import androidx.viewpager.widget.PagerAdapter;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import claco.store.R;
import com.imagezoom.ImageAttacher;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private Context mContext;
    private float mBaseElevation;
    public View.OnClickListener clickListener;
    ArrayList<HashMap<String,String>> arraylist;

    int [] array;

    protected abstract void onCategoryClick(View view, String str);


    public CardPagerAdapter(Context context, ArrayList<HashMap<String,String>> arraylist) {
        mContext = context;
        this.arraylist=arraylist;


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
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        if (this.mBaseElevation == 0.0f) {
            this.mBaseElevation = cardView.getCardElevation();
        }

   //     Glide.with(this.mContext).load((String) ((HashMap) this.arraylist.get(position)).get("BannerImage")).into(iv);

        Glide.with(this.mContext)
                .load(arraylist.get(position).get("BannerImages"))
                .placeholder(R.drawable.placeholder11)
                .error(R.drawable.placeholder11)
                .into(iv);

        cardView.setMaxCardElevation(this.mBaseElevation * ((float) this.arraylist.size()));

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
        return arraylist.size();
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
