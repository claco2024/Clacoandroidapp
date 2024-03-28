package claco.store.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import claco.store.APi.ApiClent;
import claco.store.APi.ApiResponse.ProductsLists;
import claco.store.APi.ApiResponse.Proitemnew;
import claco.store.ApiUtils.APiUtils;
import claco.store.R;
import claco.store.utils.WebService;

public class OurProductAdapter extends RecyclerView.Adapter<OurProductAdapter.MyViewHolder> {
    Context context;

    private List<Proitemnew> productsLists;

    APiUtils.ApiCallBAck apiCallBAck;


    public OurProductAdapter(Context context, List<Proitemnew> productsLists,APiUtils.ApiCallBAck apiCallBAck) {
        this.context = context;
        this.productsLists = productsLists;
        this.apiCallBAck = apiCallBAck;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_list_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvProductName.setText(productsLists.get(position).getProductName());
        holder.tvFinalprice.setText(productsLists.get(position).getSalePrice());

        holder.tvOldPrice.setText(productsLists.get(position).getRegularPrice());
        holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context)
                .load(WebService.imageURL + productsLists.get(position).getProductMainImageUrl())
                .fitCenter()
                .into(holder.ivProductImg);

        holder.itemView.setOnClickListener(view -> apiCallBAck.onSuccess(productsLists.get(position)));


        double retailprice = Double.parseDouble(productsLists.get(position).getRegularPrice());

        double saving = Double.parseDouble(productsLists.get(position).getRegularPrice()) -
                Double.parseDouble(productsLists.get(position).getSalePrice());
        if (productsLists.get(position).getSalePrice().equalsIgnoreCase(productsLists.get(position).getRegularPrice())
                || productsLists.get(position).getRegularPrice().equalsIgnoreCase("0.0")
                || productsLists.get(position).getRegularPrice().equalsIgnoreCase("0.00")
                || productsLists.get(position).getRegularPrice().equalsIgnoreCase("00.00")
        ) {
            holder.tvOldPrice.setVisibility(View.GONE);
        }
        try {
            if (retailprice != 0) {
                double res = (saving / retailprice) * 100;
                roundTwoDecimals(res);
                int i2 = (int) Math.round(roundTwoDecimals(res));
                if (i2 > 0) {
                    holder.tvDiscount.setText(String.valueOf(i2) + " % off");
                    holder.llDiscount.setVisibility(View.VISIBLE);
                } else {
                    holder.llDiscount.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
        }
    }
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.valueOf(twoDForm.format(d));
    }
    @Override
    public int getItemCount() {
        return productsLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvFinalprice,tvOldPrice,tvDiscount;
        TextView tvSave;
        RelativeLayout llDiscount;
        ImageView ivProductImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFinalprice = itemView.findViewById(R.id.tvFinalprice);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvDiscount=itemView.findViewById(R.id.tvDiscount);
            llDiscount=itemView.findViewById(R.id.llDiscount);
            tvSave=itemView.findViewById(R.id.tvSave);
            ivProductImg=itemView.findViewById(R.id.ivProduct);
            tvOldPrice=itemView.findViewById(R.id.tvOldPrice);
        }
    }
}
