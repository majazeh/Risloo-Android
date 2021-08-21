package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
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

        detector();

        listener();

        setExtra();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator(this);

        windowDecorator.showSystemUI(false, false);
        windowDecorator.setSystemUIColor(getResources().getColor(R.color.Gray900), getResources().getColor(R.color.Gray900));
    }

    private void initializer() {
        extras = getIntent().getExtras();

        InitManager.imgResTint(this, binding.returnImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Gray50);
        InitManager.imgResTint(this, binding.downloadImageView.getRoot(), R.drawable.ic_arrow_to_bottom_light, R.color.Gray50);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.returnImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);
            binding.downloadImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);

            binding.titleTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener() {
        CustomClickView.onClickListener(() -> {
                IntentManager.finish(this);
        }).widget(binding.returnImageView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            if (PermissionManager.storagePermission(this))
                IntentManager.download(this, path);
        }).widget(binding.downloadImageView.getRoot());
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

                Picasso.get().load(path).placeholder(R.color.Gray900).into(binding.avatarZoomageView);
                binding.downloadImageView.getRoot().setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                IntentManager.download(this, path);
            }
        }
    }

}