package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;

public class MainActivity extends AppCompatActivity {

    // Objects
    private Handler handler;

    // Widgets
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView toolbarMenuImageView, toolbarBellImageView;

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

        toolbarMenuImageView = findViewById(R.id.activity_main_content_menu_imageView);
        toolbarMenuImageView.setImageResource(R.drawable.ic_bars_light);
        ImageViewCompat.setImageTintList(toolbarMenuImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));
        toolbarBellImageView = findViewById(R.id.activity_main_content_bell_imageView);
        toolbarBellImageView.setImageResource(R.drawable.ic_bell_light);
        ImageViewCompat.setImageTintList(toolbarBellImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            toolbarMenuImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);
            toolbarBellImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);
        }
    }

    private void listener() {
        toolbarMenuImageView.setOnClickListener(v -> {
            toolbarMenuImageView.setClickable(false);
            handler.postDelayed(() -> toolbarMenuImageView.setClickable(true), 300);

            drawerLayout.openDrawer(GravityCompat.START);
        });

        toolbarBellImageView.setOnClickListener(v -> {
            toolbarBellImageView.setClickable(false);
            handler.postDelayed(() -> toolbarBellImageView.setClickable(true), 300);


        });
    }

    private void setData() {
        // TODO : Place Code Here
    }

}