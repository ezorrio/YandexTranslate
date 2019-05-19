package io.ezorrio.yandextranslate.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.ezorrio.yandextranslate.model.room.AppBookmark

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(bookmark: AppBookmark)

    @Query("SELECT * FROM bookmark")
    fun load(): LiveData<List<AppBookmark>>
}