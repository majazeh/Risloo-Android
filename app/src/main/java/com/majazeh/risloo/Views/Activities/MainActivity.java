package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.ExtendOnFailureException;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Views.Adapters.Recycler.NavsAdapter;
import com.majazeh.risloo.Views.BottomSheets.LogoutBottomSheet;
import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
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

    // Adapters
    private NavsAdapter navsAdapter;

    // BottomSheets
    private LogoutBottomSheet logoutBottomSheet;

    // Singleton
    public Singleton singleton;

    // Dialogs
    public LoadingDialog loadingDialog;

    // Objects
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;

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
        navsAdapter = new NavsAdapter(this);

        logoutBottomSheet = new LogoutBottomSheet();

        singleton = new Singleton(this);

        loadingDialog = new LoadingDialog();

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentIncludeLayout.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        InitManager.imgResTint(this, binding.contentIncludeLayout.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.imgResTint(this, binding.contentIncludeLayout.enterImageView.getRoot(), R.drawable.ic_user_crown_light, R.color.Gray500);
        InitManager.imgResTint(this, binding.contentIncludeLayout.notificationImageView.getRoot(), R.drawable.ic_bell_light, R.color.Gray500);

        InitManager.fixedCustomSpinner(this, binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner, R.array.MainRows, "toolbar");

        InitManager.fixedVerticalRecyclerView(this, binding.navIncludeLayout.listRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._8sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.contentIncludeLayout.menuImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            binding.contentIncludeLayout.enterImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            binding.contentIncludeLayout.notificationImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);

            binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> binding.getRoot().openDrawer(GravityCompat.START)).widget(binding.contentIncludeLayout.menuImageView.getRoot());

        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.contentIncludeLayout.enterImageView.getRoot());

        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.contentIncludeLayout.notificationImageView.getRoot());

        binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

                binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setSelection(binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            binding.contentIncludeLayout.breadcumpTextView.setText();
//            binding.contentIncludeLayout.breadcumpTextView.setMovementMethod(LinkMovementMethod.getInstance());
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
            case "اسناد و مدارک": {
                NavDirections action = NavigationMainDirections.actionGlobalDocumentsFragment();
                navController.navigate(action);
            } break;
        }

        binding.getRoot().closeDrawer(GravityCompat.START);

        navsAdapter.notifyDataSetChanged();
    }

    private Fragment getCurrent() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateCenterFragment)
                return (CreateCenterFragment) fragment;

            else if (fragment instanceof EditCenterFragment) {
                Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditCenterAvatarFragment)
                        return (EditCenterAvatarFragment) childFragment;

            } else if (fragment instanceof EditUserFragment) {
                Fragment childFragment = ((EditUserFragment) fragment).adapter.hashMap.get(((EditUserFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditUserAvatarFragment)
                        return (EditUserAvatarFragment) childFragment;
            }

            else if (fragment instanceof CreateDocumentFragment)
                return (CreateDocumentFragment) fragment;

            else if (fragment instanceof CreatePracticeFragment)
                return (CreatePracticeFragment) fragment;

        return null;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 100:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED)
                            return;
                    }

                    IntentManager.file(this);
                }
                break;
            case 300:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED)
                            return;
                    }

                    IntentManager.gallery(this);
                }
                break;
            case 400:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED)
                            return;
                    }

                    if (getCurrent() != null) {
                        if (getCurrent() instanceof CreateCenterFragment)
                            ((CreateCenterFragment) getCurrent()).avatarPath = IntentManager.camera(this);

                        else if (getCurrent() instanceof EditCenterAvatarFragment)
                            ((EditCenterAvatarFragment) getCurrent()).avatarPath = IntentManager.camera(this);

                        else if (getCurrent() instanceof EditUserAvatarFragment)
                            ((EditUserAvatarFragment) getCurrent()).avatarPath = IntentManager.camera(this);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK: {
                if (getCurrent() != null) {
                    if (getCurrent() instanceof CreateCenterFragment) {
                        if (requestCode == 300)
                            ((CreateCenterFragment) getCurrent()).responseAction("gallery", data);
                        else if (requestCode == 400)
                            ((CreateCenterFragment) getCurrent()).responseAction("camera", data);

                    } else if (getCurrent() instanceof EditCenterAvatarFragment) {
                        if (requestCode == 300)
                            ((EditCenterAvatarFragment) getCurrent()).responseAction("gallery", data);
                        else if (requestCode == 400)
                            ((EditCenterAvatarFragment) getCurrent()).responseAction("camera", data);

                    } else if (getCurrent() instanceof EditUserAvatarFragment) {
                        if (requestCode == 300)
                            ((EditUserAvatarFragment) getCurrent()).responseAction("gallery", data);
                        else if (requestCode == 400)
                            ((EditUserAvatarFragment) getCurrent()).responseAction("camera", data);

                    } else if (getCurrent() instanceof CreateDocumentFragment) {
                        if (requestCode == 100)
                            ((CreateDocumentFragment) getCurrent()).responseAction("file", data);

                    } else if (getCurrent() instanceof CreatePracticeFragment) {
                        if (requestCode == 100)
                            ((CreatePracticeFragment) getCurrent()).responseAction("file", data);
                    }
                }
            } break;
            case RESULT_CANCELED: {
                switch (requestCode) {
                    case 100:
                        Toast.makeText(this, "File Exception", Toast.LENGTH_SHORT).show();
                        break;
                    case 200:
                        Toast.makeText(this, "Storage Exception", Toast.LENGTH_SHORT).show();
                        break;
                    case 300:
                        Toast.makeText(this, "Gallery Exception", Toast.LENGTH_SHORT).show();
                        break;
                    case 400:
                        Toast.makeText(this, "Camera Exception", Toast.LENGTH_SHORT).show();
                        break;
                }
            } break;
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.getRoot().isDrawerOpen(GravityCompat.START))
            binding.getRoot().closeDrawer(GravityCompat.START);
        else if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.dashboardFragment)
            navController.navigateUp();
        else
            finish();
    }

}