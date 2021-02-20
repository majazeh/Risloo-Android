package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;

public class MainActivity extends AppCompatActivity {

    // Objects
    private Handler handler;

    // Widgets
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ConstraintLayout accountConstraintLayout;
    private ImageView avatarImageView;
    private TextView charTextView, nameTextView, numberTextView;
    private ImageView menuImageView, bellImageView;

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

        accountConstraintLayout = findViewById(R.id.activity_main_content_account_imageView);

        avatarImageView = findViewById(R.id.component_toolbar_rectangle_avatar_imageView);

        charTextView = findViewById(R.id.component_toolbar_rectangle_char_textView);
        nameTextView = findViewById(R.id.component_toolbar_rectangle_name_textView);
        numberTextView = findViewById(R.id.component_toolbar_rectangle_number_textView);

        menuImageView = findViewById(R.id.activity_main_content_menu_imageView);
        menuImageView.setImageResource(R.drawable.ic_bars_light);
        ImageViewCompat.setImageTintList(menuImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));
        bellImageView = findViewById(R.id.activity_main_content_bell_imageView);
        bellImageView.setImageResource(R.drawable.ic_bell_light);
        ImageViewCompat.setImageTintList(bellImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            accountConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);

            menuImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);
            bellImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);
        }
    }

    private void listener() {
        accountConstraintLayout.setOnClickListener(v -> {
            accountConstraintLayout.setClickable(false);
            handler.postDelayed(() -> accountConstraintLayout.setClickable(true), 300);


        });

        menuImageView.setOnClickListener(v -> {
            menuImageView.setClickable(false);
            handler.postDelayed(() -> menuImageView.setClickable(true), 300);

            drawerLayout.openDrawer(GravityCompat.START);
        });

        bellImageView.setOnClickListener(v -> {
            bellImageView.setClickable(false);
            handler.postDelayed(() -> bellImageView.setClickable(true), 300);


        });
    }

    private void setData() {
        charTextView.setText("ال");
        nameTextView.setText("دکتر جان بزرگی");
        numberTextView.setText("100.000");
    }

}