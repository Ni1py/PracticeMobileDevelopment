package mobile.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobile.newsapp.adapter.NewsAdapter
import mobile.newsapp.data.api.ApiServices
import mobile.newsapp.data.model.News
import mobile.newsapp.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        init()
    }

    private fun init() {
        adapter = NewsAdapter()
        mainBinding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
            btGetNews.setOnClickListener {
                makeApiRequest()
            }
        }
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl("https://ws-tszh-1c-test.vdgb-soft.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getNewsList()
                Log.d("Main", "Success: ${response.success}")
                runOnUiThread {
                    adapter.submitList(response.data.news)
                }
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}
