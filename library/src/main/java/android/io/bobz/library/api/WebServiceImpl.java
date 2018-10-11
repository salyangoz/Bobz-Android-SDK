package android.io.bobz.library.api;


import android.io.bobz.library.api.model.Popup;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface WebServiceImpl {

    @Headers(Version.V1_0)
    @GET("sdk/search/{project-id}")
    Call<Popup> popup(@Path("project-id") String projectId);

    @Headers(Version.V1_0)
    @FormUrlEncoded
    @POST("sdk/tracking/")
    Call<Void> track(@Path("project-id") String projectId, @Field("popup-token") String popupToken, @Field("event") String event, @Field("action") String action, @Field("timestamp") String timestamp);


}
