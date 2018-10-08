package android.io.bobz.library.popup.v1;

import android.content.Context;
import android.graphics.Color;
import android.io.bobz.library.R;
import android.io.bobz.library.api.model.Popup;
import android.io.bobz.library.popup.v1.enums.TemplateName;
import android.io.bobz.library.popup.v1.templates.MilkywayTemplate;
import android.io.bobz.library.popup.window.PopupWindow;
import android.io.bobz.library.popup.window.PopupWindowBuilder;
import android.view.Gravity;

public class Template {

    private TemplateName template;
    private Context context;
    private Popup popup;

    public Template(TemplateName template, Context context, Popup popup) {

        this.template = template;
        this.context = context;
        this.popup = popup;

    }

    public void show() {

        PopupWindow temp;

        switch (template) {

            case MILKYWAY:
                temp = new MilkywayTemplate<>(context, popup).build();
                break;
            //TODO ADD MORE TEMPLATES
            default:
                temp = new MilkywayTemplate<>(context, popup).build();
                break;
        }

        temp.show();
    }

}
