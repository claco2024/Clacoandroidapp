package claco.store.adapters;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.imagezoom.ImageAttacher;
import claco.store.R;

import java.util.ArrayList;


public abstract class BannerAdapter extends PagerAdapter implements CardAdapter {

    private Context mContext;
    private float mBaseElevation;
    public View.OnClickListener clickListener;
    ArrayList<  String>   arraylist;

    int [] array;

    protected abstract void onCategoryClick(View view, String str);


    public BannerAdapter(Context context, ArrayList< String> arraylist) {
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

          //  Glide.with( mContext).load(   arraylist.get(position)  ).into(iv);

        Glide.with(this.mContext)
                .load(/*arraylist.get(position)*/"")

                .placeholder(R.drawable.placeholder11)
                .error(R.drawable.placeholder11)
                .into(iv);

        cardView.setMaxCardElevation(this.mBaseElevation * ((float) this.arraylist.size()));

        Toast.makeText(this.mContext, ""+arraylist.get(position),Toast.LENGTH_LONG).show();
        Log.v("Glide",""+arraylist.get(position));
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
