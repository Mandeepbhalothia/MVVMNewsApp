package com.mandeep.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mandeep.mvvmnewsapp.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Convertor::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var instance: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            var tempInstance = instance
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                tempInstance = createDatabase(context)
                instance = tempInstance
                return tempInstance!!
            }

        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java, "articleDb"
            ).build()


    }

}