package io.ezorrio.yandextranslate.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.ezorrio.yandextranslate.model.api.LanguageDetectionResult
import io.ezorrio.yandextranslate.model.api.TranslationDirs
import io.ezorrio.yandextranslate.model.api.TranslationResult
import io.ezorrio.yandextranslate.utils.Constants.API_BASE_URL
import io.ezorrio.yandextranslate.utils.Constants.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class ApiHelper {
    private val mService: ApiService

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .build()
        mService = retrofit.create(ApiService::class.java)
    }

    fun translateAsync(text: String, lang: String): Deferred<Response<TranslationResult>> {
        return mService.translateAsync(API_KEY, text, lang)
    }

    fun detectAsync(text: String): Deferred<Response<LanguageDetectionResult>> {
        return mService.detectLanguageAsync(API_KEY, text)
    }

    fun getLanguagesAsync(): Deferred<Response<TranslationDirs>> {
        return mService.getLangsAsync(API_KEY, Locale.getDefault().language)
    }
}
