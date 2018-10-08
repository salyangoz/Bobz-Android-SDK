package android.io.bobz.library.api.model.type.social;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialFollow {

    @SerializedName("title")
    public String title;
    @SerializedName("social_platform")
    public List<SocialPlatform> socialPlatforms;

}
