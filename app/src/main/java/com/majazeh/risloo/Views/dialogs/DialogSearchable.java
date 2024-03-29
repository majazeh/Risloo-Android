package com.majazeh.risloo.views.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ParamsManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.dialog.DialogSearchableAdapter;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCase;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCaseUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenter;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenterUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateRoom;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateRoomUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSample;
import com.majazeh.risloo.views.fragments.main.create.FragmentReserveSchedule;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterTags;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomTags;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditCenterTabDetail;
import com.majazeh.risloo.databinding.DialogSearchableBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogSearchable extends AppCompatDialogFragment {

    // Binding
    private DialogSearchableBinding binding;

    // Adapters
    private DialogSearchableAdapter searchableAdapter;

    // Fragments
    private Fragment current, child;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogSearchableBinding binding = DialogSearchableBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setAttributes(ParamsManager.windowMatchAndWrap(dialog));

        setCancelable(true);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogSearchableBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        setHashmap();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.inputEditText.getText().clear();
    }

    private void initializer() {
        searchableAdapter = new DialogSearchableAdapter(requireActivity());

        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();
        child = ((ActivityMain) requireActivity()).fragmont.getChild();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        InitManager.rcvVerticalUnfixed(requireActivity(), binding.listRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.inputEditText);
            return false;
        });

        binding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    data.put("q", String.valueOf(s));

                    if (binding.searchProgressBar.getVisibility() == View.GONE)
                        binding.searchProgressBar.setVisibility(View.VISIBLE);

                    setHashmap();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CustomClickView.onDelayedListener(this::dismiss).widget(binding.entryButton);
    }

    private void setDialog() {
        switch (method) {
            case "scales":
                binding.titleTextView.setText(getResources().getString(R.string.DialogScaleTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogScaleHint));
                binding.entryButton.setText(getResources().getString(R.string.DialogScaleEntry));
                break;
            case "references":
                binding.titleTextView.setText(getResources().getString(R.string.DialogReferenceTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogReferenceHint));

                if (current instanceof FragmentReserveSchedule)
                    binding.entryButton.setVisibility(View.GONE);
                else
                    binding.entryButton.setText(getResources().getString(R.string.DialogReferenceEntry));
                break;
            case "tags":
                binding.titleTextView.setText(getResources().getString(R.string.DialogTagTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogTagHint));

                if (current instanceof FragmentCenterTags || current instanceof FragmentRoomTags)
                    binding.entryButton.setVisibility(View.GONE);
                else
                    binding.entryButton.setText(getResources().getString(R.string.DialogTagEntry));
                break;
            case "managers":
                binding.titleTextView.setText(getResources().getString(R.string.DialogManagerTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogManagerHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "rooms":
                binding.titleTextView.setText(getResources().getString(R.string.DialogRoomTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogRoomHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "cases":
                binding.titleTextView.setText(getResources().getString(R.string.DialogCaseTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogCaseHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "sessions":
                binding.titleTextView.setText(getResources().getString(R.string.DialogSessionTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogSessionHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "psychologies":
                binding.titleTextView.setText(getResources().getString(R.string.DialogPsychologyTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogPsychologyHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "patternDays":
                binding.titleTextView.setText(getResources().getString(R.string.DialogDayTitle));
                binding.inputEditText.setVisibility(View.GONE);
                binding.entryButton.setText(getResources().getString(R.string.DialogDayEntry));
                break;
        }
    }

    private void setHashmap() {
        if (current instanceof FragmentCreateCase) {
            switch (method) {
                case "references":
                    if (StringManager.suffix(((FragmentCreateCase) current).typeModel.getClass().getName(), '.').equals("CenterModel")) {
                        CenterModel centerModel = (CenterModel) ((FragmentCreateCase) current).typeModel;
                        data.put("id", centerModel.getId());
                    } else if (StringManager.suffix(((FragmentCreateCase) current).typeModel.getClass().getName(), '.').equals("RoomModel")) {
                        RoomModel roomModel = (RoomModel) ((FragmentCreateCase) current).typeModel;
                        data.put("id", roomModel.getId());
                    }

                    data.put("usage", "create_case");
                    break;
                case "tags":
                    if (StringManager.suffix(((FragmentCreateCase) current).typeModel.getClass().getName(), '.').equals("CenterModel")) {
                        CenterModel centerModel = (CenterModel) ((FragmentCreateCase) current).typeModel;
                        data.put("region", centerModel.getId());
                    } else if (StringManager.suffix(((FragmentCreateCase) current).typeModel.getClass().getName(), '.').equals("RoomModel")) {
                        RoomModel roomModel = (RoomModel) ((FragmentCreateCase) current).typeModel;
                        data.put("region", roomModel.getCenter().getId());
                    }

                    break;
            }
        }

        if (current instanceof FragmentCreateCaseUser) {
            if (method.equals("references")) {
                data.put("id", ((FragmentCreateCaseUser) current).caseModel.getRoom().getId());
                data.put("not_in_case", ((FragmentCreateCaseUser) current).caseModel.getId());
            }
        }

        if (current instanceof FragmentCreateCenter) {
            if (method.equals("managers")) {
                if (((FragmentCreateCenter) current).type.equals("personal_clinic"))
                    data.put("personal_clinic", "no");
                else
                    data.put("personal_clinic", "yes");
            }
        }

        if (current instanceof FragmentCreateCenterUser) {
            if (method.equals("rooms")) {
                data.put("center", ((FragmentCreateCenterUser) current).centerModel.getId());
            }
        }

        if (current instanceof FragmentCreateRoom) {
            if (method.equals("psychologies")) {
                data.put("id", ((FragmentCreateRoom) current).centerModel.getId());
                data.put("has_room", "no");
                data.put("position", "manager,operator,psychologist,under_supervision");
            }
        }

        if (current instanceof FragmentCreateRoomUser) {
            if (method.equals("references")) {
                data.put("id", ((FragmentCreateRoomUser) current).roomModel.getCenter().getId());
                data.put("acceptation_room", ((FragmentCreateRoomUser) current).roomModel.getId());
            }
        }

        if (current instanceof FragmentCreateSample) {
            switch (method) {
                case "scales":
                    // TODO : Place Code If  Needed
                    break;
                case "references":
                    data.put("id", ((FragmentCreateSample) current).roomId);
                    data.put("status", "accepted");
                    break;
                case "rooms":
                    data.put("my_management", "1");
                    data.put("instance", "1");
                    break;
                case "cases":
                    data.put("room", ((FragmentCreateSample) current).roomId);
                    data.put("instance", "1");
                    break;
                case "sessions":
                    data.put("case", ((FragmentCreateSample) current).caseId);
                    data.put("instance", "1");
                    break;
            }
        }

        if (child instanceof FragmentCreateScheduleTabReference) {
            if (method.equals("cases")) {
                data.put("room", ((FragmentCreateScheduleTabReference) child).roomId);
                data.put("instance", "1");
            }
        }

        if (current instanceof FragmentReserveSchedule) {
            switch (method) {
                case "references":
                    data.put("id", ((FragmentReserveSchedule) current).roomId);
                    data.put("usage", "create_case");
                    break;
                case "cases":
                    data.put("room", ((FragmentReserveSchedule) current).roomId);
                    data.put("instance", "1");
                    break;
            }
        }

        if (child instanceof FragmentEditCenterTabDetail) {
            if (method.equals("managers")) {
                if (((FragmentEditCenterTabDetail) child).type.equals("personal_clinic"))
                    data.put("personal_clinic", "no");
                else
                    data.put("personal_clinic", "yes");
            }
        }

        if (current instanceof FragmentCenterTags) {
            if (method.equals("tags"))
                data.put("region", ((FragmentCenterTags) current).centerModel.getId());
        }

        if (current instanceof FragmentRoomTags) {
            if (method.equals("tags"))
                data.put("region", ((FragmentRoomTags) current).roomModel.getId());
        }

        getData();
    }

    private void getData() {
        if (binding.searchProgressBar.getVisibility() == View.GONE)
            binding.searchProgressBar.setVisibility(View.VISIBLE);

        switch (method) {
            case "scales":
                Sample.listInstance(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        List list = (List) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (!list.data().isEmpty()) {
                                    searchableAdapter.setItems(list.data(), method, binding.countTextView);
                                    binding.listRecyclerView.setAdapter(searchableAdapter);

                                    binding.emptyTextView.setVisibility(View.GONE);
                                } else {
                                    searchableAdapter.clearItems();

                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }

                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                });
                break;
            case "rooms":
                Room.list(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        List list = (List) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (!list.data().isEmpty()) {
                                    searchableAdapter.setItems(list.data(), method, null);
                                    binding.listRecyclerView.setAdapter(searchableAdapter);

                                    binding.emptyTextView.setVisibility(View.GONE);
                                } else {
                                    searchableAdapter.clearItems();

                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }

                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                });
                break;
            case "cases":
                Case.list(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        List list = (List) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (!list.data().isEmpty()) {
                                    searchableAdapter.setItems(list.data(), method, null);
                                    binding.listRecyclerView.setAdapter(searchableAdapter);

                                    binding.emptyTextView.setVisibility(View.GONE);
                                } else {
                                    searchableAdapter.clearItems();

                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }

                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                });
                break;
            case "sessions":
                Session.list(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        List list = (List) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (!list.data().isEmpty()) {
                                    searchableAdapter.setItems(list.data(), method, null);
                                    binding.listRecyclerView.setAdapter(searchableAdapter);

                                    binding.emptyTextView.setVisibility(View.GONE);
                                } else {
                                    searchableAdapter.clearItems();

                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }

                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                });
                break;
            case "references":
                if (current instanceof FragmentCreateRoomUser)
                    Center.users(data, header, new Response() {
                        @Override
                        public void onOK(Object object) {
                            List list = (List) object;

                            if (isAdded()) {
                                requireActivity().runOnUiThread(() -> {
                                    if (!list.data().isEmpty()) {
                                        searchableAdapter.setItems(list.data(), method, binding.countTextView);
                                        binding.listRecyclerView.setAdapter(searchableAdapter);

                                        binding.emptyTextView.setVisibility(View.GONE);
                                    } else {
                                        searchableAdapter.clearItems();

                                        binding.emptyTextView.setVisibility(View.VISIBLE);
                                    }

                                    if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                        binding.searchProgressBar.setVisibility(View.GONE);
                                });
                            }
                        }

                        @Override
                        public void onFailure(String response) {
                            if (isAdded()) {
                                requireActivity().runOnUiThread(() -> {
                                    if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                        binding.searchProgressBar.setVisibility(View.GONE);
                                });
                            }
                        }
                    });
                else
                    Room.users(data, header, new Response() {
                        @Override
                        public void onOK(Object object) {
                            List list = (List) object;

                            if (isAdded()) {
                                requireActivity().runOnUiThread(() -> {
                                    if (!list.data().isEmpty()) {
                                        searchableAdapter.setItems(list.data(), method, binding.countTextView);
                                        binding.listRecyclerView.setAdapter(searchableAdapter);

                                        binding.emptyTextView.setVisibility(View.GONE);
                                    } else {
                                        searchableAdapter.clearItems();

                                        binding.emptyTextView.setVisibility(View.VISIBLE);
                                    }

                                    if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                        binding.searchProgressBar.setVisibility(View.GONE);
                                });
                            }
                        }

                        @Override
                        public void onFailure(String response) {
                            if (isAdded()) {
                                requireActivity().runOnUiThread(() -> {
                                    if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                        binding.searchProgressBar.setVisibility(View.GONE);
                                });
                            }
                        }
                    });
                break;
            case "tags":
                Case.tags(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List list = (List) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (!list.data().isEmpty()) {
                                searchableAdapter.setItems(list.data(), method, binding.countTextView);
                                binding.listRecyclerView.setAdapter(searchableAdapter);

                                binding.emptyTextView.setVisibility(View.GONE);
                            } else {
                                String input = binding.inputEditText.getText().toString();

                                if (!input.equals("")) {
                                    searchableAdapter.clearItems();
                                    binding.emptyTextView.setVisibility(View.GONE);

                                    try {
                                        TagModel typeModel = new TagModel(new JSONObject().put("id", input).put("title", input));
                                        searchableAdapter.addItem(typeModel);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    searchableAdapter.clearItems();
                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }
                            }

                            if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchProgressBar.setVisibility(View.GONE);
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchProgressBar.setVisibility(View.GONE);
                        });
                    }
                }
            });
                break;
            case "psychologies":
                Center.users(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        List list = (List) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (!list.data().isEmpty()) {
                                    searchableAdapter.setItems(list.data(), method, null);
                                    binding.listRecyclerView.setAdapter(searchableAdapter);

                                    binding.emptyTextView.setVisibility(View.GONE);
                                } else {
                                    searchableAdapter.clearItems();

                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }

                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                });
                break;
            case "managers":
                User.list(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        List list = (List) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (!list.data().isEmpty()) {
                                    searchableAdapter.setItems(list.data(), method, null);
                                    binding.listRecyclerView.setAdapter(searchableAdapter);

                                    binding.emptyTextView.setVisibility(View.GONE);
                                } else {
                                    searchableAdapter.clearItems();

                                    binding.emptyTextView.setVisibility(View.VISIBLE);
                                }

                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.searchProgressBar.setVisibility(View.GONE);
                            });
                        }
                    }
                });
                break;
            case "patternDays":
                patternList();
                break;
        }
    }

    private void patternList() {
        ArrayList<TypeModel> values = new ArrayList<>();
        String[] weekDays = requireActivity().getResources().getStringArray(R.array.WeekDays);

        for (String weekDay : weekDays) {
            try {
                TypeModel model = new TypeModel(new JSONObject().put("id", weekDay));

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        searchableAdapter.setItems(values, method, binding.countTextView);
        binding.listRecyclerView.setAdapter(searchableAdapter);

        if (binding.searchProgressBar.getVisibility() == View.VISIBLE)
            binding.searchProgressBar.setVisibility(View.GONE);
    }

    public void setData(String method) {
        this.method = method;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacksAndMessages(null);
    }

}