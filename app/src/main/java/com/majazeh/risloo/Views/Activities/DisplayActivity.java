package com.majazeh.risloo.Views.Activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Decorator;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.TransitionManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.databinding.ActivityDisplayBinding;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    // Binding
    private ActivityDisplayBinding binding;

    // Entities
    private Decorator decorator;

    // Objects
    private Bundle extras;

    // Vars
    private String title = "", path = "";
    private boolean systemUiVisibility = true;

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
        decorator = new Decorator(this);

        decorator.showSystemUI(true, true);
        decorator.setSystemUIColor(getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.CoolGray50));
    }

    private void initializer() {
        extras = getIntent().getExtras();
    }

    private void listener() {
        CustomClickView.onClickListener(() -> {
                IntentManager.finish(this);
        }).widget(binding.returnImageView);

        CustomClickView.onDelayedListener(() -> {
            IntentManager.share(this, path, getResources().getString(R.string.AppShareImage));
        }).widget(binding.shareImageView);

        CustomClickView.onDelayedListener(() -> {
            if (PermissionManager.storagePermission(this))
                IntentManager.download(this, path);
        }).widget(binding.downloadImageView);

        CustomClickView.onClickListener(() -> {
            if (!systemUiVisibility)
                decorator.showSystemUI(true, true);
            else
                decorator.immersiveHideSystemUI();
        }).widget(binding.avatarZoomageView);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                systemUiVisibility = true;

                TransitionManager.reverseTransition(binding.getRoot());
                TransitionManager.reverseTransition(binding.toolbarContainer);
                TransitionManager.reverseTransition(binding.topView);
                TransitionManager.reverseTransition(binding.bottomView);

                binding.toolbarContainer.setVisibility(View.VISIBLE);
                binding.topView.setVisibility(View.VISIBLE);
                binding.bottomView.setVisibility(View.VISIBLE);
            } else {
                systemUiVisibility = false;

                TransitionManager.startTransition(binding.getRoot());
                TransitionManager.startTransition(binding.toolbarContainer);
                TransitionManager.startTransition(binding.topView);
                TransitionManager.startTransition(binding.bottomView);

                binding.toolbarContainer.setVisibility(View.GONE);
                binding.topView.setVisibility(View.GONE);
                binding.bottomView.setVisibility(View.GONE);
            }
        });
    }

    private void setExtra() {
        if (extras != null) {
            if (!extras.getString("title").equals("")) {
                title = extras.getString("title");

                binding.titleTextView.setText(title);
                binding.titleTextView.setVisibility(View.VISIBLE);
            }

            if (!extras.getString("path").equals("")) {
                path = extras.getString("path");

                Picasso.get().load(path).placeholder(R.color.White).into(binding.avatarZoomageView);
                binding.shareImageView.setVisibility(View.VISIBLE);
                binding.downloadImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED)
                    return;
            }

            if (requestCode == 200)
                IntentManager.download(this, path);
        }
    }

}