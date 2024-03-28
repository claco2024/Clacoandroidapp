package claco.store.Activities;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import claco.store.Main.DrawerActivity;

import claco.store.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderConfirmationActivity extends AppCompatActivity {


    TextView tvHeaderText;
    TextView tvViewOrder;

    TextView tvOrderNumber;
    TextView tvDate;
    TextView tvEmail;
    TextView tvPayMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        tvHeaderText=findViewById(R.id.tvHeaderText);
        tvHeaderText.setText("Order Confirmation");

        tvOrderNumber=findViewById(R.id.tvOrderNumber);
        tvEmail=findViewById(R.id.tvEmail);
        tvDate=findViewById(R.id.tvDate);
        tvPayMode=findViewById(R.id.tvPayMode);

        tvEmail.setText(ConfirmAddressActivity.Email);
        tvOrderNumber.setText(ConfirmAddressActivity.OrderId);


        if(ConfirmAddressActivity.Paymode.equalsIgnoreCase("COD"))
        {
            tvPayMode.setText("Cash on delivery");
        }
        else
        {
            tvPayMode.setText("Online Payment");
        }


        tvViewOrder=findViewById(R.id.tvViewOrder);
        tvViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(OrderConfirmationActivity.this, DrawerActivity.class);
//                i.putExtra("page","order");
//                startActivity(i);
//                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_right);
                triggerRebirth(getApplicationContext());
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = sdf.format(c.getTime());

        tvDate.setText(formattedDate + "," +currentDateTimeString);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i=new Intent(this,DrawerActivity.class);
//        i.putExtra("page","home");
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
        triggerRebirth(getApplicationContext());
    }

    public static void triggerRebirth(Context context) {
        Intent intent = new Intent(context, DrawerActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("page", "home");
//        intent.putExtra(KEY_RESTART_INTENT, nextIntent);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finishAffinity();
        }
        Runtime.getRuntime().exit(0);
    }
}
