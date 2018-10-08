package android.io.bobz.library.popup;

import android.content.Context;

public class PopupBuilder<T extends Popup> {

    private Context context;
    private String templateName;

    public PopupBuilder(Context context) {

        this.context = context;
    }

    protected T createPopup() {

        return (T) new Popup(context);
    }

    public T build() {

        T popup = createPopup();

        return popup;
    }
}
