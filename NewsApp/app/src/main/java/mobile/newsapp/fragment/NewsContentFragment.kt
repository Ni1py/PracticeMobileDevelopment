package mobile.newsapp.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import mobile.newsapp.databinding.FragmentNewsContentBinding
import mobile.newsapp.viewModel.NewsViewModel

class NewsContentFragment : Fragment() {
    private lateinit var binding : FragmentNewsContentBinding
    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsContentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsViewModel.currentNews.observe(activity as LifecycleOwner) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webViewSetup(it.mobile_url)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(url: String) {
        binding.wv.webViewClient = WebViewClient()
        binding.wv.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsContentFragment()
    }
}