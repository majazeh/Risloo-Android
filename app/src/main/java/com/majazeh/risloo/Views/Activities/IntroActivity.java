package com.majazeh.risloo.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Entities.Decoraton;
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

        listener();

        setData();
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(true, true);
        decoraton.setSystemUIColor(getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.headerIncludeLayout.debugTextView.setVisibility(View.VISIBLE);
        else
            binding.headerIncludeLayout.debugTextView.setVisibility(View.GONE);
    }

    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.auth(this, "login")).widget(binding.headerIncludeLayout.loginTextView);

        CustomClickView.onClickListener(() -> IntentManager.auth(this, "register")).widget(binding.registerTextView);
    }

    private void setData() {
        binding.descriptionTextView.setText(StringManager.foregroundStyle(getResources().getString(R.string.IntroDescription), 72, 92, getResources().getColor(R.color.CoolGray800), R.style.danaDemiBoldTextStyle));

        String version = getResources().getString(R.string.IntroVersion) + " " + PackageManager.versionNameWithoutSuffix(this);
        binding.versionTextView.setText(version);
    }

}