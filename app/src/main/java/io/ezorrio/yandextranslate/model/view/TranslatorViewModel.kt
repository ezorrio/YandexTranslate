package io.ezorrio.yandextranslate.model.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.ezorrio.yandextranslate.api.ApiHelper
import io.ezorrio.yandextranslate.model.api.LanguageDetectionResult
import io.ezorrio.yandextranslate.model.api.TranslationResult

class TranslatorViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun translate(text: String, dir: Array<String?>): TranslationResult? {
        val dirStr = "${dir[0]}-${dir[1]}"
        return ApiHelper().translateAsync(text, dirStr).await().body()
    }

    suspend fun translate(text: String, dir: String?): TranslationResult? {
        return ApiHelper().translateAsync(text, dir!!).await().body()
    }

    suspend fun detectLanguage(text: String): LanguageDetectionResult? {
        return ApiHelper().detectAsync(text).await().body()
    }
}