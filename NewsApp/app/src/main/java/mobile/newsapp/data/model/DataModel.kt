package mobile.newsapp.data.model

data class DataModel (
    val news: List<NewsModel>,
    val count: Int,
    val error_msg: String
)
