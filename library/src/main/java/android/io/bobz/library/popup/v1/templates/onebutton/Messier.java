package android.io.bobz.library.popup.v1.templates.onebutton;

import android.content.Context;
import android.graphics.Color;
import android.io.bobz.library.R;
import android.io.bobz.library.api.model.Background;
import android.io.bobz.library.api.model.Popup;
import android.io.bobz.library.popup.v1.listener.DeepLink;
import android.io.bobz.library.popup.v1.view.ButtonDrawable;
import android.io.bobz.library.popup.v1.view.LayoutDrawable;
import android.io.bobz.library.popup.window.PopupWindow;
import android.io.bobz.library.popup.window.PopupWindowBuilder;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class Messier<T extends PopupWindow> implements View.OnClickListener {

    private Popup popup;
    private Context context;
    private PopupWindow window;
    private PopupWindowBuilder builder;

    //View Components
    private Button button;
    private LinearLayout layout;
    private AppCompatImageView image;
    private AppCompatTextView description;

    public Messier(Context context, Popup popup) {

        this.context = context;
        this.popup = popup;
    }

    protected PopupWindowBuilder createPopupWindowBuilder() {

        //Popup Window Builder
        PopupWindowBuilder popupWindowBuilder = new PopupWindowBuilder(context)
                .setContentView(R.layout.popup_messier)
                .bindClickListener(this, R.id.button1)
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

        EventBus.getDefault().post(new DeepLink(Uri.parse(popup.options.buttons.get(0).url)));
        window.dismiss();

    }

    private void setupView() {

        View view = builder.mContentView;

        layout = (LinearLayout) view.findViewById(R.id.layout);
        button = (Button) view.findViewById(R.id.button1);
        image = (AppCompatImageView) view.findViewById(R.id.image);
        description = (AppCompatTextView) view.findViewById(R.id.description);

    }

    private void setupOptions() {

        //Description Textview Options
        description.setText(popup.options.description);
        description.setTextColor(Color.parseColor(popup.options.descriptionColor));

        //Popup Overlay Color
        window.setBackgroundColor(Color.parseColor(popup.options.overlayColor));

        //Popup Background
        Background background = popup.options.background;

        if (!background.color.isEmpty())
            layout.setBackgroundDrawable(LayoutDrawable.rounded(popup.options.background.color));

        //Image View Options
        if (!popup.options.image.isEmpty())
            Picasso.get().load(popup.options.image).into(image);

        //Window outside popup Layer Background
        if (!popup.options.overlayColor.isEmpty())
            window.setBackgroundColor(Color.parseColor(popup.options.overlayColor));

        //First Button Options
        button.setText(popup.options.buttons.get(0).title);
        button.setTextColor(Color.parseColor(popup.options.buttons.get(0).titleColor));
        button.setBackgroundDrawable(ButtonDrawable.roundedGradient(popup.options.buttons.get(0).backgroundColor, popup.options.buttons.get(0).backgroundColor));

    }

}
