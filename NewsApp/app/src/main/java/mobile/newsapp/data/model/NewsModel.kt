package mobile.newsapp.data.model

import java.io.Serializable

data class NewsModel (
    val id: Int,
    val title: String,
    val img: String,
    val news_date: String,
    val annotation: String,
    val mobile_url: String
) : Serializable
