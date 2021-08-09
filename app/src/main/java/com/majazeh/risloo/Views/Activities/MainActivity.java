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
import android.os.Build;
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
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Fragmont;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Adapters.Recycler.NavsAdapter;
import com.majazeh.risloo.Views.BottomSheets.LogoutBottomSheet;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
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
    public Singleton singleton;

    // Adapters
    private NavsAdapter navsAdapter;

    // BottomSheets
    private LogoutBottomSheet logoutBottomSheet;

    // Dialogs
    public LoadingDialog loadingDialog;

    // Objects
    public ControlEditText controlEditText;
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

        ExtendOnFailureException.activity = this;

        detector();

        listener();

        setData();

        setToolbar();

        setDrawer();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            windowDecorator.navShowSystemUI(false, true);
            windowDecorator.setSystemUIColor(getResources().getColor(R.color.Red500), getResources().getColor(R.color.Gray50));

            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            windowDecorator.navShowSystemUI(true, true);
            windowDecorator.setSystemUIColor(Color.TRANSPARENT, getResources().getColor(R.color.Gray50));

            binding.contentIncludeLayout.debugTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void initializer() {
        breadCrumb = new BreadCrumb(this);

        singleton = new Singleton(this);

        navsAdapter = new NavsAdapter(this);

        logoutBottomSheet = new LogoutBottomSheet();

        loadingDialog = new LoadingDialog();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentIncludeLayout.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        fragmont = new Fragmont(navHostFragment);

        InitManager.imgResTint(this, binding.contentIncludeLayout.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.fixedVerticalRecyclerView(this, binding.navIncludeLayout.listRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._8sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.contentIncludeLayout.menuImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);

            binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            binding.getRoot().openDrawer(GravityCompat.START);
        }).widget(binding.contentIncludeLayout.menuImageView.getRoot());

        binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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
                        case "کیف پول\u200Cها": {
                            NavDirections action = NavigationMainDirections.actionGlobalTreasuriesFragment();
                            navController.navigate(action);
                        } break;
                        case "صورت حساب\u200Cها": {
                            NavDirections action = NavigationMainDirections.actionGlobalBillingsFragment();
                            navController.navigate(action);
                        } break;
                        case "شارژ حساب": {
                            NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment();
                            navController.navigate(action);
                        } break;
                        case "خروج": {
                            logoutBottomSheet.show(MainActivity.this.getSupportFragmentManager(), "logoutBottomSheet");
                            logoutBottomSheet.setData(singleton.getName(), singleton.getAvatar());
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
        if (!singleton.getName().equals("")) {
            binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(singleton.getName());
        } else {
            binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (!singleton.getMoney().equals("")) {
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
    }

    private void setToolbar() {
        ArrayList<String> items = new ArrayList<>();

        items.add(getResources().getString(R.string.MainMe));
        items.add(getResources().getString(R.string.MainLogout));
        items.add("");

        InitManager.toolbarCustomSpinner(this, binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner, items);
    }

    private void setDrawer() {
        ArrayList<TypeModel> values = new ArrayList<>();

        String[] titles = getResources().getStringArray(R.array.MainTitles);
        String[] description = getResources().getStringArray(R.array.MainDescriptions);
        int[] images = new int[]{R.drawable.ic_tachometer_alt_light, R.drawable.ic_building_light, R.drawable.ic_user_friends_light, R.drawable.ic_users_light, R.drawable.ic_balance_scale_light, R.drawable.ic_vial_light, R.drawable.ic_users_medical_light};

        for (int i = 0; i < titles.length; i++) {
            try {
                JSONObject object = new JSONObject();
                object.put("title", titles[i]);
                object.put("description", description[i]);
                object.put("image", images[i]);

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
            case "پیش\u200Cخوان": {
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
                        ToastManager.showDefaultToast(this, getResources().getString(R.string.ToastFileException));
                        break;
                    case 200:
                        ToastManager.showDefaultToast(this, getResources().getString(R.string.ToastStorageException));
                        break;
                    case 300:
                        ToastManager.showDefaultToast(this, getResources().getString(R.string.ToastGalleryException));
                        break;
                    case 400:
                        ToastManager.showDefaultToast(this, getResources().getString(R.string.ToastCameraException));
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
        if (binding.getRoot().isDrawerOpen(GravityCompat.START))
            binding.getRoot().closeDrawer(GravityCompat.START);
        else if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.dashboardFragment)
            navController.navigateUp();
        else
            IntentManager.finish(this);
    }

}