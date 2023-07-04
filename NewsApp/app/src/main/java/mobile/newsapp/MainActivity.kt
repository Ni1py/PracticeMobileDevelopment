package mobile.newsapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mobile.newsapp.data.api.ApiServices
import mobile.newsapp.data.db.MainDb
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.data.model.NewsModel
import mobile.newsapp.databinding.ActivityMainBinding
import mobile.newsapp.fragment.NewsContentFragment
import mobile.newsapp.fragment.NewsListFragment
import mobile.newsapp.viewModel.NewsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var db: MainDb
    private val newsViewModel: NewsViewModel by viewModels()

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
        when (item.itemId) {
            android.R.id.home -> newsViewModel.isClick.value = false
            R.id.refresh -> makeApiRequest()
        }
        return true
    }

    private fun init() {
        db = MainDb.getDb(this)
        db.getDao().getAllNews().asLiveData().observe(this) {list ->
            newsViewModel.newsList.value = list.map { NewsModel.fromNewsEntity(it) }
        }
        newsViewModel.isClick.value = false
        newsViewModel.searchWord.value = ""
        openFragCondition()
        displayHomeButton()
        search()
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

    private fun openFrag(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, fragment)
            .commit()
    }

    private fun openFragCondition() {
        newsViewModel.isClick.observe(this) {
            if (it)
                openFrag(NewsContentFragment.newInstance())
            else
                openFrag(NewsListFragment.newInstance())
        }
    }

    private fun displayHomeButton() {
        newsViewModel.isClick.observe(this) {
            if (it)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            else
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun search() {
        newsViewModel.searchWord.observe(this) {word ->
            if (word.isNotEmpty())
                db.getDao().getNewsByTitleAnnotation(word).asLiveData().observe(this) {list ->
                    newsViewModel.newsList.value = list.map { NewsModel.fromNewsEntity(it) }
                }
        }
    }
}
