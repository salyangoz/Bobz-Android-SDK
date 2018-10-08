package android.io.bobz.library.popup.v1.listener;


import android.net.Uri;

import java.net.URI;

public class ButtonClicked {

    private String data;
    private Uri uri;

    public ButtonClicked(String data, Uri uri) {

        this.data = data;
        this.uri = uri;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {

        this.data = data;
    }

    public Uri getUri() {

        return uri;
    }

    public void setUri(Uri uri) {

        this.uri = uri;
    }
}
