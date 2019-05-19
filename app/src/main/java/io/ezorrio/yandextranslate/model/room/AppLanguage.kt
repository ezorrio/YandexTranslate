package io.ezorrio.yandextranslate.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class AppLanguage(
        @PrimaryKey val id: String,
        val lang: String)