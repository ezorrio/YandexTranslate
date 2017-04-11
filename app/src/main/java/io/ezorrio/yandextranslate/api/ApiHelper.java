package io.ezorrio.yandextranslate.api;



import android.content.Context;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import io.ezorrio.yandextranslate.adapter.LanguageAdapter;
import io.ezorrio.yandextranslate.model.api.LanguageDetectionResult;
import io.ezorrio.yandextranslate.model.api.TranslationDirs;
import io.ezorrio.yandextranslate.model.api.TranslationResult;
import io.ezorrio.yandextranslate.utils.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.ezorrio.yandextranslate.utils.Constants.API_BASE_URL;
import static io.ezorrio.yandextranslate.utils.Constants.API_KEY;

/**
 * Created by golde on 28.03.2017.
 */

public class ApiHelper {
    private ApiService mService;

    public ApiHelper(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .build();
        mService = retrofit.create(ApiService.class);
    }

    public void translate(String text, String lang, final TextView showOn) throws IOException {
        mService.translate(API_KEY, text, lang).enqueue(new Callback<TranslationResult>() {
            @Override
            public void onResponse(Call<TranslationResult> call, Response<TranslationResult> response) {
                Log.d("API", response.toString());
                if (response.body() != null){
                    showOn.setText(TextUtils.unescape(response.body().getText().toString()));
                }
            }

            @Override
            public void onFailure(Call<TranslationResult> call, Throwable t) {
                showOn.setText("Error");
            }
        });
    }

    public void detectLanguage(final Context context, final String text, final Spinner showOn){
        mService.detectLanguage(API_KEY, text).enqueue(new Callback<LanguageDetectionResult>() {
            @Override
            public void onResponse(Call<LanguageDetectionResult> call, Response<LanguageDetectionResult> response) {
            }

            @Override
            public void onFailure(Call<LanguageDetectionResult> call, Throwable t) {

            }
        });
    }

    public void updateTranslationDir(final Context context, final Spinner input, final Spinner output){
        mService.getLangs(API_KEY, "en").enqueue(new Callback<TranslationDirs>() {
            @Override
            public void onResponse(Call<TranslationDirs> call, Response<TranslationDirs> response) {
                input.setAdapter(new LanguageAdapter(context, response.body(), true));
                output.setAdapter(new LanguageAdapter(context, response.body(), false));
            }

            @Override
            public void onFailure(Call<TranslationDirs> call, Throwable t) {

            }
        });

        mService.getLangs(API_KEY, "en");
    }

}
