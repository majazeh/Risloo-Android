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
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.Views.Fragments.Test.TestChainFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestDescriptionFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestEndFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestEntityFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestOptionalFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestPictoralFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Test.TestPrerequisiteFragmentDirections;
import com.majazeh.risloo.databinding.ActivityTestBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.Madule.SampleAnswers;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    // Binding
    private ActivityTestBinding binding;

    // Singleton
    public Singleton singleton;

    // Dialogs
    private LoadingDialog loadingDialog;

    // Objects
    private Bundle extras;
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;
    public NavGraph navGraph;

    // Vars
    private HashMap data, header;
    private SampleAnswers sampleAnswers;
    public SampleModel sampleModel;
    public FormModel formModel;
    private boolean userSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator(true);

        initializer();

        ExtendOnFailureException.activity = this;

        detector();

        listener();

        setExtra();

        getData();
    }

    private void decorator(boolean dark) {
        WindowDecorator windowDecorator = new WindowDecorator(this);

        if (dark) {
            windowDecorator.showSystemUI(false, true);
            windowDecorator.setSystemUIColor(getResources().getColor(R.color.Blue600), getResources().getColor(R.color.Gray50));
        } else {
            if (BuildConfig.BUILD_TYPE.equals("debug")) {
                windowDecorator.showSystemUI(false, true);
                windowDecorator.setSystemUIColor(getResources().getColor(R.color.Red500), getResources().getColor(R.color.Gray50));

                binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
            } else {
                windowDecorator.showSystemUI(true, true);
                windowDecorator.setSystemUIColor(getResources().getColor(R.color.White), getResources().getColor(R.color.Gray50));

                binding.debugTextView.getRoot().setVisibility(View.GONE);
            }
        }
    }

    private void initializer() {
        singleton = new Singleton(this);

        loadingDialog = new LoadingDialog();

        extras = getIntent().getExtras();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        navGraph = navController.getNavInflater().inflate(R.navigation.navigation_test);

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", singleton.getAuthorization());

        sampleAnswers = new SampleAnswers();

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
            formModel = sampleModel.getSampleForm().prev();
            navigateFragment();
        }).widget(binding.backwardImageView.getRoot());

        ClickManager.onClickListener(() -> {
            formModel = sampleModel.getSampleForm().next();
            navigateFragment();
        }).widget(binding.forwardImageView.getRoot());

        binding.locationIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.locationIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    formModel = sampleModel.getSampleForm().goTo(pos);
                    navigateFragment();

                    userSelect = false;
                }
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
                sampleAnswers.id = extras.getString("id");
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

        if (model.getSampleForm() != null && model.getSampleForm().getForms() != null && model.getSampleForm().getForms().length() != 0) {
            try {
                ArrayList<String> titles = new ArrayList<>();

                for (int i = 0; i < model.getSampleForm().getForms().length(); i++) {
                    titles.add(model.getSampleForm().getForms().get(i).toString());
                }

                titles.add("");

                InitManager.unfixedSpinner(this, binding.locationIncludeLayout.selectSpinner, titles, "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (model.getSampleForm() != null && model.getSampleForm().getCurrentForm() != null) {
            formModel = model.getSampleForm().getCurrentForm();

            switch (formModel.getType()) {
                case "chain":
                    navGraph.setStartDestination(R.id.testChainFragment);
                    break;
                case "prerequisites":
                    navGraph.setStartDestination(R.id.testPrerequisiteFragment);
                    break;
                case "description":
                    navGraph.setStartDestination(R.id.testDescriptionFragment);
                    break;
            }

            navController.setGraph(navGraph);
        }

        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));

        setWidgets();
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
                runOnUiThread(() -> IntentManager.finish(TestActivity.this));
            }
        });
    }

    private void navigateFragment() {
        switch (navController.getCurrentDestination().getId()) {
            case R.id.testChainFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        IntentManager.finish(this);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestPrerequisiteFragment();
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestDescriptionFragment();
                        navController.navigate(action);
                    } break;
                    case "entities": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        NavDirections action = TestChainFragmentDirections.actionTestChainFragmentToTestEndFragment();
                        navController.navigate(action);
                    } break;
                }
                break;

            case R.id.testPrerequisiteFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestChainFragment();
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        IntentManager.finish(this);
                    } break;
                    case "description": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestDescriptionFragment();
                        navController.navigate(action);
                    } break;
                    case "entities": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        NavDirections action = TestPrerequisiteFragmentDirections.actionTestPrerequisiteFragmentToTestEndFragment();
                        navController.navigate(action);
                    } break;
                }
                break;

            case R.id.testDescriptionFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestChainFragment();
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestPrerequisiteFragment();
                        navController.navigate(action);
                    } break;
                    case "description": {
                        IntentManager.finish(this);
                    } break;
                    case "entities": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        NavDirections action = TestDescriptionFragmentDirections.actionTestDescriptionFragmentToTestEndFragment();
                        navController.navigate(action);
                    } break;
                }
                break;

            case R.id.testEntityFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestChainFragment();
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestPrerequisiteFragment();
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestDescriptionFragment();
                        navController.navigate(action);
                    } break;
                    case "entities": {
                        NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        NavDirections action = TestEntityFragmentDirections.actionTestEntityFragmentToTestEndFragment();
                        navController.navigate(action);
                    } break;
                }
                break;

            case R.id.testOptionalFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestChainFragment();
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestPrerequisiteFragment();
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestDescriptionFragment();
                        navController.navigate(action);
                    } break;
                    case "entities": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        NavDirections action = TestOptionalFragmentDirections.actionTestOptionalFragmentToTestEndFragment();
                        navController.navigate(action);
                    } break;
                }
                break;

            case R.id.testPictoralFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestChainFragment();
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestPrerequisiteFragment();
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestDescriptionFragment();
                        navController.navigate(action);
                    } break;
                    case "entities": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        NavDirections action = TestPictoralFragmentDirections.actionTestPictoralFragmentToTestEndFragment();
                        navController.navigate(action);
                    } break;
                }
                break;

            case R.id.testEndFragment:
                switch (formModel.getType()) {
                    case "chain": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestChainFragment();
                        navController.navigate(action);
                    } break;
                    case "prerequisites": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestPrerequisiteFragment();
                        navController.navigate(action);
                    } break;
                    case "description": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestDescriptionFragment();
                        navController.navigate(action);
                    } break;
                    case "entities": {
                        NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestEntityFragment();
                        navController.navigate(action);
                    } break;
                    case "item": {
                        ItemModel itemModel = (ItemModel) formModel.getObject();

                        if (itemModel.getType().equals("text")) {
                            NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestOptionalFragment();
                            navController.navigate(action);
                        } else if (itemModel.getType().equals("image_url")) {
                            NavDirections action = TestEndFragmentDirections.actionTestEndFragmentToTestPictoralFragment();
                            navController.navigate(action);
                        }
                    } break;
                    case "close": {
                        closeSample();
                    } break;
                }
                break;
        }

        setWidgets();
    }

    private void setWidgets() {
        String locationSum = sampleModel.getSampleForm().itemSize() + " / " + sampleModel.getSampleForm().getItemPosition();
        binding.locationSumTextView.getRoot().setText(locationSum);

        binding.headerIncludeLayout.answeredProgressBar.setMax(sampleModel.getSampleForm().itemSize());
        binding.headerIncludeLayout.answeredProgressBar.setProgress(sampleModel.getSampleForm().getItemPosition());

        for (int i = 0; i < binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
            if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(formModel.getTitle())) {
                binding.locationIncludeLayout.selectSpinner.setSelection(i);
                break;
            } else {
                binding.locationIncludeLayout.selectSpinner.setSelection(0);
            }
        }
    }

    public void sendItem(String key, String value) {
        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestSaving));
        binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Yellow500));
        binding.statusTextView.getRoot().requestLayout();

        sampleAnswers.addToRemote(Integer.parseInt(key), value);
        sampleAnswers.sendRequest(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
                    formModel = sampleModel.getSampleForm().next();
                    navigateFragment();

                    binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));
                    binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray600));
                    binding.statusTextView.getRoot().requestLayout();
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));
                    binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray600));
                    binding.statusTextView.getRoot().requestLayout();
                });
            }
        });
    }

    public void closeSample() {
        loadingDialog.show(this.getSupportFragmentManager(), "loadingDialog");

        Sample.close(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
                    loadingDialog.dismiss();
                    IntentManager.main(TestActivity.this);
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    // Place Code if Needed
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        formModel = sampleModel.getSampleForm().prev();
        navigateFragment();
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

}