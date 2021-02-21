package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;

public class SplashActivity extends AppCompatActivity {

    // Singleton
    private Singleton singleton;

    // Objects
    private Handler handler;

    // Widgets
    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        setContentView(R.layout.activity_splash);

        initializer();

        detector();

        listener();

        setData();

        navigator();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.darkShowSystemUI(this);
        windowDecorator.darkSetSystemUIColor(this, getResources().getColor(R.color.Red500), getResources().getColor(R.color.Red500));
    }

    private void initializer() {
        singleton = new Singleton(this);

        handler = new Handler();

        versionTextView = findViewById(R.id.activity_splash_version_textView);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            // TODO : Place Code Here
        }
    }

    private void listener() {
        // TODO : Place Code Here
    }

    private void setData() {
        versionTextView.setText(getResources().getString(R.string.SplashVersion) + " " + PackageManager.versionName(this));
    }

    private void navigator() {
        if (singleton.getAuth()) {
            handler.postDelayed(() -> IntentManager.auth(this), 1000);
        } else {
            handler.postDelayed(() -> IntentManager.main(this), 1000);
        }
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}