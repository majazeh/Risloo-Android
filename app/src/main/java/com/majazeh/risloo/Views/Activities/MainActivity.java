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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    // Singleton
    private Singleton singleton;

    // Objects
    private Handler handler;

    // Widgets
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ConstraintLayout accountConstraintLayout;
    private ImageView avatarImageView;
    private TextView charTextView, nameTextView, moneyTextView;
    private ImageView menuImageView, logoutImageView, notificationImageView;
    private TextView badgeTextView, locationTextView;

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
        singleton = new Singleton(this);

        handler = new Handler();

        drawerLayout = findViewById(R.id.activity_main);

        navigationView = findViewById(R.id.activity_main_navigationView);

        accountConstraintLayout = findViewById(R.id.activity_main_content_account_imageView);

        avatarImageView = findViewById(R.id.component_toolbar_rectangle_avatar_imageView);

        charTextView = findViewById(R.id.component_toolbar_rectangle_char_textView);
        nameTextView = findViewById(R.id.component_toolbar_rectangle_name_textView);
        moneyTextView = findViewById(R.id.component_toolbar_rectangle_money_textView);

        menuImageView = findViewById(R.id.activity_main_content_menu_imageView);
        menuImageView.setImageResource(R.drawable.ic_bars_light);
        ImageViewCompat.setImageTintList(menuImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));
        logoutImageView = findViewById(R.id.activity_main_content_logout_imageView);
        logoutImageView.setImageResource(R.drawable.ic_logout_light);
        ImageViewCompat.setImageTintList(logoutImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));
        logoutImageView.setRotation(logoutImageView.getRotation() + 180);
        notificationImageView = findViewById(R.id.activity_main_content_notification_imageView);
        notificationImageView.setImageResource(R.drawable.ic_bell_light);
        ImageViewCompat.setImageTintList(notificationImageView, AppCompatResources.getColorStateList(this, R.color.Gray500));

        badgeTextView = findViewById(R.id.activity_main_content_badge_textView);
        locationTextView = findViewById(R.id.activity_main_content_location_textView);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            accountConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);

            menuImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);
            logoutImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_red400);
            notificationImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_gray300_ripple_gray400);
        }
    }

    private void listener() {
        accountConstraintLayout.setOnClickListener(v -> {
            accountConstraintLayout.setClickable(false);
            handler.postDelayed(() -> accountConstraintLayout.setClickable(true), 300);

            // TODO : Place Code Here
        });

        menuImageView.setOnClickListener(v -> {
            menuImageView.setClickable(false);
            handler.postDelayed(() -> menuImageView.setClickable(true), 300);

            drawerLayout.openDrawer(GravityCompat.START);
        });

        logoutImageView.setOnClickListener(v -> {
            logoutImageView.setClickable(false);
            handler.postDelayed(() -> logoutImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        notificationImageView.setOnClickListener(v -> {
            notificationImageView.setClickable(false);
            handler.postDelayed(() -> notificationImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData() {
        if (singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(singleton.getName());
        }

        if (singleton.getMoney().equals("")) {
            moneyTextView.setVisibility(View.GONE);
        } else {
            moneyTextView.setText(singleton.getMoney());
        }

        if (singleton.getNotification().equals("")) {
            badgeTextView.setVisibility(View.GONE);
        } else {
            badgeTextView.setVisibility(View.VISIBLE);
            badgeTextView.setText(singleton.getNotification());
        }

        if (singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(singleton.getAvatar()).placeholder(R.color.Blue500).into(avatarImageView);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

}