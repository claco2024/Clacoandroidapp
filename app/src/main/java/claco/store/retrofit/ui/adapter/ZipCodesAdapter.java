

package claco.store.retrofit.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import claco.store.R;
import claco.store.retrofit.ui.model.ZipCodeData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ZipCodesAdapter extends RecyclerView.Adapter<ZipCodesAdapter.ZipCodesViewHolder>{

    private List<ZipCodeData> zipCodeDataList;

    @Inject
    public ZipCodesAdapter() {
        zipCodeDataList = new ArrayList<>();
    }

    public void setZipCodeDataList(List<ZipCodeData> zipCodeDataList) {
        this.zipCodeDataList = zipCodeDataList;
    }

    @Override
    public int getItemCount() {
        return zipCodeDataList.size();
    }

    @Override
    public ZipCodesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.list_item_zip_code, null);
        return new ZipCodesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZipCodesViewHolder holder, int position) {
        holder.bind( zipCodeDataList.get( position ) );
    }

    protected static class ZipCodesViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCityName, tvCityZip, tvCityAreaCode, tvCityState;

        public ZipCodesViewHolder(View itemView) {
            super(itemView);
            tvCityAreaCode= (TextView)itemView.findViewById(R.id.tv_city_area_code);
            tvCityName= (TextView)itemView.findViewById(R.id.tv_city_name);
            tvCityZip= (TextView)itemView.findViewById(R.id.tv_city_zip);
            tvCityState= (TextView)itemView.findViewById(R.id.tv_city_state);
        }

        @SuppressLint("StringFormatInvalid")
        public void bind(ZipCodeData data){

            tvCityName.setText(data.getAreaCode());

            tvCityAreaCode.setText( itemView.getContext().getString(R.string.item_area, data.getAreaCode() ) );

            tvCityZip.setText(itemView.getContext().getString(R.string.item_zip, data.getZipCode() ) );

            tvCityState.setText(itemView.getContext().getString(R.string.item_state, data.getState() ) );
        }

    }

}
