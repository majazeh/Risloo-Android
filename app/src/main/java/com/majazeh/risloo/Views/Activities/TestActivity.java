package com.majazeh.risloo.Views.Activities;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.Decoraton;
import com.majazeh.risloo.Utils.Entities.Inputon;
import com.majazeh.risloo.Utils.Entities.Navigatoon;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Entities.Validatoon;
import com.majazeh.risloo.Utils.Managers.AnimateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.databinding.ActivityTestBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.Madule.SampleAnswers;
import com.mre.ligheh.Model.Madule.SampleForm;
import com.mre.ligheh.Model.TypeModel.ChainModel;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    // Binding
    private ActivityTestBinding binding;

    // Entities
    public Inputon inputon;
    public Navigatoon navigatoon;
    public Singleton singleton;
    public Validatoon validatoon;

    // Models
    public SampleAnswers sampleAnswers;
    public SampleModel sampleModel;
    public SampleForm sampleForm;
    public FormModel formModel;

    // Objects
    private Bundle extras;
    private Handler handler;
    private HashMap data, header;

    // Vars
    private ArrayList<Boolean> answers;
    private boolean userSelect = false, doubleBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator(true);

        varianter();

        initializer();

        ExtendException.activity = this;

        listener();

        setExtra();

        getData();
    }

    private void decorator(boolean dark) {
        Decoraton decoraton = new Decoraton(this);

        if (dark) {
            decoraton.showSystemUI(false, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.Risloo500), getResources().getColor(R.color.CoolGray50));
        } else {
            decoraton.showSystemUI(true, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.CoolGray50));
        }
    }

    private void varianter() {
        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.debugTextView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.debugTextView.getRoot().setVisibility(View.GONE);
    }

    private void initializer() {
        inputon = new Inputon(this);
        singleton = new Singleton(this);
        validatoon = new Validatoon(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentNavHostFragment.getId());
        navigatoon = new Navigatoon(this, Objects.requireNonNull(navHostFragment));

        sampleAnswers = new SampleAnswers();

        extras = getIntent().getExtras();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", singleton.getAuthorization());

        InitManager.imgResTint(this, binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.CoolGray500);
        InitManager.imgResTintRotate(this, binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.CoolGray500, 180);
    }

    private void setExtra() {
        if (extras != null) {
            if (!extras.getString("id").equals("")) {
                data.put("id", extras.getString("id"));
                sampleAnswers.id = extras.getString("id");
            }
        }
    }

    private void postHandler() {
        doubleBackPressed = true;
        handler.postDelayed(() -> doubleBackPressed = false, 2000);

        ToastManager.showDefaultToast(this, getResources().getString(R.string.ToastSampleDoublePressExit));
    }






















    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.risloo(this)).widget(binding.debugTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            formModel = sampleForm.prev();
            navigate();
        }).widget(binding.backwardImageView.getRoot());

        CustomClickView.onClickListener(() -> {
            formModel = sampleForm.next();
            navigate();
        }).widget(binding.forwardImageView.getRoot());

        binding.locationIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.locationIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.locationIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    formModel = sampleForm.goTo(pos);
                    navigate();

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData() {
        if (!sampleModel.getScaleTitle().equals("")) {
            binding.headerIncludeLayout.titleTextView.setText(sampleModel.getScaleTitle());
        }

        if (!sampleModel.getEdition().equals("")) {
            String title = binding.headerIncludeLayout.titleTextView.getText().toString() + " " + StringManager.bracing(sampleModel.getEdition());
            binding.headerIncludeLayout.titleTextView.setText(StringManager.foregroundSize(title, binding.headerIncludeLayout.titleTextView.getText().toString().length() + 1, title.length(), getResources().getColor(R.color.CoolGray400), (int) getResources().getDimension(R.dimen._9ssp)));
        }

        if (sampleForm.getForms().length() != 0) {
            try {
                ArrayList<String> options = new ArrayList<>();
                answers = new ArrayList<>();

                for (int i = 0; i < sampleForm.getForms().length(); i++) {
                    options.add(sampleForm.getForms().getJSONObject(i).getString("title"));
                    answers.add(sampleForm.getForms().getJSONObject(i).getBoolean("answer"));
                }

                options.add("");
                answers.add(false);

                InitManager.inputCustomTestSpinner(this, binding.locationIncludeLayout.selectSpinner, options, answers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (sampleForm.getCurrentForm() != null) {
            formModel = sampleForm.getCurrentForm();

            switch (formModel.getType()) {
                case "psychologist_description":
                    navigatoon.setStartDestinationId(R.id.testPsyDescFragment);
                    break;
                case "chain":
                    navigatoon.setStartDestinationId(R.id.testChainFragment);
                    break;
                case "prerequisites":
                    navigatoon.setStartDestinationId(R.id.testPrerequisiteFragment);
                    break;
                case "description":
                    navigatoon.setStartDestinationId(R.id.testDescriptionFragment);
                    break;
            }
        }

        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));

        setWidgets();
    }

    private void setWidgets() {
        String indexSum = sampleForm.itemSize() + " / " + sampleForm.getItemPosition();
        binding.indexTextView.getRoot().setText(StringManager.foregroundSizeStyle(indexSum, String.valueOf(sampleForm.itemSize()).length() + 3, indexSum.length(), getResources().getColor(R.color.Risloo500), (int) getResources().getDimension(R.dimen._15ssp), Typeface.BOLD));

        AnimateManager.animateProgressValue(binding.headerIncludeLayout.answeredProgressBar, 500, sampleForm.itemSize(), sampleForm.getItemPosition());

        userSelect = false;

        for (int i = 0; i < binding.locationIncludeLayout.selectSpinner.getCount(); i++) {
            if (binding.locationIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(formModel.getTitle())) {
                binding.locationIncludeLayout.selectSpinner.setSelection(i);
                break;
            } else {
                binding.locationIncludeLayout.selectSpinner.setSelection(0);
            }
        }
    }

    private void getData() {
        Sample.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;

                runOnUiThread(() -> {
                    sampleForm = sampleModel.getSampleForm();

                    if (sampleForm == null) {
                        ToastManager.showErrorToast(TestActivity.this, getResources().getString(R.string.ToastSampleFormIsNull));
                        IntentManager.finish(TestActivity.this);
                    } else {
                        setData();

                        binding.getRoot().transitionToState(R.id.end);
                        decorator(false);
                    }
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> IntentManager.finish(TestActivity.this));
            }
        });
    }

    private void navigate() {
        if (navigatoon.getCurrentDestinationId() == R.id.testPrerequisiteFragment) {
            int key = -1;
            sendPre(key, "");
        }

        switch (formModel.getType()) {
            case "psychologist_description":
                if (navigatoon.getCurrentDestinationId() != R.id.testPsyDescFragment)
                    navigatoon.navigateToTestPsyDescFragment();
                else
                    IntentManager.finish(this);

                break;
            case "chain":
                if (navigatoon.getCurrentDestinationId() != R.id.testChainFragment)
                    navigatoon.navigateToTestChainFragment();
                else
                    IntentManager.finish(this);

                break;
            case "prerequisites":
                if (navigatoon.getCurrentDestinationId() != R.id.testPrerequisiteFragment)
                    navigatoon.navigateToTestPrerequisiteFragment();
                else
                    IntentManager.finish(this);

                break;
            case "description":
                if (navigatoon.getCurrentDestinationId() != R.id.testDescriptionFragment)
                    navigatoon.navigateToTestDescriptionFragment();
                else
                    IntentManager.finish(this);

                break;
            case "entities":
                navigatoon.navigateToTestEntityFragment();

                break;
            case "item":
                ItemModel itemModel = (ItemModel) formModel.getObject();

                switch (itemModel.getType()) {
                    case "text":
                        if (itemModel.getAnswer().getType().equals("descriptive"))
                            navigatoon.navigateToTestDescriptiveFragment();
                        else
                            navigatoon.navigateToTestOptionalFragment();

                        break;
                    case "image_url":
                        navigatoon.navigateToTestPictoralFragment();
                        break;
                }

                break;
            case "close":
                navigatoon.navigateToTestEndFragment();

                break;
        }

        setWidgets();
    }

    public void sendPre(int key, String value) {
        if (key != -1) {
            PrerequisitesModel model = (PrerequisitesModel) ((List) formModel.getObject()).data().get(key - 1);
            model.setUserAnswered(value);
        }

        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestSaving));
        binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Amber500));
        binding.statusTextView.getRoot().requestLayout();

        if (key != -1) {
            sampleAnswers.addToPrerequisites(key, value);
        }

        sampleAnswers.sendPrerequisites(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
                    binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));
                    binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.CoolGray600));
                    binding.statusTextView.getRoot().requestLayout();
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));
                    binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.CoolGray600));
                    binding.statusTextView.getRoot().requestLayout();
                });
            }
        });
    }

    public void sendItem(int key, String value) {
        ItemModel model = (ItemModel) formModel.getObject();
        model.setUserAnswered(value);

        answers.set(sampleForm.getPosition(), true);

        binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestSaving));
        binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Amber500));
        binding.statusTextView.getRoot().requestLayout();

        sampleAnswers.add(key, value);
        sampleAnswers.sendRequest(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
                    binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));
                    binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.CoolGray600));
                    binding.statusTextView.getRoot().requestLayout();
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    binding.statusTextView.getRoot().setText(getResources().getString(R.string.TestFixed));
                    binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.CoolGray600));
                    binding.statusTextView.getRoot().requestLayout();
                });
            }
        });

        handler.postDelayed(() -> {
            formModel = sampleForm.next();
            navigate();
        }, 500);
    }

    public void closeSample() {
        DialogManager.showLoadingDialog(this, "");

        Sample.close(sampleAnswers, data, header, new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> {
                    FormModel formModel = sampleForm.getModel("زنجیره");

                    if (formModel == null) {
                        DialogManager.dismissLoadingDialog();
                        IntentManager.main(TestActivity.this);
                    } else {
                        List items = (List) formModel.getObject();

                        for (int i = 0; i < items.data().size(); i++) {
                            ChainModel chainModel = (ChainModel) items.data().get(i);

                            if ((chainModel.getStatus().equals("seald") || chainModel.getStatus().equals("open")) && i != items.data().size() && !chainModel.getId().equals(sampleAnswers.id)) {
                                DialogManager.dismissLoadingDialog();

                                data.put("id", chainModel.getId());
                                sampleAnswers.id = chainModel.getId();

                                binding.getRoot().transitionToState(R.id.start);
                                decorator(true);

                                getData();
                                break;
                            } else {
                                DialogManager.dismissLoadingDialog();
                                IntentManager.main(TestActivity.this);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> {
                    try {
                        JSONObject responseObject = new JSONObject(response);
                        if (!responseObject.isNull("errors")) {
                            JSONObject errorsObject = responseObject.getJSONObject("errors");

                            Iterator<String> keys = (errorsObject.keys());
                            StringBuilder errors = new StringBuilder();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                    String validation = errorsObject.getJSONArray(key).get(i).toString();

                                    errors.append(validation);
                                    errors.append("\n");
                                }
                            }

                            SnackManager.showErrorSnack(TestActivity.this, errors.substring(0, errors.length() - 1));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }




























    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    if (inputon.editText != null && inputon.editText.hasFocus()) {
                        inputon.clear(inputon.editText);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (!doubleBackPressed)
            postHandler();
        else
            IntentManager.finish(this);
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}