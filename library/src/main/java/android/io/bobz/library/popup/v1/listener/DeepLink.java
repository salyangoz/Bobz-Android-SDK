package android.io.bobz.library.popup.v1.listener;


import android.net.Uri;

import java.net.URI;

public class DeepLink {

    private Uri uri;

    public DeepLink(Uri uri) {

        this.uri = uri;
    }


    public Uri getUri() {

        return uri;
    }

    public void setUri(Uri uri) {

        this.uri = uri;
    }
}
