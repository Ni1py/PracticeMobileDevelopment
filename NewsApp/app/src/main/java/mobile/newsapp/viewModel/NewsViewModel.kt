package mobile.newsapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.data.model.NewsModel

open class NewsViewModel : ViewModel() {
    val newsList: MutableLiveData<List<NewsEntity>> by lazy {
        MutableLiveData<List<NewsEntity>>()
    }
    val newsVisibleList: MutableLiveData<List<NewsEntity>> by lazy {
        MutableLiveData<List<NewsEntity>>()
    }
    val newsHiddenList: MutableLiveData<List<NewsEntity>> by lazy {
        MutableLiveData<List<NewsEntity>>()
    }
    val currentNews: MutableLiveData<NewsEntity> by lazy {
        MutableLiveData<NewsEntity>()
    }
    val isClickCard: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isClickHiddenButton: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val hiddenNews: MutableLiveData<NewsEntity> by lazy {
        MutableLiveData<NewsEntity>()
    }
    val searchVisibleWord: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val searchHiddenWord: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}