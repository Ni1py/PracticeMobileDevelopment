package mobile.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobile.newsapp.data.api.ApiServices
import mobile.newsapp.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.btGetNews.setOnClickListener {
            makeApiRequest()
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
                    mainBinding.tvId.text = response.data.news[0].id.toString()
                    mainBinding.tvTitle.text = response.data.news[0].title
                    mainBinding.tvImg.text = response.data.news[0].img
                    mainBinding.tvDate.text = response.data.news[0].news_date
                    mainBinding.tvAnnotation.text = response.data.news[0].annotation
                    mainBinding.tvMobileUrl.text = response.data.news[0].mobile_url
                }
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}
