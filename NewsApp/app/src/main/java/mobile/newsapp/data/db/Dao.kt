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
    @Insert
    fun insertNews(item: NewsEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllNews(newsList: List<NewsEntity>)
    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>
    @Query("SELECT * FROM news WHERE title LIKE :search OR annotation LIKE :search")
    fun getNewsByTitleAnnotation(search: String): Flow<List<NewsEntity>>
    @Update
    fun updateHidden(item: NewsEntity)
    @Query("DELETE FROM news")
    fun deleteNews()
}