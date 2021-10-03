package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Entities.Inputor;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Entities.Decorator;
import com.majazeh.risloo.Utils.Entities.Validatoon;
import com.majazeh.risloo.databinding.ActivityAuthBinding;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    // Binding
    private ActivityAuthBinding binding;

    // Entities
    public Inputor inputor;
    public Singleton singleton;
    public Validatoon validatoon;

    // Objects
    private NavHostFragment navHostFragment;
    public NavController navController;
    private NavGraph navGraph;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        initializer();

        ExtendException.activity = this;

        setExtra();
    }

    private void decorator() {
        Decorator decorator = new Decorator(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            decorator.showSystemUI(false, true);
            decorator.setSystemUIColor(getResources().getColor(R.color.Red600), getResources().getColor(R.color.CoolGray50));

            binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            decorator.showSystemUI(true, true);
            decorator.setSystemUIColor(getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.CoolGray50));

            binding.debugTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void initializer() {
        inputor = new Inputor();

        singleton = new Singleton(this);

        validatoon = new Validatoon();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        navGraph = navController.getNavInflater().inflate(R.navigation.navigation_auth);

        extras = getIntent().getExtras();
    }

    private void setExtra() {
        if (extras != null) {
            if (extras.getString("theory") != null) {
                if (extras.getString("theory").equals("login"))
                    navGraph.setStartDestination(R.id.authLoginFragment);
                else
                    navGraph.setStartDestination(R.id.authRegisterFragment);

                navController.setGraph(navGraph);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    if (inputor.editText != null && inputor.editText.hasFocus()) {
                        inputor.clear(this, inputor.editText);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (navGraph.getStartDestination() == R.id.authLoginFragment) {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.authLoginFragment && Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.authSerialFragment)
                navController.navigateUp();
            else
                IntentManager.finish(this);
        } else {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.authRegisterFragment && Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.authSerialFragment)
                navController.navigateUp();
            else
                IntentManager.finish(this);
        }
    }

}