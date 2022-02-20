package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.recycler.main.Index.IndexRoomAdapter;
import com.majazeh.risloo.databinding.FragmentCenterBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class CenterFragment extends Fragment {

    // Binding
    private FragmentCenterBinding binding;

    // Adapters
    private IndexRoomAdapter indexRoomAdapter;

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
        indexRoomAdapter = new IndexRoomAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.RoomAdapterHeader));

        InitManager.imgResTintBackground(requireActivity(), binding.roomsAddView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);

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
                case "اعضاء":
                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterUsersFragment(centerModel);
                    break;
                case "برنامه درمانی":
                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(centerModel);
                    break;
                case "پروفایل من":
                    ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(centerModel, ((MainActivity) requireActivity()).singleton.getUserModel());
                    break;
                case "ویرایش":
                    ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(centerModel);
                    break;
                case "محل برگزاری جلسات":
                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterPlatformsFragment(centerModel);
                    break;
                case "اتاق\u200Cهای درمان":
                    ((MainActivity) requireActivity()).navigatoon.navigateToRoomsFragment(centerModel);
                    break;
                case "حسابداری":
                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterAccountingFragment(centerModel);
                    break;
            }
        }).widget(binding.menuSpinner.selectImageView);

        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "اعضاء":
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterUsersFragment(centerModel);
                            break;
                        case "برنامه درمانی":
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(centerModel);
                            break;
                        case "پروفایل من":
                            ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(centerModel, ((MainActivity) requireActivity()).singleton.getUserModel());
                            break;
                        case "ویرایش":
                            ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(centerModel);
                            break;
                        case "محل برگزاری جلسات":
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterPlatformsFragment(centerModel);
                            break;
                        case "اتاق\u200Cهای درمان":
                            ((MainActivity) requireActivity()).navigatoon.navigateToRoomsFragment(centerModel);
                            break;
                        case "حسابداری":
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterAccountingFragment(centerModel);
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

        CustomClickView.onDelayedListener(() -> {
            if (binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.CenterFragmentRequest)))
                doWork();
            else
                ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(centerModel);

        }).widget(binding.actionTextView.getRoot());

        binding.roomsSearchLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.roomsSearchLayout.searchEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.roomsSearchLayout.searchEditText);
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
            ((MainActivity) requireActivity()).navigatoon.navigateToCreateRoomFragment(centerModel, null);
        }).widget(binding.roomsAddView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        try {
            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getDetail() != null && model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals("")) {
                binding.nameTextView.setText(model.getDetail().getString("title"));
            } else if (model.getId() != null && !model.getId().equals("")) {
                binding.nameTextView.setText(model.getId());
            } else {
                binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
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
                binding.mobileTextView.setText(StringManager.phones(requireActivity(), model.getDetail().getJSONArray("phone_numbers")));
                binding.mobileTextView.setMovementMethod(LinkMovementMethod.getInstance());

                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (model.getDetail() != null && model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            setAcceptation(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAcceptation(CenterModel model) {
        if (model.getAcceptation() != null) {
            if (!model.getAcceptation().getKickedAt().equals("")) {
                setStatus("kicked");
            } else {
                if (model.getAcceptation().getAcceptedAt() != 0)
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
            InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.CenterFragmentRequest), getResources().getColor(R.color.white), R.drawable.draw_16sdp_solid_emerald600_ripple_emerald800);

            binding.statusTextView.setVisibility(View.GONE);
            binding.statusTextView.setText("");
        } else {
            InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.CenterFragmentSchedules), getResources().getColor(R.color.risloo500), R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

            binding.statusTextView.setVisibility(View.VISIBLE);
            binding.statusTextView.setText(SelectionManager.getCenterStatus(requireActivity(), "fa", status));

            switch (status) {
                case "accepted":
                    binding.statusTextView.setTextColor(getResources().getColor(R.color.emerald500));
                    break;
                case "kicked":
                    binding.statusTextView.setTextColor(getResources().getColor(R.color.red500));
                    break;
                default:
                    binding.statusTextView.setTextColor(getResources().getColor(R.color.coolGray500));
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

        if (binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.CenterFragmentRequest)))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentSchedules));

        if (!binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.CenterFragmentRequest)))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentProfile));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownEdit(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentEdit));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownPlatforms(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentSessionPlatforms));

        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownRooms(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentRooms));

//        if (((MainActivity) requireActivity()).permissoon.showCenterDropdownAccounting(((MainActivity) requireActivity()).singleton.getUserModel(), status))
//            items.add(requireActivity().getResources().getString(R.string.CenterFragmentAccounting));

        items.add("");

        if (items.size() > 2) {
            InitManager.spinnerOvalEnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
            InitManager.selectCustomActionSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "اعضاء":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_users_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "برنامه درمانی":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_calendar_alt_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "پروفایل من":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_user_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "ویرایش":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "محل برگزاری جلسات":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_map_marker_alt_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "اتاق\u200Cهای درمان":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_loveseat_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "حسابداری":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_calculator_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
            }
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
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++)
                                items.add(new RoomModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));

//                            if (Objects.equals(data.get("page"), 1))
//                                roomsAdapter.clearItems();

                            if (!items.data().isEmpty()) {
                                indexRoomAdapter.setItems(items.data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(indexRoomAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexRoomAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.recyclerView.setAdapter(null);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                if (binding.roomsSearchLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomAdapterEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                            hideShimmer();
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
                        hideShimmer();
                    });

                    isLoading = false;
                }
            }
        });
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        Center.request(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                centerModel = (CenterModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackSuccesAcceptation));

                        setAcceptation(centerModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!responseObject.isNull("errors")) {
                                JSONObject errorsObject = responseObject.getJSONObject("errors");

                                Iterator<String> keys = (errorsObject.keys());
                                StringBuilder allErrors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();

                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String error = errorsObject.getJSONArray(key).getString(i);

                                        allErrors.append(error);
                                        allErrors.append("\n");
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void hideShimmer() {
        binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.roomsShimmerLayout.getRoot().stopShimmer();

        if (binding.roomsSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.roomsSingleLayout.progressBar.setVisibility(View.GONE);
        if (binding.roomsSearchLayout.searchProgressBar.getVisibility() == View.VISIBLE)
            binding.roomsSearchLayout.searchProgressBar.setVisibility(View.GONE);
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