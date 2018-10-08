package android.io.bobz.library.listener;

import android.content.Context;

public class PopupReadyEvent {

    Context context;

    public PopupReadyEvent(Context context) {

        this.context = context;
    }

    public Context getContext() {

        return context;
    }

    public void setContext(Context context) {

        this.context = context;
    }
}
