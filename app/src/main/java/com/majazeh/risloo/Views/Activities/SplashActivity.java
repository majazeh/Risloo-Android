package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    // Binding
    private ActivitySplashBinding binding;

    // Singleton
    private Singleton singleton;

    // Objects
    private Handler handler;

    // Widgets
    private TextView versionTextView;
    private ImageView logoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        binder();

        initializer();

        detector();

        listener();

        setData();

        navigator();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.darkShowSystemUI(this);
        windowDecorator.darkSetSystemUIColor(this, getResources().getColor(R.color.Risloo500), getResources().getColor(R.color.Risloo500));
    }

    private void binder() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initializer() {
        singleton = new Singleton(this);

        handler = new Handler();

        versionTextView = binding.activitySplashVersionTextView;
        logoTextView = binding.activitySplashLogoImageView;
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            // TODO : Place Code Here
        }
    }

    private void listener() {
        // TODO : Place Code Here
    }

    private void setData() {
        versionTextView.setText(getResources().getString(R.string.SplashVersion) + " " + PackageManager.versionName(this));
    }

    private void navigator() {
        handler.postDelayed(() -> IntentManager.auth(this), 1000);
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}