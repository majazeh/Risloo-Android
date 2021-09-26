package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.BreadCrumb;
import com.majazeh.risloo.Utils.Config.ExtendException;
import com.majazeh.risloo.Utils.Entities.Fragmont;
import com.majazeh.risloo.Utils.Entities.Permissoon;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.PaymentManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Entities.Inputor;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Utils.Entities.Decorator;
import com.majazeh.risloo.Utils.Entities.Validatoon;
import com.majazeh.risloo.Views.Adapters.Recycler.NavsAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.databinding.ActivityMainBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;

    // Entities
    private BreadCrumb breadCrumb;
    public Fragmont fragmont;
    public Inputor inputor;
    public Permissoon permissoon;
    public Singleton singleton;
    public Validatoon validatoon;

    // Adapters
    private NavsAdapter navsAdapter;

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

        setToolbar();

        setDrawer();

        PaymentManager.callback(this);
    }

    private void decorator() {
        Decorator decorator = new Decorator(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            decorator.showSystemUI(false, true);
            decorator.setSystemUIColor(getResources().getColor(R.color.Red600), getResources().getColor(R.color.Gray50));

            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            decorator.showSystemUI(true, true);
            decorator.setSystemUIColor(Color.TRANSPARENT, getResources().getColor(R.color.Gray50));

            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void initializer() {
        breadCrumb = new BreadCrumb(this);

        inputor = new Inputor();

        singleton = new Singleton(this);

        permissoon = new Permissoon();

        validatoon = new Validatoon();

        navsAdapter = new NavsAdapter(this);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentIncludeLayout.fragmentNavHostFragment.getId());
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        fragmont = new Fragmont(navHostFragment);

        InitManager.imgResTint(this, binding.contentIncludeLayout.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.fixedVerticalRecyclerView(this, binding.navIncludeLayout.listRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._8sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            binding.getRoot().openDrawer(GravityCompat.START);
        }).widget(binding.contentIncludeLayout.menuImageView.getRoot());

        binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                            SheetManager.showLogoutBottomSheet(MainActivity.this, singleton.getName(), singleton.getAvatar());
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
            binding.contentIncludeLayout.breadcumpIncludeLayout.getRoot().setText(breadCrumb.getFa(destination, arguments));
            binding.contentIncludeLayout.breadcumpIncludeLayout.getRoot().setMovementMethod(LinkMovementMethod.getInstance());

            navsAdapter.setFocused(breadCrumb.getFa(destination, arguments).toString());
        });
    }

    public void setData() {
        if (!singleton.getToken().equals("")) {
            if (!singleton.getName().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(singleton.getName());
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
            }

            if (!singleton.getMoney().equals("0")) {
                String money = StringManager.separate(singleton.getMoney()) + " " + getResources().getString(R.string.MainToman);
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText(money);
            } else {
                String money = "0" + " " + getResources().getString(R.string.MainToman);
                binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText(money);
            }

            if (!singleton.getAvatar().equals("")) {
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(singleton.getAvatar()).placeholder(R.color.Blue500).into(binding.contentIncludeLayout.toolbarIncludeLayout.avatarImageView);
            } else {
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.getText().toString()));
            }
        } else {
            IntentManager.auth(this, "login");
        }
    }

    private void setToolbar() {
        ArrayList<String> items = new ArrayList<>();

        items.add(getResources().getString(R.string.MainTitleMe));
        items.add(getResources().getString(R.string.MainTitleAccounting));
        items.add(getResources().getString(R.string.MainTitlePayments));
        items.add(getResources().getString(R.string.MainTitleLogout));
        items.add("");

        InitManager.toolbarCustomSpinner(this, binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner, items);
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

            navsAdapter.setItems(values);
            binding.navIncludeLayout.listRecyclerView.setAdapter(navsAdapter);
        }
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
                NavDirections action = NavigationMainDirections.actionGlobalSamplesFragment();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED)
                    return;
            }

            switch (requestCode) {
                case 100:
                    IntentManager.file(this);
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
                if (fragmont.getCurrent() instanceof CreateCenterFragment) {
                    if (requestCode == 300)
                        ((CreateCenterFragment) fragmont.getCurrent()).responseAction("gallery", data);
                    else if (requestCode == 400)
                        ((CreateCenterFragment) fragmont.getCurrent()).responseAction("camera", data);
                }

                if (fragmont.getChild() instanceof EditCenterTabAvatarFragment) {
                    if (requestCode == 300)
                        ((EditCenterTabAvatarFragment) fragmont.getChild()).responseAction("gallery", data);
                    else if (requestCode == 400)
                        ((EditCenterTabAvatarFragment) fragmont.getChild()).responseAction("camera", data);
                }

                if (fragmont.getChild() instanceof EditUserTabAvatarFragment) {
                    if (requestCode == 300)
                        ((EditUserTabAvatarFragment) fragmont.getChild()).responseAction("gallery", data);
                    else if (requestCode == 400)
                        ((EditUserTabAvatarFragment) fragmont.getChild()).responseAction("camera", data);
                }

                if (fragmont.getCurrent() instanceof CreateDocumentFragment) {
                    if (requestCode == 100)
                        ((CreateDocumentFragment) fragmont.getCurrent()).responseAction("file", data);
                }

                if (fragmont.getCurrent() instanceof CreatePracticeFragment) {
                    if (requestCode == 100)
                        ((CreatePracticeFragment) fragmont.getCurrent()).responseAction("file", data);
                }

            } break;
            case RESULT_CANCELED: {
                switch (requestCode) {
                    case 100:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastFileException));
                        break;
                    case 200:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastStorageException));
                        break;
                    case 300:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastGalleryException));
                        break;
                    case 400:
                        ToastManager.showErrorToast(this, getResources().getString(R.string.ToastCameraException));
                        break;
                }
            } break;
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
                    if (inputor.editText != null && inputor.editText.hasFocus()) {
                        inputor.clear(this, inputor.editText);
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