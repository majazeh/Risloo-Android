package com.majazeh.risloo.views.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.ActivityDisplayBinding;
import com.majazeh.risloo.utils.entities.Decoraton;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.squareup.picasso.Picasso;

public class ActivityDisplay extends AppCompatActivity {

    // Binding
    private ActivityDisplayBinding binding;

    // Entities
    private Decoraton decoraton;

    // Objects
    private Bundle extras;

    // Vars
    private String title = "", path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        initializer();

        listener();

        setExtra();
    }

    private void decorator() {
        decoraton = new Decoraton(this);

        decoraton.showSystemUI(true, true);
        decoraton.colorSystemUI(getResources().getColor(R.color.coolGray50), getResources().getColor(R.color.coolGray50));
    }

    private void initializer() {
        extras = getIntent().getExtras();
    }

    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.finish(this)).widget(binding.returnImageView);

        CustomClickView.onClickListener(() -> IntentManager.share(this, path)).widget(binding.shareImageView);

        CustomClickView.onClickListener(() -> IntentManager.download(this, title, path)).widget(binding.downloadImageView);

        CustomClickView.onClickListener(() -> {
            if (binding.getRoot().getCurrentState() == R.id.end)
                decoraton.showSystemUI(true, true);
            else
                decoraton.immersiveHideSystemUI();
        }).widget(binding.avatarZoomageView);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                binding.getRoot().transitionToState(R.id.start);
            else
                binding.getRoot().transitionToState(R.id.end);
        });
    }

    private void setExtra() {
        if (extras != null) {
            if (!extras.getString("title").equals("")) {
                title = extras.getString("title");

                binding.titleTextView.setText(title);
            }

            if (!extras.getString("path").equals("")) {
                path = extras.getString("path");

                Picasso.get().load(path).placeholder(R.color.white).into(binding.avatarZoomageView);

                if (path.contains("https://") || path.contains(" http://")) {
                    binding.shareImageView.setVisibility(View.VISIBLE);
                    binding.downloadImageView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    if (requestCode == 300) {
                        ToastManager.showToastError(this, getResources().getString(R.string.ToastPermissionDownloadException));
                    }

                    return;
                }
            }

            if (requestCode == 300) {
                IntentManager.download(this, title, path);
            }
        }
    }

}