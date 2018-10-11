package android.io.bobz.library.popup;

import android.content.Context;

public class PopupBuilder<T extends Popup> {

    private Context context;
    private String projectId;

    public PopupBuilder(Context context, String projectId) {

        this.context = context;
        this.projectId = projectId;
    }

    protected T createPopup() {

        return (T) new Popup(context, projectId);
    }

    public T build() {

        T popup = createPopup();

        return popup;
    }
}
