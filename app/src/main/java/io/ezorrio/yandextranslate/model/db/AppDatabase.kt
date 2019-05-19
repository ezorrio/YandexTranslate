package io.ezorrio.yandextranslate.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.ezorrio.yandextranslate.model.dao.BookmarksDao
import io.ezorrio.yandextranslate.model.dao.HistoryDao
import io.ezorrio.yandextranslate.model.dao.LanguagesDao
import io.ezorrio.yandextranslate.model.room.AppBookmark
import io.ezorrio.yandextranslate.model.room.AppHistory
import io.ezorrio.yandextranslate.model.room.AppLanguage

@Database(entities = [AppLanguage::class, AppHistory::class, AppBookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getBookmarkDao(): BookmarksDao
    abstract fun getLanguagesDao(): LanguagesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                AppDatabase::class.java, "translator.db").build()
    }
}