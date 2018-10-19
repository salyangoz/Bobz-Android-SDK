package android.io.bobz.library.popup.v1.templates.socialfollow;

import android.content.Context;
import android.graphics.Color;
import android.io.bobz.library.R;
import android.io.bobz.library.api.model.Background;
import android.io.bobz.library.api.model.Popup;
import android.io.bobz.library.api.model.type.social.SocialPlatform;
import android.io.bobz.library.popup.v1.enums.SocialMedia;
import android.io.bobz.library.popup.v1.view.LayoutDrawable;
import android.io.bobz.library.popup.window.PopupWindow;
import android.io.bobz.library.popup.window.PopupWindowBuilder;
import android.io.bobz.library.utils.Utilities;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class Iok1<T extends PopupWindow> implements View.OnClickListener {

    private Popup popup;
    private Context context;
    private PopupWindow window;
    private PopupWindowBuilder builder;

    //View Components
    private AppCompatImageButton facebook, instagram, twitter, linkedin, youtube;
    private LinearLayout layout;
    private AppCompatTextView subtitle;

    public Iok1(Context context, Popup popup) {

        this.context = context;
        this.popup = popup;
    }

    protected PopupWindowBuilder createPopupWindowBuilder() {

        //Popup Window Builder
        PopupWindowBuilder popupWindowBuilder = new PopupWindowBuilder(context)
                .setContentView(R.layout.popup_iok1)
                .bindClickListener(this, R.id.button1)
                .setDismissOnTouchBackground(true)
                .setGravity(Gravity.BOTTOM);

        this.builder = popupWindowBuilder;

        return popupWindowBuilder;

    }

    protected T createPopupWindow() {

        //Popup Window
        PopupWindow window = createPopupWindowBuilder().build();
        this.window = window;

        return (T) window;
    }

    public PopupWindow build() {

        //Popup Window
        T template = createPopupWindow();


        //Setup Views
        setupView();

        //Setup Popup Options
        setupOptions();

        return template;
    }


    //On click event
    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.facebook) {
            Utilities.openURL(context, popup.options.socialFollow.socialPlatforms.get(0).url);
        } else if (id == R.id.instagram) {
            Utilities.openURL(context, popup.options.socialFollow.socialPlatforms.get(1).url);
        } else if (id == R.id.twitter) {
            Utilities.openURL(context, popup.options.socialFollow.socialPlatforms.get(2).url);
        } else if (id == R.id.linkedin) {
            Utilities.openURL(context, popup.options.socialFollow.socialPlatforms.get(3).url);
        } else if (id == R.id.youtube) {
            Utilities.openURL(context, popup.options.socialFollow.socialPlatforms.get(4).url);
        }
    }

    private void setupView() {

        View view = builder.mContentView;

        layout = (LinearLayout) view.findViewById(R.id.layout);
        facebook = (AppCompatImageButton) view.findViewById(R.id.facebook);
        instagram = (AppCompatImageButton) view.findViewById(R.id.instagram);
        twitter = (AppCompatImageButton) view.findViewById(R.id.twitter);
        linkedin = (AppCompatImageButton) view.findViewById(R.id.linkedin);
        youtube = (AppCompatImageButton) view.findViewById(R.id.youtube);
        subtitle = (AppCompatTextView) view.findViewById(R.id.subtitle);

    }

    private void setupOptions() {

        //Subtitle Textview Options
        subtitle.setText(popup.options.subtitle);
        subtitle.setTextColor(Color.parseColor(popup.options.subtitleColor));

        //Popup Overlay Color
        window.setBackgroundColor(Color.parseColor(popup.options.overlayColor));

        //Popup Background
        Background background = popup.options.background;

        if (!background.color.isEmpty())
            layout.setBackgroundDrawable(LayoutDrawable.topRound(background.color));

        //Window outside popup Layer Background
        if (!popup.options.overlayColor.isEmpty())
            window.setBackgroundColor(Color.parseColor(popup.options.overlayColor));

        //Social Platforms:
        for (SocialPlatform socialPlatform : popup.options.socialFollow.socialPlatforms) {

            SocialMedia media = SocialMedia.valueOf(socialPlatform.platform);

            switch (media) {

                case FACEBOOK:
                    facebook.setVisibility(View.VISIBLE);
                    break;
                case INSTAGRAM:
                    instagram.setVisibility(View.VISIBLE);
                    break;
                case TWITTER:
                    twitter.setVisibility(View.VISIBLE);
                    break;
                case LINKEDIN:
                    linkedin.setVisibility(View.VISIBLE);
                    break;
                case YOUTUBE:
                    youtube.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

}
