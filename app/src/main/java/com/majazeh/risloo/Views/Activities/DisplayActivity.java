package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Entities.Decorator;
import com.majazeh.risloo.databinding.ActivityDisplayBinding;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    // Binding
    private ActivityDisplayBinding binding;

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
        Decorator decorator = new Decorator(this);

        decorator.showSystemUI(false, false);
        decorator.setSystemUIColor(getResources().getColor(R.color.Black), getResources().getColor(R.color.Black));
    }

    private void initializer() {
        extras = getIntent().getExtras();
    }

    private void listener() {
        CustomClickView.onClickListener(() -> {
                IntentManager.finish(this);
        }).widget(binding.returnImageView);

        CustomClickView.onDelayedListener(() -> {
            if (PermissionManager.storagePermission(this))
                IntentManager.download(this, path);
        }).widget(binding.downloadImageView);
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

                Picasso.get().load(path).placeholder(R.color.CoolGray900).into(binding.avatarZoomageView);
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