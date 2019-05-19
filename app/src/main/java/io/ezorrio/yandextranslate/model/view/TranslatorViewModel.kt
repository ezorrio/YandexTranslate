package io.ezorrio.yandextranslate.model.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.ezorrio.yandextranslate.api.ApiHelper
import io.ezorrio.yandextranslate.model.api.TranslationResult

class TranslatorViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun translate(text: String, dir: Array<String?>): TranslationResult? {
        val dirStr = if (dir[0] == null) dir[1] else "${dir[0]}-${dir[1]}"
//        val dirStr =
        val result = ApiHelper().translateAsync(text, dirStr!!).await()
        return result.body()
    }
}