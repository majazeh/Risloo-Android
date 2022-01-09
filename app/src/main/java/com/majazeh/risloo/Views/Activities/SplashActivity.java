package com.majazeh.risloo.Views.Activities;

import android.annotation.SuppressLint;
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

@SuppressLint("CustomSplashScreen")
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
            binding.debugTextView.setVisibility(View.INVISIBLE);
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
            handler.postDelayed(() -> {
                binding.getRoot().transitionToState(R.id.end);

                Auth.explode(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        VersionModel versionModel = new VersionModel((JSONObject) object);

                        runOnUiThread(() -> {
                            binding.getRoot().transitionToState(R.id.start);

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
                            binding.getRoot().transitionToState(R.id.start);

                            navigate();
                        });
                    }
                });
            }, 500);
        } else {
            navigate();
        }
    }

    private void navigate() {
        handler.postDelayed(() -> {
            if (!singleton.getToken().equals(""))
                IntentManager.main(this);
            else
                IntentManager.auth(this, "login");
        }, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}