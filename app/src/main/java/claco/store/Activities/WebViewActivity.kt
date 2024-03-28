package claco.store.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import claco.store.databinding.ActivityWebViewBinding
import com.vivafresh.helpers.Constants

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private var isLoaded: Boolean = false
    private var doubleBackToExitPressedOnce = false
    var url = Constants.url

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        url = intent.getStringExtra("url").toString()
        loadWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true;
        binding.webView.settings.allowContentAccess = true;
        binding.webView.settings.allowFileAccess = true;
        binding.webView.settings.builtInZoomControls = false
        binding.webView.settings.displayZoomControls = false
        binding.webView.settings.cacheMode =
            if (isNetworkConnected()) WebSettings.LOAD_NO_CACHE else WebSettings.LOAD_CACHE_ONLY

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                isLoaded = true
                binding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                isLoaded = false
                val errorMessage = "Got Error! $error"
                showToast(errorMessage)
                binding.progressBar.visibility = View.GONE
                super.onReceivedError(view, request, error)
            }
        }

        binding.webView.loadUrl(url)
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    showToastToExit()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showToastToExit() {
        when {
            doubleBackToExitPressedOnce -> {
                onBackPressed()
                finish()
            }
            else -> {
                doubleBackToExitPressedOnce = true
                showToast("Press back again to exit")
                Handler(Looper.myLooper()!!).postDelayed(
                    {
                        doubleBackToExitPressedOnce = false
                        finish()
                    },
                    2000
                )
            }
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        if (isOnline() && !isLoaded) loadWebView()
        super.onResume()
    }
}