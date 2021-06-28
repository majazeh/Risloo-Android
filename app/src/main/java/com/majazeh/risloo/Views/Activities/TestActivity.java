package com.majazeh.risloo.Views.Activities;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Fragments.Test.TestChainFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestDescriptionFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestEndFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestOptionalFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestPictoralFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestPrerequisiteFragmentDirections;
import com.majazeh.risloo.databinding.ActivityTestBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;

import java.util.HashMap;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    // Binding
    private ActivityTestBinding binding;

    // Singleton
    public Singleton singleton;

    // Objects
    private Bundle extras;
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;

    // Vars
    private HashMap data, header;
    private SampleModel sampleModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator(true);

        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializer();

        ExtendOnFailureException.activity = this;

        detector();

        listener();

        setExtra();

        getData();
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

        extras = getIntent().getExtras();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", singleton.getAuthorization());

        InitManager.fixedSpinner(this, binding.locationIncludeLayout.selectSpinner, R.array.TestStages, "test");

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
        ClickManager.onClickListener(() -> doWork("prev")).widget(binding.backwardImageView.getRoot());

        ClickManager.onClickListener(() -> doWork("next")).widget(binding.forwardImageView.getRoot());

        binding.locationIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = parent.getItemAtPosition(position).toString();

                doWork(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setExtra() {
        if (extras != null) {
            if (extras.getString("id") != null) {
                data.put("id", extras.getString("id"));
            }
        }
    }

    private void setData(SampleModel model) {
        if (model.getSampleScaleTitle() != null && !model.getSampleScaleTitle().equals("")) {
            binding.headerIncludeLayout.titleTextView.setText(model.getSampleScaleTitle());
        }

        if (model.getSampleEdition() != null && !model.getSampleEdition().equals("")) {
            binding.headerIncludeLayout.typeTextView.setText(model.getSampleEdition());
        }
    }

    private void getData() {
        Sample.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;

                runOnUiThread(() -> {
                    setData(sampleModel);

                    binding.loadingIncludeLayout.getRoot().setVisibility(View.GONE);
                    decorator(false);
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    binding.loadingIncludeLayout.getRoot().setVisibility(View.GONE);
                    decorator(false);
                });
            }
        });
    }

    private void doWork(String location) {
//        switch (location) {
//            case "prev": {
//                FormModel model = sampleModel.getSampleForm().prev();
//                loadFragment(model);
//            } break;
//            case "next": {
//                FormModel model = sampleModel.getSampleForm().next();
//                loadFragment(model);
//            } break;
//            case "last": {
//                FormModel model = sampleModel.getSampleForm().last();
//                loadFragment(model);
//            } break;
//            default: {
//                FormModel model = sampleModel.getSampleForm().goTo(Integer.parseInt(location));
//                loadFragment(model);
//            } break;
//        }
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
        doWork("prev");
    }
























    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void loadFragment(FormModel model) {
        switch (navController.getCurrentDestination().getId()) {
            case R.id.testChainFragment:
                switch (model.getType()) {
                    case "chain": {
                        // Exit of the Sample in backward mode
                    } break;
                    case "description": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestDescriptionFragment(model);
                        navController.navigate(action);
                    } break;
                    case "close": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestEndFragment(model);
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestPrerequisiteFragment(model);
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) model.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestOptionalFragment(model);
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image")) {
                            NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestPictoralFragment(model);
                            navController.navigate(action);
                        }
                    } break;
                }
                break;

            case R.id.testDescriptionFragment:
                switch (model.getType()) {
                    case "chain": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestChainFragment(model);
                        navController.navigate(action);
                    } break;
                    case "description": {
                        // Exit of the Sample in backward mode
                    } break;
                    case "close": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestEndFragment(model);
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestPrerequisiteFragment(model);
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) model.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestOptionalFragment(model);
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image")) {
                            NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestPictoralFragment(model);
                            navController.navigate(action);
                        }
                    } break;
                }
                break;

            case R.id.testEndFragment:
                switch (model.getType()) {
                    case "chain": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestChainFragment(model);
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestDescriptionFragment(model);
                        navController.navigate(action);
                    } break;
                    case "close": {
                        // End of the Sample in forward mode
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestPrerequisiteFragment(model);
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) model.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestOptionalFragment(model);
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image")) {
                            NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestPictoralFragment(model);
                            navController.navigate(action);
                        }
                    } break;
                }
                break;

            case R.id.testPrerequisiteFragment:
                switch (model.getType()) {
                    case "chain": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestChainFragment(model);
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestDescriptionFragment(model);
                        navController.navigate(action);
                    } break;
                    case "close": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestEndFragment(model);
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) model.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestOptionalFragment(model);
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image")) {
                            NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestPictoralFragment(model);
                            navController.navigate(action);
                        }
                    } break;
                }
                break;

            case R.id.testOptionalFragment:
                switch (model.getType()) {
                    case "chain": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestChainFragment(model);
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestDescriptionFragment(model);
                        navController.navigate(action);
                    } break;
                    case "close": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestEndFragment(model);
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestPrerequisiteFragment(model);
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) model.getObject();

                        if (itemModel.getType().equals("text")) {
                            // Referesh with new data
                        } else if (itemModel.getType().equals("image")) {
                            NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestPictoralFragment(model);
                            navController.navigate(action);
                        }
                    } break;
                }
                break;

            case R.id.testPictoralFragment:
                switch (model.getType()) {
                    case "chain": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestChainFragment(model);
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestDescriptionFragment(model);
                        navController.navigate(action);
                    } break;
                    case "close": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestEndFragment(model);
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestPrerequisiteFragment(model);
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) model.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestOptionalFragment(model);
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image")) {
                            // Referesh with new data
                        }
                    } break;
                }
                break;
        }
    }

    private void setWidgets(boolean spinner, boolean textView) {
//        if (spinner) {
//            for (int i=0; i<binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
//                if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(location)) {
//                    binding.locationIncludeLayout.selectSpinner.setSelection(i);
//                }
//            }
//        }
//
//        if (textView) {
//            locationSum = "185" + " / "  + location;
//            binding.locationSumTextView.getRoot().setText(locationSum);
//        }
//
//        binding.headerIncludeLayout.answeredProgressBar.setProgress(0);
//
//        status = getResources().getString(R.string.TestFixed);
//        binding.statusTextView.getRoot().setText(status);
//
//        location = "زنجیره";
//        for (int i=0; i<binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
//            if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(location)) {
//                binding.locationIncludeLayout.selectSpinner.setSelection(i);
//            }
//        }
//
//        locationSum = "185" + " / "  + location;
//        binding.locationSumTextView.getRoot().setText(locationSum);
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

}