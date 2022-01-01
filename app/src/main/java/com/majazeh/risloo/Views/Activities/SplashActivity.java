package com.majazeh.risloo.Views.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.Decoraton;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PackageManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.databinding.ActivitySplashBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.VersionModel;

import org.json.JSONObject;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    // Binding
    private ActivitySplashBinding binding;

    // Entities
    public Singleton singleton;

    // Objects
    private HashMap data, header;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        initializer();

        ExtendException.activity = this;

        setData();

        getData();
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(false, false);
        decoraton.setSystemUIColor(getResources().getColor(R.color.Risloo500), getResources().getColor(R.color.Risloo500));

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.debugTextView.setVisibility(View.VISIBLE);
        else
            binding.debugTextView.setVisibility(View.GONE);
    }

    private void initializer() {
        singleton = new Singleton(this);

        data = new HashMap<>();
        header = new HashMap<>();

        handler = new Handler();
    }

    private void setData() {
        binding.versionTextView.setText(PackageManager.versionNameWithText(this));
    }

    private void getData() {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            handler.postDelayed(this::showProgress, 500);

            Auth.explode(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    VersionModel versionModel = new VersionModel((JSONObject) object);

                    runOnUiThread(() -> {
                        hideProgress();

                        if (versionModel.getAndroid() != null) {
                            if (StringManager.compareVersionNames(PackageManager.versionNameNoSuffix(SplashActivity.this), versionModel.getAndroid().getForce()) == 1) {
                                SheetManager.showVersionBottomSheet(SplashActivity.this, versionModel, "force");
                                return;
                            } else if (StringManager.compareVersionNames(versionModel.getAndroid().getForce(), versionModel.getAndroid().getCurrent()) == 1) {
                                SheetManager.showVersionBottomSheet(SplashActivity.this, versionModel, "current");
                                return;
                            }
                        }

                        navigate();
                    });
                }

                @Override
                public void onFailure(String response) {
                    runOnUiThread(() -> {
                        hideProgress();

                        navigate();
                    });
                }
            });
        } else {
            handler.postDelayed(this::navigate, 1000);
        }
    }

    private void navigate() {
        if (!singleton.getToken().equals(""))
            IntentManager.main(this);
        else
            IntentManager.auth(this, "login");
    }

    private void showProgress() {
        if (binding.explodeProgressBar.getVisibility() == View.GONE)
            binding.explodeProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        if (binding.explodeProgressBar.getVisibility() == View.VISIBLE)
            binding.explodeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}