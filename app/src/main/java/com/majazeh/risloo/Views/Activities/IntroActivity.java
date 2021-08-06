package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    // Binding
    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        detector();

        listener();

        setData();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator(this);

        windowDecorator.showSystemUI(true, true);
        windowDecorator.setSystemUIColor(getResources().getColor(R.color.White), getResources().getColor(R.color.Gray50));

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.headerIncludeLayout.debugTextView.setVisibility(View.VISIBLE);
        else
            binding.headerIncludeLayout.debugTextView.setVisibility(View.GONE);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.registerTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    private void listener() {
        ClickManager.onClickListener(() -> IntentManager.auth(this, "login")).widget(binding.headerIncludeLayout.loginTextView);

        ClickManager.onClickListener(() -> IntentManager.auth(this, "register")).widget(binding.registerTextView);
    }

    private void setData() {
        String version = getResources().getString(R.string.IntroVersion) + " " + PackageManager.versionNameWithoutSuffix(this);
        binding.versionTextView.setText(version);
    }

}