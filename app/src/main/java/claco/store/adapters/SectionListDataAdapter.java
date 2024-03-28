package claco.store.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import claco.store.DataModel.SingleItemModel;
import claco.store.ProductFragment.ProductDescriptionFragment;
import claco.store.R;
import claco.store.utils.AppSettings;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    private Context mContext;

    private ArrayList<SingleItemModel> itemsList;

    String productId,   ProductCategory, ProductName , CatId ;

    public static JSONObject jsonObject;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_supplier_items, parent, false);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        final SingleItemModel singleItem = itemsList.get(i);
        holder.tvTitle.setText(singleItem.getName());
        holder.tvFinalprice.setText("\u20b9" + singleItem.getSalePrice() + " per pc");
        holder.tvMinQty.setText("\u2265 " + singleItem.getMinimumQuantity());
        Glide.with(mContext).load(itemsList.get(i).getMainPicture()).centerCrop().placeholder(R.drawable.placeholder11).into(holder.itemImage);


        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productId=singleItem.getProductId();

//                ProductCategory = data.get(position).get("ProductCategory");
//                ProductName = data.get(position).get("ProductName");
//                CatId = data.get(position).get("MainCategoryCode");

                if (Utils.isNetworkConnectedMainThred((Activity)mContext)) {
                    new HitProductDetail().execute();
                } else {
                    Toasty.error(((Activity)mContext), "No internet access", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse (""))
                .buildShortDynamicLink()
                // .addOnCompleteListener(  MainActivity.this, new OnCompleteListener<ShortDynamicLink>() {
                // .addOnCompleteListener(    getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                // .addOnCompleteListener(    (Activity) holder.itemView.getContext() , new OnCompleteListener<ShortDynamicLink>() {
                .addOnCompleteListener((Activity) holder.itemView.getContext(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                            whatsappIntent.setType("text/plain");
                            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString() + " Download the   App  and use the above referral code to earn money");
                            holder.itemView.getContext() .startActivity(Intent.createChooser(whatsappIntent, "Share using"));
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Try again", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle;
        protected TextView tvFinalprice;
        protected TextView tvMinQty;
        protected ImageView itemImage;


        protected  LinearLayout layout_item;

        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvProductName);
            this.tvMinQty = (TextView) view.findViewById(R.id.tvMinQty);
            this.tvFinalprice = (TextView) view.findViewById(R.id.tvFinalprice);
            this.itemImage = (ImageView) view.findViewById(R.id.ivProduct);
            this.layout_item=(LinearLayout) view.findViewById(R.id.layout_item);
        }
    }

    public class HitProductDetail extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {

            displayText = WebService.GetProductDetail(productId, ProductCategory,ProductName,CatId,"getProductdetail");

           // displayText = WebService.GetProductDetail(productId,"", "getProductdetail");

            Log.e("displaytext1234", displayText);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {

                    jsonObject = new JSONObject(displayText);
                    if (jsonObject.has("getProductResponse")) {
                        JSONArray jsonResponse = jsonObject.getJSONArray("getProductResponse");
                        AppSettings.fromPage = "4";
                        replaceFragmentWithAnimation(new ProductDescriptionFragment());

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

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
       // android.app.FragmentTransaction transaction = ((Activity)mContext).getFragmentManager().beginTransaction();
      //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }


}