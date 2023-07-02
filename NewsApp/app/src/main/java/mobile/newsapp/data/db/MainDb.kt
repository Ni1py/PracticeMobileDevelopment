package mobile.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mobile.newsapp.data.db.entity.NewsEntity

@Database (entities = [NewsEntity::class], version = 1)
abstract class MainDb : RoomDatabase() {
    companion object {
        fun getDb(context: Context) : MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "NewsApp.db"
            ).build()
        }
    }

    abstract fun getDao() : Dao
}