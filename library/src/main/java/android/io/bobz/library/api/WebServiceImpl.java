package android.io.bobz.library.api;


import android.io.bobz.library.api.model.Popup;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface WebServiceImpl {

    @Headers(Version.V1_0)
    @GET("popups")
    Call<Popup> popup();


}
