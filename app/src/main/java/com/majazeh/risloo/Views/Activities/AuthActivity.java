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
import com.majazeh.risloo.Utils.Entities.Inputon;
import com.majazeh.risloo.Utils.Entities.Navigatoon;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Entities.Validatoon;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.databinding.ActivityAuthBinding;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    // Binding
    private ActivityAuthBinding binding;

    // Entities
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

        listener();

        setExtra();
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(true, true);
        decoraton.setSystemUIColor(getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.CoolGray50));

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.debugTextView.getRoot().setVisibility(View.GONE);
    }

    private void initializer() {
        inputon = new Inputon(this);
        singleton = new Singleton(this);
        validatoon = new Validatoon(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());
        navigatoon = new Navigatoon(this, Objects.requireNonNull(navHostFragment));

        extras = getIntent().getExtras();
    }

    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.risloo(this)).widget(binding.debugTextView.getRoot());
    }

    private void setExtra() {
        if (extras != null) {
            if (extras.getString("theory") != null) {
                if (extras.getString("theory").equals("login"))
                    navigatoon.setStartDestinationId(R.id.authLoginFragment);
                else if (extras.getString("theory").equals("register"))
                    navigatoon.setStartDestinationId(R.id.authRegisterFragment);
                else
                    navigatoon.setStartDestinationId(R.id.authPasswordRecoverFragment);
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
        } else if (navigatoon.getStartDestinationId() == R.id.authRegisterFragment) {
            if (navigatoon.getCurrentDestinationId() != R.id.authRegisterFragment && navigatoon.getCurrentDestinationId() != R.id.authSerialFragment)
                navigatoon.navigateUp();
            else
                IntentManager.finish(this);
        } else {
            if (navigatoon.getCurrentDestinationId() != R.id.authPasswordRecoverFragment && navigatoon.getCurrentDestinationId() != R.id.authSerialFragment)
                navigatoon.navigateUp();
            else
                IntentManager.finish(this);
        }
    }

}