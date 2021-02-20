package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;

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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    // Vars
    private String title = "", bitmap = "", path = "";

    // Objects
    private Bundle extras;
    private Handler handler;

    // Widgets
    private ImageView toolbarReturnImageView, toolbarDownloadImageView;
    private ZoomageView avatarZoomageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        setContentView(R.layout.activity_display);

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

    private void initializer() {
        extras = getIntent().getExtras();

        handler = new Handler();

        toolbarReturnImageView = findViewById(R.id.activity_display_return_imageView);
        toolbarReturnImageView.setImageResource(R.drawable.ic_angle_light);
        ImageViewCompat.setImageTintList(toolbarReturnImageView, AppCompatResources.getColorStateList(this, R.color.Gray50));
        toolbarDownloadImageView = findViewById(R.id.activity_display_download_imageView);
        toolbarDownloadImageView.setImageResource(R.drawable.ic_download_light);
        ImageViewCompat.setImageTintList(toolbarDownloadImageView, AppCompatResources.getColorStateList(this, R.color.Gray50));

        avatarZoomageView = findViewById(R.id.activity_display_avatar_zoomageView);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            toolbarReturnImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_gray200_ripple_gray300);
            toolbarDownloadImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray900_border_gray200_ripple_gray300);
        }
    }

    private void listener() {
        toolbarReturnImageView.setOnClickListener(v -> {
            toolbarReturnImageView.setClickable(false);
            handler.postDelayed(() -> toolbarReturnImageView.setClickable(true), 300);

            finish();
        });

        toolbarDownloadImageView.setOnClickListener(v -> {
            toolbarDownloadImageView.setClickable(false);
            handler.postDelayed(() -> toolbarDownloadImageView.setClickable(true), 300);

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
            toolbarDownloadImageView.setVisibility(View.VISIBLE);
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