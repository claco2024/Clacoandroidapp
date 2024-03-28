package claco.store.adapters;

import android.content.Context;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.imagezoom.ImageAttacher;
import claco.store.R;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class CardPagerAdapterBottom extends PagerAdapter implements CardAdapter {

    private Context mContext;
    private float mBaseElevation;
    public View.OnClickListener clickListener;
    ArrayList<HashMap<String,String>> arraylist;

    int [] array;

    protected abstract void onCategoryClick(View view, String str);


    public CardPagerAdapterBottom(Context context, ArrayList<HashMap<String,String>> arraylist) {
        mContext = context;
        this.arraylist=arraylist;


        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryClick(v, String.valueOf(v.getTag()));
            }
        };
    }


// map.put("Bannertitle", object.getString("Bannertitle"));
//                            map.put("BannerImage", completeUrl);
//                            map.put("DiscountType", object.getString("DiscountType"));
//                            map.put("DiscountValue", object.getString("DiscountValue"));
//                            map.put("ProductCategory"
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_bottom, container, false);
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv_title =  view.findViewById(R.id.tv_cate);
        TextView title =  view.findViewById(R.id.title);
        if (arraylist.get(position).get("ProductCategory").isEmpty()){
            tv_title.setVisibility(View.GONE);
        }else {
            tv_title.setText(arraylist.get(position).get("ProductCategory"));
        }
        if (arraylist.get(position).get("Bannertitle").isEmpty()){
            title.setVisibility(View.GONE);
        }else {
            title.setText(arraylist.get(position).get("Bannertitle"));
        }

        TextView percent = view.findViewById(R.id.percent);
        if (arraylist.get(position).get("DiscountType").equals("Percent")){
            percent.setText(arraylist.get(position).get("DiscountValue")+" % OFF");
        } else if (arraylist.get(position).get("DiscountType").equals("Amount")){
            percent.setText(" Rs "+ arraylist.get(position).get("DiscountValue")+" OFF");
        }


        if (this.mBaseElevation == 0.0f) {
            this.mBaseElevation = cardView.getCardElevation();
        }

        //     Glide.with(this.mContext).load((String) ((HashMap) this.arraylist.get(position)).get("BannerImage")).into(iv);

        Glide.with(this.mContext)
                .load(arraylist.get(position).get("BannerImage"))
//                .apply(new RequestOptions().override(720, 300))
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
