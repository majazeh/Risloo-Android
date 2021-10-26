package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CenterFragment extends Fragment {

    // Binding
    private FragmentCenterBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;

    // Models
    private CenterModel centerModel;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private boolean isLoading = true, userSelect = false, userScroll = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));

        InitManager.imgResTintBackground(requireActivity(), binding.roomsAddView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE) {
                try {
                    IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), centerModel.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onClickListener(() -> {
            switch (binding.menuSpinner.selectImageView.getTag().toString()) {
                case "اعضاء": {
                    NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(centerModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
                case "برنامه درمانی": {
                    NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
                case "پروفایل من": {
                    NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(centerModel, null);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
                case "ویرایش": {
                    NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(centerModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
                case "محل برگزاری": {
                    NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(centerModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
            }
        }).widget(binding.menuSpinner.selectImageView);

        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "اعضاء": {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "برنامه درمانی": {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "پروفایل من": {
                            NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(centerModel, null);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "ویرایش": {
                            NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "محل برگزاری": {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
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

        CustomClickView.onDelayedListener(() -> {
            if (binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.CenterFragmentRequest))) {
                DialogManager.showLoadingDialog(requireActivity(), "");

                Center.request(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        centerModel = (CenterModel) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                setAcceptation(centerModel);

                                DialogManager.dismissLoadingDialog();
                                SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackSuccesAcceptation));
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                // Place Code if Needed
                            });
                        }
                    }
                });
            } else {
                NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                ((MainActivity) requireActivity()).navController.navigate(action);
            }
        }).widget(binding.actionTextView.getRoot());

        binding.roomsSearchLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.roomsSearchLayout.searchEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.roomsSearchLayout.searchEditText);
            return false;
        });

        binding.roomsSearchLayout.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));

                    if (binding.roomsSearchLayout.searchProgressBar.getVisibility() == View.GONE)
                        binding.roomsSearchLayout.searchProgressBar.setVisibility(View.VISIBLE);

                    getData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.getRoot().setOnTouchListener((v, event) -> {
            userScroll = true;
            return false;
        });

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (userScroll && !isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
//                userScroll = false;
//                isLoading = true;
//
//                if (data.containsKey("page"))
//                    data.put("page", ((int) data.get("page")) + 1);
//                else
//                    data.put("page", 1);
//
//                if (binding.roomsSingleLayout.progressBar.getVisibility() == View.GONE)
//                    binding.roomsSingleLayout.progressBar.setVisibility(View.VISIBLE);
//
//                getData();
            }
        });

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateRoomFragment(centerModel, null);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.roomsAddView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        try {
            if (model.getCenterId() != null && !model.getCenterId().equals("")) {
                data.put("id", model.getCenterId());
            }

            if (model.getDetail() != null && model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals("")) {
                binding.nameTextView.setText(model.getDetail().getString("title"));
            } else {
                binding.nameTextView.setText(model.getCenterId());
            }

            if (model.getManager() != null && model.getManager().getName() != null && !model.getManager().getName().equals("")) {
                binding.ownerTextView.setText(model.getManager().getName());
                binding.ownerGroup.setVisibility(View.VISIBLE);
            } else {
                binding.ownerGroup.setVisibility(View.GONE);
            }

            if (model.getDetail() != null && model.getDetail().has("description") && !model.getDetail().isNull("description") && !model.getDetail().getString("description").equals("")) {
                binding.descriptionTextView.setText(model.getDetail().getString("description"));
                binding.descriptionGroup.setVisibility(View.VISIBLE);
            } else {
                binding.descriptionGroup.setVisibility(View.GONE);
            }

            if (model.getDetail() != null && model.getDetail().has("phone_numbers") && !model.getDetail().isNull("phone_numbers") && model.getDetail().getJSONArray("phone_numbers").length() != 0) {
                JSONArray phones = model.getDetail().getJSONArray("phone_numbers");

                binding.mobileTextView.setText("");
                for (int i = 0; i < phones.length(); i++) {
                    binding.mobileTextView.append(phones.get(i).toString());
                    if (i != phones.length() - 1) {
                        binding.mobileTextView.append("  -  ");
                    }
                }

                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (model.getDetail() != null && model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.CoolGray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray50).placeholder(R.color.CoolGray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            setAcceptation(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAcceptation(CenterModel model) {
        if (model.getAcceptation() != null) {
            if (!model.getAcceptation().getKicked_at().equals("")) {
                setStatus("kicked");
            } else {
                if (model.getAcceptation().getAccepted_at() != 0)
                    switch (model.getAcceptation().getPosition()) {
                        case "manager":
                        case "operator":
                        case "psychologist":
                        case "client":
                            setStatus(model.getAcceptation().getPosition());
                            break;
                        default:
                            setStatus("accepted");
                            break;
                    }
                else
                    setStatus("awaiting");
            }
        } else {
            setStatus("request");
        }
    }

    private void setStatus(String status) {
        if (status.equals("request")) {
            InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.CenterFragmentRequest), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_emerald600_ripple_emerald800);

            binding.statusTextView.setVisibility(View.GONE);
            binding.statusTextView.setText("");
        } else {
            InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.CenterFragmentSchedules), getResources().getColor(R.color.LightBlue600), R.drawable.draw_16sdp_solid_white_border_1sdp_lightblue600_ripple_lightblue300);

            binding.statusTextView.setVisibility(View.VISIBLE);
            binding.statusTextView.setText(SelectionManager.getCenterStatus(requireActivity(), "fa", status));

            switch (status) {
                case "accepted":
                    binding.statusTextView.setTextColor(getResources().getColor(R.color.Emerald600));
                    break;
                case "kicked":
                    binding.statusTextView.setTextColor(getResources().getColor(R.color.Red600));
                    break;
                default:
                    binding.statusTextView.setTextColor(getResources().getColor(R.color.CoolGray600));
                    break;
            }
        }

        setDropdown(status);
        setPermission(status);
    }

    private void setDropdown(String status) {
        ArrayList<String> items = new ArrayList<>();

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownUsers(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentUsers));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownSchedules(status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentSchedules));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownProfile(status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentProfile));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownEdit(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentEdit));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownPlatforms(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentSessionPlatforms));

        items.add("");

        if (items.size() > 2) {
            InitManager.imgResTintBackground(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.CoolGray500, R.drawable.draw_oval_solid_transparent_border_1sdp_coolgray300);
            InitManager.selectCustomActionSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "اعضاء":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_users_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "برنامه درمانی":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_calendar_alt_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "پروفایل من":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_user_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "ویرایش":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "محل برگزاری":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_map_marker_alt_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
            }

            binding.menuSpinner.selectImageView.setPadding((int) getResources().getDimension(R.dimen._8sdp), (int) getResources().getDimension(R.dimen._8sdp), (int) getResources().getDimension(R.dimen._8sdp), (int) getResources().getDimension(R.dimen._8sdp));
            binding.menuSpinner.selectSpinner.setVisibility(View.GONE);
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
        }
    }

    private void setPermission(String status) {
        if (((MainActivity) requireActivity()).permissoon.showCenterCreateRoom(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            binding.roomsAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.roomsAddView.getRoot().setVisibility(View.GONE);
    }

    private void getData() {
        Center.showDashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            centerModel = new CenterModel(((JSONObject) object).getJSONObject("center"));
                            setData(centerModel);

                            List items = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++) {
                                items.add(new RoomModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));
                            }

                            if (Objects.equals(data.get("page"), 1))
                                roomsAdapter.clearItems();

                            if (!items.data().isEmpty()) {
                                roomsAdapter.setItems(items.data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (roomsAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                if (binding.roomsSearchLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomsAdapterEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));

                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            if (binding.roomsSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.roomsSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.roomsSearchLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.roomsSearchLayout.searchProgressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().stopShimmer();

                        if (binding.roomsSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.roomsSingleLayout.progressBar.setVisibility(View.GONE);
                        if (binding.roomsSearchLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                            binding.roomsSearchLayout.searchProgressBar.setVisibility(View.GONE);
                    });

                    isLoading = false;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
        userSelect = false;
        userScroll = false;
        handler.removeCallbacksAndMessages(null);
    }

}