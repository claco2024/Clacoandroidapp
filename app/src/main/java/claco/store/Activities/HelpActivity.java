package claco.store.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import claco.store.databinding.ActivityHelpBinding;
import claco.store.utils.AppSettings;
import claco.store.utils.Preferences;
import com.vivafresh.helpers.Constants;

public class HelpActivity extends AppCompatActivity {
    private ActivityHelpBinding binding;
    Preferences pref;
    String mobileNo = "1234567890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = new Preferences(this);

        binding.ivBack.setOnClickListener(view -> finish());

        binding.mobileNo.setText("+91 " + mobileNo);
        binding.btnHelpline.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mobileNo));
            startActivity(intent);
        });

        binding.btnEmail.setOnClickListener(v -> {
            try {
                String body = "My details :" + pref.get(AppSettings.firstName) + "\n" + pref.get(AppSettings.Phone1) + "\n" + pref.get(AppSettings.Email) + "\n";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:claco@support.in?subject=" + "support" + "&body=" + body);
                intent.setData(data);
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvAbout.setOnClickListener(view -> {
            Intent i = new Intent(HelpActivity.this, WebViewActivity.class);
            i.putExtra("url", Constants.INSTANCE.getAbout());
            startActivity(i);
        });

        binding.tvTerm.setOnClickListener(view -> {
            Intent i = new Intent(HelpActivity.this, WebViewActivity.class);
            i.putExtra("url", Constants.INSTANCE.getTerms());
            startActivity(i);
        });

        binding.tvPrivacy.setOnClickListener(view -> {
            Intent i = new Intent(HelpActivity.this, WebViewActivity.class);
            i.putExtra("url", Constants.INSTANCE.getPrivacy());
            startActivity(i);
        });
    }
}