package claco.store.adapters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import claco.store.APi.ApiResponse.CategoryIteam;

import claco.store.ApiUtils.APiUtils;
import claco.store.R;

import claco.store.fragments.DashboardFragment;
import claco.store.utils.WebService;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context mContext;
    List<CategoryIteam> response;
    APiUtils.ApiCallBAck apiCallBAck;


    public CategoryAdapter(Context mContext, List<CategoryIteam> response,APiUtils.ApiCallBAck apiCallBAck) {
        this.mContext = mContext;
        this.response = response;
        this.apiCallBAck = apiCallBAck;
    }


    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.category_home_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        holder.catName.setText(response.get(position).getMainCategoryName());

        Glide.with(mContext)
                .load(WebService.imageURL + response.get(position).getMainCategoryImage())
                .fitCenter()
                .into(holder.catImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


apiCallBAck.onSuccess(response.get(position));



            }
        });
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catName;
        ImageView catImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.tvCatName);
            catImage=itemView.findViewById(R.id.catImage);
        }
    }
}
