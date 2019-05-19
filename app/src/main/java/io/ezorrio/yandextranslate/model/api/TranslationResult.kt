package io.ezorrio.yandextranslate.model.api

/**
 * Created by golde on 28.03.2017.
 */

data class TranslationResult(
        var code: Int,
        var lang: String,
        var text: List<String>
)
