package android.io.bobz.library.popup.v1.templates.twobuttons;

import android.content.Context;
import android.graphics.Color;
import android.io.bobz.library.R;
import android.io.bobz.library.api.model.Background;
import android.io.bobz.library.api.model.Popup;
import android.io.bobz.library.popup.v1.listener.ButtonClicked;
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

public class BlackEye<T extends PopupWindow> implements View.OnClickListener {

    private Popup popup;
    private Context context;
    private PopupWindow window;
    private PopupWindowBuilder builder;

    //View Components
    private Button firstButton, secondButton;
    private LinearLayout layout;
    private AppCompatImageView image;
    private AppCompatTextView subtitle, description;

    public BlackEye(Context context, Popup popup) {

        this.context = context;
        this.popup = popup;
    }

    protected PopupWindowBuilder createPopupWindowBuilder() {

        //Popup Window Builder
        PopupWindowBuilder popupWindowBuilder = new PopupWindowBuilder(context)
                .setContentView(R.layout.popup_blackeye)
                .bindClickListener(this, R.id.button1)
                .bindClickListener(this, R.id.button2)
                .setDismissOnTouchBackground(false)
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

        switch (view.getId()) {

            default:
                EventBus.getDefault().post(new ButtonClicked("Test", Uri.parse("test")));
                Log.i("Event", "Button click fired" + view.getId());
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                window.dismiss();
                break;
        }

    }

    private void setupView() {

        View view = builder.mContentView;

        layout = (LinearLayout) view.findViewById(R.id.layout);
        firstButton = (Button) view.findViewById(R.id.button1);
        secondButton = (Button) view.findViewById(R.id.button2);
        image = (AppCompatImageView) view.findViewById(R.id.image);
        subtitle = (AppCompatTextView) view.findViewById(R.id.subtitle);
        description = (AppCompatTextView) view.findViewById(R.id.description);

    }

    private void setupOptions() {

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
