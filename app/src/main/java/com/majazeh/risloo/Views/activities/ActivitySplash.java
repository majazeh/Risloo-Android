package com.majazeh.risloo.views.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.configs.ExtendException;
import com.majazeh.risloo.utils.entities.Decoraton;
import com.majazeh.risloo.utils.entities.Singleton;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.VersionManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.databinding.ActivitySplashBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.ClientModel;
import com.mre.ligheh.Model.TypeModel.VersionModel;

import org.json.JSONObject;

import java.util.HashMap;

@SuppressLint("CustomSplashScreen")
public class ActivitySplash extends AppCompatActivity {

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

        varianter();

        initializer();

        ExtendException.activity = this;

        setData();

        getData();
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(false, false);
        decoraton.colorSystemUI(getResources().getColor(R.color.risloo500), getResources().getColor(R.color.risloo500));
    }

    private void varianter() {
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
        binding.versionTextView.setText(VersionManager.versionNameDesc(this));
    }

    private void getData() {
        if (BuildConfig.BUILD_TYPE.equals("release"))
            explode();
        else
            navigate();
    }

    private void explode() {
        handler.postDelayed(() -> {
            binding.getRoot().transitionToState(R.id.end);

            Auth.explode(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    VersionModel versionModel = new VersionModel((JSONObject) object);
                    ClientModel clientModel = versionModel.getAndroid();

                    runOnUiThread(() -> {
                        binding.getRoot().transitionToState(R.id.start);

                        if (clientModel != null) {
                            update(clientModel);
                            return;
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
    }

    private void navigate() {
        handler.postDelayed(() -> {
            if (!singleton.getToken().equals(""))
                IntentManager.main(this);
            else
                IntentManager.auth(this, "login");
        }, 1000);
    }

    private void update(ClientModel model) {
        if (StringManager.compareVersionNames(VersionManager.versionNamePrefix(this), model.getForce()) == 1)
            SheetManager.showSheetVersion(this, model, "force");
        else if (StringManager.compareVersionNames(model.getForce(), model.getCurrent()) == 1)
            SheetManager.showSheetVersion(this, model, "current");
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}