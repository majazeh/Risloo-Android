package com.majazeh.risloo.views.activities;

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
import com.majazeh.risloo.utils.configs.ExtendException;
import com.majazeh.risloo.utils.entities.Decoraton;
import com.majazeh.risloo.utils.entities.Inputon;
import com.majazeh.risloo.utils.entities.Navigatoon;
import com.majazeh.risloo.utils.entities.Singleton;
import com.majazeh.risloo.utils.entities.Validatoon;
import com.majazeh.risloo.utils.managers.AnimateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
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
            decoraton.setSystemUIColor(getResources().getColor(R.color.risloo500), getResources().getColor(R.color.coolGray50));
        } else {
            decoraton.showSystemUI(true, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.coolGray50), getResources().getColor(R.color.coolGray50));
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
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.risloo(this)).widget(binding.debugTextView.getRoot());

        CustomClickView.onClickListener(() -> setFormModel("prev")).widget(binding.prevImageView);

        CustomClickView.onClickListener(() -> setFormModel("next")).widget(binding.nextImageView);

        binding.indexSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.indexSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.indexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    setFormModel(pos);

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
            if (!extras.getString("id").equals("")) {
                data.put("id", extras.getString("id"));
                sampleAnswers.id = extras.getString("id");
            }
        }
    }

    private void getData() {
        Sample.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;
                sampleForm = sampleModel.getSampleForm();

                runOnUiThread(() -> {
                    if (sampleForm != null) {
                        binding.getRoot().transitionToState(R.id.end);
                        decorator(false);

                        setData();

                        return;
                    }

                    SnackManager.showDefaultSnack(TestActivity.this, getResources().getString(R.string.SnackSampleNull));
                    IntentManager.finish(TestActivity.this);
                });
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> IntentManager.finish(TestActivity.this));
            }
        });
    }

    private void setData() {
        if (!sampleModel.getScaleTitle().equals("")) {
            binding.titleTextView.setText(sampleModel.getScaleTitle());
        }

        if (!sampleModel.getEdition().equals("")) {
            String title = binding.titleTextView.getText().toString() + " " + StringManager.bracing(sampleModel.getEdition());
            binding.titleTextView.setText(StringManager.foregroundSize(title, binding.titleTextView.getText().toString().length() + 1, title.length(), getResources().getColor(R.color.coolGray400), (int) getResources().getDimension(R.dimen._9ssp)));
        }

        if (sampleForm.getForms().length() != 0) {
            setSpinner();
        }

        if (sampleForm.getCurrentForm() != null) {
            setStartDest();
        }

        setStatus("fixed");

        setButtons();

        setIndex();
    }

    private void setSpinner() {
        try {
            ArrayList<String> options = new ArrayList<>();
            answers = new ArrayList<>();

            for (int i = 0; i < sampleForm.getForms().length(); i++) {
                options.add(sampleForm.getForms().getJSONObject(i).getString("title"));
                answers.add(sampleForm.getForms().getJSONObject(i).getBoolean("answer"));
            }

            options.add("");
            answers.add(false);

            InitManager.inputCustomTestSpinner(this, binding.indexSpinner, options, answers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setStartDest() {
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
            case "entities":
                navigatoon.setStartDestinationId(R.id.testEntityFragment);
                break;
            case "item":
                ItemModel itemModel = (ItemModel) formModel.getObject();

                switch (itemModel.getType()) {
                    case "text":
                        if (itemModel.getAnswer().getType().equals("descriptive"))
                            navigatoon.setStartDestinationId(R.id.testDescriptiveFragment);
                        else
                            navigatoon.setStartDestinationId(R.id.testOptionalFragment);

                        break;
                    case "image_url":
                        navigatoon.setStartDestinationId(R.id.testPictoralFragment);
                        break;
                }

                break;
        }
    }

    private void setStatus(String type) {
        if (type.equals("saving")) {
            binding.statusTextView.setText(getResources().getString(R.string.TestSaving));
            binding.statusTextView.setTextColor(getResources().getColor(R.color.amber500));
        } else {
            binding.statusTextView.setText(getResources().getString(R.string.TestFixed));
            binding.statusTextView.setTextColor(getResources().getColor(R.color.coolGray600));
        }

        binding.statusTextView.requestLayout();
    }

    private void setFormModel(String type) {
        if (type.equals("prev"))
            formModel = sampleForm.prev();
        else if (type.equals("next"))
            formModel = sampleForm.next();
        else
            formModel = sampleForm.goTo(type);

        setFramgent();

        setButtons();

        setIndex();
    }

    private void setFramgent() {
        if (navigatoon.getCurrentDestinationId() == R.id.testPrerequisiteFragment) {
            sendPre();
        }

        switch (formModel.getType()) {
            case "psychologist_description":
                navigatoon.navigateToTestPsyDescFragment();
                break;
            case "chain":
                navigatoon.navigateToTestChainFragment();
                break;
            case "prerequisites":
                navigatoon.navigateToTestPrerequisiteFragment();
                break;
            case "description":
                navigatoon.navigateToTestDescriptionFragment();
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
    }

    private void setButtons() {
        if (navigatoon.getCurrentDestinationId() == navigatoon.getStartDestinationId()) {
            binding.prevImageView.setClickable(false);
            binding.prevImageView.setAlpha(0.5F);

            binding.nextImageView.setClickable(true);
            binding.nextImageView.setAlpha(1F);
        } else if (navigatoon.getCurrentDestinationId() == R.id.testEndFragment) {
            binding.prevImageView.setClickable(true);
            binding.prevImageView.setAlpha(1F);

            binding.nextImageView.setClickable(false);
            binding.nextImageView.setAlpha(0.5F);
        } else {
            binding.prevImageView.setClickable(true);
            binding.prevImageView.setAlpha(1F);

            binding.nextImageView.setClickable(true);
            binding.nextImageView.setAlpha(1F);
        }
    }

    private void setIndex() {
        AnimateManager.animateProgressValue(binding.indexProgressBar, 500, sampleForm.itemSize(), sampleForm.getItemPosition());

        String index = sampleForm.itemSize() + " / " + sampleForm.getItemPosition();
        binding.indexTextView.setText(StringManager.foregroundSizeStyle(index, String.valueOf(sampleForm.itemSize()).length() + 3, index.length(), getResources().getColor(R.color.risloo500), (int) getResources().getDimension(R.dimen._15ssp), Typeface.BOLD));

        userSelect = false;

        for (int i = 0; i < binding.indexSpinner.getCount(); i++) {
            if (binding.indexSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(formModel.getTitle())) {
                binding.indexSpinner.setSelection(i);
                break;
            } else {
                binding.indexSpinner.setSelection(0);
            }
        }
    }

    private void sendPre() {
        setStatus("saving");

        sampleAnswers.sendPrerequisites(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> setStatus("fixed"));
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> setStatus("fixed"));
            }
        });
    }

    public void sendPre(int key, String value) {
        PrerequisitesModel model = (PrerequisitesModel) ((List) formModel.getObject()).data().get(key - 1);
        model.setUserAnswered(value);

        setStatus("saving");

        sampleAnswers.addToPrerequisites(key, value);
        sampleAnswers.sendPrerequisites(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> setStatus("fixed"));
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> setStatus("fixed"));
            }
        });
    }

    public void sendItem(int key, String value) {
        ItemModel model = (ItemModel) formModel.getObject();
        model.setUserAnswered(value);

        setStatus("saving");

        sampleAnswers.add(key, value);
        sampleAnswers.sendRequest(singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                runOnUiThread(() -> setStatus("fixed"));
            }

            @Override
            public void onFailure(String response) {
                runOnUiThread(() -> setStatus("fixed"));
            }
        });

        answers.set(sampleForm.getPosition(), true);

        handler.postDelayed(() -> setFormModel("next"), 500);
    }

    public void closeSample() {
        DialogManager.showLoadingDialog(this, "");

        Sample.close(sampleAnswers, data, header, new Response() {
            @Override
            public void onOK(Object object) {
                formModel = sampleForm.getModel("زنجیره");

                runOnUiThread(() -> {
                    if (formModel != null) {
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

                                return;
                            }
                        }
                    }

                    DialogManager.dismissLoadingDialog();

                    SnackManager.showSuccesSnack(TestActivity.this, getResources().getString(R.string.SnackSampleClosed));
                    IntentManager.finish(TestActivity.this);
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
                            StringBuilder allErrors = new StringBuilder();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                    String validation = errorsObject.getJSONArray(key).get(i).toString();

                                    allErrors.append(validation);
                                    allErrors.append("\n");
                                }
                            }

                            SnackManager.showErrorSnack(TestActivity.this, allErrors.substring(0, allErrors.length() - 1));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void doublePressExit() {
        doubleBackPressed = true;
        handler.postDelayed(() -> doubleBackPressed = false, 2000);

        ToastManager.showDefaultToast(this, getResources().getString(R.string.ToastSampleDoublePressExit));
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
            doublePressExit();
        else
            IntentManager.finish(this);
    }

    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacksAndMessages(null);
    }

}