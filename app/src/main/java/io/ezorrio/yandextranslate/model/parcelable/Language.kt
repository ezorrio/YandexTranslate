package io.ezorrio.yandextranslate.model.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by golde on 13.04.2017.
 */

@Parcelize
data class Language(
        val id: String,
        val lang: String) : Parcelable
