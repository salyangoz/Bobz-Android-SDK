package android.io.bobz.library.api;


import android.content.Context;

import java.io.IOException;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public final class HeaderInterceptor implements Interceptor {

    private Context mContext;

    HeaderInterceptor(Context context) {

        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain
                .request()
                .newBuilder();

        builder
                .addHeader("Timezone", TimeZone.getDefault().getID());

        return chain.proceed(builder.build());
    }
}