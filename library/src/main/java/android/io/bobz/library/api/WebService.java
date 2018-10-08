package android.io.bobz.library.api;

import android.content.Context;
import android.io.bobz.library.BuildConfig;
import android.io.bobz.library.api.serializer.UriSerializer;
import android.net.Uri;

import com.google.gson.GsonBuilder;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WebService {

    private static WebServiceImpl sInstance;
    private static final String BASE_URL = "http://apibeta.bobz.io";

    public static WebServiceImpl getInstance(Context context) {

        if (sInstance == null) {

            HeaderInterceptor header = new HeaderInterceptor(context);

            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addInterceptor(header)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            sInstance = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(new CallAdapter.Factory() {

                        @Override
                        public CallAdapter<?, ?> get(Type type, Annotation[] annotations, Retrofit retrofit) {

                            return null;
                        }
                    })
                    .addConverterFactory(
                            GsonConverterFactory
                                    .create(
                                            new GsonBuilder()
                                                    .disableHtmlEscaping()
                                                    .registerTypeAdapter(Uri.class, new UriSerializer())
                                                    .create()))
                    .client(client)
                    .build()
                    .create(WebServiceImpl.class);
        }

        return sInstance;
    }
}