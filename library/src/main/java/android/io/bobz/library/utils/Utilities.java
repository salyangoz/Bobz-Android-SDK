package android.io.bobz.library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.io.bobz.library.Bobz;
import android.net.Uri;
import android.util.Log;

public class Utilities {

    public static String getAppVersion(Context context) {

        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Bobz.TAG, e.getMessage());
        }

        return result;
    }

    public static void openURL(Context context, String updateUrl) {

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
