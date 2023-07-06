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
import mobile.newsapp.databinding.ActivityMainBinding
import mobile.newsapp.fragment.NewsContentFragment
import mobile.newsapp.fragment.NewsTapFragment
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
            android.R.id.home -> newsViewModel.isClickCard.value = false
            R.id.refresh -> makeApiRequest()
        }
        return true
    }

    private fun init() {
        db = MainDb.getDb(this)
        db.getDao().getVisibleNews().asLiveData().observe(this) {list ->
            newsViewModel.newsVisibleList.value = list
        }
        db.getDao().getHiddenNews().asLiveData().observe(this) {list ->
            newsViewModel.newsHiddenList.value = list
        }
        newsViewModel.hiddenNews.value = NewsEntity.getEmptyNews()
        newsViewModel.isClickCard.value = false
        //newsViewModel.isClickHiddenButton.value = false
        newsViewModel.searchVisibleWord.value = ""
        newsViewModel.searchHiddenWord.value = ""
        replaceFragments()
        search()
        hide()
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl("https://ws-tszh-1c-test.vdgb-soft.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)

        var listHiddenNews = listOf<Int>()
        newsViewModel.newsList.observe(this) {newsList ->
            listHiddenNews = newsList.map { news ->
                if (news.hidden)
                    news.id
                else
                    -1
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getNewsList()
                db.getDao().deleteNews()
                db.getDao().insertAllNews(response.data.news.map {
                        NewsEntity.fromNewsModel(
                            it.id in listHiddenNews,
                            it
                        )
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

    private fun replaceFragments() {
        newsViewModel.isClickCard.observe(this) {
            if (it) {
                openFrag(NewsContentFragment.newInstance())
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = getString(R.string.content)
            }
            else {
                openFrag(NewsTapFragment.newInstance())
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.title = getString(R.string.app_name).uppercase()
            }
        }
    }

    private fun search() {
        newsViewModel.searchVisibleWord.observe(this) {word ->
            updateSearchNews(word, false)
        }
        newsViewModel.searchHiddenWord.observe(this) {word ->
            updateSearchNews(word, true)
        }
    }

    private fun hide() {
        newsViewModel.hiddenNews.observe(this) { news ->
            if (news.id != -1)
                if (news.hidden)
                    sendHideRequest(news.copy(hidden = false))
                else
                    sendHideRequest(news.copy(hidden = true))
        }
    }

    private fun updateSearchNews(word: String, isHidden: Boolean) {
        if (word.isNotEmpty())
            if (isHidden)
                db.getDao().getHiddenNewsByTitleAnnotation(word).asLiveData().observe(this) {list ->
                    newsViewModel.newsHiddenList.value = list
                }
            else
                db.getDao().getVisibleNewsByTitleAnnotation(word).asLiveData().observe(this) {list ->
                    newsViewModel.newsVisibleList.value = list
                }
    }

    private fun sendHideRequest(news: NewsEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.getDao().updateHidden(news)
                Log.d("Main", "Success update")
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}
