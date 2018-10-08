package android.io.bobz.library;

import android.content.Context;
import android.io.bobz.library.listener.PopupReadyEvent;
import android.io.bobz.library.popup.PopupBuilder;

import org.greenrobot.eventbus.Subscribe;


public class BobzBuilder {

    private Context context;

    public BobzBuilder(Context context) {

        this.context = context;
    }

    public Bobz build() {

        return new Bobz(context);
    }

}
