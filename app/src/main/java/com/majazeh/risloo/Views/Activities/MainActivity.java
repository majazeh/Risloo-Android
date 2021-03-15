package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.DateDialog;
import com.majazeh.risloo.Views.Dialogs.ImageDialog;
import com.majazeh.risloo.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;

    // Singleton
    public Singleton singleton;

    // Objects
    public Handler handler;
    public ImageDialog imageDialog;
    public DateDialog dateDialog;
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;

    // Widgets
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ConstraintLayout accountToolbar;
    private ImageView avatarImageView;
    private TextView charTextView, nameTextView, moneyTextView;
    private ImageView menuImageView, logoutImageView, notificationImageView;
    private TextView badgeTextView, breadTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        binder();

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

    private void binder() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initializer() {
        singleton = new Singleton(this);

        handler = new Handler();

        imageDialog = new ImageDialog();
        dateDialog = new DateDialog();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.activityMainContent.activityMainContentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        drawerLayout = binding.getRoot();

        navigationView = binding.activityMainNavigationView;

        accountToolbar = binding.activityMainContent.activityMainContentAccountImageView.componentMainToolbar;

        avatarImageView = binding.activityMainContent.activityMainContentAccountImageView.componentMainToolbarAvatarImageView;
        charTextView = binding.activityMainContent.activityMainContentAccountImageView.componentMainToolbarCharTextView;
        nameTextView = binding.activityMainContent.activityMainContentAccountImageView.componentMainToolbarNameTextView;
        moneyTextView = binding.activityMainContent.activityMainContentAccountImageView.componentMainToolbarMoneyTextView;

        menuImageView = binding.activityMainContent.activityMainContentMenuImageView.componentMainButton;
        InitManager.imageView(this, menuImageView, R.drawable.ic_bars_light, R.color.Gray500);

        logoutImageView = binding.activityMainContent.activityMainContentLogoutImageView.componentMainButton;
        InitManager.imageView(this, logoutImageView, R.drawable.ic_logout_light, R.color.Gray500);
        logoutImageView.setRotation(logoutImageView.getRotation() + 180);

        notificationImageView = binding.activityMainContent.activityMainContentNotificationImageView.componentMainButton;
        InitManager.imageView(this, notificationImageView, R.drawable.ic_bell_light, R.color.Gray500);

        badgeTextView = binding.activityMainContent.activityMainContentBadgeTextView.componentMainBadge;
        breadTextView = binding.activityMainContent.activityMainContentBreadTextView;
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            accountToolbar.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);

            menuImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            logoutImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_red300);
            notificationImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
        }
    }

    private void listener() {
        accountToolbar.setOnClickListener(v -> {
            accountToolbar.setClickable(false);
            handler.postDelayed(() -> accountToolbar.setClickable(true), 300);

            navigator(R.id.accountFragment);
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

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            breadTextView.setText(StringManager.clickableNavBackStack(this, controller));
            breadTextView.setMovementMethod(LinkMovementMethod.getInstance());
        });
    }

    private void setData() {
        NavigationUI.setupWithNavController(navigationView, navController);

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

    public void navigator(int destinationId) {
        try {
            if (navController.getBackStackEntry(destinationId).getDestination() != navController.getCurrentDestination()) {
                while (Objects.requireNonNull(navController.getCurrentDestination()).getId()!=destinationId) {
                    navController.popBackStack();
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.dashboardFragment  && destinationId == R.id.accountFragment) {
            navController.popBackStack();
        }
        navController.navigate(destinationId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    if (controlEditText.input() != null && controlEditText.input().hasFocus()) {
                        controlEditText.clear(this, controlEditText.input());
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!navController.popBackStack()) {
                finish();
            }
        }
    }

}