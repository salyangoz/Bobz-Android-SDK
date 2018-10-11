package android.io.bobz.library.popup;

import android.content.Context;
import android.io.bobz.library.api.WebService;
import android.io.bobz.library.popup.v1.TemplateBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup implements Callback<android.io.bobz.library.api.model.Popup> {

    private Context context;
    private String projectId;

    public Popup(Context context, String projectId) {

        this.context = context;
        this.projectId = projectId;
    }

    public void show() {

        callPopup();
    }

    public void callPopup() {

        WebService
                .getInstance(context)
                .popup(projectId)
                .enqueue(this);

    }

    public void createPopup(android.io.bobz.library.api.model.Popup popup) {

        TemplateBuilder temp = new TemplateBuilder(popup.options.template.type, context, popup);
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
