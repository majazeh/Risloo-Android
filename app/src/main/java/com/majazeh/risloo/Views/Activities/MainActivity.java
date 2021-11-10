package com.majazeh.risloo.Views.Activities;

import static android.content.RestrictionsManager.RESULT_ERROR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.BreadCrumb;
import com.majazeh.risloo.Utils.Entities.Decoraton;
import com.majazeh.risloo.Utils.Entities.Fragmont;
import com.majazeh.risloo.Utils.Entities.Inputon;
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
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexNavAdapter;
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
    public Permissoon permissoon;
    public Singleton singleton;
    public Validatoon validatoon;

    // Adapters
    private IndexNavAdapter indexNavAdapter;

    // Objects
    public NavHostFragment navHostFragment;
    public NavController navController;

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

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            decoraton.showSystemUI(false, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.Red600), getResources().getColor(R.color.CoolGray50));

            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            decoraton.showSystemUI(true, true);
            decoraton.setSystemUIColor(getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));

            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void initializer() {
        breadCrumb = new BreadCrumb(this);

        inputon = new Inputon();

        singleton = new Singleton(this);

        permissoon = new Permissoon();

        validatoon = new Validatoon();

        indexNavAdapter = new IndexNavAdapter(this);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentIncludeLayout.fragmentNavHostFragment.getId());
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        fragmont = new Fragmont(navHostFragment);

        InitManager.imgResTint(this, binding.contentIncludeLayout.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.CoolGray500);
        InitManager.imgResTint(this, binding.contentIncludeLayout.logoutImageView.getRoot(), R.drawable.ic_user_crown_light, R.color.CoolGray500);

        InitManager.fixedVerticalRecyclerView(this, binding.navIncludeLayout.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            binding.getRoot().openDrawer(GravityCompat.START);
        }).widget(binding.contentIncludeLayout.menuImageView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            userChange("logoutFormOtherUser", "");
        }).widget(binding.contentIncludeLayout.logoutImageView.getRoot());

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
                        case "مشاهده پروفایل": {
                            NavDirections action = NavigationMainDirections.actionGlobalMeFragment(singleton.getUserModel());
                            navController.navigate(action);
                        } break;
                        case "حسابداری": {
                            NavDirections action = NavigationMainDirections.actionGlobalAccountingFragment();
                            navController.navigate(action);
                        } break;
                        case "شارژ حساب": {
                            NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment(null);
                            navController.navigate(action);
                        } break;
                        case "خروج": {
                            SheetManager.showLogoutBottomSheet(MainActivity.this, singleton.getUserModel());
                        } break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding.contentIncludeLayout.headerAppBarLayout.setExpanded(true);

            binding.contentIncludeLayout.breadcumpIncludeLayout.getRoot().setText(breadCrumb.getFa(destination, arguments));
            binding.contentIncludeLayout.breadcumpIncludeLayout.getRoot().setMovementMethod(LinkMovementMethod.getInstance());

            indexNavAdapter.setFocused(breadCrumb.getFa(destination, arguments).toString());
        });

        binding.contentIncludeLayout.headerAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0 && binding.contentIncludeLayout.seperateView.getVisibility() == View.VISIBLE) {
                if (!BuildConfig.BUILD_TYPE.equals("debug"))
                    AnimateManager.animateStatusBarColor(this, 300, getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.White));

                AnimateManager.animateAppBarColor(binding.contentIncludeLayout.headerAppBarLayout, 300, getResources().getColor(R.color.CoolGray50), getResources().getColor(R.color.White));
                binding.contentIncludeLayout.seperateView.setVisibility(View.GONE);
            }

            if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() && binding.contentIncludeLayout.seperateView.getVisibility() == View.GONE) {
                if (!BuildConfig.BUILD_TYPE.equals("debug"))
                    AnimateManager.animateStatusBarColor(this, 300, getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));

                AnimateManager.animateAppBarColor(binding.contentIncludeLayout.headerAppBarLayout, 300, getResources().getColor(R.color.White), getResources().getColor(R.color.CoolGray50));
                binding.contentIncludeLayout.seperateView.setVisibility(View.VISIBLE);
            }
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
                    binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText(StringManager.separate(String.valueOf(balance)) + " " + getResources().getString(R.string.MainToman));
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

        titles.add(getResources().getString(R.string.MainTitleDashboard));
        titles.add(getResources().getString(R.string.MainTitleCenters));
        titles.add(getResources().getString(R.string.MainTitleSessions));
        description.add(getResources().getString(R.string.MainDescDashboard));
        description.add(getResources().getString(R.string.MainDescCenters));
        description.add(getResources().getString(R.string.MainDescSessions));
        images.add(R.drawable.ic_tachometer_alt_light);
        images.add(R.drawable.ic_building_light);
        images.add(R.drawable.ic_user_friends_light);

        if (permissoon.showUsers(singleton.getUserModel())) {
            titles.add(getResources().getString(R.string.MainTitleUsers));
            description.add(getResources().getString(R.string.MainDescUsers));
            images.add(R.drawable.ic_users_light);
        }

        titles.add(getResources().getString(R.string.MainTitleScales));
        titles.add(getResources().getString(R.string.MainTitleSamples));
        description.add(getResources().getString(R.string.MainDescScales));
        description.add(getResources().getString(R.string.MainDescSamples));
        images.add(R.drawable.ic_balance_scale_light);
        images.add(R.drawable.ic_vial_light);

        if (permissoon.showBulkSamples(singleton.getUserModel())) {
            titles.add(getResources().getString(R.string.MainTitleBulkSamples));
            description.add(getResources().getString(R.string.MainDescBulkSamples));
            images.add(R.drawable.ic_users_medical_light);
        }

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

            indexNavAdapter.setItems(values);
            binding.navIncludeLayout.listRecyclerView.setAdapter(indexNavAdapter);
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

    public void responseAdapter(String item) {
        switch (item) {
            case "داشبورد": {
                NavDirections action = NavigationMainDirections.actionGlobalDashboardFragment();
                navController.navigate(action);
            } break;
            case "مراکز درمانی": {
                NavDirections action = NavigationMainDirections.actionGlobalCentersFragment();
                navController.navigate(action);
            } break;
            case "پرونده\u200Cها": {
                NavDirections action = NavigationMainDirections.actionGlobalCasesFragment();
                navController.navigate(action);
            } break;
            case "جلسات": {
                NavDirections action = NavigationMainDirections.actionGlobalSessionsFragment();
                navController.navigate(action);
            } break;
            case "اعضاء": {
                NavDirections action = NavigationMainDirections.actionGlobalUsersFragment();
                navController.navigate(action);
            } break;
            case "ارزیابی\u200Cها": {
                NavDirections action = NavigationMainDirections.actionGlobalScalesFragment();
                navController.navigate(action);
            } break;
            case "نمونه\u200Cها": {
                NavDirections action = NavigationMainDirections.actionGlobalSamplesFragment(null, null);
                navController.navigate(action);
            } break;
            case "نمونه\u200Cهای گروهی": {
                NavDirections action = NavigationMainDirections.actionGlobalBulkSamplesFragment();
                navController.navigate(action);
            } break;
            case "مدارک": {
                NavDirections action = NavigationMainDirections.actionGlobalDocumentsFragment();
                navController.navigate(action);
            } break;
        }

        binding.getRoot().closeDrawer(GravityCompat.START);
    }

    public void userChange(String method, String userId) {
        DialogManager.showLoadingDialog(this, "");

        HashMap data = new HashMap<>();
        HashMap header = new HashMap<>();

        if (!userId.equals(""))
            data.put("id", userId);

        header.put("Authorization", singleton.getAuthorization());

        if (method.equals("loginOtherUser")) {
            Auth.loginOtherUser(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    runOnUiThread(() -> {
                        singleton.login(model);
                        singleton.otherUser(true);

                        setData();

                        setDrawer();

                        setToolbar();

                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(MainActivity.this, getResources().getString(R.string.SnackLoginOtherUser));

                        NavDirections action = NavigationMainDirections.actionGlobalDashboardFragment();
                        navController.navigate(action);
                    });
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

                    runOnUiThread(() -> {
                        singleton.login(model);
                        singleton.otherUser(false);

                        setData();

                        setDrawer();

                        setToolbar();

                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(MainActivity.this, getResources().getString(R.string.SnackLogoutFormOtherUser));

                        NavDirections action = NavigationMainDirections.actionGlobalDashboardFragment();
                        navController.navigate(action);
                    });
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
                        IntentManager.download(this, ((SampleFragment) fragmont.getCurrent()).selectedProfileUrl);

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
            case RESULT_CANCELED: {
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
                        inputon.clear(this, inputon.editText);
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
        else if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.dashboardFragment)
            navController.navigateUp();
        else
            IntentManager.finish(this);
    }

}