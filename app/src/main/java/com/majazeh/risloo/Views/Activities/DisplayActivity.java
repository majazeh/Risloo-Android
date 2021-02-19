package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

//    // Vars
//    private String title = "", bitmap = "", path = "";
//
//    // Objects
//    private Bundle extras;
//    private Handler handler;
//
//    // Widgets
//    private ImageView toolbarBackImageView, toolbarDownloadImageView;
//    private TextView toolbarTextView;
//    private ZoomageView avatarZoomageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        decorator();

        setContentView(R.layout.activity_display);

//        initializer();
//
//        detector();
//
//        listener();
//
//        setData();
    }

//    private void decorator() {
//        WindowDecorator windowDecorator = new WindowDecorator();
//
//        windowDecorator.darkShowSystemUI(this);
//        windowDecorator.darkSetSystemUIColor(this, getResources().getColor(R.color.Gray900), getResources().getColor(R.color.Gray900));
//    }
//
//    private void initializer() {
//        extras = getIntent().getExtras();
//
//        handler = new Handler();
//
//        toolbarTextView = findViewById(R.id.component_toolbar_textView);
//
//        toolbarBackImageView = findViewById(R.id.component_toolbar_primary_imageView);
//        toolbarDownloadImageView = findViewById(R.id.component_toolbar_secondary_imageView);
//
//        avatarZoomageView = findViewById(R.id.activity_display_avatar_zoomageView);
//    }
//
//    private void detector() {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            toolbarBackImageView.setBackgroundResource(R.drawable.draw_oval_solid_gray900_ripple_white);
//            toolbarDownloadImageView.setBackgroundResource(R.drawable.draw_oval_solid_gray900_ripple_white);
//        }
//    }
//
//    private void listener() {
//        toolbarBackImageView.setOnClickListener(v -> {
//            toolbarBackImageView.setClickable(false);
//            handler.postDelayed(() -> toolbarBackImageView.setClickable(true), 250);
//
//            finish();
//        });
//
//        toolbarDownloadImageView.setOnClickListener(v -> {
//            toolbarDownloadImageView.setClickable(false);
//            handler.postDelayed(() -> toolbarDownloadImageView.setClickable(true), 250);
//
//            if (PermissionManager.storagePermission(this)) {
//                IntentManager.download(this, bitmap);
//            }
//        });
//    }
//
//    private void setData() {
//        if (extras.getString("title") != null) {
//            title = extras.getString("title");
//        }
//        if (extras.getString("bitmap") != null) {
//            bitmap = extras.getString("bitmap");
//        }
//        if (extras.getString("path") != null) {
//            path = extras.getString("path");
//        }
//
//        if (!title.equals("")) {
//            toolbarTextView.setText(title);
//        }
//        if (!bitmap.equals("")) {
//            Picasso.get().load(bitmap).placeholder(R.color.Gray900).into(avatarZoomageView);
//            toolbarDownloadImageView.setVisibility(View.VISIBLE);
//        }
//        if (!path.equals("")) {
//            avatarZoomageView.setImageBitmap(BitmapManager.modifyOrientation(FileManager.readBitmapFromCache(this, "bitmap"), path));
//            FileManager.deleteFileFromCache(this, "bitmap");
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 200) {
//            if (grantResults.length > 0) {
//                for (int grantResult : grantResults) {
//                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                }
//                IntentManager.download(this, bitmap);
//            }
//        }
//    }

}