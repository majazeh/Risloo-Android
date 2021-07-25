package com.majazeh.risloo.Views.Activities;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.NavigationTestDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.databinding.ActivityTestBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.Madule.SampleAnswers;
import com.mre.ligheh.Model.TypeModel.ChainModel;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    // Binding
    private ActivityTestBinding binding;

    // Entities
    public Singleton singleton;

    // Dialogs
    private LoadingDialog loadingDialog;

    // Models
    private SampleAnswers sampleAnswers;
    public SampleModel sampleModel;
    public FormModel formModel;

    // Objects
    public ControlEditText controlEditText;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private NavGraph navGraph;
    private Bundle extras;
    private Handler handler;
    private Toast toast;
    public HashMap data, header;

    // Vars
    private boolean userSelect = false, doubleBackPressed = false;

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

        sampleAnswers = new SampleAnswers();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        navGraph = navController.getNavInflater().inflate(R.navigation.navigation_test);

        extras = getIntent().getExtras();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", singleton.getAuthorization());

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
            navigate();
        }).widget(binding.backwardImageView.getRoot());

        ClickManager.onClickListener(() -> {
            formModel = sampleModel.getSampleForm().next();
            navigate();
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
                    navigate();

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

    private void setData() {
        if (sampleModel.getSampleScaleTitle() != null && !sampleModel.getSampleScaleTitle().equals("")) {
            binding.headerIncludeLayout.titleTextView.setText(sampleModel.getSampleScaleTitle());
        }

        if (sampleModel.getSampleEdition() != null && !sampleModel.getSampleEdition().equals("")) {
            String title = binding.headerIncludeLayout.titleTextView.getText().toString() + " " + StringManager.bracing(sampleModel.getSampleEdition());
            binding.headerIncludeLayout.titleTextView.setText(StringManager.foregroundSize(title, binding.headerIncludeLayout.titleTextView.getText().toString().length() + 1, title.length(), getResources().getColor(R.color.Gray400), (int) getResources().getDimension(R.dimen._9ssp)));
        }

        if (sampleModel.getSampleForm() != null && sampleModel.getSampleForm().getForms() != null && sampleModel.getSampleForm().getForms().length() != 0) {
            try {
                ArrayList<String> options = new ArrayList<>();

                for (int i = 0; i < sampleModel.getSampleForm().getForms().length(); i++) {
                    options.add(sampleModel.getSampleForm().getForms().get(i).toString());
                }

                options.add("");

                InitManager.unfixedSpinner(this, binding.locationIncludeLayout.selectSpinner, options, "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (sampleModel.getSampleForm() != null && sampleModel.getSampleForm().getCurrentForm() != null) {
            formModel = sampleModel.getSampleForm().getCurrentForm();

            switch (formModel.getType()) {
                case "psychologist_description":
                    navGraph.setStartDestination(R.id.testPsyDescFragment);
                    break;
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
                    setData();

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

    private void navigate() {
        switch (formModel.getType()) {
            case "psychologist_description": {
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.testPsyDescFragment) {
                    NavDirections action = NavigationTestDirections.actionGlobalTestPsyDescFragment();
                    navController.navigate(action);
                } else {
                    IntentManager.finish(this);
                }
            } break;
            case "chain": {
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.testChainFragment) {
                    NavDirections action = NavigationTestDirections.actionGlobalTestChainFragment();
                    navController.navigate(action);
                } else {
                    IntentManager.finish(this);
                }
            } break;
            case "prerequisites": {
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.testPrerequisiteFragment) {
                    NavDirections action = NavigationTestDirections.actionGlobalTestPrerequisiteFragment();
                    navController.navigate(action);
                } else {
                    IntentManager.finish(this);
                }
            } break;
            case "description": {
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.testDescriptionFragment) {
                    NavDirections action = NavigationTestDirections.actionGlobalTestDescriptionFragment();
                    navController.navigate(action);
                } else {
                    IntentManager.finish(this);
                }
            } break;
            case "entities": {
                NavDirections action = NavigationTestDirections.actionGlobalTestEntityFragment();
                navController.navigate(action);
            } break;
            case "item": {
                ItemModel itemModel = (ItemModel) formModel.getObject();

                if (itemModel.getType().equals("text")) {
                    NavDirections action = NavigationTestDirections.actionGlobalTestOptionalFragment();
                    navController.navigate(action);
                } else if (itemModel.getType().equals("image_url")) {
                    NavDirections action = NavigationTestDirections.actionGlobalTestPictoralFragment();
                    navController.navigate(action);
                }
            } break;
            case "close": {
                NavDirections action = NavigationTestDirections.actionGlobalTestEndFragment();
                navController.navigate(action);
            } break;
        }

        setWidgets();
    }

    private void setWidgets() {
        String indexSum = sampleModel.getSampleForm().itemSize() + " / " + sampleModel.getSampleForm().getItemPosition();
        binding.indexTextView.getRoot().setText(StringManager.foregroundSizeStyle(indexSum, String.valueOf(sampleModel.getSampleForm().itemSize()).length() + 3, indexSum.length(), getResources().getColor(R.color.Blue700), (int) getResources().getDimension(R.dimen._15ssp), Typeface.BOLD));

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

    public void sendPre(int key, String value) {
        PrerequisitesModel prere = (PrerequisitesModel) ((List) formModel.getObject()).data().get(key - 1);
        prere.setUser_answered(value);

        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestSaving));
        binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Yellow500));
        binding.statusTextView.getRoot().requestLayout();

        sampleAnswers.addToPrerequisites(key, value);
        sampleAnswers.sendPrerequisites(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
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

    public void sendItem(int key, String value) {
        ItemModel item = (ItemModel) formModel.getObject();
        item.setUser_answered(value);

        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestSaving));
        binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Yellow500));
        binding.statusTextView.getRoot().requestLayout();

        sampleAnswers.addToRemote(key, value);
        sampleAnswers.sendRequest(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
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

        handler.postDelayed(() -> {
            formModel = sampleModel.getSampleForm().next();
            navigate();
        }, 1000);
    }

    public void closeSample() {
        loadingDialog.show(this.getSupportFragmentManager(), "loadingDialog");

        Sample.close(sampleAnswers, data, header, new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
                    FormModel model = sampleModel.getSampleForm().getModel("زنجیره");

                    if (model == null) {
                        loadingDialog.dismiss();
                        IntentManager.main(TestActivity.this);
                    } else {
                        List chains = (List) model.getObject();

                        for (int i = 0; i < chains.data().size(); i++) {
                            ChainModel chainModel = (ChainModel) chains.data().get(i);

                            if ((chainModel.getStatus().equals("seald") || chainModel.getStatus().equals("open")) && i != chains.data().size() && !chainModel.getId().equals(data.get("id"))) {
                                loadingDialog.dismiss();

                                data.put("id", chainModel.getId());
                                sampleAnswers.id = chainModel.getId();

                                binding.loadingIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                                decorator(true);

                                getData();
                                break;
                            } else {
                                loadingDialog.dismiss();
                                IntentManager.main(TestActivity.this);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    loadingDialog.dismiss();
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            IntentManager.finish(this);
        } else {
            doubleBackPressed = true;
            handler.postDelayed(() -> doubleBackPressed = false, 2000);

            try {
                toast.getView().isShown();
                toast.setText(getResources().getString(R.string.AppDoubleBackPressed));
            } catch (Exception e) {
                toast = Toast.makeText(this, getResources().getString(R.string.AppDoubleBackPressed), Toast.LENGTH_SHORT);
            }

            toast.show();
        }
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
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}