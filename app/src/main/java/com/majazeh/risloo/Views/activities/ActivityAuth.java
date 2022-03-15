package com.majazeh.risloo.views.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.configs.ExtendException;
import com.majazeh.risloo.utils.entities.Decoraton;
import com.majazeh.risloo.utils.entities.Inputon;
import com.majazeh.risloo.utils.entities.Navigatoon;
import com.majazeh.risloo.utils.entities.Singleton;
import com.majazeh.risloo.utils.entities.Validatoon;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.databinding.ActivityAuthBinding;

import java.util.Objects;

public class ActivityAuth extends AppCompatActivity {

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

        varianter();

        initializer();

        ExtendException.activity = this;

        listener();

        setExtra();
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(true, true);
        decoraton.colorSystemUI(getResources().getColor(R.color.coolGray50), getResources().getColor(R.color.coolGray50));
    }

    private void varianter() {
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
            if (extras.getString("theory").equals("recover"))
                navigatoon.setStartDestinationId(R.id.fragmentAuthPasswordRecover);
            else if (extras.getString("theory").equals("register"))
                navigatoon.setStartDestinationId(R.id.fragmentAuthRegister);
            else
                navigatoon.setStartDestinationId(R.id.fragmentAuthLogin);
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
                    if (inputon.editText() != null && inputon.editText().hasFocus()) {
                        inputon.clear();
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (navigatoon.getCurrentDestinationId() != navigatoon.getStartDestinationId() && navigatoon.getCurrentDestinationId() != R.id.fragmentAuthSerial)
            navigatoon.navigateUp();
        else
            IntentManager.finish(this);
    }

}