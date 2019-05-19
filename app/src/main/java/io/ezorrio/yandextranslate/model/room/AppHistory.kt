package io.ezorrio.yandextranslate.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class AppHistory(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val originalData: String,
        val originalLang: String,
        val translatedData: String,
        val translatedLang: String)