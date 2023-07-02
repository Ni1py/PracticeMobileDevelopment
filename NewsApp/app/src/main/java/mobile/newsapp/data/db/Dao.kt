package mobile.newsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
}