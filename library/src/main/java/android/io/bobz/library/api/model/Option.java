package android.io.bobz.library.api.model;

import android.graphics.Color;
import android.io.bobz.library.api.model.type.newsletter.Newsletter;
import android.io.bobz.library.api.model.type.rate.Rate;
import android.io.bobz.library.api.model.type.social.SocialFollow;
import android.io.bobz.library.api.model.type.upgrade.Upgrade;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Option {

    @SerializedName("platform")
    public Platform platform;
    @SerializedName("template")
    public Template template;
    @SerializedName("title")
    public String title;
    @SerializedName("title_color")
    public String titleColor;
    @SerializedName("sub_title")
    public String subtitle;
    @SerializedName("sub_title_color")
    public String subtitleColor;
    @SerializedName("description")
    public String description;
    @SerializedName("description_color")
    public String descriptionColor;
    @SerializedName("close_icon_color")
    public String closeIconColor;
    @SerializedName("image")
    public String image;
    @SerializedName("overlay_color")
    public String overlayColor;
    @SerializedName("background")
    public Background background;
    @SerializedName("button")
    public List<Button> buttons;
    @SerializedName("newsletter")
    public Newsletter newsletter;
    @SerializedName("social_follow")
    public SocialFollow socialFollow;
    @SerializedName("rate")
    public Rate rate;
    @SerializedName("upgrade")
    public Upgrade upgrade;


}
