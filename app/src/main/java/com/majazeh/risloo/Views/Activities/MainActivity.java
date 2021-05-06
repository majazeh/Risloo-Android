package com.majazeh.risloo.Views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Entities.Singleton;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.WindowDecorator;
import com.majazeh.risloo.Utils.Widgets.ControlEditText;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Adapters.Recycler.NavsAdapter;
import com.majazeh.risloo.Views.BottomSheets.LogoutBottomSheet;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateDocumentFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreatePracticeFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;

    // Singleton
    public Singleton singleton;

    // Adapters
    private NavsAdapter navsAdapter;

    // BottomSheets
    private LogoutBottomSheet logoutBottomSheet;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    public ControlEditText controlEditText;
    public NavHostFragment navHostFragment;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorator();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializer();

        detector();

        listener();

        setData();

        setRecyclerView();
    }

    private void decorator() {
        WindowDecorator windowDecorator = new WindowDecorator();

        windowDecorator.lightNavShowSystemUI(this);
        windowDecorator.lightSetSystemUIColor(this, Color.TRANSPARENT, getResources().getColor(R.color.Gray50));
    }

    private void initializer() {
        singleton = new Singleton(this);

        navsAdapter = new NavsAdapter(this);

        logoutBottomSheet = new LogoutBottomSheet();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._8sdp));

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        controlEditText = new ControlEditText();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentIncludeLayout.fragmentNavHostFragment.getId());

        navController = Objects.requireNonNull(navHostFragment).getNavController();

        InitManager.imgResTint(this, binding.contentIncludeLayout.menuImageView.getRoot(), R.drawable.ic_bars_light, R.color.Gray500);
        InitManager.imgResTint(this, binding.contentIncludeLayout.notificationImageView.getRoot(), R.drawable.ic_bell_light, R.color.Gray500);

        InitManager.spinner(this, binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner, R.array.MainRows, "toolbar");

        InitManager.recyclerView(binding.navIncludeLayout.listRecyclerView.getRoot(), itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.contentIncludeLayout.menuImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);
            binding.contentIncludeLayout.notificationImageView.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_gray300);

            binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray300_ripple_blue300);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> binding.getRoot().openDrawer(GravityCompat.START)).widget(binding.contentIncludeLayout.menuImageView.getRoot());

        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.contentIncludeLayout.notificationImageView.getRoot());

        binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        navigator(R.id.meFragment);
                        break;
                    case 1:
                        navigator(R.id.treasuriesFragment);
                        break;
                    case 2:
                        navigator(R.id.billingsFragment);
                        break;
                    case 3:
                        navigator(R.id.paymentsFragment);
                        break;
                    case 4:
                        logoutBottomSheet.show(MainActivity.this.getSupportFragmentManager(), "logoutBottomSheet");
                        logoutBottomSheet.setData(singleton.getName(), singleton.getAvatar());
                        break;
                }

                binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.setSelection(binding.contentIncludeLayout.toolbarIncludeLayout.toolbarSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding.contentIncludeLayout.breadcumpTextView.setText(StringManager.clickableNavBackStack(this, controller));
            binding.contentIncludeLayout.breadcumpTextView.setMovementMethod(LinkMovementMethod.getInstance());
        });
    }

    private void setData() {
        if (!singleton.getName().equals("")) {
            binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(singleton.getName());
        } else {
            binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (!singleton.getMoney().equals("")) {
            binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText(StringManager.separate(singleton.getMoney()) + " " + getResources().getString(R.string.MainToman));
        } else {
            binding.contentIncludeLayout.toolbarIncludeLayout.moneyTextView.setText("0" + " " + getResources().getString(R.string.MainToman));
        }

        if (!singleton.getNotification().equals("")) {
            binding.contentIncludeLayout.badgeTextView.setVisibility(View.VISIBLE);
            binding.contentIncludeLayout.badgeTextView.setText(singleton.getNotification());
        } else {
            binding.contentIncludeLayout.badgeTextView.setVisibility(View.GONE);
        }

        if (!singleton.getAvatar().equals("")) {
            binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(singleton.getAvatar()).placeholder(R.color.Blue500).into(binding.contentIncludeLayout.toolbarIncludeLayout.avatarImageView);
        } else {
            binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.contentIncludeLayout.toolbarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.contentIncludeLayout.toolbarIncludeLayout.nameTextView.getText().toString()));
        }
    }

    private void setRecyclerView() {
        ArrayList<Model> values = new ArrayList<>();

        String[] titles = getResources().getStringArray(R.array.MainTitles);
        String[] description = getResources().getStringArray(R.array.MainDescriptions);
        int[] images = new int[]{R.drawable.ic_tachometer_alt_light, R.drawable.ic_building_light, R.drawable.ic_folders_light, R.drawable.ic_user_friends_light, R.drawable.ic_users_light, R.drawable.ic_balance_scale_light, R.drawable.ic_vial_light, R.drawable.ic_users_medical_light, R.drawable.ic_file_certificate_light};

        for (int i = 0; i < titles.length; i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", titles[i]);
                jsonObject.put("description", description[i]);
                jsonObject.put("image", images[i]);

                Model model = new Model(jsonObject);

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            navsAdapter.setItems(values);
            binding.navIncludeLayout.listRecyclerView.getRoot().setAdapter(navsAdapter);
        }
    }

    public void responseAdapter(int position) {
        switch (position) {
            case 0:
                navigator(R.id.dashboardFragment);
                break;
            case 1:
                navigator(R.id.centersFragment);
                break;
            case 2:
                navigator(R.id.casesFragment);
                break;
            case 3:
                navigator(R.id.sessionsFragment);
                break;
            case 4:
                navigator(R.id.usersFragment);
                break;
            case 5:
                navigator(R.id.scalesFragment);
                break;
            case 6:
                navigator(R.id.samplesFragment);
                break;
            case 7:
                navigator(R.id.bulkSamplesFragment);
                break;
            case 8:
                navigator(R.id.documentsFragment);
                break;
        }

        binding.getRoot().closeDrawer(GravityCompat.START);

        navsAdapter.notifyDataSetChanged();
    }

    public void navigator(int destinationId) {
        try {
            if (navController.getBackStackEntry(destinationId).getDestination() != navController.getCurrentDestination()) {
                while (Objects.requireNonNull(navController.getCurrentDestination()).getId()!=destinationId) {
                    navController.popBackStack();
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.dashboardFragment  && destinationId == R.id.meFragment) {
            navController.popBackStack();
        }
        navController.navigate(destinationId);
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
        if (binding.getRoot().isDrawerOpen(GravityCompat.START)) {
            binding.getRoot().closeDrawer(GravityCompat.START);
        } else {
            if (!navController.popBackStack()) {
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                IntentManager.file(this);
            }
        } else if (requestCode == 300) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                IntentManager.gallery(this);
            }
        } else if (requestCode == 400) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                switch (navController.getCurrentDestination().getId()) {
                    case R.id.createCenterFragment:
                        CreateCenterFragment createCenterFragment = (CreateCenterFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                        if (createCenterFragment != null) {
                            createCenterFragment.avatarPath = IntentManager.camera(this);
                        }
                        break;
                    case R.id.editCenterFragment:
                        EditCenterFragment editCenterFragment = (EditCenterFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                        if (editCenterFragment != null) {
                            EditCenterAvatarFragment editCenterAvatarFragment = (EditCenterAvatarFragment) editCenterFragment.adapter.hashMap.get(editCenterFragment.binding.viewPager.getCurrentItem());
                            if (editCenterAvatarFragment != null) {
                                editCenterAvatarFragment.avatarPath = IntentManager.camera(this);
                            }
                        }
                        break;
                    case R.id.editUserFragment:
                        EditUserFragment editUserFragment = (EditUserFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                        if (editUserFragment != null) {
                            EditAvatarFragment editAvatarFragment = (EditAvatarFragment) editUserFragment.adapter.hashMap.get(editUserFragment.binding.viewPager.getCurrentItem());
                            if (editAvatarFragment != null) {
                                editAvatarFragment.avatarPath = IntentManager.camera(this);
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (Objects.requireNonNull(navController.getCurrentDestination()).getId()) {
                case R.id.createCenterFragment:
                    CreateCenterFragment createCenterFragment = (CreateCenterFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCenterFragment != null) {
                        if (requestCode == 300) {
                            ResultManager.galleryResult(this, data, createCenterFragment.avatarPath, createCenterFragment.avatarBitmap, createCenterFragment.binding.avatarIncludeLayout.avatarCircleImageView, null);
                        } else if (requestCode == 400) {
                            ResultManager.cameraResult(this, createCenterFragment.avatarPath, createCenterFragment.avatarBitmap, createCenterFragment.binding.avatarIncludeLayout.avatarCircleImageView, null);
                        }
                    }
                    break;
                case R.id.editCenterFragment:
                    EditCenterFragment editCenterFragment = (EditCenterFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (editCenterFragment != null) {
                        EditCenterAvatarFragment editCenterAvatarFragment = (EditCenterAvatarFragment) editCenterFragment.adapter.hashMap.get(editCenterFragment.binding.viewPager.getCurrentItem());
                        if (editCenterAvatarFragment != null) {
                            if (requestCode == 300) {
                                ResultManager.galleryResult(this, data, editCenterAvatarFragment.avatarPath, editCenterAvatarFragment.avatarBitmap, editCenterAvatarFragment.binding.avatarIncludeLayout.avatarCircleImageView, editCenterAvatarFragment.binding.avatarIncludeLayout.charTextView);
                            } else if (requestCode == 400) {
                                ResultManager.cameraResult(this, editCenterAvatarFragment.avatarPath, editCenterAvatarFragment.avatarBitmap, editCenterAvatarFragment.binding.avatarIncludeLayout.avatarCircleImageView, editCenterAvatarFragment.binding.avatarIncludeLayout.charTextView);
                            }
                        }
                    }
                    break;
                case R.id.editUserFragment:
                    EditUserFragment editUserFragment = (EditUserFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (editUserFragment != null) {
                        EditAvatarFragment editAvatarFragment = (EditAvatarFragment) editUserFragment.adapter.hashMap.get(editUserFragment.binding.viewPager.getCurrentItem());
                        if (editAvatarFragment != null) {
                            if (requestCode == 300) {
                                ResultManager.galleryResult(this, data, editAvatarFragment.avatarPath, editAvatarFragment.avatarBitmap, editAvatarFragment.binding.avatarIncludeLayout.avatarCircleImageView, editAvatarFragment.binding.avatarIncludeLayout.charTextView);
                            } else if (requestCode == 400) {
                                ResultManager.cameraResult(this, editAvatarFragment.avatarPath, editAvatarFragment.avatarBitmap, editAvatarFragment.binding.avatarIncludeLayout.avatarCircleImageView, editAvatarFragment.binding.avatarIncludeLayout.charTextView);
                            }
                        }
                    }
                    break;
                case R.id.createDocumentFragment:
                    CreateDocumentFragment createDocumentFragment = (CreateDocumentFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createDocumentFragment != null) {
                        if (requestCode == 100) {
                            ResultManager.fileResult(this, data, createDocumentFragment.filePath, createDocumentFragment.binding.fileIncludeLayout.nameTextView);
                        }
                    }
                    break;
                case R.id.createPracticeFragment:
                    CreatePracticeFragment createPracticeFragment = (CreatePracticeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createPracticeFragment != null) {
                        if (requestCode == 100) {
                            ResultManager.fileResult(this, data, createPracticeFragment.filePath, createPracticeFragment.binding.fileIncludeLayout.nameTextView);
                        }
                    }
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == 100) {
                Toast.makeText(this, "File Exception", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 300) {
                Toast.makeText(this, "Gallery Exception", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 400) {
                Toast.makeText(this, "Camera Exception", Toast.LENGTH_SHORT).show();
            }
        }
    }

}