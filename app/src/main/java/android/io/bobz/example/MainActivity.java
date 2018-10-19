package android.io.bobz.example;

import android.io.bobz.library.Bobz;
import android.io.bobz.library.popup.v1.listener.DeepLink;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Console;


public class MainActivity extends AppCompatActivity {

    private final static String PROJECT_ID = "PROJECT-ID";
    private final static String DEEP_LINK_URI_1 = "/uri1";
    private final static String DEEP_LINK_URI_2 = "/uri2";
    private final static String DEEP_LINK_URI_3 = "/uri3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                Bobz.with(MainActivity.this).build(PROJECT_ID);

            }
        }, 2000);

    }

    @Override
    public void onStart() {

        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onPopupButtonClick(DeepLink deepLink) {

        Log.i("URI", deepLink.getUri().getPath());

        switch (deepLink.getUri().getPath()) {
            case DEEP_LINK_URI_1:
                break;
            case DEEP_LINK_URI_2:
                break;
            case DEEP_LINK_URI_3:
                break;
            default:
                break;

        }

        Toast.makeText(MainActivity.this, deepLink.getUri().getPath(), Toast.LENGTH_SHORT).show();
    }

}
