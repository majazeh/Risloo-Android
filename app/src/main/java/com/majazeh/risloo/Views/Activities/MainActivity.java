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

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.mainContent.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        InitManager.imgResTint(this, binding.mainContent.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.imgResTintRotate(this, binding.mainContent.logoutImageView.getRoot(), R.drawable.ic_sign_out_light, R.color.Gray500, 180);
        InitManager.imgResTint(this, binding.mainContent.notificationImageView.getRoot(), R.drawable.ic_bell_light, R.color.Gray500);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.mainContent.toolbarIncludeLayout.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);

            binding.mainContent.menuImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            binding.mainContent.logoutImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_red300);
            binding.mainContent.notificationImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
        }
    }

    private void listener() {
        binding.mainContent.toolbarIncludeLayout.getRoot().setOnClickListener(v -> {
            binding.mainContent.toolbarIncludeLayout.getRoot().setClickable(false);
            handler.postDelayed(() -> binding.mainContent.toolbarIncludeLayout.getRoot().setClickable(true), 300);

            navigator(R.id.accountFragment);
        });

        binding.mainContent.menuImageView.getRoot().setOnClickListener(v -> {
            binding.mainContent.menuImageView.getRoot().setClickable(false);
            handler.postDelayed(() -> binding.mainContent.menuImageView.getRoot().setClickable(true), 300);

            binding.getRoot().openDrawer(GravityCompat.START);
        });

        binding.mainContent.logoutImageView.getRoot().setOnClickListener(v -> {
            binding.mainContent.logoutImageView.getRoot().setClickable(false);
            handler.postDelayed(() -> binding.mainContent.logoutImageView.getRoot().setClickable(true), 300);

            // TODO : Place Code Here
        });

        binding.mainContent.notificationImageView.getRoot().setOnClickListener(v -> {
            binding.mainContent.notificationImageView.getRoot().setClickable(false);
            handler.postDelayed(() -> binding.mainContent.notificationImageView.getRoot().setClickable(true), 300);

            // TODO : Place Code Here
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding.mainContent.breadcumpTextView.setText(StringManager.clickableNavBackStack(this, controller));
            binding.mainContent.breadcumpTextView.setMovementMethod(LinkMovementMethod.getInstance());
        });
    }

    private void setData() {
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        if (singleton.getName().equals("")) {
            binding.mainContent.toolbarIncludeLayout.nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            binding.mainContent.toolbarIncludeLayout.nameTextView.setText(singleton.getName());
        }

        if (singleton.getMoney().equals("")) {
            binding.mainContent.toolbarIncludeLayout.moneyTextView.setVisibility(View.GONE);
        } else {
            binding.mainContent.toolbarIncludeLayout.moneyTextView.setText(singleton.getMoney());
        }

        if (singleton.getNotification().equals("")) {
            binding.mainContent.badgeTextView.setVisibility(View.GONE);
        } else {
            binding.mainContent.badgeTextView.setVisibility(View.VISIBLE);
            binding.mainContent.badgeTextView.setText(singleton.getNotification());
        }

        if (singleton.getAvatar().equals("")) {
            binding.mainContent.toolbarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.mainContent.toolbarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.mainContent.toolbarIncludeLayout.nameTextView.getText().toString()));
        } else {
            binding.mainContent.toolbarIncludeLayout.charTextView.setVisibility(View.GONE);

            Picasso.get().load(singleton.getAvatar()).placeholder(R.color.Blue500).into(binding.mainContent.toolbarIncludeLayout.avatarImageView);
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