package io.ezorrio.yandextranslate.api;

import io.ezorrio.yandextranslate.model.api.LanguageDetectionResult;
import io.ezorrio.yandextranslate.model.api.TranslationDirs;
import io.ezorrio.yandextranslate.model.api.TranslationResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by golde on 28.03.2017.
 */

public interface ApiService {
    @GET("getLangs")
    Call<TranslationDirs> getLangs(@Query("key") String key,
                                   @Query("ui") String ui);

    @GET("detect")
    Call<LanguageDetectionResult> detectLanguage(@Query("key") String key,
                                           @Query("text") String text);

    @GET("translate")
    Call<TranslationResult> translate(@Query("key") String key,
                                @Query("text") String text,
                                @Query("lang") String lang);
}
