package io.ezorrio.yandextranslate.model.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.ezorrio.yandextranslate.model.dao.HistoryDao
import io.ezorrio.yandextranslate.model.db.AppDatabase
import io.ezorrio.yandextranslate.model.room.AppHistory
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private var historyDao: HistoryDao = AppDatabase.invoke(application).getHistoryDao()
    @ObsoleteCoroutinesApi
    var coroutineContext: ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    fun getAllHistory(): LiveData<List<AppHistory>> {
        return historyDao.load()
    }

    @ObsoleteCoroutinesApi
    suspend fun saveHistory(history: AppHistory) {
        withContext(coroutineContext) {
            historyDao.save(history)
        }
    }
}