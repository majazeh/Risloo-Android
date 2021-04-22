package com.majazeh.risloo.Views.Activities;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.databinding.ActivityTestBinding;

import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    // Binding
    private ActivityTestBinding binding;

    // Singleton
    public Singleton singleton;

    // Objects
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;
    private Bundle extras;

    // Vars
    private String test = "", type = "", status = "", location = "", locationSum = "";
    private int progress = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator(true);

        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializer();

        detector();

        listener();

        setData();
    }

    private void decorator(boolean dark) {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.lightShowSystemUI(this);
        if (dark)
            windowDecorator.darkSetSystemUIColor(this, getResources().getColor(R.color.Blue600), getResources().getColor(R.color.Gray50));
        else
            windowDecorator.lightSetSystemUIColor(this, getResources().getColor(R.color.White), getResources().getColor(R.color.Gray50));
    }

    private void initializer() {
        singleton = new Singleton(this);

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        extras = getIntent().getExtras();

        InitManager.spinner(this, binding.locationIncludeLayout.selectSpinner, R.array.TestStages, "test");

        InitManager.imgResTint(this, binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Gray500);
        InitManager.imgResTintRotate(this, binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Gray500, 180);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.backwardImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            binding.forwardImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);

            binding.locationIncludeLayout.selectSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onClickListener(() -> {
            switch (location) {
                case "زنجیره":
                    finish();
                    break;
                case "اطلاعات":
                    location = "زنجیره";
                    navigator(R.id.testBulkFragment);
                    break;
                case "توضیحات":
                    location = "اطلاعات";
                    navigator(R.id.testFormFragment);
                    break;
                case "یک":
                    location = "توضیحات";
                    navigator(R.id.testDescriptionFragment);
                    break;
                case "دو":
                    location = "یک";
                    navigator(R.id.testOptionalFragment);
                    break;
                case "پایان":
                    location = "دو";
                    navigator(R.id.testPictoralFragment);
                    break;
            }

            for (int i=0; i<binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(location)) {
                    binding.locationIncludeLayout.selectSpinner.setSelection(i);
                }
            }

            locationSum = "185" + " / "  + location;
            binding.locationSumTextView.setText(locationSum);

        }).widget(binding.backwardImageView.getRoot());

        ClickManager.onClickListener(() -> {
            switch (location) {
                case "زنجیره":
                    location = "اطلاعات";
                    navigator(R.id.testFormFragment);
                    break;
                case "اطلاعات":
                    location = "توضیحات";
                    navigator(R.id.testDescriptionFragment);
                    break;
                case "توضیحات":
                    location = "یک";
                    navigator(R.id.testOptionalFragment);
                    break;
                case "یک":
                    location = "دو";
                    navigator(R.id.testPictoralFragment);
                    break;
                case "دو":
                    location = "پایان";
                    navigator(R.id.testEndFragment);
                    break;
            }

            for (int i=0; i<binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(location)) {
                    binding.locationIncludeLayout.selectSpinner.setSelection(i);
                }
            }

            locationSum = "185" + " / "  + location;
            binding.locationSumTextView.setText(locationSum);

        }).widget(binding.forwardImageView.getRoot());

        binding.locationIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = parent.getItemAtPosition(position).toString();

                switch (location) {
                    case "زنجیره":
                        navigator(R.id.testBulkFragment);
                        break;
                    case "اطلاعات":
                        navigator(R.id.testFormFragment);
                        break;
                    case "توضیحات":
                        navigator(R.id.testDescriptionFragment);
                        break;
                    case "یک":
                        navigator(R.id.testOptionalFragment);
                        break;
                    case "دو":
                        navigator(R.id.testPictoralFragment);
                        break;
                    case "پایان":
                        navigator(R.id.testEndFragment);
                        break;
                }

                locationSum = "185" + " / "  + location;
                binding.locationSumTextView.setText(locationSum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData() {
        if (extras.getString("test") != null) {
            test = extras.getString("test");
        }

        if (!test.equals("")) {
            binding.headerIncludeLayout.titleTextView.setText(test);

            // TODO : Place Code Here
        }

        test = "پرسشنامه 16 عاملی شخصیت کتل";
        binding.headerIncludeLayout.titleTextView.setText(test);

        type = "";
        binding.headerIncludeLayout.typeTextView.setText(type);

        progress = 25;
        binding.headerIncludeLayout.loadingProgressBar.setProgress(progress);

        status = getResources().getString(R.string.TestUnChanged);
        binding.statusTextView.setText(status);

        location = "زنجیره";
        for (int i=0; i<binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
            if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(location)) {
                binding.locationIncludeLayout.selectSpinner.setSelection(i);
            }
        }

        locationSum = "185" + " / "  + location;
        binding.locationSumTextView.setText(locationSum);

        new Handler().postDelayed(() -> {
            binding.loadingIncludeLayout.getRoot().setVisibility(View.GONE);
            decorator(false);
        }, 2000);
    }

    public void navigator(int destinationId) {
        try {
            if (navController.getBackStackEntry(destinationId).getDestination() != navController.getCurrentDestination()) {
                while (Objects.requireNonNull(navController.getCurrentDestination()).getId()!=destinationId) {
                    navController.popBackStack();
                }
                if (destinationId == navController.getGraph().getStartDestination()){
                    navController.popBackStack();
                }
            }
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        navController.navigate(destinationId);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    if (controlEditText.input() != null && controlEditText.input().hasFocus()) {
                        controlEditText.clear(this, controlEditText.input());
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        switch (location) {
            case "زنجیره":
                finish();
                break;
            case "اطلاعات":
                location = "زنجیره";
                navigator(R.id.testBulkFragment);
                break;
            case "توضیحات":
                location = "اطلاعات";
                navigator(R.id.testFormFragment);
                break;
            case "یک":
                location = "توضیحات";
                navigator(R.id.testDescriptionFragment);
                break;
            case "دو":
                location = "یک";
                navigator(R.id.testOptionalFragment);
                break;
            case "پایان":
                location = "دو";
                navigator(R.id.testPictoralFragment);
                break;
        }

        for (int i=0; i<binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
            if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(location)) {
                binding.locationIncludeLayout.selectSpinner.setSelection(i);
            }
        }

        locationSum = "185" + " / "  + location;
        binding.locationSumTextView.setText(locationSum);
    }

}