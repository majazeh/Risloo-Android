package com.majazeh.risloo.Views.Activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.Decoraton;
import com.majazeh.risloo.Utils.Entities.Fragmont;
import com.majazeh.risloo.Utils.Entities.Inputon;
import com.majazeh.risloo.Utils.Entities.Navigatoon;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Entities.Validatoon;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.databinding.ActivityAuthBinding;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    // Binding
    private ActivityAuthBinding binding;

    // Entities
    public Fragmont fragmont;
    public Inputon inputon;
    public Navigatoon navigatoon;
    public Singleton singleton;
    public Validatoon validatoon;

    // Objects
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
        Decoraton decoraton = new Decoraton(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            decoraton.showSystemUI(false, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.Red600), getResources().getColor(R.color.CoolGray50));

            binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            decoraton.showSystemUI(true, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.CoolGray50));

            binding.debugTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void initializer() {
        inputon = new Inputon(this);
        singleton = new Singleton(this);
        validatoon = new Validatoon(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());
        navigatoon = new Navigatoon(this, Objects.requireNonNull(navHostFragment));

        fragmont = new Fragmont(navHostFragment);

        extras = getIntent().getExtras();
    }

    private void setExtra() {
        if (extras != null) {
            if (extras.getString("theory") != null) {
                if (extras.getString("theory").equals("login"))
                    navigatoon.setStartDestinationId(R.id.authLoginFragment);
                else
                    navigatoon.setStartDestinationId(R.id.authRegisterFragment);
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
                    if (inputon.editText != null && inputon.editText.hasFocus()) {
                        inputon.clear(inputon.editText);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (navigatoon.getStartDestinationId() == R.id.authLoginFragment) {
            if (navigatoon.getCurrentDestinationId() != R.id.authLoginFragment && navigatoon.getCurrentDestinationId() != R.id.authSerialFragment)
                navigatoon.navigateUp();
            else
                IntentManager.finish(this);
        } else {
            if (navigatoon.getCurrentDestinationId() != R.id.authRegisterFragment && navigatoon.getCurrentDestinationId() != R.id.authSerialFragment)
                navigatoon.navigateUp();
            else
                IntentManager.finish(this);
        }
    }

}