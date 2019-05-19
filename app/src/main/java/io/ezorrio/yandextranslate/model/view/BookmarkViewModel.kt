package io.ezorrio.yandextranslate.model.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.ezorrio.yandextranslate.model.dao.BookmarksDao
import io.ezorrio.yandextranslate.model.db.AppDatabase
import io.ezorrio.yandextranslate.model.room.AppBookmark
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    var bookmarksDao: BookmarksDao = AppDatabase.invoke(application).getBookmarkDao()
    @ObsoleteCoroutinesApi
    var coroutineContext: ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    fun getAllBookmarks(): LiveData<List<AppBookmark>> {
        return bookmarksDao.load()
    }

    @ObsoleteCoroutinesApi
    suspend fun saveBookmark(bookmark: AppBookmark) {
        withContext(coroutineContext) {
            bookmarksDao.save(bookmark)
        }
    }
}
