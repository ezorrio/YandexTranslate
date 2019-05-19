package io.ezorrio.yandextranslate.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.ezorrio.yandextranslate.model.room.AppHistory

@Dao
interface HistoryDao {
    @Insert(onConflict = REPLACE)
    fun save(history: AppHistory)

    @Query("SELECT * FROM history")
    fun load(): LiveData<List<AppHistory>>
}