package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;
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

    // Widgets
    private ImageView returnImageView, downloadImageView;
    private ZoomageView avatarZoomageView;

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

        returnImageView = binding.activityDisplayReturnImageView.componentMainButton;
        InitManager.imageView(this, returnImageView, R.drawable.ic_angle_right_regular, R.color.Gray50);

        downloadImageView = binding.activityDisplayDownloadImageView.componentMainButton;
        InitManager.imageView(this, returnImageView, R.drawable.ic_download_light, R.color.Gray50);

        avatarZoomageView = binding.activityDisplayAvatarZoomageView;
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            returnImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);
            downloadImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener() {
        returnImageView.setOnClickListener(v -> {
            returnImageView.setClickable(false);
            handler.postDelayed(() -> returnImageView.setClickable(true), 300);

            finish();
        });

        downloadImageView.setOnClickListener(v -> {
            downloadImageView.setClickable(false);
            handler.postDelayed(() -> downloadImageView.setClickable(true), 300);

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
            Picasso.get().load(bitmap).placeholder(R.color.Gray900).into(avatarZoomageView);
            downloadImageView.setVisibility(View.VISIBLE);
        }
        if (!path.equals("")) {
            avatarZoomageView.setImageBitmap(BitmapManager.modifyOrientation(FileManager.readBitmapFromCache(this, "bitmap"), path));
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