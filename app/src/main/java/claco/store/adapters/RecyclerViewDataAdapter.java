package claco.store.adapters;



import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import claco.store.DataModel.SectionDataModel;
import claco.store.R;
import claco.store.utils.AppSettings;


import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.supplier_products_items, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        itemRowHolder.tvPlace.setText(dataList.get(i).getCityName() + " , " +dataList.get(i).getStateName());

        Glide.with(mContext).load(dataList.get(i).getStoreImage()).centerCrop().placeholder(R.drawable.shop).into(itemRowHolder.image);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

    }


    @Override
    public int getItemCount() {

        if(AppSettings.statusvendor.equalsIgnoreCase("2"))
        {
            return (null != dataList ? dataList.size() : 0);
        }
        else
        {
            return 3;
        }

    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected TextView tvPlace;

        protected ImageView image;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.tvShopName);
            this.tvPlace = (TextView) view.findViewById(R.id.tvPlace);

            this.image=(ImageView)view.findViewById(R.id.image);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.productRecyclerView1);
            //this.btnMore= (Button) view.findViewById(R.id.btnMore);


        }

    }

}