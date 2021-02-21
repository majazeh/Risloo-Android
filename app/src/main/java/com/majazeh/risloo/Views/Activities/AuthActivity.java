package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;

public class AuthActivity extends AppCompatActivity {

    // Singleton
    private Singleton singleton;

    // Objects
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        setContentView(R.layout.activity_auth);

        initializer();

        detector();

        listener();

        setData();

        navigator();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.lightShowSystemUI(this);
        windowDecorator.lightSetSystemUIColor(this, getResources().getColor(R.color.White), getResources().getColor(R.color.Gray50));
    }

    private void initializer() {
        singleton = new Singleton(this);

        handler = new Handler();
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
        // TODO : Place Code Here
    }

    private void navigator() {
        IntentManager.main(this);
        singleton.setAuth(false);
    }

}