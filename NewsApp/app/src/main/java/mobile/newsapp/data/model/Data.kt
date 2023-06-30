package mobile.newsapp.data.model

data class Data(
    val news: List<News>,
    val count: Int,
    val error_msg: String
)
