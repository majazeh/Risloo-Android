package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.DateDialog;
import com.majazeh.risloo.Views.Dialogs.ImageDialog;
import com.majazeh.risloo.databinding.ActivityMainBinding;
import com.majazeh.risloo.databinding.ActivityMainContentBinding;
import com.majazeh.risloo.databinding.ComponentMainButtonBinding;
import com.majazeh.risloo.databinding.ComponentMainToolbarBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;
    private ActivityMainContentBinding contentBinding;
    private ComponentMainToolbarBinding accountBinding;
    private ComponentMainButtonBinding menuBinding, logoutBinding, notificationBinding;

    // Singleton
    public Singleton singleton;

    // Objects
    public Handler handler;
    public ImageDialog imageDialog;
    public DateDialog dateDialog;
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        imageDialog = new ImageDialog();
        dateDialog = new DateDialog();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.activityMainIncludeLayout.activityMainContentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        contentBinding = binding.activityMainIncludeLayout;

        accountBinding = contentBinding.activityMainContentAccountIncludeLayout;
        menuBinding = contentBinding.activityMainContentMenuIncludeLayout;
        logoutBinding = contentBinding.activityMainContentLogoutIncludeLayout;
        notificationBinding = contentBinding.activityMainContentNotificationIncludeLayout;

        InitManager.imgResTint(this, menuBinding.componentMainButton, R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.imgResTintRotate(this, logoutBinding.componentMainButton, R.drawable.ic_logout_light, R.color.Gray500, 180);
        InitManager.imgResTint(this, notificationBinding.componentMainButton, R.drawable.ic_bell_light, R.color.Gray500);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            accountBinding.componentMainToolbar.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);

            menuBinding.componentMainButton.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            logoutBinding.componentMainButton.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_red300);
            notificationBinding.componentMainButton.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
        }
    }

    private void listener() {
        accountBinding.componentMainToolbar.setOnClickListener(v -> {
            accountBinding.componentMainToolbar.setClickable(false);
            handler.postDelayed(() -> accountBinding.componentMainToolbar.setClickable(true), 300);

            navigator(R.id.accountFragment);
        });

        menuBinding.componentMainButton.setOnClickListener(v -> {
            menuBinding.componentMainButton.setClickable(false);
            handler.postDelayed(() -> menuBinding.componentMainButton.setClickable(true), 300);

            binding.activityMainDrawerLayout.openDrawer(GravityCompat.START);
        });

        logoutBinding.componentMainButton.setOnClickListener(v -> {
            logoutBinding.componentMainButton.setClickable(false);
            handler.postDelayed(() -> logoutBinding.componentMainButton.setClickable(true), 300);

            // TODO : Place Code Here
        });

        notificationBinding.componentMainButton.setOnClickListener(v -> {
            notificationBinding.componentMainButton.setClickable(false);
            handler.postDelayed(() -> notificationBinding.componentMainButton.setClickable(true), 300);

            // TODO : Place Code Here
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            contentBinding.activityMainContentBreadTextView.setText(StringManager.clickableNavBackStack(this, controller));
            contentBinding.activityMainContentBreadTextView.setMovementMethod(LinkMovementMethod.getInstance());
        });
    }

    private void setData() {
        NavigationUI.setupWithNavController(binding.activityMainNavigationView, navController);

        if (singleton.getName().equals("")) {
            accountBinding.componentMainToolbarNameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            accountBinding.componentMainToolbarNameTextView.setText(singleton.getName());
        }

        if (singleton.getMoney().equals("")) {
            accountBinding.componentMainToolbarMoneyTextView.setVisibility(View.GONE);
        } else {
            accountBinding.componentMainToolbarMoneyTextView.setText(singleton.getMoney());
        }

        if (singleton.getNotification().equals("")) {
            contentBinding.activityMainContentBadgeTextView.setVisibility(View.GONE);
        } else {
            contentBinding.activityMainContentBadgeTextView.setVisibility(View.VISIBLE);
            contentBinding.activityMainContentBadgeTextView.setText(singleton.getNotification());
        }

        if (singleton.getAvatar().equals("")) {
            accountBinding.componentMainToolbarCharTextView.setVisibility(View.VISIBLE);
            accountBinding.componentMainToolbarCharTextView.setText(StringManager.firstChars(accountBinding.componentMainToolbarNameTextView.getText().toString()));
        } else {
            accountBinding.componentMainToolbarCharTextView.setVisibility(View.GONE);

            Picasso.get().load(singleton.getAvatar()).placeholder(R.color.Blue500).into(accountBinding.componentMainToolbarAvatarImageView);
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
        return NavigationUI.navigateUp(navController, binding.activityMainDrawerLayout);
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
        if (binding.activityMainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.activityMainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!navController.popBackStack()) {
                finish();
            }
        }
    }

}