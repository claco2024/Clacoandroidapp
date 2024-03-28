package claco.store.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import claco.store.Main.DrawerActivity;
import claco.store.Main.LoginActivity;
import claco.store.R;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;


public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    ImageView ivImage;

    TextView tvName;
    TextView tvPhone;
    TextView tvEmail;
    ConstraintLayout tvViewOrder, tvWallet, tvAddr;
    TextView tvViewWishlist;
    TextView tvViewAddress;

    Preferences pref;

    ImageView rlEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        rlEdit=findViewById(R.id.rlEdit);
        rlEdit.setOnClickListener(this);

        pref=new Preferences(this);

        ivImage=findViewById(R.id.ivImage);
        tvName=findViewById(R.id.tvName);
        tvPhone=findViewById(R.id.tvPhone);
        tvEmail=findViewById(R.id.tvEmail);
        tvViewOrder=findViewById(R.id.tvViewOrder);
        tvViewOrder.setOnClickListener(this);

        tvWallet=findViewById(R.id.tvWallet);
        tvViewWishlist=findViewById(R.id.tvViewWishlist);
        tvViewAddress=findViewById(R.id.tvViewAddress);
        tvWallet.setOnClickListener(this);
        tvViewWishlist.setOnClickListener(this);
        tvViewAddress.setOnClickListener(this);
        tvAddr= findViewById(R.id.tvAddr);
        tvAddr.setOnClickListener(this);

        //Setting text

        tvName.setText(pref.get(AppSettings.firstName));
        tvPhone.setText(pref.get(AppSettings.Phone1));
        tvEmail.setText(pref.get(AppSettings.Email));


        if(!(pref.get(AppSettings.Gender).equalsIgnoreCase("")))
        {
            if(pref.get(AppSettings.Gender).equalsIgnoreCase("Male"))
            {
                ivImage.setImageResource(R.drawable.boy);
            }
            else
            {
                ivImage.setImageResource(R.drawable.girl);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MyAccountActivity.this, DrawerActivity.class);
        i.putExtra("page", "home");
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.ivBack:

                Intent i = new Intent(MyAccountActivity.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;


            case R.id.tvViewOrder:
                i = new Intent(MyAccountActivity.this, DrawerActivity.class);
                i.putExtra("page", "account");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;

            case R.id.rlEdit:
                if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    i = new Intent(MyAccountActivity.this, ProfilePageActivity.class);
                    startActivity(i);
                }
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;


            case R.id.tvWallet:

                if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                else
                {
                    i = new Intent(MyAccountActivity.this, WalletActivity.class);
                    startActivity(i);
                }

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;


            case R.id.tvViewWishlist:

                if (pref.get(AppSettings.CustomerID).equalsIgnoreCase("")) {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                else
                {
                    i = new Intent(MyAccountActivity.this, WishlistActivity.class);
                    startActivity(i);
                }
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;


            case R.id.tvViewAddress:
                i = new Intent(MyAccountActivity.this, AddressListActivity.class);
                startActivity(i);
                break;

            case R.id.tvAddr:
                startActivity(new Intent(MyAccountActivity.this, SelectAddressPage.class));
//                startActivity(new Intent(MyAccountActivity.this, AddAddressActivity.class));
                break;
        }
    }
}
