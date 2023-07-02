package mobile.newsapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import mobile.newsapp.data.model.NewsModel
import mobile.newsapp.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val news = intent.getSerializableExtra("news", NewsModel::class.java)
        webViewSetup(news!!.mobile_url)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(url: String) {
        binding.wvMobileUrl.webViewClient = WebViewClient()
        binding.wvMobileUrl.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}