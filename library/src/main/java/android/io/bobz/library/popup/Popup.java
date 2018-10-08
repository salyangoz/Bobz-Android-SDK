package android.io.bobz.library.popup;

import android.content.Context;
import android.io.bobz.library.api.WebService;
import android.io.bobz.library.popup.v1.TemplateBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup implements Callback<android.io.bobz.library.api.model.Popup> {

    private Context context;

    public Popup(Context context) {

        this.context = context;
    }

    public void show() {

        callPopup();
    }

    public void callPopup() {

        WebService
                .getInstance(context)
                .popup()
                .enqueue(this);

        TemplateBuilder temp = new TemplateBuilder("MILKYWAY", context);
        temp.build().show();
    }

    public void createPopup(android.io.bobz.library.api.model.Popup popup) {

        TemplateBuilder temp = new TemplateBuilder(popup.getName(), context);
        temp.build().show();
    }

    @Override
    public void onResponse(Call<android.io.bobz.library.api.model.Popup> call, Response<android.io.bobz.library.api.model.Popup> response) {

        if (!response.isSuccessful())
            return;

        createPopup(response.body());
    }

    @Override
    public void onFailure(Call<android.io.bobz.library.api.model.Popup> call, Throwable t) {

    }
}
