package com.majazeh.risloo.Views.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SearchableAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.databinding.DialogSearchableBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchableDialog extends AppCompatDialogFragment {

    // Binding
    private DialogSearchableBinding binding;

    // Adapters
    private SearchableAdapter searchableAdapter;

    // Fragments
    private Fragment current, child;

    // Objects
    private Handler handler;

    // Vars
    private HashMap data, header;
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogSearchableBinding binding = DialogSearchableBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.getWindow().setAttributes(ParamsManager.applyWrapContent(dialog));

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogSearchableBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        detector();

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
        searchableAdapter = new SearchableAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();
        child = ((MainActivity) requireActivity()).fragmont.getChild();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.inputEditText);
                }
            }
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

        ClickManager.onDelayedClickListener(this::dismiss).widget(binding.entryButton);
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
                binding.entryButton.setText(getResources().getString(R.string.DialogReferenceEntry));
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
        if (current instanceof CreateCaseFragment) {
            switch (method) {
                case "rooms":
                    data.put("my_management", "1");
                    break;
                case "references":
                    data.put("id", ((CreateCaseFragment) current).roomId);
                    data.put("usage", "create_case");
                    break;
            }
        }

        if (current instanceof CreateCaseUserFragment) {
            if (method.equals("references")) {
                data.put("id", ((CreateCaseUserFragment) current).roomId);
                data.put("not_in_case", ((CreateCaseUserFragment) current).caseId);
            }
        }

        if (current instanceof CreateCenterFragment) {
            if (method.equals("managers")) {
                if (((CreateCenterFragment) current).type.equals("personal_clinic"))
                    data.put("personal_clinic", "no");
                else
                    data.put("personal_clinic", "yes");
            }
        }

        if (current instanceof CreateCenterUserFragment) {
            if (method.equals("rooms")) {
                data.put("center", ((CreateCenterUserFragment) current).centerId);
            }
        }

        if (current instanceof CreateRoomFragment) {
            if (method.equals("psychologies")) {
                data.put("id", ((CreateRoomFragment) current).centerId);
                data.put("has_room", "no");
                data.put("position", "manager,operator,psychologist,under_supervision");
            }
        }

        if (current instanceof CreateRoomUserFragment) {
            if (method.equals("references")) {
                data.put("id", ((CreateRoomUserFragment) current).centerId);
                data.put("acceptation_room", ((CreateRoomUserFragment) current).roomId);
            }
        }

        if (current instanceof CreateSampleFragment) {
            switch (method) {
                case "scales":
                    // TODO : Place Code If  Needed
                    break;
                case "references":
                    data.put("id", ((CreateSampleFragment) current).roomId);
                    data.put("status", "accepted");
                    break;
                case "rooms":
                    data.put("my_management", "1");
                    data.put("instance", "1");
                    break;
                case "cases":
                    data.put("room", ((CreateSampleFragment) current).roomId);
                    data.put("instance", "1");
                    break;
                case "sessions":
                    data.put("case", ((CreateSampleFragment) current).caseId);
                    data.put("instance", "1");
                    break;
            }
        }

        if (child instanceof CreateScheduleReferenceFragment) {
            if (method.equals("cases")) {
                data.put("room", ((CreateScheduleReferenceFragment) child).roomId);
                data.put("instance", "1");
            }
        }

        if (child instanceof EditCenterDetailFragment) {
            if (method.equals("managers")) {
                if (((EditCenterDetailFragment) child).type.equals("personal_clinic"))
                    data.put("personal_clinic", "no");
                else
                    data.put("personal_clinic", "yes");
            }
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
                if (current instanceof CreateRoomUserFragment)
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