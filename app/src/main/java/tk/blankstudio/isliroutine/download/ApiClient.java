package tk.blankstudio.isliroutine.download;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by deadsec on 11/8/17.
 */

public class ApiClient {
    public static final String BASE_URL="http://isliroutine.tk/api/";
    public static Retrofit sRetrofit =null;

    public static OkHttpClient okHttpClient;

    public static Retrofit getApiClient() {
        if(sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient()).build();
        }
        return sRetrofit;
    }
    public static OkHttpClient getOkHttpClient() {
        if(okHttpClient==null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

}
