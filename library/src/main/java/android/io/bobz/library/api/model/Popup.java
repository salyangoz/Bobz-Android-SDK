package android.io.bobz.library.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Popup {

    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("options")
    public Option options;
    @SerializedName("token")
    public String token;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("started_at")
    public String startedAt;
    @SerializedName("expires_at")
    public String expiresAt;
    @SerializedName("paused_at")
    public String pausedAt;

}
