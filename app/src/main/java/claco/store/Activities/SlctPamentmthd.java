package claco.store.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import claco.store.Main.DrawerActivity;
import claco.store.R;

public class SlctPamentmthd extends AppCompatActivity {
    ImageView iv_menu; TextView tvSubmit;
    RelativeLayout rrOnline;
    ImageView ivOnline,ivCash;
    String PaymentMode="COD";
    LinearLayout family,prim;
    String prime,faimly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slct_pamentmthd);
        iv_menu=findViewById(R.id.iv_menu);
        rrOnline = findViewById(R.id.rrOnline);
        ivOnline = findViewById(R.id.imageView9);
        ivCash = findViewById(R.id.imageView8);
        family = findViewById(R.id.family);
        prim = findViewById(R.id.prime);
        ivCash = findViewById(R.id.imageView8);
        tvSubmit = findViewById(R.id.tvSubmit);
        Intent i=getIntent();
        prime=i.getStringExtra("member");
       // faimly=i.getStringExtra("member");
        if (prime.equals("faimly"))
        {
family.setVisibility(View.VISIBLE);
prim.setVisibility(View.GONE);
        }else {
            family.setVisibility(View.GONE);
            prim.setVisibility(View.VISIBLE);
        }
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlctPamentmthd.this, DrawerActivity.class);
                i.putExtra("page", "home");
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });
        rrOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PaymentMode.equalsIgnoreCase("COD")) {

                    ivOnline.setImageResource(R.drawable.ic_radio_button_checked);
                   // ivCash.setImageResource(R.drawable.ic_radio_button_unchecked);
                    PaymentMode = "CARD";

                }

            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PaymentMode.equalsIgnoreCase("COD")) {

                    ivOnline.setImageResource(R.drawable.ic_radio_button_checked);
                   // ivCash.setImageResource(R.drawable.ic_radio_button_unchecked);
                    PaymentMode = "CARD";

//MembershipApplication

                }
                startActivity(new Intent(SlctPamentmthd.this, MembershipApplication.class)
                        .putExtra("PaymentMode",PaymentMode)
                        .putExtra("member",prime));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });
    }
}