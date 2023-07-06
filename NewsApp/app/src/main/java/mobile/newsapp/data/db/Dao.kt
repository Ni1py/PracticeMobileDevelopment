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
    @Query("SELECT * FROM news WHERE hidden = 0")
    fun getVisibleNews(): Flow<List<NewsEntity>>
    @Query("SELECT * FROM news WHERE hidden = 1")
    fun getHiddenNews(): Flow<List<NewsEntity>>
    @Query("SELECT * FROM news WHERE (title LIKE :search OR annotation LIKE :search) AND hidden = 0")
    fun getVisibleNewsByTitleAnnotation(search: String): Flow<List<NewsEntity>>
    @Query("SELECT * FROM news WHERE (title LIKE :search OR annotation LIKE :search) AND hidden = 1")
    fun getHiddenNewsByTitleAnnotation(search: String): Flow<List<NewsEntity>>
    @Update
    fun updateHidden(item: NewsEntity)
    @Query("DELETE FROM news")
    fun deleteNews()
}