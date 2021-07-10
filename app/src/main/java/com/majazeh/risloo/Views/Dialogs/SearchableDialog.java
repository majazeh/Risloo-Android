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
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;
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

        setRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.inputEditText.getText().clear();
    }

    private void initializer() {
        searchableAdapter = new SearchableAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

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

                    setRecyclerView();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onDelayedClickListener(this::dismiss).widget(binding.entryButton);
    }

    private void setRecyclerView() {
        if (binding.searchProgressBar.getVisibility() == View.GONE)
            binding.searchProgressBar.setVisibility(View.VISIBLE);

        if (getCurrent() != null) {
            if (getCurrent() instanceof CreateCaseFragment) {
                switch (method) {
                    case "rooms":
                        data.put("my_management", "1");
                        break;
                    case "references":
                        data.put("id", ((CreateCaseFragment) getCurrent()).roomId);
                        data.put("usage", "create_case");
                        break;
                }

            } else if (getCurrent() instanceof CreateCaseUserFragment) {
                if (method.equals("references")) {
                    data.put("id", ((CreateCaseUserFragment) getCurrent()).roomId);
                    data.put("not_in_case", ((CreateCaseUserFragment) getCurrent()).caseId);
                }

            } else if (getCurrent() instanceof CreateCenterFragment) {
                if (method.equals("managers")) {
                    if (((CreateCenterFragment) getCurrent()).type.equals("personal_clinic"))
                        data.put("personal_clinic", "no");
                    else
                        data.put("personal_clinic", "yes");
                }

            } else if (getCurrent() instanceof CreateCenterUserFragment) {
                if (method.equals("rooms")) {
                    data.put("center", ((CreateCenterUserFragment) getCurrent()).centerId);
                }

            } else if (getCurrent() instanceof CreateRoomFragment) {
                if (method.equals("psychologies")) {
                    data.put("id", ((CreateRoomFragment) getCurrent()).centerId);
                    data.put("has_room", "no");
                    data.put("position", "manager,operator,psychologist,under_supervision");
                }

            } else if (getCurrent() instanceof CreateRoomUserFragment) {
                if (method.equals("references")) {
                    data.put("id", ((CreateRoomUserFragment) getCurrent()).centerId);
                    data.put("acceptation_room", ((CreateRoomUserFragment) getCurrent()).roomId);
                }

            } else if (getCurrent() instanceof CreateSampleFragment) {
                switch (method) {
                    case "scales":
                        // TODO : Place Code If  Needed
                        break;
                    case "references":
                        data.put("id", ((CreateSampleFragment) getCurrent()).roomId);
                        data.put("usage", "create_case");
                        break;
                    case "rooms":
                        data.put("my_management", "1");
                        data.put("instance", "1");
                        break;
                    case "cases":
                        data.put("room", ((CreateSampleFragment) getCurrent()).roomId);
                        data.put("instance", "1");
                        break;
                    case "sessions":
                        data.put("case", ((CreateSampleFragment) getCurrent()).caseId);
                        data.put("instance", "1");
                        break;
                }

            } else if (getCurrent() instanceof CreateScheduleReferenceFragment) {
                if (method.equals("cases")) {
                    // TODO : Place Code If  Needed
                }

            } else if (getCurrent() instanceof EditCenterDetailFragment) {
                if (method.equals("managers")) {
                    if (((EditCenterDetailFragment) getCurrent()).type.equals("personal_clinic"))
                        data.put("personal_clinic", "no");
                    else
                        data.put("personal_clinic", "yes");
                }

            }

            callList();
        }
    }

    private void callList() {
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
                if (getCurrent() != null)
                    if (getCurrent() instanceof CreateRoomUserFragment)
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
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", weekDay);
                jsonObject.put("title", weekDay);

                TypeModel model = new TypeModel(jsonObject);

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        searchableAdapter.setItems(values, method, binding.countTextView);
        binding.listRecyclerView.setAdapter(searchableAdapter);
    }

    private Fragment getCurrent() {
        Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateCaseFragment)
                return fragment;

            else if (fragment instanceof CreateCaseUserFragment)
                return fragment;

            else if (fragment instanceof CreateCenterFragment)
                return fragment;

            else if (fragment instanceof CreateCenterUserFragment)
                return fragment;

            else if (fragment instanceof CreateRoomFragment)
                return fragment;

            else if (fragment instanceof CreateRoomUserFragment)
                return fragment;

            else if (fragment instanceof CreateSampleFragment)
                return fragment;

            else if (fragment instanceof CreateScheduleFragment) {
                Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateScheduleReferenceFragment)
                        return childFragment;
                    else if (childFragment instanceof CreateScheduleTimeFragment)
                        return childFragment;

            } else if (fragment instanceof CreateSessionFragment) {
                Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateSessionTimeFragment)
                        return childFragment;

            } else if (fragment instanceof EditCenterFragment) {
                Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditCenterDetailFragment)
                        return childFragment;

            } else if (fragment instanceof EditSessionFragment) {
                Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditSessionTimeFragment)
                        return childFragment;
            }

        return null;
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