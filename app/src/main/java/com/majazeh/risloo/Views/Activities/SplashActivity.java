package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    // Binding
    private ActivitySplashBinding binding;

    // Entities
    private Singleton singleton;

    // Objects
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        initializer();

        setData();

        navigator();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator(this);

        windowDecorator.showSystemUI(false, false);
        windowDecorator.setSystemUIColor(getResources().getColor(R.color.Risloo500), getResources().getColor(R.color.Risloo500));

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.debugTextView.getRoot().setVisibility(View.GONE);
    }

    private void initializer() {
        singleton = new Singleton(this);

        handler = new Handler();
    }

    private void setData() {
        String version = getResources().getString(R.string.SplashVersion) + " " + PackageManager.versionNameWithoutSuffix(this);
        binding.versionTextView.setText(version);
    }

    private void navigator() {
        handler.postDelayed(() -> {
            if (!singleton.getToken().equals(""))
                IntentManager.main(this);
            else
                IntentManager.intro(this);
        }, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}