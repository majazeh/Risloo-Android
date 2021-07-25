package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.databinding.ActivityAuthBinding;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    // Binding
    private ActivityAuthBinding binding;

    // Entities
    public Singleton singleton;

    // Dialogs
    public LoadingDialog loadingDialog;

    // Objects
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        initializer();

        ExtendOnFailureException.activity = this;
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            windowDecorator.showSystemUI(false, true);
            windowDecorator.setSystemUIColor(getResources().getColor(R.color.Red500), getResources().getColor(R.color.Gray50));

            binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            windowDecorator.showSystemUI(true, true);
            windowDecorator.setSystemUIColor(getResources().getColor(R.color.Gray50), getResources().getColor(R.color.Gray50));

            binding.debugTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void initializer() {
        singleton = new Singleton(this);

        loadingDialog = new LoadingDialog();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();
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
        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.authLoginFragment && Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.authSerialFragment)
            navController.navigateUp();
        else
            IntentManager.finish(this);
    }

}