package mobile.newsapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobile.newsapp.data.model.NewsModel

open class NewsViewModel : ViewModel() {
    val newsList: MutableLiveData<List<NewsModel>> by lazy {
        MutableLiveData<List<NewsModel>>()
    }
    val currentNews: MutableLiveData<NewsModel> by lazy {
        MutableLiveData<NewsModel>()
    }
    val isClick: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}