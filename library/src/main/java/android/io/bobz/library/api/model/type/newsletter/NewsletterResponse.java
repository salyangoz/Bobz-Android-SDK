package android.io.bobz.library.api.model.type.newsletter;

import com.google.gson.annotations.SerializedName;

public class NewsletterResponse {

    @SerializedName("success_title")
    public String successTitle;
    @SerializedName("fail_title")
    public String failTitle;

}
