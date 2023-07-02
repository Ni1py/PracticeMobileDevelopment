package mobile.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobile.newsapp.adapter.NewsAdapter
import mobile.newsapp.data.api.ApiServices
import mobile.newsapp.data.db.MainDb
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.data.model.NewsModel
import mobile.newsapp.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var db: MainDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh) {
            makeApiRequest()
        }
        return true
    }

    private fun init() {
        db = MainDb.getDb(this)
        adapter = NewsAdapter()
        db.getDao().getAllNews().asLiveData().observe(this) {list ->
            adapter.submitList(list.map { NewsModel.fromNewsEntity(it) })
        }
        mainBinding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
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
                db.getDao().insertAllNews(response.data.news.map {
                        NewsEntity.fromNewsModel(false, it)
                    })
                Log.d("Main", "Success: ${response.success}")
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}
