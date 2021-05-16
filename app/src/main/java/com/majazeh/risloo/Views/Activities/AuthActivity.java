package com.majazeh.risloo.Views.Activities;

import androidx.annotation.Nullable;
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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.databinding.ActivityAuthBinding;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

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

        if (!singleton.getToken().equals("")){
            IntentManager.main(this);
        }
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

    public void navigator(int destinationId) {
        navigator(destinationId, null);
    }

    public void login(AuthModel object) {
        UserModel user = object.getUser();
        loadingDialog.dismiss();
        if (object.getToken() != null) singleton.editor.putString("token", object.getToken());
        if (user.getUserId() != null) singleton.editor.putString("userId", user.getUserId());
        if (user.getName() != null) singleton.editor.putString("name", user.getName());
        if (user.getUsername() != null) singleton.editor.putString("username", user.getUsername());
        if (user.getBirthday() != null) singleton.editor.putString("birthday", user.getBirthday());
        if (user.getEmail() != null) singleton.editor.putString("email", user.getEmail());
        if (user.getMobile() != null) singleton.editor.putString("mobile", user.getMobile());
        if (user.getUserStatus() != null) singleton.editor.putString("status", user.getUserStatus());
        if (user.getUserType() != null) singleton.editor.putString("type", user.getUserType());
        if (user.getGender() != null) singleton.editor.putString("gender", user.getGender());
        String url = null;
        if (user.getAvatar() != null) url = user.getAvatar().getMedium().getUrl();
        if (url != null) singleton.editor.putString("avatar", url);
        if (user.getPublic_key() != null) singleton.editor.putString("public_key", user.getPublic_key());
        singleton.editor.apply();
        navigator(R.id.authSerialFragment);
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