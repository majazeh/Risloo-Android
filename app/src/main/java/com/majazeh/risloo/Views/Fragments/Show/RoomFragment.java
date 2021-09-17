package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
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
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.FilterTagsAdapter;
import com.majazeh.risloo.databinding.FragmentRoomBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CaseModel;

import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RoomFragment extends Fragment {

    // Binding
    private FragmentRoomBinding binding;

    // Adapters
    private Cases2Adapter cases2Adapter;
    private FilterTagsAdapter filterTagsAdapter;

    // Models
    private RoomModel roomModel;
    private CenterModel centerModel;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private String type = "room";
    private boolean isLoading = true, userSelect = false, userScroll = false, succesRequest = false, isFiltered = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        cases2Adapter = new Cases2Adapter(requireActivity());
        filterTagsAdapter = new FilterTagsAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.fixedHorizontalRecyclerView(requireActivity(), binding.tagsRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.casesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.menuSpinner.selectImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
        } else {
            binding.menuSpinner.selectImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE) {
                if (!type.equals("room") && !succesRequest) {
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
                        if (centerModel != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(roomModel.getRoomCenter());
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        }
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalRoomUsersFragment(roomModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    } break;
                case "برنامه درمانی":
                    if (!type.equals("room")) {
                        if (centerModel != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(roomModel.getRoomCenter());
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        }
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalRoomSchedulesFragment(roomModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    } break;
                case "تعریف برنامه درمانی": {
                    NavDirections action = NavigationMainDirections.actionGlobalCreateScheduleFragment(roomModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
                case "پروفایل من":
                    if (!type.equals("room")) {
                        if (centerModel != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(centerModel, null);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(roomModel.getRoomCenter(), null);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        }
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(roomModel, null);
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    } break;
                case "ویرایش":
                    if (!type.equals("room")) {
                        if (centerModel != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(roomModel.getRoomCenter());
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        }
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(roomModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    } break;
                case "محل برگزاری":
                    if (!type.equals("room")) {
                        if (centerModel != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(roomModel.getRoomCenter());
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        }
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalRoomPlatformsFragment(roomModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    } break;
                case "برچسب\u200Cهای مهم":
                    if (!type.equals("room")) {
                        if (centerModel != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterTagsFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterTagsFragment(roomModel.getRoomCenter());
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        }
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalRoomTagsFragment(roomModel);
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
                        case "اعضاء":
                            if (!type.equals("room")) {
                                if (centerModel != null) {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(centerModel);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                } else {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(roomModel.getRoomCenter());
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                }
                            } else {
                                NavDirections action = NavigationMainDirections.actionGlobalRoomUsersFragment(roomModel);
                                ((MainActivity) requireActivity()).navController.navigate(action);
                            } break;
                        case "برنامه درمانی":
                            if (!type.equals("room")) {
                                if (centerModel != null) {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                } else {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(roomModel.getRoomCenter());
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                }
                            } else {
                                NavDirections action = NavigationMainDirections.actionGlobalRoomSchedulesFragment(roomModel);
                                ((MainActivity) requireActivity()).navController.navigate(action);
                            } break;
                        case "تعریف برنامه درمانی": {
                            NavDirections action = NavigationMainDirections.actionGlobalCreateScheduleFragment(roomModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "پروفایل من":
                            if (!type.equals("room")) {
                                if (centerModel != null) {
                                    NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(centerModel, null);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                } else {
                                    NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(roomModel.getRoomCenter(), null);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                }
                            } else {
                                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(roomModel, null);
                                ((MainActivity) requireActivity()).navController.navigate(action);
                            } break;
                        case "ویرایش":
                            if (!type.equals("room")) {
                                if (centerModel != null) {
                                    NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(centerModel);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                } else {
                                    NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(roomModel.getRoomCenter());
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                }
                            } else {
                                NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(roomModel);
                                ((MainActivity) requireActivity()).navController.navigate(action);
                            } break;
                        case "محل برگزاری":
                            if (!type.equals("room")) {
                                if (centerModel != null) {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(centerModel);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                } else {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(roomModel.getRoomCenter());
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                }
                            } else {
                                NavDirections action = NavigationMainDirections.actionGlobalRoomPlatformsFragment(roomModel);
                                ((MainActivity) requireActivity()).navController.navigate(action);
                            } break;
                        case "برچسب\u200Cهای مهم":
                            if (!type.equals("room")) {
                                if (centerModel != null) {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterTagsFragment(centerModel);
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                } else {
                                    NavDirections action = NavigationMainDirections.actionGlobalCenterTagsFragment(roomModel.getRoomCenter());
                                    ((MainActivity) requireActivity()).navController.navigate(action);
                                }
                            } else {
                                NavDirections action = NavigationMainDirections.actionGlobalRoomTagsFragment(roomModel);
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
            if (binding.actionTextView.getRoot().getText().equals(getResources().getString(R.string.RoomFragmentRequest))) {
                DialogManager.showLoadingDialog(requireActivity(), "");

                Room.request(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        roomModel = (RoomModel) object;
                        centerModel = roomModel.getRoomCenter();

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                setAcceptation(roomModel);

                                DialogManager.dismissLoadingDialog();
                                SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastRequestSucces));
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
                if (!type.equals("room")) {
                    if (centerModel != null) {
                        NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    } else {
                        NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(roomModel.getRoomCenter());
                        ((MainActivity) requireActivity()).navController.navigate(action);
                    }
                } else {
                    NavDirections action = NavigationMainDirections.actionGlobalRoomSchedulesFragment(roomModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                }
            }
        }).widget(binding.actionTextView.getRoot());

        binding.searchIncludeLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.searchIncludeLayout.searchEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.searchIncludeLayout.searchEditText);
            return false;
        });

        binding.searchIncludeLayout.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));

                    if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.GONE)
                        binding.searchIncludeLayout.searchProgressBar.setVisibility(View.VISIBLE);

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
            if (roomModel != null) {
                NavDirections action = NavigationMainDirections.actionGlobalCreateCaseFragment(roomModel);
                ((MainActivity) requireActivity()).navController.navigate(action);
            }
        }).widget(binding.addImageView.getRoot());
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
            } else if (model.getManager() != null) {
                binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + model.getManager().getId());
            } else {
                binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + "نامعلوم");
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
                Picasso.get().load(model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
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
                else if (model.getRoomManager() != null)
                    binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + model.getRoomManager().getId());
                else
                    binding.nameTextView.setText(requireActivity().getResources().getString(R.string.RoomFragmentPersonalClinic) + " " + "نامعلوم");

            } else {
                if (model.getRoomManager() != null && model.getRoomManager().getName() != null && !model.getRoomManager().getName().equals(""))
                    binding.nameTextView.setText(model.getRoomManager().getName());
                else if (model.getRoomManager() != null)
                    binding.nameTextView.setText(model.getRoomManager().getId());
                else
                    binding.nameTextView.setText("نامعلوم");
            }

            if (!type.equals("room") && model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("description") && !model.getRoomCenter().getDetail().isNull("description") && !model.getRoomCenter().getDetail().getString("description").equals("")) {
                binding.descriptionTextView.setText(model.getRoomCenter().getDetail().getString("description"));
                binding.descriptionGroup.setVisibility(View.VISIBLE);
            } else {
                binding.descriptionGroup.setVisibility(View.GONE);
            }

            if (!type.equals("room") && model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("phone_numbers") && !model.getRoomCenter().getDetail().isNull("phone_numbers") && model.getRoomCenter().getDetail().getJSONArray("phone_numbers").length() != 0) {
                JSONArray phones = model.getRoomCenter().getDetail().getJSONArray("phone_numbers");

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

            if (model.getRoomManager() != null && model.getRoomManager().getAvatar() != null && model.getRoomManager().getAvatar().getMedium() != null && model.getRoomManager().getAvatar() .getMedium().getUrl() != null && !model.getRoomManager().getAvatar().getMedium().getUrl().equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getRoomManager().getAvatar() .getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
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
                InitManager.txtTextColor(binding.actionTextView.getRoot(), getResources().getString(R.string.RoomFragmentRequest), getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.actionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
                else
                    binding.actionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600);

                binding.statusTextView.setVisibility(View.GONE);
                binding.statusTextView.setText("");
            } else {
                InitManager.txtTextColor(binding.actionTextView.getRoot(), getResources().getString(R.string.RoomFragmentSchedules), getResources().getColor(R.color.Blue600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.actionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
                else
                    binding.actionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);

                binding.statusTextView.setVisibility(View.VISIBLE);
                binding.statusTextView.setText(SelectionManager.getCenterStatus(requireActivity(), "fa", status));

                switch (status) {
                    case "accepted":
                        binding.statusTextView.setTextColor(getResources().getColor(R.color.Green600));
                        break;
                    case "kicked":
                        binding.statusTextView.setTextColor(getResources().getColor(R.color.Red600));
                        break;
                    default:
                        binding.statusTextView.setTextColor(getResources().getColor(R.color.Gray600));
                        break;
                }
            }

        } else {
            InitManager.txtTextColor(binding.actionTextView.getRoot(), getResources().getString(R.string.RoomFragmentSchedules), getResources().getColor(R.color.Blue600));

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                binding.actionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
            else
                binding.actionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);

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

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownSchedules(status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentSchedules));

        if (type.equals("room") && ((MainActivity) requireActivity()).permissoon.showRoomDropdownCreateSchedule(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentAddSchedule));

        if (!type.equals("room") && ((MainActivity) requireActivity()).permissoon.showRoomDropdownProfile(status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentProfile));

        if (!type.equals("room"))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentEdit));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownPlatforms(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentPlatforms));

        if (((MainActivity) requireActivity()).permissoon.showRoomDropdownTags(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            items.add(requireActivity().getResources().getString(R.string.RoomFragmentTags));

        items.add("");

        if (items.size() > 2) {
            InitManager.imgResTint(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.Gray500);
            InitManager.actionCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "اعضاء":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_users_light, R.color.Gray500, items.get(0));
                    break;
                case "برنامه درمانی":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_calendar_alt_light, R.color.Gray500, items.get(0));
                    break;
                case "تعریف برنامه درمانی":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_calendar_plus_light, R.color.Gray500, items.get(0));
                    break;
                case "پروفایل من":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_user_crown_light, R.color.Gray500, items.get(0));
                    break;
                case "ویرایش":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.Gray500, items.get(0));
                    break;
                case "محل برگزاری":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_map_marker_alt_light, R.color.Gray500, items.get(0));
                    break;
                case "برچسب\u200Cهای مهم":
                    InitManager.imgResTintTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_tags_light, R.color.Gray500, items.get(0));
                    break;
            }

            binding.menuSpinner.selectImageView.setPadding((int) getResources().getDimension(R.dimen._9sdp), (int) getResources().getDimension(R.dimen._9sdp), (int) getResources().getDimension(R.dimen._9sdp), (int) getResources().getDimension(R.dimen._9sdp));
            binding.menuSpinner.selectSpinner.setVisibility(View.GONE);
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
        }
    }

    private void setPermission(String status) {
        if (((MainActivity) requireActivity()).permissoon.showRoomCreateCase(((MainActivity) requireActivity()).singleton.getUserModel(), status))
            binding.addImageView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.addImageView.getRoot().setVisibility(View.GONE);
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

                            List items = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++) {
                                items.add(new CaseModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));
                            }

                            if (Objects.equals(data.get("page"), 1))
                                cases2Adapter.clearItems();

                            if (!items.data().isEmpty()) {
                                cases2Adapter.setItems(items.data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);

                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (cases2Adapter.getItemCount() == 0) {
                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE || isFiltered)
                                    binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.Cases2AdapterEmpty));
                            }

                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(cases2Adapter.getItemCount()));

                            // Tags Data
                            if (!isFiltered) {
                                if (!roomModel.getPinned_tags().data().isEmpty()) {
                                    filterTagsAdapter.setItems(roomModel.getPinned_tags().data());
                                    binding.tagsRecyclerView.setAdapter(filterTagsAdapter);

                                    binding.filterHorizontalScrollView.setVisibility(View.VISIBLE);
                                } else if (filterTagsAdapter.getItemCount() == 0) {
                                    binding.filterHorizontalScrollView.setVisibility(View.GONE);
                                }
                            }

                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

                            if (binding.casesSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.casesSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);

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
                        binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.casesShimmerLayout.getRoot().stopShimmer();

                        if (binding.casesSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.casesSingleLayout.progressBar.setVisibility(View.GONE);
                        if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                            binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);
                    });

                    isLoading = false;
                }
            }
        });
    }

    public void responseAdapter() {
        data.put("page", 1);

        if (filterTagsAdapter.getIds().size() != 0) {
            isFiltered = true;
            data.put("tag", filterTagsAdapter.getIds());
        } else if (data.containsKey("tag")) {
            isFiltered = false;
            data.remove("tag");
        }

        // Cases Data
        binding.casesSingleLayout.getRoot().setVisibility(View.GONE);
        binding.casesShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.casesShimmerLayout.getRoot().startShimmer();

        getData();
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
        handler.removeCallbacksAndMessages(null);
    }

}