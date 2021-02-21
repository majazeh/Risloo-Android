package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;

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
    }

    private void decorator() {

    }

    private void initializer() {
        singleton = new Singleton(this);

        handler = new Handler();
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

        }
    }

    private void listener() {

    }

    private void setData() {

    }

}