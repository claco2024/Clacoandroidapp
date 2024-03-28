package claco.store.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import claco.store.Main.DrawerActivity;
import claco.store.R;
import claco.store.util.CustomLoader;
import claco.store.utils.Utils;
import claco.store.utils.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Membership extends AppCompatActivity {
    TextView tvHeaderText;
    LinearLayout primemember,faimlymember;
    RecyclerView rv_membershiptype;
    ImageView iv_menu;
    CustomLoader loader;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        tvHeaderText=findViewById(R.id.tvHeaderText);
        primemember=findViewById(R.id.primemember);
        faimlymember=findViewById(R.id.faimlymember);
        rv_membershiptype=findViewById(R.id.rv_membershiptype);
        tvHeaderText.setText("MemberShip");
        iv_menu=findViewById(R.id.iv_menu);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Membership.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });
        if (Utils.isNetworkConnectedMainThred(Membership.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(true);
          HitMembershiptype cart = new  HitMembershiptype();
            cart.execute();
        }
        faimlymember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Membership.this, SlctPamentmthd.class);
                i.putExtra("member","faimly");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });
        primemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(Membership.this, SlctPamentmthd.class);
                i.putExtra("member","prime");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, DrawerActivity.class);
        i.putExtra("page", "home");
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }
    public class HitMembershiptype extends AsyncTask<String, Void, Void> {

        String displayText;
        String msg;

        @Override
        protected Void doInBackground(String... params) {


                // displayText = WebService.Checkout(pref.get(AppSettings.CustomerID), "CheckOut", "" + final_del_charges, "COD", ChooseDeliverySpeed.deliveryId,pref.get(AppSettings.District));
                displayText = WebService.GetMembershiptype(  "GetMembershiptype"  );


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            loader.dismiss();
            Log.e("cvvvvvvvv", displayText);

            if (displayText != "" && displayText != null && displayText != "connection fault" && !displayText.contains("recvfrom failed: ECONNRESET (Connection reset by peer)")) {
                try {
                    JSONObject jsonObject = new JSONObject(displayText);

                    JSONArray array = jsonObject.getJSONArray("membetype");



                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                          String  pk_MemberShipId = obj.getString("pk_MemberShipId");
                            String MemberShipTitle = obj.getString("MemberShipTitle");
                            HashMap<String,String> map=new HashMap<>();
                            map.put("pk_MemberShipId",pk_MemberShipId);
                            map.put("MemberShipTitle",MemberShipTitle);

                            //  map.put("RateId", RateId);
                            arrayList.add(map);

                        }
                    rv_membershiptype.setAdapter(new  ProductAdapter(arrayList));
                        Log.e("arrayList","" +arrayList);
 //rv_membershiptype.setAdapter(ProductAdapter);



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
        private class FavNameHolder extends RecyclerView.ViewHolder {
            TextView tv_title;


            public FavNameHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);


            }
        }

        private class ProductAdapter extends RecyclerView.Adapter< FavNameHolder> {
            ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

            public ProductAdapter(ArrayList<HashMap<String, String>> favList) {
                data = favList;
                Log.e("Im","settingmembershipvalues"+data);
            }


            public ProductAdapter() {
            }

            public  FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
               // return new  FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_items, parent, false));
                return new   FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_items, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull final   FavNameHolder holder, final int position) {
                  holder.tv_title.setText(data.get(position).get("MemberShipTitle"));
                 Log.e("Im","settingmembershipvalues");



            }
            public int getItemCount() {
                return data.size();
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

        }
    }

}