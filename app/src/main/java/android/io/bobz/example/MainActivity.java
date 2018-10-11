package android.io.bobz.example;

import android.io.bobz.library.Bobz;
import android.io.bobz.library.BobzBuilder;
import android.io.bobz.library.popup.v1.listener.ButtonClicked;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {

    private final static String PROJECT_ID = "PROJECT-ID";


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
    public void onButtonClicked(ButtonClicked buttonClicked) {

        Log.i("Event", "Main Activity Event Subscribed");
        Toast.makeText(MainActivity.this, buttonClicked.getData(), Toast.LENGTH_SHORT).show();

    }

}
