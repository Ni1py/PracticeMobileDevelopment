package mobile.newsapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mobile.newsapp.constants.Constants
import mobile.newsapp.data.model.NewsModel

@Entity (tableName = "news")
data class NewsEntity (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "img")
    var img: String,
    @ColumnInfo(name = "news_date")
    var news_date: String,
    @ColumnInfo(name = "annotation")
    var annotation: String,
    @ColumnInfo(name = "mobile_url")
    var mobile_url: String,
    @ColumnInfo(name = "hidden")
    var hidden: Boolean
) {
    companion object {
        fun fromNewsModel (hidden: Boolean, news: NewsModel) =
            NewsEntity(news.id, news.title,
                news.img, news.news_date,
                news.annotation, news.mobile_url, hidden)
        fun getEmptyNews() = NewsEntity(
            Constants.NEGATIVE_ID,
            Constants.EMPTY_STRING,
            Constants.EMPTY_STRING,
            Constants.EMPTY_STRING,
            Constants.EMPTY_STRING,
            Constants.EMPTY_STRING,
            false
        )
    }
}
