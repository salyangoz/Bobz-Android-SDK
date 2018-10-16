package android.io.bobz.library;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.io.bobz.library.listener.PopupReadyEvent;
import android.io.bobz.library.popup.PopupBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

public class Bobz {

    public static String TAG = "BOBZ";

    private Context context;
    private String projectId;

    public Bobz(Context context, String projectId) {

        this.context = context;
        this.projectId = projectId;
        callPopup();

    }

    public static BobzBuilder with(Context context) {

        return new BobzBuilder(context);
    }

    public void callPopup() {

        new PopupBuilder<>(context, projectId)
                .build()
                .show();
    }


}
