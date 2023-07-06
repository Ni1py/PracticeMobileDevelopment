package mobile.newsapp.data.model

import mobile.newsapp.data.db.entity.NewsEntity
import java.io.Serializable

data class NewsModel (
    val id: Int,
    val title: String,
    val img: String,
    //val local_img: String,
    //val news_date: String,
    val annotation: String,
    //val id_resource: Int,
    //val type: Int,
    //val news_date_uts: Long,
    val mobile_url: String
) : Serializable {}
