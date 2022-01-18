package com.majazeh.risloo.Views.Activities;

import static android.content.RestrictionsManager.RESULT_ERROR;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Config.ExtendEvent;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.BreadCrumb;
import com.majazeh.risloo.Utils.Entities.Decoraton;
import com.majazeh.risloo.Utils.Entities.Fragmont;
import com.majazeh.risloo.Utils.Entities.Inputon;
import com.majazeh.risloo.Utils.Entities.Navigatoon;
import com.majazeh.risloo.Utils.Entities.Permissoon;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Entities.Validatoon;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.ListManager;
import com.majazeh.risloo.Utils.Managers.PaymentManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Utils.Managers.TreasuryManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.MainNavAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.databinding.ActivityMainBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;

    // Entities
    private BreadCrumb breadCrumb;
    public Fragmont fragmont;
    public Inputon inputon;
    public Navigatoon navigatoon;
    public Permissoon permissoon;
    public Singleton singleton;
    public Validatoon validatoon;

    // Adapters
    private MainNavAdapter mainNavAdapter;

    // Objects
    private HashMap data, header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        varianter();

        initializer();

        ExtendException.activity = this;

        listener();

        setData();

        PaymentManager.callback(this);
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(true, true);
        decoraton.setSystemUIColor(getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));
    }

    private void varianter() {
        if (BuildConfig.BUILD_TYPE.equals("debug"))
            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.GONE);
    }

    private void initializer() {
        breadCrumb = new BreadCrumb(this);
        inputon = new Inputon(this);
        permissoon = new Permissoon(this);
        singleton = new Singleton(this);
        validatoon = new Validatoon(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentIncludeLayout.fragmentNavHostFragment.getId());
        navigatoon = new Navigatoon(this, Objects.requireNonNull(navHostFragment));

        fragmont = new Fragmont(navHostFragment);

        mainNavAdapter = new MainNavAdapter(this);

        data = new HashMap<>();
        header = new HashMap<>();

        InitManager.selectToolbarSpinner(this, binding.contentIncludeLayout.toolbarIncludeLayout.selectSpinner, ListManager.getToolbar(this));

        InitManager.fixedVerticalRecyclerView2(this, binding.navIncludeLayout.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.risloo(this)).widget(binding.contentIncludeLayout.debugTextView.getRoot());

        CustomClickView.onDelayedListener(() -> setDrawer("openDrawer")).widget(binding.contentIncludeLayout.menuImageView);

        CustomClickView.onDelayedListener(() -> setUser("logoutFormOtherUser", "")).widget(binding.contentIncludeLayout.logoutImageView);

        binding.contentIncludeLayout.toolbarIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = parent.getItemAtPosition(position).toString();

                setFragment(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.contentIncludeLayout.getRoot().addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if (currentId == motionLayout.getStartState())
                    MainActivity.this.getWindow().setStatusBarColor(getResources().getColor(R.color.White));
                else if (currentId == motionLayout.getEndState())
                    MainActivity.this.getWindow().setStatusBarColor(getResources().getColor(R.color.CoolGray50));
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });

        navigatoon.getNavController().addOnDestinationChangedListener((controller, destination, arguments) -> {
            SpannableStringBuilder faBreadCump = breadCrumb.getFa(destination, arguments);

            binding.contentIncludeLayout.breadcumpTextView.setText(faBreadCump);
            binding.contentIncludeLayout.breadcumpTextView.setMovementMethod(LinkMovementMethod.getInstance());

            mainNavAdapter.setFocused(faBreadCump.toString());

            ExtendEvent.cancelRequest();
        });
    }

    public void setData() {
        if (!singleton.getToken().equals("")) {
            UserModel model = singleton.getUserModel();

            if (!model.getName().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(model.getName());
            } else if (!model.getId().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(model.getId());
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
            }

            if (model.getAvatar() != null && model.getAvatar().getMedium() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.contentIncludeLayout.toolbarIncludeLayout.avatarImageView);
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.getText().toString()));
            }

            if (!TreasuryManager.getWalletAndGift(model.getTreasuries()).equals("0")) {
                String value = StringManager.separate(TreasuryManager.getWalletAndGift(model.getTreasuries())) + " " + getResources().getString(R.string.MainToman);

                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText(value);
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setVisibility(View.VISIBLE);

                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setMaxLines(1);
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText("");
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setVisibility(View.GONE);

                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setMaxLines(2);
            }

            if (singleton.getOtherUser()) {
                binding.contentIncludeLayout.logoutImageView.setVisibility(View.VISIBLE);
            } else {
                binding.contentIncludeLayout.logoutImageView.setVisibility(View.GONE);
            }

            mainNavAdapter.setItems(ListManager.getDrawer(this, permissoon, singleton));
            binding.navIncludeLayout.listRecyclerView.setAdapter(mainNavAdapter);
        } else {
            IntentManager.auth(this, "login");
        }
    }

    private void setHashmap(String userId) {
        if (!userId.equals(""))
            data.put("id", userId);
        else
            data.remove("id");

        header.put("Authorization", singleton.getAuthorization());
    }

    private void setSingleton(String method, AuthModel model) {
        singleton.login(model);
        singleton.otherUser(method.equals("loginOtherUser"));
    }

    public void setFragment(String destination) {
        switch (destination) {

            // Toolbar
            case "مشاهده پروفایل":
                navigatoon.navigateToMeFragment(singleton.getUserModel());
                break;
            case "حسابداری":
                navigatoon.navigateToAccountingFragment();
                break;
            case "شارژ حساب":
                navigatoon.navigateToPaymentsFragment(null);
                break;
            case "خروج":
                SheetManager.showLogoutBottomSheet(MainActivity.this, singleton.getUserModel());
                break;

            // Drawer
            case "داشبورد":
                navigatoon.navigateToDashboardFragment();
                break;
            case "مراکز درمانی":
                navigatoon.navigateToCentersFragment();
                break;
            case "پرونده\u200Cها":
                navigatoon.navigateToCasesFragment();
                break;
            case "جلسات":
                navigatoon.navigateToSessionsFragment();
                break;
            case "اعضاء":
                navigatoon.navigateToUsersFragment();
                break;
            case "نمونه\u200Cها":
                navigatoon.navigateToSamplesFragment(null, null);
                break;
            case "نمونه\u200Cهای گروهی":
                navigatoon.navigateToBulkSamplesFragment();
                break;
            case "ارزیابی\u200Cها":
                navigatoon.navigateToScalesFragment();
                break;
            case "مدارک":
                navigatoon.navigateToDocumentsFragment();
                break;
            case "دانلودها":
                navigatoon.navigateToDownloadsFragment();
                break;
        }
    }

    public void setDrawer(String type) {
        if (type.equals("openDrawer"))
            binding.getRoot().openDrawer(GravityCompat.START);
        else
            binding.getRoot().closeDrawer(GravityCompat.START);
    }

    public void setUser(String method, String userId) {
        DialogManager.showLoadingDialog(this, "");

        setHashmap(userId);

        if (method.equals("loginOtherUser")) {
            Auth.loginOtherUser(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;

                    runOnUiThread(() -> {
                        setSingleton(method, authModel);

                        setData();

                        DialogManager.dismissLoadingDialog();

                        SnackManager.showSuccesSnack(MainActivity.this, getResources().getString(R.string.SnackLoginOtherUser));
                        navigatoon.navigateToDashboardFragment();
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

                                SnackManager.showErrorSnack(MainActivity.this, allErrors.substring(0, allErrors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        } else {
            Auth.logoutFromOtherUser(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;

                    runOnUiThread(() -> {
                        setSingleton(method, authModel);

                        setData();

                        DialogManager.dismissLoadingDialog();

                        SnackManager.showSuccesSnack(MainActivity.this, getResources().getString(R.string.SnackLogoutFormOtherUser));
                        navigatoon.navigateToDashboardFragment();
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

                                SnackManager.showErrorSnack(MainActivity.this, allErrors.substring(0, allErrors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    switch (requestCode) {
                        case 100:
                            ToastManager.showErrorToast(this, getResources().getString(R.string.ToastPermissionFileException));
                            break;
                        case 200:
                            ToastManager.showErrorToast(this, getResources().getString(R.string.ToastPermissionStorageException));
                            break;
                        case 300:
                            ToastManager.showErrorToast(this, getResources().getString(R.string.ToastPermissionGalleryException));
                            break;
                        case 400:
                            ToastManager.showErrorToast(this, getResources().getString(R.string.ToastPermissionCameraException));
                            break;
                    }

                    return;
                }
            }

            switch (requestCode) {
                case 100:
                    IntentManager.file(this);
                    break;
                case 200:
                    if (fragmont.getCurrent() instanceof SampleFragment)
                        IntentManager.download(this, ((SampleFragment) fragmont.getCurrent()).binding.scaleTextView.getText().toString(), ((SampleFragment) fragmont.getCurrent()).selectedProfileUrl);

                    break;
                case 300:
                    IntentManager.gallery(this);
                    break;
                case 400:
                    if (fragmont.getCurrent() instanceof CreateCenterFragment)
                        ((CreateCenterFragment) fragmont.getCurrent()).avatarPath = IntentManager.camera(this);

                    if (fragmont.getChild() instanceof EditCenterTabAvatarFragment)
                        ((EditCenterTabAvatarFragment) fragmont.getChild()).avatarPath = IntentManager.camera(this);

                    if (fragmont.getChild() instanceof EditUserTabAvatarFragment)
                        ((EditUserTabAvatarFragment) fragmont.getChild()).avatarPath = IntentManager.camera(this);

                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK: {
                switch (requestCode) {
                    case 100:
                        if (fragmont.getCurrent() instanceof CreateDocumentFragment)
                            ((CreateDocumentFragment) fragmont.getCurrent()).responseAction("file", data);

                        if (fragmont.getCurrent() instanceof CreatePracticeFragment)
                            ((CreatePracticeFragment) fragmont.getCurrent()).responseAction("file", data);

                        break;
                    case 300:
                        if (fragmont.getCurrent() instanceof CreateCenterFragment)
                            ((CreateCenterFragment) fragmont.getCurrent()).responseAction("gallery", data);

                        if (fragmont.getChild() instanceof EditCenterTabAvatarFragment)
                            ((EditCenterTabAvatarFragment) fragmont.getChild()).responseAction("gallery", data);

                        if (fragmont.getChild() instanceof EditUserTabAvatarFragment)
                            ((EditUserTabAvatarFragment) fragmont.getChild()).responseAction("gallery", data);

                        break;
                    case 400:
                        if (fragmont.getCurrent() instanceof CreateCenterFragment)
                            ((CreateCenterFragment) fragmont.getCurrent()).responseAction("camera", data);

                        if (fragmont.getChild() instanceof EditCenterTabAvatarFragment)
                            ((EditCenterTabAvatarFragment) fragmont.getChild()).responseAction("camera", data);

                        if (fragmont.getChild() instanceof EditUserTabAvatarFragment)
                            ((EditUserTabAvatarFragment) fragmont.getChild()).responseAction("camera", data);

                        break;
                    case UCrop.REQUEST_CROP:
                        if (fragmont.getCurrent() instanceof CreateCenterFragment)
                            ((CreateCenterFragment) fragmont.getCurrent()).responseAction("crop", data);

                        if (fragmont.getChild() instanceof EditCenterTabAvatarFragment)
                            ((EditCenterTabAvatarFragment) fragmont.getChild()).responseAction("crop", data);

                        if (fragmont.getChild() instanceof EditUserTabAvatarFragment)
                            ((EditUserTabAvatarFragment) fragmont.getChild()).responseAction("crop", data);

                        break;
                }
            } break;
            case RESULT_ERROR: {
                switch (requestCode) {
                    case 100:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastIntentFileException));
                        break;
                    case 300:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastIntentGalleryException));
                        break;
                    case 400:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastIntentCameraException));
                        break;
                }
            } break;
            case UCrop.RESULT_ERROR: {
                ToastManager.showErrorToast(this, getResources().getString(R.string.ToastIntentCropException));
                break;
            }
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
        if (binding.getRoot().isDrawerOpen(GravityCompat.START))
            setDrawer("closeDrawer");
        else if (navigatoon.getCurrentDestinationId() != navigatoon.getStartDestinationId())
            navigatoon.navigateUp();
        else
            IntentManager.finish(this);
    }

}