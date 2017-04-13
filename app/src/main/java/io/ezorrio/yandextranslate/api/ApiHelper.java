package io.ezorrio.yandextranslate.api;



import android.content.Context;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.adapter.LanguageAdapter;
import io.ezorrio.yandextranslate.model.Language;
import io.ezorrio.yandextranslate.model.api.LanguageDetectionResult;
import io.ezorrio.yandextranslate.model.api.TranslationDirs;
import io.ezorrio.yandextranslate.model.api.TranslationResult;
import io.ezorrio.yandextranslate.utils.TextUtils;
import okhttp3.Request;
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

    public void translateAsync(String text, String lang, final TextView showOn) throws IOException {
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

    public void detectAsync(final Context context, final String text, final TextView showOn){
        mService.detectLanguage(API_KEY, text).enqueue(new Callback<LanguageDetectionResult>() {
            @Override
            public void onResponse(Call<LanguageDetectionResult> call, Response<LanguageDetectionResult> response) {
                showOn.setText(response.body().getLang());
            }

            @Override
            public void onFailure(Call<LanguageDetectionResult> call, Throwable t) {

            }
        });
    }

    public LinkedHashMap<String, String> getLanguages(final Context context){
        try {
            Response<TranslationDirs> translationDirsResponse = mService.getLangs(API_KEY, "en").execute();
            return translationDirsResponse.body().getLangs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getLanguagesAndSave(final Context context){
        mService.getLangs(API_KEY, "en").enqueue(new Callback<TranslationDirs>() {
            @Override
            public void onResponse(Call<TranslationDirs> call, Response<TranslationDirs> response) {
                LinkedHashMap<String, String> data = response.body().getLangs();
                ArrayList<Language> transformed = new ArrayList<>();
                for (String key : data.keySet()) {
                    String value = (String) data.get(key);
                    transformed.add(new Language(key, value));
                }
                App.getLanguageRepository().saveLanguageList(transformed);
            }

            @Override
            public void onFailure(Call<TranslationDirs> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
