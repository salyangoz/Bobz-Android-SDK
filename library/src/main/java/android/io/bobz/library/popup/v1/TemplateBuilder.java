package android.io.bobz.library.popup.v1;

import android.content.Context;
import android.io.bobz.library.api.model.Popup;
import android.io.bobz.library.popup.v1.enums.TemplateName;

public class TemplateBuilder<T extends Template> {

    private TemplateName template;
    private Context context;
    private Popup popup;

    public TemplateBuilder(String templateName, Context context, Popup popup) {

        TemplateName template = TemplateName.valueOf(templateName);
        this.template = template;
        this.context = context;
        this.popup = popup;

    }

    protected T createTemplate() {

        return (T) new Template(template, context, popup);
    }

    public T build() {

        T template = createTemplate();

        return template;
    }


}
