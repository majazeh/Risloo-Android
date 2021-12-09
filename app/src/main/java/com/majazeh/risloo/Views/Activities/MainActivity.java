package com.majazeh.risloo.Views.Activities;

import static android.content.RestrictionsManager.RESULT_ERROR;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.Utils.Managers.AnimateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PaymentManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
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
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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

    // Vars
    private boolean userSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorator();

        initializer();

        ExtendException.activity = this;

        listener();

        setData();

        setDrawer();

        setToolbar();

        PaymentManager.callback(this);
    }

    private void decorator() {
        Decoraton decoraton = new Decoraton(this);

        decoraton.showSystemUI(true, true);
        decoraton.setSystemUIColor(getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));

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

        InitManager.imgResTint(this, binding.contentIncludeLayout.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.CoolGray500);
        InitManager.imgResTint(this, binding.contentIncludeLayout.logoutImageView.getRoot(), R.drawable.ic_user_crown_light, R.color.CoolGray500);

        InitManager.fixedVerticalRecyclerView(this, binding.navIncludeLayout.listRecyclerView.getRoot(), getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> IntentManager.risloo(this)).widget(binding.contentIncludeLayout.debugTextView.getRoot());

        CustomClickView.onDelayedListener(() -> binding.getRoot().openDrawer(GravityCompat.START)).widget(binding.contentIncludeLayout.menuImageView.getRoot());

        CustomClickView.onDelayedListener(() -> changeUser("logoutFormOtherUser", "")).widget(binding.contentIncludeLayout.logoutImageView.getRoot());

        binding.contentIncludeLayout.toolbarIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.contentIncludeLayout.toolbarIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.contentIncludeLayout.toolbarIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
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
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.contentIncludeLayout.headerAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0 && binding.contentIncludeLayout.seperateView.getVisibility() == View.VISIBLE) {
                AnimateManager.animateStatusBarColor(this, 300, getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.White));
                AnimateManager.animateAppBarColor(binding.contentIncludeLayout.headerAppBarLayout, 300, getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.White));

                binding.contentIncludeLayout.seperateView.setVisibility(View.GONE);
            }

            if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() && binding.contentIncludeLayout.seperateView.getVisibility() == View.GONE) {
                AnimateManager.animateStatusBarColor(this, 300, getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));
                AnimateManager.animateAppBarColor(binding.contentIncludeLayout.headerAppBarLayout, 300, getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));

                binding.contentIncludeLayout.seperateView.setVisibility(View.VISIBLE);
            }
        });

        navigatoon.getNavController().addOnDestinationChangedListener((controller, destination, arguments) -> {
            SpannableStringBuilder faBreadCump = breadCrumb.getFa(destination, arguments);

            binding.contentIncludeLayout.headerAppBarLayout.setExpanded(true);

            binding.contentIncludeLayout.breadcumpIncludeLayout.getRoot().setText(faBreadCump);
            binding.contentIncludeLayout.breadcumpIncludeLayout.getRoot().setMovementMethod(LinkMovementMethod.getInstance());

            mainNavAdapter.setFocused(faBreadCump.toString());

            ExtendEvent.cancelRequest();
        });
    }

    public void setData() {
        if (!singleton.getToken().equals("")) {
            UserModel model = singleton.getUserModel();

            if (model.getName() != null && !model.getName().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(model.getName());
            } else if (model.getId() != null && !model.getId().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(model.getId());
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
            }

            if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.contentIncludeLayout.toolbarIncludeLayout.avatarImageView);
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.getText().toString()));
            }

            if (model.getTreasuries() != null) {
                int balance = 0;

                for (TypeModel typeModel : model.getTreasuries().data()) {
                    TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel;

                    if (treasuriesModel.getSymbol().equals("wallet") || treasuriesModel.getSymbol().equals("gift")) {
                        balance += treasuriesModel.getBalance();
                    }
                }

                if (balance != 0) {
                    String wallet = StringManager.separate(String.valueOf(balance)) + " " + getResources().getString(R.string.MainToman);

                    binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText(wallet);
                    binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setVisibility(View.VISIBLE);

                    binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setMaxLines(1);
                } else {
                    binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText("");
                    binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setVisibility(View.GONE);

                    binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setMaxLines(2);
                }
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText("");
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setVisibility(View.GONE);

                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setMaxLines(2);
            }

            if (singleton.getOtherUser()) {
                binding.contentIncludeLayout.logoutImageView.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.contentIncludeLayout.logoutImageView.getRoot().setVisibility(View.GONE);
            }
        } else {
            IntentManager.auth(this, "login");
        }
    }

    private void setDrawer() {
        ArrayList<TypeModel> values = new ArrayList<>();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();

        // Dashboard
        titles.add(getResources().getString(R.string.MainTitleDashboard));
        description.add(getResources().getString(R.string.MainDescDashboard));
        images.add(R.drawable.ic_tachometer_alt_light);

        // Centers
        titles.add(getResources().getString(R.string.MainTitleCenters));
        description.add(getResources().getString(R.string.MainDescCenters));
        images.add(R.drawable.ic_building_light);

        // Sessions
        titles.add(getResources().getString(R.string.MainTitleSessions));
        description.add(getResources().getString(R.string.MainDescSessions));
        images.add(R.drawable.ic_user_friends_light);

        // Users
        if (permissoon.showUsers(singleton.getUserModel())) {
            titles.add(getResources().getString(R.string.MainTitleUsers));
            description.add(getResources().getString(R.string.MainDescUsers));
            images.add(R.drawable.ic_users_light);
        }

        // Samples
        titles.add(getResources().getString(R.string.MainTitleSamples));
        description.add(getResources().getString(R.string.MainDescSamples));
        images.add(R.drawable.ic_vial_light);

        // BulkSamples
        if (permissoon.showBulkSamples(singleton.getUserModel())) {
            titles.add(getResources().getString(R.string.MainTitleBulkSamples));
            description.add(getResources().getString(R.string.MainDescBulkSamples));
            images.add(R.drawable.ic_users_medical_light);
        }

        // Scales
        if (permissoon.showScales(singleton.getUserModel())) {
            titles.add(getResources().getString(R.string.MainTitleScales));
            description.add(getResources().getString(R.string.MainDescScales));
            images.add(R.drawable.ic_balance_scale_light);
        }

        // Downloads
        titles.add(getResources().getString(R.string.MainTitleDownloads));
        description.add(getResources().getString(R.string.MainDescDownloads));
        images.add(R.drawable.ic_arrow_to_bottom_light);

        for (int i = 0; i < titles.size(); i++) {
            try {
                JSONObject object = new JSONObject();
                object.put("title", titles.get(i));
                object.put("description", description.get(i));
                object.put("image", images.get(i));

                TypeModel model = new TypeModel(object);

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mainNavAdapter.setItems(values);
            binding.navIncludeLayout.listRecyclerView.getRoot().setAdapter(mainNavAdapter);
        }
    }

    private void setToolbar() {
        ArrayList<String> items = new ArrayList<>();

        items.add(getResources().getString(R.string.MainToolbarMe));
        items.add(getResources().getString(R.string.MainToolbarAccounting));
        items.add(getResources().getString(R.string.MainToolbarPayments));
        items.add(getResources().getString(R.string.MainToolbarLogout));
        items.add("");

        InitManager.selectToolbarSpinner(this, binding.contentIncludeLayout.toolbarIncludeLayout.selectSpinner, items);
    }

    private void setHashmap(String userId) {
        if (!userId.equals(""))
            data.put("id", userId);
        else
            data.remove("id");

        header.put("Authorization", singleton.getAuthorization());
    }

    private void resetUser(String method, AuthModel model) {
        singleton.login(model);
        singleton.otherUser(method.equals("loginOtherUser"));

        setData();

        setDrawer();

        setToolbar();

        DialogManager.dismissLoadingDialog();

        if (method.equals("loginOtherUser"))
            SnackManager.showSuccesSnack(MainActivity.this, getResources().getString(R.string.SnackLoginOtherUser));
        else
            SnackManager.showSuccesSnack(MainActivity.this, getResources().getString(R.string.SnackLogoutFormOtherUser));

        navigatoon.navigateToDashboardFragment();
    }

    public void changeFragment(String title) {
        switch (title) {
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

        binding.getRoot().closeDrawer(GravityCompat.START);
    }

    public void changeUser(String method, String userId) {
        DialogManager.showLoadingDialog(this, "");

        setHashmap(userId);

        if (method.equals("loginOtherUser")) {
            Auth.loginOtherUser(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    runOnUiThread(() -> resetUser(method, model));
                }

                @Override
                public void onFailure(String response) {
                    runOnUiThread(() -> {
                        // TODO : Place Code If Needed
                    });
                }
            });
        } else {
            Auth.logoutFromOtherUser(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    runOnUiThread(() -> resetUser(method, model));
                }

                @Override
                public void onFailure(String response) {
                    runOnUiThread(() -> {
                        // TODO : Place Code If Needed
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
            binding.getRoot().closeDrawer(GravityCompat.START);
        else if (navigatoon.getCurrentDestinationId() != R.id.dashboardFragment)
            navigatoon.navigateUp();
        else
            IntentManager.finish(this);
    }

}