package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Filter.FilterTagAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexCaseAdapter;
import com.majazeh.risloo.databinding.FragmentRoomBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class RoomFragment extends Fragment {

    // Binding
    private FragmentRoomBinding binding;

    // Adapters
    private IndexCaseAdapter indexCaseAdapter;
    private FilterTagAdapter filterTagAdapter;

    // Models
    private RoomModel roomModel;
    private CenterModel centerModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String type = "room";
    private boolean isLoading = true, userSelect = false, userScroll = false, succesRequest = false, isFiltered = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexCaseAdapter = new IndexCaseAdapter(requireActivity());
        filterTagAdapter = new FilterTagAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.casesHeaderLayout.titleTextView.setText(getResources().getString(R.string.CaseAdapterHeader));

        InitManager.imgResTintBackground(requireActivity(), binding.casesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_emerald600_ripple_white);

        InitManager.fixedHorizontalRecyclerView(requireActivity(), binding.tagsRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.casesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE) {
                if (!type.equals("room") && !succesRequest && centerModel != null) {
                    try {
                        IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), centerModel.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), roomModel.getRoomManager().getAvatar() .getMedium().getUrl());
                }
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onClickListener(() -> {
            switch (binding.menuSpinner.selectImageView.getTag().toString()) {
                case "اعضاء":
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterUsersFragment(centerModel);
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterUsersFragment(roomModel.getRoomCenter());

                    } else {
                        ((MainActivity) requireActivity()).navigatoon.navigateToRoomUsersFragment(roomModel);
                    } break;
                case "برنامه درمانی":
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(centerModel);
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(roomModel.getRoomCenter());

                    } else {
                        ((MainActivity) requireActivity()).navigatoon.navigateToRoomSchedulesFragment(roomModel);
                    } break;
                case "تعریف برنامه درمانی":
                    ((MainActivity) requireActivity()).navigatoon.navigateToCreateScheduleFragment(roomModel);
                    break;
                case "پروفایل من":
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(centerModel, ((MainActivity) requireActivity()).singleton.getUserModel());
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(roomModel.getRoomCenter(), ((MainActivity) requireActivity()).singleton.getUserModel());

                    } else {
                        ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(roomModel, ((MainActivity) requireActivity()).singleton.getUserModel());
                    } break;
                case "ویرایش":
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(centerModel);
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(roomModel.getRoomCenter());

                    } else {
                        ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(roomModel);
                    } break;
                case "محل برگزاری جلسات":
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterPlatformsFragment(centerModel);
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterPlatformsFragment(roomModel.getRoomCenter());

                    } else {
                        ((MainActivity) requireActivity()).navigatoon.navigateToRoomPlatformsFragment(roomModel);
                    } break;
                case "برچسب\u200Cهای مهم":
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterTagsFragment(centerModel);
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterTagsFragment(roomModel.getRoomCenter());

                    } else {
                        ((MainActivity) requireActivity()).navigatoon.navigateToRoomTagsFragment(roomModel);
                    } break;
                case "حسابداری": {
                    if (!type.equals("room")) {
                        if (centerModel != null)
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterAccountingFragment(centerModel);
                        else
                            ((MainActivity) requireActivity()).navigatoon.navigateToCenterAccountingFragment(roomModel.getRoomCenter());

                    }
                } break;
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
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterUsersFragment(centerModel);
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterUsersFragment(roomModel.getRoomCenter());

                            } else {
                                ((MainActivity) requireActivity()).navigatoon.navigateToRoomUsersFragment(roomModel);
                            } break;
                        case "برنامه درمانی":
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(centerModel);
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(roomModel.getRoomCenter());

                            } else {
                                ((MainActivity) requireActivity()).navigatoon.navigateToRoomSchedulesFragment(roomModel);
                            } break;
                        case "تعریف برنامه درمانی":
                            ((MainActivity) requireActivity()).navigatoon.navigateToCreateScheduleFragment(roomModel);
                            break;
                        case "پروفایل من":
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(centerModel, ((MainActivity) requireActivity()).singleton.getUserModel());
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(roomModel.getRoomCenter(), ((MainActivity) requireActivity()).singleton.getUserModel());

                            } else {
                                ((MainActivity) requireActivity()).navigatoon.navigateToReferenceFragment(roomModel, ((MainActivity) requireActivity()).singleton.getUserModel());
                            } break;
                        case "ویرایش":
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(centerModel);
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(roomModel.getRoomCenter());

                            } else {
                                ((MainActivity) requireActivity()).navigatoon.navigateToEditCenterFragment(roomModel);
                            } break;
                        case "محل برگزاری جلسات":
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterPlatformsFragment(centerModel);
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterPlatformsFragment(roomModel.getRoomCenter());

                            } else {
                                ((MainActivity) requireActivity()).navigatoon.navigateToRoomPlatformsFragment(roomModel);
                            } break;
                        case "برچسب\u200Cهای مهم":
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterTagsFragment(centerModel);
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterTagsFragment(roomModel.getRoomCenter());

                            } else {
                                ((MainActivity) requireActivity()).navigatoon.navigateToRoomTagsFragment(roomModel);
                            } break;
                        case "حسابداری": {
                            if (!type.equals("room")) {
                                if (centerModel != null)
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterAccountingFragment(centerModel);
                                else
                                    ((MainActivity) requireActivity()).navigatoon.navigateToCenterAccountingFragment(roomModel.getRoomCenter());

                            }
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
            if (binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.RoomFragmentRequest))) {
                doWork();
            } else {
                if (!type.equals("room")) {
                    if (centerModel != null)
                        ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(centerModel);
                    else
                        ((MainActivity) requireActivity()).navigatoon.navigateToCenterSchedulesFragment(roomModel.getRoomCenter());

                } else {
                    ((MainActivity) requireActivity()).navigatoon.navigateToRoomSchedulesFragment(roomModel);
                }
            }
        }).widget(binding.actionTextView.getRoot());

        binding.getRoot().setOnTouchListener((v, event) -> {
            userScroll = true;
            return false;
        });

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (userScroll && !isLoading && !isFiltered && !Objects.requireNonNull(v).canScrollVertically(1)) {
                userScroll = false;
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.casesSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.casesSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onClickListener(() -> {
            if (roomModel != null)
                ((MainActivity) requireActivity()).navigatoon.navigateToCreateCaseFragment(roomModel);

        }).widget(binding.casesAddView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = RoomFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel")) {
            centerModel = (CenterModel) typeModel;
            setData(centerModel);
        } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel")) {
            roomModel = (RoomModel) typeModel;
            setData(roomModel);
        }
    }

    private void setData(CenterModel model) {
        try {
            if (model.getCenterId() != null && !model.getCenterId().equals("")) {
                data.put("id", model.getCenterId());
                data.put("session_platforms", 1);
            }

            if (model.getCenterType() != null && !model.getCenterType().equals("")) {
                type = model.getCenterType();
            }

            if (model.getManager() != null && model.getManager().getName() != null && !model.getManager().getName().equals("")) {
                binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + model.getManager().getName());
            } else if (model.getManager() != null && model.getManager().getId() != null && !model.getManager().getId().equals("")) {
                binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + model.getManager().getId());
            } else {
                binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
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
                Picasso.get().load(model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            setAcceptation(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData(RoomModel model) {
        try {
            if (model.getRoomId() != null && !model.getRoomId().equals("")) {
                data.put("id", model.getRoomId());
                data.put("session_platforms", 1);
            }

            if (model.getRoomType() != null && !model.getRoomType().equals("")) {
                type = model.getRoomType();
            }

            if (!type.equals("room")) {
                if (model.getRoomManager() != null && model.getRoomManager().getName() != null && !model.getRoomManager().getName().equals(""))
                    binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + model.getRoomManager().getName());
                else if (model.getRoomManager() != null && model.getRoomManager().getId() != null && !model.getRoomManager().getId().equals(""))
                    binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + model.getRoomManager().getId());
                else
                    binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
            } else {
                if (model.getRoomManager() != null && model.getRoomManager().getName() != null && !model.getRoomManager().getName().equals(""))
                    binding.nameTextView.setText(model.getRoomManager().getName());
                else if (model.getRoomManager() != null && model.getRoomManager().getId() != null && !model.getRoomManager().getId().equals(""))
                    binding.nameTextView.setText(model.getRoomManager().getId());
                else
                    binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
            }

            if (!type.equals("room") && model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("description") && !model.getRoomCenter().getDetail().isNull("description") && !model.getRoomCenter().getDetail().getString("description").equals("")) {
                binding.descriptionTextView.setText(model.getRoomCenter().getDetail().getString("description"));
                binding.descriptionGroup.setVisibility(View.VISIBLE);
            } else {
                binding.descriptionGroup.setVisibility(View.GONE);
            }

            if (!type.equals("room") && model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("phone_numbers") && !model.getRoomCenter().getDetail().isNull("phone_numbers") && model.getRoomCenter().getDetail().getJSONArray("phone_numbers").length() != 0) {
                binding.mobileTextView.setText(StringManager.phones(requireActivity(), model.getRoomCenter().getDetail().getJSONArray("phone_numbers")));
                binding.mobileTextView.setMovementMethod(LinkMovementMethod.getInstance());

                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (model.getRoomManager() != null && model.getRoomManager().getAvatar() != null && model.getRoomManager().getAvatar().getMedium() != null && model.getRoomManager().getAvatar() .getMedium().getUrl() != null && !model.getRoomManager().getAvatar().getMedium().getUrl().equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getRoomManager().getAvatar() .getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
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

    private void setAcceptation(RoomModel model) {
        if (model.getRoomAcceptation() != null) {
            if (!model.getRoomAcceptation().getKicked_at().equals("")) {
                setStatus("kicked");
            } else {
                if (model.getRoomAcceptation().getAccepted_at() != 0)
                    switch (model.getRoomAcceptation().getPosition()) {
                        case "manager":
                        case "operator":
                        case "psychologist":
                        case "client":
                            setStatus(model.getRoomAcceptation().getPosition());
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
        if (!type.equals("room")) {
            if (status.equals("request")) {
                InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.RoomFragmentRequest), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_emerald600_ripple_emerald800);

                binding.statusTextView.setVisibility(View.GONE);
                binding.statusTextView.setText("");
            } else {
                InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.RoomFragmentSchedules), getResources().getColor(R.color.Risloo500), R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

                binding.statusTextView.setVisibility(View.VISIBLE);
                binding.statusTextView.setText(SelectionManager.getCenterStatus(requireActivity(), "fa", status));

                switch (status) {
                    case "accepted":
                        binding.statusTextView.setTextColor(getResources().getColor(R.color.Emerald500));
                        break;
                    case "kicked":
                        binding.statusTextView.setTextColor(getResources().getColor(R.color.Red500));
                        break;
                    default:
                        binding.statusTextView.setTextColor(getResources().getColor(R.color.CoolGray500));
                        break;
                }
            }

        } else {
            InitManager.txtTextColorBackground(binding.actionTextView.getRoot(), getResources().getString(R.string.RoomFragmentSchedules), getResources().getColor(R.color.Risloo500), R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

            binding.statusTextView.setVisibility(View.GONE);
            binding.statusTextView.setText("");
        }

        setDropdown(status);
        setPermission(status);
    }

    private void setDropdown(String status) {
        ArrayList<String> items = new ArrayList<>();

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownUsers(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentUsers));

        if (binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.RoomFragmentRequest)))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentSchedules));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownCreateSchedule(((MainActivity) requireActivity()).singleton.getUserModel(), status, type))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentCreateSchedule));

        if (!binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.RoomFragmentRequest)) && !type.equals("room"))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentProfile));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownEdit(((MainActivity) requireActivity()).singleton.getUserModel(), status, type))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentEdit));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownPlatforms(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentSessionPlatforms));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownTags(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentTags));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownAccounting(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentAccounting));

        items.add("");

        if (items.size() > 2) {
            InitManager.spinnerOvalEnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
            InitManager.selectCustomActionSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "اعضاء":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_users_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "برنامه درمانی":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_calendar_alt_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "تعریف برنامه درمانی":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_calendar_plus_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "پروفایل من":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_user_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "ویرایش":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "محل برگزاری جلسات":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_map_marker_alt_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "برچسب\u200Cهای مهم":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_tags_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "حسابداری":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_calculator_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
            }
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
        }
    }

    private void setPermission(String status) {
        if (((MainActivity) requireActivity()).permissoon.showRoomCreateCase(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            binding.casesAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.casesAddView.getRoot().setVisibility(View.GONE);

        if (((MainActivity) requireActivity()).permissoon.showRoomCases(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            binding.casesGroup.setVisibility(View.VISIBLE);
        else
            binding.casesGroup.setVisibility(View.GONE);
    }

    private void getData() {
        Room.showDashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            roomModel = new RoomModel(((JSONObject) object).getJSONObject("room"));
                            centerModel = roomModel.getRoomCenter();
                            setData(roomModel);

                            if (binding.casesGroup.getVisibility() == View.VISIBLE) {

                                List items = new List();
                                for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++)
                                    items.add(new CaseModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));

                                if (Objects.equals(data.get("page"), 1))
                                    indexCaseAdapter.clearItems();

                                if (!items.data().isEmpty()) {
                                    indexCaseAdapter.setItems(items.data());
                                    binding.casesSingleLayout.recyclerView.setAdapter(indexCaseAdapter);

                                    binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                                } else if (indexCaseAdapter.getItemCount() == 0) {
                                    binding.casesSingleLayout.recyclerView.setAdapter(null);

                                    binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                    binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.CaseAdapterEmpty));
                                }

                                binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                                // Tags Data
                                if (!isFiltered) {
                                    if (!roomModel.getPinned_tags().data().isEmpty()) {
                                        filterTagAdapter.setItems(roomModel.getPinned_tags().data());
                                        binding.tagsRecyclerView.setAdapter(filterTagAdapter);

                                        binding.casesFilterView.setVisibility(View.VISIBLE);
                                    } else if (filterTagAdapter.getItemCount() == 0) {
                                        binding.tagsRecyclerView.setAdapter(null);

                                        binding.casesFilterView.setVisibility(View.GONE);
                                    }
                                }

                                hideShimmer();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

                    isLoading = false;
                    succesRequest = true;
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

        Room.request(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                roomModel = (RoomModel) object;
                centerModel = roomModel.getRoomCenter();

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackSuccesAcceptation));

                        setAcceptation(roomModel);
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

    public void responseAdapter() {
        data.put("page", 1);

        if (!filterTagAdapter.getIds().isEmpty()) {
            isFiltered = true;
            data.put("tag", filterTagAdapter.getIds());
        } else {
            isFiltered = false;
            data.remove("tag");
        }

        // Cases Data
        binding.casesSingleLayout.getRoot().setVisibility(View.GONE);
        binding.casesShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.casesShimmerLayout.getRoot().startShimmer();

        getData();
    }

    private void hideShimmer() {
        binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.casesShimmerLayout.getRoot().stopShimmer();

        if (binding.casesSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.casesSingleLayout.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
        userSelect = false;
        userScroll = false;
        succesRequest = false;
        isFiltered = false;
    }

}