package mobile.newsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import mobile.newsapp.data.db.entity.NewsEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllNews(newsList: List<NewsEntity>)
    @Query("SELECT * FROM news WHERE hidden = :hidden")
    fun getNewsByHidden(hidden: Boolean): Flow<List<NewsEntity>>
    @Query("SELECT * FROM news WHERE (title LIKE :search OR annotation LIKE :search) AND hidden = :hidden")
    fun getNewsByTitleAnnotationHidden(search: String, hidden: Boolean): Flow<List<NewsEntity>>
    @Update
    fun updateHidden(item: NewsEntity)
    @Query("DELETE FROM news")
    fun deleteNews()
}