package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
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
    private Handler handler;

    // Vars
    private String title = "", bitmap = "", path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        binder();

        initializer();

        detector();

        listener();

        setData();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.darkShowSystemUI(this);
        windowDecorator.darkSetSystemUIColor(this, getResources().getColor(R.color.Gray900), getResources().getColor(R.color.Gray900));
    }

    private void binder() {
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initializer() {
        extras = getIntent().getExtras();

        handler = new Handler();

        InitManager.imgResTint(this, binding.returnImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Gray50);
        InitManager.imgResTint(this, binding.downloadImageView.getRoot(), R.drawable.ic_arrow_to_bottom_light, R.color.Gray50);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.returnImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);
            binding.downloadImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener() {
        binding.returnImageView.getRoot().setOnClickListener(v -> {
            binding.returnImageView.getRoot().setClickable(false);
            handler.postDelayed(() -> binding.returnImageView.getRoot().setClickable(true), 300);

            finish();
        });

        binding.downloadImageView.getRoot().setOnClickListener(v -> {
            binding.downloadImageView.getRoot().setClickable(false);
            handler.postDelayed(() -> binding.downloadImageView.getRoot().setClickable(true), 300);

            if (PermissionManager.storagePermission(this)) {
                IntentManager.download(this, bitmap);
            }
        });
    }

    private void setData() {
        if (extras.getString("title") != null) {
            title = extras.getString("title");
        }
        if (extras.getString("bitmap") != null) {
            bitmap = extras.getString("bitmap");
        }
        if (extras.getString("path") != null) {
            path = extras.getString("path");
        }

        if (!title.equals("")) {
            // TODO ; Place Code If Needed
        }
        if (!bitmap.equals("")) {
            Picasso.get().load(bitmap).placeholder(R.color.Gray900).into(binding.avatarZoomageView);
            binding.avatarZoomageView.setVisibility(View.VISIBLE);
        }
        if (!path.equals("")) {
            binding.avatarZoomageView.setImageBitmap(BitmapManager.modifyOrientation(FileManager.readBitmapFromCache(this, "bitmap"), path));
            FileManager.deleteFileFromCache(this, "bitmap");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                IntentManager.download(this, bitmap);
            }
        }
    }

}