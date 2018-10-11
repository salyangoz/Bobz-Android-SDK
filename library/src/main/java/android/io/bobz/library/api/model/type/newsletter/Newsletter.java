package android.io.bobz.library.api.model.type.newsletter;

import com.google.gson.annotations.SerializedName;

public class Newsletter {

    @SerializedName("placeholder")
    public String placeholder;
    @SerializedName("submit_response")
    public NewsletterResponse newsletterResponse;
    @SerializedName("title_color")
    public String titleColor;
    @SerializedName("icon_color")
    public String iconColor;

}
