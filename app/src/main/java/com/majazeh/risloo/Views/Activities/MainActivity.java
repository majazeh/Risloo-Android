package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.navigation.NavigationView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;

public class MainActivity extends AppCompatActivity {

    // Objects
    private Handler handler;

    // Widgets
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        setContentView(R.layout.activity_main);

        initializer();

        detector();

        listener();

        setData();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.lightNavShowSystemUI(this);
        windowDecorator.lightSetSystemUIColor(this, Color.TRANSPARENT, getResources().getColor(R.color.Gray50));
    }

    private void initializer() {
        handler = new Handler();

        drawerLayout = findViewById(R.id.activity_main);

        navigationView = findViewById(R.id.activity_main_navigationView);
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

}