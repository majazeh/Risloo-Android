package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    // Binding
    private ActivitySplashBinding binding;

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
    }

    private void initializer() {
        handler = new Handler();
    }

    private void setData() {
        String version = getResources().getString(R.string.SplashVersion) + " " + PackageManager.versionName(this);
        binding.versionTextView.setText(version);
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