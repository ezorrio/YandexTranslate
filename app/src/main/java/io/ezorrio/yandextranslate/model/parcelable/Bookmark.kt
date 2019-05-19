package io.ezorrio.yandextranslate.model.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by golde on 10.04.2017.
 */
@Parcelize
data class Bookmark(val id: Int,
                    val originalData: String?,
                    val originalLang: String?,
                    val translatedData: String?,
                    val translatedLang: String?) : Parcelable
