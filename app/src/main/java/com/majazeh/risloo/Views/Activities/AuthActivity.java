package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.databinding.ActivityAuthBinding;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    // Binding
    private ActivityAuthBinding binding;

    // Singleton
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

        decorator();

        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializer();

        ExtendOnFailureException.activity = this;
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.lightShowSystemUI(this);
        windowDecorator.lightSetSystemUIColor(this, getResources().getColor(R.color.Gray50), getResources().getColor(R.color.Gray50));
    }

    private void initializer() {
        singleton = new Singleton(this);

        loadingDialog = new LoadingDialog();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    public void navigator(int destinationId, Bundle extras) {
        try {
            if (navController.getBackStackEntry(destinationId).getDestination() != navController.getCurrentDestination()) {
                while (Objects.requireNonNull(navController.getCurrentDestination()).getId() != destinationId) {
                    navController.popBackStack();
                }
                if (destinationId == navController.getGraph().getStartDestination()) {
                    navController.popBackStack();
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        navController.navigate(destinationId, extras);
    }

    public void login(AuthModel object) {
        UserModel user = object.getUser();

        if (object.getToken() != null)
            singleton.setToken(object.getToken());

        if (object.getToken() != null)
            singleton.setAuthorization("Bearer " + object.getToken());

        if (user.getUserId() != null)
            singleton.setUserId(user.getUserId());

        if (user.getName() != null)
            singleton.setName(user.getName());

        if (user.getUsername() != null)
            singleton.setUsername(user.getUsername());

        if (user.getBirthday() != null)
            singleton.setBirthday(user.getBirthday());

        if (user.getEmail() != null)
            singleton.setEmail(user.getEmail());

        if (user.getMobile() != null)
            singleton.setMobile(user.getMobile());

        if (user.getUserStatus() != null)
            singleton.setStatus(user.getUserStatus());

        if (user.getUserType() != null)
            singleton.setType(user.getUserType());

        if (user.getGender() != null)
            singleton.setGender(user.getGender());

        if (user.getAvatar() != null)
            if (user.getAvatar().getMedium() != null)
                if (user.getAvatar().getMedium().getUrl() != null)
                    singleton.setAvatar(user.getAvatar().getMedium().getUrl());

        if (user.getPublic_key() != null)
            singleton.setPublicKey(user.getPublic_key());

        if (user.getTreasuries() != null) {
            try {
                singleton.setMoney(String.valueOf(user.getTreasuries().getJSONObject(0).getInt("balance") + user.getTreasuries().getJSONObject(1).getInt("balance") + user.getTreasuries().getJSONObject(2).getInt("balance")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        navigator(R.id.authSerialFragment, null);
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
        if (!navController.popBackStack()) {
            finish();
        }
    }

}