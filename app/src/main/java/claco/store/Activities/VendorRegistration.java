package claco.store.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import claco.store.Main.DrawerActivity;

import claco.store.R;

public class VendorRegistration extends AppCompatActivity {


    WebView webView;

    ImageView ivLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        webView = (WebView) findViewById(R.id.webview);

        ivLoader=findViewById(R.id.ivLoader);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new MyBrowser());

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.loadUrl("https://gosocart.com/Vendor/VendorLogin");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mIntent = new Intent(VendorRegistration.this, DrawerActivity.class);
        mIntent.putExtra("page", "home");
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            ivLoader.setVisibility(View.GONE);

            webView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
