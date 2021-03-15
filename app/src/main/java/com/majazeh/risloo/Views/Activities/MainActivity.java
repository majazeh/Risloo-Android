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
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;
    private ActivityMainContentBinding contentBinding;

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

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.mainIncludeLayout.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        contentBinding = binding.mainIncludeLayout;

        InitManager.imgResTint(this, contentBinding.menuIncludeLayout.componentMainButton, R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.imgResTintRotate(this, contentBinding.logoutIncludeLayout.componentMainButton, R.drawable.ic_logout_light, R.color.Gray500, 180);
        InitManager.imgResTint(this, contentBinding.notificationIncludeLayout.componentMainButton, R.drawable.ic_bell_light, R.color.Gray500);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            contentBinding.accountIncludeLayout.componentMainToolbar.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);

            contentBinding.menuIncludeLayout.componentMainButton.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            contentBinding.logoutIncludeLayout.componentMainButton.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_red300);
            contentBinding.notificationIncludeLayout.componentMainButton.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
        }
    }

    private void listener() {
        contentBinding.accountIncludeLayout.componentMainToolbar.setOnClickListener(v -> {
            contentBinding.accountIncludeLayout.componentMainToolbar.setClickable(false);
            handler.postDelayed(() -> contentBinding.accountIncludeLayout.componentMainToolbar.setClickable(true), 300);

            navigator(R.id.accountFragment);
        });

        contentBinding.menuIncludeLayout.componentMainButton.setOnClickListener(v -> {
            contentBinding.menuIncludeLayout.componentMainButton.setClickable(false);
            handler.postDelayed(() -> contentBinding.menuIncludeLayout.componentMainButton.setClickable(true), 300);

            binding.getRoot().openDrawer(GravityCompat.START);
        });

        contentBinding.logoutIncludeLayout.componentMainButton.setOnClickListener(v -> {
            contentBinding.logoutIncludeLayout.componentMainButton.setClickable(false);
            handler.postDelayed(() -> contentBinding.logoutIncludeLayout.componentMainButton.setClickable(true), 300);

            // TODO : Place Code Here
        });

        contentBinding.notificationIncludeLayout.componentMainButton.setOnClickListener(v -> {
            contentBinding.notificationIncludeLayout.componentMainButton.setClickable(false);
            handler.postDelayed(() -> contentBinding.notificationIncludeLayout.componentMainButton.setClickable(true), 300);

            // TODO : Place Code Here
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            contentBinding.breadcumpTextView.setText(StringManager.clickableNavBackStack(this, controller));
            contentBinding.breadcumpTextView.setMovementMethod(LinkMovementMethod.getInstance());
        });
    }

    private void setData() {
        NavigationUI.setupWithNavController(binding.mainNavigationView, navController);

        if (singleton.getName().equals("")) {
            contentBinding.accountIncludeLayout.nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            contentBinding.accountIncludeLayout.nameTextView.setText(singleton.getName());
        }

        if (singleton.getMoney().equals("")) {
            contentBinding.accountIncludeLayout.moneyTextView.setVisibility(View.GONE);
        } else {
            contentBinding.accountIncludeLayout.moneyTextView.setText(singleton.getMoney());
        }

        if (singleton.getNotification().equals("")) {
            contentBinding.badgeTextView.setVisibility(View.GONE);
        } else {
            contentBinding.badgeTextView.setVisibility(View.VISIBLE);
            contentBinding.badgeTextView.setText(singleton.getNotification());
        }

        if (singleton.getAvatar().equals("")) {
            contentBinding.accountIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            contentBinding.accountIncludeLayout.charTextView.setText(StringManager.firstChars(contentBinding.accountIncludeLayout.nameTextView.getText().toString()));
        } else {
            contentBinding.accountIncludeLayout.charTextView.setVisibility(View.GONE);

            Picasso.get().load(singleton.getAvatar()).placeholder(R.color.Blue500).into(contentBinding.accountIncludeLayout.avatarImageView);
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
        return NavigationUI.navigateUp(navController, binding.getRoot());
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
        if (binding.getRoot().isDrawerOpen(GravityCompat.START)) {
            binding.getRoot().closeDrawer(GravityCompat.START);
        } else {
            if (!navController.popBackStack()) {
                finish();
            }
        }
    }

}