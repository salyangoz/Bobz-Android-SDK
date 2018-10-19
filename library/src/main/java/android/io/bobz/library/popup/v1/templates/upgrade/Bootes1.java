package android.io.bobz.library.popup.v1.templates.upgrade;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.io.bobz.library.R;
import android.io.bobz.library.api.model.Background;
import android.io.bobz.library.api.model.Popup;
import android.io.bobz.library.popup.v1.view.ButtonDrawable;
import android.io.bobz.library.popup.v1.view.LayoutDrawable;
import android.io.bobz.library.popup.window.PopupWindow;
import android.io.bobz.library.popup.window.PopupWindowBuilder;
import android.io.bobz.library.utils.Utilities;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class Bootes1<T extends PopupWindow> implements View.OnClickListener {

    private Popup popup;
    private Boolean isRated;
    private Context context;
    private String appVersion;
    private PopupWindow window;
    private PopupWindowBuilder builder;
    private SharedPreferences sharedPreferences;

    //View Components
    private Button firstButton, secondButton;
    private LinearLayout layout;
    private AppCompatImageView image;
    private AppCompatTextView title, subtitle, description;

    public Bootes1(Context context, Popup popup) {

        this.context = context;
        this.popup = popup;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.file_key), Context.MODE_PRIVATE);
        this.isRated = this.sharedPreferences.getBoolean(context.getString(R.string.is_rated), false);
        this.appVersion = Utilities.getAppVersion(context);

    }

    protected PopupWindowBuilder createPopupWindowBuilder() {

        //Popup Window Builder
        PopupWindowBuilder popupWindowBuilder = new PopupWindowBuilder(context)
                .setContentView(R.layout.popup_bootes1)
                .bindClickListener(this, R.id.button1)
                .bindClickListener(this, R.id.button2)
                .setDismissOnTouchBackground(true)
                .setGravity(Gravity.CENTER);

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

        //If app is rated return null

        if (TextUtils.equals(appVersion, popup.options.upgrade.version))
            return null;

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
        if (id == R.id.button2) {
            //Rate clicked
            Utilities.openURL(this.context, popup.options.upgrade.url);
        }

        window.dismiss();
    }

    private void setupView() {

        View view = builder.mContentView;

        layout = (LinearLayout) view.findViewById(R.id.layout);
        firstButton = (Button) view.findViewById(R.id.button1);
        secondButton = (Button) view.findViewById(R.id.button2);
        image = (AppCompatImageView) view.findViewById(R.id.image);
        title = (AppCompatTextView) view.findViewById(R.id.title);
        subtitle = (AppCompatTextView) view.findViewById(R.id.subtitle);
        description = (AppCompatTextView) view.findViewById(R.id.description);

    }

    private void setupOptions() {

        //Ttile Textview Options
        title.setText(popup.options.title);
        title.setTextColor(Color.parseColor(popup.options.titleColor));

        //Subtitle Textview Options
        subtitle.setText(popup.options.subtitle);
        subtitle.setTextColor(Color.parseColor(popup.options.subtitleColor));

        //Description Textview Options
        description.setText(popup.options.description);
        description.setTextColor(Color.parseColor(popup.options.descriptionColor));

        //Popup Overlay Color
        window.setBackgroundColor(Color.parseColor(popup.options.overlayColor));

        //Popup Background
        Background background = popup.options.background;

        if (!background.color.isEmpty())
            layout.setBackgroundDrawable(LayoutDrawable.rounded(background.color));

        //Image View Options
        if (!popup.options.image.isEmpty())
            Picasso.get().load(popup.options.image).into(image);

        //Window outside popup Layer Background
        if (!popup.options.overlayColor.isEmpty())
            window.setBackgroundColor(Color.parseColor(popup.options.overlayColor));

        //First Button Options
        firstButton.setText(popup.options.buttons.get(0).title);
        firstButton.setTextColor(Color.parseColor(popup.options.buttons.get(0).titleColor));
        firstButton.setBackgroundDrawable(ButtonDrawable.rounded(popup.options.buttons.get(0).backgroundColor));

        //Second Button Options
        secondButton.setText(popup.options.buttons.get(1).title);
        secondButton.setTextColor(Color.parseColor(popup.options.buttons.get(1).titleColor));
        secondButton.setBackgroundDrawable(ButtonDrawable.rounded(popup.options.buttons.get(1).backgroundColor));


    }


}
